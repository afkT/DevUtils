package afkt.project

import afkt.project.app.AppActivity
import afkt.project.app.AppViewModel
import afkt.project.app.appViewModel
import afkt.project.databinding.MainActivityBinding
import afkt.project.model.button.args
import androidx.navigation.findNavController
import dev.utils.DevFinal

class MainActivity : AppActivity<MainActivityBinding, AppViewModel>(
    R.layout.main_activity
) {
    override fun initObserve() {
        super.initObserve()
        // navigate fragment
        appViewModel().fragNavigate.observe(this) { btn ->
            if (btn.fragmentId == DevFinal.DEFAULT.ERROR_INT) return@observe
            findNavController(binding.navContainer.id)
                .navigate(btn.fragmentId, btn.args())
        }
        // fragment popBackStack
        appViewModel().fragPopBack.observe(this) {
            findNavController(binding.navContainer.id)
                .popBackStack()
        }
    }
}