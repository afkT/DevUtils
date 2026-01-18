package dev.simple.core.livedata

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * detail: 一个用于封装数据流中有效载荷的 LiveData 类
 * @author Ttt
 */
open class PayloadLiveData<T>(
    _value: T? = null
) {

    // 有效载荷数据
    protected val _payload = MutableLiveData<T>(_value)
    val payload: LiveData<T> get() = _payload

    /**
     * 获取有效载荷数据
     * @return value
     */
    open fun getPayload(): T? {
        return _payload.value
    }

    /**
     * 主线程直接设置值
     * @param value 待更新值
     * @return `true` success, `false` fail
     */
    open fun setPayload(value: T?): Boolean {
        if (shouldUpdatePayload(value)) {
            if (value != null) {
                _payload.value = value
                return true
            }
        }
        return false
    }

    /**
     * 子线程安全设置值 ( 自动切换到主线程 )
     * @param value 待更新值
     * @return `true` success, `false` fail
     */
    open fun postPayload(value: T?): Boolean {
        if (shouldUpdatePayload(value)) {
            if (value != null) {
                _payload.postValue(value)
                return true
            }
        }
        return false
    }

    /**
     * 智能线程判断 ( 自动选择 setValue、postValue )
     * @param value 待更新值
     * @return `true` success, `false` fail
     */
    open fun smartUpdatePayload(value: T?): Boolean {
        return if (isMainThread()) {
            setPayload(value)
        } else {
            postPayload(value)
        }
    }

    /**
     * 判断是否需要更新值
     * @param value 待更新值
     * @return `true` yes, `false` no
     */
    open fun shouldUpdatePayload(value: T?): Boolean {
        return !isEqual(value, _payload.value)
    }

    /**
     * 判断是否相同值
     * @param value 待判断值
     * @return `true` yes, `false` no
     */
    open fun isEqual(value: T?): Boolean {
        return isEqual(value, _payload.value)
    }

    /**
     * 判断是否相同值
     * @param newValue 待更新值
     * @param currentValue 当前值
     * @return `true` yes, `false` no
     */
    open fun isEqual(
        newValue: T?,
        currentValue: T?
    ): Boolean {
        return when {
            newValue == currentValue -> true
            else -> {
                // 对于复杂对象，建议重写 equals 方法
                newValue == currentValue
            }
        }
    }

    /**
     * 当前线程是否主线程
     * @return `true` yes, `false` no
     */
    open fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}