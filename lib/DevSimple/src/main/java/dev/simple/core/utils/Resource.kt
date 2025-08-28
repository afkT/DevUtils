package dev.simple.core.utils

import dev.utils.app.ResourceUtils

// ============
// = Resource =
// ============

/**
 * 获取 R.string.id String
 * @return String
 */
fun Int.toResString(): String {
    return ResourceUtils.getString(this)
}