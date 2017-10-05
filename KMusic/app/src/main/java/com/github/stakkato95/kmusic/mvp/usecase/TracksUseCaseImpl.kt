package com.github.stakkato95.kmusic.mvp.usecase

import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class TracksUseCaseImpl : TracksUseCase {

    override fun getData(): Observable<List<Track>> {
        return Observable.just(listOf(Track("Hello", "World")))
    }
}