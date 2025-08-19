package dev.base.utils.assist

import dev.utils.LogPrintUtils

/**
 * detail: DevBase 合并相同代码辅助类
 * @author Ttt
 */
class DevBaseAssist {

    // 日志 TAG
    private var mTag = DevBaseAssist::class.java.simpleName

    fun setTag(tag: String): DevBaseAssist {
        this.mTag = tag
        return this
    }

    fun getTag(): String {
        return this.mTag
    }

    // ==========
    // = 日志处理 =
    // ==========

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param message 打印内容
     * @return [DevBaseAssist]
     */
    fun printLog(message: String): DevBaseAssist {
        LogPrintUtils.dTag(mTag, "%s -> %s", mTag, message)
        return this
    }

    /**
     * 统一打印日志 ( 内部封装调用 )
     * @param throwable 异常
     * @param message   打印内容
     * @return [DevBaseAssist]
     */
    fun printLog(
        throwable: Throwable,
        message: String
    ): DevBaseAssist {
        LogPrintUtils.eTag(mTag, throwable, message)
        return this
    }
}