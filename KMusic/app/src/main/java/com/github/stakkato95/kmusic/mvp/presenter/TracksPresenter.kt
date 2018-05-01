package com.github.stakkato95.kmusic.mvp.presenter

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
interface TracksPresenter : BasePresenter {

    fun playPause(trackOrdinal: Int)

    fun nextTrack()

    fun previousTrack()

    fun rewind(progress: Float)
}