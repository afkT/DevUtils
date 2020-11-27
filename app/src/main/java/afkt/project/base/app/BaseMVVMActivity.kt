package afkt.project.base.app

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dev.base.expand.mvvm.DevBaseMVVMActivity

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
abstract class BaseMVVMActivity<VDB : ViewDataBinding, VM : ViewModel> :
    DevBaseMVVMActivity<VDB, VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    override fun baseContentView(): View? {
        return null
    }
}