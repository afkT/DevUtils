package dev.engine.extensions.debug

import androidx.fragment.app.FragmentActivity
import dev.engine.DevEngine
import dev.engine.debug.IDebugEngine

// =============================
// = IDebugEngine<EngineConfig> =
// =============================

/**
 * 通过 Key 获取 Debug Engine
 * @receiver String?
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

/**
 * 初始化方法
 * @receiver Debug Config
 * @param engine String?
 */
fun <Config : IDebugEngine.EngineConfig> Config.debug_initialize(
    engine: String? = null
) {
    engine.getDebugEngine()?.initialize(this)
}

/**
 * 是否显示 Debug 功能开关
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun debug_isDisplayDebugFunction(
    engine: String? = null
): Boolean {
    return engine.getDebugEngine()?.isDisplayDebugFunction() == true
}

/**
 * 设置 Debug 功能开关
 * @receiver 是否显示 Debug 功能
 * @param engine String?
 */
fun Boolean.debug_setDebugFunction(
    engine: String? = null
) {
    engine.getDebugEngine()?.setDebugFunction(this)
}

/**
 * 连接 ( 显示 ) Debug 功能关联
 * @receiver 所属 Activity
 * @param engine String?
 */
fun FragmentActivity.debug_attachDebug(
    engine: String? = null
) {
    engine.getDebugEngine()?.attachDebug(this)
}

/**
 * 分离 ( 隐藏 ) Debug 功能关联
 * @receiver 所属 Activity
 * @param engine String?
 */
fun FragmentActivity.debug_detachDebug(
    engine: String? = null
) {
    engine.getDebugEngine()?.detachDebug(this)
}

/**
 * 更新 Debug Config
 * @receiver Debug Config
 * @param engine String?
 */
fun <Config : IDebugEngine.EngineConfig> Config.debug_updateConfig(
    engine: String? = null
) {
    engine.getDebugEngine()?.updateConfig(this)
}