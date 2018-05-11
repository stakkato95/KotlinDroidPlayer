package com.github.stakkato95.kmusic.screen.player.controller.exo

import android.content.Context
import android.net.Uri
import com.github.stakkato95.kmusic.mvp.TracksState
import com.github.stakkato95.kmusic.screen.player.controller.PlayerController
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.util.*

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class ExoPlayerController(private val state: TracksState, private val context: Context) : PlayerController {

    companion object {

        const val MEDIA_SOURCES_BOUNDARY_OFFSET = 5

        const val PLAYER_TIMER = "PLAYER_TIMER"

        const val PLAYER_TIMER_PERIOD_MILLIS = 50L
    }

    private var listeners = mutableListOf<PlayerController.Listener>()

    private var player: ExoPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
    )

    private var currentPlayedTrackOrdinal = C.INDEX_UNSET

    private val currentMediaSource = DynamicConcatenatingMediaSource()

    private val userAgent = Util.getUserAgent(context, context.applicationInfo.name)

    private var lastTrackDuration: Long? = null

    private var userCausedSwitchToNextTrack = false

    override val isPlaying: Boolean
        get() = player.playWhenReady

    init {
        val timer = Timer(PLAYER_TIMER, true)
        timer.schedule(object : TimerTask() {
            override fun run() = onTimerPeriodElapsed()
        }, 0, PLAYER_TIMER_PERIOD_MILLIS)

        player.addListener(SimpleExoPlayerListener(onTrackChanged = {
            //player.currentWindowIndex == currentPlayedTrackOrdinal
            //for the case, when track changed wie swipe by user
            val previousTrackOrdinal = currentPlayedTrackOrdinal
            currentPlayedTrackOrdinal = player.currentWindowIndex
            val isNextTrack = (player.currentWindowIndex != 0 || previousTrackOrdinal < currentPlayedTrackOrdinal) && !userCausedSwitchToNextTrack
            userCausedSwitchToNextTrack = false

            state.setCurrentTrack(currentPlayedTrackOrdinal)
            if (!player.playWhenReady) {
                return@SimpleExoPlayerListener
            }
            synchronized(listeners) {
                listeners.forEach { it.onTrackPlaybackStarted(currentPlayedTrackOrdinal, isNextTrack) }
            }
        }))
    }

    override fun playPause(trackOrdinal: Int) {
        if (currentPlayedTrackOrdinal == C.INDEX_UNSET || currentPlayedTrackOrdinal != trackOrdinal) {
            currentPlayedTrackOrdinal = trackOrdinal
            createMediaSource(trackOrdinal)
            player.prepare(currentMediaSource)
        }

        player.playWhenReady = !player.playWhenReady
        if (player.playWhenReady) {
            synchronized(listeners) {
                listeners.forEach { it.onTrackPlaybackStarted(trackOrdinal) }
            }
        } else {
            synchronized(listeners) {
                listeners.forEach { it.onTrackPlaybackPaused(trackOrdinal) }
            }
        }
    }

    override fun nextTrack() {
        userCausedSwitchToNextTrack = true

        if (player.nextWindowIndex == C.INDEX_UNSET) {
            concatToMediaSource()
            player.prepare(currentMediaSource)
            player.addListener(SimpleExoPlayerListener(onPlayerStateChanged = { listener, _, _ ->
                player.seekToDefaultPosition(currentPlayedTrackOrdinal + 1)
                player.removeListener(listener)
            }))
            return
        }

        val nextWindowIndex = player.nextWindowIndex
        if (nextWindowIndex != C.INDEX_UNSET) {
            player.seekToDefaultPosition(nextWindowIndex)
        }
    }

    override fun previousTrack() {
        val previousWindowIndex = player.previousWindowIndex
        if (previousWindowIndex != C.INDEX_UNSET) {
            player.seekTo(previousWindowIndex, C.TIME_UNSET)
        }
    }

    override fun rewind(progress: Float) {
        val durationOfTrack = player.duration
        if (durationOfTrack == C.TIME_UNSET) {
            return
        }

        player.seekTo((durationOfTrack * progress).toLong())
    }

    override fun isPlayingTrack(trackOrdinal: Int) = player.playWhenReady && player.currentWindowIndex == trackOrdinal

    override fun addListener(listener: PlayerController.Listener) {
        synchronized(listeners) {
            listeners.add(listener)
        }
    }

    override fun removeListener(listener: PlayerController.Listener) {
        synchronized(listeners) {
            listeners.remove(listener)
        }
    }

    private fun createMediaSource(firstTrackOrdinal: Int) {
        val mediaSources = mutableListOf<MediaSource>()

        //TODO all tracks are initialised in one go => remove unused code
        for (i in firstTrackOrdinal until state.tracks.size) {// MEDIA_SOURCES_BOUNDARY_OFFSET) {
            val dataSourceFactory = DefaultDataSourceFactory(context, userAgent, null)
            val uri = Uri.parse(state.tracks[i].path)
            mediaSources.add(ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri))
        }

        currentMediaSource.addMediaSources(mediaSources)
    }

    private fun concatToMediaSource() {
        for (i in currentPlayedTrackOrdinal + 1 until currentPlayedTrackOrdinal + MEDIA_SOURCES_BOUNDARY_OFFSET) {
            val dataSourceFactory = DefaultDataSourceFactory(context, userAgent, null)
            val uri = Uri.parse(state.tracks[i].path)
            currentMediaSource.addMediaSource(ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri))
        }
    }

    private fun onTimerPeriodElapsed() {
        if (!player.playWhenReady) {
            return
        }
        if (lastTrackDuration == null || lastTrackDuration == C.TIME_END_OF_SOURCE) {
            lastTrackDuration = player.duration
        }
        synchronized(listeners) {
            listeners.forEach { it.onProgressChanged(player.currentWindowIndex, player.currentPosition / player.duration.toFloat()) }
        }
    }
}