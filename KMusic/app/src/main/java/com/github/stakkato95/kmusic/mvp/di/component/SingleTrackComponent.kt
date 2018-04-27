package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.AllTracksModule
import com.github.stakkato95.kmusic.mvp.di.module.PlayerModule
import com.github.stakkato95.kmusic.mvp.di.module.SingleTrackModule
import com.github.stakkato95.kmusic.mvp.di.scope.SingleTrackScope
import dagger.Subcomponent

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Subcomponent(modules = [SingleTrackModule::class])
@SingleTrackScope
interface SingleTrackComponent {

    fun plusAllTracksComponent(allTracksModule: AllTracksModule): AllTracksComponent

    fun plusPlayerComponent(playerModule: PlayerModule): PlayerComponent
}