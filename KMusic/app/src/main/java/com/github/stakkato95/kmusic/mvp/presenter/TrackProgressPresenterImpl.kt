package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.os.Handler
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.view.ProgressView
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController

class TrackProgressPresenterImpl(private val view: ProgressView,
                                 private val state: TracksState,
                                 private val playerController: PlayerController,
                                 private val handler: Handler) : TrackProgressPresenter {

    private val listener = PlayerController.SimpleListener(onTrackPlaybackStarted = { trackOrdinal, _ ->
        handler.post {
            view.changePlayBackState(trackOrdinal == view.trackOrdinal)
        }
    }, onTrackPlaybackPaused = { trackOrdinal ->
        if (trackOrdinal != view.trackOrdinal) {
            return@SimpleListener
        }
        handler.post { view.changePlayBackState(false) }
    }, onProgressChanged = { trackOrdinal, progress ->
        if (trackOrdinal != view.trackOrdinal) {
            return@SimpleListener
        }
        handler.post { view.updateProgress(progress) }
    })

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        playerController.addListener(listener)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        playerController.removeListener(listener)
    }

    override fun isPlayingCurrentTrack(trackOrdinal: Int) = playerController.isPlayingTrack(trackOrdinal)

    override fun getCoverPath(trackOrdinal: Int) = state.tracks[trackOrdinal].coverPath

    override fun playPause() {
        view.trackOrdinal?.let { playerController.playPause(it) }
    }
}