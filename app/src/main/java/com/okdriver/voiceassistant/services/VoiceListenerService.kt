package com.okdriver.voiceassistant.services

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.okdriver.voiceassistant.MainActivity
import com.okdriver.voiceassistant.overlay.VoiceOverlayManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class AssistantState {
    SLEEPING,        // waiting for wake word
    LISTENING,       // recording user query
    THINKING,        // calling Groq API
    SPEAKING         // playing TTS response
}

class VoiceListenerService : Service() {

    private lateinit var recognizer: SpeechRecognizer
    private lateinit var tts: TextToSpeech
    private lateinit var overlayManager: VoiceOverlayManager
    private lateinit var handler: Handler
    private var state = AssistantState.SLEEPING
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    companion object {
        const val CHANNEL_ID = "voice_listener_channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())

        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                buildNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
            )
        } else {
            @Suppress("DEPRECATION")
            startForeground(NOTIFICATION_ID, buildNotification())
        }

        // Init overlay manager
        overlayManager = VoiceOverlayManager(this)
        overlayManager.setOnCloseCallback {
            startWakeWordListening()
        }

        // Init TTS first
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale("hi", "IN")
                tts.setSpeechRate(1.0f)
                tts.setOnUtteranceProgressListener(ttsListener)
                Log.d("VLS", "TTS initialized")
            } else {
                Log.e("VLS", "TTS initialization failed")
            }
        }

        // Init recognizer on main thread
        handler.post {
            if (SpeechRecognizer.isRecognitionAvailable(this)) {
                recognizer = SpeechRecognizer.createSpeechRecognizer(this)
                recognizer.setRecognitionListener(recognitionListener)
                startWakeWordListening()
            } else {
                Log.e("VLS", "Speech recognition not available on this device")
            }
        }
    }

    // ============ WAKE WORD LISTENING ============
    private fun startWakeWordListening() {
        state = AssistantState.SLEEPING
        updateOverlay(AssistantState.SLEEPING)
        overlayManager.setSleeping()
        Log.d("VLS", "Started wake word listening")

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 500L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 300L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 300L)
        }
        try {
            recognizer.startListening(intent)
        } catch (e: Exception) {
            Log.e("VLS", "Error starting wake word listening", e)
            handler.postDelayed({ startWakeWordListening() }, 1000)
        }
    }

    // ============ QUERY LISTENING ============
    private fun startQueryListening() {
        state = AssistantState.LISTENING
        updateOverlay(AssistantState.LISTENING)
        overlayManager.showOverlay()
        Log.d("VLS", "Started query listening")

        // Beep sound (Siri-style chime)
        playBeep()

        handler.postDelayed({
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-IN")
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2500L)
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 2000L)
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 500L)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            }
            try {
                recognizer.startListening(intent)
            } catch (e: Exception) {
                Log.e("VLS", "Error starting query listening", e)
                handler.postDelayed({ startQueryListening() }, 500)
            }
        }, 300)
    }

    // ============ SINGLE RECOGNITION LISTENER ============
    private val recognitionListener = object : RecognitionListener {

        override fun onPartialResults(partialResults: Bundle) {
            val text = partialResults
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.firstOrNull() ?: return

            when (state) {
                AssistantState.SLEEPING -> {
                    if (isWakeWord(text)) {
                        recognizer.cancel()
                        onWakeWordDetected()
                    }
                }
                AssistantState.LISTENING -> {
                    overlayManager.updateTranscript(text)
                }
                else -> {}
            }
        }

        override fun onResults(results: Bundle) {
            val text = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.firstOrNull()?.trim() ?: ""

            Log.d("VLS", "Recognition result in state $state: $text")

            when (state) {
                AssistantState.SLEEPING -> {
                    if (isWakeWord(text)) {
                        onWakeWordDetected()
                    } else {
                        handler.postDelayed({ startWakeWordListening() }, 100)
                    }
                }
                AssistantState.LISTENING -> {
                    if (text.isBlank()) {
                        overlayManager.updateOverlayStatus("I didn't catch that. Ask again...")
                        handler.postDelayed({ startQueryListening() }, 1000)
                        return
                    }
                    if (checkForCloseCommand(text)) {
                        overlayManager.hideOverlay()
                        startWakeWordListening()
                        return
                    }
                    sendQueryToGroq(text)
                }
                else -> {}
            }
        }

        override fun onError(error: Int) {
            val errorMsg = when (error) {
                SpeechRecognizer.ERROR_NO_MATCH -> "no_match"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "timeout"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "busy"
                SpeechRecognizer.ERROR_AUDIO -> "audio"
                SpeechRecognizer.ERROR_NETWORK -> "network"
                else -> "error_$error"
            }
            Log.w("VLS", "Recognition error in state $state: $errorMsg")

            when (state) {
                AssistantState.SLEEPING -> {
                    handler.postDelayed({ startWakeWordListening() }, 200)
                }
                AssistantState.LISTENING -> {
                    when (error) {
                        SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                            handler.postDelayed({ startQueryListening() }, 1000)
                        }
                        SpeechRecognizer.ERROR_AUDIO -> {
                            try {
                                recognizer.destroy()
                                recognizer = SpeechRecognizer.createSpeechRecognizer(this@VoiceListenerService)
                                recognizer.setRecognitionListener(this)
                            } catch (e: Exception) {
                                Log.e("VLS", "Error recreating recognizer", e)
                            }
                            handler.postDelayed({ startQueryListening() }, 500)
                        }
                        SpeechRecognizer.ERROR_NO_MATCH,
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                            overlayManager.updateOverlayStatus("I didn't catch that. Ask again...")
                            handler.postDelayed({ startQueryListening() }, 1000)
                        }
                        else -> {
                            handler.postDelayed({ startQueryListening() }, 500)
                        }
                    }
                }
                else -> {}
            }
        }

        override fun onReadyForSpeech(params: Bundle) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {
            if (state == AssistantState.LISTENING) {
                overlayManager.updateAmplitude(rmsdB)
            }
        }
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    // ============ WAKE WORD DETECTED ============
    private fun onWakeWordDetected() {
        Log.d("VLS", "Wake word detected!")

        // Vibrate
        try {
            val vibrator = getSystemService(Vibrator::class.java)
            vibrator?.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE))
        } catch (e: Exception) {
            Log.w("VLS", "Vibration failed: ${e.message}")
        }

        handler.postDelayed({ startQueryListening() }, 150)
    }

    // ============ GROQ API CALL ============
    private fun sendQueryToGroq(query: String) {
        state = AssistantState.THINKING
        updateOverlay(AssistantState.THINKING)
        overlayManager.updateTranscript(query)
        Log.d("VLS", "Sending query to Groq: $query")

        val apiKey = getApiKeyFromEncryptedPrefs()?.takeIf { it.isNotBlank() }.orEmpty()
        if (apiKey.isBlank()) {
            Toast.makeText(this, "No API key set. Please configure in Settings.",
                Toast.LENGTH_LONG).show()
            speakAndRestart("Please set your Groq API key in Settings.")
            return
        }

        // DEBUG: Show query and partial key
        handler.post {
            Toast.makeText(this, "Query: $query | Key: ${apiKey.take(8)}...",
                Toast.LENGTH_LONG).show()
        }

        serviceScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build()

                val body = """
                    {
                        "model": "openai/gpt-oss-120b",
                        "max_tokens": 150,
                        "messages": [
                            {
                                "role": "system",
                                "content": "You are okDriver, a friendly AI road safety assistant. Keep ALL responses under 2 short sentences. If user speaks Hindi or Hinglish, reply in Hinglish. Be conversational and warm."
                            },
                            {
                                "role": "user",
                                "content": "$query"
                            }
                        ]
                    }
                """.trimIndent()

                val request = Request.Builder()
                    .url("https://api.groq.com/openai/v1/chat/completions")
                    .addHeader("Authorization", "Bearer $apiKey")
                    .addHeader("Content-Type", "application/json")
                    .post(body.toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    val errorBody = response.body?.string()
                    Log.e("VLS", "API error: ${response.code} - $errorBody")
                    withContext(Dispatchers.Main) {
                        speakAndRestart("API error: ${response.code}. Check your Groq API key.")
                    }
                    return@launch
                }

                val responseStr = response.body?.string() ?: throw Exception("Empty response body")
                Log.d("VLS", "API Response: $responseStr")

                val json = JSONObject(responseStr)
                val answer = json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim()

                Log.d("VLS", "LLM answer: $answer")

                withContext(Dispatchers.Main) {
                    // DEBUG: Show response
                    Toast.makeText(
                        this@VoiceListenerService, "Response: ${answer.take(50)}...",
                        Toast.LENGTH_LONG
                    ).show()
                    speakAndRestart(answer)
                }

            } catch (e: Exception) {
                Log.e("VLS", "Network/parse error", e)
                withContext(Dispatchers.Main) {
                    speakAndRestart("Network error. Please check your connection.")
                }
            }
        }
    }

    // ============ SPEAK RESPONSE THEN RESTART LISTENING ============
    private fun speakAndRestart(text: String) {
        state = AssistantState.SPEAKING
        updateOverlay(AssistantState.SPEAKING)
        overlayManager.setSpeaking(text)
        Log.d("VLS", "Speaking: $text")

        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "RESPONSE")
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "RESPONSE")
    }

    private val ttsListener = object : UtteranceProgressListener() {
        override fun onDone(utteranceId: String?) {
            Log.d("VLS", "TTS done, restarting query listening")
            handler.postDelayed({
                overlayManager.updateOverlayStatus("Ask another question...")
                startQueryListening()
            }, 800)
        }
        override fun onError(utteranceId: String?) {
            Log.e("VLS", "TTS error")
            handler.postDelayed({ startQueryListening() }, 500)
        }
        override fun onStart(utteranceId: String?) {}
    }

    // ============ WAKE WORD MATCHING ============
    private fun isWakeWord(text: String): Boolean {
        val t = text.lowercase().trim()
            .replace(".", "").replace(",", "")
            .replace("okay", "ok").replace("okey", "ok")

        val triggers = listOf(
            "ok driver", "okay driver", "okdriver",
            "ok drive", "ok dryer", "ok draiver",
            "hey driver", "hai driver", "hey dr",
            "ok dr", "a driver", "ok diver",
            "o driver", "ek driver"
        )
        if (triggers.any { t.contains(it) }) {
            Log.d("VLS", "Wake word matched: $t")
            return true
        }

        // Fuzzy match
        val words = t.split(" ")
        for (i in 0 until words.size - 1) {
            val pair = "${words[i]} ${words[i+1]}"
            if (levenshtein(pair, "ok driver") <= 2) {
                Log.d("VLS", "Wake word fuzzy matched: $pair -> ok driver")
                return true
            }
        }
        return false
    }

    private fun levenshtein(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        for (i in 1..s1.length)
            for (j in 1..s2.length)
                dp[i][j] = if (s1[i-1] == s2[j-1]) dp[i-1][j-1]
                            else 1 + minOf(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
        return dp[s1.length][s2.length]
    }

    private fun checkForCloseCommand(text: String): Boolean {
        val t = text.lowercase()
        return listOf("band karo", "close", "dismiss", "cancel",
            "bye", "thank you", "shukriya", "band kar", "hatao")
            .any { t.contains(it) }
    }

    // ============ BEEP SOUND ============
    private fun playBeep() {
        try {
            val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 60)
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150)
            handler.postDelayed({ toneGen.release() }, 300)
        } catch (e: Exception) {
            Log.w("VLS", "Beep failed: ${e.message}")
        }
    }

    // ============ GET API KEY FROM ENCRYPTED PREFS ============
    private fun getApiKeyFromEncryptedPrefs(): String? {
        return try {
            val masterKey = MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            val prefs = EncryptedSharedPreferences.create(
                this, "okdriver_prefs", masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            val key = prefs.getString("groq_api_key", null)
            Log.d("VLS", "Retrieved API key from prefs: ${if (key != null) "SUCCESS" else "NULL"}")
            key
        } catch (e: Exception) {
            Log.e("VLS", "Error reading API key from prefs", e)
            null
        }
    }

    // ============ UI UPDATES ============
    private fun updateOverlay(newState: AssistantState) {
        handler.post {
            when (newState) {
                AssistantState.SLEEPING -> overlayManager.updateOverlayStatus("Ready for wake word...")
                AssistantState.LISTENING -> overlayManager.updateOverlayStatus("Listening...")
                AssistantState.THINKING -> overlayManager.updateOverlayStatus("Thinking...")
                AssistantState.SPEAKING -> overlayManager.updateOverlayStatus("Speaking...")
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Voice Listener Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the voice assistant listening for wake word"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_IMMUTABLE
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("okDriver Listening")
            .setContentText("Tap to open dashboard")
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            recognizer.destroy()
            tts.shutdown()
        } catch (e: Exception) {
            Log.e("VLS", "Error in onDestroy", e)
        }
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
