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

    companion object {

        const val BITMAP_INSET = 0.05f
    }

    override fun key() = javaClass.simpleName

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        rect.inset(width * BITMAP_INSET, height * BITMAP_INSET)

        canvas.drawRoundRect(rect, width.toFloat() / 2, height.toFloat() / 2, paint)

        source.recycle()
        return bitmap.blur(context, 0.5f, 25 / 2f)
    }
}