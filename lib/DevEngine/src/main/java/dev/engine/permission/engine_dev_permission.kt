package dev.engine.permission

import android.app.Activity
import android.content.Context
import dev.utils.app.permission.PermissionUtils

/**
 * detail: DevUtils Permission Engine 实现
 * @author Ttt
 */
class DevPermissionEngineImpl : IPermissionEngine {

    // =============
    // = 对外公开方法 =
    // =============

    override fun isGranted(
        context: Context?,
        vararg permissions: String?
    ): Boolean {
        return PermissionUtils.isGranted(*permissions)
    }

    override fun shouldShowRequestPermissionRationale(
        activity: Activity?,
        vararg permissions: String?
    ): Boolean {
        return PermissionUtils.shouldShowRequestPermissionRationale(
            activity, *permissions
        )
    }

    override fun getDeniedPermissionStatus(
        activity: Activity?,
        shouldShow: Boolean,
        vararg permissions: String?
    ): MutableList<String> {
        return PermissionUtils.getDeniedPermissionStatus(
            activity, shouldShow, *permissions
        )
    }

    override fun againRequest(
        activity: Activity?,
        callback: IPermissionEngine.Callback?,
        deniedList: List<String>?
    ): Int {
        return PermissionUtils.againRequest(activity, object : PermissionUtils.PermissionCallback {
            override fun onGranted() {
                callback?.onGranted()
            }

            override fun onDenied(
                grantedList: List<String>,
                deniedList: List<String>,
                notFoundList: List<String>
            ) {
                callback?.onDenied(grantedList, deniedList, notFoundList)
            }
        }, deniedList)
    }

    // =============
    // = 权限请求方法 =
    // =============

    override fun request(
        activity: Activity?,
        permissions: Array<out String>?
    ) {
        request(activity, permissions, null, false)
    }

    override fun request(
        activity: Activity?,
        permissions: Array<out String>?,
        callback: IPermissionEngine.Callback?
    ) {
        request(activity, permissions, callback, false)
    }

    override fun request(
        activity: Activity?,
        permissions: Array<out String>?,
        callback: IPermissionEngine.Callback?,
        againRequest: Boolean
    ) {
        permissions?.let {
            PermissionUtils.permission(*it)
                .callback(object : PermissionUtils.PermissionCallback {
                    override fun onGranted() {
                        callback?.onGranted()
                    }

                    override fun onDenied(
                        grantedList: List<String>,
                        deniedList: List<String>,
                        notFoundList: List<String>
                    ) {
                        callback?.onDenied(grantedList, deniedList, notFoundList)
                        // 再次请求处理操作
                        if (againRequest && deniedList.isNotEmpty()) {
                            againRequest(activity, callback, deniedList)
                        }
                    }
                }).request(activity)
        }
    }
}