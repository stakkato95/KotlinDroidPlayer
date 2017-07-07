package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.github.stakkato95.kmusic.player.PlayerPagerAdapter

class MainActivity : AppCompatActivity() {

    //TODO remove bullshit
    var isPlaying = true

    val playPause by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_play_pause, null) }

    val pausePlay by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_play, null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)

        val pager = findViewById(R.id.pager) as ViewPager
        pager.adapter = PlayerPagerAdapter(supportFragmentManager)
    }
}
