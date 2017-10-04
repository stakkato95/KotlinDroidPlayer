package com.github.stakkato95.kmusic.database

import com.github.stakkato95.kmusic.database.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
interface Repository {

    fun getAllTracks(): Observable<Track>
}