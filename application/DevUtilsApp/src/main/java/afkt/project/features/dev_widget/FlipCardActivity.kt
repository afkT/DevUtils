package afkt.project.features.dev_widget

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.helper.IMAGE_ROUND_10
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.ActivityFlipCardBinding
import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import dev.base.DevSource
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.timer.DevTimer
import dev.widget.ui.FlipCardView

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
class FlipCardActivity : BaseProjectActivity<ActivityFlipCardBinding, AppViewModel>(
    R.layout.activity_flip_card, simple_Agile = {
        if (it is FlipCardActivity) {
            it.apply {
//                // 也可以自定义动画效果
//                binding.vidFcv1.setInOutAnimator(
//                    AnimatorInflater.loadAnimator(mContext, R.animator.dev_flip_card_in),
//                    AnimatorInflater.loadAnimator(mContext, R.animator.dev_flip_card_out)
//                )
//
//                binding.vidFcv1.adapter = FlipCardAdapter(
//                    mutableListOf(
//                        DevSource.create(R.drawable.bg_wallpaper),
//                        DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_1)),
//                        DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                        DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_2)),
//                    )
//                )
//
//                binding.vidFcv2.adapter = FlipCardAdapter(
//                    mutableListOf(
//                        DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_5)),
//                        DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_4)),
//                        DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                        DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                    )
//                )

                binding.vidFcv3.adapter = FlipCardAdapter(
                    mutableListOf(
                        DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_3))
                    )
                )
                HandlerUtils.postRunnable({
                    binding.vidFcv3.flip()
                }, 1000 * 10)

//                flipTimer = DevTimer.Builder(5000L, 5000L, -1)
//                    .build().setHandler(Handler(Looper.getMainLooper()))
//                    .setCallback { timer: DevTimer?, number: Int, end: Boolean, infinite: Boolean ->
//                        if (!ActivityUtils.isFinishing(mActivity)) {
////                            binding.vidFcv1.flip()
////                            binding.vidFcv2.flip()
//                            binding.vidFcv3.flip()
//                        }
//                    }.start()
            }
        }
    }
) {

    // 翻转定时器
    private var flipTimer: DevTimer? = null

    override fun onDestroy() {
        super.onDestroy()
        flipTimer?.stop()
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