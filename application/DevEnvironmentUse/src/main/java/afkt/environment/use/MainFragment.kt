package afkt.environment.use

import afkt.environment.use.base.BaseFragment
import afkt.environment.use.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding, AppViewModel>(
    R.layout.fragment_main, BR.viewModel, simple_Agile = { it ->
        if (it is MainFragment) {

        }
    }
)