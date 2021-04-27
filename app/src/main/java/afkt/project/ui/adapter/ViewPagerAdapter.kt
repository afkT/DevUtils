package afkt.project.ui.adapter

import afkt.project.databinding.ViewPagerItemViewBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

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
        val binding = ViewPagerItemViewBinding.inflate(LayoutInflater.from(container.context))
        // 设置文本
        binding.vidVpivContentTv.text = lists[position % lists.size]
        // 保存 View
        container.addView(binding.root)
        return binding.root
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun isViewFromObject(
        view: View,
        obj: Any
    ): Boolean {
        return view === obj
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        obj: Any
    ) {
        container.removeView(obj as? View)
    }
}