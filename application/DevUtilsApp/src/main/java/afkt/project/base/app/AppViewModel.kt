package afkt.project.base.app

import afkt.project.base.BaseViewModel
import afkt.project.model.data.button.ButtonEnum
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class AppViewModel : BaseViewModel() {

    // navigate fragment
    private val _fragNavigate = MutableLiveData<ButtonEnum>()
    val fragNavigate: LiveData<ButtonEnum> get() = _fragNavigate

    // fragment popBackStack
    private val _fragPopBack = MutableLiveData<Long>()
    val fragPopBack: LiveData<Long> get() = _fragPopBack

    /**
     * navigate fragment
     * @param button Button
     */
    fun navigate(button: ButtonEnum) {
        _fragNavigate.value = button
    }

    /**
     * fragment popBackStack
     */
    fun popBackStack() {
        _fragPopBack.value = System.currentTimeMillis()
    }
}