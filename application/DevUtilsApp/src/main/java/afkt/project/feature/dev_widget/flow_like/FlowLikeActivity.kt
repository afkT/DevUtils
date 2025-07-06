package afkt.project.feature.dev_widget.flow_like

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityFlowLikeBinding
import afkt.project.model.data.button.RouterPath
import com.therouter.router.Route

/**
 * detail: 自定义点赞效果 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.FlowLikeActivity_PATH)
class FlowLikeActivity : BaseProjectActivity<ActivityFlowLikeBinding, AppViewModel>(
    R.layout.activity_flow_like, simple_Agile = {
        if (it is FlowLikeActivity) {
            it.apply {
//                app:dev_animDuration = "2000"
//                app:dev_iconHeight = "30.0dp"
//                app:dev_iconWidth = "30.0dp"
//
//                // 设置动画时间
//                binding.vidFlv.animDuration = 2000
//                // 设置图标宽度
//                binding.vidFlv.iconWidth = AppSize.dp2px(30F)
//                // 设置图标高度
//                binding.vidFlv.iconHeight = AppSize.dp2px(30F)
//
//                // 设置漂浮图标
//                val lists = mutableListOf<Drawable>()
//                lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_1))
//                lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_2))
//                lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_3))
//                lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_4))
//                lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_5))
//                binding.vidFlv.drawables = lists

                // 设置漂浮图标
                binding.vidFlv.setDrawablesById(
                    R.mipmap.icon_live_brow_1,
                    R.mipmap.icon_live_brow_2,
                    R.mipmap.icon_live_brow_3,
                    R.mipmap.icon_live_brow_4,
                    R.mipmap.icon_live_brow_5
                )
                binding.vidFlv.setOnClickListener {
                    binding.vidFlv.like()
                    binding.vidFlv.like() // 演示效果
                }
            }
        }
    }
)