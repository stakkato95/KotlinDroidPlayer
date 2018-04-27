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

    private var player: ExoPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
    )
    private var currentPlayedTrackOrdinal: Int? = null
    private lateinit var currentMediaSource: DynamicConcatenatingMediaSource

    private val userAgent = Util.getUserAgent(context, context.applicationInfo.nonLocalizedLabel.toString())

    override fun playPause(trackOrdinal: Int) {
        if (currentPlayedTrackOrdinal == null || currentPlayedTrackOrdinal != trackOrdinal) {
            currentMediaSource = DynamicConcatenatingMediaSource()
            currentMediaSource.addMediaSources(createNewMediaSource(trackOrdinal))
            player.prepare(currentMediaSource)
            player.playWhenReady = true
        } else {
            player.playWhenReady = false
        }
    }

    override fun nextTrack() {
        val timeLine = player.currentTimeline
        if (timeLine.isEmpty) {
            return
        }

        val nextWindowIndex = player.nextWindowIndex
        if (nextWindowIndex != C.INDEX_UNSET) {
            player.seekTo(nextWindowIndex, C.TIME_UNSET)
        }
    }

    override fun previousTrack() {
        //TODO similar to nextTrack
        val timeLine = player.currentTimeline
        if (timeLine.isEmpty) {
            return
        }

        val previousWindowIndex = player.previousWindowIndex
        if (previousWindowIndex != C.INDEX_UNSET) {
            player.seekTo(previousWindowIndex, C.TIME_UNSET)
        }
    }

    private fun createNewMediaSource(firstTrackOrdinal: Int): MutableList<MediaSource> {
        val mediaSources = mutableListOf<MediaSource>()

        for (i in firstTrackOrdinal..firstTrackOrdinal + 2) {
            val dataSourceFactory = DefaultDataSourceFactory(context, userAgent, null)
            val uri = Uri.parse(state.tracks[i].path)
            mediaSources.add(ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri))
        }

        return mediaSources
    }
}