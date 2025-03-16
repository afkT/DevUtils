package dev.capture

import androidx.annotation.StringRes

/**
 * detail: 抓包库 Toast
 * @author Ttt
 */
interface DevHttpCaptureToast {

    /**
     * 默认提示 Toast
     * @param id R.string.id
     */
    fun normal(@StringRes id: Int)

    /**
     * 操作成功 Toast
     * @param id R.string.id
     */
    fun success(@StringRes id: Int)
}