package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseFragment
import afkt.httpmanager.use.databinding.FragmentMainBinding
import dev.base.simple.FragmentVMType

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main, BR.viewModel,
    FragmentVMType.ACTIVITY
)