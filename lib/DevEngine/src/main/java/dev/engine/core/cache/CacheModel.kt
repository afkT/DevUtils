package dev.engine.core.cache

import dev.engine.cache.ICacheEngine
import dev.utils.app.cache.DevCache
import dev.utils.common.cipher.Cipher

/**
 * detail: Cache Config
 * @author Ttt
 */
open class CacheConfig(
    val mDevCache: DevCache
) : ICacheEngine.EngineConfig {

    /**
     * 通用加解密中间层
     */
    override fun cipher(): Cipher? = null
}

/**
 * detail: Cache ( Data、Params ) Item
 * @author Ttt
 */
open class DataItem(
    // 存储 Key
    private val key: String?,
    // 存储类型
    private val type: Int,
    // 文件大小
    private val size: Long,
    // 保存时间 ( 毫秒 )
    private val saveTime: Long,
    // 有效期 ( 毫秒 )
    private val validTime: Long,
    // 是否永久有效
    private val isPermanent: Boolean,
    // 是否过期
    private val isDue: Boolean
) : ICacheEngine.EngineItem {

    /**
     * 存储 Key
     */
    override fun key(): String? = key

    /**
     * 存储类型
     */
    override fun type(): Int = type

    /**
     * 文件大小
     */
    override fun size(): Long = size

    /**
     * 保存时间 ( 毫秒 )
     */
    override fun saveTime(): Long = saveTime

    /**
     * 有效期 ( 毫秒 )
     */
    override fun validTime(): Long = validTime

    /**
     * 是否永久有效
     */
    override fun isPermanent(): Boolean = isPermanent

    /**
     * 是否过期
     */
    override fun isDue(): Boolean = isDue
}