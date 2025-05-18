package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseActivity
import afkt.httpcapture.use.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment

class MainActivity : BaseActivity<ActivityMainBinding, AppViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
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