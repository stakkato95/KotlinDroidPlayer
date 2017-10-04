package com.github.stakkato95.kmusic.mvp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext() = context
}