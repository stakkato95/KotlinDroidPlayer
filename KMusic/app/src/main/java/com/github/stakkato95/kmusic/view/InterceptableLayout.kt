package com.github.stakkato95.kmusic.view

import android.content.Context
import android.support.percent.PercentRelativeLayout
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * TODO: document your custom view class.
 */
class InterceptableLayout : PercentRelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}
