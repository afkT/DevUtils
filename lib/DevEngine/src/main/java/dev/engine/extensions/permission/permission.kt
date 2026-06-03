package dev.engine.extensions.permission

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import dev.engine.DevEngine
import dev.engine.permission.IPermissionEngine

// =====================
// = IPermissionEngine =
// =====================

/**
 * 通过 Key 获取 Permission Engine
 * @param engine String?
 * @return IPermissionEngine
 * 内部做了处理如果匹配不到则返回默认 Permission Engine
 */
fun String?.getPermissionEngine(): IPermissionEngine<in IPermissionEngine.EngineConfig, in IPermissionEngine.EngineItem>? {
    DevEngine.getPermission(this)?.let { value ->
        return value
    }
    return DevEngine.getPermission()
}

// =========================
// = Key Permission Engine =
// =========================

// =============
// = 对外公开方法 =
// =============

// ==========
// = 权限请求 =
// ==========

/**
 * 请求权限
 * @receiver Activity
 * @param engine String?
 * @param permission 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Item : IPermissionEngine.EngineItem> Activity.permission_request(
    engine: String? = null,
    permission: Item,
    callback: IPermissionEngine.Callback<Item>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permission, callback)
    } ?: false
}

/**
 * 请求权限
 * @receiver Activity
 * @param engine String?
 * @param permissions 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Item : IPermissionEngine.EngineItem> Activity.permission_request(
    engine: String? = null,
    permissions: MutableList<Item?>,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permissions, callback)
    } ?: false
}

/**
 * 请求权限
 * @receiver Fragment
 * @param engine String?
 * @param permission 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Item : IPermissionEngine.EngineItem> Fragment.permission_request(
    engine: String? = null,
    permission: Item,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permission, callback)
    } ?: false
}

/**
 * 请求权限
 * @receiver Fragment
 * @param engine String?
 * @param permissions 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Item : IPermissionEngine.EngineItem> Fragment.permission_request(
    engine: String? = null,
    permissions: MutableList<Item?>,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permissions, callback)
    } ?: false
}

// ===================
// = 权限请求 ( 配置 ) =
// ===================

/**
 * 请求权限
 * @receiver Activity
 * @param engine String?
 * @param config Config
 * @param permission 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Config : IPermissionEngine.EngineConfig, Item : IPermissionEngine.EngineItem> Activity.permission_request(
    engine: String? = null,
    config: Config,
    permission: Item,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<Config, Item>)?.request(config, this, permission, callback)
    } ?: false
}

/**
 * 请求权限
 * @receiver Activity
 * @param engine String?
 * @param config Config
 * @param permissions 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Config : IPermissionEngine.EngineConfig, Item : IPermissionEngine.EngineItem> Activity.permission_request(
    engine: String? = null,
    config: Config,
    permissions: MutableList<Item?>,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<Config, Item>)?.request(config, this, permissions, callback)
    } ?: false
}

/**
 * 请求权限
 * @receiver Fragment
 * @param engine String?
 * @param config Config
 * @param permission 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Config : IPermissionEngine.EngineConfig, Item : IPermissionEngine.EngineItem> Fragment.permission_request(
    engine: String? = null,
    config: Config,
    permission: Item,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<Config, Item>)?.request(config, this, permission, callback)
    } ?: false
}

/**
 * 请求权限
 * @receiver Fragment
 * @param engine String?
 * @param config Config
 * @param permissions 待申请权限
 * @param callback 权限请求回调
 * @return `true` success, `false` fail
 */
fun <Config : IPermissionEngine.EngineConfig, Item : IPermissionEngine.EngineItem> Fragment.permission_request(
    engine: String? = null,
    config: Config,
    permissions: MutableList<Item?>,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<Config, Item>)?.request(config, this, permissions, callback)
    } ?: false
}

// ==========
// = 快捷方法 =
// ==========

/**
 * 判断是否授予了权限
 * @receiver Context
 * @param engine String?
 * @param permission 待判断权限
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> Context.permission_isGrantedPermission(
    engine: String? = null,
    permission: Item
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isGrantedPermission(this, permission)
    } ?: false
}

/**
 * 判断是否授予了权限
 * @receiver Context
 * @param engine String?
 * @param permissions 待判断权限列表
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> Context.permission_isGrantedPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isGrantedPermissions(this, permissions)
    } ?: false
}

/**
 * 获取已经授予的权限
 * @receiver Context
 * @param engine String?
 * @param permissions 待判断权限列表
 * @return 已经授予的权限集合
 */
fun <Item : IPermissionEngine.EngineItem> Context.permission_getGrantedPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): MutableList<Item?>? {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.getGrantedPermissions(this, permissions)
    }
}

/**
 * 获取已经拒绝的权限
 * @receiver Context
 * @param engine String?
 * @param permissions 待判断权限列表
 * @return 已经拒绝的权限集合
 */
fun <Item : IPermissionEngine.EngineItem> Context.permission_getDeniedPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): MutableList<Item?>? {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.getDeniedPermissions(this, permissions)
    }
}

/**
 * 获取拒绝权限询问勾选状态
 * @receiver Activity
 * @param engine String?
 * @param permission 待判断权限
 * @return `true` 没有勾选不再询问, `false` 勾选了不再询问
 */
fun <Item : IPermissionEngine.EngineItem> Activity.permission_isDoNotAskAgainPermission(
    engine: String? = null,
    permission: Item
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isDoNotAskAgainPermission(this, permission)
    } ?: false
}

/**
 * 获取拒绝权限询问勾选状态
 * @receiver Activity
 * @param engine String?
 * @param permissions 待判断权限列表
 * @return `true` 没有勾选不再询问, `false` 勾选了不再询问
 */
fun <Item : IPermissionEngine.EngineItem> Activity.permission_isDoNotAskAgainPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isDoNotAskAgainPermissions(this, permissions)
    } ?: false
}

/**
 * 判断两个权限是否相等
 * @receiver 待判断权限
 * @param engine String?
 * @param permission2 待判断权限
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> Item.permission_equalsPermission(
    engine: String? = null,
    permission2: Item
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.equalsPermission(this, permission2)
    } ?: false
}

/**
 * 判断两个权限是否相等
 * @receiver 待判断权限
 * @param engine String?
 * @param permissionName 待判断权限
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> Item.permission_equalsPermission(
    engine: String? = null,
    permissionName: String
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.equalsPermission(this, permissionName)
    } ?: false
}

/**
 * 判断两个权限是否相等
 * @receiver 待判断权限
 * @param engine String?
 * @param permissionName 待判断权限
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> String.permission_equalsPermission(
    engine: String? = null,
    permissionName: String
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.equalsPermission(this, permissionName)
    } ?: false
}

/**
 * 判断权限列表中是否包含某个权限
 * @receiver 待判断权限
 * @param engine String?
 * @param permissions 待判断权限列表
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> Item.permission_containsPermission(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.containsPermission(permissions, this)
    } ?: false
}

/**
 * 判断权限列表中是否包含某个权限
 * @receiver 待判断权限
 * @param engine String?
 * @param permissions 待判断权限列表
 * @return `true` yes, `false` no
 */
fun <Item : IPermissionEngine.EngineItem> String.permission_containsPermission(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.containsPermission(permissions, this)
    } ?: false
}