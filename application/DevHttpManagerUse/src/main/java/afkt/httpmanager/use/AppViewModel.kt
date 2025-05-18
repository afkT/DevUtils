package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseViewModel
import androidx.databinding.ObservableField
import dev.DevHttpManager


/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    // ===============
    // = MainFragment =
    // ===============

    // DevHttpManager:Version
    val devHttpManagerVersion = ObservableField(
        "DevHttpManager:${DevHttpManager.getDevHttpManagerVersion()}"
    )
}