package afkt.project.feature.dev_widget.view_pager

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityViewPagerBinding
import dev.expand.engine.log.log_dTag
import dev.widget.custom.CustomViewPager.OnDirectionListener

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
class ViewPagerActivity : BaseProjectActivity<ActivityViewPagerBinding, AppViewModel>(
    R.layout.activity_view_pager, simple_Agile = {
        if (it is ViewPagerActivity) {
            it.apply {
                val lists = mutableListOf<String>()
                for (i in 0..4) lists.add((i + 1).toString())
                binding.vidVp.adapter = ViewPagerAdapter(lists)
                binding.vidVp.setCurrentItem(lists.size * 100, false)
                binding.vidVp.setOnPageChangeListener(object : OnDirectionListener() {
                    override fun onSlideDirection(
                        left: Boolean,
                        right: Boolean
                    ) {
                        if (left && !right) {
                            TAG.log_dTag(
                                message = "往左滑 - 从右往左"
                            )
                        } else {
                            TAG.log_dTag(
                                message = "往右滑 - 从左往右"
                            )
                        }
                    }

                    override fun onPageSelected(index: Int) {
                        TAG.log_dTag(
                            message = "索引变动: $index"
                        )
                        if (mLeftScroll) {
                            showToast(true, "往左滑 - 从右往左")
                        } else {
                            showToast(false, "往右滑 - 从左往右")
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
    }
)