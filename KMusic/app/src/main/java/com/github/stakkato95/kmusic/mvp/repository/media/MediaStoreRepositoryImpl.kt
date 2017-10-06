package com.github.stakkato95.kmusic.mvp.repository.media

import android.content.Context
import android.provider.MediaStore
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class MediaStoreRepositoryImpl(val context: Context, val parser: MediaCursorParser) : MediaStoreRepository {

    val projection = arrayOf(
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST
    )

    override fun getAllTracks(): Observable<List<Track>> {
        return Observable.create<List<Track>> {
            var tracks = listOf<Track>()

            context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null).use {
                tracks = parser.parse(it)
            }

            it.onNext(tracks)
            it.onComplete()
        }
    }
}