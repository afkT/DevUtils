package afkt.httpmanager.use

import afkt.httpmanager.use.base.BaseViewModel
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.DevHttpManager

class MainViewModel : BaseViewModel() {

    // DevHttpManager:Version
    val devHttpManagerVersion = ObservableField(
        "DevHttpManager:${DevHttpManager.getDevHttpManagerVersion()}"
    )

    // Fragment Navigate
    private val _clickNavigateEvent = MutableLiveData<NavigateRes>()
    val clickNavigateEvent: LiveData<NavigateRes> get() = _clickNavigateEvent

    val clickNavigate: (Int) -> Unit = { index ->
        when (index) {
            1 -> _clickNavigateEvent.value = NavigateRes(
                R.id.RMFragment
            )

//            2 -> _clickNavigateEvent.value = NavigateRes(
//                R.id.PMFragment
//            )
        }
    }
}

class NavigateRes(
    val id: Int
)