package com.github.stakkato95.kmusic.mvp.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
@Entity
data class Track(
        @NonNull @PrimaryKey val id: Int,
        val path: String,
        val name: String,
        val author: String,
        val albumId: Int,
        var coverPath: String? = null
)