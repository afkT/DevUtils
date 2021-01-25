package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityViewPagerBinding
import afkt.project.ui.adapter.ViewPagerAdapter
import dev.engine.log.DevLogEngine
import dev.widget.custom.CustomViewPager.OnDirectionListener
import java.util.*

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
class ViewPagerActivity : BaseActivity<ActivityViewPagerBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_view_pager

    override fun initValue() {
        super.initValue()
        val lists: MutableList<String> = ArrayList()
        for (i in 0..4) lists.add((i + 1).toString())
        binding.vidAvpViewpager.adapter = ViewPagerAdapter(lists)
        binding.vidAvpViewpager.setCurrentItem(lists.size * 100, false)
        binding.vidAvpViewpager.setOnPageChangeListener(object : OnDirectionListener() {
            override fun onSlideDirection(
                left: Boolean,
                right: Boolean
            ) {
                if (left && !right) {
                    DevLogEngine.getEngine().dTag(TAG, "往左滑 - 从右往左")
                } else {
                    DevLogEngine.getEngine().dTag(TAG, "往右滑 - 从左往右")
                }
            }

            override fun onPageSelected(index: Int) {
                DevLogEngine.getEngine().dTag(TAG, "索引变动: %s", index)
                if (mLeftScroll) {
                    showToast("往左滑 - 从右往左")
                } else {
                    showToast("往右滑 - 从左往右")
                }
            }
        })
        binding.vidAvpAllowBtn.setOnClickListener {
            binding.vidAvpViewpager.isSlide = true
            showToast(true, "已允许滑动")
        }
        binding.vidAvpBanBtn.setOnClickListener {
            binding.vidAvpViewpager.isSlide = false
            showToast(false, "已禁止滑动")
        }
    }
}