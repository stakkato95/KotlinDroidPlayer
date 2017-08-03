package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.stakkato95.kmusic.common.view.RootPager
import com.github.stakkato95.kmusic.tracks.view.ViewPagerCoordinator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player_button.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalPagerView.adapter = RootPager(supportFragmentManager)

        val coordinator = ViewPagerCoordinator(verticalPagerView, albumTextVeiw, 1f, 0.5f)
        coordinator.labelMovementPercent = 0.4f

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
