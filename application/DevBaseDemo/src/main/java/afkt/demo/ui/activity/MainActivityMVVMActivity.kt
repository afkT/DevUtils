package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.databinding.ActivityMainActivityMvvmBinding
import afkt.demo.model.ActivityViewModel
import afkt.demo.ui.fragment.ActivityMVVMFragment
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.view.View
import dev.base.expand.mvvm.DevBaseMVVMActivity
import dev.utils.app.HandlerUtils
import dev.utils.common.ColorUtils

/**
 * detail: Main Activity MVVM Activity
 * @author Ttt
 */
class MainActivityMVVMActivity :
    DevBaseMVVMActivity<ActivityMainActivityMvvmBinding, ActivityViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_activity_mvvm
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding.title = "Activity MVVM Title"

        // 随机设置背景色
        binding.vidInclude.color = ColorUtils.getRandomColor()

        // 嵌套处理
        ActivityMVVMFragment.commit(supportFragmentManager, R.id.vid_fl, 0, 4)
    }

    override fun initViewModel() {
        viewModel = getActivityViewModel(ActivityViewModel::class.java)!!
        // 复用方法进行监听
        ViewModelTempUtils.observe(TAG, this, viewModel)
        // 临时改变值
        HandlerUtils.postRunnable({
            viewModel.number.value = Int.MAX_VALUE
        }, 2000)
    }
}