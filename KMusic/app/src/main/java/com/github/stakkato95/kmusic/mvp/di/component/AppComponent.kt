package com.github.stakkato95.kmusic.mvp.di.component

import com.github.stakkato95.kmusic.mvp.di.module.AppModule
import com.github.stakkato95.kmusic.mvp.di.module.RepositoryModule
import com.github.stakkato95.kmusic.mvp.di.scope.ApplicationScope
import dagger.Component

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Component(modules = arrayOf(AppModule::class, RepositoryModule::class))
@ApplicationScope
interface AppComponent {

    fun plusSingleTrackComponent(): SingleTrackComponent
}