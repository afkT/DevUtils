package afkt.project.feature.ui_effect.text_calc

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityTextCalcBinding
import android.graphics.Color
import android.view.View
import com.therouter.router.Route
import androidx.appcompat.widget.AppCompatTextView
import dev.utils.app.TextViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 计算字体宽度、高度
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.TextCalcActivity_PATH)
class TextCalcActivity : BaseProjectActivity<ActivityTextCalcBinding, BaseProjectViewModel>(
    R.layout.activity_text_calc, simple_Agile = {
        if (it is TextCalcActivity) {
            it.apply {
                for (i in 0..14) {
                    val text = ChineseUtils.randomWord(RandomUtils.getRandom(100)) +
                            RandomUtils.getRandomLetters(RandomUtils.getRandom(20))

                    val randomText = RandomUtils.getRandom(text.toCharArray(), text.length)
                    val view = QuickHelper.get(AppCompatTextView(this))
                        .setPadding(30)
                        .setMarginTop(40)
                        .setMarginBottom(20)
                        .setTextColors(Color.BLACK)
                        .setTextSizeBySp(RandomUtils.getRandom(13, 20).toFloat())
                        .setBold(RandomUtils.nextBoolean())
                        .setText(randomText).setOnClick { v ->
                            val textView = v as AppCompatTextView
                            val text = textView.text.toString()
                            val builder = StringBuilder()
                            builder.append("字体总数: ").append(text.length)
                            builder.append("\n字体高度: ")
                                .append(TextViewUtils.getTextHeight(textView))
                            builder.append("\n偏移高度: ")
                                .append(TextViewUtils.getTextTopOffsetHeight(textView))
                            builder.append("\n字体宽度: ")
                                .append(TextViewUtils.getTextWidth(textView))
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
                        }.getView<View>()
                    binding.vidLl.addView(view)
                }
            }
        }
    }
)