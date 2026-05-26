package com.okdriver.voiceassistant.services

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

data class SttResult(val timestamp: Long, val text: String, val isFinal: Boolean)

@Singleton
class SpeechToTextManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val mainHandler = Handler(Looper.getMainLooper())

    private var speechRecognizer: SpeechRecognizer? = null
    private val _sttFlow = MutableSharedFlow<SttResult>(extraBufferCapacity = 5)
    val sttFlow: SharedFlow<SttResult> = _sttFlow

    private val _errorFlow = MutableSharedFlow<Int>(extraBufferCapacity = 5)
    val errorFlow: SharedFlow<Int> = _errorFlow

    private val _rmsFlow = MutableSharedFlow<Float>(
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val rmsFlow: SharedFlow<Float> = _rmsFlow

    private fun buildRecognitionListener() = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            Log.d("STT", "Ready for speech — playing beep")
            playBeep() // 🔔 Beep when mic is ready!
        }
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {
            // Normalize RMS from -2f..10f to 0.0..1.0 roughly
            val normalized = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
            _rmsFlow.tryEmit(normalized)
        }
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {
            Log.e("STT", "Error code $error")
            CoroutineScope(Dispatchers.Default).launch {
                _errorFlow.emit(error)
            }
        }
        override fun onResults(results: Bundle?) {
            handleResult(results, isFinal = true)
        }
        override fun onPartialResults(partialResults: Bundle?) {
            handleResult(partialResults, isFinal = false)
        }
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    /** Play a short confirmation beep using ToneGenerator */
    private fun playBeep() {
        try {
            val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150) // 150ms beep
            mainHandler.postDelayed({ toneGen.release() }, 300)
        } catch (e: Exception) {
            Log.e("STT", "Could not play beep", e)
        }
    }

    private fun handleResult(results: Bundle?, isFinal: Boolean) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val text = matches?.firstOrNull() ?: ""
        if (text.isNotBlank()) {
            val ts = System.currentTimeMillis()
            CoroutineScope(Dispatchers.Default).launch {
                _sttFlow.emit(SttResult(ts, text, isFinal))
            }
        }
    }

    /**
     * Reuses a warm SpeechRecognizer when available for lower latency.
     */
    fun startListening(languageCode: String = "en-IN") {
        mainHandler.post {
            try {
                if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                    Log.e("STT", "Speech recognizer not available")
                    CoroutineScope(Dispatchers.Default).launch { _errorFlow.emit(0) }
                    return@post
                }

                speechRecognizer?.destroy()
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
                speechRecognizer?.setRecognitionListener(buildRecognitionListener())

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languageCode)
                    putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
                    putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                    putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
                    putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000L)
                    putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1200L)
                    putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 800L)
                }
                speechRecognizer?.startListening(intent)
                Log.d("STT", "Started fresh SpeechRecognizer with language $languageCode")
            } catch (e: Exception) {
                Log.e("STT", "Error starting Google SpeechRecognizer", e)
                CoroutineScope(Dispatchers.Default).launch { _errorFlow.emit(0) }
            }
        }
    }

    fun stopListening() {
        mainHandler.post {
            try {
                speechRecognizer?.stopListening()
            } catch (_: Exception) {}
            speechRecognizer?.destroy()
            speechRecognizer = null
        }
        Log.d("STT", "Stopped and destroyed SpeechRecognizer")
    }
}