package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.RouterPath
import androidx.viewbinding.ViewBinding
import com.therouter.router.Route

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * 描边需设置 padding 大小为描边宽度一半, 否则显示不全
 */
@Route(path = RouterPath.UI_EFFECT.ShapeableImageViewActivity_PATH)
class ShapeableImageViewActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_shapeable_image_view
}