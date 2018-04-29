package com.github.stakkato95.kmusic.screen.player

import android.content.Context
import android.net.Uri
import com.github.stakkato95.kmusic.mvp.TracksState
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class ExoPlayerController(private val state: TracksState, private val context: Context) : PlayerController {

    companion object {

        const val MEDIA_SOURCES_BOUNDARY_OFFSET = 5
    }

    private var player: ExoPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
    )

    private var currentPlayedTrackOrdinal = C.INDEX_UNSET

    private val currentMediaSource = DynamicConcatenatingMediaSource()

    private val userAgent = Util.getUserAgent(context, context.applicationInfo.name)

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
            player.addListener(object : Player.EventListener {
                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                }

                override fun onSeekProcessed() {
                }

                override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
                }

                override fun onPlayerError(error: ExoPlaybackException?) {
                }

                override fun onLoadingChanged(isLoading: Boolean) {
                }

                override fun onPositionDiscontinuity(reason: Int) {
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                }

                override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    player.seekToDefaultPosition(++currentPlayedTrackOrdinal)
                    player.removeListener(this)
                }
            })
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
}