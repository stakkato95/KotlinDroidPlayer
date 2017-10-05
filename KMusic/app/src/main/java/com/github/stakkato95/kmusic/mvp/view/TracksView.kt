package com.github.stakkato95.kmusic.mvp.view

import com.github.stakkato95.kmusic.mvp.repository.model.Track

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
interface TracksView {

    fun showTracks(tracks: List<Track>)

    fun showNoTracks()
}