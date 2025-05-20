package afkt.retrofit.use

import afkt.retrofit.use.base.BaseActivity
import afkt.retrofit.use.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment

class MainActivity : BaseActivity<ActivityMainBinding, AppViewModel>(
    R.layout.activity_main, simple_Agile = { act ->
        if (act is MainActivity) {
            act.apply {
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