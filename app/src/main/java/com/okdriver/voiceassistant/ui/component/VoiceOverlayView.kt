package com.okdriver.voiceassistant.ui.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

class VoiceOverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val scrim: View
    private val sheet: LinearLayout
    private val statusText: TextView
    private val transcriptText: TextView
    private val responseText: TextView
    private val waveformView: WaveformView
    private val orbView: OrbAnimationView
    private val closeButton: TextView
    private val idlePromptText: TextView

    private val handler = Handler(Looper.getMainLooper())
    private var typewriterRunnable: Runnable? = null
    private var idlePulseAnimator: ValueAnimator? = null

    // Close callback
    private var onCloseListener: (() -> Unit)? = null

    init {
        // Full screen scrim — tap to dismiss
        scrim = View(context).apply {
            setBackgroundColor(Color.parseColor("#80000000")) // 50% black
            alpha = 0f
            setOnClickListener {
                // Tap outside (on scrim) to dismiss
                dismissOverlay()
            }
        }
        addView(scrim, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        // Bottom sheet
        sheet = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 24, 48, 48)

            val bg = GradientDrawable().apply {
                setColor(Color.parseColor("#E60D1117")) // Semi-transparent dark
                cornerRadii = floatArrayOf(60f, 60f, 60f, 60f, 0f, 0f, 0f, 0f)
            }
            background = bg
        }

        val sheetParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.BOTTOM
        }
        addView(sheet, sheetParams)

        // Top row: drag handle + close button
        val topRow = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        // Drag handle
        val handle = View(context).apply {
            val handleBg = GradientDrawable().apply {
                setColor(Color.parseColor("#40FFFFFF"))
                cornerRadius = 20f
            }
            background = handleBg
        }
        topRow.addView(handle, LinearLayout.LayoutParams(120, 12).apply {
            topMargin = 16
            bottomMargin = 16
            weight = 1f
            gravity = Gravity.CENTER
        })

        // Close button (X)
        closeButton = TextView(context).apply {
            text = "✕"
            setTextColor(Color.WHITE)
            textSize = 24f
            setPadding(16, 8, 16, 8)
            setOnClickListener {
                dismissOverlay()
            }
        }
        topRow.addView(closeButton, LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        sheet.addView(topRow, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

        // Branding
        val brand = TextView(context).apply {
            text = "okDriver"
            setTextColor(Color.parseColor("#00E5A0"))
            textSize = 14f
        }
        sheet.addView(brand, LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        // Status text
        statusText = TextView(context).apply {
            text = "Listening..."
            setTextColor(Color.WHITE)
            textSize = 20f
            alpha = 0.8f
        }
        sheet.addView(statusText, LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = 8
            bottomMargin = 48
        })

        // Animation container
        val animContainer = FrameLayout(context)
        sheet.addView(animContainer, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 300))

        waveformView = WaveformView(context)
        animContainer.addView(waveformView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        orbView = OrbAnimationView(context)
        animContainer.addView(orbView, LayoutParams(200, 200, Gravity.CENTER))

        // Texts
        transcriptText = TextView(context).apply {
            text = ""
            setTextColor(Color.WHITE)
            textSize = 18f
            maxLines = 2
            gravity = Gravity.CENTER
        }
        sheet.addView(transcriptText, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = 48
        })

        responseText = TextView(context).apply {
            text = ""
            setTextColor(Color.parseColor("#00E5A0"))
            textSize = 16f
            gravity = Gravity.CENTER
        }
        sheet.addView(responseText, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = 24
            bottomMargin = 24
        })

        // Idle prompt (shown in IDLE state)
        idlePromptText = TextView(context).apply {
            text = ""
            setTextColor(Color.parseColor("#00E5A0"))
            textSize = 14f
            gravity = Gravity.CENTER
            alpha = 0.6f
        }
        sheet.addView(idlePromptText, LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            bottomMargin = 48
        })

        // Setup swipe-down gesture detection
        setupSwipeGesture()

        // Ensure initial state
        visibility = View.GONE
    }

    private fun setupSwipeGesture() {
        sheet.setOnTouchListener(object : OnTouchListener {
            private var startY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> startY = event.rawY
                    MotionEvent.ACTION_UP -> {
                        if (event.rawY - startY > 150f) {
                            // Swiped down 150+ pixels — dismiss
                            dismissOverlay()
                        }
                    }
                }
                return false
            }
        })
    }

    fun show() {
        visibility = VISIBLE

        // Animate scrim
        scrim.animate().alpha(1f).setDuration(300).start()

        // Slide up sheet
        val slideUp = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 400
            interpolator = OvershootInterpolator(0.8f)
        }
        sheet.startAnimation(slideUp)
    }

    fun dismiss(onHidden: () -> Unit) {
        scrim.animate().alpha(0f).setDuration(200).start()

        val slideDown = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f
        ).apply {
            duration = 300
            interpolator = AccelerateInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(a: Animation?) {}
                override fun onAnimationRepeat(a: Animation?) {}
                override fun onAnimationEnd(a: Animation?) {
                    visibility = GONE
                    onHidden()
                }
            })
        }
        sheet.startAnimation(slideDown)
    }

    private fun dismissOverlay() {
        dismiss {
            onCloseListener?.invoke()
        }
    }

    fun setOnCloseListener(listener: () -> Unit) {
        onCloseListener = listener
    }

    fun setStateListening() {
        statusText.text = "Listening..."
        transcriptText.text = ""
        responseText.text = ""
        idlePromptText.text = ""
        orbView.stopAnimation()
        waveformView.visibility = VISIBLE
        cancelIdlePulse()
    }

    fun updateAmplitude(amp: Float) {
        waveformView.updateAmplitude(amp)
    }

    fun updateTranscript(text: String) {
        transcriptText.text = text
    }

    fun updateStatus(status: String) {
        statusText.text = status
    }

    fun setStateThinking() {
        statusText.text = "Thinking..."
        waveformView.visibility = INVISIBLE
        orbView.startAnimation()
        cancelIdlePulse()
    }

    fun setStateSleeping() {
        statusText.text = "Waiting for 'Ok Driver'..."
        waveformView.visibility = INVISIBLE
        orbView.stopAnimation()
        responseText.alpha = 0.4f  // Fade previous response
        idlePromptText.text = ""

        typewriterRunnable?.let { handler.removeCallbacks(it) }
        cancelIdlePulse()
    }

    fun setStateIdlePulse() {
        statusText.text = "Listening..."
        waveformView.visibility = VISIBLE
        orbView.stopAnimation()

        // Start gentle pulse animation for waveform
        startIdlePulse()

        // Keep previous response at 40% opacity
        responseText.alpha = 0.4f

        typewriterRunnable?.let { handler.removeCallbacks(it) }
    }

    fun updateIdlePrompt() {
        idlePromptText.text = "Ask me anything..."
        idlePromptText.alpha = 0.6f
    }

    fun showIdleTimeoutWarning() {
        idlePromptText.text = "Still there? Tap to continue"
        idlePromptText.alpha = 0.9f
    }

    private fun startIdlePulse() {
        cancelIdlePulse()
        idlePulseAnimator = ValueAnimator.ofFloat(0.4f, 0.8f).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animator ->
                waveformView.alpha = animator.animatedValue as Float
            }
            start()
        }
    }

    private fun cancelIdlePulse() {
        idlePulseAnimator?.cancel()
        idlePulseAnimator = null
        waveformView.alpha = 1.0f
    }

    fun setStateSpeaking(response: String) {
        statusText.text = "Speaking..."
        orbView.stopAnimation()
        waveformView.visibility = VISIBLE
        idlePromptText.text = ""
        cancelIdlePulse()

        // Typewriter effect
        responseText.text = ""
        responseText.alpha = 1.0f
        typewriterRunnable?.let { handler.removeCallbacks(it) }

        var index = 0
        typewriterRunnable = object : Runnable {
            override fun run() {
                if (index <= response.length) {
                    responseText.text = response.substring(0, index)
                    index++
                    handler.postDelayed(this, 30) // 30ms per char
                }
            }
        }
        handler.post(typewriterRunnable!!)
    }
}