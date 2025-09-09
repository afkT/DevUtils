package afkt.project

import afkt.project.app.AppActivity
import afkt.project.app.AppViewModel
import afkt.project.app.appViewModel
import afkt.project.databinding.MainActivityBinding
import afkt.project.model.button.args
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import dev.utils.DevFinal
import dev.utils.app.assist.lifecycle.fragment.AbstractFragmentLifecycle

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

    // Fragment LifecycleCallbacks 抽象类
    override fun fragmentLifecycleImpl(): AbstractFragmentLifecycle? {
        return object : AbstractFragmentLifecycle() {
            override fun onFragmentResumed(
                fm: FragmentManager,
                fragment: Fragment
            ) {
                Log.d("QWERQAZZXCV", "【Lifecycle】onFragmentResumed() => $fragment")
            }
        }
    }
}