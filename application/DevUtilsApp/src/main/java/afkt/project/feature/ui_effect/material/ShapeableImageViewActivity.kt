package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityShapeableImageViewBinding
import com.therouter.router.Route

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * 描边需设置 padding 大小为描边宽度一半, 否则显示不全
 */
@Route(path = RouterPath.UI_EFFECT.ShapeableImageViewActivity_PATH)
class ShapeableImageViewActivity :
    BaseProjectActivity<ActivityShapeableImageViewBinding, BaseProjectViewModel>(
        R.layout.activity_shapeable_image_view
    )