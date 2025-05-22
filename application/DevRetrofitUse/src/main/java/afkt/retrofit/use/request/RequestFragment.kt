package afkt.retrofit.use.request

import afkt.retrofit.use.BR
import afkt.retrofit.use.R
import afkt.retrofit.use.base.AppApplication
import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentRequestBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class RequestFragment : BaseFragment<FragmentRequestBinding, RequestViewModel>(
    R.layout.fragment_request, BR.viewModel, simple_Agile = { frg ->
        if (frg is RequestFragment) {
            frg.apply {
                binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                    override fun onLeftClick(titleBar: TitleBar) {
                        AppApplication.viewModel()?.postBackPressed()
                    }
                })
            }
        }
    }
)