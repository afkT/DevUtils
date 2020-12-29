package dev.base.expand.mvp

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * detail: MVP Contract 类
 * @author Ttt
 */
class MVP private constructor() {

    /**
     * detail: 空实现 MVPView
     * @author Ttt
     */
    class ViewImpl : IView

    // =======
    // = MVP =
    // =======

    /**
     * detail: MVP 模式的 Model ( 通常作为获取数据 )
     * @author Ttt
     */
    interface IModel

    /**
     * detail: MVP 模式的 View ( 通过 Presenter 将数据传入到该层, 负责 View 的展示相关 )
     * @author Ttt
     */
    interface IView

    /**
     * detail: MVP 模式 P 层接口类
     * @author Ttt
     */
    interface IPresenter<V : IView> {

        /**
         * 设置 View 层与 P 层 关联持有
         * @param view [IView]
         */
        fun attachView(view: V)

        /**
         * 销毁 View 与 P 层 关联关系
         */
        fun detachView()
    }

    /**
     * detail: MVP 模式的指挥者 ( 连接 View 和 Model)
     * @author Ttt
     */
    open class Presenter<V : IView, M : IModel> : IPresenter<V>,
        DefaultLifecycleObserver {

        // 是否分离 MVP View
        private var detach = true

        constructor(view: V) : this(view, true)

        constructor(
            view: V,
            detach: Boolean
        ) {
            attachView(view)
            this.detach = detach
        }

        // IView
        @JvmField
        protected var mvpView: V? = null

        // IModel
        @JvmField
        protected var mvpModel: M? = null

        override fun attachView(view: V) {
            mvpView = view
        }

        override fun detachView() {
            mvpView = null
        }

        override fun onDestroy(owner: LifecycleOwner) {
            if (detach) detachView()
        }
    }
}