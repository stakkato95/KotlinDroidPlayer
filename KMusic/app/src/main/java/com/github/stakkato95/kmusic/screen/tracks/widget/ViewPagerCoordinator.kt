package com.github.stakkato95.kmusic.screen.tracks.widget

import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.screen.main.widget.VerticalViewPager
import com.github.stakkato95.kmusic.util.extensions.lerp
import com.github.stakkato95.kmusic.util.extensions.setBlendedTextColor

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
class ViewPagerCoordinator(
        pager: VerticalViewPager,
        private val text: TextView,
        private val startTextSize: Float,
        private val endTextSize: Float) : ScrollCoordinator<VerticalViewPager>(pager) {

    companion object {
        const val TEXT_INITIAL_Y_COORDINATE_NOT_INITED = -1f
    }

    private val textViewsToFade = arrayOf(
            R.id.artistView,
            R.id.artistLabelView,
            R.id.audioFormatView,
            R.id.audioFormatLabelView
    )

    private var lastScrollPercent = 0f
    var lastScroll = 0f

    var albumTextInitialHeight = 0
    var albumTextInitialY = TEXT_INITIAL_Y_COORDINATE_NOT_INITED

    //to what distance label will move to top when ViewPager is scrolled to top
    var labelMovementPercent = 0f
    var labelMovementDistance = 0f
        get() = observableView.height * labelMovementPercent

    var startColor = ResourcesCompat.getColor(pager.context.resources, R.color.colorPrimary, null)
    var endColor = ResourcesCompat.getColor(pager.context.resources, R.color.colorAccent, null)

    override fun onObservableViewScrolled() {
        if (albumTextInitialY == TEXT_INITIAL_Y_COORDINATE_NOT_INITED) {
            albumTextInitialY = text.y
            albumTextInitialHeight = text.height
        }

        val pagerScroll = observableView.normalizedScrollY
        val scrollPercent = pagerScroll / observableView.height
        val isMovingFromFirstToSecondPage = pagerScroll - lastScroll >= 0 && pagerScroll < observableView.height
        val isMovingFromSecondToFirstPage = pagerScroll - lastScroll < 0 && pagerScroll < observableView.height

        moveText(pagerScroll, scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        scaleText(scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        text.setBlendedTextColor(startColor, endColor, scrollPercent)

        fadeOtherText(isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        lastScrollPercent = scrollPercent
        lastScroll = pagerScroll
    }

    private fun moveText(scroll: Float, scrollPercent: Float, movingFromFirstToSecond: Boolean, movingFromSecondToFirst: Boolean) {
        val animation = if (movingFromFirstToSecond.xor(movingFromSecondToFirst)) {
            text.animate().y(albumTextInitialY - labelMovementDistance * scrollPercent)
        } else {
            text.animate().yBy(lastScroll - scroll)
        }
        animation.setDuration(0).start()
    }

    private fun scaleText(scrollPercent: Float, movingFromFirstToSecond: Boolean, movingFromSecondToFirst: Boolean) {
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

    private fun fadeOtherText(isMovingFromFirstToSecondPage: Boolean, isMovingFromSecondToFirstPage: Boolean) {
        var trackInfoView: ViewGroup? = null
        var albumLabel: View? = null

        for (i in 0..observableView.childCount) {
            trackInfoView = observableView.getChildAt(i) as ViewGroup?
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