package com.github.stakkato95.kmusic

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        val pager = findViewById(R.id.root_pager) as ViewPager
        pager.adapter = RootPager(supportFragmentManager)

//
//        val pager = findViewById(R.id.pager) as ViewPager
//        with(pager) {
//            adapter = PlayerPagerAdapter(supportFragmentManager)
//            val leftRightPadding = resources.displayMetrics.widthPixels / 6
//            setPadding(leftRightPadding, 0, leftRightPadding, 0)
//        }
//
//
//        val cover = findViewById(R.id.cover_image) as ImageView
//        val blurredImage = (cover.drawable as BitmapDrawable).bitmap.blur(this, 0.5f, 12.5f)
//        cover.setImageBitmap(blurredImage)
//
//
//        val albumText = findViewById(R.id.album_text)
//        val scroll = findViewById(R.id.scroll) as ScrollView
//        val container = findViewById(R.id.container)
//        var albumTextInitialY = 0.0f
//
//        scroll.viewTreeObserver.addOnScrollChangedListener {
//            if (albumTextInitialY == 0.0f) {
//                albumTextInitialY = albumText.y
//            }
//
//            val oneScreen = container.height / 2
//            val labelMovementDistance = oneScreen * 0.6f
//
//            val scrollPercent = if (scroll.scrollY > 0) scroll.scrollY / oneScreen.toFloat() else 0.0f
//            albumText.animate().y(albumTextInitialY + labelMovementDistance * scrollPercent).setDuration(0).start()
//        }
    }
}
