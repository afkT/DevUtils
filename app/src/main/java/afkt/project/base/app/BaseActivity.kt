package afkt.project.base.app

import androidx.viewbinding.ViewBinding
import dev.base.expand.mvp.MVP
import dev.base.expand.mvp.MVPViewImpl

/**
 * detail: Base ViewBinding 基类
 * @author Ttt
 */
abstract class BaseActivity<VB : ViewBinding> :
    BaseMVPActivity<MVP.Presenter<out MVP.IView, out MVP.IModel>, VB>() {

    override fun createPresenter(): MVP.Presenter<out MVP.IView, out MVP.IModel> {
        return MVP.Presenter(MVPViewImpl())
    }

    /**
     * 是否需要 ToolBar
     */
    override fun isToolBar(): Boolean {
        return false
    }
}