package afkt.project.features.ui_effect

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectViewpager2Binding
import afkt.project.databinding.FragmentUiEffectViewpager2PagerBinding
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import dev.simple.app.base.asFragment
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.assist.ResourceColor
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.image.ImageUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: ViewPager2
 * @author Ttt
 */
class ViewPager2Fragment : AppFragment<FragmentUiEffectViewpager2Binding, AppViewModel>(
    R.layout.fragment_ui_effect_viewpager2, simple_Agile = { frg ->
        frg.asFragment<ViewPager2Fragment> {
            binding.vidVp.adapter = ViewPager2Adapter(
                this, mutableListOf(
                    newPagerFragment(1), newPagerFragment(2),
                    newPagerFragment(3), newPagerFragment(4)
                )
            )
            binding.vidTl.setTabTextColors(
                ResourceColor.get().getColor(R.color.black),
                ResourceColor.get().getColor(R.color.white)
            )
            // TabLayout 与 ViewPager2 联动
            TabLayoutMediator(
                binding.vidTl, binding.vidVp
            ) { tab, position ->
                tab.text = "Pager-${position + 1}"
            }.attach()
        }
    }
) {
    override fun initView() {
        super.initView()
        // 设置右边切换按钮
        setTitleBarRight("切换") { view ->
            showMenuPop(view)
        }
    }

    private fun showMenuPop(view: View) {
        val popupView = ViewPager2DirectionMenuPopup(view.context, menuClick)
        XPopup.Builder(view.context)
            .isDestroyOnDismiss(true)
            .hasShadowBg(false)
            .atView(view)
            .asCustom(popupView)
            .show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private val menuClick = View.OnClickListener {
        when (it.id) {
            R.id.vid_menu_ltr -> {
                binding.vidTl.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding.vidVp.layoutDirection = View.LAYOUT_DIRECTION_LTR
            }

            R.id.vid_menu_rtl -> {
                binding.vidTl.layoutDirection = View.LAYOUT_DIRECTION_RTL
                binding.vidVp.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }

            R.id.vid_menu_horizontal -> {
                binding.vidVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }

            R.id.vid_menu_vertical -> {
                binding.vidVp.orientation = ViewPager2.ORIENTATION_VERTICAL
            }

            R.id.vid_menu_reset -> {
                binding.vidTl.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding.vidVp.layoutDirection = View.LAYOUT_DIRECTION_LTR
                binding.vidVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }
        binding.vidVp.adapter?.notifyDataSetChanged()
    }
}

private class ViewPager2Adapter(
    fragment: Fragment,
    val fragments: MutableList<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}

@SuppressLint("ViewConstructor")
class ViewPager2DirectionMenuPopup(
    context: Context,
    val listener: OnClickListener
) : AttachPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.popup_viewpager2_direction
    }

    override fun onCreate() {
        super.onCreate()
        ViewHelper.get().setOnClick(
            { view ->
                listener.onClick(view)
                dismiss()
            }, ViewUtils.findViewById(this, R.id.vid_menu_ltr),
            ViewUtils.findViewById(this, R.id.vid_menu_rtl),
            ViewUtils.findViewById(this, R.id.vid_menu_horizontal),
            ViewUtils.findViewById(this, R.id.vid_menu_vertical),
            ViewUtils.findViewById(this, R.id.vid_menu_reset)
        )
    }
}

// ========================================
// = 创建 ViewPager2 Adapter Item Fragment =
// ========================================

fun newPagerFragment(position: Int) = PagerFragment().apply {
    arguments = bundleOf(DevFinal.STR.POSITION to position)
}

class PagerFragment : AppFragment<FragmentUiEffectViewpager2PagerBinding, AppViewModel>(
    R.layout.fragment_ui_effect_viewpager2_pager
) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(DevFinal.STR.POSITION) ?: 1
        val titleText = "${position}.${ChineseUtils.randomWord(5)}"
        binding.vidPrefaceTv.text = ChineseUtils.randomWord(RandomUtils.getRandom(30, 100))
        binding.vidTitleTv.text = titleText
        binding.vidContentTv.text = ChineseUtils.randomWord(400)
        binding.vidIv.setImageBitmap(getBitmap(position))
    }

    private fun getBitmap(position: Int): Bitmap {
        val rawId = ResourceUtils.getRawId("wallpaper_${position}")
        val stream = ResourceUtils.openRawResource(rawId)
        return ImageUtils.decodeStream(stream)
    }
}