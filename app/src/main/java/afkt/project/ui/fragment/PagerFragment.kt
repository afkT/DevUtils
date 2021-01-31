package afkt.project.ui.fragment

import afkt.project.R
import afkt.project.base.app.BaseFragment
import afkt.project.databinding.FragmentPagerBinding
import afkt.project.ui.widget.VerticalScrollView
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

fun newPagerFragment(position: Int) = PagerFragment().apply {
    arguments = bundleOf(DevFinal.POSITION to position)
}

class PagerFragment : BaseFragment<FragmentPagerBinding>() {

    private var position: Int = 0

    override fun baseContentId(): Int = R.layout.fragment_pager

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(DevFinal.POSITION) ?: 1

        /**
         * 竖屏需要使用 [VerticalScrollView]
         */

        binding.vidFpPrefaceTv.text = ChineseUtils.randomWord(RandomUtils.getRandom(30, 100))
        binding.vidFpTitleTv.text = "${position}.${ChineseUtils.randomWord(5)}"
        binding.vidFpContentTv.text = ChineseUtils.randomWord(400)
        binding.vidFpIgview.setImageBitmap(getBitmap())
    }

    private fun getBitmap(): Bitmap {
        var rawId = ResourceUtils.getRawId("wallpaper_${position}")
        var stream = ResourceUtils.openRawResource(rawId)
        return ImageUtils.decodeStream(stream)
    }
}