package com.github.stakkato95.kmusic.util.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.net.Uri
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
//        val bitmap = source.blur(context, 0.5f, 25 / 2f)
//        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, bitmap)
//        roundedBitmapDrawable.isCircular = true
//        roundedBitmapDrawable.cornerRadius = Math.max(bitmap.height, bitmap.width).toFloat()

        val bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rect = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rect, source.width.toFloat() / 2, source.height.toFloat() / 2, paint)

        source.recycle()

        return bitmap.blur(context, 0.5f, 25 / 2f)
    }
}