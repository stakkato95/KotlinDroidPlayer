package com.github.stakkato95.kmusic.mvp.usecase

import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
interface UseCase<T> {

    fun getData(): Observable<T>
}