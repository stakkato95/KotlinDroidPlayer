package com.github.stakkato95.kmusic.mvp.repository.database

import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.repository.model.Track

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
interface DatabaseRepository : Repository {

    fun saveAllTracks(tracks: List<Track>)
}