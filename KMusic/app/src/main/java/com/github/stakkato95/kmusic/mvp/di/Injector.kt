package com.github.stakkato95.kmusic.mvp.di

import android.content.Context
import com.github.stakkato95.kmusic.mvp.di.component.AllTracksComponent
import com.github.stakkato95.kmusic.mvp.di.component.AppComponent
import com.github.stakkato95.kmusic.mvp.di.component.DaggerAppComponent
import com.github.stakkato95.kmusic.mvp.di.component.PlayerComponent
import com.github.stakkato95.kmusic.mvp.di.component.SingleTrackComponent
import com.github.stakkato95.kmusic.mvp.di.module.AllTracksModule
import com.github.stakkato95.kmusic.mvp.di.module.AppModule
import com.github.stakkato95.kmusic.mvp.di.module.PlayerModule
import com.github.stakkato95.kmusic.mvp.view.AllTracksView
import com.github.stakkato95.kmusic.mvp.view.PlayerView

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class Injector(context: Context) {

    public val appComponent: AppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .build()

    private var singleTrackComponent: SingleTrackComponent? = null

    private var allTracksComponent: AllTracksComponent? = null

    private var playerComponent: PlayerComponent? = null

    private var playerButtonComponent: PlayerComponent? = null

    fun plusSingleTrackComponent(): SingleTrackComponent? {
        singleTrackComponent = appComponent.plusSingleTrackComponent()
        return singleTrackComponent
    }

    fun clearSingleTrackComponent() {
        singleTrackComponent = null
    }

    fun plusAllTracksComponent(allTracksView: AllTracksView): AllTracksComponent? {
        if (singleTrackComponent == null) {
            plusSingleTrackComponent()
        }

        allTracksComponent = singleTrackComponent?.plusAllTracksComponent(AllTracksModule(allTracksView))
        return allTracksComponent
    }

    fun clearAllTracksComponent() {
        allTracksComponent = null
    }

    fun plusPlayerComponent(playerView: PlayerView): PlayerComponent? {
        if (singleTrackComponent == null) {
            plusSingleTrackComponent()
        }

        playerComponent = singleTrackComponent?.plusPlayerComponent(PlayerModule(playerView))
        return playerComponent
    }

    fun clearPlayerComponent() {
        playerComponent = null
    }
}