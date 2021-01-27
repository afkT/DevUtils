package afkt.project.base.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dev.base.expand.viewbinding.DevBaseViewBindingFragment

/**
 * detail: Base ViewBinding 基类
 * @author Ttt
 */
abstract class BaseFragment<VB : ViewBinding> :
    DevBaseViewBindingFragment<VB>() {

    override fun baseContentView(): View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
        return mContentView
    }
}