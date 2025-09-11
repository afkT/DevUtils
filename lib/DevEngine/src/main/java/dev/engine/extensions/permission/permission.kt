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

fun <Item : IPermissionEngine.EngineItem> Activity.permission_request(
    engine: String? = null,
    permission: Item,
    callback: IPermissionEngine.Callback<Item>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permission, callback)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Activity.permission_request(
    engine: String? = null,
    permissions: MutableList<Item?>,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permissions, callback)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Fragment.permission_request(
    engine: String? = null,
    permission: Item,
    callback: IPermissionEngine.Callback<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.request(this, permission, callback)
    } ?: false
}

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

fun <Item : IPermissionEngine.EngineItem> Context.permission_isGrantedPermission(
    engine: String? = null,
    permission: Item
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isGrantedPermission(this, permission)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Context.permission_isGrantedPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isGrantedPermissions(this, permissions)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Context.permission_getGrantedPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): MutableList<Item?>? {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.getGrantedPermissions(this, permissions)
    }
}

fun <Item : IPermissionEngine.EngineItem> Context.permission_getDeniedPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): MutableList<Item?>? {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.getDeniedPermissions(this, permissions)
    }
}

fun <Item : IPermissionEngine.EngineItem> Activity.permission_isDoNotAskAgainPermission(
    engine: String? = null,
    permission: Item
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isDoNotAskAgainPermission(this, permission)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Activity.permission_isDoNotAskAgainPermissions(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.isDoNotAskAgainPermissions(this, permissions)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Item.permission_equalsPermission(
    engine: String? = null,
    permission2: Item
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.equalsPermission(this, permission2)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Item.permission_equalsPermission(
    engine: String? = null,
    permissionName: String
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.equalsPermission(this, permissionName)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> String.permission_equalsPermission(
    engine: String? = null,
    permissionName: String
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.equalsPermission(this, permissionName)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> Item.permission_containsPermission(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.containsPermission(permissions, this)
    } ?: false
}

fun <Item : IPermissionEngine.EngineItem> String.permission_containsPermission(
    engine: String? = null,
    permissions: MutableList<Item?>
): Boolean {
    return engine.getPermissionEngine()?.let {
        (it as? IPermissionEngine<*, Item>)?.containsPermission(permissions, this)
    } ?: false
}