package dev.simple.app.controller.ui.theme

import android.content.Intent
import android.os.Bundle
import dev.base.DevIntent
import dev.utils.common.ConvertUtils

/**
 * detail: 通用基础样式 Intent 传参读写辅助类
 * @author Ttt
 */
abstract class BaseUITheme<T> : BaseUIThemeIntent<T>() {

    // =============
    // = 对外公开方法 =
    // =============

    fun isContentAssistSafe(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isContentAssistSafe))
    }

    fun setContentAssistSafe(value: Boolean): T {
        return put(UIThemeKey.isContentAssistSafe, value.toString())
    }

    fun removeContentAssistSafe(): T {
        return remove(UIThemeKey.isContentAssistSafe)
    }

    // ===========
    // = Base UI =
    // ===========

    fun isStatusBarFrame(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isStatusBarFrame))
    }

    fun setStatusBarFrame(value: Boolean): T {
        return put(UIThemeKey.isStatusBarFrame, value.toString())
    }

    fun removeStatusBarFrame(): T {
        return remove(UIThemeKey.isStatusBarFrame)
    }

    // =

    fun isLightMode(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isLightMode))
    }

    fun setLightMode(value: Boolean): T {
        return put(UIThemeKey.isLightMode, value.toString())
    }

    fun removeLightMode(): T {
        return remove(UIThemeKey.isLightMode)
    }

    // =

    fun isAddStatusBar(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isAddStatusBar))
    }

    fun setAddStatusBar(value: Boolean): T {
        return put(UIThemeKey.isAddStatusBar, value.toString())
    }

    fun removeAddStatusBar(): T {
        return remove(UIThemeKey.isAddStatusBar)
    }

    // =

    fun isAddTitleBar(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isAddTitleBar))
    }

    fun setAddTitleBar(value: Boolean): T {
        return put(UIThemeKey.isAddTitleBar, value.toString())
    }

    fun removeAddTitleBar(): T {
        return remove(UIThemeKey.isAddTitleBar)
    }

    // ===============
    // = Window Flag =
    // ===============

    fun isFlagFullScreen(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isFlagFullScreen))
    }

    fun setFlagFullScreen(value: Boolean): T {
        return put(UIThemeKey.isFlagFullScreen, value.toString())
    }

    fun removeFlagFullScreen(): T {
        return remove(UIThemeKey.isFlagFullScreen)
    }

    // =

    fun isFlagKeepScreen(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isFlagKeepScreen))
    }

    fun setFlagKeepScreen(value: Boolean): T {
        return put(UIThemeKey.isFlagKeepScreen, value.toString())
    }

    fun removeFlagKeepScreen(): T {
        return remove(UIThemeKey.isFlagKeepScreen)
    }

    // =

    fun isFlagSecure(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isFlagSecure))
    }

    fun setFlagSecure(value: Boolean): T {
        return put(UIThemeKey.isFlagSecure, value.toString())
    }

    fun removeFlagSecure(): T {
        return remove(UIThemeKey.isFlagSecure)
    }

    // ==================
    // = Window Feature =
    // ==================

    fun isFeatureNoTitle(): Boolean {
        return ConvertUtils.toBoolean(get(UIThemeKey.isFeatureNoTitle))
    }

    fun setFeatureNoTitle(value: Boolean): T {
        return put(UIThemeKey.isFeatureNoTitle, value.toString())
    }

    fun removeFeatureNoTitle(): T {
        return remove(UIThemeKey.isFeatureNoTitle)
    }
}

/**
 * detail: 通用基础样式 Intent 传参读写辅助类
 * @author Ttt
 */
abstract class BaseUIThemeIntent<T> {

    /**
     * 返回 T 链式调用
     * @return [T]
     */
    abstract fun returnT(): T

    // =============
    // = 对外公开方法 =
    // =============

    // Intent 传参读写辅助类
    private val mIntent = DevIntent.with()

    /**
     * 插入数据
     * @param intent [Intent]
     * @return [Intent]
     */
    fun insert(intent: Intent?): Intent? {
        return mIntent.insert(intent)
    }

    /**
     * 插入数据
     * @return [Bundle]
     */
    fun insert(): Bundle? {
        return mIntent.insert()
    }

    /**
     * 插入数据
     * @param bundle [Bundle]
     * @return [Bundle]
     */
    fun insert(bundle: Bundle?): Bundle? {
        return mIntent.insert(bundle)
    }

    // =

    /**
     * 读取数据并存储
     * @param intent [Intent]
     * @return [T]
     */
    fun reader(intent: Intent?): T {
        mIntent.reader(intent)
        return returnT()
    }

    /**
     * 读取数据并存储
     * @param bundle [Bundle]
     * @return [T]
     */
    fun reader(bundle: Bundle?): T {
        mIntent.reader(bundle)
        return returnT()
    }

    // =======
    // = 通用 =
    // =======

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @return [T]
     */
    fun put(
        key: String?,
        value: String?
    ): T {
        mIntent.put(key, value)
        return returnT()
    }

    /**
     * 移除数据
     * @param key 保存的 key
     * @return [T]
     */
    fun remove(key: String?): T {
        mIntent.remove(key)
        return returnT()
    }

    /**
     * 获取对应 Key 保存的 Value
     * @param key 保存的 key
     * @return 保存的 value
     */
    fun get(key: String?): String? {
        return mIntent.get(key)
    }
}