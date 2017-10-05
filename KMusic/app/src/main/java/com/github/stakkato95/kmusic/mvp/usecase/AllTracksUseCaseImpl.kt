package com.github.stakkato95.kmusic.mvp.usecase

import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class AllTracksUseCaseImpl(val repository: Repository) : AllTracksUseCase {

    override fun getData(): Observable<List<Track>> {
        return repository
                .getAllTracks()
                .subscribeOn(Schedulers.io())
    }
}