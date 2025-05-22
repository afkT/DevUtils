package afkt.retrofit.use

import afkt.retrofit.use.base.AppApplication
import afkt.retrofit.use.base.BaseActivity
import afkt.retrofit.use.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.mvvm.utils.hi.hiif.hiIfNotNull

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel
) {
    override fun initListener() {
        super.initListener()
        // 返回键触发监听
        AppApplication.viewModel().hiIfNotNull {
            it.backPressedEvent.observe(this) {
                navController().popBackStack()
            }
        }
        // Fragment Navigate 监听
        viewModel.clickNavigateEvent.observe(this) { res ->
            navController().navigate(res.id)
        }
    }

    private fun navController(): NavController {
        val fragment = supportFragmentManager.findFragmentById(
            binding.navContainer.id
        ) as NavHostFragment
        return fragment.navController
    }
}