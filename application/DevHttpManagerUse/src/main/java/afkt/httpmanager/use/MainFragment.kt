package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseFragment
import afkt.httpmanager.use.databinding.FragmentMainBinding
import dev.simple.app.base.FragmentVMType

class MainFragment : BaseFragment<FragmentMainBinding, AppViewModel>(
    R.layout.fragment_main, BR.viewModel, FragmentVMType.ACTIVITY
)