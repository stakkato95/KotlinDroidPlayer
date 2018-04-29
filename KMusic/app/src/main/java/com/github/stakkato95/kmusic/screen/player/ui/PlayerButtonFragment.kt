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
import java.io.Serializable

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerButtonFragment : Fragment() {

    companion object {

        private const val TRACK_ORDINAL_NO_VALUE = -1

        private const val PLAY_PAUSE_CALLBACK_KEY = "PLAY_PAUSE_CALLBACK_KEY"

        private const val TRACK_ORDINAL_KEY = "TRACK_ORDINAL_KEY"

        fun newInstance(callback: (Int) -> Unit, trackOrdinal: Int): PlayerButtonFragment {
            val fragment = PlayerButtonFragment()

            val args = Bundle()
            args.putSerializable(PLAY_PAUSE_CALLBACK_KEY, PlayPauseCallbackHolder(callback))
            args.putInt(TRACK_ORDINAL_KEY, trackOrdinal)
            fragment.arguments = args

            return fragment
        }
    }

    private var isPlaying = false

    private val playPause by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_play_pause, null) }

    private val pausePlay by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_play, null) }

    private var playPauseCallbackHolder: PlayPauseCallbackHolder? = null

    private var trackOrdinal: Int? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_player_button, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playPauseCallbackHolder = arguments.getSerializable(PLAY_PAUSE_CALLBACK_KEY) as PlayPauseCallbackHolder
        trackOrdinal = arguments.getInt(TRACK_ORDINAL_KEY)

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

            val ordinal: Int = trackOrdinal ?: TRACK_ORDINAL_NO_VALUE
            if (ordinal != TRACK_ORDINAL_NO_VALUE) {
                playPauseCallbackHolder?.callback?.invoke(ordinal)
            }
        }
    }
}

class PlayPauseCallbackHolder(val callback: (Int) -> Unit) : Serializable