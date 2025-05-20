package afkt.retrofit.use

import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, AppViewModel>(
    R.layout.fragment_main, BR.viewModel
)