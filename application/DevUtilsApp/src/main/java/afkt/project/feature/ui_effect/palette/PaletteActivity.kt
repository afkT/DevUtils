package afkt.project.feature.ui_effect.palette

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityPaletteBinding
import afkt.project.model.item.RouterPath
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.therouter.router.Route
import dev.utils.app.BarUtils

/**
 * detail: Palette 调色板
 * @author Ttt
 * Android Palette 基本使用
 * @see https://www.jianshu.com/p/d3c13eb700a4
 * Android Material Design 系列之 Palette 开发详解
 * @see https://blog.csdn.net/jaynm/article/details/107076754
 */
@Route(path = RouterPath.UI_EFFECT.PaletteActivity_PATH)
class PaletteActivity : BaseActivity<ActivityPaletteBinding>() {

    private val viewModel by viewModels<PaletteViewModel>()

    override fun baseLayoutId(): Int = R.layout.activity_palette

    override fun initValue() {
        super.initValue()

        viewModel.paletteColor.observe(this) {
            it.vibrantSwatch?.run {
                binding.vidTl.setBackgroundColor(rgb)
                toolbar?.let { bar ->
                    bar.setBackgroundColor(rgb)
                    BarUtils.addMarginTopEqualStatusBarHeight(bar)
                }
                BarUtils.setStatusBarColor(mActivity, rgb)
            }
        }

        val list = mutableListOf<Fragment>()
        list.add(newPaletteFragment(1))
        list.add(newPaletteFragment(2))
        list.add(newPaletteFragment(3))
        list.add(newPaletteFragment(4))
        list.add(newPaletteFragment(5))

        binding.vidVp.adapter = MyPagerAdapter(this, list)
    }

    override fun initListener() {
        super.initListener()

        binding.vidVp.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.postItemPosition(position + 1)
            }
        })

        // TabLayout 与 ViewPager2 联动
        TabLayoutMediator(
            binding.vidTl, binding.vidVp
        ) { tab, position ->
            tab.text = "Wallpaper-${position}"
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