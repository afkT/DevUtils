package afkt.environment.use

import afkt.environment.use.base.BaseActivity
import afkt.environment.use.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, AppViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = {
        if (it is MainActivity) {

        }
    }
)