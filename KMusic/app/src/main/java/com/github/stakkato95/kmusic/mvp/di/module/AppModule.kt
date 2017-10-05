package com.github.stakkato95.kmusic.mvp.di.module

import android.content.Context
import com.github.stakkato95.kmusic.mvp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Module
class AppModule(val context: Context) {

    @Provides
    @ApplicationScope
    fun provideAppContext() = context
}