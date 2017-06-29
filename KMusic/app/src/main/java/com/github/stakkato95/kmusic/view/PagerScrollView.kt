package com.github.stakkato95.kmusic.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ScrollView

class PagerScrollView : ScrollView {

    val minFlingLengthDp = 6

    val flingLength get() = context.resources.displayMetrics.density * minFlingLengthDp

    val flingVelocity = 300

    var currentPosition = 0

    val minItemHeight by lazy { (getChildAt(0) as ViewGroup).getChildAt(0).height }

    var gestureDetector: GestureDetector = GestureDetector(context, SimpleOnGestureListener(onFlingEvent =  this::onFling))

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val event = ev!!

        when {
            gestureDetector.onTouchEvent(event) -> return true
            event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL -> {
                currentPosition = Math.rint(scrollY / minItemHeight.toDouble()).toInt()
                smoothScrollTo(0, currentPosition * minItemHeight)
                return true
            }
        }

        return super.onTouchEvent(ev)
    }

    fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (p0 != null && p1 != null) {

            val flingDistance = p1.x - p0.x
            val isFlingToTop = flingDistance < 0

            return if (flingDistance > flingLength && flingVelocity >= p3) {
                smoothScrollTo(0, ++currentPosition * minItemHeight)
                true
            } else if (Math.abs(flingDistance) > flingLength && isFlingToTop && p3 >= flingVelocity) {
                smoothScrollTo(0, --currentPosition * minItemHeight)
                true
            } else {
                false
            }
        }

        return false
    }
}
