package com.github.stakkato95.kmusic.mvp.repository.database

import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.mvp.repository.room.KMusicDatabase
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class DatabaseRepositoryImpl(val database: KMusicDatabase) : DatabaseRepository {

    override fun getAllTracks() = database.getTrackDao().getAllTracks().toObservable()

    override fun getCurrentTrack(): Observable<PlayerTrack> {
        return Observable.empty()
    }

    override fun saveAllTracks(playerTracks: List<PlayerTrack>) {
        database.getTrackDao().insertTracks(playerTracks)
    }
}