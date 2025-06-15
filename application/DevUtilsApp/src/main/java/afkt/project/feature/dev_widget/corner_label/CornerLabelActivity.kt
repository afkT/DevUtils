package afkt.project.feature.dev_widget.corner_label

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.ActivityCornerLabelBinding
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.therouter.router.Route
import dev.mvvm.utils.size.AppSize
import dev.utils.app.ListenerUtils
import dev.utils.common.RandomUtils

/**
 * detail: 自定义角标 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.CornerLabelActivity_PATH)
class CornerLabelActivity : BaseProjectActivity<ActivityCornerLabelBinding, AppViewModel>(
    R.layout.activity_corner_label, simple_Agile = {
        if (it is CornerLabelActivity) {
            it.apply {
                ListenerUtils.setOnClicks(
                    this,
                    binding.vidBtnColorTv, binding.vidBtnLeftTv,
                    binding.vidBtnTopTv, binding.vidBtnTriangleTv,
                    binding.vidBtnText1MinusTv, binding.vidBtnText1PlusTv,
                    binding.vidBtnHeight1MinusTv, binding.vidBtnHeight1PlusTv,
                    binding.vidBtnText2MinusTv, binding.vidBtnText2PlusTv,
                    binding.vidBtnHeight2MinusTv, binding.vidBtnHeight2PlusTv
                )
            }
        }
    }
) {

    override fun onClick(v: View) {
        super.onClick(v)
        val labelView = binding.vidClv
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

    private var convertPx = 0F
    private var mText1Index = 3
    private var mText1Height = 12F
    private var mText2Index = 3
    private var mText2Height = 8F
    private var mLeft = true
    private var mTop = true
    private var mTriangle = false

    companion object {
        val TEXTS = arrayOf("滿減", "赠品", "满送", "包邮", "拼图", "新人", "砍价", "预售", "众筹")
    }
}