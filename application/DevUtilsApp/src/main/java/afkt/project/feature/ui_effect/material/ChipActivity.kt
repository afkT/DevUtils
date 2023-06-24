package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityChipBinding
import afkt.project.model.item.RouterPath
import android.os.Bundle
import android.view.View
import com.google.android.material.chip.Chip
import com.therouter.router.Route
import dev.base.widget.BaseTextView
import dev.utils.app.ResourceUtils
import dev.utils.app.StateListUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import java.util.*

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
@Route(path = RouterPath.UI_EFFECT.ChipActivity_PATH)
class ChipActivity : BaseActivity<ActivityChipBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = QuickHelper.get(BaseTextView(this))
            .setText("刷新")
            .setBold()
            .setTextColors(ResourceUtils.getColor(R.color.red))
            .setTextSizeBySp(15.0F)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClick { initValue() }.getView<View>()
        toolbar?.addView(view)
    }

    override fun initValue() {
        super.initValue()

        binding.vidGroup.removeAllViews()

        for (i in 1..20) {
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(8)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(5))
            val randomText =
                i.toString() + "." + RandomUtils.getRandom(text.toCharArray(), text.length)

            val chip = ViewUtils.inflate(this, R.layout.include_chip) as? Chip
            chip?.run {
                // 随机颜色
                val pressed = ColorUtils.getRandomColorString()
                val normal = ColorUtils.getRandomColorString()

                chip.text = randomText
                chip.chipBackgroundColor = StateListUtils.createColorStateList(pressed, normal)
                binding.vidGroup.addView(this)
            }
        }
    }
}