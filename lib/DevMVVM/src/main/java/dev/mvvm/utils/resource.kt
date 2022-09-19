package dev.mvvm.utils

import dev.utils.app.ResourceUtils

/**
 * 获取 R.string.id String
 * @return String
 */
fun Int.toResString(): String {
    return ResourceUtils.getString(this)
}