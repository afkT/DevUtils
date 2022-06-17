package afkt.demo.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {

    val number = MutableLiveData<Int>()

    init {
        number.value = -100
    }
}