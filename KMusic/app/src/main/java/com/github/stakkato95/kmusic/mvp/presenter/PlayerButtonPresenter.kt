package com.github.stakkato95.kmusic.mvp.presenter

interface PlayerButtonPresenter : BasePresenter {

    fun setProgressListener(listener: (Float) -> Unit)

    fun removeProgressListener()
}