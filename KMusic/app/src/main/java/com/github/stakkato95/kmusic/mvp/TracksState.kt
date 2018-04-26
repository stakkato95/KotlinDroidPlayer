package com.github.stakkato95.kmusic.mvp

import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import kotlin.properties.Delegates.observable

class TracksState(var tracks: List<PlayerTrack>? = null) {

    private val observers = mutableListOf<((PlayerTrack?) -> Unit)>()

    var currentPlayerTrack by observable<PlayerTrack?>(null) { _, _, new -> observers.forEach { it(new) } }

    fun addCurrentTrackObserver(observer: (PlayerTrack?) -> Unit) {
        observers.add(observer)
    }

    fun removeCurrentTrackObserver(observer: (PlayerTrack?) -> Unit) {
        observers.remove(observer)
    }

}