package com.github.stakkato95.kmusic

import android.app.Application
import com.github.stakkato95.kmusic.mvp.di.Injector

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class App: Application() {

    companion object {
        lateinit var INJECTOR: Injector
    }

    override fun onCreate() {
        super.onCreate()

        INJECTOR = Injector(applicationContext)
    }
}