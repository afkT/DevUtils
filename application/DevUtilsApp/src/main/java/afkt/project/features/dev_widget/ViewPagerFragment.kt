package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.AdapterItemViewPagerBinding
import afkt.project.databinding.FragmentDevWidgetViewPagerBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import dev.base.simple.extensions.asFragment
import dev.expand.engine.log.log_dTag
import dev.expand.engine.toast.toast_showShort
import dev.widget.custom.CustomViewPager

/**
 * detail: ViewPager 滑动监听、控制滑动
 * @author Ttt
 */
class ViewPagerFragment : AppFragment<FragmentDevWidgetViewPagerBinding, ViewPagerViewModel>(
    R.layout.fragment_dev_widget_view_pager, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<ViewPagerFragment> {
            val lists = mutableListOf(
                "P1", "P2", "P3", "P4"
            )
            binding.vidVp.adapter = ViewPagerAdapter(lists)
            binding.vidVp.setCurrentItem(lists.size * 100, false)
            binding.vidVp.setOnPageChangeListener(object : CustomViewPager.OnDirectionListener() {
                override fun onSlideDirection(
                    left: Boolean,
                    right: Boolean
                ) {
                    if (left && !right) {
                        TAG.log_dTag(message = "往左滑 - 从右往左")
                    } else {
                        TAG.log_dTag(message = "往右滑 - 从左往右")
                    }
                }

                override fun onPageSelected(index: Int) {
                    TAG.log_dTag(
                        message = "索引变动: $index"
                    )
                    if (mLeftScroll) {
                        toast_showShort(text = "往左滑 - 从右往左")
                    } else {
                        toast_showShort(text = "往右滑 - 从左往右")
                    }
                }
            })
            viewModel.clickEnable = View.OnClickListener {
                binding.vidVp.isSlide = true
                toast_showShort(text = "已允许滑动")
            }
            viewModel.clickDisable = View.OnClickListener {
                binding.vidVp.isSlide = false
                toast_showShort(text = "已禁止滑动")
            }
        }
    }
)

class ViewPagerViewModel : AppViewModel() {

    var clickEnable = View.OnClickListener { view -> }

    var clickDisable = View.OnClickListener { view -> }
}

/**
 * detail: ViewPager 适配器
 * @author Ttt
 */
class ViewPagerAdapter(
    private val lists: List<String>
) : PagerAdapter() {

    override fun instantiateItem(
        container: ViewGroup,
        position: Int
    ): Any {
        return AdapterItemViewPagerBinding.inflate(
            LayoutInflater.from(container.context),
            container, true
        ).apply {
            // 设置文本
            vidContentTv.text = lists[position % lists.size]
        }.root
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun isViewFromObject(
        view: View,
        obj: Any
    ): Boolean {
        return view == obj
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        obj: Any
    ) {
        container.removeView(obj as? View)
    }
}