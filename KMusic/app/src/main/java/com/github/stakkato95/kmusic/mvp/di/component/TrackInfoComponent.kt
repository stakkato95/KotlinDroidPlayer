package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.TrackInfoModule
import com.github.stakkato95.kmusic.mvp.di.scope.TrackInfoScope
import com.github.stakkato95.kmusic.screen.trackinfo.TrackInfoFragment
import dagger.Subcomponent

@Subcomponent(modules = [TrackInfoModule::class])
@TrackInfoScope
interface TrackInfoComponent {

    fun inject(fragment: TrackInfoFragment)
}