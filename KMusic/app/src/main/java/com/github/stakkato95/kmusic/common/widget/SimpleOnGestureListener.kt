package com.github.stakkato95.kmusic.common.widget

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Created by artsiomkaliaha on 28.06.17.
 */

class SimpleOnGestureListener(
        var onShowPressEvent: (MotionEvent) -> Unit = {},
        var onSingleTapUpEvent: (MotionEvent?) -> Boolean = { false },
        var onDownEvent: (MotionEvent?) -> Boolean = { false },
        var onFlingEvent: (MotionEvent?, MotionEvent?, Float, Float) -> Boolean = { _, _, _, _ -> false },
        var onScrollEvent: (MotionEvent?, MotionEvent?, Float, Float) -> Boolean = { _, _, _, _ -> false },
        var onLongPressEvent: (MotionEvent?) -> Unit = {}) : GestureDetector.OnGestureListener {

    override fun onShowPress(p0: MotionEvent?) {
        onShowPressEvent.invoke(p0!!)
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return onSingleTapUpEvent.invoke(p0)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return onDownEvent.invoke(p0)
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return onFlingEvent.invoke(p0, p1, p2, p3)
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return onScrollEvent.invoke(p0, p1, p2, p3)
    }

    override fun onLongPress(p0: MotionEvent?) {
        onLongPressEvent.invoke(p0)
    }
}