package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetLineTextBinding
import afkt.project.model.helper.RandomHelper
import android.graphics.Color
import android.view.View
import androidx.databinding.ObservableField
import dev.base.simple.extensions.asFragment
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.RandomUtils
import dev.widget.function.LineTextView

/**
 * detail: TextView 换行监听
 * @author Ttt
 */
class LineTextFragment : AppFragment<FragmentDevWidgetLineTextBinding, LineTextViewModel>(
    R.layout.fragment_dev_widget_line_text, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<LineTextFragment> {
            viewModel.initialize(binding.vidTv)
        }
    }
)

class LineTextViewModel : AppViewModel() {

    // 换行信息文本
    val infoText = ObservableField<String>("")

    // 随机生成文本
    val contentText = ObservableField<String>("")

    // 点击内容文本随机生成
    val clickContent = View.OnClickListener { view ->
        randomText()
        randomConfig(view)
    }

    // ==========
    // = 内部方法 =
    // ==========

    private fun randomText() {
        contentText.set(RandomHelper.randomText(300, 50))
    }

    /**
     * 随机更新配置
     * @param view View
     */
    private fun randomConfig(view: View) {
        QuickHelper.get(view)
            .setTextColors(Color.BLACK)
            .setTextSizeBySp(RandomHelper.randomFloat(13, 25))
            .setBold(RandomUtils.nextBoolean())
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 初始化
     */
    fun initialize(view: LineTextView) {
        // 设置换行监听回调
        view.setNewLineCallback { isNewLine, line ->
            val builder = StringBuilder()
            builder.append("是否换行: ").append(isNewLine)
            builder.append("\n换行数量: ").append(line)
            infoText.set(builder.toString())
        }
        // 默认随机生成文本
        randomText()
    }
}