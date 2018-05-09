package com.github.stakkato95.kmusic.mvp.view

interface ProgressView {

    fun changePlayBackState(isCurrentTrack: Boolean, isPlaying: Boolean)

    fun updateProgress(progress: Float)

    val trackOrdinal: Int?
}