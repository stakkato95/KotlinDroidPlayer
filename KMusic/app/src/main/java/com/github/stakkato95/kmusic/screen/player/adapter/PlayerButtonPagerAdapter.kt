package com.github.stakkato95.kmusic.screen.player.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.stakkato95.kmusic.screen.player.ui.PlayerButtonFragment

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerButtonPagerAdapter(fm: FragmentManager, private val tracksCount: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = PlayerButtonFragment.newInstance()

    override fun getCount() = tracksCount
}