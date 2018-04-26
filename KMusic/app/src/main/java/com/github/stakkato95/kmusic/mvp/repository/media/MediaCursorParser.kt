package com.github.stakkato95.kmusic.mvp.repository.media

import android.database.Cursor
import android.provider.MediaStore
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import com.github.stakkato95.kmusic.util.extensions.getInt
import com.github.stakkato95.kmusic.util.extensions.getString

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class MediaCursorParser : CursorParser {

    override fun parse(cursor: Cursor, tracks: MutableList<Track>) {
        while (cursor.moveToNext()) {
            val track = Track(
                    cursor.getInt(MediaStore.Audio.Media._ID),
                    cursor.getString(MediaStore.Audio.Media.DATA),
                    cursor.getString(MediaStore.Audio.Media.DISPLAY_NAME),
                    cursor.getString(MediaStore.Audio.Media.ARTIST),
                    cursor.getInt(MediaStore.Audio.Media.ALBUM_ID)
            )
            tracks.add(track)
        }
    }
}