package com.github.stakkato95.kmusic.mvp.repository.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
@Entity
data class PlayerTrack(@NonNull @PrimaryKey val id: Int,
                       val path: String,
                       val name: String,
                       val author: String,
                       val albumId: Int,
                       var bitRate: Int = SAMPLE_BIT_RATE_UNSET,
                       var sampleRate: Int = SAMPLE_BIT_RATE_UNSET,
                       var coverPath: String? = null) {

    companion object {

        const val SAMPLE_BIT_RATE_UNSET = -1
    }
}