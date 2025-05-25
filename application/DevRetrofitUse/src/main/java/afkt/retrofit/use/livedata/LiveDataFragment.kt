package afkt.retrofit.use.livedata

import afkt.retrofit.use.BR
import afkt.retrofit.use.R
import afkt.retrofit.use.base.AppApplication
import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentLivedataBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class LiveDataFragment : BaseFragment<FragmentLivedataBinding, LiveDataViewModel>(
    R.layout.fragment_livedata, BR.viewModel, simple_Agile = { frg ->
        if (frg is LiveDataFragment) {
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