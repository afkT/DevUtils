package afkt.retrofit.use

import afkt.retrofit.use.base.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.DevRetrofit

class MainViewModel : BaseViewModel() {

    // DevRetrofit:Version
    val devRetrofitVersion: LiveData<String> = MutableLiveData(
        "DevRetrofit:${DevRetrofit.getDevRetrofitVersion()}"
    )

    // Fragment Navigate
    private val _clickNavigateEvent = MutableLiveData<NavigateRes>()
    val clickNavigateEvent: LiveData<NavigateRes> get() = _clickNavigateEvent

    val clickNavigate: (Int) -> Unit = { index ->
        when (index) {
            1 -> _clickNavigateEvent.value = NavigateRes(
                R.id.RequestFragment
            )

            2 -> _clickNavigateEvent.value = NavigateRes(
                R.id.SimpleFragment
            )
        }
    }
}

class NavigateRes(
    val id: Int
)