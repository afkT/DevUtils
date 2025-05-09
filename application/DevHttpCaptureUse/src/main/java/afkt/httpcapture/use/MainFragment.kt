package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseFragment
import afkt.httpcapture.use.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, AppViewModel>(
    R.layout.fragment_main, BR.viewModel, simple_Agile = { it ->
        if (it is MainFragment) {

        }
    }
)