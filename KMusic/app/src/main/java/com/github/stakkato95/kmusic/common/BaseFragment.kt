package com.github.stakkato95.kmusic.common

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presenter = injectPresenter()
        lifecycle.addObserver(presenter)
    }

    abstract fun injectPresenter() : LifecycleObserver

    protected inline fun <reified T> findFirstResponder(): T? {
        val parent = parentFragment
        while (parent != null) {
            if (parent is T) {
                return parent
            }
        }
        return activity as T
    }
}