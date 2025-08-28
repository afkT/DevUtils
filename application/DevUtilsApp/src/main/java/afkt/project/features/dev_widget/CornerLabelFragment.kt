package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetCornerLabelBinding
import android.view.Gravity
import android.widget.FrameLayout
import dev.base.simple.extensions.asFragment
import dev.simple.core.utils.size.AppSize
import dev.utils.common.RandomUtils
import dev.widget.ui.CornerLabelView

/**
 * detail: 自定义角标 View
 * @author Ttt
 */
class CornerLabelFragment : AppFragment<FragmentDevWidgetCornerLabelBinding, CornerLabelViewModel>(
    R.layout.fragment_dev_widget_corner_label, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<CornerLabelFragment> {
            binding.setVariable(BR.labelView, binding.vidClv)
        }
    }
)

class CornerLabelViewModel : AppViewModel() {

    // 随机文案
    private val TEXTS = arrayOf(
        "滿減", "赠品", "满送", "包邮", "拼图", "新人", "砍价", "预售", "众筹"
    )

    private var mText1Index = 3
    private var mText1Height = 12F
    private var mText2Index = 3
    private var mText2Height = 8F
    private var mLeft = true
    private var mTop = true
    private var mTriangle = false

    // ==========
    // = 点击事件 =
    // ==========

    val clickColor: (CornerLabelView?) -> Unit = {
        it?.apply {
            setFillColor(-0x1000000 or RandomUtils.getRandom(0, 0xffffff))
        }
    }

    val clickLeft: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            if (mLeft) {
                labelView.right()
            } else {
                labelView.left()
            }
            mLeft = !mLeft
            val layoutParams = labelView.layoutParams as FrameLayout.LayoutParams
            val leftGravity = (if (mLeft) Gravity.START else Gravity.END)
            val topGravity = if (mTop) Gravity.TOP else Gravity.BOTTOM
            layoutParams.gravity = leftGravity or topGravity
            labelView.layoutParams = layoutParams
        }
    }

    val clickTop: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            if (mTop) {
                labelView.bottom()
            } else {
                labelView.top()
            }
            mTop = !mTop
            val layoutParams = labelView.layoutParams as FrameLayout.LayoutParams
            val leftGravity = (if (mLeft) Gravity.START else Gravity.END)
            val topGravity = if (mTop) Gravity.TOP else Gravity.BOTTOM
            layoutParams.gravity = leftGravity or topGravity
            labelView.layoutParams = layoutParams
        }
    }

    val clickTriangle: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            mTriangle = !mTriangle
            labelView.triangle(mTriangle)
        }
    }

    // =====
    // = 1 =
    // =====

    val clickText1Minus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            mText1Index = (mText1Index - 1 + TEXTS.size) % TEXTS.size
            labelView.setText1(TEXTS[mText1Index])
        }
    }

    val clickText1Plus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            mText1Index = (mText1Index + 1) % TEXTS.size
            labelView.setText1(TEXTS[mText1Index])
        }
    }

    val clickHeight1Minus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            if (mText1Height < 8) return@let
            mText1Height -= 2F
            val convertPx = AppSize.sp2pxf(mText1Height)
            labelView.setTextHeight1(convertPx)
            labelView.setPaddingTop(convertPx)
            labelView.setPaddingCenter(convertPx / 3)
            labelView.setPaddingBottom(convertPx / 3)
        }
    }

    val clickHeight1Plus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            if (mText1Height > 30) return@let
            mText1Height += 2F
            val convertPx = AppSize.sp2pxf(mText1Height)
            labelView.setTextHeight1(convertPx)
            labelView.setPaddingTop(convertPx)
            labelView.setPaddingCenter(convertPx / 3)
            labelView.setPaddingBottom(convertPx / 3)
        }
    }

    // =====
    // = 2 =
    // =====

    val clickText2Minus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            mText2Index = (mText2Index + 5 - 1) % 5
            labelView.setText2("1234567890".substring(0, mText2Index))
        }
    }

    val clickText2Plus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            mText2Index = (mText2Index + 5 + 1) % 5
            labelView.setText2("1234567890".substring(0, mText2Index))
        }
    }

    val clickHeight2Minus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            if (mText2Height < 4) return@let
            mText2Height -= 2F
            val convertPx = AppSize.sp2pxf(mText2Height)
            labelView.setTextHeight2(convertPx)
        }
    }

    val clickHeight2Plus: (CornerLabelView?) -> Unit = {
        it?.let { labelView ->
            if (mText2Height > 20) return@let
            mText2Height += 2F
            val convertPx = AppSize.sp2pxf(mText2Height)
            labelView.setTextHeight2(convertPx)
        }
    }
}