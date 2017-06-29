package com.github.stakkato95.kmusic.extensions

import android.graphics.Point

/**
 * Created by artsiomkaliaha on 29.06.17.
 */

fun Point.lengthProductOfVectorsStartingInZero(secondPoint: Point): Double {
    val firstLength = Math.sqrt(Math.pow(x.toDouble(), 2.toDouble()) + Math.pow(y.toDouble(), 2.toDouble()))
    val secondLength = Math.sqrt(Math.pow(secondPoint.x.toDouble(), 2.toDouble()) + Math.pow(secondPoint.y.toDouble(), 2.toDouble()))
    return firstLength * secondLength
}

fun Point.scalarProduct(secondPoint: Point): Int {
    return x * secondPoint.x + y * secondPoint.y
}