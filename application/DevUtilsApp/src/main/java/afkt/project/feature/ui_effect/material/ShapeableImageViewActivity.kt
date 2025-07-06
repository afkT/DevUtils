package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityShapeableImageViewBinding
import afkt.project.model.data.button.RouterPath
import com.therouter.router.Route

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * 描边需设置 padding 大小为描边宽度一半, 否则显示不全
 */
@Route(path = RouterPath.UI_EFFECT.ShapeableImageViewActivity_PATH)
class ShapeableImageViewActivity :
    BaseProjectActivity<ActivityShapeableImageViewBinding, AppViewModel>(
        R.layout.activity_shapeable_image_view
    )