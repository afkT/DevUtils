package dev.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dev.utils.LogPrintUtils
import dev.utils.app.AppUtils

/**
 * detail: 应用状态监听广播 ( 安装、更新、卸载 )
 * @author Ttt
 */
class AppStateReceiver private constructor() : BroadcastReceiver() {

    // 日志 TAG
    private val TAG = AppStateReceiver::class.java.simpleName

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        // 打印当前触发的广播
        LogPrintUtils.dTag(TAG, "onReceive Action: %s", action)
        // 被操作应用包名
        var packageName: String? = intent.data?.encodedSchemeSpecificPart
        // 判断类型
        when (action) {
            // 应用安装
            Intent.ACTION_PACKAGE_ADDED -> sListener?.onAdded(packageName)
            // 应用更新
            Intent.ACTION_PACKAGE_REPLACED -> sListener?.onReplaced(packageName)
            // 应用卸载
            Intent.ACTION_PACKAGE_REMOVED -> sListener?.onRemoved(packageName)
        }
    }

    companion object {

        // 应用状态监听广播
        private val sReceiver = AppStateReceiver()

        // 应用状态监听事件
        private var sListener: Listener? = null

        /**
         * 注册应用状态监听广播
         */
        fun register() {
            IntentFilter().apply {
                addAction(Intent.ACTION_PACKAGE_ADDED) // 安装
                addAction(Intent.ACTION_PACKAGE_REPLACED) // 更新
                addAction(Intent.ACTION_PACKAGE_REMOVED) // 卸载
                addDataScheme("package")
                // 注册广播
                AppUtils.registerReceiver(sReceiver, this)
            }
        }

        /**
         * 取消注册应用状态监听广播
         */
        fun unregister() = AppUtils.unregisterReceiver(sReceiver)

        /**
         * 设置应用状态监听事件
         * @param listener [Listener]
         */
        fun setListener(listener: Listener?) {
            sListener = listener
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * detail: 应用状态监听事件
     * @author Ttt
     */
    interface Listener {
        /**
         * 应用安装
         * @param packageName 应用包名
         */
        fun onAdded(packageName: String?)

        /**
         * 应用更新
         * @param packageName 应用包名
         */
        fun onReplaced(packageName: String?)

        /**
         * 应用卸载
         * @param packageName 应用包名
         */
        fun onRemoved(packageName: String?)
    }
}