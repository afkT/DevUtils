package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils.TruncateAt
import dev.utils.app.ResourceUtils
import dev.utils.app.toast.ToastTintUtils
import utils_use.toast.ToastTintUse

/**
 * detail: ToastTint ( 着色美化 Toast )
 * @author Ttt
 * [ToastTintUse]
 */
class ToastTintActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(ButtonList.getToastButtonValues())
        binding.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.setOnItemChildClickListener { adapter, view, position ->
            val buttonValue = buttonAdapter.getItem(position)
            when (buttonValue.type) {
                ButtonValue.BTN_TOAST_TINT_SUCCESS -> ToastTintUtils.success("Success Style Toast")
                ButtonValue.BTN_TOAST_TINT_ERROR -> ToastTintUtils.error("Error Style Toast")
                ButtonValue.BTN_TOAST_TINT_INFO -> ToastTintUtils.info("Info Style Toast")
                ButtonValue.BTN_TOAST_TINT_NORMAL -> ToastTintUtils.normal("Normal Style Toast")
                ButtonValue.BTN_TOAST_TINT_WARNING -> ToastTintUtils.warning("Warning Style Toast")
                ButtonValue.BTN_TOAST_TINT_CUSTOM_STYLE -> ToastTintUtils.custom(
                    TempStyle(), "Custom Style Toast",
                    ResourceUtils.getDrawable(R.mipmap.icon_launcher_round)
                )
                else -> ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件")
            }
        }
    }

    /**
     * 自定义实现样式
     * [ToastTintUtils.SuccessStyle]
     * [ToastTintUtils.ErrorStyle]
     * [ToastTintUtils.InfoStyle]
     * [ToastTintUtils.WarningStyle]
     * [ToastTintUtils.NormalStyle]
     * [ToastTintUtils.DefaultStyle]
     */
    private class TempStyle : ToastTintUtils.Style {

        /**
         * 文本颜色
         */
        override fun getTextColor(): Int = Color.WHITE

        /**
         * 字体大小
         */
        override fun getTextSize(): Float = 16f

        /**
         * 背景着色颜色
         */
        override fun getBackgroundTintColor(): Int = 0

        /**
         * 背景图片
         */
        override fun getBackground(): Drawable? = null

        /**
         * 最大行数
         */
        override fun getMaxLines(): Int = 0

        /**
         * Ellipsize 效果
         */
        override fun getEllipsize(): TruncateAt? = null

        /**
         * 字体样式
         * return Typeface.create("sans-serif-condensed", Typeface.NORMAL)
         */
        override fun getTypeface(): Typeface? = null

        /**
         * 获取图标着色颜色
         */
        override fun getTintIconColor(): Int = Color.WHITE

        /**
         * 是否渲染图标 ( getTintIconColor() 着色渲染 )
         * @return `true` yes, `false` no
         */
        override fun isTintIcon(): Boolean = false
    }
}