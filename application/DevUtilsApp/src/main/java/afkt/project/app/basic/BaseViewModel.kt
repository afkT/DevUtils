package afkt.project.app.basic

import afkt.project.model.basic.IntentData
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
     * @return `true` success, `false` fail
     */
    fun intentReader(activity: Activity?): Boolean {
        return intentReader(activity?.intent)
    }

    /**
     * 初始化数据【读取数据并存储】
     * @return `true` success, `false` fail
     */
    fun intentReader(fragment: Fragment?): Boolean {
        return intentReader(fragment?.arguments)
    }

    /**
     * 初始化数据【读取数据并存储】
     * @return `true` success, `false` fail
     */
    fun intentReader(intent: Intent?): Boolean {
        if (intent != null) {
            intentData.reader(intent)
            return true
        }
        return false
    }

    /**
     * 初始化数据【读取数据并存储】
     * @return `true` success, `false` fail
     */
    fun intentReader(args: Bundle?): Boolean {
        if (args != null) {
            intentData.reader(args)
            return true
        }
        return false
    }
}