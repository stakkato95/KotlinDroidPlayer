package com.github.stakkato95.kmusic.mvp.repository.media

import android.content.Context
import android.provider.MediaStore
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class MediaStoreRepositoryImpl(val context: Context, private val parser: CursorParser, private val albumsParser: CursorParser) : MediaStoreRepository {

    //TODO dieses LOGIC muss sich im Parser befinden
    private val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST
    )
    private val albumsProjection = arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART)

    override fun getAllTracks(): Observable<List<PlayerTrack>> {
        return Observable.create<List<PlayerTrack>> {
            val tracks = mutableListOf<PlayerTrack>()

            context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null).use {
                parser.parse(it, tracks)
            }

            context.contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumsProjection, null, null, null).use {
                albumsParser.parse(it, tracks)
            }

            it.onNext(tracks)
            it.onComplete()
        }
    }
}