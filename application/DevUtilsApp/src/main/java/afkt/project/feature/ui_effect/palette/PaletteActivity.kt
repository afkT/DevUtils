package afkt.project.feature.ui_effect.palette

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.ActivityPaletteBinding
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
class PaletteActivity : BaseProjectActivity<ActivityPaletteBinding, PaletteViewModel>(
    R.layout.activity_palette, simple_Agile = {
        if (it is PaletteActivity) {
            it.apply {
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
                binding.vidVp.adapter = PalettePagerAdapter(this, list)

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
        }
    }
) {

    class PalettePagerAdapter(
        val activity: FragmentActivity,
        val list: MutableList<Fragment>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment = list[position]
    }
}