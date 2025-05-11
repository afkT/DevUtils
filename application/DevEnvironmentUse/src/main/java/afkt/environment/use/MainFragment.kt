package afkt.environment.use

import afkt.environment.use.base.BaseFragment
import afkt.environment.use.databinding.FragmentMainBinding
import dev.simple.app.base.FragmentVMType

class MainFragment : BaseFragment<FragmentMainBinding, AppViewModel>(
    R.layout.fragment_main, BR.viewModel,
    FragmentVMType.ACTIVITY, simple_Agile = { it ->
        if (it is MainFragment) {

        }
    }
)