package com.github.stakkato95.kmusic.mvp.repository.media.extractor

import android.database.Cursor
import android.provider.MediaStore
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.util.extensions.getInt
import com.github.stakkato95.kmusic.util.extensions.getString

class AlbumsExtractor : Extractor() {

    override val contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI!!

    override val projection = arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART)

    override fun parseItem(cursor: Cursor, playerTracks: MutableList<PlayerTrack>) {
        val albumId = cursor.getInt(MediaStore.Audio.Albums._ID)
        val trackCover = cursor.getString(MediaStore.Audio.Albums.ALBUM_ART)
        val track = playerTracks.asSequence().first { it.albumId == albumId }
        track.coverPath = trackCover
    }
}