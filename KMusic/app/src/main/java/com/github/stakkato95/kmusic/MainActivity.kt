package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.github.stakkato95.kmusic.view.PagerScrollView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scroll = findViewById(R.id.scroll) as PagerScrollView
        val progress = findViewById(R.id.progress)

        progress.setOnTouchListener { view, event ->
            scroll.shouldRespondToTouchEvents = event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL
            return@setOnTouchListener true
        }
    }
}
