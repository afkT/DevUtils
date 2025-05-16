package dev.simple.app.base.lifecycle

import dev.utils.app.assist.lifecycle.current.ThisActivityLifecycleAssist

/**
 * detail: 内部生命周期管理类
 * @author Ttt
 */
internal class LifecycleManager private constructor() {

    private object Holder {
        val instance = LifecycleManager()
    }

    companion object {

        private fun manager(): LifecycleManager {
            return Holder.instance
        }

        /**
         * 获取当前 Activity 生命周期辅助类
         * @return ThisActivityLifecycleAssist
         */
        fun activity(): ThisActivityLifecycleAssist = manager().mActivityAssist
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 当前 Activity 生命周期辅助类
    private val mActivityAssist by lazy {
        ThisActivityLifecycleAssist().registerActivityLifecycleCallbacks()
    }
}