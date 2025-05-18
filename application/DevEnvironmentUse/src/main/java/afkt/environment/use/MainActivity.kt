package afkt.environment.use

import afkt.environment.use.base.BaseActivity
import afkt.environment.use.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment

class MainActivity : BaseActivity<ActivityMainBinding, AppViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
        if (act is MainActivity) {
            act.apply {
                viewModel.clickCustomEvent.observe(this) {
                    val fragment = supportFragmentManager.findFragmentById(
                        binding.navContainer.id
                    ) as NavHostFragment
                    val navController = fragment.navController
                    // 显示【自定义】切换环境【UI、功能】Fragment
                    navController.navigate(R.id.CustomFragment)
                }
                viewModel.backPressedEvent.observe(this) {
                    val fragment = supportFragmentManager.findFragmentById(
                        binding.navContainer.id
                    ) as NavHostFragment
                    val navController = fragment.navController
                    navController.popBackStack()
                }
            }
        }
    }
)