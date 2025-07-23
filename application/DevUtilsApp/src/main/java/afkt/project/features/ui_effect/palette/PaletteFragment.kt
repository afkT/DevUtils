package afkt.project.features.ui_effect.palette

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.databinding.FragmentUiEffectPaletteBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.asFragment

/**
 * detail: Palette 调色板
 * @author Ttt
 */
class PaletteFragment : AppFragment<FragmentUiEffectPaletteBinding, PaletteViewModel>(
    R.layout.fragment_ui_effect_palette, vmType = FragmentVMType.APPLICATION,
    simple_Agile = { frg ->
        frg.asFragment<PaletteFragment> {
            viewModel.paletteColor.observe(this) {
                it.vibrantSwatch?.run {
                    binding.vidTl.setBackgroundColor(rgb)
                    // 更新 TitleView 颜色
                    titleView()?.setLayoutBackground(rgb)
                }
            }
            binding.vidVp.adapter = PalettePagerAdapter(
                this, mutableListOf(
                    newPalettePagerFragment(1), newPalettePagerFragment(2),
                    newPalettePagerFragment(3), newPalettePagerFragment(4),
                    newPalettePagerFragment(5)
                )
            )
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
)

private class PalettePagerAdapter(
    fragment: Fragment,
    val fragments: MutableList<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}