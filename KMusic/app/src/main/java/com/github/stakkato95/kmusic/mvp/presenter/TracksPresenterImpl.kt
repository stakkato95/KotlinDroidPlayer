package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.view.TracksView
import com.github.stakkato95.kmusic.screen.player.PlayerController
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
abstract class TracksPresenterImpl(private var view: TracksView,
                                   private val useCase: AllTracksUseCase,
                                   private val state: TracksState,
                                   private val playerController: PlayerController) : TracksPresenter, DefaultLifecycleObserver {

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

    override fun playPause(trackOrdinal: Int) {
        playerController.playPause(trackOrdinal)
    }

    override fun nextTrack() {
        playerController.nextTrack()
    }

    override fun previousTrack() {
        playerController.previousTrack()
    }
}