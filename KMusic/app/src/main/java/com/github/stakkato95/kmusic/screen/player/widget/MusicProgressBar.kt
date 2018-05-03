package com.github.stakkato95.kmusic.screen.player.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Vibrator
import android.support.percent.PercentFrameLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.util.extensions.cosAngleOfTwoVectorsStartingInZeroPoint
import com.github.stakkato95.kmusic.util.extensions.toPoint


/**
 * Created by artsiomkaliaha on 28.06.17.
 */
class MusicProgressBar : PercentFrameLayout {

    enum class TouchState(val isStarted: Boolean = false,
                          val isInProgress: Boolean = false,
                          val isFinished: Boolean = false) {
        STARTED(isStarted = true),
        IN_PROGRESS(isInProgress = true),
        FINISHED(isFinished = true)
    }

    companion object {
        const val DEFAULT_TOUCH_TIME_TO_START_SCROLLING = 500

        const val IDEAL_FPS_RATE = 90f
        const val SECOND_IN_MILLIS = 1000f

        const val DEFAULT_ANGLE_BETWEEN_VIBRATIONS = 10
        const val DEFAULT_VIBRATION_TIME = 50
        const val DEFAULT_VIBRATION_TIME_ON_FIRST_TOUCH = 100
        const val DEFAULT_ANGLE_MEASUREMENT_ERROR = 0f
    }

    //progress paints
    private lateinit var backgroundLinePaint: Paint
    private lateinit var progressPaint: Paint
    private lateinit var innerCirclePaint: Paint

    private var progressBarNormalThickness: Float = 0f
    private var progressBarTouchedThickness: Float = 0f
    private var progressBarOffsetFromViewBorder: Float = 0f

    //progress coordinates
    private var progressbarAngle = 0f
    private val progressStartAngle = -90f

    private var center = Point()
        get() = Point(width / 2, height / 2)
    private var centerX = 0f
        get() = center.x.toFloat()
    private var centerY = 0f
        get() = center.y.toFloat()

    private var halfWidth = 0f
        get() = width / 2f
    private var halfHeight = 0f
        get() = height / 2f

    //params that control touch
    private var touchTimeToStartScrolling = 0L

    private var touchState = TouchState.FINISHED
    private var progressScaleTimeElapsed = 0L
    private val progressBarUpdateStep = (SECOND_IN_MILLIS / IDEAL_FPS_RATE).toLong()

    private var angleBetweenVibration = 0
    private var angleMeasurementError = 0f
    var vibrationTime = 0L
    var vibrationTimeOnFirstTouch = 0L
    private lateinit var vibrator: Vibrator

    private var lastTouchPoint: Point? = null
    private var canUpdateProgressAngle = true

