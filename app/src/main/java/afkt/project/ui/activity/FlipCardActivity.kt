package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityFlipCardBinding
import afkt.project.ui.adapter.FlipCardAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.DevSource
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.timer.DevTimer

/**
 * detail: 翻转卡片 View
 * @author Ttt
 */
@Route(path = RouterPath.FlipCardActivity_PATH)
class FlipCardActivity : BaseActivity<ActivityFlipCardBinding>() {

    // 翻转定时器
    private var flipTimer: DevTimer? = null

    override fun baseLayoutId(): Int = R.layout.activity_flip_card

    override fun initView() {
        super.initView()

//        // 也可以自定义动画效果
//        binding.vidAfcView1.setInOutAnimator(
//            AnimatorInflater.loadAnimator(mContext, R.animator.dev_flip_card_in),
//            AnimatorInflater.loadAnimator(mContext, R.animator.dev_flip_card_out)
//        )

//        binding.vidAfcView1.adapter = FlipCardAdapter(
//            mutableListOf(
//                DevSource.create(R.drawable.bg_wallpaper),
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_1)),
//                DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_2)),
//            )
//        )
//
//        binding.vidAfcView2.adapter = FlipCardAdapter(
//            mutableListOf(
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_5)),
//                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_4)),
//                DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//                DevSource.create("https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"),
//            )
//        )

        binding.vidAfcView3.adapter = FlipCardAdapter(
            mutableListOf(
                DevSource.create(ResourceUtils.openRawResource(R.raw.wallpaper_3))
            )
        )
        HandlerUtils.postRunnable({
            binding.vidAfcView3.flip()
        }, 1000 * 10)

//        flipTimer = DevTimer.Builder(5000L, 5000L, -1)
//            .build().setHandler(Handler())
//            .setCallback { timer: DevTimer?, number: Int, end: Boolean, infinite: Boolean ->
//                if (!ActivityUtils.isFinishing(mActivity)) {
////                    binding.vidAfcView1.flip()
////                    binding.vidAfcView2.flip()
//                    binding.vidAfcView3.flip()
//                }
//            }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        flipTimer?.stop()
    }
}