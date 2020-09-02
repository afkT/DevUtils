package afkt.project.base.app

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import dev.base.expand.content.DevBaseContentActivity

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
abstract class BaseMVVMActivity<VDB : ViewDataBinding?> : DevBaseContentActivity() {

    @JvmField
    protected var viewDataBinding: VDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        // MVVM 只需要调用此句绑定
//        viewDataBinding = DataBindingUtil.bind(mContentView)
//        viewDataBinding = DataBindingUtil.setContentView(this, baseLayoutId())

        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消 MVVM 绑定
        viewDataBinding?.unbind()
    }

    override fun baseLayoutView(): View? {
        return null
    }
}