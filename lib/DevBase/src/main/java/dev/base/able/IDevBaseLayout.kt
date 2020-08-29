package dev.base.able

import android.view.View

/**
 * detail: 基类 Layout View 相关方法
 * @author Ttt
 */
interface IDevBaseLayout {

    /**
     * 获取 Layout Id ( 用于 contentLinear addView )
     * @return Layout Id
     */
    fun baseLayoutId(): Int

    /**
     * 获取 Layout View ( 用于 contentLinear addView )
     * @return Layout View
     */
    fun baseLayoutView(): View?

    /**
     * Layout View addView 是否 LayoutParams.MATCH_PARENT
     */
    fun isLayoutMatchParent(): Boolean {
        return true
    }
}