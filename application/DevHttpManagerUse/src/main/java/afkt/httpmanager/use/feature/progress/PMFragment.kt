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
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import dev.utils.common.StringUtils

class PMFragment : BaseFragment<FragmentProgressManagerBinding, PMViewModel>(
    R.layout.fragment_progress_manager, BR.viewModel, simple_Agile = { frg ->
        if (frg is PMFragment) {
            frg.apply {
                binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                    override fun onLeftClick(titleBar: TitleBar) {
                        findNavController().popBackStack()
                    }
                })
            }
        }
    }
) {
    override fun initObserve() {
        super.initObserve()
        // 监听下载消息
        viewModel.downloadMessage.observe(this) { message ->
            if (StringUtils.isNotEmpty(message)) {
                showDownloadPopup(message)
            } else {
                dismissDownloadPopup()
            }
        }
    }

    // ==========
    // = 弹窗相关 =
    // ==========

    // 下载弹窗
    private var _downloadPopup: LoadingPopupView? = null

    private fun downloadPopup(): LoadingPopupView? {
        if (_downloadPopup != null) return _downloadPopup
        try {
            _downloadPopup = XPopup.Builder(requireContext())
                .isViewMode(true)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .autoDismiss(false)
                .asLoading("")
        } catch (_: Exception) {
        }
        return _downloadPopup
    }

    private fun showDownloadPopup(message: String) {
        downloadPopup().hiIfNotNull {
            it.setTitle(message)
            if (!it.isShow) {
                it.show()
            }
        }
    }

    private fun dismissDownloadPopup() {
        downloadPopup()?.dismiss()
    }
}