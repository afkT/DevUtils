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

    override fun request(
        activity: Activity,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(activity, mutableListOf(permission), callback)
    }

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

    override fun request(
        fragment: Fragment,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(fragment, mutableListOf(permission), callback)
    }

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

    override fun request(
        config: PermissionConfig,
        activity: Activity,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(config, activity, mutableListOf(permission), callback)
    }

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

    override fun request(
        config: PermissionConfig,
        fragment: Fragment,
        permission: PermissionItem,
        callback: IPermissionEngine.Callback<PermissionItem>
    ): Boolean {
        return request(config, fragment, mutableListOf(permission), callback)
    }

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

    override fun isGrantedPermission(
        context: Context,
        permission: PermissionItem
    ): Boolean {
        return XXPermissions.isGrantedPermission(
            context, permission.permission
        )
    }

    override fun isGrantedPermissions(
        context: Context,
        permissions: List<PermissionItem?>
    ): Boolean {
        return XXPermissions.isGrantedPermissions(
            context, permissions.toPermissions()
        )
    }

    // =

    override fun getGrantedPermissions(
        context: Context,
        permissions: List<PermissionItem?>
    ): List<PermissionItem> {
        return XXPermissions.getGrantedPermissions(
            context, permissions.toPermissions()
        ).toPermissionItems()
    }

    override fun getDeniedPermissions(
        context: Context,
        permissions: List<PermissionItem?>
    ): List<PermissionItem> {
        return XXPermissions.getDeniedPermissions(
            context, permissions.toPermissions()
        ).toPermissionItems()
    }

    // =

    override fun isDoNotAskAgainPermission(
        activity: Activity,
        permission: PermissionItem
    ): Boolean {
        return XXPermissions.isDoNotAskAgainPermission(
            activity, permission.permission
        )
    }

    override fun isDoNotAskAgainPermissions(
        activity: Activity,
        permissions: List<PermissionItem?>
    ): Boolean {
        return XXPermissions.isDoNotAskAgainPermissions(
            activity, permissions.toPermissions()
        )
    }

    // =

    override fun equalsPermission(
        permission: PermissionItem,
        permission2: PermissionItem
    ): Boolean {
        return XXPermissions.equalsPermission(
            permission.permission,
            permission2.permission
        )
    }

    override fun equalsPermission(
        permission: PermissionItem,
        permissionName: String
    ): Boolean {
        return XXPermissions.equalsPermission(
            permission.permission,
            permissionName
        )
    }

    override fun equalsPermission(
        permissionName1: String,
        permissionName2: String
    ): Boolean {
        return XXPermissions.equalsPermission(
            permissionName1, permissionName2
        )
    }

    // =

    override fun containsPermission(
        permissions: List<PermissionItem?>,
        permission: PermissionItem
    ): Boolean {
        return XXPermissions.containsPermission(
            permissions.toPermissions(),
            permission.permission
        )
    }

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