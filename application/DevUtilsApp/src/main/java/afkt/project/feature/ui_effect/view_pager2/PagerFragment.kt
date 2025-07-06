package afkt.project.feature.ui_effect.view_pager2

import afkt.project.R
import afkt.project.app.project.BaseFragment
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
    arguments = bundleOf(DevFinal.STR.POSITION to position)
}

class PagerFragment : BaseFragment<FragmentPagerBinding>() {

    private var position: Int = 0

    override fun baseContentId(): Int = R.layout.fragment_pager

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(DevFinal.STR.POSITION) ?: 1

        /**
         * 竖屏需要使用 [VerticalScrollView]
         */
        val titleText = "${position}.${ChineseUtils.randomWord(5)}"

        binding.vidPrefaceTv.text = ChineseUtils.randomWord(RandomUtils.getRandom(30, 100))
        binding.vidTitleTv.text = titleText
        binding.vidContentTv.text = ChineseUtils.randomWord(400)
        binding.vidIv.setImageBitmap(getBitmap())
    }

    private fun getBitmap(): Bitmap {
        val rawId = ResourceUtils.getRawId("wallpaper_${position}")
        val stream = ResourceUtils.openRawResource(rawId)
        return ImageUtils.decodeStream(stream)
    }
}