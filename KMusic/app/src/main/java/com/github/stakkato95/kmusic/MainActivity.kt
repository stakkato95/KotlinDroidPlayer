package com.github.stakkato95.kmusic

import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.stakkato95.kmusic.common.adapter.RootPagerAdapter
import com.github.stakkato95.kmusic.tracks.view.ViewPagerCoordinator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player_button.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalPagerView.adapter = RootPagerAdapter(supportFragmentManager)

        val coordinator = ViewPagerCoordinator(verticalPagerView, albumTextVeiw, 1f, 0.5f)
        coordinator.labelMovementPercent = 0.4f

        setProgressBarTouchListener()

        val cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME),
                null,
                null,
                null
        )

        Toast.makeText(this, "Tracks count = ${cursor.count}", Toast.LENGTH_LONG).show()

        cursor.close()
    }

    fun setProgressBarTouchListener() {
        albumTextVeiw.post {
            musicProgressBar.setProgressStateListener { isScrollInProgress ->
                verticalPagerView.canInterceptTouchEvents = !isScrollInProgress
            }
        }
    }
}
