package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.di.scope.TrackInfoScope
import com.github.stakkato95.kmusic.mvp.presenter.TrackInfoPresenter
import com.github.stakkato95.kmusic.mvp.presenter.TrackInfoPresenterImpl
import com.github.stakkato95.kmusic.mvp.view.TrackInfoView
import dagger.Module
import dagger.Provides

@Module
class TrackInfoModule(private val view: TrackInfoView) {

    @Provides
    @TrackInfoScope
    fun provideTrackInfoPresenter(state: TracksState): TrackInfoPresenter = TrackInfoPresenterImpl(view, state)
}