package com.github.stakkato95.kmusic.screen.player.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.stakkato95.kmusic.screen.player.ui.TrackProgressFragment

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerButtonPagerAdapter(fm: FragmentManager,
                               private val tracksCount: Int,
                               private val progressCallback: (Float) -> Unit,
                               private val playPauseCallback: (Int) -> Unit) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = TrackProgressFragment.newInstance(progressCallback, playPauseCallback, position)

    override fun getCount() = tracksCount
}