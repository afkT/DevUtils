package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityViewPagerBinding
import afkt.project.ui.adapter.ViewPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.widget.custom.CustomViewPager.OnDirectionListener
import java.util.*

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
@Route(path = RouterPath.ViewPagerActivity_PATH)
class ViewPagerActivity : BaseActivity<ActivityViewPagerBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_view_pager

    override fun initValue() {
        super.initValue()
        val lists: MutableList<String> = ArrayList()
        for (i in 0..4) lists.add((i + 1).toString())
        binding.vidViewpager.adapter = ViewPagerAdapter(lists)
        binding.vidViewpager.setCurrentItem(lists.size * 100, false)
        binding.vidViewpager.setOnPageChangeListener(object : OnDirectionListener() {
            override fun onSlideDirection(
                left: Boolean,
                right: Boolean
            ) {
                if (left && !right) {
                    DevEngine.getLog()?.dTag(TAG, "往左滑 - 从右往左")
                } else {
                    DevEngine.getLog()?.dTag(TAG, "往右滑 - 从左往右")
                }
            }

            override fun onPageSelected(index: Int) {
                DevEngine.getLog()?.dTag(TAG, "索引变动: %s", index)
                if (mLeftScroll) {
                    showToast("往左滑 - 从右往左")
                } else {
                    showToast("往右滑 - 从左往右")
                }
            }
        })
        binding.vidAllowBtn.setOnClickListener {
            binding.vidViewpager.isSlide = true
            showToast(true, "已允许滑动")
        }
        binding.vidBanBtn.setOnClickListener {
            binding.vidViewpager.isSlide = false
            showToast(false, "已禁止滑动")
        }
    }
}