package com.github.stakkato95.kmusic.screen.trackinfo

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.util.extensions.blur

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class TrackInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_track_info, container, false)

        val cover = view.findViewById<ImageView>(R.id.coverImageView)
        val blurredImage = (cover.drawable as BitmapDrawable)
                .bitmap
                .blur(this@TrackInfoFragment.activity, 0.5f, 12.5f)
        cover.setImageBitmap(blurredImage)

        return view
    }
}