package com.okdriver.voiceassistant.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import com.okdriver.voiceassistant.ui.component.VoiceOverlayView
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceOverlayManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var overlayView: VoiceOverlayView? = null
    var isShowing = false

    private var onCloseCallback: (() -> Unit)? = null

    fun setOnCloseCallback(callback: () -> Unit) {
        onCloseCallback = callback
    }

    fun showOverlay() {
        if (isShowing) return

        overlayView = VoiceOverlayView(context)

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,  // For instant rendering
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        windowManager.addView(overlayView, params)
        overlayView?.show()

        // Setup close listener
        overlayView?.setOnCloseListener {
            isShowing = false
            onCloseCallback?.invoke()
            try {
                windowManager.removeView(overlayView)
            } catch (e: Exception) {}
            overlayView = null
        }

        isShowing = true
    }

    fun hideOverlay() {
        if (!isShowing) return
        overlayView?.dismiss {
            try {
                windowManager.removeView(overlayView)
            } catch (e: Exception) {}
            overlayView = null
            isShowing = false
        }
    }

    fun setSleeping() {
        overlayView?.setStateSleeping()
    }

    fun setSleepingIdle() {
        overlayView?.setStateIdlePulse()
    }

    fun setIdleState() {
        overlayView?.updateIdlePrompt()
    }

    fun showIdleTimeout() {
        overlayView?.showIdleTimeoutWarning()
    }

    fun setListening() {
        overlayView?.setStateListening()
    }

    fun setThinking() {
        overlayView?.setStateThinking()
    }

    fun setSpeaking(response: String) {
        overlayView?.setStateSpeaking(response)
    }

    fun updateTranscript(text: String) {
        overlayView?.updateTranscript(text)
    }

    fun updateAmplitude(amp: Float) {
        overlayView?.updateAmplitude(amp)
    }

    fun updateOverlayStatus(status: String) {
        overlayView?.updateStatus(status)
    }}