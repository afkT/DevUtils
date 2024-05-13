package dev.simple.app.controller.ui.skeleton

import android.view.View
import dev.widget.assist.ViewAssist

/**
 * detail: ViewAssist 通用接口工厂模式
 * @author Ttt
 */
interface IViewAssistFactory {

    // ==========
    // = 变量获取 =
    // ==========

    /**
     * View 填充辅助类载体
     * @return ViewAssist?
     */
    fun viewAssist(): ViewAssist?

    /**
     * 重新加载点击事件
     * @return View.OnClickListener
     */
    fun reloadListener(): View.OnClickListener

    /**
     * 具体功能页面 View 填充展示容器
     * @return View?
     */
    fun contentLayout(): View?

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 注册 View type 方法
     */
    fun register()
}

/**
 * detail: ViewAssist 工厂注册接口
 * @author Ttt
 */
interface IViewAssistFactoryRegister {

    /**
     * ViewAssist 工厂注册方法
     * @param factory [IViewAssistFactory]
     */
    fun register(factory: IViewAssistFactory)
}