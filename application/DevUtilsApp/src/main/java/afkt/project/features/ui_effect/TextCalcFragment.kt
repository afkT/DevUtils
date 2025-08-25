package afkt.project.features.ui_effect

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectTextCalcBinding
import afkt.project.model.helper.RandomHelper
import android.content.Context
import android.graphics.Color
import androidx.appcompat.widget.AppCompatTextView
import dev.base.simple.extensions.asFragment
import dev.engine.extensions.toast.toast_showShort
import dev.utils.DevFinal
import dev.utils.app.TextViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.RandomUtils

/**
 * detail: 计算字体宽度、高度
 * @author Ttt
 */
class TextCalcFragment : AppFragment<FragmentUiEffectTextCalcBinding, AppViewModel>(
    R.layout.fragment_ui_effect_text_calc, simple_Agile = { frg ->
        frg.asFragment<TextCalcFragment> {
            for (i in 0..14) {
                val randomTextView = createTextView(
                    RandomHelper.randomText(100, 20),
                    binding.vidLl.context
                )
                binding.vidLl.addView(randomTextView)
                randomTextView.setOnClickListener { view ->
                    // 点击 View 获取信息
                    val textView = view as AppCompatTextView
                    val text = textView.text.toString()
                    val builder = StringBuilder()

                    builder.append("字体总数: ").append(text.length)
                        .append(DevFinal.SYMBOL.NEW_LINE)

                    builder.append("字体高度: ")
                        .append(TextViewUtils.getTextHeight(textView))
                        .append(DevFinal.SYMBOL.NEW_LINE)

                    builder.append("偏移高度: ")
                        .append(TextViewUtils.getTextTopOffsetHeight(textView))
                        .append(DevFinal.SYMBOL.NEW_LINE)

                    builder.append("字体宽度: ")
                        .append(TextViewUtils.getTextWidth(textView))
                        .append(DevFinal.SYMBOL.NEW_LINE)

                    builder.append("字体大小: ").append(textView.textSize)
                        .append(DevFinal.SYMBOL.NEW_LINE)

                    builder.append("计算字体大小: ").append(
                        TextViewUtils.reckonTextSizeByHeight(
                            TextViewUtils.getTextHeight(textView)
                        )
                    ).append(DevFinal.SYMBOL.NEW_LINE)

                    builder.append("计算行数: ").append(
                        TextViewUtils.calcTextLine(
                            textView,
                            textView.measuredWidth.toFloat()
                        )
                    )

                    val content = builder.toString()
                    toast_showShort(text = content)
                }
            }
        }
    }
)

/**
 * 创建随机 TextView
 * @param text 随机文案
 */
private fun createTextView(
    text: String,
    context: Context
): AppCompatTextView {
    return QuickHelper.get(AppCompatTextView(context))
        .setPadding(30)
        .setMarginTop(40)
        .setMarginBottom(20)
        .setTextColors(Color.BLACK)
        .setTextSizeBySp(RandomHelper.randomFloat(13, 20))
        .setBold(RandomUtils.nextBoolean())
        .setText(text).getView()
}