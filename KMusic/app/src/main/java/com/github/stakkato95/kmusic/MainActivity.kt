package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.percent.PercentRelativeLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.github.stakkato95.kmusic.common.extensions.lerp
import com.github.stakkato95.kmusic.common.extensions.setBlendedColorText
import com.github.stakkato95.kmusic.common.view.VerticalViewPager

class MainActivity : AppCompatActivity() {

    var lastScrollPercent: Float = 0.0f
    var albumTextInitialHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById(R.id.root_pager) as VerticalViewPager
        pager.adapter = RootPager(supportFragmentManager)

        val albumText = findViewById(R.id.album_text) as TextView
        var albumTextInitialY = 0.0f

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
            }

            val scrollPercent = pager.normalizedScrollY!! / pager.height
            albumText.animate().y(albumTextInitialY + -labelMovementDistance * scrollPercent).setDuration(0).start()

            albumText.visibility = if (pager.normalizedScrollY!! <= pager.height) View.VISIBLE else View.GONE
            albumText.setBlendedColorText(startColor, endColor, scrollPercent)

            val targetSize = albumTextInitialHeight * lerp(1f, 0.5f, scrollPercent)
            val scale = targetSize / albumText.height

            albumText.animate().scaleX(scale).setDuration(0).start()
            albumText.animate().scaleY(scale).setDuration(0).start()

            if (pager.currentItem == 0) {
                val textAlpha = 1 - Math.pow(scrollPercent.toDouble(), 1 / 3.toDouble()).toFloat()

                val trackInfoView = pager.getChildAt(0)
                trackInfoView.findViewById(R.id.album_label).alpha = textAlpha
                trackInfoView.findViewById(R.id.artist_text).alpha = textAlpha
                trackInfoView.findViewById(R.id.artist_label).alpha = textAlpha
                trackInfoView.findViewById(R.id.audio_format_text).alpha = textAlpha
                trackInfoView.findViewById(R.id.audio_format_label).alpha = textAlpha
            }

            lastScrollPercent = scrollPercent
        }
    }
}
