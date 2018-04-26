package com.github.stakkato95.kmusic.mvp.repository

import com.github.stakkato95.kmusic.mvp.repository.media.MediaStoreRepository
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
interface Repository : MediaStoreRepository {

    fun getCurrentTrack(): Observable<PlayerTrack>
}