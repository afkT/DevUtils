package afkt.project.features.ui_effect.material

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectMaterialShapeableImageViewBinding

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * 描边需设置 padding 大小为描边宽度一半, 否则显示不全
 */
class ShapeableImageViewFragment : AppFragment<FragmentUiEffectMaterialShapeableImageViewBinding, AppViewModel>(
    R.layout.fragment_ui_effect_material_shapeable_image_view
)