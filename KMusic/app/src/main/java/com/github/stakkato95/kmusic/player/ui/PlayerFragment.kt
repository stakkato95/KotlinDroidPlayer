package com.github.stakkato95.kmusic.player.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.player.adapter.PlayerButtonPagerAdapter

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class PlayerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)


        val pager = view.findViewById<ViewPager>(R.id.pager)
        with(pager) {
            adapter = PlayerButtonPagerAdapter(fragmentManager)
            val leftRightPadding = resources.displayMetrics.widthPixels / 6
            setPadding(leftRightPadding, 0, leftRightPadding, 0)
        }

        return view
    }
}