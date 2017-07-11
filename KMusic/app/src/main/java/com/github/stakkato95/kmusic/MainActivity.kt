package com.github.stakkato95.kmusic

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ScrollView
import com.github.stakkato95.kmusic.common.extensions.blur
import com.github.stakkato95.kmusic.player.PlayerPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pager = findViewById(R.id.pager) as ViewPager
        with(pager) {
            adapter = PlayerPagerAdapter(supportFragmentManager)
            val leftRightPadding = resources.displayMetrics.widthPixels / 6
            setPadding(leftRightPadding, 0, leftRightPadding, 0)
        }


        val cover = findViewById(R.id.cover_image) as ImageView
        val blurredImage = (cover.drawable as BitmapDrawable).bitmap.blur(this, 0.5f, 12.5f)
        cover.setImageBitmap(blurredImage)


        val albumText = findViewById(R.id.album_text)
        val scroll = findViewById(R.id.scroll) as ScrollView
        val container = findViewById(R.id.container)

        scroll.viewTreeObserver.addOnScrollChangedListener {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
