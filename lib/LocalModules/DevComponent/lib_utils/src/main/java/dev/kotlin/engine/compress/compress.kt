package dev.kotlin.engine.compress

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
 * @param engine String?
 * @return ICompressEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 Compress Engine
 */
internal fun getEngine(engine: String?): ICompressEngine<in ICompressEngine.EngineConfig>? {
    DevEngine.getCompress(engine)?.let { value ->
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

fun <Config : ICompressEngine.EngineConfig> compress_any(
    engine: String? = null,
    data: Any?,
    config: Config?,
    compressListener: OnCompressListener?
): Boolean {
    return getEngine(engine)?.compress(data, config, compressListener) ?: false
}

fun <Config : ICompressEngine.EngineConfig> compress_any(
    engine: String? = null,
    data: Any?,
    config: Config?,
    filter: CompressFilter?,
    renameListener: OnRenameListener?,
    compressListener: OnCompressListener?
): Boolean {
    return getEngine(engine)?.compress(
        data, config, filter, renameListener, compressListener
    ) ?: false
}

// =

fun <Config : ICompressEngine.EngineConfig> compress_list(
    engine: String? = null,
    lists: List<*>?,
    config: Config?,
    compressListener: OnCompressListener?
): Boolean {
    return getEngine(engine)?.compress(
        lists, config, compressListener
    ) ?: false
}

fun <Config : ICompressEngine.EngineConfig> compress_list(
    engine: String? = null,
    lists: List<*>?,
    config: Config?,
    filter: CompressFilter?,
    renameListener: OnRenameListener?,
    compressListener: OnCompressListener?
): Boolean {
    return getEngine(engine)?.compress(
        lists, config, filter, renameListener, compressListener
    ) ?: false
}