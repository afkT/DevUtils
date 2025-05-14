package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseActivity
import afkt.httpmanager.use.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, AppViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
        if (act is MainActivity) {
            act.apply {
            }
        }
    }
)