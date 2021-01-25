package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityCornerLabelBinding
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import dev.utils.app.ListenerUtils
import dev.utils.app.SizeUtils
import dev.utils.common.RandomUtils

/**
 * detail: 自定义角标 View
 * @author Ttt
 */
class CornerLabelActivity : BaseActivity<ActivityCornerLabelBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_corner_label

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.btnColor, binding.btnLeft, binding.btnTop, binding.btnTriangle,
            binding.btnText1Minus, binding.btnText1Plus,
            binding.btnHeight1Minus, binding.btnHeight1Plus,
            binding.btnText2Minus, binding.btnText2Plus,
            binding.btnHeight2Minus, binding.btnHeight2Plus
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        val labelView = binding.vidAclLabelview
        val layoutParams: FrameLayout.LayoutParams
        when (v.id) {
            R.id.btn_color -> labelView.setFillColor(
                -0x1000000 or RandomUtils.getRandom(0, 0xffffff)
            )
            R.id.btn_left -> {
                if (mIsLeft) {
                    labelView.right()
                } else {
                    labelView.left()
                }
                mIsLeft = !mIsLeft
                layoutParams = labelView.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity =
                    (if (mIsLeft) Gravity.LEFT else Gravity.RIGHT) or if (mIsTop) Gravity.TOP else Gravity.BOTTOM
                labelView.layoutParams = layoutParams
            }
            R.id.btn_top -> {
                if (mIsTop) {
                    labelView.bottom()
                } else {
                    labelView.top()
                }
                mIsTop = !mIsTop
                layoutParams = labelView.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity =
                    (if (mIsLeft) Gravity.LEFT else Gravity.RIGHT) or if (mIsTop) Gravity.TOP else Gravity.BOTTOM
                labelView.layoutParams = layoutParams
            }
            R.id.btn_triangle -> {
                mIsTriangle = !mIsTriangle
                labelView.triangle(mIsTriangle)
            }
            R.id.btn_text1_minus -> {
                mText1Index = (mText1Index - 1 + TEXTS.size) % TEXTS.size
                labelView.setText1(TEXTS[mText1Index])
            }
            R.id.btn_text1_plus -> {
                mText1Index = (mText1Index + 1) % TEXTS.size
                labelView.setText1(TEXTS[mText1Index])
            }
            R.id.btn_height1_minus -> {
                if (mText1Height < 8) return
                mText1Height -= 2f
                convertPx = SizeUtils.spConvertPx(mText1Height).toFloat()
                labelView.setTextHeight1(convertPx)
                labelView.setPaddingTop(convertPx)
                labelView.setPaddingCenter(convertPx / 3)
                labelView.setPaddingBottom(convertPx / 3)
            }
            R.id.btn_height1_plus -> {
                if (mText1Height > 30) return
                mText1Height += 2f
                convertPx = SizeUtils.spConvertPx(mText1Height).toFloat()
                labelView.setTextHeight1(convertPx)
                labelView.setPaddingTop(convertPx)
                labelView.setPaddingCenter(convertPx / 3)
                labelView.setPaddingBottom(convertPx / 3)
            }
            R.id.btn_text2_minus -> {
                mText2Index = (mText2Index + 5 - 1) % 5
                labelView.setText2("1234567890".substring(0, mText2Index))
            }
            R.id.btn_text2_plus -> {
                mText2Index = (mText2Index + 5 + 1) % 5
                labelView.setText2("1234567890".substring(0, mText2Index))
            }
            R.id.btn_height2_minus -> {
                if (mText2Height < 4) return
                mText2Height -= 2f
                convertPx = SizeUtils.spConvertPx(mText2Height).toFloat()
                labelView.setTextHeight2(convertPx)
            }
            R.id.btn_height2_plus -> {
                if (mText2Height > 20) return
                mText2Height += 2f
                convertPx = SizeUtils.spConvertPx(mText2Height).toFloat()
                labelView.setTextHeight2(convertPx)
            }
        }
    }

    var convertPx = 0f
    var mText1Index = 3
    var mText1Height = 12f
    var mText2Index = 3
    var mText2Height = 8f
    var mIsLeft = true
    var mIsTop = true
    var mIsTriangle = false

    companion object {
        val TEXTS = arrayOf("滿減", "赠品", "满送", "包邮", "拼图", "新人", "砍价", "预售", "众筹")
    }
}