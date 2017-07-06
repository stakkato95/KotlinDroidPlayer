package com.github.stakkato95.kmusic.player

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.support.percent.PercentFrameLayout
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.extensions.cosAngleOfTwoVectorsStartingInZeroPoint


/**
 * Created by artsiomkaliaha on 28.06.17.
 */
class MusicProgressBar : PercentFrameLayout {

    lateinit var progressBackgroundPaint: Paint
    lateinit var progressbarPaint: Paint
    lateinit var innerCirclePaint: Paint

    var center = Point(0, 0)
        get() = Point(width / 2, height / 2)

    var progressbarAngle = 0f

    var progressStartPoint = Point(0, 0)
        get() = Point(width / 2, 0)

    val progressStartAngle = -90f

    constructor(context: Context?) : super(context) { init() }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    fun init() {
        progressBackgroundPaint = Paint()
        progressBackgroundPaint.color = ResourcesCompat.getColor(context.resources, R.color.grey, null)
        progressBackgroundPaint.strokeWidth = 48 * resources.displayMetrics.density
        progressBackgroundPaint.isAntiAlias = true

        progressbarPaint = Paint()
        progressbarPaint.color = ResourcesCompat.getColor(context.resources, R.color.colorPrimary, null)
        progressbarPaint.strokeWidth = 8 * resources.displayMetrics.density
        progressbarPaint.isAntiAlias = true

        innerCirclePaint = Paint()
        innerCirclePaint.color = Color.WHITE
        progressbarPaint.isAntiAlias = true

        setWillNotDraw(false)
        setOnTouchListener(this::touchEvent)
    }

//    override fun onInterceptTouchEvent(ev: MotionEvent?) = true

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            with(canvas) {
                drawCircle(width / 2f, height / 2f, Math.min(width / 2, height / 2).toFloat(), progressBackgroundPaint)
                drawArc(0f, 0f, width.toFloat(), height.toFloat(), progressStartAngle, progressbarAngle, true, progressbarPaint)
                drawCircle(width / 2f, height / 2f, Math.min((width / 2.1).toInt(), (height / 2.1).toInt()).toFloat(), innerCirclePaint)
            }
        }

    }

    fun touchEvent(view: View, event: MotionEvent?): Boolean {
        val ev = event!!
        progressbarAngle = calculateAngle(ev.x.toInt(), ev.y.toInt())
        invalidate()
        return true
    }

    fun calculateAngle(x: Int, y: Int): Float {
        val currentPoint = Point(x, y)

        val startX = progressStartPoint.x - width / 2
        val currentX = currentPoint.x - (width / 2)
        val startY = progressStartPoint.y + height / 2
        val currentY = (height / 2) - currentPoint.y

        val translatedStartPoint = Point(startX, startY)
        val translatedCurrentPoint = Point(currentX, currentY)
        val angleCosine = translatedStartPoint.cosAngleOfTwoVectorsStartingInZeroPoint(translatedCurrentPoint)

        val fl = if (currentPoint.x > width / 2) {
            (Math.acos(angleCosine) * 180 / Math.PI ).toFloat()
        } else {
            360 - (Math.acos(angleCosine) * 180 / Math.PI ).toFloat()
        }
        return fl
    }
}