package com.github.stakkato95.kmusic.extensions

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

/**
 * Created by artsiomkaliaha on 30.06.17.
 */

fun Bitmap.blur(context: Context, outputBitmapScale: Float, blurRadius: Float): Bitmap {
    var checkedBlurRadius = 0.0f
    checkedBlurRadius = if (blurRadius > 25) 25f else if (blurRadius < 0) 0f else blurRadius

    val height = Math.round(this.height * outputBitmapScale)
    val width = Math.round(this.width * outputBitmapScale)

    val inputBitmap = Bitmap.createScaledBitmap(this, width, height, false)
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    val renderScript = RenderScript.create(context)
    val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

    val allocationIn = Allocation.createFromBitmap(renderScript, inputBitmap)
    val allocationOut = Allocation.createFromBitmap(renderScript, outputBitmap)

    blurScript.setRadius(blurRadius)
    blurScript.setInput(allocationIn)
    blurScript.forEach(allocationOut)
    allocationOut.copyTo(outputBitmap)

    return outputBitmap
}
