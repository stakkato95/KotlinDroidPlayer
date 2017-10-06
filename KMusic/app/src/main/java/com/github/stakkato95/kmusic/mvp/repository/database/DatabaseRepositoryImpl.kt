package com.github.stakkato95.kmusic.mvp.repository.database

import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class DatabaseRepositoryImpl : DatabaseRepository {

    override fun getAllTracks(): Observable<List<Track>> {
        return Observable.empty()
    }

    override fun getCurrentTrack(): Observable<Track> {
        return Observable.empty()
    }

    override fun saveAllTracks(tracks: List<Track>) {
    }
}