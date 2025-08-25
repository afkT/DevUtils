package afkt.project.features.dev_widget

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetProgressBarBinding
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import dev.base.simple.extensions.asFragment
import dev.utils.app.ActivityUtils

/**
 * detail: 自定义 ProgressBar 样式 View
 * @author Ttt
 */
class ProgressBarFragment : AppFragment<FragmentDevWidgetProgressBarBinding, AppViewModel>(
    R.layout.fragment_dev_widget_progress_bar, simple_Agile = { frg ->
        frg.asFragment<ProgressBarFragment> {

            // ==========
            // = 代码设置 =
            // ==========

//            // 内外圆环 + 数字 + 无扇形
//            binding.vidProgress1.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
//                .setOuterRingWidth(AppSize.dp2px(5F).toFloat()) // 内环宽度
//                .setOuterRingColor(ResourceUtils.getColor(R.color.gold)) // 内环颜色
//                .setProgressColor(ResourceUtils.getColor(R.color.color_88)).isCanvasNumber =
//                true // 是否绘制数字
//
//            // 扇形 + 数字 + 无内外圆环
//            binding.vidProgress2.setProgressStyle(LoadProgressBar.ProgressStyle.FAN_SHAPED)
//                .setProgressColor(ResourceUtils.getColor(R.color.blue)).isCanvasNumber =
//                true // 是否绘制数字
//
//            // 扇形 + 数字 + 外圆环
//            binding.vidProgress3.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
//                .setOuterRingWidth(AppSize.dp2px(1F).toFloat()) // 内环宽度
//                .setOuterRingColor(Color.RED) // 内环颜色
//                .setProgressColor(ResourceUtils.getColor(R.color.pink)) // 进度颜色
//                .setNumberTextColor(Color.parseColor("#FB7D00")).isCanvasNumber = true // 是否绘制数字
//
//            // 单独字体
//            binding.vidProgress4.setProgressStyle(LoadProgressBar.ProgressStyle.NUMBER)
//                .setNumberTextSize(20F).numberTextColor =
//                ResourceUtils.getColor(R.color.pink) // 字体颜色

            // 延迟发送通知
            handler.sendEmptyMessageDelayed(0, 100)
        }
    }
) {

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 如果页面销毁了则不处理
            if (ActivityUtils.isFinishing(activity)) return
            try {
                val progress = binding.vidProgress1.progress + 1
                // 每次进行累加
                binding.vidProgress1.progress = progress
                binding.vidProgress2.progress = progress
                binding.vidProgress3.progress = progress
                binding.vidProgress4.progress = progress
                // 判断是否符合条件
                if (binding.vidProgress1.progress < binding.vidProgress1.max) {
                    // 延迟发送通知
                    sendEmptyMessageDelayed(0, 100)
                }
            } catch (_: Exception) {
            }
        }
    }
}