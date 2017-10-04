package com.github.stakkato95.kmusic.player.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.extensions.blur
import com.github.stakkato95.kmusic.common.extensions.picasso
import com.squareup.picasso.Callback

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerButtonFragment : Fragment() {

    var isPlaying = true

    val playPause by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_play_pause, null) }

    val pausePlay by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_play, null) }

    companion object {
        fun newInstance(): PlayerButtonFragment {
            return PlayerButtonFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_player_button, container, false)!!

        val image = view.findViewById<ImageView>(R.id.centerImage)

        activity.picasso.load(R.drawable.test_background).into(image, object: Callback {
            override fun onSuccess() {
                val bitmap = (image.drawable as BitmapDrawable).bitmap.blur(this@PlayerButtonFragment.activity, 0.5f, 25 / 2f)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                roundedBitmapDrawable.isCircular = true
                roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()

                image.setImageDrawable(roundedBitmapDrawable)
            }

            override fun onError() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        val vectorIcon = view.findViewById<Button>(R.id.vector_icon)

        vectorIcon.setOnClickListener {
            vectorIcon.background = if (isPlaying) playPause else pausePlay
            (vectorIcon.background as AnimatedVectorDrawable).start()
            isPlaying = !isPlaying
        }

        return view
    }
}