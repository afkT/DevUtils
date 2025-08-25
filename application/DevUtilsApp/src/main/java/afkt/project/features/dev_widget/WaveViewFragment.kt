package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetWaveViewBinding
import android.widget.SeekBar
import androidx.core.graphics.toColorInt
import com.lihang.ShadowLayout
import dev.base.simple.extensions.asFragment
import dev.utils.app.ResourceUtils
import dev.widget.ui.WaveView
import dev.widget.utils.WaveHelper

/**
 * detail: 波浪 View
 * @author Ttt
 */
class WaveViewFragment : AppFragment<FragmentDevWidgetWaveViewBinding, AppViewModel>(
    R.layout.fragment_dev_widget_wave_view, BR.viewModel,
    simple_Agile = { frg ->
        frg.asFragment<WaveViewFragment> {
            val titleView = contentAssist.titleLinear().getChildAt(0)
            if (titleView is ShadowLayout) {
                titleView.setLayoutBackground(
                    ResourceUtils.getColor(R.color.color_55)
                )
            }
        }
    }
) {

    override fun onPause() {
        super.onPause()
        helper.cancel()
    }

    override fun onResume() {
        super.onResume()
        helper.start()
    }

    override fun initListener() {
        super.initListener()
        // 设置波浪边框信息
        setSeekBarChange()
        // 设置波浪外形形状
        setShapeChoiceChange()
        // 设置波浪颜色
        setColorChoiceChange()
    }

    // ===========
    // = 内部方法 =
    // ===========

    val helper: WaveHelper by lazy {
        val lazyObj = WaveHelper.get(binding.vidWave)
        // 通过属性动画进行设置波浪 View 动画效果
        lazyObj.buildPropertyAnimation(
            WaveHelper.WaveProperty.Builder()
                // 设置水位高度属性值
                .setWaterLevelRatio(
                    0F, 0.7F, 10000L
                ).build()
        )
        lazyObj
    }

    // =

    /**
     * 设置波浪边框信息
     */
    private fun setSeekBarChange() {
        binding.vidSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                helper.setBorder(progress.toFloat(), helper.borderColor)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    /**
     * 设置波浪外形形状
     */
    private fun setShapeChoiceChange() {
        binding.vidShapeChoiceRg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.vid_shape_circle_rb -> {
                    helper.shapeType = WaveView.ShapeType.CIRCLE
                }

                R.id.vid_shape_square_rb -> {
                    helper.shapeType = WaveView.ShapeType.SQUARE
                }
            }
        }
    }

    /**
     * 设置波浪颜色
     */
    private fun setColorChoiceChange() {
        binding.vidColorChoiceRg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.vid_color_default_rb -> {
                    helper.setBorder(
                        helper.borderWidth, WaveView.DEFAULT_BORDER_COLOR
                    ).setWaveColor(
                        WaveView.DEFAULT_BEHIND_WAVE_COLOR,
                        WaveView.DEFAULT_FRONT_WAVE_COLOR,
                    )
                }

                R.id.vid_color_red_rb -> {
                    helper.setBorder(
                        helper.borderWidth, "#44f16d7a".toColorInt()
                    ).setWaveColor(
                        "#28f16d7a".toColorInt(),
                        "#3cf16d7a".toColorInt()
                    )
                }

                R.id.vid_color_green_rb -> {
                    helper.setBorder(
                        helper.borderWidth, "#B0b7d28d".toColorInt()
                    ).setWaveColor(
                        "#40b7d28d".toColorInt(),
                        "#80b7d28d".toColorInt()
                    )
                }

                R.id.vid_color_blue_rb -> {
                    helper.setBorder(
                        helper.borderWidth, "#b8f1ed".toColorInt()
                    ).setWaveColor(
                        "#88b8f1ed".toColorInt(),
                        "#b8f1ed".toColorInt()
                    )
                }
            }
        }
    }
}