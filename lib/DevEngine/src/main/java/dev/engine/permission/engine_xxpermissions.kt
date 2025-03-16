package dev.engine.permission

import android.app.Activity
import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import dev.utils.app.AppUtils
import dev.utils.app.IntentUtils
import dev.utils.app.info.AppInfoUtils
import dev.utils.common.ArrayUtils
import dev.utils.common.CollectionUtils

/**
 * detail: XXPermissions Engine 实现
 * @author Ttt
 */
open class XXPermissionsEngineImpl : IPermissionEngine {

    // =============
    // = 对外公开方法 =
    // =============

    override fun isGranted(
        context: Context?,
        vararg permissions: String?
    ): Boolean {
        if (context != null) {
            return XXPermissions.isGranted(context, *permissions)
        }
        return false
    }

    override fun shouldShowRequestPermissionRationale(
        activity: Activity?,
        vararg permissions: String?
    ): Boolean {
        if (activity != null) {
            return !XXPermissions.isDoNotAskAgainPermissions(
                activity, *permissions
            )
        }
        return false
    }

    override fun getDeniedPermissionStatus(
        activity: Activity?,
        shouldShow: Boolean,
        vararg permissions: String?
    ): MutableList<String> {
        if (activity != null) {
            val sets = mutableSetOf<String>()
            permissions.forEach {
                if (it != null && !sets.contains(it) && !isGranted(activity, it)) {
                    val state = XXPermissions.isDoNotAskAgainPermissions(activity, it)
                    // shouldShow {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
                    if (!shouldShow == state) {
                        sets.add(it)
                    }
                }
            }
            return sets.toMutableList()
        }
        return mutableListOf()
    }

    override fun againRequest(
        activity: Activity?,
        callback: IPermissionEngine.Callback?,
        deniedList: List<String>?
    ): Int {
        if (activity == null || deniedList == null || CollectionUtils.isEmpty(deniedList)) {
            return 0
        }
        if (XXPermissions.isDoNotAskAgainPermissions(
                activity, deniedList
            )
        ) {
            // 拒绝权限且不再询问, 跳转到应用设置页面
            AppUtils.startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent())
            return 2
        } else {
            // 再次请求
            request(activity, CollectionUtils.toArrayT<String>(deniedList))
            return 1
        }
    }

    // =============
    // = 权限请求方法 =
    // =============

    override fun request(
        activity: Activity?,
        permissionArray: Array<out String>?
    ) {
        request(activity, permissionArray, null, false)
    }

    override fun request(
        activity: Activity?,
        permissionArray: Array<out String>?,
        callback: IPermissionEngine.Callback?
    ) {
        request(activity, permissionArray, callback, false)
    }

    override fun request(
        activity: Activity?,
        permissionArray: Array<out String>?,
        callback: IPermissionEngine.Callback?,
        againRequest: Boolean
    ) {
        if (activity != null && permissionArray != null) {
            XXPermissions.with(activity)
                .permission(permissionArray)
                .request(object : OnPermissionCallback {
                    override fun onGranted(
                        grantedPermissions: List<String>,
                        allGranted: Boolean
                    ) {
                        if (allGranted) {
                            callback?.onGranted()
                            return
                        }

                        // ============
                        // = 非全部授权 =
                        // ============

                        callback?.let {
                            // 获取申请的全部权限
                            val allPermission = ArrayUtils.asList<String>(permissionArray)
                            // 获取查询不到的权限 ( 包含未注册 )
                            val notFoundList = notFoundPermissionList(permissionArray)
                            // 移除已授权和未注册的权限
                            CollectionUtils.removeAll<String>(allPermission, grantedPermissions)
                            CollectionUtils.removeAll<String>(allPermission, notFoundList)
                            // 获取未授权权限
                            val deniedList = allPermission
                            // 触发授权未通过权限回调
                            it.onDenied(
                                grantedPermissions,
                                deniedList,
                                notFoundList
                            )

                            // 再次请求处理操作
                            if (againRequest && deniedList.isNotEmpty()) {
                                againRequest(activity, callback, deniedList)
                            }
                        }
                    }

                    override fun onDenied(
                        deniedPermissions: List<String>,
                        doNotAskAgain: Boolean
                    ) {

                        callback?.let {
                            // 获取申请的全部权限
                            val allPermission = ArrayUtils.asList<String>(permissionArray)
                            // 获取查询不到的权限 ( 包含未注册 )
                            val notFoundList = notFoundPermissionList(permissionArray)
                            // 移除未授权和未注册的权限
                            CollectionUtils.removeAll<String>(allPermission, deniedPermissions)
                            CollectionUtils.removeAll<String>(allPermission, notFoundList)
                            // 获取已授权权限
                            val grantedList = allPermission

                            // 触发授权未通过权限回调
                            it.onDenied(
                                grantedList,
                                deniedPermissions,
                                notFoundList
                            )

                            // 再次请求处理操作
                            if (againRequest && deniedPermissions.isNotEmpty()) {
                                againRequest(activity, callback, deniedPermissions)
                            }
                        }
                    }
                })
        }
    }

    // ==============
    // = 额外实现方法 =
    // ==============

    // APP 注册的权限
    private val sAppPermissionSets: Set<String> by lazy {
        AppInfoUtils.getAppPermissionToSet()
    }

    /**
     * 获取查询不到的权限 ( 包含未注册 )
     * @param permissionArray  待申请权限
     * @return 查询不到的权限 ( 包含未注册 )
     */
    private fun notFoundPermissionList(permissionArray: Array<out String>?): List<String> {
        val notFoundList = mutableListOf<String>()
        if (permissionArray != null) {
            for (permission in permissionArray) {
                if (!sAppPermissionSets.contains(permission)) {
                    notFoundList.add(permission)
                }
            }
        }
        return notFoundList
    }
}