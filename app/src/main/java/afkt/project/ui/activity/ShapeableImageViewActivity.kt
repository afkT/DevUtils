package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import androidx.viewbinding.ViewBinding

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * <pre>
 *     描边需设置 padding 大小为描边宽度一半, 否则显示不全
 * </pre>
 */
class ShapeableImageViewActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_shapeable_image_view
}