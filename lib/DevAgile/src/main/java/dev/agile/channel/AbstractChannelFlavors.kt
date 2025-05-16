package dev.agile.channel

import dev.base.DevVariable

/**
 * detail: 多渠道接口
 * @author Ttt
 * 用于统一不同渠道使用相同方法获取渠道、数据信息
 * 通过官方 productFlavors 实现
 * @see https://developer.android.com/studio/build/build-variants
 * 支持不同渠道编译不同代码、logo、布局、资源文件等各种差异化实现
 */
interface AbstractChannelFlavors {

    /**
     * 获取渠道名
     * @return 渠道名
     */
    fun getChannel(): String

    // ==========
    // = 渠道信息 =
    // ==========

    /**
     * 获取指定 Key 渠道信息 ( 只读 )
     * @param key 指定 Key
     * @return 指定渠道信息
     */
    fun getChannelInfo(key: String): String?

    // ==============
    // = 额外携带信息 =
    // ==============

    /**
     * 获取指定 Key 渠道额外携带信息 ( 只读 )
     * @param key 指定 Key
     * @return 指定渠道额外携带信息
     * 专门用于区分渠道信息, 便于后续扩展及区分
     */
    fun getExtraInfo(key: String): String?

    // ============
    // = 可读写数据 =
    // ============

    /**
     * 获取渠道变量操作基类
     * @return DevVariable<String, Any>
     */
    fun getVariable(): DevVariable<String, Any>

    /**
     * 操作渠道变量
     * @param operate 操作类型
     * @return `true` success, `false` fail
     * 可自行决定是否根据 [operate] 存储到本地、删除数据等
     * 防止开发人员传入无法 json 映射数据
     * 尽量建议该 [DevVariable] 用于内存数据读写不操作本地
     * 只是提供一种方案用于不用自行决定统一规范
     */
    fun opVariable(operate: String): Boolean
}