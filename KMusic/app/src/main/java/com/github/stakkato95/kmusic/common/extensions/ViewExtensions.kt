package com.github.stakkato95.kmusic.common.extensions

import android.support.v4.graphics.ColorUtils
import android.util.TypedValue
import android.widget.TextView

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
fun TextView.setBlendedColorText(startColor: Int, endColor: Int, ratio: Float) {
    setTextColor(ColorUtils.blendARGB(startColor, endColor, ratio))
}

fun TextView.setInterpolatedTextSize(startSize: Float, endSize: Float, ratio: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
    setTextSize(unit, startSize + ratio * (endSize - startSize))
}

fun lerp(start: Float, end: Float, ratio: Float) = start + ratio * (end - start)