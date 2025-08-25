package afkt.retrofit.use

import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentMainBinding
import dev.base.simple.FragmentVMType

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main, BR.viewModel, FragmentVMType.ACTIVITY
)