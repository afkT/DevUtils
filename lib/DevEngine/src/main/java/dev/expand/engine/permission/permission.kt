package dev.expand.engine.permission

import android.app.Activity
import android.content.Context
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
fun String?.getPermissionEngine(): IPermissionEngine? {
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

fun Context.permission_isGranted(
    engine: String? = null,
    vararg permissions: String?
): Boolean {
    return engine.getPermissionEngine()?.isGranted(this, *permissions) ?: false
}

fun Activity.permission_shouldShowRequestPermissionRationale(
    engine: String? = null,
    vararg permissions: String?
): Boolean {
    return engine.getPermissionEngine()?.shouldShowRequestPermissionRationale(
        this, *permissions
    ) ?: false
}

fun Activity.permission_getDeniedPermissionStatus(
    engine: String? = null,
    shouldShow: Boolean,
    vararg permissions: String?
): MutableList<String> {
    return engine.getPermissionEngine()?.getDeniedPermissionStatus(
        this, shouldShow, *permissions
    ) ?: mutableListOf()
}

fun Activity.permission_againRequest(
    engine: String? = null,
    callback: IPermissionEngine.Callback?,
    deniedList: MutableList<String>?
): Int {
    return engine.getPermissionEngine()?.againRequest(this, callback, deniedList) ?: 0
}

// =============
// = 权限请求方法 =
// =============

fun Activity.permission_request(
    engine: String? = null,
    permissions: Array<out String>?
) {
    engine.getPermissionEngine()?.request(this, permissions)
}

fun Activity.permission_request(
    engine: String? = null,
    permissions: Array<out String>?,
    callback: IPermissionEngine.Callback?
) {
    engine.getPermissionEngine()?.request(this, permissions, callback)
}

fun Activity.permission_request(
    engine: String? = null,
    permissions: Array<out String>?,
    callback: IPermissionEngine.Callback?,
    againRequest: Boolean
) {
    engine.getPermissionEngine()?.request(this, permissions, callback, againRequest)
}