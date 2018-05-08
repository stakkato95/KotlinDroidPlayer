package com.github.stakkato95.kmusic.screen.player.ui

import android.arch.lifecycle.LifecycleObserver
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.App
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.BaseFragment
import com.github.stakkato95.kmusic.mvp.di.module.TrackProgressModule
import com.github.stakkato95.kmusic.mvp.presenter.TrackProgressPresenter
import com.github.stakkato95.kmusic.mvp.view.ProgressView
import com.github.stakkato95.kmusic.util.extensions.RoundedAndBlurredImageTransformation
import com.github.stakkato95.kmusic.util.extensions.blur
import com.github.stakkato95.kmusic.util.extensions.loadCover
import com.github.stakkato95.kmusic.util.extensions.picasso
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_player_button.*
import kotlinx.android.synthetic.main.item_track_info.*
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class TrackProgressFragment : BaseFragment(), ProgressView, TrackProgressAware {

    companion object {

        private const val TRACK_ORDINAL_NO_VALUE = -1

        private const val PLAY_PAUSE_CALLBACK_KEY = "PLAY_PAUSE_CALLBACK_KEY"

        private const val TRACK_ORDINAL_KEY = "TRACK_ORDINAL_KEY"

        fun newInstance(progressCallback: (Float) -> Unit, playPauseCallback: (Int) -> Unit, trackOrdinal: Int): TrackProgressFragment {
            val fragment = TrackProgressFragment()

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

    @Inject
    lateinit var presenter: TrackProgressPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) = inflater?.inflate(R.layout.fragment_player_button, container, false)!!

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callbacksHolder = arguments.getSerializable(PLAY_PAUSE_CALLBACK_KEY) as PlayPauseCallbackHolder
        trackOrdinal = arguments.getInt(TRACK_ORDINAL_KEY)

        trackOrdinal?.let {
            context.picasso
                    .loadCover(presenter.getCoverPath(it))
                    .placeholder(R.drawable.test_background)
                    .transform(RoundedAndBlurredImageTransformation(context))
                    .into(centerImage)
        }

        vector_icon.setOnClickListener {
            isPlaying = !isPlaying
            switchPlayPauseIcon()
            val ordinal: Int = trackOrdinal ?: TRACK_ORDINAL_NO_VALUE
            if (ordinal != TRACK_ORDINAL_NO_VALUE) {
                callbacksHolder?.playPauseCallback?.invoke(ordinal)
            }
        }
        switchPlayPauseIcon()

        musicProgressBar.setProgressPercentListener { progress -> callbacksHolder?.progressCallback?.invoke(progress) }
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.appComponent.plusTrackProgressComponent(TrackProgressModule(this)).inject(this)
        return presenter
    }

    override fun setProgress(progress: Float) {
        if (!isPlaying) {
            isPlaying = true
            switchPlayPauseIcon()
        }
        musicProgressBar.setProgress(progress)
    }

    override fun resetProgress() {
        isPlaying = false
        switchPlayPauseIcon()
        musicProgressBar.setProgress(.0f)
    }

    private fun switchPlayPauseIcon() {
        vector_icon.background = if (isPlaying) playPause else pausePlay
        (vector_icon.background as AnimatedVectorDrawable).start()
    }
}

class PlayPauseCallbackHolder(val progressCallback: (Float) -> Unit, val playPauseCallback: (Int) -> Unit) : Serializable

interface TrackProgressAware {

    fun setProgress(progress: Float)

    fun resetProgress()
}