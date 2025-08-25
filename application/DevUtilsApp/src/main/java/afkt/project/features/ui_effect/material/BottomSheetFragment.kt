package afkt.project.features.ui_effect.material

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectMaterialBottomSheetBinding
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.base.simple.extensions.asFragment
import dev.engine.extensions.log.log_dTag
import dev.utils.app.ViewUtils

/**
 * detail: Material BottomSheet
 * @author Ttt
 */
class BottomSheetFragment : AppFragment<FragmentUiEffectMaterialBottomSheetBinding, AppViewModel>(
    R.layout.fragment_ui_effect_material_bottom_sheet, simple_Agile = { frg ->
        frg.asFragment<BottomSheetFragment> {
//            // 手动设置状态
//            // 展开
//            mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            // 折叠
//            mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//            // 隐藏
//            mBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//
//            // Bottom Sheet 是否允许隐藏
//            // xml 设置
//            app:behavior_hideable = "false"
//            // 代码设置
//            mBehavior.isHideable = false

            val mBehavior = BottomSheetBehavior.from(binding.vidSheetLl)
            mBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
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

                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            // 显示在某个中间高度 ( 例如屏幕高度的 50% )
                            TAG.log_dTag(
                                message = "STATE_HALF_EXPANDED"
                            )
//                            mBehavior.isFitToContents = false // 必须设为 false
//                            mBehavior.halfExpandedRatio = 0.5f // 设置展开比例 ( 0.5= 50% 高度 )
                        }
                    }
                }

                override fun onSlide(
                    bottomSheet: View,
                    slideOffset: Float
                ) {
                }
            })
        }
    }
)