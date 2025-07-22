package afkt.project.features.ui_effect

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectToastTintBinding
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import dev.utils.app.ResourceUtils
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: ToastTint ( 着色美化 Toast )
 * @author Ttt
 */
class ToastTintFragment : AppFragment<FragmentUiEffectToastTintBinding, ToastTintViewModel>(
    R.layout.fragment_ui_effect_toast_tint, BR.viewModel
)

class ToastTintViewModel : AppViewModel() {

    var clickToastSuccess = View.OnClickListener { view ->
        ToastTintUtils.success("Success Style Toast")
    }

    var clickToastError = View.OnClickListener { view ->
        ToastTintUtils.error("Error Style Toast")
    }

    var clickToastInfo = View.OnClickListener { view ->
        ToastTintUtils.info("Info Style Toast")
    }

    var clickToastNormal = View.OnClickListener { view ->
        ToastTintUtils.normal("Normal Style Toast")
    }

    var clickToastWarning = View.OnClickListener { view ->
        ToastTintUtils.warning("Warning Style Toast")
    }

    var clickToastCustom = View.OnClickListener { view ->
        ToastTintUtils.custom(
            TempStyle(), "Custom Style Toast",
            ResourceUtils.getDrawable(R.mipmap.icon_launcher_round)
        )
    }

    // =

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
        override fun getTextSize(): Float = 16F

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
        override fun getEllipsize(): TextUtils.TruncateAt? = null

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