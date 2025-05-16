package dev.simple.app.base.simple

/**
 * detail: 敏捷简化开发扩展工厂类
 * @author Ttt
 * 具体查看 [ISimpleAgile]
 */
interface SimpleAgileFactory<SimpleTClass, SimpleTUITheme> {

    // ==========
    // = 具体方法 =
    // ==========

    /**
     * 简化构造函数 init {} 调用
     */
    fun simpleInit(thisClass: SimpleTClass)

    /**
     * 简化内部初始化后开始流程调用
     */
    fun simpleStart(thisClass: SimpleTClass)

    /**
     * 简化预加载
     */
    fun simplePreLoad(thisClass: SimpleTClass)

    /**
     * 简化调用代码
     */
    fun simpleAgile(thisClass: SimpleTClass)

    // ==========
    // = 特殊简化 =
    // ==========

    /**
     * 简化页面 UI Theme 初始化
     */
    fun simpleUITheme(uiTheme: SimpleTUITheme): SimpleTUITheme
}