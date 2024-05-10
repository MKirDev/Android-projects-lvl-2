package com.application.customstopwatch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.Shader
import android.icu.util.Calendar
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.application.customstopwatch.R
import kotlinx.coroutines.Job
import kotlin.math.cos
import kotlin.math.sin

class CustomClock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var heightView = 0
    private var widthView = 0

    private val clockHours = Array(12) { i -> i + 1 }

    private var padding = 0
    private val numeralSpacing = 0

    private var handTruncationView = 0
    private var hourHandTruncationView = 0

    private var radius = 0
    private lateinit var paint: Paint
    private val rect = Rect()

    private var isInit: Boolean = false

    private var gradient: RadialGradient? = null

    private var isStart = false

    private var timerSeconds: Long = 0


    private fun init() {
        Log.d("Clock", "init $this")
        val radiusCircle = (radius + padding - 10).toFloat()
        val centerX = (widthView / 2).toFloat()
        val centerY = (heightView / 2).toFloat()
        gradient = RadialGradient(
            centerX,
            centerY,
            radiusCircle,
            resources.getColor(R.color.pastel_blue, null),
            resources.getColor(R.color.pastel_blue, null).apply { alpha = 0.8f },
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isInit) {
            paint = Paint()
            heightView = height
            widthView = width
            padding = numeralSpacing + 80
            val minAttr = heightView.coerceAtMost(widthView)
            radius = minAttr / 2 - padding

            handTruncationView = minAttr / 20
            hourHandTruncationView = minAttr / 17

            init()
            isInit = true
        }

        paint.reset()
        paint.color = resources.getColor(R.color.pastel_blue, null)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.shader = gradient
        canvas?.drawCircle(
            (widthView / 2).toFloat(),
            (heightView / 2).toFloat(), (radius + padding - 10).toFloat(), paint
        )

        paint.style = Paint.Style.FILL
        paint.shader = null
        paint.color = Color.WHITE
        paint.setShadowLayer(10F, 0F, 0F, resources.getColor(R.color.light_green, null))
        canvas?.drawCircle((widthView / 2).toFloat(), (heightView / 2).toFloat(), 12F, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
        paint.setShadowLayer(10F, 0F, 0F, resources.getColor(R.color.light_green, null))
        canvas?.drawCircle(
            (widthView / 2).toFloat(),
            (heightView / 2).toFloat(), (radius + padding - 17).toFloat(), paint
        )

        val fontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24F, resources.displayMetrics)
        paint.textSize = fontSize

        paint.strokeWidth = 5F
        for (hour in clockHours) {
            val tmp = hour.toString()
            paint.getTextBounds(tmp, 0, tmp.length, rect)

            val angle = Math.PI / 6 * (hour - 3)
            val x = (widthView / 2 + cos(angle) * radius - rect.width() / 2)
            val y = (heightView / 2 + sin(angle) * radius + rect.height() / 2)

            canvas?.drawText(hour.toString(), x.toFloat(), y.toFloat(), paint)
        }

        var hours = timerSeconds / 3600
        hours = if (hours > 12) hours - 12 else hours
        val minutes = (timerSeconds % 3600) / 60
        val seconds = timerSeconds % 60

        drawHandLine(canvas, ((hours + minutes / 60.0)), isHour = true, isSecond = false)
        drawHandLine(canvas, (minutes + seconds / 60.0), isHour = false, isSecond = false)
        drawHandLine(canvas, seconds.toDouble(), isHour = false, isSecond = true)

          postInvalidateDelayed(500)
          invalidate()
    }

    private fun drawHandLine(canvas: Canvas?, moment: Double, isHour: Boolean, isSecond: Boolean) {
        val angle = if (isHour) Math.PI * moment / 6 - Math.PI / 2
            else Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            if (isHour) radius - handTruncationView - hourHandTruncationView else radius - handTruncationView
        if (isSecond) paint.color = resources.getColor(R.color.light_green, null)
        canvas?.drawLine(
            (widthView / 2).toFloat(),
            (heightView / 2).toFloat(),
            (widthView / 2 + cos(angle) * handRadius).toFloat(),
            (heightView / 2 + sin(angle) * handRadius).toFloat(),
            paint
        )
    }

    fun startTime(timeState: TimeState) {
        isStart = timeState.isPlayed
        timerSeconds = timeState.time
    }

}