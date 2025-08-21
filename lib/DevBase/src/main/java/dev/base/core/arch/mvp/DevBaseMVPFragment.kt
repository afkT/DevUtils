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