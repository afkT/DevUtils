package afkt.httpmanager.use.feature.progress

import afkt.httpmanager.use.BR
import afkt.httpmanager.use.R
import afkt.httpmanager.use.base.BaseFragment
import afkt.httpmanager.use.databinding.FragmentProgressManagerBinding
import androidx.navigation.fragment.findNavController
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

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
)