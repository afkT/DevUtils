package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityTextCalcBinding
import android.graphics.Color
import dev.base.widget.BaseTextView
import dev.utils.app.TextViewUtils
import dev.utils.app.helper.QuickHelper
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 计算字体宽度、高度
 * @author Ttt
 */
class TextCalcActivity : BaseActivity<ActivityTextCalcBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_text_calc

    override fun initValue() {
        super.initValue()
        for (i in 0..14) {
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(100)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(20))

            val randomText = RandomUtils.getRandom(text.toCharArray(), text.length)
            val view: BaseTextView = QuickHelper.get(BaseTextView(this))
                .setPadding(30)
                .setMarginTop(40)
                .setMarginBottom(20)
                .setTextColor(Color.BLACK)
                .setTextSizeBySp(RandomUtils.getRandom(13, 20).toFloat())
                .setBold(RandomUtils.nextBoolean())
                .setText(randomText).setOnClicks { v ->
                    val textView = v as BaseTextView
                    val text = textView.text.toString()
                    val builder = StringBuilder()
                    builder.append("字体总数: ").append(text.length)
                    builder.append("\n字体高度: ").append(TextViewUtils.getTextHeight(textView))
                    builder.append("\n偏移高度: ")
                        .append(TextViewUtils.getTextTopOffsetHeight(textView))
                    builder.append("\n字体宽度: ").append(TextViewUtils.getTextWidth(textView))
                    builder.append("\n字体大小: ").append(textView.textSize)
                    builder.append("\n计算字体大小: ").append(
                        TextViewUtils.reckonTextSizeByHeight(
                            TextViewUtils.getTextHeight(textView)
                        )
                    )
                    builder.append("\n计算行数: ").append(
                        TextViewUtils.calcTextLine(
                            textView,
                            textView.measuredWidth.toFloat()
                        )
                    )
                    val content = builder.toString()
                    ToastTintUtils.normal(content)
                }.getView()
            binding.vidAtcLinear.addView(view)
        }
    }
}