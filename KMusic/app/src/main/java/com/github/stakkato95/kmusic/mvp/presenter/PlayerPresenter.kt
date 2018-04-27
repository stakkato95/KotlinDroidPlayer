package com.github.stakkato95.kmusic.mvp.presenter

import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.mvp.usecase.AllTracksUseCase
import com.github.stakkato95.kmusic.mvp.view.PlayerView

class PlayerPresenter(view: PlayerView, useCase: AllTracksUseCase, state: TracksState) : TracksPresenterImpl(view, useCase, state)