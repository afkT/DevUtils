package dev.kotlin.engine.storage

import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.storage.IStorageEngine
import dev.engine.storage.listener.OnInsertListener

// ============================================
// = IStorageEngine<EngineItem, EngineResult> =
// ============================================

/**
 * 通过 Key 获取 Storage Engine
 * @param engine String?
 * @return IStorageEngine<EngineItem, EngineResult>
 * 内部做了处理如果匹配不到则返回默认 Storage Engine
 */
internal fun getEngine(engine: String?): IStorageEngine<in IStorageEngine.EngineItem, in IStorageEngine.EngineResult>? {
    DevEngine.getStorage(engine)?.let { value ->
        return value
    }
    return DevEngine.getStorage()
}

// ======================
// = Key Storage Engine =
// ======================

// =============
// = 对外公开方法 =
// =============

// ==========
// = 外部存储 =
// ==========

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertImageToExternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertImageToExternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertVideoToExternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertVideoToExternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertAudioToExternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertAudioToExternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertDownloadToExternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertDownloadToExternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertMediaToExternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertMediaToExternal(
            params, this, listener
        )
    }
}

// ==========
// = 内部存储 =
// ==========

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertImageToInternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertImageToInternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertVideoToInternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertVideoToInternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertAudioToInternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertAudioToInternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertDownloadToInternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertDownloadToInternal(
            params, this, listener
        )
    }
}

fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> DevSource.storage_insertMediaToInternal(
    engine: String? = null,
    params: Item?,
    listener: OnInsertListener<Item, Result>?
) {
    getEngine(engine)?.let {
        (it as? IStorageEngine<Item, Result>)?.insertMediaToInternal(
            params, this, listener
        )
    }
}