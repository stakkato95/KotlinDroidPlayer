package com.github.stakkato95.kmusic

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.stakkato95.kmusic.player.PlayerPageFragment
import com.github.stakkato95.kmusic.tracks.TracksFragment

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class RootPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return if (position == 1) PlayerPageFragment() else TracksFragment()
    }
}