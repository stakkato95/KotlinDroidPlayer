package com.github.stakkato95.kmusic.mvp.di.module

import android.content.Context
import com.github.stakkato95.kmusic.mvp.presenter.TracksPresenter
import com.github.stakkato95.kmusic.mvp.presenter.TracksPresenterImpl
import com.github.stakkato95.kmusic.mvp.repository.Repository
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
    fun provideAllTracksUseCase(repository: Repository): TracksUseCase = TracksUseCaseImpl(repository)

    @Provides
    fun provideAllTracksPresenter(context: Context, tracksUseCase: TracksUseCase): TracksPresenter
            = TracksPresenterImpl(context, tracksUseCase)
}