package com.github.stakkato95.kmusic.util.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

/**
 * Created by artsiomkaliaha on 29.06.17.
 */

val Context.picasso: Picasso get() = Picasso.with(this)

fun Picasso.loadCover(coverPath: String?) = load(Uri.parse("file://$coverPath"))

class RoundedAndBlurredImageTransformation(private val context: Context) : Transformation {

    override fun key() = javaClass.simpleName

    override fun transform(source: Bitmap): Bitmap {
        val bitmap = source.blur(context, 0.5f, 25 / 2f)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, bitmap)
        roundedBitmapDrawable.isCircular = true
        roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()

        source.recycle()

        return roundedBitmapDrawable.bitmap
    }
}