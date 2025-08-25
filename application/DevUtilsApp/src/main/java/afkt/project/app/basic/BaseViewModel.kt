package afkt.project.app.basic

import afkt.project.model.basic.IntentData
import android.os.Bundle
import dev.base.simple.contracts.viewmodel.LifecycleViewModel

/**
 * detail: Base ViewModel
 * @author Ttt
 */
open class BaseViewModel : LifecycleViewModel() {

    // Intent 传参读写辅助类
    val intentData = IntentData.with()

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 初始化数据【读取数据并存储】
     */
    fun intentReader(args: Bundle?) {
        if (args != null) {
            intentData.reader(args)
        }
    }
}