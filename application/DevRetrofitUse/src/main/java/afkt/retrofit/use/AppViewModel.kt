package afkt.retrofit.use

import afkt.retrofit.use.base.BaseViewModel
import androidx.databinding.ObservableField
import dev.DevRetrofit

/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    // ===============
    // = MainFragment =
    // ===============

    // DevRetrofit:Version
    val devRetrofitVersion = ObservableField(
        "DevRetrofit:${DevRetrofit.getDevRetrofitVersion()}"
    )
}