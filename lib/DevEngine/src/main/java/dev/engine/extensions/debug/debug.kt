package dev.engine.extensions.debug

import androidx.fragment.app.FragmentActivity
import dev.engine.DevEngine
import dev.engine.debug.IDebugEngine

// =============================
// = IDebugEngine<EngineConfig> =
// =============================

/**
 * 通过 Key 获取 Debug Engine
 * @param engine String?
 * @return IDebugEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 Debug Engine
 */
fun String?.getDebugEngine(): IDebugEngine<in IDebugEngine.EngineConfig>? {
    DevEngine.getDebug(this)?.let { value ->
        return value
    }
    return DevEngine.getDebug()
}

// ================
// = Debug Engine =
// ================

fun <Config : IDebugEngine.EngineConfig> Config.debug_initialize(
    engine: String? = null
) {
    engine.getDebugEngine()?.initialize(this)
}

fun debug_isDisplayDebugFunction(
    engine: String? = null
): Boolean {
    return engine.getDebugEngine()?.isDisplayDebugFunction() == true
}

fun Boolean.debug_setDebugFunction(
    engine: String? = null
) {
    engine.getDebugEngine()?.setDebugFunction(this)
}

fun FragmentActivity.debug_attachDebug(
    engine: String? = null
) {
    engine.getDebugEngine()?.attachDebug(this)
}

fun FragmentActivity.debug_detachDebug(
    engine: String? = null
) {
    engine.getDebugEngine()?.detachDebug(this)
}

fun <Config : IDebugEngine.EngineConfig> Config.debug_updateConfig(
    engine: String? = null
) {
    engine.getDebugEngine()?.updateConfig(this)
}