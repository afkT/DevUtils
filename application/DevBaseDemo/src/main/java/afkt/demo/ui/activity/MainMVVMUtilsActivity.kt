package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.databinding.ActivityMainMvvmUtilsBinding
import afkt.demo.model.ActivityViewModel
import afkt.demo.ui.fragment.MVVMUtilsFragment
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.os.Handler
import android.view.View
import dev.base.expand.mvvm.DevBaseMVVMActivity
import dev.base.utils.ViewModelUtils
import dev.utils.common.ColorUtils

/**
 * detail: MVVM Utils Activity
 * @author Ttt
 */
class MainMVVMUtilsActivity :
    DevBaseMVVMActivity<ActivityMainMvvmUtilsBinding, ActivityViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main_mvvm_utils
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding.title = "MVVM Utils Title"

        // 随机设置背景色
        binding.vidInclude.color = ColorUtils.getRandomColor()

        // 嵌套处理
        MVVMUtilsFragment.commit(supportFragmentManager, R.id.vid_fl, 0, 4)
    }

    override fun initViewModel() {
        viewModel = ViewModelUtils.getActivityViewModel(
            viewModelAssist,
            this,
            ActivityViewModel::class.java
        )!!

//        viewModel = ViewModelUtils.getActivityViewModel(this, ActivityViewModel::class.java)!!
        // 复用方法进行监听
        ViewModelTempUtils.observe(TAG, this, viewModel)
        // 临时改变值
        Handler().postDelayed({
            viewModel.number.value = Int.MAX_VALUE
        }, 2000)
    }
}