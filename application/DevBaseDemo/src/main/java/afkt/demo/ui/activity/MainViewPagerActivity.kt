package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.databinding.ActivityMainViewpagerBinding
import afkt.demo.ui.adapter.MainTabAdapter
import afkt.demo.ui.fragment.ItemValueFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.base.expand.viewbinding.DevBaseViewBindingActivity
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Main Activity
 * @author Ttt
 * 使用 ViewPager2
 */
class MainViewPagerActivity : DevBaseViewBindingActivity<ActivityMainViewpagerBinding>() {

    // Tab Fragment
    private val fragments = ArrayList<Fragment>()

    override fun baseContentId(): Int {
        return R.layout.activity_main_viewpager
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragments.add(ItemValueFragment.get(1))
        fragments.add(ItemValueFragment.get(2))
        fragments.add(ItemValueFragment.get(3))
        fragments.add(ItemValueFragment.get(4))

        binding.vidVp.apply {
            // 设置适配器
            adapter = MainTabAdapter(fragments, this@MainViewPagerActivity)
        }

        ViewHelper.get()
            .setOnClick({
                if (isSelectTab(TAB_1)) {
                    return@setOnClick
                }
                setSelectTab(TAB_1)
            }, binding.vidTab1Tv)
            .setOnClick({
                if (isSelectTab(TAB_2)) {
                    return@setOnClick
                }
                setSelectTab(TAB_2)
            }, binding.vidTab2Tv)
            .setOnClick({
                if (isSelectTab(TAB_3)) {
                    return@setOnClick
                }
                setSelectTab(TAB_3)
            }, binding.vidTab3Tv)
            .setOnClick({
                if (isSelectTab(TAB_4)) {
                    return@setOnClick
                }
                setSelectTab(TAB_4)
            }, binding.vidTab4Tv)
    }

    // Tab 类型索引
    private val TAB_1 = 0
    private val TAB_2 = 1
    private val TAB_3 = 2
    private val TAB_4 = 3

    /**
     * 判断是否选中 Tab 类型
     * @param tabType Int
     */
    private fun isSelectTab(tabType: Int): Boolean {
        return (binding.vidVp.currentItem == tabType)
    }

    /**
     * 设置选中 Tab
     * @param tabType Int
     */
    private fun setSelectTab(tabType: Int) {
        // 滑动到指定 Tab
        binding.vidVp.setCurrentItem(tabType, false)
    }
}