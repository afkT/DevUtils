package afkt.project.feature.dev_widget.line_view

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.ActivityLineBinding
import android.graphics.Color
import com.therouter.router.Route
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 换行监听 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.LineActivity_PATH)
class LineActivity : BaseProjectActivity<ActivityLineBinding, AppViewModel>(
    R.layout.activity_line, simple_Agile = {
        if (it is LineActivity) {
            it.apply {
                // 设置监听
                binding.vidContentTv.setNewLineCallback { isNewLine, line ->
                    val builder = StringBuilder()
                    builder.append("是否换行: ").append(isNewLine)
                    builder.append("\n换行数量: ").append(line)
                    binding.vidTv.text = builder.toString()
                }
                binding.vidContentTv.setOnClickListener { // 随机字符串
                    val text = ChineseUtils.randomWord(RandomUtils.getRandom(300)) +
                            RandomUtils.getRandomLetters(RandomUtils.getRandom(50))
                    val randomText = RandomUtils.getRandom(text.toCharArray(), text.length)
                    // 设置内容
                    QuickHelper.get(binding.vidContentTv)
                        .setTextColors(Color.BLACK)
                        .setTextSizeBySp(RandomUtils.getRandom(13, 25).toFloat())
                        .setBold(RandomUtils.nextBoolean())
                        .setText(randomText)
                }
                // 默认点击
                binding.vidContentTv.performClick()
            }
        }
    }
)