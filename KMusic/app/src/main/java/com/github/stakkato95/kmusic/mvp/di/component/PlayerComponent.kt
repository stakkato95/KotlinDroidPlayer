package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.PlayerModule
import com.github.stakkato95.kmusic.mvp.di.scope.PlayerScope
import com.github.stakkato95.kmusic.screen.player.ui.PlayerFragment
import dagger.Subcomponent

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Subcomponent(modules = [PlayerModule::class])
@PlayerScope
interface PlayerComponent {

    fun inject(fragment: PlayerFragment)
}