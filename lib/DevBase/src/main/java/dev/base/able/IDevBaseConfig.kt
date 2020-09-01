package dev.base.able

/**
 * detail: 基类配置
 * @author Ttt
 */
interface IDevBaseConfig {

    /**
     * 是否进行 Activity 管理控制
     * 可通过 lifecycle 实现 Activity Manager
     */
    fun isActivityManager(): Boolean {
        return true
    }
}