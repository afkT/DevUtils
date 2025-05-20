package afkt.retrofit.use.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.simple.app.BaseAppViewModel

/**
 * detail: Base ViewModel
 * @author Ttt
 */
open class BaseViewModel : BaseAppViewModel() {

    private val _backPressedEvent = MutableLiveData<Unit>()
    val backPressedEvent: LiveData<Unit> get() = _backPressedEvent

    /**
     * 触发返回键事件
     */
    fun postBackPressed() {
        _backPressedEvent.value = Unit
    }
}