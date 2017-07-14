package com.github.stakkato95.kmusic.player

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.extensions.blur

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class PlayerPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.activity_main, container, false)


        val pager = view?.findViewById(R.id.pager) as ViewPager
        with(pager) {
            adapter = PlayerPagerAdapter(fragmentManager)
            val leftRightPadding = resources.displayMetrics.widthPixels / 6
            setPadding(leftRightPadding, 0, leftRightPadding, 0)
        }


        val cover = view.findViewById(R.id.cover_image) as ImageView
        val blurredImage = (cover.drawable as BitmapDrawable).bitmap.blur(this@PlayerPageFragment.activity, 0.5f, 12.5f)
        cover.setImageBitmap(blurredImage)


        val albumText = view.findViewById(R.id.album_text)
        val scroll = view.findViewById(R.id.scroll) as ScrollView
        val scrollContainer = view.findViewById(R.id.container)
        var albumTextInitialY = 0.0f

        scroll.viewTreeObserver.addOnScrollChangedListener {
            if (albumTextInitialY == 0.0f) {
                albumTextInitialY = albumText.y
            }

            val oneScreen = scrollContainer.height / 2
            val labelMovementDistance = oneScreen * 0.6f

            val scrollPercent = if (scroll.scrollY > 0) scroll.scrollY / oneScreen.toFloat() else 0.0f
            albumText.animate().y(albumTextInitialY + labelMovementDistance * scrollPercent).setDuration(0).start()
        }

        return view
    }
}