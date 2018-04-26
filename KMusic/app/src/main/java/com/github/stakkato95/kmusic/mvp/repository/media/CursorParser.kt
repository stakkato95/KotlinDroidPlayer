package com.github.stakkato95.kmusic.mvp.repository.media

import android.database.Cursor
import com.github.stakkato95.kmusic.mvp.repository.model.Track

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
interface CursorParser {

    fun parse(cursor: Cursor, tracks: MutableList<Track>)
}