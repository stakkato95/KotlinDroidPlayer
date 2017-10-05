package com.github.stakkato95.kmusic.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.stakkato95.kmusic.player.ui.PlayerFragment
import com.github.stakkato95.kmusic.trackinfo.TrackInfoFragment
import com.github.stakkato95.kmusic.tracks.ui.TracksFragment

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class RootPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TrackInfoFragment()
            1 -> PlayerFragment()
            else -> TracksFragment()
        }
    }
}