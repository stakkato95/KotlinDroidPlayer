package com.github.stakkato95.kmusic.mvp.usecase

import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class SingleTrackUseCaseImpl(val repository: Repository): SingleTrackUseCase {

    override fun getData(): Observable<Track> {
        return repository
                .getCurrentTrack()
                .subscribeOn(Schedulers.io())
    }
}