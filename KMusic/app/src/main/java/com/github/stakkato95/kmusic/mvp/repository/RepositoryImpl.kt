package com.github.stakkato95.kmusic.mvp.repository

import com.github.stakkato95.kmusic.mvp.repository.database.DatabaseRepository
import com.github.stakkato95.kmusic.mvp.repository.media.MediaStoreRepository
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class RepositoryImpl(private val mediaStoreRepository: MediaStoreRepository,
                     private val databaseRepository: DatabaseRepository) : Repository {

    override fun getAllTracks(): Observable<List<PlayerTrack>> {
        return databaseRepository
                .getAllTracks()
                .take(1)
                .filter { it.isNotEmpty() }
                .switchIfEmpty(mediaStoreRepository.getAllTracks().doOnNext {
                    databaseRepository.saveAllTracks(it)
                })
    }

    override fun getCurrentTrack(): Observable<PlayerTrack> = databaseRepository.getCurrentTrack()
}