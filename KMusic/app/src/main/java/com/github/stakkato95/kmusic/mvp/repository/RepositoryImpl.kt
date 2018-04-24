package com.github.stakkato95.kmusic.mvp.repository

import com.github.stakkato95.kmusic.mvp.repository.database.DatabaseRepository
import com.github.stakkato95.kmusic.mvp.repository.media.MediaStoreRepository
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class RepositoryImpl(private val mediaStoreRepository: MediaStoreRepository,
                     private val databaseRepository: DatabaseRepository): Repository {

    override fun getAllTracks(): Observable<List<Track>> {
        return databaseRepository
                .getAllTracks()
                .switchIfEmpty(mediaStoreRepository.getAllTracks())
                .doOnNext { databaseRepository.saveAllTracks(it) }
    }

    override fun getCurrentTrack(): Observable<Track> = databaseRepository.getCurrentTrack()
}