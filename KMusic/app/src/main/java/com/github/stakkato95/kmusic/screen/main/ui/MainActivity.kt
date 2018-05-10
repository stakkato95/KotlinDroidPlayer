package com.github.stakkato95.kmusic.screen.main.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.screen.main.adapter.RootPagerAdapter
import com.github.stakkato95.kmusic.screen.trackinfo.TrackInfoFragment
import com.github.stakkato95.kmusic.screen.tracks.widget.ViewPagerCoordinator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TrackInfoFragment.TitleAware {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalPagerView.adapter = RootPagerAdapter(supportFragmentManager)

        val coordinator = ViewPagerCoordinator(verticalPagerView, albumTextVeiw, 1f, 0.5f)
        coordinator.labelMovementPercent = 0.4f
    }

    override fun setTitle(title: String) {
        albumTextVeiw.text = title
    }
}
