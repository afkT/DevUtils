package afkt.project.feature.dev_widget.flip_card

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityFlipCardBinding
import com.therouter.router.Route
import dev.base.DevSource
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.timer.DevTimer

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
@Route(path = RouterPath.DEV_WIDGET.FlipCardActivity_PATH)
class FlipCardActivity : BaseActivity<ActivityFlipCardBinding>() {

    // 翻转定时器
    private var flipTimer: DevTimer? = null

    override fun baseLayoutId(): Int = R.layout.activity_flip_card

    override fun initView() {
        super.initView()

//        // 也可以自定义动画效果
//        binding.vidFcv1.setInOutAnimator(
//            AnimatorInflater.loadAnimator(mContext, R.animator.dev_flip_card_in),
//            AnimatorInflater.loadAnimator(mContext, R.animator.dev_flip_card_out)
//        )

//        binding.vidFcv1.adapter = FlipCardAdapter(
//            mutableListOf(
//                DevSource.create(R.drawable.bg_wallpaper),
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_1)),
//                DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_2)),
//            )
//        )
//
//        binding.vidFcv2.adapter = FlipCardAdapter(
//            mutableListOf(
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_5)),
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_4)),
//                DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//            )
//        )

        binding.vidFcv3.adapter = FlipCardAdapter(
            mutableListOf(
                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_3))
            )
        )
        HandlerUtils.postRunnable({
            binding.vidFcv3.flip()
        }, 1000 * 10)

//        flipTimer = DevTimer.Builder(5000L, 5000L, -1)
//            .build().setHandler(Handler())
//            .setCallback { timer: DevTimer?, number: Int, end: Boolean, infinite: Boolean ->
//                if (!ActivityUtils.isFinishing(mActivity)) {
////                    binding.vidFcv1.flip()
////                    binding.vidFcv2.flip()
//                    binding.vidFcv3.flip()
//                }
//            }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        flipTimer?.stop()
    }
}