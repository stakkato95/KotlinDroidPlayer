package com.github.stakkato95.kmusic.mvp.di.module

import com.github.stakkato95.kmusic.mvp.di.scope.PlayerScope
import com.github.stakkato95.kmusic.player.ExoPlayerController
import com.github.stakkato95.kmusic.player.PlayerController
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Module
class PlayerModule {

    @Provides
    @PlayerScope
    fun providePlayerController(): PlayerController = ExoPlayerController()
}