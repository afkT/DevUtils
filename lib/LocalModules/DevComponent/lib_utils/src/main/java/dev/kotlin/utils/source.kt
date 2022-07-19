package dev.kotlin.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import dev.base.DevSource
import java.io.File
import java.io.InputStream

// ============
// = DevSource =
// ============

fun String.toSource(): DevSource {
    return DevSource.create(this)
}

fun String.toSourceByPath(): DevSource {
    return DevSource.createWithPath(this)
}

fun Uri.toSource(): DevSource {
    return DevSource.create(this)
}

fun ByteArray.toSource(): DevSource {
    return DevSource.create(this)
}

fun Int.toSource(): DevSource {
    return DevSource.create(this)
}

fun File.toSource(): DevSource {
    return DevSource.create(this)
}

fun InputStream.toSource(): DevSource {
    return DevSource.create(this)
}

fun Drawable.toSource(): DevSource {
    return DevSource.create(this)
}

fun Bitmap.toSource(): DevSource {
    return DevSource.create(this)
}