package com.github.stakkato95.kmusic.util.extensions

import android.content.Context
import com.squareup.picasso.Picasso

/**
 * Created by artsiomkaliaha on 29.06.17.
 */

val Context.picasso: Picasso get() = Picasso.with(this)