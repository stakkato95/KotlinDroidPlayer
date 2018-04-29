package com.github.stakkato95.kmusic.screen.player.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import kotlin.properties.Delegates

class PagePositionViewPager : ViewPager {

    var observableCurrentItem: Int by Delegates.observable(currentItem) { _, old, new -> observer?.invoke(old, new) }

    var observer: ((Int, Int) -> Unit)? = null

    constructor(context: Context) : super(context) { setCurrentItemListener() }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { setCurrentItemListener() }

    fun setCurrentItemObserver(observer: (Int, Int) -> Unit) { this.observer = observer }

    fun removeCurrentItemObserver() { this.observer = null }

    private fun setCurrentItemListener() {
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) { observableCurrentItem = currentItem }
        })
    }
}