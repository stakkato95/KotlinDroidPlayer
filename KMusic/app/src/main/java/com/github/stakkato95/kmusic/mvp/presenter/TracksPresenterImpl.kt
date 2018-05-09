package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import android.os.Handler
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.view.TracksView
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
abstract class TracksPresenterImpl(private var view: TracksView,
                                   private val useCase: AllTracksUseCase,
                                   private val state: TracksState,
                                   private val playerController: PlayerController,
                                   private val mainHandler: Handler) : TracksPresenter {

    private var listener = PlayerController.SimpleListener(onProgressChanged = {
        mainHandler.post { view.updateCurrentTrackProgress(it) }
    }, onNextTrackPlaybackStarted = {
        mainHandler.post {
            view.startNextTrackPlayback()
        }
    }, onTrackPlaybackStarted = { _, isNextTrack ->
        if (isNextTrack == null || !isNextTrack) {
            return@SimpleListener
        }
        mainHandler.post {
            view.startNextTrackPlayback()
        }
    })

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        var disposeable = useCase.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isEmpty()) {
                                view.showNoTracks()
                                return@subscribe
                            }
                            state.tracks = it
                            view.showTracks(it)
                        },
                        {
                            view.showError()
                        }
                )

        //TODO DISPOSEABLE!!!
        //TODO DISPOSEABLE!!!
        //TODO where to dispose of???
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        playerController.addListener(listener)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        playerController.removeListener(listener)
    }

    override fun playPause(trackOrdinal: Int) {
        playerController.playPause(trackOrdinal)
    }

    override fun nextTrack() {
        playerController.nextTrack()
    }

    override fun previousTrack() {
        playerController.previousTrack()
    }

    override fun rewind(progress: Float) {
        playerController.rewind(progress)
    }
}