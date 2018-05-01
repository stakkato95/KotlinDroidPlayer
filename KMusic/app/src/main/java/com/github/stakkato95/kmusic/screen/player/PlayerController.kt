package com.github.stakkato95.kmusic.screen.player

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
interface PlayerController {

    fun playPause(trackOrdinal: Int)

    fun nextTrack()

    fun previousTrack()

    fun rewind(progress: Float)
}