package com.github.stakkato95.kmusic.mvp.repository.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.github.stakkato95.kmusic.mvp.repository.model.Track

@Database(entities = [Track::class], version = 1)
abstract class KMusicDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "kmusik_db"

        fun initDatabase(context: Context)
                = Room.databaseBuilder(context.applicationContext, KMusicDatabase::class.java, DATABASE_NAME).build()
    }

    abstract fun getTrackDao(): TrackDao
}