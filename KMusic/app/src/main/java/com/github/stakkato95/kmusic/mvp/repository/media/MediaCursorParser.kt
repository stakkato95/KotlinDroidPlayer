package com.github.stakkato95.kmusic.mvp.repository.media

import android.database.Cursor
import com.github.stakkato95.kmusic.mvp.repository.model.Track

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
interface MediaCursorParser {

    fun parse(cursor: Cursor) : List<Track>
}