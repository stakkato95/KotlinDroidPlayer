package com.github.stakkato95.kmusic.screen.tracks.widget

import android.view.View

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
abstract class ScrollCoordinator<T: View>(protected val observableView: T) {

    init {
        observableView.viewTreeObserver.addOnScrollChangedListener(::onObservableViewScrolled)
    }

    abstract fun onObservableViewScrolled()
}