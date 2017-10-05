package com.github.stakkato95.kmusic.mvp.repository

import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
interface Repository {

    fun getAllTracks(): Observable<List<Track>>

    fun getCurrentTrack(): Observable<Track>
}