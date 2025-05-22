package afkt.retrofit.use.simple

import afkt.retrofit.use.BR
import afkt.retrofit.use.R
import afkt.retrofit.use.base.AppApplication
import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentSimpleBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class SimpleFragment : BaseFragment<FragmentSimpleBinding, SimpleViewModel>(
    R.layout.fragment_simple, BR.viewModel, simple_Agile = { frg ->
        if (frg is SimpleFragment) {
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