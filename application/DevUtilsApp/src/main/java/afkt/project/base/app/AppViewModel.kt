package afkt.project.base.app

import afkt.project.base.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class AppViewModel : BaseViewModel() {

    // navigate fragment
    private val _fragNavigate = MutableLiveData<Int>()
    val fragNavigate: LiveData<Int> get() = _fragNavigate

    // fragment popBackStack
    private val _fragPopBack = MutableLiveData<Long>()
    val fragPopBack: LiveData<Long> get() = _fragPopBack

    /**
     * navigate fragment
     * @param id fragment id
     */
    fun navigate(id: Int) {
        _fragNavigate.value = id
    }

    /**
     * fragment popBackStack
     */
    fun popBackStack() {
        _fragPopBack.value = System.currentTimeMillis()
    }
}