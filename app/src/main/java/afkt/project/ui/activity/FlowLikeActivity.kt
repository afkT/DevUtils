package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityFlowLikeBinding
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * detail: 自定义点赞效果 View
 * @author Ttt
 */
@Route(path = RouterPath.FlowLikeActivity_PATH)
class FlowLikeActivity : BaseActivity<ActivityFlowLikeBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_flow_like

    override fun initValue() {
        super.initValue()

//        app:dev_animDuration="2000"
//        app:dev_iconHeight="30.0dp"
//        app:dev_iconWidth="30.0dp"

//        // 设置动画时间
//        binding.vidAflFlowlike.animDuration = 2000
//        // 设置图标宽度
//        binding.vidAflFlowlike.iconWidth = SizeUtils.dipConvertPx(30f)
//        // 设置图标高度
//        binding.vidAflFlowlike.iconHeight = SizeUtils.dipConvertPx(30f)
//
//        // 设置漂浮图标
//        val lists: MutableList<Drawable> = ArrayList()
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_1))
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_2))
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_3))
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_4))
//        lists.add(ResourceUtils.getDrawable(R.drawable.icon_live_brow_5))
//        binding.vidAflFlowlike.drawables = lists

        // 设置漂浮图标
        binding.vidAflFlowlike.setDrawablesById(
            R.drawable.icon_live_brow_1, R.drawable.icon_live_brow_2,
            R.drawable.icon_live_brow_3, R.drawable.icon_live_brow_4, R.drawable.icon_live_brow_5
        )
        binding.vidAflFlowlike.setOnClickListener {
            binding.vidAflFlowlike.like()
            binding.vidAflFlowlike.like() // 演示效果
        }
    }
}