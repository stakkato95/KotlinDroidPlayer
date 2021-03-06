package com.github.stakkato95.kmusic.mvp.presenter

import android.os.Handler
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.view.AllTracksView
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController

class AllTracksPresenter(view: AllTracksView,
                         useCase: AllTracksUseCase,
                         state: TracksState,
                         playerController: PlayerController,
                         mainHandler: Handler) : TracksPresenterImpl(view, useCase, state, playerController, mainHandler)