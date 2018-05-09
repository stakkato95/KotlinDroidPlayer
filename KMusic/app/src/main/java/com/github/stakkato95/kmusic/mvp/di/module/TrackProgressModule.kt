package com.github.stakkato95.kmusic.mvp.di.module

import android.os.Handler
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.presenter.TrackProgressPresenter
import com.github.stakkato95.kmusic.mvp.presenter.TrackProgressPresenterImpl
import com.github.stakkato95.kmusic.mvp.view.ProgressView
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController
import dagger.Module
import dagger.Provides

@Module
class TrackProgressModule(private val view: ProgressView) {

    @Provides
    fun provideTrackProgressPresenter(state: TracksState, playerController: PlayerController, handler: Handler): TrackProgressPresenter
            = TrackProgressPresenterImpl(view, state, playerController, handler)
}