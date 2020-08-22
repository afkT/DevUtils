package dev.base.expand.mvp

import android.os.Bundle
import dev.base.activity.DevBaseActivity

/**
 * detail: MVP Activity 基类
 * @author Ttt
 * 需要自己实现 Contract ( 契约类 ) 用来管理 View 与 Presenter 的交互
 */
abstract class DevBaseMVPActivity<P : MVP.Presenter<out MVP.IView, out MVP.IModel>> :
    DevBaseActivity() {

    // MVP Presenter
    lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        // 创建 MVP 模式的 Presenter
        mPresenter = createPresenter()
        // 初始化操作
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消 MVP 各个模块间的关联
        mPresenter.detachView()
    }

    /**
     * 初始化创建 Presenter
     * @return [MVP.Presenter]
     */
    abstract fun createPresenter(): P
}