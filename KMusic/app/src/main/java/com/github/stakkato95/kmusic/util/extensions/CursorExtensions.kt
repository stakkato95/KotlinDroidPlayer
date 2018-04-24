package com.github.stakkato95.kmusic.util.extensions

import android.database.Cursor

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
fun Cursor.getString(column: String) = getString(getColumnIndex(column))

fun Cursor.getInteger(column: String) = getInt(getColumnIndex(column))