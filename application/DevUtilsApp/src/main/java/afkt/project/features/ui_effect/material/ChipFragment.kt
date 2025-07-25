package afkt.project.features.ui_effect.material

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectMaterialChipBinding
import afkt.project.model.helper.RandomHelper
import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dev.simple.app.base.asFragment
import dev.utils.app.StateListUtils
import dev.utils.app.ViewUtils
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
class ChipFragment : AppFragment<FragmentUiEffectMaterialChipBinding, ChipViewModel>(
    R.layout.fragment_ui_effect_material_chip, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<ChipFragment> {
            viewModel.clickRefresh = View.OnClickListener { view ->
                viewModel.refreshView(binding.vidGroup)
            }
            viewModel.refreshView(binding.vidGroup)
        }
    }
)

class ChipViewModel : AppViewModel() {

    var clickRefresh = View.OnClickListener { view -> }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 刷新 View
     */
    fun refreshView(chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        for (i in 1..20) {
            val chip = ViewUtils.inflate(
                chipGroup.context, R.layout.include_material_chip
            ) as? Chip
            chip?.run {
                // 随机颜色
                val pressed = ColorUtils.getRandomColorString()
                val normal = ColorUtils.getRandomColorString()

                chip.text = "$i." + RandomHelper.randomText(8, 5)
                chip.chipBackgroundColor = StateListUtils.createColorStateList(
                    pressed, normal
                )
                chipGroup.addView(this)
            }
        }
    }
}