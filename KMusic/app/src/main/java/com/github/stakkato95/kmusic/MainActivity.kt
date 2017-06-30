package com.github.stakkato95.kmusic

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.github.stakkato95.kmusic.extensions.blur
import com.github.stakkato95.kmusic.extensions.picasso
import com.squareup.picasso.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image = findViewById(R.id.centerImage) as ImageView

        picasso.load(R.drawable.test_background).into(image, object: Callback {
            override fun onSuccess() {
                val bitmap = (image.drawable as BitmapDrawable).bitmap.blur(this@MainActivity, 0.5f, 25 / 2f)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                roundedBitmapDrawable.isCircular = true
                roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()

                image.setImageDrawable(roundedBitmapDrawable)
            }

            override fun onError() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        val vectorIcon = findViewById(R.id.vector_icon) as Button

        vectorIcon.setOnClickListener {
            (vectorIcon.background as AnimatedVectorDrawable).start()
        }
    }
}
