package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineMediaSelectorBinding
import afkt.project.model.engine.createGalleryConfig
import android.view.View
import dev.engine.extensions.media.media_deleteAllCacheDirFile
import dev.engine.extensions.media.media_openCamera
import dev.engine.extensions.media.media_openGallery
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.ActivityUtils

/**
 * detail: Media Selector Engine 多媒体资源选择
 * @author Ttt
 */
class MediaSelectorFragment :
    AppFragment<FragmentDevEngineMediaSelectorBinding, MediaSelectorViewModel>(
        R.layout.fragment_dev_engine_media_selector, BR.viewModel
    )

/**
 * detail: Media Selector Engine 多媒体资源选择 ViewModel
 * @author Ttt
 */
class MediaSelectorViewModel : AppViewModel() {

    val clickOpenGallery = View.OnClickListener { view ->
        ActivityUtils.getActivity(view)?.let { activity ->
            activity.media_openGallery(config = activity.createGalleryConfig())
        }
    }

    val clickOpenCamera = View.OnClickListener { view ->
        val result = ActivityUtils.getActivity(view)?.media_openCamera() ?: false
        if (!result) toast_showShort(text = "打开相机失败 ( 需配置 MediaConfig )")
    }

    val clickDeleteCache = View.OnClickListener { view ->
        view.context.media_deleteAllCacheDirFile()
        toast_showShort(text = "deleteAllCacheDirFile 清除缓存完成")
    }
}