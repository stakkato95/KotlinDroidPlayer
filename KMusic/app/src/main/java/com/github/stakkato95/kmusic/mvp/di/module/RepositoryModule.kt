package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository() = RepositoryImpl()
}