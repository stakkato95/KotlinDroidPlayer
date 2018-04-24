package com.github.stakkato95.kmusic.mvp.repository.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import io.reactivex.Flowable

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTracks(tracks: List<Track>)

    @Query("SELECT * FROM Track")
    fun getAllTracks(): Flowable<List<Track>>
}