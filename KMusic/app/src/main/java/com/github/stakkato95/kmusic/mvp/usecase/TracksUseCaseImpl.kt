package com.github.stakkato95.kmusic.mvp.usecase

import com.github.stakkato95.kmusic.database.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
class TracksUseCaseImpl : TracksUseCase {

    override fun getData(): Observable<Track> {
        return Observable.just(Track("Hello", "World"))
    }
}