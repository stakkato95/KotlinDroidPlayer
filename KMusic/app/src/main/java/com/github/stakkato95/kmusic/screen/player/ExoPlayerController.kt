package com.github.stakkato95.kmusic.screen.player

import android.content.Context
import android.net.Uri
import com.github.stakkato95.kmusic.mvp.TracksState
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

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class ExoPlayerController(private val state: TracksState, private val context: Context) : PlayerController {

    companion object {

        const val MEDIA_SOURCES_BOUNDARY_OFFSET = 5

        const val CURRENT_PLAYED_TRACK_NOT_SET = -1
    }

    private var player: ExoPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
    )

    private var currentPlayedTrackOrdinal = CURRENT_PLAYED_TRACK_NOT_SET

    private lateinit var currentMediaSource: DynamicConcatenatingMediaSource

    private val userAgent = Util.getUserAgent(context, context.applicationInfo.name)

    override fun playPause(trackOrdinal: Int) {
        if (currentPlayedTrackOrdinal == CURRENT_PLAYED_TRACK_NOT_SET || currentPlayedTrackOrdinal != trackOrdinal) {
            currentPlayedTrackOrdinal = trackOrdinal
            currentMediaSource = DynamicConcatenatingMediaSource()
            currentMediaSource.addMediaSources(createNewMediaSource(trackOrdinal))
            player.prepare(currentMediaSource)
        }

        player.playWhenReady = !player.playWhenReady
    }

    override fun nextTrack() {
        val timeLine = player.currentTimeline
        if (timeLine.isEmpty) {
            createNewMediaSource(currentPlayedTrackOrdinal)
        }

        val nextWindowIndex = player.nextWindowIndex
        if (nextWindowIndex != C.INDEX_UNSET) {
            player.seekTo(nextWindowIndex, C.TIME_UNSET)
            player.playWhenReady = true
        }
    }

    override fun previousTrack() {
        //TODO similar to nextTrack
        val timeLine = player.currentTimeline
        if (timeLine.isEmpty) {
            createNewMediaSource(currentPlayedTrackOrdinal)
        }

        val previousWindowIndex = player.previousWindowIndex
        if (previousWindowIndex != C.INDEX_UNSET) {
            player.seekTo(previousWindowIndex, C.TIME_UNSET)
            player.playWhenReady = true
        }
    }

    private fun createNewMediaSource(firstTrackOrdinal: Int): MutableList<MediaSource> {
        val mediaSources = mutableListOf<MediaSource>()

        val bottomBoundary = Math.max(firstTrackOrdinal - MEDIA_SOURCES_BOUNDARY_OFFSET, 0)
        val topBoundary = Math.min(firstTrackOrdinal + MEDIA_SOURCES_BOUNDARY_OFFSET, state.tracks.size)

        for (i in bottomBoundary..topBoundary) {
            val dataSourceFactory = DefaultDataSourceFactory(context, userAgent, null)
            val uri = Uri.parse(state.tracks[i].path)
            mediaSources.add(ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri))
        }

        return mediaSources
    }
}