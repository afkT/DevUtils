package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetWrapViewBinding
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import dev.simple.app.base.asFragment
import dev.utils.app.ResourceUtils
import dev.utils.app.ShapeUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils
import dev.widget.ui.WrapView

/**
 * detail: 自动换行 View
 * @author Ttt
 */
class WrapViewFragment : AppFragment<FragmentDevWidgetWrapViewBinding, WrapViewModel>(
    R.layout.fragment_dev_widget_wrap_view, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<WrapViewFragment> {
            binding.vidWrap
//                // 设置最大行数
//                .setMaxLine(RandomUtils.getRandom(10, 30))
//                // 设置每一行向上的边距 ( 行间隔 )
//                .setRowTopMargin(30)
//                // 设置每个 View 之间的 Left 边距
//                .setViewLeftMargin(30)
                // 快捷设置两个边距
                .setRowViewMargin(30, 30)
            viewModel.refreshView(binding.vidWrap)

            // 点击刷新按钮
            viewModel.clickRefresh = View.OnClickListener { view ->
                binding.vidWrap.removeAllViews()
                // 每次删除旧的重新刷新 View
                viewModel.refreshView(binding.vidWrap)
            }
        }
    }
)

class WrapViewModel : AppViewModel() {

    var clickRefresh = View.OnClickListener { view -> }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 刷新 View【随机添加不同 Text View】
     */
    fun refreshView(wrapView: WrapView) {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // 设置点击效果
        val drawable = ShapeUtils.newShape(
            30F, ResourceUtils.getColor(R.color.color_88)
        ).drawable
        for (i in 1..20) {
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(7)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(5))
            val randomText = "$i." + RandomUtils.getRandom(
                text.toCharArray(), text.length
            )
            wrapView.addView(createTextView(wrapView.context, randomText, layoutParams, drawable))
        }
    }

    /**
     * 创建随机 TextView
     */
    private fun createTextView(
        context: Context,
        text: String,
        layoutParams: ViewGroup.LayoutParams,
        drawable: GradientDrawable
    ): AppCompatTextView {
        return QuickHelper.get(AppCompatTextView(context))
            .setLayoutParams(layoutParams)
            .setPadding(30, 15, 30, 15)
            .setBackground(drawable)
            .setMaxLines(1)
            .setEllipsize(TextUtils.TruncateAt.END)
            .setTextColors(Color.WHITE)
            .setTextSizeBySp(15F)
            .setBold(RandomUtils.nextBoolean())
            .setText(text)
            .getView()
    }
}