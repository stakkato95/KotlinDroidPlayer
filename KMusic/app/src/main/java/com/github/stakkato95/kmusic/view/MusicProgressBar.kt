package com.github.stakkato95.kmusic.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.stakkato95.kmusic.R



/**
 * Created by artsiomkaliaha on 28.06.17.
 */
class MusicProgressBar : FrameLayout {

    lateinit var paint: Paint
    lateinit var paint1: Paint

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        paint = Paint()
        paint.color = ResourcesCompat.getColor(context.resources, R.color.colorPrimaryDark, null)
        paint.strokeWidth = 48 * resources.displayMetrics.density
        paint.isAntiAlias = true

        paint1 = Paint()
        paint1.color = Color.WHITE
        paint1.strokeWidth = 8 * resources.displayMetrics.density
        paint1.isAntiAlias = true

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = ResourcesCompat.getColor(context.resources, R.color.colorPrimaryDark, null)
        canvas?.drawCircle(width / 2f, height / 2f, Math.min(width / 2, height / 2).toFloat(), paint)

        canvas?.drawArc(0f, 0f, width.toFloat(), height.toFloat(), 0f, 180f, false, paint1)
    }
}