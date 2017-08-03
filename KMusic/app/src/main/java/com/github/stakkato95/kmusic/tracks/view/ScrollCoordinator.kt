package com.github.stakkato95.kmusic.tracks.view

import android.view.View

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
abstract class ScrollCoordinator(val observableView: View) {

    init {
        observableView.viewTreeObserver.addOnScrollChangedListener { onObservableViewScrolled() }
    }

    abstract fun onObservableViewScrolled()
}