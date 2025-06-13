package afkt.environment.use

import afkt.environment.use.base.BaseActivity
import afkt.environment.use.databinding.ActivityMainBinding
import androidx.navigation.findNavController

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
        if (act is MainActivity) {
            act.apply {
                viewModel.clickCustomEvent.observe(this) {
                    // 显示【自定义】切换环境【UI、功能】Fragment
                    findNavController(binding.navContainer.id)
                        .navigate(R.id.CustomFragment)
                }
            }
        }
    }
)