package afkt.retrofit.use.request

import afkt.retrofit.use.BR
import afkt.retrofit.use.R
import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentRequestBinding
import androidx.navigation.fragment.findNavController
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import dev.simple.app.base.asFragment

class RequestFragment : BaseFragment<FragmentRequestBinding, RequestViewModel>(
    R.layout.fragment_request, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<RequestFragment> {
            binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(titleBar: TitleBar) {
                    findNavController().popBackStack()
                }
            })
        }
    }
)