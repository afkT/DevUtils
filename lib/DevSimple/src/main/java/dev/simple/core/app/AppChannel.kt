package dev.simple.core.app

import dev.base.DevVariable
import dev.simple.core.channel.AbstractChannelFlavors
import dev.utils.DevFinal
import dev.utils.common.ThrowableUtils

/**
 * detail: APP 渠道信息
 * @author Ttt
 */
object AppChannel : AbstractChannelFlavors {

    // 渠道信息实现
    private val IMPL: AbstractChannelFlavors by lazy {
        newChannelFlavorsImpl()
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 是否未找到渠道实现
     * @return `true` yes, `false` no
     */
    fun isNotFoundChannel(): Boolean {
        return IMPL is NotFoundChannelFlavors
    }

    // ==========================
    // = AbstractChannelFlavors =
    // ==========================

    /**
     * 获取渠道名
     * @return 渠道名
     */
    override fun getChannel(): String {
        return IMPL.getChannel()
    }

    // ==========
    // = 渠道信息 =
    // ==========

    /**
     * 获取指定 Key 渠道信息 ( 只读 )
     * @param key 指定 Key
     * @return 指定渠道信息
     */
    override fun getChannelInfo(key: String): String? {
        return IMPL.getChannelInfo(key)
    }

    // ==============
    // = 额外携带信息 =
    // ==============

    /**
     * 获取指定 Key 渠道额外携带信息 ( 只读 )
     * @param key 指定 Key
     * @return 指定渠道额外携带信息
     */
    override fun getExtraInfo(key: String): String? {
        return IMPL.getExtraInfo(key)
    }

    // ============
    // = 可读写数据 =
    // ============

    /**
     * 获取渠道变量操作基类
     * @return DevVariable<String, Any>
     */
    override fun getVariable(): DevVariable<String, Any> {
        return IMPL.getVariable()
    }

    /**
     * 操作渠道变量
     * @param operate 操作类型
     * @return `true` success, `false` fail
     */
    override fun opVariable(operate: String): Boolean {
        return IMPL.opVariable(operate)
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 多渠道实现类名 ( 类名必须一致且包名位置相同 )
    private const val IMPL_CLASS_NAME = "dev.simple.agile.channel.ChannelFlavorsImpl"

    /**
     * 通过 class 创建多渠道实例
     * @return AbstractChannelFlavors Impl
     */
    private fun newChannelFlavorsImpl(): AbstractChannelFlavors {
        return try {
            val clazz = Class.forName(IMPL_CLASS_NAME)
            val channelImpl = clazz.newInstance()
            channelImpl as AbstractChannelFlavors
        } catch (e: Exception) {
            NotFoundChannelFlavors(ThrowableUtils.getThrowable(e))
        }
    }

    /**
     * detail: 未找到渠道实现
     * @author Ttt
     */
    private class NotFoundChannelFlavors(
        private val errorMessage: String
    ) : AbstractChannelFlavors {

        private val mVariable = DevVariable<String, Any>()

        override fun getChannel(): String {
            return DevFinal.STR.NOT_FOUND.uppercase()
        }

        override fun getChannelInfo(key: String): String {
            return errorMessage
        }

        override fun getExtraInfo(key: String): String {
            return errorMessage
        }

        override fun getVariable(): DevVariable<String, Any> {
            return mVariable
        }

        override fun opVariable(operate: String): Boolean {
            return false
        }
    }
}