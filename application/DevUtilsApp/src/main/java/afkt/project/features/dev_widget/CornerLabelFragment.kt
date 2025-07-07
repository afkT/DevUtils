package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetCornerLabelBinding
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import dev.mvvm.utils.size.AppSize
import dev.simple.app.base.asFragment
import dev.utils.common.RandomUtils
import dev.utils.common.assist.WeakReferenceAssist
import dev.widget.ui.CornerLabelView

/**
 * detail: 自定义角标 View
 * @author Ttt
 */
class CornerLabelFragment : AppFragment<FragmentDevWidgetCornerLabelBinding, CornerLabelViewModel>(
    R.layout.fragment_dev_widget_corner_label, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<CornerLabelFragment> {
            viewModel.initialize(binding.vidClv)
        }
    }
)

class CornerLabelViewModel : AppViewModel() {

    // 自定义角标 View 弱引用辅助类
    private val assist = WeakReferenceAssist<CornerLabelView>()

    // 随机文案
    private val TEXTS = arrayOf(
        "滿減", "赠品", "满送", "包邮", "拼图", "新人", "砍价", "预售", "众筹"
    )

    private var convertPx = 0F
    private var mText1Index = 3
    private var mText1Height = 12F
    private var mText2Index = 3
    private var mText2Height = 8F
    private var mLeft = true
    private var mTop = true
    private var mTriangle = false

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 初始化方法
     */
    fun initialize(view: CornerLabelView?) {
        assist.singleWeakValue = view
    }

    // ==========
    // = 点击事件 =
    // ==========

    fun onClick(v: View) {
        val labelView = assist.singleWeakValue
        val layoutParams: FrameLayout.LayoutParams
        when (v.id) {
            R.id.vid_btn_color_tv -> labelView.setFillColor(
                -0x1000000 or RandomUtils.getRandom(0, 0xffffff)
            )

            R.id.vid_btn_left_tv -> {
                if (mLeft) {
                    labelView.right()
                } else {
                    labelView.left()
                }
                mLeft = !mLeft
                layoutParams = labelView.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity =
                    (if (mLeft) Gravity.START else Gravity.END) or if (mTop) Gravity.TOP else Gravity.BOTTOM
                labelView.layoutParams = layoutParams
            }

            R.id.vid_btn_top_tv -> {
                if (mTop) {
                    labelView.bottom()
                } else {
                    labelView.top()
                }
                mTop = !mTop
                layoutParams = labelView.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity =
                    (if (mLeft) Gravity.START else Gravity.END) or if (mTop) Gravity.TOP else Gravity.BOTTOM
                labelView.layoutParams = layoutParams
            }

            R.id.vid_btn_triangle_tv -> {
                mTriangle = !mTriangle
                labelView.triangle(mTriangle)
            }

            R.id.vid_btn_text1_minus_tv -> {
                mText1Index = (mText1Index - 1 + TEXTS.size) % TEXTS.size
                labelView.setText1(TEXTS[mText1Index])
            }

            R.id.vid_btn_text1_plus_tv -> {
                mText1Index = (mText1Index + 1) % TEXTS.size
                labelView.setText1(TEXTS[mText1Index])
            }

            R.id.vid_btn_height1_minus_tv -> {
                if (mText1Height < 8) return
                mText1Height -= 2F
                convertPx = AppSize.sp2pxf(mText1Height)
                labelView.setTextHeight1(convertPx)
                labelView.setPaddingTop(convertPx)
                labelView.setPaddingCenter(convertPx / 3)
                labelView.setPaddingBottom(convertPx / 3)
            }

            R.id.vid_btn_height1_plus_tv -> {
                if (mText1Height > 30) return
                mText1Height += 2F
                convertPx = AppSize.sp2pxf(mText1Height)
                labelView.setTextHeight1(convertPx)
                labelView.setPaddingTop(convertPx)
                labelView.setPaddingCenter(convertPx / 3)
                labelView.setPaddingBottom(convertPx / 3)
            }

            R.id.vid_btn_text2_minus_tv -> {
                mText2Index = (mText2Index + 5 - 1) % 5
                labelView.setText2("1234567890".substring(0, mText2Index))
            }

            R.id.vid_btn_text2_plus_tv -> {
                mText2Index = (mText2Index + 5 + 1) % 5
                labelView.setText2("1234567890".substring(0, mText2Index))
            }

            R.id.vid_btn_height2_minus_tv -> {
                if (mText2Height < 4) return
                mText2Height -= 2F
                convertPx = AppSize.sp2pxf(mText2Height)
                labelView.setTextHeight2(convertPx)
            }

            R.id.vid_btn_height2_plus_tv -> {
                if (mText2Height > 20) return
                mText2Height += 2F
                convertPx = AppSize.sp2pxf(mText2Height)
                labelView.setTextHeight2(convertPx)
            }
        }
    }
}