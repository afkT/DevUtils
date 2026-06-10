package dev.engine.extensions.compress

import dev.engine.DevEngine
import dev.engine.compress.ICompressEngine
import dev.engine.compress.listener.CompressFilter
import dev.engine.compress.listener.OnCompressListener
import dev.engine.compress.listener.OnRenameListener

// =================================
// = ICompressEngine<EngineConfig> =
// =================================

/**
 * 通过 Key 获取 Compress Engine
 * @receiver String?
 * @return ICompressEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 Compress Engine
 */
fun String?.getCompressEngine(): ICompressEngine<in ICompressEngine.EngineConfig>? {
    DevEngine.getCompress(this)?.let { value ->
        return value
    }
    return DevEngine.getCompress()
}

// =======================
// = Key Compress Engine =
// =======================

// =============
// = 对外公开方法 =
// =============

/**
 * 压缩方法
 * @receiver 待压缩图片
 * @param engine String?
 * @param config 压缩配置参数
 * @param compressListener 压缩回调接口
 * @return `true` success, `false` fail
 */
fun <Config : ICompressEngine.EngineConfig> Any?.compress_any(
    engine: String? = null,
    config: Config?,
    compressListener: OnCompressListener?
): Boolean {
    return engine.getCompressEngine()?.compress(this, config, compressListener) ?: false
}

/**
 * 压缩方法
 * @receiver 待压缩图片
 * @param engine String?
 * @param config 压缩配置参数
 * @param filter 开启压缩条件
 * @param renameListener 压缩前重命名接口
 * @param compressListener 压缩回调接口
 * @return `true` success, `false` fail
 */
fun <Config : ICompressEngine.EngineConfig> Any?.compress_any(
    engine: String? = null,
    config: Config?,
    filter: CompressFilter?,
    renameListener: OnRenameListener?,
    compressListener: OnCompressListener?
): Boolean {
    return engine.getCompressEngine()?.compress(
        this, config, filter, renameListener, compressListener
    ) ?: false
}

// =

/**
 * 压缩方法
 * @receiver 待压缩图片集合
 * @param engine String?
 * @param config 压缩配置参数
 * @param compressListener 压缩回调接口
 * @return `true` success, `false` fail
 */
fun <Config : ICompressEngine.EngineConfig> List<*>?.compress_list(
    engine: String? = null,
    config: Config?,
    compressListener: OnCompressListener?
): Boolean {
    return engine.getCompressEngine()?.compress(
        this, config, compressListener
    ) ?: false
}

/**
 * 压缩方法
 * @receiver 待压缩图片集合
 * @param engine String?
 * @param config 压缩配置参数
 * @param filter 开启压缩条件
 * @param renameListener 压缩前重命名接口
 * @param compressListener 压缩回调接口
 * @return `true` success, `false` fail
 */
fun <Config : ICompressEngine.EngineConfig> List<*>?.compress_list(
    engine: String? = null,
    config: Config?,
    filter: CompressFilter?,
    renameListener: OnRenameListener?,
    compressListener: OnCompressListener?
): Boolean {
    return engine.getCompressEngine()?.compress(
        this, config, filter, renameListener, compressListener
    ) ?: false
}