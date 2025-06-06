package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseFragment
import afkt.httpcapture.use.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main, BR.viewModel
)