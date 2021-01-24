package dev.base.expand.content

import android.os.Bundle
import dev.base.expand.mvp.MVP

/**
 * detail: Content MVP Activity 基类
 * @author Ttt
 */
abstract class DevBaseContentMVPActivity<P : MVP.Presenter<out MVP.IView, out MVP.IModel>> :
    DevBaseContentActivity() {

    // MVP Presenter
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        // 创建 MVP 模式的 Presenter
        presenter = createPresenter()
        // lifecycle
        lifecycle.addObserver(presenter)
        // 初始化操作
        super.onCreate(savedInstanceState)
    }

    /**
     * 初始化创建 Presenter
     * @return [MVP.Presenter]
     */
    abstract fun createPresenter(): P
}