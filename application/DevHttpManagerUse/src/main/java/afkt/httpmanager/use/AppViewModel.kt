package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseViewModel
import androidx.databinding.ObservableField
import dev.DevHttpManager


/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    // 日志 TAG
    private val TAG = AppViewModel::class.java.simpleName

    // ===============
    // = MainFragment =
    // ===============

    // DevHttpManager:Version
    val devHttpManagerVersion = ObservableField(
        "DevHttpManager:${DevHttpManager.getDevHttpManagerVersion()}"
    )
}