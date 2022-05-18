package afkt.project.feature.dev_widget.view_pager

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityViewPagerBinding
import afkt.project.model.item.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import dev.kotlin.engine.log.log_dTag
import dev.widget.custom.CustomViewPager.OnDirectionListener

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.ViewPagerActivity_PATH)
class ViewPagerActivity : BaseActivity<ActivityViewPagerBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_view_pager

    override fun initValue() {
        super.initValue()
        val lists: MutableList<String> = ArrayList()
        for (i in 0..4) lists.add((i + 1).toString())
        binding.vidVp.adapter = ViewPagerAdapter(lists)
        binding.vidVp.setCurrentItem(lists.size * 100, false)
        binding.vidVp.setOnPageChangeListener(object : OnDirectionListener() {
            override fun onSlideDirection(
                left: Boolean,
                right: Boolean
            ) {
                if (left && !right) {
                    log_dTag(
                        tag = TAG,
                        message = "往左滑 - 从右往左"
                    )
                } else {
                    log_dTag(
                        tag = TAG,
                        message = "往右滑 - 从左往右"
                    )
                }
            }

            override fun onPageSelected(index: Int) {
                log_dTag(
                    tag = TAG,
                    message = "索引变动: $index"
                )
                if (mLeftScroll) {
                    showToast("往左滑 - 从右往左")
                } else {
                    showToast("往右滑 - 从左往右")
                }
            }
        })
        binding.vidAllowBtn.setOnClickListener {
            binding.vidVp.isSlide = true
            showToast(true, "已允许滑动")
        }
        binding.vidBanBtn.setOnClickListener {
            binding.vidVp.isSlide = false
            showToast(false, "已禁止滑动")
        }
    }
}