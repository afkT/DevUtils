package afkt.project.features.ui_effect

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectShapeGradientBinding
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.widget.AppCompatTextView
import dev.utils.app.ListenerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ShapeUtils
import dev.utils.app.StateListUtils
import dev.utils.app.assist.ResourceColor
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Shape、GradientDrawable 效果
 * @author Ttt
 */
class ShapeGradientFragment : AppFragment<FragmentUiEffectShapeGradientBinding, AppViewModel>(
    R.layout.fragment_ui_effect_shape_gradient
) {

    override fun initValue() {
        super.initValue()

        // 默认选中
        ViewHelper.get()
            .setSelected(true, binding.vid10Tv)
            .setSelected(false, binding.vid11Tv)
        changeTab1(binding.vid20Tv, binding.vid21Tv)
        changeTab2(binding.vid31Tv, binding.vid30Tv)

        // 动态设置
        StateListUtils.newSelector(R.mipmap.btn_pressed, R.mipmap.btn_normal)
            .also { binding.vid40Btn.background = it }

        binding.vid40Btn.setTextColor(
            StateListUtils.createColorStateList(
                R.color.black, R.color.white
            )
        )

        // 默认就设置背景色
        ShapeUtils.newShape()
            .setCornerRadiusLeft(10.0F)
            .setColor(Color.BLACK)
            .drawable.also { binding.vid41Btn.background = it }

        // 设置点击效果
        val drawable1 = ShapeUtils.newShape(10F, ResourceUtils.getColor(R.color.black))
            .setStroke(5, ResourceUtils.getColor(R.color.gold)).drawable
        val drawable2 = ShapeUtils.newShape(10F, ResourceUtils.getColor(R.color.blue))
            .setStroke(5, ResourceUtils.getColor(R.color.gray)).drawable

        StateListUtils.newSelector(drawable2, drawable1)
            .also { binding.vid41Btn.background = it }

        binding.vid41Btn.setTextColor(
            StateListUtils.createColorStateList(
                R.color.red, R.color.white
            )
        )

        // ==========
        // = 渐变效果 =
        // ==========

        ShapeUtils.newShape(
            GradientDrawable.Orientation.BR_TL,
            intArrayOf(Color.RED, Color.BLUE, Color.GREEN)
        ).setDrawable(binding.vid50View)

        val colors = IntArray(3)
        colors[0] = ResourceColor.get().getColor(R.color.black)
        colors[1] = ResourceColor.get().getColor(R.color.blue)
        colors[2] = ResourceColor.get().getColor(R.color.orange)
        val drawable = ShapeUtils.newShape(GradientDrawable.Orientation.BR_TL, colors).drawable
//        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT   // 线性渐变, 这是默认设置
//        drawable.gradientType = GradientDrawable.RADIAL_GRADIENT   // 放射性渐变, 以开始色为中心
        drawable.gradientType = GradientDrawable.SWEEP_GRADIENT     // 扫描线式的渐变
        binding.vid60View.background = drawable
    }

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            { v ->
                when (v.id) {
                    R.id.vid_1_0_tv -> {
                        ViewHelper.get()
                            .setSelected(true, binding.vid10Tv)
                            .setSelected(false, binding.vid11Tv)
                    }

                    R.id.vid_1_1_tv -> {
                        ViewHelper.get()
                            .setSelected(true, binding.vid11Tv)
                            .setSelected(false, binding.vid10Tv)
                    }

                    R.id.vid_2_0_tv -> changeTab1(binding.vid20Tv, binding.vid21Tv)
                    R.id.vid_2_1_tv -> changeTab1(binding.vid21Tv, binding.vid20Tv)
                    R.id.vid_3_0_tv -> changeTab2(binding.vid30Tv, binding.vid31Tv)
                    R.id.vid_3_1_tv -> changeTab2(binding.vid31Tv, binding.vid30Tv)
                }
            },
            binding.vid10Tv, binding.vid11Tv,
            binding.vid20Tv, binding.vid21Tv,
            binding.vid30Tv, binding.vid31Tv
        )
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private fun changeTab1(
        clickTab: AppCompatTextView,
        unClickTab: AppCompatTextView
    ) {
        // 判断点击的是左边还是右边
        if (clickTab.id == R.id.vid_2_0_tv) { // 点击左边
            clickTab.setBackgroundResource(R.drawable.shape_tab_left_pressed)
            unClickTab.setBackgroundResource(R.drawable.shape_tab_right_selector)
        } else {
            clickTab.setBackgroundResource(R.drawable.shape_tab_right_pressed)
            unClickTab.setBackgroundResource(R.drawable.shape_tab_left_selector)
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(ResourceColor.get().getColor(R.color.white))
        // 未选中增加点击效果
        unClickTab.setTextColor(ResourceUtils.getColorStateList(R.color.text_color_333333_ffffff_selector))
    }

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private fun changeTab2(
        clickTab: AppCompatTextView,
        unClickTab: AppCompatTextView
    ) {
        // 判断点击的是左边还是右边
        if (clickTab.id == R.id.vid_3_0_tv) { // 点击左边
            // 设置选中颜色
            ShapeUtils.newShape().setCornerRadiusLeft(10F)
                .setColor(ResourceUtils.getColor(R.color.blue))
                .setDrawable(clickTab)

            // 设置未选中颜色
            val drawable1 = ShapeUtils.newShape()
                .setCornerRadiusRight(10F)
                .setColor(ResourceUtils.getColor(R.color.blue))
                .drawable
            val drawable2 = ShapeUtils.newShape()
                .setCornerRadiusRight(10F)
                .setColor(ResourceUtils.getColor(R.color.color_33))
                .drawable
            unClickTab.background = StateListUtils.newSelector(drawable1, drawable2)
        } else {
            // 设置选中颜色
            ShapeUtils.newShape().setCornerRadiusRight(10F)
                .setColor(ResourceUtils.getColor(R.color.blue))
                .setDrawable(clickTab)

            // 设置未选中颜色
            val drawable1 = ShapeUtils.newShape()
                .setCornerRadiusLeft(10F)
                .setColor(ResourceUtils.getColor(R.color.blue))
                .drawable
            val drawable2 = ShapeUtils.newShape()
                .setCornerRadiusLeft(10F)
                .setColor(ResourceUtils.getColor(R.color.color_33))
                .drawable
            unClickTab.background = StateListUtils.newSelector(drawable1, drawable2)
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(ResourceColor.get().getColor(R.color.white))
        // 设置未选中颜色
        unClickTab.setTextColor(StateListUtils.createColorStateList(R.color.white, R.color.red))
    }
}