package com.okdriver.voiceassistant.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

data class WakeWordEvent(val timestamp: Long, val confidence: Float)

@Singleton
class WakeWordDetector @Inject constructor(@ApplicationContext private val context: Context) :
    RecognitionListener {
    private val mainHandler = Handler(Looper.getMainLooper())

    private var speechService: SpeechService? = null
    private var model: Model? = null
    private var lastWakeWordTime = 0L
    private var isPaused = false

    private val _wakeWordFlow = MutableSharedFlow<WakeWordEvent>(extraBufferCapacity = 1)
    val wakeWordFlow: SharedFlow<WakeWordEvent> = _wakeWordFlow

    init {
        initVosk()
    }

    private fun initVosk() {
        StorageService.unpack(context, "model", "model",
            { model: Model ->
                this.model = model
                mainHandler.post { startEngine() }
            },
            { exception: Exception ->
                Log.e("WakeWordDetector", "Failed to unpack Vosk model", exception)
                mainHandler.post {
                    Toast.makeText(context, "Vosk Model Error: ${exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun startEngine() {
        val m = model ?: run {
            Log.e("WakeWordDetector", "Model is null! Cannot start engine.")
            return
        }
        if (speechService != null) {
            Log.w("WakeWordDetector", "SpeechService already running, skipping startEngine")
            return
        }
        try {
            // Use a small wake-word grammar so Vosk focuses on just the trigger phrases.
            // This is much better for far-field wake words than free-form recognition.
            val grammar = "[\"ok driver\",\"okay driver\",\"hey driver\",\"hai driver\",\"ok drive\",\"ok dr\",\"okdriver\",\"ok diver\",\"ok draiver\"]"
            val rec = Recognizer(m, 16000.0f, grammar)
            speechService = SpeechService(rec, 16000.0f)
            speechService?.startListening(this)
            Log.d("WakeWordDetector", "Vosk engine started OK")
        } catch (e: Exception) {
            Log.e("WakeWordDetector", "Error starting speech service", e)
        }
    }

    /**
     * ACTUALLY releases the microphone by stopping Vosk's AudioRecord.
     * setPause() does NOT release the mic hardware — stop() does.
     * Safe to call because we post to mainHandler (never from inside C++ callback).
     */
    fun pauseForQuery() {
        if (isPaused) return
        isPaused = true
        mainHandler.post {
            try {
                speechService?.stop()   // Releases AudioRecord hardware
                speechService = null    // Allow GC to free resources
                Log.d("WakeWordDetector", "Vosk stopped — mic released for STT")
            } catch (e: Exception) {
                Log.e("WakeWordDetector", "Error stopping Vosk for STT handoff", e)
            }
        }
    }

    /**
     * Restart Vosk after Android STT is done with the microphone.
     */
    fun resumeAfterQuery() {
        if (!isPaused) return
        isPaused = false
        mainHandler.postDelayed({
            Log.d("WakeWordDetector", "Restarting Vosk engine...")
            startEngine()
        }, 700) // shorter delay for faster return to wake-word listening
    }

    // Legacy compat
    fun start() = resumeAfterQuery()
    fun stop() = pauseForQuery()
    fun wakeUp() {} // No longer needed — STT handles query capture
    fun sleep() = resumeAfterQuery()

    // ── Vosk Callbacks ────────────────────────────────────────────────────────

    override fun onResult(hypothesis: String?) {
        if (isPaused) return // Ignore while STT is active
        val text = extractText(hypothesis?.lowercase() ?: "")
        if (text.isNotBlank()) {
            Log.d("WakeWordDetector", "onResult: '$text'")
            checkForWakeWord(text)
        }
    }

    override fun onPartialResult(hypothesis: String?) {
        if (isPaused) return
        val partial = extractText(hypothesis?.lowercase() ?: "")
        if (partial.isNotBlank()) {
            // Check partial results immediately for faster wake word response.
            checkForWakeWord(partial)
        }
    }

    override fun onFinalResult(hypothesis: String?) {
        if (isPaused) return
        val text = extractText(hypothesis?.lowercase() ?: "")
        if (text.isNotBlank()) checkForWakeWord(text)
    }

    private fun checkForWakeWord(text: String) {
        if (text.isBlank()) return

        val now = System.currentTimeMillis()
        if (now - lastWakeWordTime < 5000) return // 5s debounce — prevents rapid re-triggers

        if (!isWakeWord(text)) return

        lastWakeWordTime = now

        Log.d("WakeWordDetector", "✅ Wake word detected: '$text'")
        mainHandler.post {
            _wakeWordFlow.tryEmit(WakeWordEvent(now, 1.0f))
        }
    }

    /**
     * Fast wake word detection with optimized fuzzy matching.
     * Handles mishearings like "okay", "okey", "ok. driver", etc.
     * Uses Levenshtein distance ONLY as fallback (expensive operation).
     */
    private fun isWakeWord(text: String): Boolean {
        val normalized = text.lowercase()
            .replace(Regex("[^a-z0-9 ]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()

        if (normalized.isBlank()) return false

        val compact = normalized.replace(" ", "")
        if (compact.contains("okdriver") || compact.contains("heydriver")) return true

        val tokens = normalized.split(" ").filter { it.isNotBlank() }
        if (tokens.isEmpty()) return false

        val okTokens = setOf("ok", "okay", "okey", "oki", "oke", "oak", "okk", "ohk")
        fun isOkLike(token: String): Boolean {
            if (token in okTokens) return true
            return token.length in 1..4 && levenshteinDistance(token, "ok") <= 1
        }

        fun isDriverLike(token: String): Boolean {
            if (token.startsWith("driver") || token.startsWith("driv") || token.startsWith("dri") || token.startsWith("dr")) return true
            return token.length >= 2 && levenshteinDistance(token, "driver") <= 2
        }

        for (i in tokens.indices) {
            val token = tokens[i]

            // Direct phrase variants first.
            if (
                token == "hey" && i + 1 < tokens.size && isDriverLike(tokens[i + 1])
            ) return true

            if (isOkLike(token)) {
                if (i + 1 < tokens.size && isDriverLike(tokens[i + 1])) return true
                if (i + 2 < tokens.size && tokens[i + 1] == "the" && isDriverLike(tokens[i + 2])) return true
            }

            // Handle merged forms like "okdriver", "okaydriver", "heydriver".
            if (compact.contains("okdriver") || compact.contains("heydriver")) return true
        }

        // Fuzzy fallback on short phrases only.
        if (tokens.size <= 4) {
            for (i in 0 until tokens.size - 1) {
                val twoWords = "${tokens[i]} ${tokens[i + 1]}"
                if (twoWords.length in 4..20) {
                    if (levenshteinDistance(twoWords, "ok driver") <= 2) return true
                    if (levenshteinDistance(twoWords, "hey driver") <= 2) return true
                }
            }
        }

        return false
    }

    /**
     * Optimized Levenshtein distance - only called as fallback for fuzzy matching.
     * Adds early termination for distance > 2 to avoid unnecessary computation.
     */
    private fun levenshteinDistance(s1: String, s2: String): Int {
        if (abs(s1.length - s2.length) > 2) return Int.MAX_VALUE  // Early exit

        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                dp[i][j] = if (s1[i-1] == s2[j-1]) dp[i-1][j-1]
                           else 1 + minOf(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
            }
            // Early termination if minimum distance exceeds threshold
            if (dp[i].minOrNull() ?: Int.MAX_VALUE > 2) continue
        }
        return dp[s1.length][s2.length]
    }

    override fun onError(exception: Exception?) {
        Log.e("WakeWordDetector", "Vosk Error", exception)
    }

    override fun onTimeout() {}

    private fun extractText(json: String): String {
        return try {
            val start = json.indexOf("\"text\"")
            if (start < 0) return ""
            val colon = json.indexOf(":", start)
            val quote1 = json.indexOf("\"", colon + 1)
            val quote2 = json.indexOf("\"", quote1 + 1)
            if (quote1 < 0 || quote2 < 0) return ""
            json.substring(quote1 + 1, quote2).trim()
        } catch (e: Exception) { "" }
    }
}