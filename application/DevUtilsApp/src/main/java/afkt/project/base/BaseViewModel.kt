package afkt.project.base

import afkt.project.model.data.IntentData
import android.os.Bundle
import dev.simple.app.BaseAppViewModel

/**
 * detail: Base ViewModel
 * @author Ttt
 */
open class BaseViewModel : BaseAppViewModel() {

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