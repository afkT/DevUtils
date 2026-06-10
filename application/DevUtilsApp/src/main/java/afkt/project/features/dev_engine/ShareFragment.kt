package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineShareBinding
import android.view.View
import dev.engine.extensions.share.share_shareImage
import dev.engine.extensions.share.share_shareText
import dev.engine.extensions.share.share_shareUrl
import dev.engine.extensions.toast.toast_showShort
import dev.engine.share.IShareEngine
import dev.utils.app.ActivityUtils

/**
 * detail: Share Engine 分享平台处理
 * @author Ttt
 * Share Engine 默认未配置实现, 需依赖具体分享平台后注册实现
 */
class ShareFragment : AppFragment<FragmentDevEngineShareBinding, ShareViewModel>(
    R.layout.fragment_dev_engine_share, BR.viewModel
)

/**
 * detail: Share Engine 分享平台处理 ViewModel
 * @author Ttt
 */
class ShareViewModel : AppViewModel() {

    val clickShareText = View.OnClickListener { view ->
        val result = ActivityUtils.getActivity(view)?.share_shareText<IShareEngine.EngineItem>(
            params = null, listener = null
        ) ?: false
        "shareText: $result ( 需配置分享平台实现 )".toast_showShort()
    }

    val clickShareUrl = View.OnClickListener { view ->
        val result = ActivityUtils.getActivity(view)?.share_shareUrl<IShareEngine.EngineItem>(
            params = null, listener = null
        ) ?: false
        "shareUrl: $result ( 需配置分享平台实现 )".toast_showShort()
    }

    val clickShareImage = View.OnClickListener { view ->
        val result = ActivityUtils.getActivity(view)?.share_shareImage<IShareEngine.EngineItem>(
            params = null, listener = null
        ) ?: false
        "shareImage: $result ( 需配置分享平台实现 )".toast_showShort()
    }
}