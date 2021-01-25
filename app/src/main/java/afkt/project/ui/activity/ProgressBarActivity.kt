package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityProgressbarBinding
import android.os.Handler
import android.os.Message
import dev.utils.app.ActivityUtils

/**
 * detail: 自定义 ProgressBar 样式 View
 * @author Ttt
 */
class ProgressBarActivity : BaseActivity<ActivityProgressbarBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_progressbar

    override fun initValue() {
        super.initValue()

//        // 内外圆环 + 数字 + 无扇形
//        binding.vidApBar1.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
//            .setOuterRingWidth(SizeUtils.dipConvertPx(5f).toFloat()) // 内环宽度
//            .setOuterRingColor(ResourceUtils.getColor(R.color.khaki)) // 内环颜色
//            .setProgressColor(ResourceUtils.getColor(R.color.color_88)).isCanvasNumber =
//            true // 是否绘制数字
//
//        // 扇形 + 数字 + 无内外圆环
//        binding.vidApBar2.setProgressStyle(LoadProgressBar.ProgressStyle.FAN_SHAPED)
//            .setProgressColor(ResourceUtils.getColor(R.color.sky_blue)).isCanvasNumber =
//            true // 是否绘制数字
//
//        // 扇形 + 数字 + 外圆环
//        binding.vidApBar3.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
//            .setOuterRingWidth(SizeUtils.dipConvertPx(1f).toFloat()) // 内环宽度
//            .setOuterRingColor(Color.RED) // 内环颜色
//            .setProgressColor(ResourceUtils.getColor(R.color.mediumturquoise)) // 进度颜色
//            .setNumberTextColor(Color.parseColor("#FB7D00")).isCanvasNumber = true // 是否绘制数字
//
//        // 单独字体
//        binding.vidApBar4.setProgressStyle(LoadProgressBar.ProgressStyle.NUMBER)
//            .setNumberTextSize(20f).numberTextColor =
//            ResourceUtils.getColor(R.color.deeppink) // 字体颜色

        // 延迟发送通知
        handler.sendEmptyMessageDelayed(0, 100)
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            // 如果页面销毁了则不处理
            if (ActivityUtils.isFinishing(mActivity)) return
            try {
                val progress = binding.vidApBar1.progress + 1
                // 每次进行累加
                binding.vidApBar1.progress = progress
                binding.vidApBar2.progress = progress
                binding.vidApBar3.progress = progress
                binding.vidApBar4.progress = progress
                // 判断是否符合条件
                if (binding.vidApBar1.progress < binding.vidApBar1.max) {
                    // 延迟发送通知
                    sendEmptyMessageDelayed(0, 100)
                }
            } catch (e: Exception) {
            }
        }
    }
}