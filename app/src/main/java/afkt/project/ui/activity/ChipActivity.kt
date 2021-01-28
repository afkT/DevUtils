package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityChipBinding
import android.os.Bundle
import android.view.View
import com.google.android.material.chip.Chip
import dev.base.widget.BaseTextView
import dev.utils.app.ResourceUtils
import dev.utils.app.StateListUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.QuickHelper
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
class ChipActivity : BaseActivity<ActivityChipBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_chip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = QuickHelper.get(BaseTextView(this))
            .setText("刷新")
            .setBold()
            .setTextColor(ResourceUtils.getColor(R.color.red))
            .setTextSizeBySp(15.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClicks { initValue() }.getView<View>()
        toolbar?.addView(view)
    }

    override fun initValue() {
        super.initValue()

        binding.vidAcGroup.removeAllViews()

        for (i in 1..20) {
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(8)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(5))
            val randomText =
                i.toString() + "." + RandomUtils.getRandom(text.toCharArray(), text.length)

            var chip = ViewUtils.inflate(this, R.layout.inflate_chip) as? Chip
            chip?.run {
                // 随机颜色
                var pressed = ColorUtils.getRandomColorString()
                var normal = ColorUtils.getRandomColorString()

                chip.text = randomText
                chip.chipBackgroundColor = StateListUtils.createColorStateList(pressed, normal)
                binding.vidAcGroup.addView(this)
            }
        }
    }
}