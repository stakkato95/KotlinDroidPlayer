package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.LifecycleOwner
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.mvp.view.TrackInfoView

class TrackInfoPresenterImpl(private val view: TrackInfoView, private val state: TracksState) : TrackInfoPresenter {

    private val listener: (PlayerTrack?) -> Unit = {
        it?.let{ view.showTrackInfo(it) }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        state.addCurrentTrackObserver(listener)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        state.removeCurrentTrackObserver(listener)
    }
}