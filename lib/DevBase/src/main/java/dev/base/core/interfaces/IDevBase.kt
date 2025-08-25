package dev.base.core.interfaces

/**
 * detail: 基类相关方法
 * @author Ttt
 */
interface IDevBase : IDevBaseConfig,
    IDevBaseLayout,
    IDevBaseMethod {

    companion object {
        val NONE = 0
    }
}