package com.github.stakkato95.kmusic.tracks.widget

import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.extensions.lerp
import com.github.stakkato95.kmusic.common.extensions.setBlendedTextColor
import com.github.stakkato95.kmusic.common.widget.VerticalViewPager

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
class ViewPagerCoordinator(
        val pager: VerticalViewPager,
        val text: TextView,
        val startTextSize: Float,
        val endTextSize: Float) : ScrollCoordinator(pager) {

    companion object {
        val TEXT_INITIAL_Y_COORDINATE_NOT_INITED = -1f
    }

    val textViewsToFade = arrayOf(
            R.id.artistView,
            R.id.artistLabelView,
            R.id.audioFormatView,
            R.id.audioFormatLabelView
    )

    var lastScrollPercent = 0f
    var lastScroll = 0f

    var albumTextInitialHeight = 0
    var albumTextInitialY = TEXT_INITIAL_Y_COORDINATE_NOT_INITED

    //to what distance label will move to top when ViewPager is scrolled to top
    var labelMovementPercent = 0f
    var labelMovementDistance = 0f
        get() = pager.height * labelMovementPercent

    val startColor = ResourcesCompat.getColor(pager.context.resources, R.color.colorPrimary, null)
    val endColor = ResourcesCompat.getColor(pager.context.resources, R.color.colorAccent, null)

    override fun onObservableViewScrolled() {
        if (albumTextInitialY == TEXT_INITIAL_Y_COORDINATE_NOT_INITED) {
            albumTextInitialY = text.y
            albumTextInitialHeight = text.height
        }

        val pagerScroll = pager.normalizedScrollY
        val scrollPercent = pagerScroll / pager.height
        val isMovingFromFirstToSecondPage = pagerScroll - lastScroll >= 0 && pagerScroll < pager.height
        val isMovingFromSecondToFirstPage = pagerScroll - lastScroll < 0 && pagerScroll < pager.height

        moveText(pagerScroll, scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        scaleText(scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        text.setBlendedTextColor(startColor, endColor, scrollPercent)

        fadeOtherText(isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        lastScrollPercent = scrollPercent
        lastScroll = pagerScroll
    }

    fun moveText(scroll: Float, scrollPercent: Float, movingFromFirstToSecond: Boolean, movingFromSecondToFirst: Boolean) {
        val animation = if (movingFromFirstToSecond.xor(movingFromSecondToFirst)) {
            text.animate().y(albumTextInitialY - labelMovementDistance * scrollPercent)
        } else {
            text.animate().yBy(lastScroll - scroll)
        }
        animation.setDuration(0).start()
    }

    fun scaleText(scrollPercent: Float, movingFromFirstToSecond: Boolean, movingFromSecondToFirst: Boolean) {
        if (movingFromFirstToSecond || movingFromSecondToFirst) {
            val targetSize = albumTextInitialHeight * lerp(startTextSize, endTextSize, scrollPercent)
            val targetScale = targetSize / text.height
            text.animate()
                    .scaleX(targetScale)
                    .scaleY(targetScale)
                    .setDuration(0)
                    .start()
        }
    }

    fun fadeOtherText(isMovingFromFirstToSecondPage: Boolean, isMovingFromSecondToFirstPage: Boolean) {
        var trackInfoView: ViewGroup? = null
        var albumLabel: View? = null

        for (i in 0..pager.childCount) {
            trackInfoView = pager.getChildAt(i) as ViewGroup?
            albumLabel = trackInfoView?.findViewById(R.id.albumLabelView)
            if (albumLabel != null) {
                break
            }
        }

        if (albumLabel == null) {
            return
        }

        val views = arrayListOf(albumLabel)
        textViewsToFade.forEach { id -> views.add(trackInfoView!!.findViewById(id)) }

        val targetAlpha = if (isMovingFromFirstToSecondPage && albumLabel.alpha == 1f) {
            0f
        } else if (isMovingFromSecondToFirstPage && albumLabel.alpha <= 1f) {
            1f
        } else {
            0f
        }

        views.forEach { it.animate().alpha(targetAlpha).start() }
    }
}