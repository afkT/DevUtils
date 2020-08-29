package dev.base.expand.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.expand.viewbinding.DevBaseViewBindingFragment

/**
 * detail: MVP Fragment ViewBinding 基类
 * @author Ttt
 */
abstract class DevBaseMVPViewBindingFragment<P : MVP.Presenter<out MVP.IView, out MVP.IModel>, VB : ViewBinding> :
    DevBaseViewBindingFragment<VB>() {

    // MVP Presenter
    lateinit var presenter: P

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 创建 MVP 模式的 Presenter
        presenter = createPresenter()
        // 底层初始化操作
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 取消 MVP 各个模块间的关联
        presenter.detachView()
    }

    /**
     * 初始化创建 Presenter
     * @return [MVP.Presenter]
     */
    abstract fun createPresenter(): P
}