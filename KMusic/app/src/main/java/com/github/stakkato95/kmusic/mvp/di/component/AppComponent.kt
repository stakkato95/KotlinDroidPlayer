package com.github.stakkato95.kmusic.mvp.di.component

import android.content.Context
import com.github.stakkato95.kmusic.mvp.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Component(modules = arrayOf(AppModule::class))
@Singleton
interface AppComponent {

    fun getContext(): Context
}