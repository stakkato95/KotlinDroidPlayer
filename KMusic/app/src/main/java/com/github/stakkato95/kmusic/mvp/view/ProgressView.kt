package com.github.stakkato95.kmusic.mvp.view

interface ProgressView {

    fun changePlayBackState(isPlaying: Boolean)

    val trackOrdinal: Int?
}