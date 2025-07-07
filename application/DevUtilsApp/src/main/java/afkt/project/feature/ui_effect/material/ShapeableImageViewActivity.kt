package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityShapeableImageViewBinding

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * 描边需设置 padding 大小为描边宽度一半, 否则显示不全
 */
class ShapeableImageViewActivity :
    BaseProjectActivity<ActivityShapeableImageViewBinding, AppViewModel>(
        R.layout.activity_shapeable_image_view
    )