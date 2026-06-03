package dev.engine.core.toast

import android.app.Application
import com.hjq.toast.ToastParams
import com.hjq.toast.Toaster
import com.hjq.toast.config.IToastInterceptor
import com.hjq.toast.config.IToastStrategy
import com.hjq.toast.config.IToastStyle
import dev.engine.toast.IToastEngine

/**
 * detail: 全局 Toast Config
 * @author Ttt
 */
open class ToastConfig : IToastEngine.EngineConfig {
    // Toast 处理策略
    var strategy: IToastStrategy? = null

    // Toast 拦截器
    var interceptor: IToastInterceptor? = null

    // Toast 样式
    var style: IToastStyle<*>? = null

    constructor(
        strategy: IToastStrategy?,
        interceptor: IToastInterceptor?,
        style: IToastStyle<*>?
    ) {
        this.strategy = strategy
        this.interceptor = interceptor
        this.style = style
    }
}

/**
 * detail: Toast ( Data、Params ) Item
 * @author Ttt
 */
open class ToastItem : IToastEngine.EngineItem {

    val params: ToastParams

    constructor(params: ToastParams) {
        this.params = params
    }
}

/**
 * detail: Toaster Toast Engine 实现
 * @author Ttt
 */
open class ToasterEngineImpl() : IToastEngine<ToastConfig, ToastItem> {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param application Application
     */
    override fun initialize(application: Application) {
        Toaster.init(application)
    }

    /**
     * 判断 Toast 框架是否已经初始化
     * @return `true` yes, `false` no
     */
    override fun isInit(): Boolean {
        return Toaster.isInit()
    }

    /**
     * 设置是否为调试模式
     * @param debug 是否为调试模式
     */
    override fun setDebugMode(debug: Boolean) {
        Toaster.setDebugMode(debug)
    }

    /**
     * 设置 Toast Config
     * @param config Toast Config
     */
    override fun setConfig(config: ToastConfig?) {
        config?.let {
            // 设置全局的 Toast 处理策略
            Toaster.setStrategy(it.strategy)
            // 设置全局的 Toast 拦截器
            Toaster.setInterceptor(it.interceptor)
            // 设置全局的 Toast 样式
            Toaster.setStyle(it.style)
        }
    }

    /**
     * 获取 Toast Config
     * @return Toast Config
     */
    override fun getConfig(): ToastConfig {
        return ToastConfig(
            Toaster.getStrategy(),
            Toaster.getInterceptor(),
            Toaster.getStyle()
        )
    }

    // =============
    // = Toast 方法 =
    // =============

    /**
     * 取消 Toast 的显示
     */
    override fun cancel() {
        Toaster.cancel()
    }

    /**
     * 延迟显示 Toast
     * @param id 字符串资源 ID
     * @param delayMillis 延迟的毫秒数
     */
    override fun delayedShow(
        id: Int,
        delayMillis: Long
    ) {
        Toaster.delayedShow(id, delayMillis)
    }

    /**
     * 延迟显示 Toast
     * @param obj 要显示的对象
     * @param delayMillis 延迟的毫秒数
     */
    override fun delayedShow(
        obj: Any?,
        delayMillis: Long
    ) {
        Toaster.delayedShow(obj, delayMillis)
    }

    /**
     * 延迟显示 Toast
     * @param text 要显示的文本
     * @param delayMillis 延迟的毫秒数
     */
    override fun delayedShow(
        text: CharSequence?,
        delayMillis: Long
    ) {
        Toaster.delayedShow(text, delayMillis)
    }

    /**
     * debug 模式下显示 Toast
     * @param id 字符串资源 ID
     */
    override fun debugShow(id: Int) {
        Toaster.debugShow(id)
    }

    /**
     * debug 模式下显示 Toast
     * @param obj 要显示的对象
     */
    override fun debugShow(obj: Any?) {
        Toaster.debugShow(obj)
    }

    /**
     * debug 模式下显示 Toast
     * @param text 要显示的文本
     */
    override fun debugShow(text: CharSequence?) {
        Toaster.debugShow(text)
    }

    /**
     * 显示一个短 Toast
     * @param id 字符串资源 ID
     */
    override fun showShort(id: Int) {
        Toaster.showShort(id)
    }

    /**
     * 显示一个短 Toast
     * @param obj 要显示的对象
     */
    override fun showShort(obj: Any?) {
        Toaster.showShort(obj)
    }

    /**
     * 显示一个短 Toast
     * @param text 要显示的文本
     */
    override fun showShort(text: CharSequence?) {
        Toaster.showShort(text)
    }

    /**
     * 显示一个长 Toast
     * @param id 字符串资源 ID
     */
    override fun showLong(id: Int) {
        Toaster.showLong(id)
    }

    /**
     * 显示一个长 Toast
     * @param obj 要显示的对象
     */
    override fun showLong(obj: Any?) {
        Toaster.showLong(obj)
    }

    /**
     * 显示一个长 Toast
     * @param text 要显示的文本
     */
    override fun showLong(text: CharSequence?) {
        Toaster.showLong(text)
    }

    /**
     * 显示 Toast
     * @param id 字符串资源 ID
     */
    override fun show(id: Int) {
        Toaster.show(id)
    }

    /**
     * 显示 Toast
     * @param obj 要显示的对象
     */
    override fun show(obj: Any?) {
        Toaster.show(obj)
    }

    /**
     * 显示 Toast
     * @param text 要显示的文本
     */
    override fun show(text: CharSequence?) {
        Toaster.show(text)
    }

    /**
     * 显示 Toast
     * @param item Toast 参数
     */
    override fun show(item: ToastItem?) {
        item?.let {
            Toaster.show(it.params)
        }
    }

    // ============
    // = Toast UI =
    // ============

    /**
     * 给当前 Toast 设置新的布局
     * @param id 布局资源 ID
     */
    override fun setView(id: Int) {
        Toaster.setView(id)
    }

    /**
     * 设置 Toast 的位置
     * @param gravity 重心
     */
    override fun setGravity(gravity: Int) {
        Toaster.setGravity(gravity)
    }

    /**
     * 设置 Toast 的位置
     * @param gravity 重心
     * @param xOffset X 轴偏移量
     * @param yOffset Y 轴偏移量
     */
    override fun setGravity(
        gravity: Int,
        xOffset: Int,
        yOffset: Int
    ) {
        Toaster.setGravity(gravity, xOffset, yOffset)
    }

    /**
     * 设置 Toast 的位置
     * @param gravity 重心
     * @param xOffset X 轴偏移量
     * @param yOffset Y 轴偏移量
     * @param horizontalMargin 水平边距
     * @param verticalMargin 垂直边距
     */
    override fun setGravity(
        gravity: Int,
        xOffset: Int,
        yOffset: Int,
        horizontalMargin: Float,
        verticalMargin: Float
    ) {
        Toaster.setGravity(gravity, xOffset, yOffset, horizontalMargin, verticalMargin)
    }
}