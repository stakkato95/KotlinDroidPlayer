package com.github.stakkato95.kmusic.mvp.presenter

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class TracksPresenterImpl(val context: Context, val useCaseAll: AllTracksUseCase):
        TracksPresenter, DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }
}