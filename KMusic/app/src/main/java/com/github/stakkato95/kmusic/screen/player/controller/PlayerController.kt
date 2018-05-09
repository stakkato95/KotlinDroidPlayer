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

    fun isPlayingTrack(trackOrdinal: Int): Boolean

    interface Listener {

        fun onProgressChanged(progress: Float)

        fun onNextTrackPlaybackStarted()

        fun onTrackPlaybackStarted(trackOrdinal: Int, isNextTrack: Boolean? = null)

        fun onTrackPlaybackPaused(trackOrdinal: Int)
    }

    class SimpleListener(private val onProgressChanged: ((Float) -> Unit)? = null,
                         private val onNextTrackPlaybackStarted: (() -> Unit)? = null,
                         private val onTrackPlaybackStarted: ((Int, Boolean?) -> Unit)? = null,
                         private val onTrackPlaybackPaused: ((Int) -> Unit)? = null) : Listener {

        override fun onProgressChanged(progress: Float) {
            onProgressChanged?.invoke(progress)
        }

        override fun onNextTrackPlaybackStarted() {
            onNextTrackPlaybackStarted?.invoke()
        }

        override fun onTrackPlaybackStarted(trackOrdinal: Int, isNextTrack: Boolean?) {
            onTrackPlaybackStarted?.invoke(trackOrdinal, isNextTrack)
        }

        override fun onTrackPlaybackPaused(trackOrdinal: Int) {
            onTrackPlaybackPaused?.invoke(trackOrdinal)
        }
    }
}