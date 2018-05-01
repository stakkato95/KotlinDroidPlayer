package com.github.stakkato95.kmusic.screen.player.controller

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
interface PlayerController {

    fun setProgressListener(listener: (Float) -> Unit)

    fun removeProgressListener()

    fun playPause(trackOrdinal: Int)

    fun nextTrack()

    fun previousTrack()

    fun rewind(progress: Float)
}