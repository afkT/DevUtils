package afkt.project.base.app

import androidx.viewbinding.ViewBinding
import dev.base.expand.mvp.MVP

/**
 * detail: Base 基类
 * @author Ttt
 */
abstract class TempBaseActivity<VB : ViewBinding> :
    BaseMVPActivity<MVP.Presenter<out MVP.IView, out MVP.IModel>, VB>() {

    override fun createPresenter(): MVP.Presenter<out MVP.IView, out MVP.IModel> {
        return MVP.Presenter(MVPView())
    }

    // 空实现 MVPView
    class MVPView : MVP.IView

    /**
     * 是否需要 ToolBar
     */
    override fun isToolBar(): Boolean {
        return false
    }
}