package afkt.retrofit.use

import afkt.retrofit.use.base.BaseActivity
import afkt.retrofit.use.databinding.ActivityMainBinding
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