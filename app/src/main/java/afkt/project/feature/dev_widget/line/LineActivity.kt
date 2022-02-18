package afkt.project.feature.dev_widget.line

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityLineBinding
import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 换行监听 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.LineActivity_PATH)
class LineActivity : BaseActivity<ActivityLineBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_line

    override fun initValue() {
        super.initValue()

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