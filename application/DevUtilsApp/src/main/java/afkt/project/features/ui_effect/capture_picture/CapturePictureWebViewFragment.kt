package afkt.project.features.ui_effect.capture_picture

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectCapturePictureWebViewBinding
import dev.base.simple.extensions.asFragment
import dev.utils.app.CapturePictureUtils

/**
 * detail: CapturePictureUtils WebView 截图
 * @author Ttt
 */
class CapturePictureWebViewFragment : AppFragment<FragmentUiEffectCapturePictureWebViewBinding, AppViewModel>(
    R.layout.fragment_ui_effect_capture_picture_web_view, simple_Agile = { frg ->
        frg.asFragment<CapturePictureWebViewFragment> {
            setTitleBarRight("截图") { view ->
                val bitmap = CapturePictureUtils.snapshotByWebView(binding.vidWv)
                CapturePictureFragment.saveBitmap("web_view.jpg", bitmap)
            }
            // 加载网页
            binding.vidWv.loadUrl("https://www.csdn.net/")
        }
    }
)