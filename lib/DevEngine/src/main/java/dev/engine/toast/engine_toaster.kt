package dev.engine.toast

import android.app.Application
import com.hjq.toast.ToastParams
import com.hjq.toast.Toaster
import com.hjq.toast.config.IToastInterceptor
import com.hjq.toast.config.IToastStrategy
import com.hjq.toast.config.IToastStyle

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

    override fun initialize(application: Application) {
        Toaster.init(application)
    }

    override fun isInit(): Boolean {
        return Toaster.isInit()
    }

    override fun setDebugMode(debug: Boolean) {
        Toaster.setDebugMode(debug)
    }

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

    override fun cancel() {
        Toaster.cancel()
    }

    override fun delayedShow(
        id: Int,
        delayMillis: Long
    ) {
        Toaster.delayedShow(id, delayMillis)
    }

    override fun delayedShow(
        obj: Any?,
        delayMillis: Long
    ) {
        Toaster.delayedShow(obj, delayMillis)
    }

    override fun delayedShow(
        text: CharSequence?,
        delayMillis: Long
    ) {
        Toaster.delayedShow(text, delayMillis)
    }

    override fun debugShow(id: Int) {
        Toaster.debugShow(id)
    }

    override fun debugShow(obj: Any?) {
        Toaster.debugShow(obj)
    }

    override fun debugShow(text: CharSequence?) {
        Toaster.debugShow(text)
    }

    override fun showShort(id: Int) {
        Toaster.showShort(id)
    }

    override fun showShort(obj: Any?) {
        Toaster.showShort(obj)
    }

    override fun showShort(text: CharSequence?) {
        Toaster.showShort(text)
    }

    override fun showLong(id: Int) {
        Toaster.showLong(id)
    }

    override fun showLong(obj: Any?) {
        Toaster.showLong(obj)
    }

    override fun showLong(text: CharSequence?) {
        Toaster.showLong(text)
    }

    override fun show(id: Int) {
        Toaster.show(id)
    }

    override fun show(obj: Any?) {
        Toaster.show(obj)
    }

    override fun show(text: CharSequence?) {
        Toaster.show(text)
    }

    override fun show(item: ToastItem?) {
        item?.let {
            Toaster.show(it.params)
        }
    }

    // ============
    // = Toast UI =
    // ============

    override fun setView(id: Int) {
        Toaster.setView(id)
    }

    override fun setGravity(gravity: Int) {
        Toaster.setGravity(gravity)
    }

    override fun setGravity(
        gravity: Int,
        xOffset: Int,
        yOffset: Int
    ) {
        Toaster.setGravity(gravity, xOffset, yOffset)
    }

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