package dev.engine.core.permission

import com.hjq.permissions.OnPermissionDescription
import com.hjq.permissions.OnPermissionInterceptor
import com.hjq.permissions.permission.base.IPermission
import dev.engine.permission.IPermissionEngine

class PermissionConfig(
    // 权限请求拦截器
    val interceptor: OnPermissionInterceptor? = null,
    // 权限请求描述
    val description: OnPermissionDescription? = null
) : IPermissionEngine.EngineConfig

class PermissionItem(
    val permission: IPermission
) : IPermissionEngine.EngineItem {

    /**
     * 获取权限的名称
     */
    override fun permissionName(): String {
        return permission.permissionName
    }
}