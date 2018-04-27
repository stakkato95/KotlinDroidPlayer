package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.di.scope.PlayerScope
import com.github.stakkato95.kmusic.mvp.presenter.PlayerPresenter
import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCaseImpl
import com.github.stakkato95.kmusic.mvp.view.PlayerView
import com.github.stakkato95.kmusic.screen.player.PlayerController
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Module
class PlayerModule(private val playerView: PlayerView) {

    //TODO may be add to AppModule
    @Provides
    @PlayerScope
    fun provideAllTracksUseCase(repository: Repository): AllTracksUseCase = AllTracksUseCaseImpl(repository)


    @Provides
    @PlayerScope
    fun providePlayerPresenter(useCase: AllTracksUseCase, state: TracksState, playerController: PlayerController)
            = PlayerPresenter(playerView, useCase, state, playerController)
}