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
class TrackProgressAwareFragment : Fragment(), TrackProgressAware {

    companion object {

        private const val TRACK_ORDINAL_NO_VALUE = -1

        private const val PLAY_PAUSE_CALLBACK_KEY = "PLAY_PAUSE_CALLBACK_KEY"

        private const val TRACK_ORDINAL_KEY = "TRACK_ORDINAL_KEY"

        fun newInstance(progressCallback: (Float) -> Unit, playPauseCallback: (Int) -> Unit, trackOrdinal: Int): TrackProgressAwareFragment {
            val fragment = TrackProgressAwareFragment()

            val args = Bundle()
            args.putSerializable(PLAY_PAUSE_CALLBACK_KEY, PlayPauseCallbackHolder(progressCallback, playPauseCallback))
            args.putInt(TRACK_ORDINAL_KEY, trackOrdinal)
            fragment.arguments = args

            return fragment
        }
    }

    private var isPlaying = false

    private val playPause by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_play_pause, null) }

    private val pausePlay by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_play, null) }

    private var callbacksHolder: PlayPauseCallbackHolder? = null

    private var trackOrdinal: Int? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_player_button, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callbacksHolder = arguments.getSerializable(PLAY_PAUSE_CALLBACK_KEY) as PlayPauseCallbackHolder
        trackOrdinal = arguments.getInt(TRACK_ORDINAL_KEY)

        activity.picasso.load(R.drawable.test_background).into(centerImage, object : Callback {
            override fun onSuccess() {
                val bitmap = (centerImage.drawable as BitmapDrawable).bitmap.blur(this@TrackProgressAwareFragment.activity, 0.5f, 25 / 2f)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                roundedBitmapDrawable.isCircular = true
                roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()
                centerImage.setImageDrawable(roundedBitmapDrawable)
            }

            override fun onError() {}
        })

        vector_icon.setOnClickListener {
            switchPlayPauseIcon()
            val ordinal: Int = trackOrdinal ?: TRACK_ORDINAL_NO_VALUE
            if (ordinal != TRACK_ORDINAL_NO_VALUE) {
                callbacksHolder?.playPauseCallback?.invoke(ordinal)
            }
        }
        switchPlayPauseIcon()

        musicProgressBar.setProgressPercentListener { progress -> callbacksHolder?.progressCallback?.invoke(progress) }
    }

    override fun setProgress(progress: Float) { musicProgressBar.setProgress(progress) }

    private fun switchPlayPauseIcon() {
        vector_icon.background = if (isPlaying) playPause else pausePlay
        (vector_icon.background as AnimatedVectorDrawable).start()
        isPlaying = !isPlaying
    }
}

class PlayPauseCallbackHolder(val progressCallback: (Float) -> Unit, val playPauseCallback: (Int) -> Unit) : Serializable

interface TrackProgressAware {

    fun setProgress(progress: Float)
}