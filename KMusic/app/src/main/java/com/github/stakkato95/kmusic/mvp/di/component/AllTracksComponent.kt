package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.AllTracksModule
import com.github.stakkato95.kmusic.tracks.ui.TracksFragment
import dagger.Subcomponent

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
@Subcomponent(modules = arrayOf(AllTracksModule::class))
interface AllTracksComponent {

    fun inject(fragment: TracksFragment)

    fun plusPlayerComponent(): PlayerComponent
}