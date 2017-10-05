package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.usecase.SingleTrackUseCase
import com.github.stakkato95.kmusic.mvp.usecase.SingleTrackUseCaseImpl
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Module
class SingleTrackModule {

    @Provides
    fun provideSingleTrackUsecase(): SingleTrackUseCase = SingleTrackUseCaseImpl()
}