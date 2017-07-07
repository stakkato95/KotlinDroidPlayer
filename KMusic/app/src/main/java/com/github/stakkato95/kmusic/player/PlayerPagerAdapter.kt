package com.github.stakkato95.kmusic.player

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = PlayerFragment.newInstance()

    override fun getCount() = 5
}