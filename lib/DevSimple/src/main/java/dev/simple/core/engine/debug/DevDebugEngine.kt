package dev.simple.core.engine.debug

import dev.engine.DevEngineAssist

/**
 * detail: Debug 编译辅助开发 Engine
 * @author Ttt
 */
object DevDebugEngine {

    private val sAssist = DevEngineAssist<IDebugEngine>()

    /**
     * 获取 Engine
     * @return [IDebugEngine]
     */
    fun getEngine(): IDebugEngine? {
        return sAssist.engine
    }

    /**
     * 获取 Engine
     * @param key key
     * @return [IDebugEngine]
     */
    fun getEngine(key: String): IDebugEngine? {
        return sAssist.getEngine(key)
    }

    /**
     * 设置 Engine
     * @param engine [IDebugEngine]
     * @return [IDebugEngine]
     */
    fun setEngine(engine: IDebugEngine): IDebugEngine? {
        return sAssist.setEngine(engine)
    }

    /**
     * 设置 Engine
     * @param key    key
     * @param engine [IDebugEngine]
     * @return [IDebugEngine]
     */
    fun setEngine(
        key: String,
        engine: IDebugEngine
    ): IDebugEngine? {
        return sAssist.setEngine(key, engine)
    }

    /**
     * 移除 Engine
     */
    fun removeEngine() {
        sAssist.removeEngine()
    }

    /**
     * 移除 Engine
     * @param key key
     */
    fun removeEngine(key: String?) {
        sAssist.removeEngine(key)
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 获取 DevEngine Generic Assist
     * @return DevEngine Generic Assist
     */
    fun getAssist(): DevEngineAssist<IDebugEngine> {
        return sAssist
    }

    /**
     * 获取 Engine Map
     * @return Engine Map
     */
    fun getEngineMaps(): Map<String?, IDebugEngine?> {
        return sAssist.engineMaps
    }

    /**
     * 是否存在 Engine
     * @return `true` yes, `false` no
     */
    fun contains(): Boolean {
        return sAssist.contains()
    }

    /**
     * 是否存在 Engine
     * @param key key
     * @return `true` yes, `false` no
     */
    operator fun contains(key: String?): Boolean {
        return sAssist.contains(key)
    }

    /**
     * 判断 Engine 是否为 null
     * @return `true` yes, `false` no
     */
    fun isEmpty(): Boolean {
        return sAssist.isEmpty
    }

    /**
     * 判断 Engine 是否为 null
     * @param key key
     * @return `true` yes, `false` no
     */
    fun isEmpty(key: String?): Boolean {
        return sAssist.isEmpty(key)
    }
}