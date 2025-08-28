package afkt.httpmanager.use.feature.progress

import afkt.httpmanager.use.BR
import afkt.httpmanager.use.R
import afkt.httpmanager.use.base.BaseFragment
import afkt.httpmanager.use.databinding.FragmentProgressManagerBinding
import androidx.navigation.fragment.findNavController
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import dev.base.simple.extensions.asFragment
import dev.simple.core.utils.hi.hiif.hiIfNotNull
import dev.utils.common.StringUtils

class PMFragment : BaseFragment<FragmentProgressManagerBinding, PMViewModel>(
    R.layout.fragment_progress_manager, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<PMFragment> {
            binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(titleBar: TitleBar) {
                    findNavController().popBackStack()
                }
            })
        }
    }
) {
    override fun initObserve() {
        super.initObserve()
        // 监听下载消息
        viewModel.downloadMessage.observe(this) {
            messageConsumer(it)
        }
        // 监听上传消息
        viewModel.uploadMessage.observe(this) {
            messageConsumer(it)
        }
    }

    /**
     * Message consumer
     * @param message Message Content
     */
    private fun messageConsumer(message: String) {
        if (StringUtils.isNotEmpty(message)) {
            showProgressPopup(message)
        } else {
            dismissProgressPopup()
        }
    }

    // ==========
    // = 弹窗相关 =
    // ==========

    // 上传、下载进度信息弹窗
    private var _progressPopup: LoadingPopupView? = null

    private fun progressPopup(): LoadingPopupView? {
        if (_progressPopup != null) return _progressPopup
        try {
            _progressPopup = XPopup.Builder(requireContext())
                .isViewMode(true)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("")
        } catch (_: Exception) {
        }
        return _progressPopup
    }

    private fun showProgressPopup(message: String) {
        progressPopup().hiIfNotNull {
            it.setTitle(message)
            if (!it.isShow) {
                it.show()
            }
        }
    }

    private fun dismissProgressPopup() {
        progressPopup()?.dismiss()
    }
}