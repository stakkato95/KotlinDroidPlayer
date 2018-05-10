package com.github.stakkato95.kmusic.mvp.repository.media.extractor

import android.database.Cursor
import android.provider.MediaStore
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.util.extensions.getInt
import com.github.stakkato95.kmusic.util.extensions.getString

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class TrackExtractor : Extractor() {

    override val contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI!!

    override val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST
    )


    override fun parseItem(cursor: Cursor, playerTracks: MutableList<PlayerTrack>) {
        playerTracks.add(PlayerTrack(
                cursor.getInt(MediaStore.Audio.Media._ID),
                cursor.getString(MediaStore.Audio.Media.DATA),
                cursor.getString(MediaStore.Audio.Media.DISPLAY_NAME),
                cursor.getString(MediaStore.Audio.Media.ARTIST),
                cursor.getInt(MediaStore.Audio.Media.ALBUM_ID)
        ))
    }
}