package com.github.stakkato95.kmusic.mvp.repository.media

import android.database.Cursor
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
interface CursorParser {

    fun parse(cursor: Cursor, playerTracks: MutableList<PlayerTrack>)
}