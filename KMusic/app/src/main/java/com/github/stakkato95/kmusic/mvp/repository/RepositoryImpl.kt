package com.github.stakkato95.kmusic.mvp.repository

import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class RepositoryImpl: Repository {

    override fun getAllTracks(): Observable<List<Track>> {
        return Observable.empty()
    }

    override fun getCurrentTrack(): Observable<Track> {
        return Observable.empty()
    }
}