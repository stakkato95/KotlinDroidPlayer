package com.github.stakkato95.kmusic.mvp.presenter


interface TrackProgressPresenter : BasePresenter {

    fun isPlayingCurrentTrack(trackOrdinal: Int): Boolean

    fun getCoverPath(trackOrdinal: Int): String?

    fun playPause()

    fun rewind(progress: Float)
}