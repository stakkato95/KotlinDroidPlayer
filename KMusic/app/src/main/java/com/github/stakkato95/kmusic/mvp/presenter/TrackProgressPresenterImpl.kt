package com.github.stakkato95.kmusic.mvp.presenter

import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.view.ProgressView
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController

class TrackProgressPresenterImpl(private val view: ProgressView,
                                 private val state: TracksState,
                                 private val playerController: PlayerController) : TrackProgressPresenter {

    override fun isPlayingCurrentTrack(trackOrdinal: Int) = playerController.isPlayingTrack(trackOrdinal)

    override fun getCoverPath(trackOrdinal: Int) = state.tracks[trackOrdinal].coverPath
}