package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineImageBinding
import afkt.project.model.engine.IMAGE_ROUND_10
import afkt.project.model.helper.RandomHelper
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import dev.engine.extensions.image.clearMemoryCache
import dev.engine.extensions.image.display
import dev.engine.extensions.toast.toast_showShort
import dev.simple.extensions.image.toImageConfig
import dev.simple.extensions.toSource
import java.lang.ref.WeakReference

/**
 * detail: Image Engine 图片加载、下载、转格式等
 * @author Ttt
 */
class ImageFragment : AppFragment<FragmentDevEngineImageBinding, ImageViewModel>(
    R.layout.fragment_dev_engine_image, BR.viewModel
) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(binding.vidIv)
    }
}

/**
 * detail: Image Engine 图片加载、下载、转格式等 ViewModel
 * @author Ttt
 */
class ImageViewModel : AppViewModel() {

    // 图片承载控件 ( 弱引用持有 )
    private var imageViewRef: WeakReference<ImageView>? = null

    /**
     * 初始化图片承载控件
     * @param imageView Image View
     */
    fun initialize(imageView: ImageView) {
        imageViewRef = WeakReference(imageView)
    }

    val clickDisplayNetwork = View.OnClickListener {
        imageViewRef?.get()?.display(source = RandomHelper.randomImage200X().toSource())
    }

    val clickDisplayRound = View.OnClickListener {
        imageViewRef?.get()?.display(
            source = RandomHelper.randomImage200X().toSource(),
            config = IMAGE_ROUND_10.toImageConfig()
        )
    }

    val clickDisplayResource = View.OnClickListener {
        imageViewRef?.get()?.display(source = R.mipmap.icon_launcher.toSource())
    }

    val clickClearMemoryCache = View.OnClickListener {
        imageViewRef?.get()?.context?.clearMemoryCache()
        toast_showShort(text = "clearMemoryCache 清除内存缓存完成")
    }
}