package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.view.TracksView
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class TracksPresenterImpl(private var view: TracksView,
                          private val useCase: AllTracksUseCase,
                          private val state: TracksState) : TracksPresenter, DefaultLifecycleObserver {

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
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }
}