package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.usecase.TracksUseCase
import com.github.stakkato95.kmusic.mvp.usecase.TracksUseCaseImpl
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Module
class AllTracksModule {

    @Provides
    fun provideAllTracksUseCase(): TracksUseCase = TracksUseCaseImpl()
}