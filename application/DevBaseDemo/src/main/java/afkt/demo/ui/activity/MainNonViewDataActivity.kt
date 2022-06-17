package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.databinding.ActivityMainNonViewdataBinding
import android.os.Bundle
import android.view.View
import dev.base.expand.viewbinding.DevBaseViewBindingActivity

/**
 * detail: Main Activity
 * @author Ttt
 * 非使用 Data 的 ViewBinding
 * 无法使用 DevBaseViewDataBindingActivity 只能使用 DevBaseViewBindingActivity 才能编译
 */
class MainNonViewDataActivity : DevBaseViewBindingActivity<ActivityMainNonViewdataBinding>() {
//class MainActivityNonViewData : DevBaseViewDataBindingActivity<ActivityMainNonViewdataBinding>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_non_viewdata
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vidTv.text = "非使用 Data 的 ViewDataBinding"
    }
}