package dev.simple.agile.livedata

import androidx.lifecycle.LiveData

/**
 * detail: 缺少数据 ( null ) 通用 LiveData
 * @author Ttt
 */
class AbsentLiveData<T> : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create() = AbsentLiveData<T>()
    }
}