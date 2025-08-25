package afkt.project.features.dev_widget

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetFlowLikeBinding
import dev.base.simple.extensions.asFragment

/**
 * detail: 自定义点赞效果 View
 * @author Ttt
 */
class FlowLikeFragment : AppFragment<FragmentDevWidgetFlowLikeBinding, AppViewModel>(
    R.layout.fragment_dev_widget_flow_like, simple_Agile = { frg ->
        frg.asFragment<FlowLikeFragment> {
//            app:dev_animDuration = "2000"
//            app:dev_iconHeight = "30.0dp"
//            app:dev_iconWidth = "30.0dp"
//
//            // 设置动画时间
//            binding.vidFlv.animDuration = 2000
//            // 设置图标宽度
//            binding.vidFlv.iconWidth = AppSize.dp2px(30F)
//            // 设置图标高度
//            binding.vidFlv.iconHeight = AppSize.dp2px(30F)
//
//            // 设置漂浮图标
//            val lists = mutableListOf<Drawable>()
//            lists.add(ResourceUtils.getDrawable(R.mipmap.icon_live_brow_1))
//            lists.add(ResourceUtils.getDrawable(R.mipmap.icon_live_brow_2))
//            lists.add(ResourceUtils.getDrawable(R.mipmap.icon_live_brow_3))
//            lists.add(ResourceUtils.getDrawable(R.mipmap.icon_live_brow_4))
//            lists.add(ResourceUtils.getDrawable(R.mipmap.icon_live_brow_5))
//            binding.vidFlv.drawables = lists

            // 设置漂浮图标
            binding.vidFlv.setDrawablesById(
                R.mipmap.icon_live_brow_1,
                R.mipmap.icon_live_brow_2,
                R.mipmap.icon_live_brow_3,
                R.mipmap.icon_live_brow_4,
                R.mipmap.icon_live_brow_5
            )

            // 点击演示效果
            binding.vidFlv.setOnClickListener {
                binding.vidFlv.like()
                binding.vidFlv.like()
            }
        }
    }
)