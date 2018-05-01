package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.LifecycleObserver

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
interface TracksPresenter : BasePresenter, LifecycleObserver {

    fun playPause(trackOrdinal: Int)

    fun nextTrack()

    fun previousTrack()

    fun rewind(progress: Float)
}