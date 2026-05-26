package com.okdriver.voiceassistant.ui.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

class WaveformView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#00E5A0") // Teal
        style = Paint.Style.FILL
    }

    private val barCount = 5
    private val barHeights = FloatArray(barCount) { 8f } // 8dp min
    private var isSpeaking = false
    private var speakingAnimAngle = 0f

    private val dp = context.resources.displayMetrics.density
    private val barWidth = 8f * dp
    private val gap = 6f * dp
    private val radius = 4f * dp
    private val minHeight = 8f * dp
    private val maxHeight = 60f * dp

    private val animators = Array(barCount) {
        ValueAnimator.ofFloat(minHeight, minHeight).apply {
            duration = 150
            interpolator = DecelerateInterpolator()
            addUpdateListener { animator ->
                if (!isSpeaking) {
                    barHeights[it] = animator.animatedValue as Float
                    invalidate()
                }
            }
        }
    }

    private val speakingAnimator = ValueAnimator.ofFloat(0f, Math.PI.toFloat() * 2f).apply {
        duration = 1000
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener {
            if (isSpeaking) {
                speakingAnimAngle = it.animatedValue as Float
                invalidate()
            }
        }
    }

    fun setSpeakingMode(speaking: Boolean) {
        isSpeaking = speaking
        paint.color = if (speaking) Color.WHITE else Color.parseColor("#00E5A0")
        if (speaking) {
            speakingAnimator.start()
        } else {
            speakingAnimator.cancel()
            reset()
        }
    }

    fun updateAmplitude(amplitude: Float) { // amplitude in dB or 0-1
        if (isSpeaking) return

        // Normalize amplitude to 0.0 - 1.0 (assuming it comes as rms dB usually -2 to 10)
        val normalized = ((amplitude.coerceIn(-2f, 10f) + 2f) / 12f).coerceIn(0f, 1f)

        for (i in 0 until barCount) {
            val randomFactor = 0.5f + Math.random().toFloat() * 0.5f
            val centerBoost = if (i == 2) 1.2f else if (i == 1 || i == 3) 0.8f else 0.5f
            val target = minHeight + (normalized * (maxHeight - minHeight) * randomFactor * centerBoost)
            val finalTarget = target.coerceIn(minHeight, maxHeight)

            animators[i].setFloatValues(barHeights[i], finalTarget)
            animators[i].start()
        }
    }

    fun reset() {
        updateAmplitude(-2f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val totalWidth = (barCount * barWidth) + ((barCount - 1) * gap)
        val startX = (width - totalWidth) / 2f
        val centerY = height / 2f

        for (i in 0 until barCount) {
            val x = startX + i * (barWidth + gap)

            val h = if (isSpeaking) {
                // Sine wave pattern
                val offset = i * (Math.PI / 2.5) // phase shift
                val sinVal = (Math.sin(speakingAnimAngle + offset) + 1f) / 2f // 0 to 1
                val centerBoost = if (i == 2) 1.0f else if (i == 1 || i == 3) 0.8f else 0.6f
                minHeight + ((maxHeight - minHeight) * sinVal.toFloat() * centerBoost)
            } else {
                barHeights[i]
            }

            val top = centerY - (h / 2f)
            val bottom = centerY + (h / 2f)

            val rect = RectF(x, top, x + barWidth, bottom)
            canvas.drawRoundRect(rect, radius, radius, paint)
        }
    }
}