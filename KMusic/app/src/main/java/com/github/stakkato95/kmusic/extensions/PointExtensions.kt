package com.github.stakkato95.kmusic.extensions

import android.graphics.Point

/**
 * Created by artsiomkaliaha on 29.06.17.
 */

fun Point.lengthTo(secondPoint: Point): Int {
    val xSquare = Math.pow((secondPoint.x - this.x).toDouble(), 2.toDouble())
    val ySquare = Math.pow((secondPoint.y - this.y).toDouble(), 2.toDouble())
    return Math.sqrt(xSquare + ySquare).toInt()
}