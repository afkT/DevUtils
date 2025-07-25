package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityChipBinding
import afkt.project.model.helper.RandomHelper
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.chip.Chip
import dev.utils.app.ResourceUtils
import dev.utils.app.StateListUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ColorUtils

/**
 * detail: Material Chip、ChipGroups、ChipDrawable
 * @author Ttt
 * Google Chips
 * @see https://material.io/components/chips
 * Android : Chip、ChipGroups、ChipDrawable
 * @see https://blog.csdn.net/north1989/article/details/81878653
 * 注意事项:
 * Activity 需要设置为 Theme.MaterialComponents 主题
 */
class ChipActivity : BaseProjectActivity<ActivityChipBinding, AppViewModel>(
    R.layout.activity_chip, simple_Agile = {
        if (it is ChipActivity) {
            it.apply {
                val view = QuickHelper.get(AppCompatTextView(this))
                    .setText("刷新")
                    .setBold()
                    .setTextColors(ResourceUtils.getColor(R.color.red))
                    .setTextSizeBySp(15.0F)
                    .setPaddingLeft(30)
                    .setPaddingRight(30)
                    .setOnClick { initValue() }.getView<View>()
                toolbar?.addView(view)
            }
        }
    }
) {

    override fun initValue() {
        super.initValue()
        binding.vidGroup.removeAllViews()

        for (i in 1..20) {
            val chip = ViewUtils.inflate(this, R.layout.include_chip) as? Chip
            chip?.run {
                // 随机颜色
                val pressed = ColorUtils.getRandomColorString()
                val normal = ColorUtils.getRandomColorString()

                chip.text = "$i." + RandomHelper.randomText(8, 5)
                chip.chipBackgroundColor = StateListUtils.createColorStateList(
                    pressed, normal
                )
                binding.vidGroup.addView(this)
            }
        }
    }
}