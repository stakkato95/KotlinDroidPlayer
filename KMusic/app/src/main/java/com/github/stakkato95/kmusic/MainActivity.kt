package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.github.stakkato95.kmusic.player.PlayerPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)

        val pager = findViewById(R.id.pager) as ViewPager
        with(pager) {
            adapter = PlayerPagerAdapter(supportFragmentManager)
            val leftRightPadding = resources.displayMetrics.widthPixels / 6
            setPadding(leftRightPadding, 0, leftRightPadding, 0)
        }
    }
}
