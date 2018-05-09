package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.AppModule
import com.github.stakkato95.kmusic.mvp.di.module.RepositoryModule
import com.github.stakkato95.kmusic.mvp.di.module.TrackInfoModule
import com.github.stakkato95.kmusic.mvp.di.module.TrackProgressModule
import com.github.stakkato95.kmusic.mvp.di.scope.ApplicationScope
import dagger.Component

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Component(modules = [AppModule::class, RepositoryModule::class])
@ApplicationScope
interface AppComponent {

    fun plusSingleTrackComponent(): SingleTrackComponent

    fun plusTrackProgressComponent(module: TrackProgressModule): TrackProgressComponent

    fun plusTrackInfoComponent(module: TrackInfoModule): TrackInfoComponent
}