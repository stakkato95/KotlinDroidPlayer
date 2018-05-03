package com.github.stakkato95.kmusic.screen.player.controller

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
interface PlayerController {

    fun addListener(listener: Listener)

    fun removeListener(listener: Listener)

    fun playPause(trackOrdinal: Int)

    fun nextTrack()

    fun previousTrack()

    fun rewind(progress: Float)

    interface Listener {

        fun onProgressChanged(progress: Float)

        fun onNextTrackPlaybackStarted()
    }

    class SimpleListener(private val onProgressChanged: ((Float) -> Unit)? = null,
                         private val onNextTrackPlaybackStarted: (() -> Unit)? = null) : Listener {

        override fun onProgressChanged(progress: Float) {
            onProgressChanged?.invoke(progress)
        }

        override fun onNextTrackPlaybackStarted() {
            onNextTrackPlaybackStarted?.invoke()
        }
    }
}