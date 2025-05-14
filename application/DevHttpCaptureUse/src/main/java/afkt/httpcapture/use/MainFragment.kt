package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseFragment
import afkt.httpcapture.use.databinding.FragmentMainBinding
import dev.simple.app.base.FragmentVMType

class MainFragment : BaseFragment<FragmentMainBinding, AppViewModel>(
    R.layout.fragment_main, BR.viewModel, FragmentVMType.ACTIVITY
)