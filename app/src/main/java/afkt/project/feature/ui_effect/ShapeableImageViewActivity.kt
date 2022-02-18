package afkt.project.feature.ui_effect

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * <pre>
 *     描边需设置 padding 大小为描边宽度一半, 否则显示不全
 * </pre>
 */
@Route(path = RouterPath.UI_EFFECT.ShapeableImageViewActivity_PATH)
class ShapeableImageViewActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_shapeable_image_view
}