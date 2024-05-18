package afkt.project.feature.ui_effect.text_view

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.RouterPath
import androidx.viewbinding.ViewBinding
import com.therouter.router.Route

/**
 * detail: 两个 TextView 显示效果
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.TextViewActivity_PATH)
class TextViewActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_textview
}