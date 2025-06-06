package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseActivity
import afkt.httpmanager.use.databinding.ActivityMainBinding
import androidx.navigation.findNavController

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel
) {
    override fun initListener() {
        super.initListener()
        // Fragment Navigate 监听
        viewModel.clickNavigateEvent.observe(this) { res ->
            findNavController(binding.navContainer.id)
                .navigate(res.id)
        }
    }
}