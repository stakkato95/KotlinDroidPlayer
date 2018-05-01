package com.github.stakkato95.kmusic.mvp.presenter

import com.github.stakkato95.kmusic.screen.player.controller.PlayerController

class PlayerButtonPresenterImpl(private val playerController: PlayerController) : PlayerButtonPresenter {

    override fun setProgressListener(listener: (Float) -> Unit) {
        playerController.addProgressListener(listener)
    }

    override fun removeProgressListener() {
        playerController.removeProgressListener()
    }
}