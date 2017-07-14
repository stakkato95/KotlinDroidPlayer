package com.github.stakkato95.kmusic

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.stakkato95.kmusic.player.PlayerFragment
import com.github.stakkato95.kmusic.trackinfo.TrackInfoFragment
import com.github.stakkato95.kmusic.tracks.TracksFragment

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class RootPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TrackInfoFragment()
            1 -> PlayerFragment()
            else -> TracksFragment()
        }
    }
}