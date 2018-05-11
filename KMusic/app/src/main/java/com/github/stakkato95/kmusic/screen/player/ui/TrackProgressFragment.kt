package com.github.stakkato95.kmusic.screen.player.ui

import android.arch.lifecycle.LifecycleObserver
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.App
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.BaseFragment
import com.github.stakkato95.kmusic.mvp.di.module.TrackProgressModule
import com.github.stakkato95.kmusic.mvp.presenter.TrackProgressPresenter
import com.github.stakkato95.kmusic.mvp.view.ProgressView
import com.github.stakkato95.kmusic.util.extensions.blur
import com.github.stakkato95.kmusic.util.extensions.loadCover
import com.github.stakkato95.kmusic.util.extensions.picasso
import com.github.stakkato95.kmusic.util.picasso.BlurTransformation
import com.github.stakkato95.kmusic.util.picasso.RoundTransformation
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_player_button.*
import javax.inject.Inject

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class TrackProgressFragment : BaseFragment(), ProgressView {

    companion object {

        private const val TRACK_ORDINAL_KEY = "TRACK_ORDINAL_KEY"

        fun newInstance(trackOrdinal: Int): TrackProgressFragment {
            val fragment = TrackProgressFragment()

            val args = Bundle()
            args.putInt(TRACK_ORDINAL_KEY, trackOrdinal)
            fragment.arguments = args

            return fragment
        }
    }

    private val playPause by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_play_pause, null) }

    private val pausePlay by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_play, null) }

    override var trackOrdinal: Int? = null
        private set

    @Inject
    lateinit var presenter: TrackProgressPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_player_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackOrdinal = arguments?.getInt(TRACK_ORDINAL_KEY)

        trackOrdinal?.let {
            if (context == null) {
                return@let
            }
            context!!.picasso
                    .loadCover(presenter.getCoverPath(it))
                    .transform(BlurTransformation(context!!))
                    .transform(RoundTransformation())
                    .error(R.drawable.test_background)
                    .into(centerImage, object : Callback {
                        override fun onSuccess() {}

                        override fun onError() {
                            if (context == null) {
                                return
                            }
                            val bitmap = (centerImage.drawable as BitmapDrawable).bitmap.blur(context!!)
                            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                            roundedBitmapDrawable.isCircular = true
                            roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()
                            centerImage.setImageDrawable(roundedBitmapDrawable)
                        }
                    })
        }

        vector_icon.setOnClickListener { presenter.playPause() }

        musicProgressBar.setProgressPercentListener { progress -> presenter.rewind(progress) }
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.appComponent.plusTrackProgressComponent(TrackProgressModule(this)).inject(this)
        return presenter
    }

    override fun updateProgress(progress: Float) {
        if (musicProgressBar == null) {
            Log.d("", "")
        }

        musicProgressBar.setProgress(progress)
    }

    override fun changePlayBackState(isCurrentTrack: Boolean, isPlaying: Boolean) {
        if (musicProgressBar == null || vector_icon == null) {
            Log.d("", "")
        }

        if (!isCurrentTrack) {
            musicProgressBar.setProgress(.0f)
        }

        vector_icon.background = if (isPlaying && isCurrentTrack) playPause else pausePlay
        (vector_icon.background as AnimatedVectorDrawable).start()
    }
}