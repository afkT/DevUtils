package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.base.BaseApplication
import afkt.demo.databinding.ActivityMainApplicationMvvmBinding
import afkt.demo.model.ApplicationViewModel
import afkt.demo.ui.fragment.ApplicationMVVMFragment
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.os.Handler
import android.view.View
import dev.base.expand.mvvm.DevBaseMVVMActivity
import dev.utils.common.ColorUtils

/**
 * detail: Main Application MVVM Activity
 * @author Ttt
 */
class MainApplicationMVVMActivity :
    DevBaseMVVMActivity<ActivityMainApplicationMvvmBinding, ApplicationViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_application_mvvm
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding.title = "Application MVVM Title"

        // 随机设置背景色
        binding.vidInclude.color = ColorUtils.getRandomColor()

        // 嵌套处理
        ApplicationMVVMFragment.commit(supportFragmentManager, R.id.vid_fl, 0, 4)
    }

    override fun initViewModel() {
        viewModel = getAppViewModel(
            BaseApplication.getApplication(),
            ApplicationViewModel::class.java
        )!!
        // 复用方法进行监听
        ViewModelTempUtils.observe(TAG, this, viewModel)
        // 临时改变值
        Handler().postDelayed({
            viewModel.number.value = Int.MAX_VALUE
        }, 2000)
    }
}