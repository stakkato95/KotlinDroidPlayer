package com.github.stakkato95.kmusic.mvp.di.module

import android.content.Context
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.di.scope.ApplicationScope
import com.github.stakkato95.kmusic.mvp.repository.room.KMusicDatabase
import com.github.stakkato95.kmusic.screen.player.controller.exo.ExoPlayerController
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideAppContext() = context

    @Provides
    @ApplicationScope
    fun provideDatabase() = KMusicDatabase.initDatabase(context)

    @Provides
    @ApplicationScope
    fun provideTracksState() = TracksState()

    @Provides
    @ApplicationScope
    fun providePlayerController(state: TracksState, context: Context): PlayerController = ExoPlayerController(state, context)
}