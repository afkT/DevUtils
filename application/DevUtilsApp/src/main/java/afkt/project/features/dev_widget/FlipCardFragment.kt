package afkt.project.features.dev_widget

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppContext
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetFlipCardBinding
import afkt.project.model.engine.IMAGE_ROUND_10
import afkt.project.model.helper.RandomHelper
import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import dev.base.DevSource
import dev.base.simple.extensions.asFragment
import dev.engine.extensions.image.display
import dev.simple.extensions.image.toImageConfig
import dev.utils.app.ActivityUtils
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.timer.DevTimer
import dev.widget.ui.FlipCardView

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
class FlipCardFragment : AppFragment<FragmentDevWidgetFlipCardBinding, AppViewModel>(
    R.layout.fragment_dev_widget_flip_card, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<FlipCardFragment> {
            val context = AppContext.context()
            // 自定义动画效果
            binding.vidFcv.setInOutAnimator(
                AnimatorInflater.loadAnimator(context, R.animator.dev_flip_card_in),
                AnimatorInflater.loadAnimator(context, R.animator.dev_flip_card_out)
            )
            // 设置适配器
            binding.vidFcv.adapter = FlipCardAdapter(
                mutableListOf(
                    DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_1)),
                    DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_2)),
                    DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_3)),
                    DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_4)),
                    DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_5)),
                    DevSource.create(RandomHelper.randomImage200X()),
                )
            )
        }
    }
) {

    // 翻转定时器
    private var flipTimer = DevTimer.Builder(2000L, 3000L, -1)
        .build().setHandler(HandlerUtils.getMainHandler())
        .setCallback { timer: DevTimer?, number: Int, end: Boolean, infinite: Boolean ->
            if (!ActivityUtils.isFinishing(activity)) {
                binding.vidFcv.flip()
            }
        }

    override fun onResume() {
        super.onResume()
        flipTimer.start()
    }

    override fun onPause() {
        super.onPause()
        flipTimer.stop()
    }
}

/**
 * detail: 翻转卡片适配器
 * @author Ttt
 */
class FlipCardAdapter(val lists: List<DevSource>) : FlipCardView.Adapter {

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun getItemView(
        context: Context?,
        position: Int,
        isFrontView: Boolean
    ): View? {
        context?.let {
            val imageView = AppCompatImageView(it)
            imageView.display(
                source = lists[position],
                config = IMAGE_ROUND_10.toImageConfig()
            )
            return imageView
        }
        return null
    }
}