package com.github.stakkato95.kmusic

import android.os.Bundle
import android.os.Handler
import android.support.percent.PercentRelativeLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stakkato95.kmusic.common.extensions.lerp
import com.github.stakkato95.kmusic.common.extensions.setBlendedColorText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player_button.*

class MainActivity : AppCompatActivity() {

    var lastScrollPercent: Float = 0.0f
    var albumTextInitialHeight = 0
    var lastScroll = 0.0f
    var albumTextInitialY = 0.0f

    val startColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
    val endColor = ResourcesCompat.getColor(resources, R.color.colorAccent, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalPagerView.adapter = RootPager(supportFragmentManager)

        verticalPagerView.viewTreeObserver.addOnScrollChangedListener { onPagerScrollChanged() }

        setProgressBarTouchListener()
    }

    fun fadeText(albumText: TextView, scrollPercent: Float, isMovingFromFirstToSecondPage: Boolean, isMovingFromSecondToFirstPage: Boolean) {
        var trackInfoView: ViewGroup? = null
        var albumLabel: View? = null

        for (i in 0..verticalPagerView.childCount) {
            trackInfoView = verticalPagerView.getChildAt(i) as ViewGroup?
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

    fun setProgressBarTouchListener() {
        Handler().postDelayed({
            musicProgressBar.setProgressStateListener { isScrollInProgress ->
                verticalPagerView.canInterceptTouchEvents = !isScrollInProgress
            }
        }, 500)
    }

    fun onPagerScrollChanged() {
        val labelMovementDistance = verticalPagerView.height * 0.4f

        if (albumTextInitialY == 0.0f) {
            albumTextInitialY = albumTextVeiw.y
            albumTextInitialHeight = albumTextVeiw.height
            val playerView = verticalPagerView.getChildAt(1)

            val albumTextPlayerScreen = playerView.findViewById(R.id.album_text_player_screen) as TextView

            val layoutParams = albumTextPlayerScreen.layoutParams as PercentRelativeLayout.LayoutParams
            layoutParams.topMargin = (albumTextInitialY + labelMovementDistance - verticalPagerView.height).toInt()
            albumTextPlayerScreen.layoutParams = layoutParams

            albumTextPlayerScreen.scaleX = 0.5f
            albumTextPlayerScreen.scaleY = 0.5f
        }

        val scrollPercent = verticalPagerView.normalizedScrollY!! / verticalPagerView.height
        val isMovingFromFirstToSecondPage = verticalPagerView.normalizedScrollY!! - lastScroll >= 0 && verticalPagerView.normalizedScrollY!! < verticalPagerView.height
        val isMovingFromSecondToFirstPage = verticalPagerView.normalizedScrollY!! - lastScroll < 0 && verticalPagerView.normalizedScrollY!! < verticalPagerView.height

        if (isMovingFromFirstToSecondPage.xor(isMovingFromSecondToFirstPage)) {
            albumTextVeiw.animate().y(albumTextInitialY + -labelMovementDistance * scrollPercent).setDuration(0).start()
        } else {
            albumTextVeiw.animate().yBy(lastScroll - verticalPagerView.normalizedScrollY!!).setDuration(0).start()
        }

        albumTextVeiw.setBlendedColorText(startColor, endColor, scrollPercent)

        if (isMovingFromFirstToSecondPage || isMovingFromSecondToFirstPage) {
            val targetSize = albumTextInitialHeight * lerp(1f, 0.5f, scrollPercent)
            val scale = targetSize / albumTextVeiw.height

            albumTextVeiw.animate().scaleX(scale).setDuration(0).start()
            albumTextVeiw.animate().scaleY(scale).setDuration(0).start()
        }

        fadeText(albumTextVeiw, scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

        lastScrollPercent = scrollPercent
        lastScroll = verticalPagerView.normalizedScrollY!!
    }
}
