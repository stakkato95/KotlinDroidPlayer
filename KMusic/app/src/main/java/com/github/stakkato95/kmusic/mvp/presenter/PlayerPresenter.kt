package com.github.stakkato95.kmusic.mvp.presenter

import android.os.Handler
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.view.PlayerView
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController

class PlayerPresenter(view: PlayerView,
                      useCase: AllTracksUseCase,
                      state: TracksState,
                      playerController: PlayerController,
                      mainHandler: Handler) : TracksPresenterImpl(view, useCase, state, playerController, mainHandler)