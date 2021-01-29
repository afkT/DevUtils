package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityViewpager2Binding
import afkt.project.ui.fragment.newPagerFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import dev.utils.app.ResourceUtils

/**
 * detail: ViewPager2
 * @author Ttt
 */
class ViewPager2Activity : BaseActivity<ActivityViewpager2Binding>() {

    override fun baseLayoutId(): Int = R.layout.activity_viewpager2

    override fun initValue() {
        super.initValue()

        val list = mutableListOf<Fragment>()
        list.add(newPagerFragment(1))
        list.add(newPagerFragment(2))
        list.add(newPagerFragment(3))
        list.add(newPagerFragment(4))

        binding.vidAvpViewPager.adapter = MyPagerAdapter(this, list)
    }

    override fun initListener() {
        super.initListener()

        binding.vidAvpViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        binding.vidAvpTab.setTabTextColors(
            ResourceUtils.getColor(R.color.black),
            ResourceUtils.getColor(R.color.white)
        )

        // TabLayout 与 ViewPager2 联动
        TabLayoutMediator(
            binding.vidAvpTab, binding.vidAvpViewPager
        ) { tab, position ->
            tab.text = "Pager-${position}"
        }.attach()
    }

    class MyPagerAdapter(
        val activity: FragmentActivity,
        val list: MutableList<Fragment>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment = list[position]
    }
}