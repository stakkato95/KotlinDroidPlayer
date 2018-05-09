package com.github.stakkato95.kmusic.util.picasso

import android.content.Context
import android.graphics.Bitmap
import com.github.stakkato95.kmusic.util.extensions.blur
import com.squareup.picasso.Transformation

class BlurTransformation(private val context: Context) : Transformation {

    override fun key() = javaClass.simpleName

    override fun transform(source: Bitmap): Bitmap {
//        val bitmap = Bitmap.createBitmap(source, 0, 0, source.width, source.height)
        val bitmap = source.blur(context)
        source.recycle()
        return bitmap
    }
}