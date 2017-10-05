package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.di.scope.ApplicationScope
import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideRepository(): Repository = RepositoryImpl()
}