package dev.base.core.arch.mvp

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import dev.base.core.arch.databinding.DevBaseVDBFragment

/**
 * detail: MVP Fragment 基类
 * @author Ttt
 */
abstract class DevBaseMVPFragment<VDB : ViewDataBinding, P : MVP.Presenter<out MVP.IView, out MVP.IModel>> :
    DevBaseVDBFragment<VDB>() {

    // MVP Presenter
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        setupPresenter()
        super.onCreate(savedInstanceState)
    }

    /**
     * 初始化创建 Presenter
     * @return [MVP.Presenter]
     */
    abstract fun createPresenter(): P

    // ============
    // = protected =
    // ============

    /**
     * 创建 [presenter] 并注册到 [lifecycle]；在 [super.onCreate] 之前执行以保持与原顺序一致。
     * 子类可重写以调整注册时机或包装 Presenter，无需复制整段 [onCreate]。
     */
    protected open fun setupPresenter() {
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
    }
}