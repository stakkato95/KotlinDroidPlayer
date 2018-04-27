package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.di.scope.AllTracksScope
import com.github.stakkato95.kmusic.mvp.presenter.AllTracksPresenter
import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCaseImpl
import com.github.stakkato95.kmusic.mvp.view.AllTracksView
import com.github.stakkato95.kmusic.screen.player.PlayerController
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Module
class AllTracksModule(private val view: AllTracksView) {

    @Provides
    @AllTracksScope
    fun provideAllTracksUseCase(repository: Repository): AllTracksUseCase = AllTracksUseCaseImpl(repository)

    @Provides
    @AllTracksScope
    fun provideAllTracksPresenter(allTracksUseCase: AllTracksUseCase, tracksState: TracksState, playerController: PlayerController)
            = AllTracksPresenter(view, allTracksUseCase, tracksState, playerController)
}