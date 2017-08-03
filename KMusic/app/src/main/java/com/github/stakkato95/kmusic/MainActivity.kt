package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.stakkato95.kmusic.tracks.view.ViewPagerCoordinator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player_button.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalPagerView.adapter = RootPager(supportFragmentManager)

        ViewPagerCoordinator(verticalPagerView, albumTextVeiw)

        setProgressBarTouchListener()
    }

    fun setProgressBarTouchListener() {
        albumTextVeiw.post {
            musicProgressBar.setProgressStateListener { isScrollInProgress ->
                verticalPagerView.canInterceptTouchEvents = !isScrollInProgress
            }
        }
    }
}
