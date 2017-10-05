package com.github.stakkato95.kmusic.mvp.usecase

import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 05.10.17.
 */
class SingleTrackUseCaseImpl: SingleTrackUseCase {

    override fun getData(): Observable<Track> = Observable.just(Track("",""))
}