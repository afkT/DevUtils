package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityShapeableImageViewBinding
import com.google.android.material.imageview.ShapeableImageView
import dev.utils.app.SizeUtils
import dev.utils.app.ViewUtils

/**
 * detail: Material ShapeableImageView
 * @author Ttt
 * <pre>
 *     描边需设置 padding 大小为描边宽度一半, 否则显示不全
 * </pre>
 */
class ShapeableImageViewActivity : BaseActivity<ActivityShapeableImageViewBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_shapeable_image_view

    override fun initValue() {
        super.initValue()
        val margin = SizeUtils.dipConvertPx(15f)
        for (i in 0..binding.vidAsivLinear.childCount) {
            val imageView = ViewUtils.getChildAt<ShapeableImageView>(
                binding.vidAsivLinear, i
            )
            ViewUtils.setMargin(imageView, 0, margin, 0, 0)
        }
        ViewUtils.setMarginBottom(
            ViewUtils.getChildAtLast<ShapeableImageView>(binding.vidAsivLinear),
            margin, false
        )
    }
}