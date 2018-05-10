package com.github.stakkato95.kmusic.mvp.repository.media.extractor

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
abstract class Extractor {

    abstract val contentUri: Uri

    abstract val projection: Array<String>

    fun extract(context: Context, playerTracks: MutableList<PlayerTrack>) {
        context.contentResolver.query(contentUri, projection, null, null, null).use {
            while (it.moveToNext()) {
                parseItem(it, playerTracks)
            }
        }
    }

    abstract fun parseItem(cursor: Cursor, playerTracks: MutableList<PlayerTrack>)
}