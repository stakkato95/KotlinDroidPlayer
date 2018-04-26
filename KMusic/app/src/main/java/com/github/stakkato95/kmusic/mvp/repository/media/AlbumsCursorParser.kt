package com.github.stakkato95.kmusic.mvp.repository.media

import android.database.Cursor
import android.provider.MediaStore
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import com.github.stakkato95.kmusic.util.extensions.getInt
import com.github.stakkato95.kmusic.util.extensions.getString

class AlbumsCursorParser : CursorParser {

    override fun parse(cursor: Cursor, tracks: MutableList<Track>) {
        while (cursor.moveToNext()) {
            val albumId = cursor.getInt(MediaStore.Audio.Albums._ID)
            val trackCover = cursor.getString(MediaStore.Audio.Albums.ALBUM_ART)
            val track = tracks.asSequence().first { it.albumId == albumId }
            track.coverPath = trackCover
        }
    }
}