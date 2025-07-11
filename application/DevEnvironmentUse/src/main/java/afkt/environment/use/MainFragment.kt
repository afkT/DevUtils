package afkt.environment.use

import afkt.environment.use.base.BaseFragment
import afkt.environment.use.databinding.FragmentMainBinding
import dev.simple.app.base.FragmentVMType

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main, BR.viewModel, FragmentVMType.ACTIVITY
)