package afkt.project.feature.ui_effect.textview

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * detail: 两个 TextView 显示效果
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.TextViewActivity_PATH)
class TextViewActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_textview
}