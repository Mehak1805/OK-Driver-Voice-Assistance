package com.okdriver.voiceassistant

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class OrbAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animValue = 0f
    private var colorAngle = 0f
    
    private val orbAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1500
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { 
            animValue = it.animatedValue as Float
            invalidate()
        }
    }
    
    private val colorAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
        duration = 3000
        repeatCount = ValueAnimator.INFINITE
        interpolator = android.view.animation.LinearInterpolator()
        addUpdateListener {
            colorAngle = it.animatedValue as Float
            invalidate()
        }
    }

    fun startAnimation() { 
        orbAnimator.start()
        colorAnimator.start()
        animate().alpha(1f).setDuration(300).start()
    }

    fun stopAnimation() { 
        orbAnimator.cancel()
        colorAnimator.cancel()
        animate().alpha(0f).setDuration(200).start()
    }
    
    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val baseRadius = Math.min(width, height) / 4f
        val pulseRadius = baseRadius + (animValue * baseRadius * 0.4f)
        
        // Outer glow rings (3 rings, decreasing opacity)
        for (i in 3 downTo 1) {
            val ringRadius = pulseRadius + (i * 15f)
            val alpha = (60 - i * 15).coerceAtLeast(0)
            paint.color = Color.argb(alpha, 0, 229, 160)  // teal glow
            canvas.drawCircle(cx, cy, ringRadius, paint)
        }
        
        // Main orb with gradient color cycling
        val hue1 = colorAngle % 360
        val hue2 = (colorAngle + 120) % 360
        val hue3 = (colorAngle + 240) % 360
        
        val gradient = SweepGradient(cx, cy,
            intArrayOf(
                Color.HSVToColor(floatArrayOf(hue1, 0.8f, 1f)),  // teal
                Color.HSVToColor(floatArrayOf(hue2, 0.8f, 1f)),  // blue/purple
                Color.HSVToColor(floatArrayOf(hue3, 0.8f, 1f)),  // back to teal
                Color.HSVToColor(floatArrayOf(hue1, 0.8f, 1f))
            ), null
        )
        paint.shader = gradient
        canvas.drawCircle(cx, cy, pulseRadius, paint)
        paint.shader = null
        
        // Inner bright core
        paint.color = Color.argb(200, 255, 255, 255)
        canvas.drawCircle(cx, cy, pulseRadius * 0.3f, paint)
    }
}
