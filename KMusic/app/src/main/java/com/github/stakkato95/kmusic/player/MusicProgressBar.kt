package com.github.stakkato95.kmusic.player

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.support.percent.PercentFrameLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.extensions.cosAngleOfTwoVectorsStartingInZeroPoint


/**
 * Created by artsiomkaliaha on 28.06.17.
 */
class MusicProgressBar : PercentFrameLayout {

    val DEFAULT_TOUCH_TIME_TO_START_SCROLLING = 500

    //progress paints
    lateinit var backgroundLinePaint: Paint
    lateinit var progressPaint: Paint
    lateinit var innerCirclePaint: Paint

    var progressBarNormalThickness: Float = 0f
    var progressBarTouchedThickness: Float = 0f
    var progressBarOffsetFromViewBorder: Float = 0f

    //progress coordinates
    var progressbarAngle = 0f

    var progressStartPoint = Point(0, 0)
        get() = Point(width / 2, 0)

    val progressStartAngle = -90f

    //params that control touch
    var touchTimeToStartScrolling = 0L
    var touchTimeElapsed = 0L
    var lastTouchTime = 0L

    constructor(context: Context?) : super(context) { init(null) }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init(attrs) }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init(attrs) }

    fun init(attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MusicProgressBar)

        try {
            backgroundLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            backgroundLinePaint.color = attributes.getColor(R.styleable.MusicProgressBar_lineColor, Color.GRAY)
            backgroundLinePaint.strokeWidth = attributes.getDimensionPixelSize(
                    R.styleable.MusicProgressBar_barThickness,
                    context.resources.getDimensionPixelSize(R.dimen.musicProgressBar_default_thickness)
            ).toFloat()

            progressBarNormalThickness = attributes.getDimensionPixelSize(
                    R.styleable.MusicProgressBar_barThickness,
                    context.resources.getDimensionPixelSize(R.dimen.musicProgressBar_default_thickness)
            ).toFloat()
            progressBarTouchedThickness= attributes.getDimensionPixelSize(
                    R.styleable.MusicProgressBar_barThicknessTouched,
                    context.resources.getDimensionPixelSize(R.dimen.musicProgressBar_touched_thickness)
            ).toFloat()
            progressBarOffsetFromViewBorder = progressBarTouchedThickness - progressBarNormalThickness
            progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            progressPaint.color = attributes.getColor(R.styleable.MusicProgressBar_progressColor, Color.RED)
            progressPaint.strokeWidth = progressBarNormalThickness

            innerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            innerCirclePaint.color = attributes.getColor(R.styleable.MusicProgressBar_innerCircleColor, Color.WHITE)

            touchTimeToStartScrolling = attributes.getInteger(R.styleable.MusicProgressBar_touchTimeToStartScrollingMillis, DEFAULT_TOUCH_TIME_TO_START_SCROLLING).toLong()
        } finally {
            attributes.recycle()
        }

        setWillNotDraw(false)
        setOnTouchListener(this::touchEvent)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            with(canvas) {
                //let's assume that width is bigger than height
                val startPadding = ((width - height) / 2).toFloat() + progressBarOffsetFromViewBorder
                val circleCenterX = width / 2f
                val circleCenterY = height / 2f

                val backgroundLineRadius = Math.min(width / 2, height / 2).toFloat() - progressBarOffsetFromViewBorder
                drawCircle(circleCenterX, circleCenterY, backgroundLineRadius, backgroundLinePaint)

                val progressArcRight = startPadding + height - progressBarOffsetFromViewBorder * 2
                val progressArcBottom = height.toFloat() - progressBarOffsetFromViewBorder
                drawArc(startPadding, progressBarOffsetFromViewBorder, progressArcRight, progressArcBottom, progressStartAngle, progressbarAngle, true, progressPaint)

                val innerCircleRadius = Math.min(
                        ((width / 2.0) - progressBarOffsetFromViewBorder - progressBarNormalThickness),
                        ((height / 2.0) - progressBarOffsetFromViewBorder - progressBarNormalThickness)
                ).toFloat()
                drawCircle(circleCenterX, circleCenterY, innerCircleRadius, innerCirclePaint)
            }
        }
    }

    fun touchEvent(view: View, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            lastTouchTime = System.currentTimeMillis()
            touchTimeElapsed = 0
        } else {
            touchTimeElapsed += System.currentTimeMillis() - lastTouchTime
        }

        if (touchTimeElapsed >= touchTimeToStartScrolling) {
            val ev = event!!
            progressbarAngle = calculateAngle(ev.x.toInt(), ev.y.toInt())
            invalidate()
        }

        lastTouchTime = System.currentTimeMillis()
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