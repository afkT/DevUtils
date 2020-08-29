package dev.base.able

import android.view.View

/**
 * detail: 基类 Content View 相关方法
 * @author Ttt
 */
interface IDevBaseContent {

    /**
     * 获取 Content Layout Id
     * @return Content Layout Id
     */
    fun baseContentId(): Int

    /**
     * 获取 Content Layout View
     * @return Content Layout View
     */
    fun baseContentView(): View?
}