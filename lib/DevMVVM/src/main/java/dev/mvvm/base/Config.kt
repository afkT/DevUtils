package dev.mvvm.base

import dev.expand.engine.log.log_isPrintLog
import dev.mvvm.DevMVVM
import dev.utils.app.ClickUtils

/**
 * detail: DevMVVM 通用配置方法
 * @author Ttt
 */
object Config {

    // 是否打印日志 ( 用于控制 MVVM 模块 )
    private var JUDGE_PRINT_LOG = false

    // 双击间隔时间
    private var INTERVAL_TIME = ClickUtils.INTERVAL_TIME

    /**
     * 开启日志开关
     */
    internal fun openLog(): DevMVVM {
        JUDGE_PRINT_LOG = true
        return DevMVVM
    }

    /**
     * 设置默认点击时间间隔
     * @param intervalTime 双击时间间隔
     * @return [DevMVVM]
     */
    internal fun setIntervalTime(intervalTime: Long): DevMVVM {
        INTERVAL_TIME = intervalTime
        return DevMVVM
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 是否打印日志
     * @return `true` yes, `false` no
     */
    fun printLog(): Boolean {
        return JUDGE_PRINT_LOG
    }

    /**
     * 是否打印日志
     * @param engine String?
     * @return `true` yes, `false` no
     */
    fun printLog(engine: String?): Boolean {
        return JUDGE_PRINT_LOG && log_isPrintLog(engine)
    }

    /**
     * 获取双击间隔时间
     * @param intervalTime 原始间隔时间
     * @return 双击间隔时间
     */
    fun intervalTime(intervalTime: Long): Long {
        return if (intervalTime == 0L) {
            INTERVAL_TIME
        } else {
            intervalTime
        }
    }
}