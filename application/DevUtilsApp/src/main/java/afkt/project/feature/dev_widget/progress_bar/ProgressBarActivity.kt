package afkt.project.feature.dev_widget.progress_bar

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityProgressbarBinding
import afkt.project.model.data.button.RouterPath
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.therouter.router.Route
import dev.utils.app.ActivityUtils

/**
 * detail: 自定义 ProgressBar 样式 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.ProgressBarActivity_PATH)
class ProgressBarActivity : BaseProjectActivity<ActivityProgressbarBinding, AppViewModel>(
    R.layout.activity_progressbar, simple_Agile = {
        if (it is ProgressBarActivity) {
            it.apply {
//                // 内外圆环 + 数字 + 无扇形
//                binding.vidLpb1.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
//                    .setOuterRingWidth(AppSize.dp2px(5F).toFloat()) // 内环宽度
//                    .setOuterRingColor(ResourceUtils.getColor(R.color.khaki)) // 内环颜色
//                    .setProgressColor(ResourceUtils.getColor(R.color.color_88)).isCanvasNumber =
//                    true // 是否绘制数字
//
//                // 扇形 + 数字 + 无内外圆环
//                binding.vidLpb2.setProgressStyle(LoadProgressBar.ProgressStyle.FAN_SHAPED)
//                    .setProgressColor(ResourceUtils.getColor(R.color.sky_blue)).isCanvasNumber =
//                    true // 是否绘制数字
//
//                // 扇形 + 数字 + 外圆环
//                binding.vidLpb3.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
//                    .setOuterRingWidth(AppSize.dp2px(1F).toFloat()) // 内环宽度
//                    .setOuterRingColor(Color.RED) // 内环颜色
//                    .setProgressColor(ResourceUtils.getColor(R.color.mediumturquoise)) // 进度颜色
//                    .setNumberTextColor(Color.parseColor("#FB7D00")).isCanvasNumber = true // 是否绘制数字
//
//                // 单独字体
//                binding.vidLpb4.setProgressStyle(LoadProgressBar.ProgressStyle.NUMBER)
//                    .setNumberTextSize(20F).numberTextColor =
//                    ResourceUtils.getColor(R.color.deeppink) // 字体颜色

                // 延迟发送通知
                handler.sendEmptyMessageDelayed(0, 100)
            }
        }
    }
) {

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 如果页面销毁了则不处理
            if (ActivityUtils.isFinishing(mActivity)) return
            try {
                val progress = binding.vidLpb1.progress + 1
                // 每次进行累加
                binding.vidLpb1.progress = progress
                binding.vidLpb2.progress = progress
                binding.vidLpb3.progress = progress
                binding.vidLpb4.progress = progress
                // 判断是否符合条件
                if (binding.vidLpb1.progress < binding.vidLpb1.max) {
                    // 延迟发送通知
                    sendEmptyMessageDelayed(0, 100)
                }
            } catch (_: Exception) {
            }
        }
    }
}