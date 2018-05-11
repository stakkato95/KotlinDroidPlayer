package com.github.stakkato95.kmusic.screen.main.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class VerticalViewPager : ViewPager {

    val normalizedScrollY: Float get() = scrollX / width.toFloat() * height

    private var canInterceptTouchEvents = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        setPageTransformer(true, VerticalViewPagerTransformer())
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (!canInterceptTouchEvents) {
            return false
        }

        val isIntercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev)
        return isIntercepted
    }

    override fun onTouchEvent(ev: MotionEvent?) = super.onTouchEvent(swapXY(ev))

    fun swapXY(motionEvent: MotionEvent?): MotionEvent? {
        if (motionEvent == null) return motionEvent

        val newX = motionEvent.y / height * width
        val newY = motionEvent.x / width * height

        motionEvent.setLocation(newX, newY)

        return motionEvent
    }

    class VerticalViewPagerTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            if (position <= 1) {
                page.translationX = page.width * -position

                page.translationY = page.height * position
            } else {
                page.alpha = 0.0f
            }
        }

    }
}