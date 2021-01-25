package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import androidx.viewbinding.ViewBinding

/**
 * detail: 两个 TextView 显示效果
 * @author Ttt
 */
class TextViewActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_textview
}