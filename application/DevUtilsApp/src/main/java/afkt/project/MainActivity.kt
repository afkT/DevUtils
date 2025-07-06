package afkt.project

import afkt.project.app.AppActivity
import afkt.project.app.AppViewModel
import afkt.project.app.base.appViewModel
import afkt.project.databinding.MainActivityBinding
import afkt.project.model.data.button.fragmentId
import afkt.project.model.data.button.title
import androidx.navigation.findNavController
import dev.utils.DevFinal

class MainActivity : AppActivity<MainActivityBinding, AppViewModel>(
    R.layout.main_activity
) {
    override fun initObserve() {
        super.initObserve()
        // navigate fragment
        appViewModel().fragNavigate.observe(this) { btn ->
            val fragId = btn.fragmentId()
            if (fragId == DevFinal.DEFAULT.ERROR_INT) return@observe
            findNavController(binding.navContainer.id)
                .navigate(fragId, btn.title())
        }
        // fragment popBackStack
        appViewModel().fragPopBack.observe(this) {
            findNavController(binding.navContainer.id)
                .popBackStack()
        }
    }
}