package com.github.stakkato95.kmusic.screen.player.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.util.extensions.blur
import com.github.stakkato95.kmusic.util.extensions.picasso
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_player_button.*

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerButtonFragment : Fragment() {

    private var isPlaying = true

    private val playPause by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_play_pause, null) }

    private val pausePlay by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_play, null) }

    companion object {
        fun newInstance(): PlayerButtonFragment {
            return PlayerButtonFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_player_button, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.picasso.load(R.drawable.test_background).into(centerImage, object : Callback {
            override fun onSuccess() {
                val bitmap = (centerImage.drawable as BitmapDrawable).bitmap.blur(this@PlayerButtonFragment.activity, 0.5f, 25 / 2f)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                roundedBitmapDrawable.isCircular = true
                roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()
                centerImage.setImageDrawable(roundedBitmapDrawable)
            }

            override fun onError() {}
        })

        vector_icon.setOnClickListener {
            vector_icon.background = if (isPlaying) playPause else pausePlay
            (vector_icon.background as AnimatedVectorDrawable).start()
            isPlaying = !isPlaying
        }
    }
}