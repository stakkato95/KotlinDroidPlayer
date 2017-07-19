package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.percent.PercentRelativeLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stakkato95.kmusic.common.extensions.lerp
import com.github.stakkato95.kmusic.common.extensions.setBlendedColorText
import com.github.stakkato95.kmusic.common.view.VerticalViewPager

class MainActivity : AppCompatActivity() {

    var lastScrollPercent: Float = 0.0f
    var albumTextInitialHeight = 0
    var lastScroll = 0.0f
    var albumTextInitialY = 0.0f

    lateinit var pager: VerticalViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.root_pager) as VerticalViewPager
        pager.adapter = RootPager(supportFragmentManager)

        val albumText = findViewById(R.id.album_text) as TextView


        val startColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        val endColor = ResourcesCompat.getColor(resources, R.color.colorAccent, null)

        pager.viewTreeObserver.addOnScrollChangedListener {
            val labelMovementDistance = pager.height * 0.4f

            if (albumTextInitialY == 0.0f) {
                albumTextInitialY = albumText.y
                albumTextInitialHeight = albumText.height
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
                Log.d("PAGER", "From 2 to 1 || from 1 to 2")
                albumText.animate().y(albumTextInitialY + -labelMovementDistance * scrollPercent).setDuration(0).start()
            } else {
                Log.d("PAGER", "1->2 = $isMovingFromFirstToSecondPage 2->1 = $isMovingFromSecondToFirstPage")
                albumText.animate().yBy(lastScroll - pager.normalizedScrollY!!).setDuration(0).start()
            }

//            albumText.visibility = if (pager.normalizedScrollY!! <= pager.height) View.VISIBLE else View.GONE
            albumText.setBlendedColorText(startColor, endColor, scrollPercent)

            if (isMovingFromFirstToSecondPage || isMovingFromSecondToFirstPage) {
                val targetSize = albumTextInitialHeight * lerp(1f, 0.5f, scrollPercent)
                val scale = targetSize / albumText.height

                albumText.animate().scaleX(scale).setDuration(0).start()
                albumText.animate().scaleY(scale).setDuration(0).start()
            }

            fadeText(albumText, scrollPercent, isMovingFromFirstToSecondPage, isMovingFromSecondToFirstPage)

            lastScrollPercent = scrollPercent
            lastScroll = pager.normalizedScrollY!!
        }
    }

    fun fadeText(albumText: TextView, scrollPercent: Float, isMovingFromFirstToSecondPage: Boolean, isMovingFromSecondToFirstPage: Boolean) {
        var trackInfoView: ViewGroup? = null
        var albumLabel: View? = null

        for (i in 0..pager.childCount) {
            trackInfoView = pager.getChildAt(i) as ViewGroup?
            albumLabel = trackInfoView?.findViewById(R.id.album_label)
            if (albumLabel != null) {
                break
            }
        }

        albumLabel?.let {
            val artistText = trackInfoView?.findViewById(R.id.artist_text)
            val artistLabel = trackInfoView?.findViewById(R.id.artist_label)
            val audioFormatText = trackInfoView?.findViewById(R.id.audio_format_text)
            val audioFormatLabel = trackInfoView?.findViewById(R.id.audio_format_label)

            val fadeOutAnimationDuration = 250L
            val fadeInAnimationDuration = 100L

            if (isMovingFromFirstToSecondPage && albumLabel != null && albumLabel!!.alpha == 1f) {
                albumLabel!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                artistText!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                artistLabel!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                audioFormatText!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()
                audioFormatLabel!!.animate().alpha(0f).setDuration(fadeInAnimationDuration).start()

//            val textAlpha = 1 - Math.pow(scrollPercent.toDouble(), 1 / 3.toDouble()).toFloat()
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
