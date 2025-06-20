package afkt.project

import afkt.project.base.app.AppActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.appViewModel
import afkt.project.databinding.MainActivityBinding
import androidx.navigation.findNavController
import dev.utils.DevFinal

class MainActivity : AppActivity<MainActivityBinding, AppViewModel>(
    R.layout.main_activity
) {
    override fun initObserve() {
        super.initObserve()
        // navigate fragment
        appViewModel().fragNavigate.observe(this) { fragId ->
            if (fragId == DevFinal.DEFAULT.ERROR_INT) return@observe
            findNavController(binding.navContainer.id)
                .navigate(fragId)
        }
        // fragment popBackStack
        appViewModel().fragPopBack.observe(this) {
            findNavController(binding.navContainer.id)
                .popBackStack()
        }
    }
}