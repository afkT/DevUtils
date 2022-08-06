package dev.engine.cache

/**
 * detail: Cache ( Data、Params ) Item
 * @author Ttt
 */
open class DataItem(
    key: String?,
    type: Int,
    size: Long,
    saveTime: Long,
    validTime: Long,
    isPermanent: Boolean,
    isDue: Boolean
) : ICacheEngine.EngineItem(key, type, size, saveTime, validTime, isPermanent, isDue)