package dev.simple.core.livedata

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * detail: 一个用于封装可观测单一状态、值的 LiveData 类
 * @author Ttt
 * 泛化概念：可表示 status、data、source、page 等
 */
open class ValueLiveData<T>(
    _value: T? = null
) {

    // 数据值
    protected val _value = MutableLiveData<T>(_value)
    val value: LiveData<T> get() = _value

    /**
     * 获取数据值
     * @return value
     */
    open fun dataValue(): T? {
        return _value.value
    }

    /**
     * 主线程直接设置值
     * @param value 待更新值
     * @param allowNull 是否允许设置为 null
     * @return `true` success, `false` fail
     */
    open fun setValue(
        value: T?,
        allowNull: Boolean = false
    ): Boolean {
        if (shouldUpdateValue(value)) {
            if (allowNull || value != null) {
                _value.value = value
                return true
            }
        }
        return false
    }

    /**
     * 子线程安全设置值 ( 自动切换到主线程 )
     * @param value 待更新值
     * @param allowNull 是否允许设置为 null
     * @return `true` success, `false` fail
     */
    open fun postValue(
        value: T?,
        allowNull: Boolean = false
    ): Boolean {
        if (shouldUpdateValue(value)) {
            if (allowNull || value != null) {
                _value.postValue(value)
                return true
            }
        }
        return false
    }

    /**
     * 智能线程判断 ( 自动选择 setValue、postValue )
     * @param value 待更新值
     * @param allowNull 是否允许设置为 null
     * @return `true` success, `false` fail
     */
    open fun smartUpdateValue(
        value: T?,
        allowNull: Boolean = false
    ): Boolean {
        return if (isMainThread()) {
            setValue(value, allowNull)
        } else {
            postValue(value, allowNull)
        }
    }

    /**
     * 重置数据值 ( 主线程直接设置 null )
     * @return `true` success, `false` fail
     */
    open fun resetValue(): Boolean {
        return setValue(null, true)
    }

    /**
     * 重置数据值 ( 子线程安全设置 null )
     * @return `true` success, `false` fail
     */
    open fun postResetValue(): Boolean {
        return postValue(null, true)
    }

    /**
     * 重置数据值 ( 智能线程判断设置 null )
     * @return `true` success, `false` fail
     */
    open fun smartResetValue(): Boolean {
        return smartUpdateValue(null, true)
    }

    /**
     * 判断是否需要更新值
     * @param value 待更新值
     * @return `true` yes, `false` no
     */
    open fun shouldUpdateValue(value: T?): Boolean {
        return !isEqual(value, _value.value)
    }

    /**
     * 判断是否相同值
     * @param value 待判断值
     * @return `true` yes, `false` no
     */
    open fun isEqual(value: T?): Boolean {
        return isEqual(value, _value.value)
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