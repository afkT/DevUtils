package dev.agile.livedata

import androidx.lifecycle.MutableLiveData

/**
 * detail: 缺少数据 ( null ) 通用 MutableLiveData
 * @author Ttt
 */
class AbsentMutableLiveData<T> : MutableLiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create() = AbsentMutableLiveData<T>()
    }
}