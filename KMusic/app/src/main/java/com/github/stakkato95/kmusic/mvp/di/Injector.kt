package com.github.stakkato95.kmusic.mvp.di

import android.content.Context
import com.github.stakkato95.kmusic.mvp.di.component.AllTracksComponent
import com.github.stakkato95.kmusic.mvp.di.component.AppComponent
import com.github.stakkato95.kmusic.mvp.di.component.DaggerAppComponent
import com.github.stakkato95.kmusic.mvp.di.component.PlayerComponent
import com.github.stakkato95.kmusic.mvp.di.component.SingleTrackComponent
import com.github.stakkato95.kmusic.mvp.di.module.AppModule

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class Injector(context: Context) {

    private var appComponent: AppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .build()

    private var singleTrackComponent: SingleTrackComponent? = null

    private var allTracksComponent: AllTracksComponent? = null

    private var playerComponent: PlayerComponent? = null

    fun plusSingleTrackComponent(): SingleTrackComponent? {
        singleTrackComponent = appComponent.plusSingleTrackComponent()
        return singleTrackComponent
    }

    fun clearSingleTracjComponent() {
        singleTrackComponent = null
    }

    fun plusAllTracksComponent(): AllTracksComponent? {
        if (singleTrackComponent == null) {
            plusSingleTrackComponent()
        }

        allTracksComponent = singleTrackComponent?.plusAllTracksComponent()
        return allTracksComponent
    }

    fun clearAllTracksComponent() {
        allTracksComponent = null
    }

    fun plusPlayerComponent(): PlayerComponent? {
        playerComponent = allTracksComponent?.plusPlayerComponent()
        return playerComponent
    }

    fun clearPlayerComponent() {
        playerComponent = null
    }
}