    private var progressListener: ((Boolean) -> Unit)? = null
    private var progressPercentListener: ((Float) -> Unit)? = null

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MusicProgressBar)

        initPaints(attributes)

        initBarsThicknesses(attributes)

        initVibration(attributes)

        touchTimeToStartScrolling = attributes
                .getInteger(R.styleable.MusicProgressBar_touchTimeToStartScrollingMillis, DEFAULT_TOUCH_TIME_TO_START_SCROLLING)
                .toLong()

        attributes.recycle()

        setWillNotDraw(false)
        setOnTouchListener(this::touchEvent)
    }

    private fun initPaints(attributes: TypedArray) {
        backgroundLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundLinePaint.color = attributes.getColor(R.styleable.MusicProgressBar_lineColor, Color.GRAY)

        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.color = attributes.getColor(R.styleable.MusicProgressBar_progressColor, Color.RED)
        progressPaint.strokeWidth = progressBarNormalThickness

        innerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        innerCirclePaint.color = attributes.getColor(R.styleable.MusicProgressBar_innerCircleColor, Color.WHITE)
    }

    private fun initBarsThicknesses(attributes: TypedArray) {
        val progressbarDefaultThickness = context.resources.getDimensionPixelSize(R.dimen.musicProgressBar_default_thickness)
        val progressbarDefaultTouchedThickness = context.resources.getDimensionPixelSize(R.dimen.musicProgressBar_touched_thickness)

        progressBarNormalThickness = attributes
                .getDimensionPixelSize(R.styleable.MusicProgressBar_barThickness, progressbarDefaultThickness)
                .toFloat()
        progressBarTouchedThickness = attributes
                .getDimensionPixelSize(R.styleable.MusicProgressBar_barThicknessTouched, progressbarDefaultTouchedThickness)
                .toFloat()
        progressBarOffsetFromViewBorder = progressBarTouchedThickness - progressBarNormalThickness
    }

    private fun initVibration(attributes: TypedArray) {
        angleBetweenVibration = attributes
                .getInteger(R.styleable.MusicProgressBar_angleBetweenVibrations, DEFAULT_ANGLE_BETWEEN_VIBRATIONS)

        vibrationTime = attributes
                .getInteger(R.styleable.MusicProgressBar_angleBetweenVibrations, DEFAULT_VIBRATION_TIME)
                .toLong()

        vibrationTimeOnFirstTouch = attributes
                .getInteger(R.styleable.MusicProgressBar_angleBetweenVibrations, DEFAULT_VIBRATION_TIME_ON_FIRST_TOUCH)
                .toLong()

        angleMeasurementError = attributes
                .getFloat(R.styleable.MusicProgressBar_vibrationAngleMeasurementErrorPercent, DEFAULT_ANGLE_MEASUREMENT_ERROR)

        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        with(canvas) {
            val touchTimeProgress = progressScaleTimeElapsed.toFloat() / touchTimeToStartScrolling

            val increaseOfOuterCircle = calculateIncreaseOfOuterCircle(touchTimeProgress)
            val reductionOfInnerCircle = calculateReductionOfInnerCircle(touchTimeProgress)

            drawBackgroundLine(canvas, increaseOfOuterCircle)

            drawProgressArc(canvas, increaseOfOuterCircle)

            drawInnerCircle(canvas, reductionOfInnerCircle)
        }
    }

    private fun drawBackgroundLine(canvas: Canvas, increaseOfOuterCircle: Float) {
        //let's assume that width is bigger than height
        val backgroundLineRadius = Math.min(halfWidth, halfHeight) - progressBarOffsetFromViewBorder + increaseOfOuterCircle
        canvas.drawCircle(centerX, centerY, backgroundLineRadius, backgroundLinePaint)
    }

    private fun drawProgressArc(canvas: Canvas, increaseOfOuterCircle: Float) {
        val progressbarLeftPadding = (width - height) / 2f + progressBarOffsetFromViewBorder

        val progressArcLeft = progressbarLeftPadding - increaseOfOuterCircle
        val progressArcRight = progressbarLeftPadding + increaseOfOuterCircle + height - progressBarOffsetFromViewBorder * 2
        val progressBarTop = progressBarOffsetFromViewBorder - increaseOfOuterCircle
        val progressArcBottom = height.toFloat() - progressBarOffsetFromViewBorder + increaseOfOuterCircle

        canvas.drawArc(
                progressArcLeft,
                progressBarTop,
                progressArcRight,
                progressArcBottom,
                progressStartAngle,
                progressbarAngle,
                true,
                progressPaint)
    }

    private fun drawInnerCircle(canvas: Canvas, reductionOfInnerCircle: Float) {
        val innerCircleRadius = Math.min(
                halfWidth - progressBarOffsetFromViewBorder - reductionOfInnerCircle,
                halfHeight - progressBarOffsetFromViewBorder - reductionOfInnerCircle
        )
        canvas.drawCircle(centerX, centerY, innerCircleRadius, innerCirclePaint)
    }

    private fun calculateIncreaseOfOuterCircle(touchTimeProgress: Float): Float {
        return (progressBarTouchedThickness - progressBarNormalThickness) / 2 * touchTimeProgress
    }

    private fun calculateReductionOfInnerCircle(touchTimeProgress: Float): Float {
        return progressBarNormalThickness - (progressBarNormalThickness - progressBarTouchedThickness) / 2 * touchTimeProgress
    }

    private fun touchEvent(view: View, event: MotionEvent): Boolean {
        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                touchState = TouchState.STARTED
                lastTouchPoint = event.toPoint()

                canUpdateProgressAngle = false
                progressScaleTimeElapsed = 0

                updateProgressBarThicknessAfterDelay()
                postDelayed({
                    if (touchState.isFinished) {
                        return@postDelayed
                    }

                    updateProgressAngle(lastTouchPoint)
                    vibrator.vibrate(vibrationTimeOnFirstTouch)
                    canUpdateProgressAngle = true
                    progressListener?.invoke(true)

                }, touchTimeToStartScrolling)
            }
            event.action == MotionEvent.ACTION_MOVE -> {
                lastTouchPoint = event.toPoint()

                if (canUpdateProgressAngle) {
                    updateProgressAngle(lastTouchPoint)
                    vibrate()
                }
            }
            event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL -> {
                touchState = TouchState.FINISHED
                updateProgressBarThicknessAfterDelay()
                progressListener?.invoke(false)

                progressPercentListener?.invoke(progressbarAngle / 360)
            }
        }

        return true
    }

    private fun calculateAngle(x: Int, y: Int): Float {
        val currentPoint = Point(x, y)

        val startX = 0
        val currentX = currentPoint.x - (width / 2)
        val startY = height / 2
        val currentY = (height / 2) - currentPoint.y

        val translatedStartPoint = Point(startX, startY)
        val translatedCurrentPoint = Point(currentX, currentY)
        val angleCosine = translatedStartPoint.cosAngleOfTwoVectorsStartingInZeroPoint(translatedCurrentPoint)

        return if (currentPoint.x > width / 2) {
            (Math.acos(angleCosine) * 180 / Math.PI).toFloat()
        } else {
            360 - (Math.acos(angleCosine) * 180 / Math.PI).toFloat()
        }
    }

    private fun updateProgressAngle(touchPoint: Point?) {
        touchPoint?.let {
            progressbarAngle = calculateAngle(touchPoint.x, touchPoint.y)
            invalidate()
        }
    }

    private fun updateProgressBarThicknessAfterDelay() {
        val shouldInvalidate = when {
            (touchState.isStarted || touchState.isInProgress) && progressScaleTimeElapsed < touchTimeToStartScrolling -> {
                progressScaleTimeElapsed += progressBarUpdateStep
                true
            }
            touchState.isFinished && progressScaleTimeElapsed > 0L -> {
                progressScaleTimeElapsed -= progressBarUpdateStep
                true
            }
            else -> false
        }

        if (shouldInvalidate) {
            invalidate()
            postDelayed(this::updateProgressBarThicknessAfterDelay, progressBarUpdateStep)
        }
    }

    private fun vibrate() {
        if (Math.round(progressbarAngle) % angleBetweenVibration <= angleBetweenVibration * angleMeasurementError) {
            vibrator.vibrate(vibrationTime)
        }
    }

    fun setProgressStateListener(listener: (Boolean) -> Unit) { progressListener = listener }

    fun removeProgressStateListener() { progressListener = null }

    fun setProgressPercentListener(listener: (Float) -> Unit) { progressPercentListener = listener }

    fun removeProgressPercentListener() { progressPercentListener = null }

    fun setProgress(progress: Float) {
        progressbarAngle = 360 * progress
        invalidate()
    }
}