package com.github.stakkato95.kmusic.screen.main.ui

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.screen.main.adapter.RootPagerAdapter
import com.github.stakkato95.kmusic.screen.tracks.widget.ViewPagerCoordinator
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player_button.*

class MainActivity : AppCompatActivity() {

    lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalPagerView.adapter = RootPagerAdapter(supportFragmentManager)

        val coordinator = ViewPagerCoordinator(verticalPagerView, albumTextVeiw, 1f, 0.5f)
        coordinator.labelMovementPercent = 0.4f

//        setProgressBarTouchListener()
//
        val cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA),
                null,
                null,
                null
        )

        Toast.makeText(this, "Tracks count = ${cursor.count}", Toast.LENGTH_LONG).show()
//
//        cursor.close()
//
        testExoPlayer()
    }

    fun setProgressBarTouchListener() {
        albumTextVeiw.post {
            musicProgressBar.setProgressStateListener { isScrollInProgress ->
                verticalPagerView.canInterceptTouchEvents = !isScrollInProgress
            }
        }
    }

    fun testExoPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(DefaultRenderersFactory(this), DefaultTrackSelector(), DefaultLoadControl())
        player.playWhenReady = true
        initializePlayer()
    }

    fun initializePlayer() {
        val uri = Uri.parse("/storage/emulated/0/Music/Wilkinson - Brand New.mp3")
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource)
    }

    fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource(uri, DefaultDataSourceFactory(this, "ua"), DefaultExtractorsFactory(), null, null)
    }
}
