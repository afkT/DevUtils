package dev.engine.core.permission

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.base.IPermission
import dev.engine.permission.IPermissionEngine

fun IPermission.toPermissionItem(): PermissionItem {
    return PermissionItem(this)
}

fun PermissionItem.toPermission(): IPermission {
    return permission
}

/**
 * 转换 Permission Engine Item List
 */
fun List<IPermission?>.toPermissionItems(): List<PermissionItem> {
    return this.mapNotNull { it?.let(::PermissionItem) }
}

/**
 * 转换 IPermission List
 */
fun List<PermissionItem?>.toPermissions(): List<IPermission> {
    return this.mapNotNull { it -> it?.permission }
}

// =

/**
 * detail: XXPermissions Engine 实现
 * @author Ttt
 */
open class XXPermissionsEngineImpl : IPermissionEngine<PermissionConfig, PermissionItem> {

    // =============
    // = 对外公开方法 =
    // =============

    // ==========
    // = 权限请求 =
    // ==========

    /**
     * 请求权限
     * @param activity Activity
     * @param permission 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        activity: Activity,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(activity, mutableListOf(permission), callback)
    }

    /**
     * 请求权限
     * @param activity Activity
     * @param permissions 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        activity: Activity,
        permissions: List<PermissionItem?>,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        val permissionsList = permissions.toPermissions()
        if (permissionsList.isEmpty()) return false
        XXPermissions.with(activity)
            .permissions(permissionsList)
            .request { grantedList, deniedList ->
                callback.onResult(
                    grantedList.toPermissionItems(),
                    deniedList.toPermissionItems()
                )
            }
        return true
    }

    // =

    /**
     * 请求权限
     * @param fragment Fragment
     * @param permission 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        fragment: Fragment,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(fragment, mutableListOf(permission), callback)
    }

    /**
     * 请求权限
     * @param fragment Fragment
     * @param permissions 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        fragment: Fragment,
        permissions: List<PermissionItem?>,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        val permissionsList = permissions.toPermissions()
        if (permissionsList.isEmpty()) return false
        XXPermissions.with(fragment)
            .permissions(permissionsList)
            .request { grantedList, deniedList ->
                callback.onResult(
                    grantedList.toPermissionItems(),
                    deniedList.toPermissionItems()
                )
            }
        return true
    }

    // ===================
    // = 权限请求 ( 配置 ) =
    // ===================

    /**
     * 请求权限
     * @param config Config
     * @param activity Activity
     * @param permission 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        config: PermissionConfig,
        activity: Activity,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(config, activity, mutableListOf(permission), callback)
    }

    /**
     * 请求权限
     * @param config Config
     * @param activity Activity
     * @param permissions 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        config: PermissionConfig,
        activity: Activity,
        permissions: List<PermissionItem?>,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        val permissionsList = permissions.toPermissions()
        if (permissionsList.isEmpty()) return false
        XXPermissions.with(activity)
            .permissions(permissionsList)
            .interceptor(config.interceptor)
            .description(config.description)
            .request { grantedList, deniedList ->
                callback.onResult(
                    grantedList.toPermissionItems(),
                    deniedList.toPermissionItems()
                )
            }
        return true
    }

    // =

    /**
     * 请求权限
     * @param config Config
     * @param fragment Fragment
     * @param permission 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        config: PermissionConfig,
        fragment: Fragment,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(config, fragment, mutableListOf(permission), callback)
    }

    /**
     * 请求权限
     * @param config Config
     * @param fragment Fragment
     * @param permissions 待申请权限
     * @param callback 权限请求回调
     * @return `true` success, `false` fail
     */
    override fun request(
        config: PermissionConfig,
        fragment: Fragment,
        permissions: List<PermissionItem?>,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        val permissionsList = permissions.toPermissions()
        if (permissionsList.isEmpty()) return false
        XXPermissions.with(fragment)
            .permissions(permissionsList)
            .interceptor(config.interceptor)
            .description(config.description)
            .request { grantedList, deniedList ->
                callback.onResult(
                    grantedList.toPermissionItems(),
                    deniedList.toPermissionItems()
                )
            }
        return true
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断是否授予了权限
     * @param context Context
     * @param permission 待判断权限
     * @return `true` yes, `false` no
     */
    override fun isGrantedPermission(
        context: Context,
        permission: PermissionItem
    ): Boolean {
        return XXPermissions.isGrantedPermission(
            context, permission.permission
        )
    }

    /**
     * 判断是否授予了权限
     * @param context Context
     * @param permissions 待判断权限列表
     * @return `true` yes, `false` no
     */
    override fun isGrantedPermissions(
        context: Context,
        permissions: List<PermissionItem?>
    ): Boolean {
        return XXPermissions.isGrantedPermissions(
            context, permissions.toPermissions()
        )
    }

    // =

    /**
     * 获取已经授予的权限
     * @param context Context
     * @param permissions 待判断权限列表
     * @return 已经授予的权限集合
     */
    override fun getGrantedPermissions(
        context: Context,
        permissions: List<PermissionItem?>
    ): List<PermissionItem> {
        return XXPermissions.getGrantedPermissions(
            context, permissions.toPermissions()
        ).toPermissionItems()
    }

    /**
     * 获取已经拒绝的权限
     * @param context Context
     * @param permissions 待判断权限列表
     * @return 已经拒绝的权限集合
     */
    override fun getDeniedPermissions(
        context: Context,
        permissions: List<PermissionItem?>
    ): List<PermissionItem> {
        return XXPermissions.getDeniedPermissions(
            context, permissions.toPermissions()
        ).toPermissionItems()
    }

    // =

    /**
     * 获取拒绝权限询问勾选状态
     * @param activity Activity
     * @param permission 待判断权限
     * @return `true` 没有勾选不再询问, `false` 勾选了不再询问
     */
    override fun isDoNotAskAgainPermission(
        activity: Activity,
        permission: PermissionItem
    ): Boolean {
        return XXPermissions.isDoNotAskAgainPermission(
            activity, permission.permission
        )
    }

    /**
     * 获取拒绝权限询问勾选状态
     * @param activity Activity
     * @param permissions 待判断权限列表
     * @return `true` 没有勾选不再询问, `false` 勾选了不再询问
     */
    override fun isDoNotAskAgainPermissions(
        activity: Activity,
        permissions: List<PermissionItem?>
    ): Boolean {
        return XXPermissions.isDoNotAskAgainPermissions(
            activity, permissions.toPermissions()
        )
    }

    // =

    /**
     * 判断两个权限是否相等
     * @param permission 待判断权限
     * @param permission2 待判断权限
     * @return `true` yes, `false` no
     */
    override fun equalsPermission(
        permission: PermissionItem,
        permission2: PermissionItem
    ): Boolean {
        return XXPermissions.equalsPermission(
            permission.permission,
            permission2.permission
        )
    }

    /**
     * 判断两个权限是否相等
     * @param permission 待判断权限
     * @param permissionName 待判断权限
     * @return `true` yes, `false` no
     */
    override fun equalsPermission(
        permission: PermissionItem,
        permissionName: String
    ): Boolean {
        return XXPermissions.equalsPermission(
            permission.permission,
            permissionName
        )
    }

    /**
     * 判断两个权限是否相等
     * @param permissionName1 待判断权限
     * @param permissionName2 待判断权限
     * @return `true` yes, `false` no
     */
    override fun equalsPermission(
        permissionName1: String,
        permissionName2: String
    ): Boolean {
        return XXPermissions.equalsPermission(
            permissionName1, permissionName2
        )
    }

    // =

    /**
     * 判断权限列表中是否包含某个权限
     * @param permissions 待判断权限列表
     * @param permission 待判断权限
     * @return `true` yes, `false` no
     */
    override fun containsPermission(
        permissions: List<PermissionItem?>,
        permission: PermissionItem
    ): Boolean {
        return XXPermissions.containsPermission(
            permissions.toPermissions(),
            permission.permission
        )
    }

    /**
     * 判断权限列表中是否包含某个权限
     * @param permissions 待判断权限列表
     * @param permissionName 待判断权限
     * @return `true` yes, `false` no
     */
    override fun containsPermission(
        permissions: List<PermissionItem?>,
        permissionName: String
    ): Boolean {
        return XXPermissions.containsPermission(
            permissions.toPermissions(),
            permissionName
        )
    }
}