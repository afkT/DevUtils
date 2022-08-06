package afkt.project.feature.ui_effect.material

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityBottomSheetBinding
import afkt.project.model.item.RouterPath
import android.view.View
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.expand.engine.log.log_dTag
import dev.utils.app.ViewUtils

/**
 * detail: Material BottomSheet
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.BottomSheetActivity_PATH)
class BottomSheetActivity : BaseActivity<ActivityBottomSheetBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_bottom_sheet

    private lateinit var mBehavior: BottomSheetBehavior<LinearLayout>

    override fun initValue() {
        super.initValue()
        mBehavior = BottomSheetBehavior.from(binding.vidSheetLl)
    }

    override fun initListener() {
        super.initListener()

        mBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(
                bottomSheet: View,
                newState: Int
            ) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // 折叠状态, bottom sheets 只在底部显示一部分布局
                        // 显示高度可以通过 app:behavior_peekHeight 设置
                        TAG.log_dTag(
                            message = "STATE_COLLAPSED"
                        )

                        ViewUtils.setVisibility(false, binding.vidBgView)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        // 过渡状态, 此时用户正在向上或者向下拖动 bottom sheet
                        TAG.log_dTag(
                            message = "STATE_DRAGGING"
                        )

                        ViewUtils.setVisibility(true, binding.vidBgView)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // 完全展开的状态
                        TAG.log_dTag(
                            message = "STATE_EXPANDED"
                        )

                        ViewUtils.setVisibility(true, binding.vidBgView)
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // 隐藏状态, 默认是 false 可通过 app:behavior_hideable 属性设置是否能隐藏
                        TAG.log_dTag(
                            message = "STATE_HIDDEN"
                        )

                        ViewUtils.setVisibility(false, binding.vidBgView)
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        // 视图从脱离手指自由滑动到最终停下的这一小段时间
                        TAG.log_dTag(
                            message = "STATE_SETTLING"
                        )
                    }
                }
            }

            override fun onSlide(
                bottomSheet: View,
                slideOffset: Float
            ) {
            }
        })

//        // 手动设置状态
//        // 展开
//        mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        // 折叠
//        mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        // 隐藏
//        mBehavior.state = BottomSheetBehavior.STATE_HIDDEN

//        // Bottom Sheet 是否允许隐藏
//        // xml 设置
//        app:behavior_hideable="false"
//        // 代码设置
//        mBehavior.isHideable = false
    }
}