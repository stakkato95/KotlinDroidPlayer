package com.github.stakkato95.kmusic.mvp.view

import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
interface TracksView {

    fun showTracks(playerTracks: List<PlayerTrack>)

    fun showNoTracks()

    fun showError()

    fun updateCurrentTrackProgress(progress: Float)

    fun startNextTrackPlayback()
}