package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.base.BaseApplication
import afkt.demo.model.ApplicationViewModel
import afkt.demo.ui.fragment.ApplicationViewModelFragment
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.os.Handler
import android.view.View
import dev.base.expand.viewmodel.DevBaseViewModelActivity
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.ColorUtils

/**
 * detail: Main Application ViewModel Activity
 * @author Ttt
 */
class MainApplicationViewModelActivity : DevBaseViewModelActivity<ApplicationViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_application_view_model
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        ViewHelper.get().setText(
            "Application ViewModel Title",
            findViewById(R.id.vid_title_tv)
        ).setBackgroundColor(
            ColorUtils.getRandomColor(), findViewById(R.id.vid_view)
        )

        // 嵌套处理
        ApplicationViewModelFragment.commit(supportFragmentManager, R.id.vid_fl, 0, 4)
    }

    override fun initViewModel() {
        viewModel =
            getAppViewModel(BaseApplication.getApplication(), ApplicationViewModel::class.java)!!
        // 复用方法进行监听
        ViewModelTempUtils.observe(TAG, this, viewModel)
        // 临时改变值
        Handler().postDelayed({
            viewModel.number.value = Int.MAX_VALUE
        }, 2000)
    }
}