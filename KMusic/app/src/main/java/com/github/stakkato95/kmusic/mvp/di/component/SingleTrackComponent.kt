package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.SingleTrackModule
import dagger.Subcomponent

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Subcomponent(modules = arrayOf(SingleTrackModule::class))
interface SingleTrackComponent {

    fun plusAllTracksComponent(): AllTracksComponent
}