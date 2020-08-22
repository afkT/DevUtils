package dev.base.expand.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.base.fragment.DevBaseFragment

/**
 * detail: MVP Fragment 基类
 * @author Ttt
 * 需要自己实现 Contract ( 契约类 ) 用来管理 View 与 Presenter 的交互
 */
abstract class DevBaseMVPFragment<P : MVP.Presenter<out MVP.IView, out MVP.IModel>> :
    DevBaseFragment() {

    // MVP Presenter
    lateinit var mPresenter: P

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 创建 MVP 模式的 Presenter
        mPresenter = createPresenter()
        // 底层初始化操作
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 取消 MVP 各个模块间的关联
        mPresenter.detachView()
    }

    /**
     * 初始化创建 Presenter
     * @return [MVP.Presenter]
     */
    abstract fun createPresenter(): P
}