package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityLineBinding
import android.graphics.Color
import dev.utils.app.helper.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 换行监听 View
 * @author Ttt
 */
class LineActivity : BaseActivity<ActivityLineBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_line

    override fun initValue() {
        super.initValue()

        // 设置监听
        binding.vidAlContentTv.setNewLineCallback { isNewLine, line ->
            val builder = StringBuilder()
            builder.append("是否换行: ").append(isNewLine)
            builder.append("\n换行数量: ").append(line)
            binding.vidAlTv.text = builder.toString()
        }
        binding.vidAlContentTv.setOnClickListener { // 随机字符串
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(300)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(50))
            val randomText = RandomUtils.getRandom(text.toCharArray(), text.length)
            // 设置内容
            QuickHelper.get(binding.vidAlContentTv)
                .setTextColor(Color.BLACK)
                .setTextSizeBySp(RandomUtils.getRandom(13, 25).toFloat())
                .setBold(RandomUtils.nextBoolean())
                .setText(randomText)
        }
        // 默认点击
        binding.vidAlContentTv.performClick()
    }
}