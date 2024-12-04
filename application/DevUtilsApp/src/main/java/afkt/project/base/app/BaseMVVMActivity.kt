package afkt.project.base.app

import afkt.project.BR
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.therouter.TheRouter
import com.therouter.router.Autowired
import dev.base.expand.mvvm.DevBaseMVVMActivity
import dev.utils.DevFinal

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
abstract class BaseMVVMActivity<VDB : ViewDataBinding, VM : ViewModel> :
    DevBaseMVVMActivity<VDB, VM>() {

    override fun baseContentView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            TheRouter.inject(this)
        } catch (_: Exception) {
        }
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    // ======================
    // = 其他方法 ( 展示所需 ) =
    // ======================

    @JvmField
    @Autowired(name = DevFinal.STR.TITLE)
    var moduleTitle: String? = null

    // ===========
    // = ToolBar =
    // ===========

    /**
     * 初始化 ToolBar
     */
    fun initToolBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            // 给左上角图标的左边加上一个返回的图标
            it.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            it.setDisplayShowTitleEnabled(false)
        }
        // 设置点击事件
        toolbar.setNavigationOnClickListener { finish() }

        // 设置 ToolBar 标题
        toolbar.title = moduleTitle

        // 或用下面设置
        binding.setVariable(BR.title, moduleTitle) // 设置后, 会动态刷新
    }
}