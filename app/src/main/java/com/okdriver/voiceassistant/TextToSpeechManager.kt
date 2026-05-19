package com.okdriver.voiceassistant

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechManager @Inject constructor(@ApplicationContext private val context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var pendingText: String? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                tts?.language = Locale("hi", "IN")
                tts?.setSpeechRate(1.1f)
                pendingText?.let { speak(it) }
                pendingText = null
                Log.d("TTS", "Initialized successfully")
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }
    }

    /**
     * Speaks the given text. If TTS is not yet ready the request is queued.
     * @param onStart Called when speaking begins, passing the timestamp (T4).
     * @param onDone Called when speaking completes or fails.
     */
    fun speak(text: String, onStart: ((Long) -> Unit)? = null, onDone: (() -> Unit)? = null) {
        if (!isInitialized) {
            pendingText = text
            return
        }
        val utteranceId = UUID.randomUUID().toString()
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                val ts = System.currentTimeMillis()
                Log.d("TTS", "Speaking started at $ts")
                onStart?.invoke(ts)
            }
            override fun onDone(utteranceId: String?) {
                Log.d("TTS", "Speaking finished")
                onDone?.invoke()
            }
            override fun onError(utteranceId: String?) {
                Log.e("TTS", "Error speaking utterance $utteranceId")
                onDone?.invoke()
            }
        })
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }

    fun shutdown() {
        tts?.shutdown()
    }
}
