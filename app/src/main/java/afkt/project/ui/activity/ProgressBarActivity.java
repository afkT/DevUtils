package afkt.project.ui.activity;

import android.os.Handler;
import android.os.Message;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityProgressbarBinding;
import dev.utils.app.ActivityUtils;

/**
 * detail: 自定义 ProgressBar 样式 View
 * @author Ttt
 */
public class ProgressBarActivity extends BaseActivity<ActivityProgressbarBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_progressbar;
    }

    @Override
    public void initValue() {
        super.initValue();

//        // 内外圆环 + 数字 + 无扇形
//        binding.vidApBar1.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
//                .setOuterRingWidth(SizeUtils.dipConvertPx(5)) // 内环宽度
//                .setOuterRingColor(ResourceUtils.getColor(R.color.khaki)) // 内环颜色
//                .setProgressColor(ResourceUtils.getColor(R.color.color_88)) // 进度颜色
//                .setCanvasNumber(true); // 是否绘制数字
//
//        // 扇形 + 数字 + 无内外圆环
//        binding.vidApBar2.setProgressStyle(LoadProgressBar.ProgressStyle.FAN_SHAPED)
//                .setProgressColor(ResourceUtils.getColor(R.color.sky_blue)) // 进度颜色
//                .setCanvasNumber(true); // 是否绘制数字
//
//        // 扇形 + 数字 + 外圆环
//        binding.vidApBar3.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
//                .setOuterRingWidth(SizeUtils.dipConvertPx(1)) // 内环宽度
//                .setOuterRingColor(Color.RED) // 内环颜色
//                .setProgressColor(ResourceUtils.getColor(R.color.mediumturquoise)) // 进度颜色
//                .setNumberTextColor(Color.parseColor("#FB7D00")) // 字体颜色
//                .setCanvasNumber(true); // 是否绘制数字
//
//        // 单独字体
//        binding.vidApBar4.setProgressStyle(LoadProgressBar.ProgressStyle.NUMBER)
//                .setNumberTextSize(20f) // 字体大小
//                .setNumberTextColor(ResourceUtils.getColor(R.color.deeppink)); // 字体颜色

        // 延迟发送通知
        handler.sendEmptyMessageDelayed(0, 100);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 如果页面销毁了则不处理
            if (ActivityUtils.isFinishing(mActivity)) return;
            try {
                int progress = binding.vidApBar1.getProgress() + 1;
                // 每次进行累加
                binding.vidApBar1.setProgress(progress);
                binding.vidApBar2.setProgress(progress);
                binding.vidApBar3.setProgress(progress);
                binding.vidApBar4.setProgress(progress);
                // 判断是否符合条件
                if (binding.vidApBar1.getProgress() < binding.vidApBar1.getMax()) {
                    // 延迟发送通知
                    handler.sendEmptyMessageDelayed(0, 100);
                }
            } catch (Exception e) {
            }
        }
    };
}