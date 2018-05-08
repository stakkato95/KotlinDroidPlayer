package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.TrackProgressModule
import com.github.stakkato95.kmusic.screen.player.ui.TrackProgressFragment
import dagger.Subcomponent

@Subcomponent(modules = [TrackProgressModule::class])
interface TrackProgressComponent {

    fun inject(fragment: TrackProgressFragment)
}