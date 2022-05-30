package afkt.project

import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityMainBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import android.Manifest
import dev.callback.DevItemClickCallback
import dev.engine.permission.IPermissionEngine
import dev.kotlin.engine.log.log_dTag
import dev.kotlin.engine.log.log_eTag
import dev.kotlin.engine.permission.permission_againRequest
import dev.kotlin.engine.permission.permission_request
import dev.utils.app.VersionUtils
import dev.utils.app.toast.ToastUtils
import dev.utils.common.HttpURLConnectionUtils
import kotlin.math.abs

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun isToolBar(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_main

    override fun initOther() {
        super.initOther()

        // ==========
        // = 时间校验 =
        // ==========

        HttpURLConnectionUtils.getNetTime(object : HttpURLConnectionUtils.TimeCallback {
            override fun onResponse(millis: Long) {
                val curTime = System.currentTimeMillis()
                if (millis >= 1) {
                    val diffTime = abs(curTime - millis)
                    // 判断是否误差超过 10 秒
                    if (diffTime >= 10000L) {
                        ToastUtils.showShort("当前时间与网络时间不一致, 误差: ${diffTime / 1000} 秒")
                    }
                }
            }

            override fun onFail(error: Throwable) {
                TAG.log_eTag(
                    throwable = error,
                    message = "getNetTime"
                )
            }
        })

        // ==========
        // = 申请权限 =
        // ==========

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
                    ToastUtils.showShort("请开启读写手机存储权限.")
                }
            }
        )

//        // 方式二
//        DevEngine.getPermission()?.request(
//            this, arrayOf(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ), object : IPermissionEngine.Callback {
//                override fun onGranted() {
//                    TAG.log_dTag(
//                        message = "permission granted"
//                    )
//                }
//
//                override fun onDenied(
//                    grantedList: MutableList<String>,
//                    deniedList: MutableList<String>,
//                    notFoundList: MutableList<String>
//                ) {
//                    TAG.log_dTag(
//                        message = StringBuilder().apply {
//                            append("permission")
//                            append("\ngrantedList: ")
//                            append(grantedList.toTypedArray().contentToString())
//                            append("\ndeniedList: ")
//                            append(deniedList.toTypedArray().contentToString())
//                            append("\nnotFoundList: ")
//                            append(notFoundList.toTypedArray().contentToString())
//                        }.toString()
//                    )
//                    // 拒绝了则再次请求处理
//                    DevEngine.getPermission()
//                        .againRequest(this@MainActivity, this, deniedList)
//                    ToastUtils.showShort("请开启读写手机存储权限.")
//                }
//            }
//        )
    }

    override fun initValue() {
        super.initValue()
        // 设置 Android 版本信息
        binding.vidAndroidTv.text = VersionUtils.convertSDKVersion()
        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.mainButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    routerActivity(buttonValue)
                }
            }).bindAdapter(binding.vidInclude.vidRv)
    }
}