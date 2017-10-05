package com.github.stakkato95.kmusic.util.extensions

import android.graphics.Point
import android.view.MotionEvent

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
fun lerp(start: Float, end: Float, ratio: Float) = start + ratio * (end - start)

fun MotionEvent.toPoint() = Point(x.toInt(), y.toInt())