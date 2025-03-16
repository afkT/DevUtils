package afkt.project.ui

import android.Manifest
import android.app.Activity
import dev.engine.DevEngine
import dev.engine.permission.IPermissionEngine
import dev.expand.engine.log.log_dTag
import dev.expand.engine.permission.permission_againRequest
import dev.expand.engine.permission.permission_request
import dev.utils.app.toast.ToastTintUtils

// ==========
// = 申请权限 =
// ==========

object PermissionRequestUse {

    const val TAG = "_permission"

    fun Activity._permission() {

        // 方式一
        permission_request(
            permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            callback = object : IPermissionEngine.Callback {
                override fun onGranted() {
                    TAG.log_dTag(
                        message = "permission granted"
                    )
                }

                override fun onDenied(
                    grantedList: MutableList<String>,
                    deniedList: MutableList<String>,
                    notFoundList: MutableList<String>
                ) {
                    TAG.log_dTag(
                        message = StringBuilder().apply {
                            append("permission")
                            append("\ngrantedList: ")
                            append(grantedList.toTypedArray().contentToString())
                            append("\ndeniedList: ")
                            append(deniedList.toTypedArray().contentToString())
                            append("\nnotFoundList: ")
                            append(notFoundList.toTypedArray().contentToString())
                        }.toString()
                    )
                    // 拒绝了则再次请求处理
                    permission_againRequest(
                        callback = this,
                        deniedList = deniedList
                    )
                    ToastTintUtils.error("请开启读写手机存储权限")
                }
            }
        )

        // 方式二
        DevEngine.getPermission()?.request(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), object : IPermissionEngine.Callback {
                override fun onGranted() {
                    TAG.log_dTag(
                        message = "permission granted"
                    )
                }

                override fun onDenied(
                    grantedList: MutableList<String>,
                    deniedList: MutableList<String>,
                    notFoundList: MutableList<String>
                ) {
                    TAG.log_dTag(
                        message = StringBuilder().apply {
                            append("permission")
                            append("\ngrantedList: ")
                            append(grantedList.toTypedArray().contentToString())
                            append("\ndeniedList: ")
                            append(deniedList.toTypedArray().contentToString())
                            append("\nnotFoundList: ")
                            append(notFoundList.toTypedArray().contentToString())
                        }.toString()
                    )
                    // 拒绝了则再次请求处理
                    DevEngine.getPermission()
                        .againRequest(this@_permission, this, deniedList)
                    ToastTintUtils.error("请开启读写手机存储权限")
                }
            }
        )
    }
}