package com.github.stakkato95.kmusic.mvp.repository.media

import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
interface MediaStoreRepository {

    fun getAllTracks() : Observable<List<PlayerTrack>>
}