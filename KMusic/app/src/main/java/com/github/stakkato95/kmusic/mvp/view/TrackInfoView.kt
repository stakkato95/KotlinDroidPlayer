package com.github.stakkato95.kmusic.mvp.view

import com.github.stakkato95.kmusic.mvp.repository.model.Track

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
interface TrackInfoView {

    fun showTrackInfo(track: Track)

    fun showDefaultInfo()
}