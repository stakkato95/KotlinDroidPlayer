package com.github.stakkato95.kmusic.mvp.di.module

import android.content.Context
import com.github.stakkato95.kmusic.mvp.di.scope.AllTracksScope
import com.github.stakkato95.kmusic.mvp.presenter.TracksPresenter
import com.github.stakkato95.kmusic.mvp.presenter.TracksPresenterImpl
import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCaseImpl
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Module
class AllTracksModule {

    @Provides
    @AllTracksScope
    fun provideAllTracksUseCase(repository: Repository): AllTracksUseCase = AllTracksUseCaseImpl(repository)

    @Provides
    @AllTracksScope
    fun provideAllTracksPresenter(context: Context, allTracksUseCase: AllTracksUseCase): TracksPresenter
            = TracksPresenterImpl(context, allTracksUseCase)
}