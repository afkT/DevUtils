package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityWaveViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.BarUtils
import dev.utils.app.ResourceUtils
import dev.widget.utils.WaveHelper

/**
 * detail: 波浪 View
 * @author Ttt
 */
@Route(path = RouterPath.WaveViewActivity_PATH)
class WaveViewActivity : BaseActivity<ActivityWaveViewBinding>() {

    val helper: WaveHelper by lazy {
        WaveHelper.get(binding.vidAwvWave)
    }

    override fun baseLayoutId(): Int = R.layout.activity_wave_view

    override fun onPause() {
        super.onPause()
        helper.cancel()
    }

    override fun onResume() {
        super.onResume()
        helper.start()
    }

    override fun initView() {
        super.initView()

        toolbar?.let { bar ->
            val rgb = ResourceUtils.getColor(R.color.color_55)
            bar.setBackgroundColor(rgb)
            BarUtils.addMarginTopEqualStatusBarHeight(bar)
            BarUtils.setStatusBarColor(mActivity, rgb)
        }
    }
}