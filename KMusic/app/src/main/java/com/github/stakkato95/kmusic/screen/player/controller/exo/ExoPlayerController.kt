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

        const val PLAYER_TIMER_PERIOD_MILLIS = 1000L
    }

    private var listeners = mutableListOf<((Float) -> Unit)?>()

    private var player: ExoPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
    )

    private var currentPlayedTrackOrdinal = C.INDEX_UNSET

    private val currentMediaSource = DynamicConcatenatingMediaSource()

    private val userAgent = Util.getUserAgent(context, context.applicationInfo.name)

    init {
        val timer = Timer(PLAYER_TIMER, true)
        timer.schedule(object : TimerTask() {
            override fun run() = onTimerPeriodElapsed()
        }, 0, PLAYER_TIMER_PERIOD_MILLIS)
    }

    override fun playPause(trackOrdinal: Int) {
        if (currentPlayedTrackOrdinal == C.INDEX_UNSET || currentPlayedTrackOrdinal != trackOrdinal) {
            currentPlayedTrackOrdinal = trackOrdinal
            createMediaSource(trackOrdinal)
            player.prepare(currentMediaSource)
        }

        player.playWhenReady = !player.playWhenReady
    }

    override fun nextTrack() {
        if (player.nextWindowIndex == C.INDEX_UNSET) {
            concatToMediaSource()
            player.prepare(currentMediaSource)
            player.addListener(SimpleExoPlayerListener({ listener, _, _ ->
                player.seekToDefaultPosition(++currentPlayedTrackOrdinal)
                player.removeListener(listener)
            }))
            return
        }

        val nextWindowIndex = player.nextWindowIndex
        if (nextWindowIndex != C.INDEX_UNSET) {
            player.seekToDefaultPosition(nextWindowIndex)
            currentPlayedTrackOrdinal++
        }
    }

    override fun previousTrack() {
        //TODO similar to nextTrack
        //TODO case, when play not from the first track???

        val previousWindowIndex = player.previousWindowIndex
        if (previousWindowIndex != C.INDEX_UNSET) {
            player.seekTo(previousWindowIndex, C.TIME_UNSET)
            player.playWhenReady = true
            currentPlayedTrackOrdinal--
        }
    }

    override fun rewind(progress: Float) {
        val durationOfTrack = player.duration
        if (durationOfTrack == C.TIME_UNSET) {
            return
        }

        player.seekTo((durationOfTrack * progress).toLong())
    }

    override fun addProgressListener(listener: (Float) -> Unit) { listeners.add(listener) }

    override fun removeProgressListener() {
        //TODO
    }

    private fun createMediaSource(firstTrackOrdinal: Int) {
        val mediaSources = mutableListOf<MediaSource>()

        for (i in firstTrackOrdinal until MEDIA_SOURCES_BOUNDARY_OFFSET) {
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
        listeners.forEach { it?.invoke(player.currentPosition / player.duration.toFloat()) }
    }
}