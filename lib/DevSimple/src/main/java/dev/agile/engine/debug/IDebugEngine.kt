package dev.agile.engine.debug

import androidx.fragment.app.FragmentActivity

/**
 * detail: Debug 编译辅助开发 Engine 接口
 * @author Ttt
 */
interface IDebugEngine {

    /**
     * 设置 Debug 功能开关
     * @param display 是否显示 Debug 功能
     */
    fun setDebugFunction(display: Boolean)

    /**
     * 连接 ( 显示 ) Debug 功能关联
     * @param activity 所属 Activity
     */
    fun attachDebug(activity: FragmentActivity?)

    /**
     * 分离 ( 隐藏 ) Debug 功能关联
     * @param activity 所属 Activity
     */
    fun detachDebug(activity: FragmentActivity?)
}