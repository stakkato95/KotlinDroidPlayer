package com.github.stakkato95.kmusic.tracks.view

import android.support.percent.PercentRelativeLayout
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.extensions.lerp
import com.github.stakkato95.kmusic.common.extensions.setBlendedColorText
import com.github.stakkato95.kmusic.common.view.VerticalViewPager

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
class ViewPagerCoordinator(var pager: VerticalViewPager, var text: TextView) : ScrollCoordinator(pager) {

    var lastScrollPercent: Float = 0.0f
    var albumTextInitialHeight = 0
    var lastScroll = 0.0f
    var albumTextInitialY = 0.0f

    val startColor = ResourcesCompat.getColor(pager.context.resources, R.color.colorPrimary, null)
    val endColor = ResourcesCompat.getColor(pager.context.resources, R.color.colorAccent, null)

    override fun onObservableViewScrolled() {
        val labelMovementDistance = observableView.height * 0.4f

        if (albumTextInitialY == 0.0f) {
            albumTextInitialY = text.y
            albumTextInitialHeight = text.height
            val playerView = pager.getChildAt(1)

            val albumTextPlayerScreen = playerView.findViewById(R.id.album_text_player_screen) as TextView

            val layoutParams = albumTextPlayerScreen.layoutParams as PercentRelativeLayout.LayoutParams
            layoutParams.topMargin = (albumTextInitialY + labelMovementDistance - pager.height).toInt()
            albumTextPlayerScreen.layoutParams = layoutParams

            albumTextPlayerScreen.scaleX = 0.5f
            albumTextPlayerScreen.scaleY = 0.5f
        }

        val scrollPercent = pager.normalizedScrollY!! / pager.height
        val isMovingFromFirstToSecondPage = pager.normalizedScrollY!! - lastScroll >= 0 && pager.normalizedScrollY!! < pager.height
        val isMovingFromSecondToFirstPage = pager.normalizedScrollY!! - lastScroll < 0 && pager.normalizedScrollY!! < pager.height

        if (isMovingFromFirstToSecondPage.xor(isMovingFromSecondToFirstPage)) {
            text.animate().y(albumTextInitialY + -labelMovementDistance * scrollPercent).setDuration(0).start()
        } else {
            text.animate().yBy(lastScroll - pager.normalizedScrollY!!).setDuration(0).start()
        }

        text.setBlendedColorText(startColor, endColor, scrollPercent)

        if (isMovingFromFirstToSecondPage || isMovingFromSecondToFirstPage) {
            val targetSize = albumTextInitialHeight * lerp(1f, 0.5f, scrollPercent)
            val scale = targetSize / text.height

            text.animate().scaleX(scale).setDuration(0).start()
            text.animate().scaleY(scale).setDuration(0).start()
        }

        fadeText(text, scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        lastScrollPercent = scrollPercent
        lastScroll = pager.normalizedScrollY!!
    }

    fun fadeText(albumText: TextView, scrollPercent: Float, isMovingFromFirstToSecondPage: Boolean, isMovingFromSecondToFirstPage: Boolean) {
        var trackInfoView: ViewGroup? = null
        var albumLabel: View? = null

        for (i in 0..pager.childCount) {
            trackInfoView = pager.getChildAt(i) as ViewGroup?
            albumLabel = trackInfoView?.findViewById(R.id.albumLabelView)
            if (albumLabel != null) {
                break
            }
        }

        albumLabel?.let {
            val artistText = trackInfoView?.findViewById(R.id.artistView)
            val artistLabel = trackInfoView?.findViewById(R.id.artistLabelView)
            val audioFormatText = trackInfoView?.findViewById(R.id.audioFormatView)
            val audioFormatLabel = trackInfoView?.findViewById(R.id.audioFormatLabelView)

            val fadeOutAnimationDuration = 250L
            val fadeInAnimationDuration = 100L

            if (isMovingFromFirstToSecondPage && albumLabel != null && albumLabel!!.alpha == 1f) {
                albumLabel!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                artistText!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                artistLabel!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                audioFormatText!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                audioFormatLabel!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
            } else if (isMovingFromSecondToFirstPage && albumLabel != null && albumLabel!!.alpha <= 1f) {
                albumLabel!!.animate().alpha(1f).setDuration(fadeOutAnimationDuration).start()
                artistText!!.animate().alpha(1f).setDuration(fadeOutAnimationDuration).start()
                artistLabel!!.animate().alpha(1f).setDuration(fadeOutAnimationDuration).start()
                audioFormatText!!.animate().alpha(1f).setDuration(fadeOutAnimationDuration).start()
                audioFormatLabel!!.animate().alpha(1f).setDuration(fadeOutAnimationDuration).start()
            }
        }
    }
}