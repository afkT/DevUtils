package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.base.app.BaseActivity
import afkt.demo.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import dev.utils.common.ColorUtils

/**
 * detail: Main Activity
 * @author Ttt
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.title = "DataBinding Title"

        // 随机设置背景色
        binding.vidInclude.color = ColorUtils.getRandomColor()

////        // 跳转校验
////        startActivity(Intent(this, MainNonViewDataActivity::class.java))
////        startActivity(Intent(this, MainBaseContentActivity::class.java))
////        startActivity(Intent(this, MainApplicationViewModelActivity::class.java))
////        startActivity(Intent(this, MainApplicationMVVMActivity::class.java))
////        startActivity(Intent(this, MainActivityMVVMActivity::class.java))
////        startActivity(Intent(this, MainFragmentMVVMActivity::class.java))
////        startActivity(Intent(this, MainFragmentParentMVVMActivity::class.java))
////        startActivity(Intent(this, MainBaseContentVMImplActivity::class.java))
////        startActivity(Intent(this, MainMVVMUtilsActivity::class.java))
//        startActivity(Intent(this, MainViewPagerActivity::class.java))

        // 跳转校验
        startActivity(Intent(this, MainNonViewDataActivity::class.java))
        startActivity(Intent(this, MainBaseContentActivity::class.java))
        startActivity(Intent(this, MainApplicationViewModelActivity::class.java))
        startActivity(Intent(this, MainApplicationMVVMActivity::class.java))
        startActivity(Intent(this, MainActivityMVVMActivity::class.java))
        startActivity(Intent(this, MainFragmentMVVMActivity::class.java))
        startActivity(Intent(this, MainFragmentParentMVVMActivity::class.java))
        startActivity(Intent(this, MainBaseContentVMImplActivity::class.java))
        startActivity(Intent(this, MainMVVMUtilsActivity::class.java))
        startActivity(Intent(this, MainViewPagerActivity::class.java))
    }
}