package afkt.demo.base.app

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import dev.base.expand.viewdata.DevBaseViewDataBindingFragment

/**
 * detail: Fragment 基类
 * @author Ttt
 */
abstract class BaseFragment<VDB : ViewDataBinding> : DevBaseViewDataBindingFragment<VDB>() {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    override fun baseContentView(): View? {
        return null
    }
}