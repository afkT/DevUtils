package afkt.project.ui.fragment

import afkt.project.R
import afkt.project.base.app.BaseFragment
import afkt.project.databinding.FragmentPaletteBinding
import afkt.project.model.vm.PaletteViewModel
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.palette.graphics.Palette
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.QuickHelper
import dev.utils.app.image.ImageUtils
import dev.utils.common.ColorUtils
import java.lang.ref.WeakReference

fun newPaletteFragment(position: Int) = PaletteFragment().apply {
    arguments = bundleOf(DevFinal.POSITION to position)
}

class PaletteFragment : BaseFragment<FragmentPaletteBinding>() {

    private var palette: Palette? = null

    private var position: Int = 0

    private var wallpaper: WeakReference<Bitmap>? = null

    private val viewModel by activityViewModels<PaletteViewModel>()

    override fun baseContentId(): Int = R.layout.fragment_palette

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(DevFinal.POSITION) ?: 1
        loadPalette()
    }

    override fun initObserve() {
        super.initObserve()

        viewModel.itemPosition.observe(this) { index ->
            if (index == position) {
                palette?.let { viewModel.postPalette(it) }
            }
        }
    }

    private fun loadPalette() {
        if (palette == null) {
            Palette.from(getBitmap()).generate {
                palette = it
                refreshUI()
            }
        } else {
            refreshUI()
        }
    }

    private fun getBitmap(): Bitmap {
        var bitmap = wallpaper?.get()
        if (bitmap != null) return bitmap
        var rawId = ResourceUtils.getRawId("wallpaper_${position}")
        var stream = ResourceUtils.openRawResource(rawId)
        bitmap = ImageUtils.decodeStream(stream)
        wallpaper = WeakReference<Bitmap>(bitmap)
        return bitmap
    }

    private fun refreshUI() {
        palette?.let {
            if (position == viewModel.itemPosition.value) {
                viewModel.postPalette(it)
            }
            // 设置图片
            binding.vidFpBgIgview.setImageBitmap(getBitmap())

//            // 获取某种特性颜色的样品
//            it.vibrantSwatch?.run {
//                // 谷歌推荐 : 图片的整体的颜色 rgb 的混合值 ( 主色调 )
//                val rgb: Int = rgb
//                // 谷歌推荐 : 图片中间的文字颜色
//                val bodyTextColor: Int = bodyTextColor
//                // 谷歌推荐 : 作为标题的颜色 ( 有一定的和图片的对比度的颜色值 )
//                val titleTextColor: Int = titleTextColor
//                // 颜色向量
//                val hsl: FloatArray = hsl
//                // 分析该颜色在图片中所占的像素多少值
//                val population: Int = population
//            }

            // 获取最活跃的颜色信息 ( 也可以说整个图片出现最多的颜色 )
            it.vibrantSwatch?.run {
                QuickHelper(binding.vidFpTv1)
                    .setBackgroundColor(rgb)
                    .setText(ColorUtils.intToRgbString(rgb).toUpperCase())
                    .setTextColor(bodyTextColor)
            }
            // 获取活跃明亮的颜色信息
            it.lightVibrantSwatch?.run {
                QuickHelper(binding.vidFpTv2)
                    .setBackgroundColor(rgb)
                    .setText(ColorUtils.intToRgbString(rgb).toUpperCase())
                    .setTextColor(bodyTextColor)
            }
            // 获取活跃深色的颜色信息
            it.darkVibrantSwatch?.run {
                QuickHelper(binding.vidFpTv3)
                    .setBackgroundColor(rgb)
                    .setText(ColorUtils.intToRgbString(rgb).toUpperCase())
                    .setTextColor(bodyTextColor)
            }
            // 获取柔和的颜色信息
            it.mutedSwatch?.run {
                QuickHelper(binding.vidFpTv4)
                    .setBackgroundColor(rgb)
                    .setText(ColorUtils.intToRgbString(rgb).toUpperCase())
                    .setTextColor(bodyTextColor)
            }
            // 获取柔和明亮的颜色信息
            it.lightMutedSwatch?.run {
                QuickHelper(binding.vidFpTv5)
                    .setBackgroundColor(rgb)
                    .setText(ColorUtils.intToRgbString(rgb).toUpperCase())
                    .setTextColor(bodyTextColor)
            }
            // 获取柔和深色的颜色信息
            it.darkMutedSwatch?.run {
                QuickHelper(binding.vidFpTv6)
                    .setBackgroundColor(rgb)
                    .setText(ColorUtils.intToRgbString(rgb).toUpperCase())
                    .setTextColor(bodyTextColor)
            }
        }
    }
}