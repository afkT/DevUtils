package dev.engine.core.cache

import dev.engine.cache.ICacheEngine

/**
 * detail: Cache ( Data、Params ) Item
 * @author Ttt
 */
open class DataItem(
    // 存储 Key
    val key: String?,
    // 存储类型
    val type: Int,
    // 文件大小
    val size: Long,
    // 保存时间 ( 毫秒 )
    val saveTime: Long,
    // 有效期 ( 毫秒 )
    val validTime: Long,
    // 是否永久有效
    val isPermanent: Boolean,
    // 是否过期
    val isDue: Boolean
) : ICacheEngine.EngineItem