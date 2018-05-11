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

    private var isViewResumed = true

    private val listener = PlayerController.SimpleListener(onTrackPlaybackStarted = { trackOrdinal, _ ->
        handler.post {
            if (isViewResumed) {
                view.changePlayBackState(trackOrdinal == view.trackOrdinal, true)
            }
        }
    }, onTrackPlaybackPaused = { trackOrdinal ->
        if (trackOrdinal == view.trackOrdinal) {
            handler.post {
                if (isViewResumed) {
                    view.changePlayBackState(trackOrdinal == view.trackOrdinal, false)
                }
            }
        }
    }, onProgressChanged = { trackOrdinal, progress ->
        if (trackOrdinal == view.trackOrdinal) {
            handler.post {
                if (isViewResumed) {
                    view.updateProgress(progress)
                }
            }
        }
    })

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        isViewResumed = true
        playerController.addListener(listener)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        isViewResumed = false
        playerController.removeListener(listener)
    }

    override fun isPlayingCurrentTrack(trackOrdinal: Int) = playerController.isPlayingTrack(trackOrdinal)

    override fun getCoverPath(trackOrdinal: Int) = state.tracks[trackOrdinal].coverPath

    override fun playPause() {
        view.trackOrdinal?.let { playerController.playPause(it) }
    }

    override fun rewind(progress: Float) {
        playerController.rewind(progress)
    }
}