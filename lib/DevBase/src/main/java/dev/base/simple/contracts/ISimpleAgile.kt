package dev.base.simple.contracts

import dev.base.core.arch.mvvm.DevBaseMVVMActivity
import dev.base.core.arch.mvvm.DevBaseMVVMFragment

/**
 * detail: 敏捷简化开发扩展接口
 * @author Ttt
 * 【起始点】[DevBaseMVVMActivity]、[DevBaseMVVMFragment]
 * TODO 特殊注释提醒
 * 该敏捷简化开发扩展接口以【起始点】进行支持, 减少无效扩展代码
 * 着重说明【起始点】后扩展的基类为了统一规范, 都需支持并实现敏捷开发功能
 */
interface ISimpleAgile {

    // ==========
    // = 具体方法 =
    // ==========

    /**
     * 简化内部初始化前调用
     */
    fun simpleInit()

    /**
     * 简化内部初始化后开始流程调用
     */
    fun simpleStart()

    /**
     * 简化预加载
     */
    fun simplePreLoad()

    /**
     * 简化调用代码
     */
    fun simpleAgile()

    // ==========
    // = 执行顺序 =
    // ==========

    // ===================
    // = Activity 执行顺序 =
    // ===================

    /**
     *【Activity】执行顺序
     * ==========
     * onCreate()
     * // 内部初始化前调用
     * [simpleInit]
     * // 内部初始化
     * innerInitialize()
     * // 内部初始化后开始流程调用
     * [simpleStart]
     * // 初始化 ViewModel
     * initViewModel()
     * // 简化预加载
     * [simplePreLoad]
     * // 预加载方法
     * preLoad()
     * // 初始化方法
     * initOrder()
     * // 敏捷开发简化调用
     * [simpleAgile]
     */

    // ===================
    // = Fragment 执行顺序 =
    // ===================

    /**
     *【Fragment】执行顺序
     * ==========
     * onCreateView()
     * // 内部初始化前调用
     * [simpleInit]
     * // 内部初始化
     * innerInitialize()
     * // 内部初始化后开始流程调用
     * [simpleStart]
     * ==========
     * onViewCreated()
     * // 初始化 ViewModel
     * initViewModel()
     * // 简化预加载
     * [simplePreLoad]
     * // 预加载方法
     * preLoad()
     * // 初始化方法
     * initOrder()
     * // 敏捷开发简化调用
     * [simpleAgile]
     */
}