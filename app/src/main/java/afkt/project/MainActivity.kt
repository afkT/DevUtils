package afkt.project

import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityMainBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.ModuleActivity
import afkt.project.ui.activity.DevEnvironmentLibActivity
import afkt.project.ui.adapter.ButtonAdapter
import afkt.project.util.SkipUtils
import android.Manifest
import dev.engine.log.DevLogEngine
import dev.engine.permission.DevPermissionEngine
import dev.engine.permission.IPermissionEngine
import dev.utils.app.AppCommonUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.app.toast.ToastUtils
import dev.utils.common.HttpURLConnectionUtils
import java.util.*
import kotlin.math.abs

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun isToolBar(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_main

    override fun initOther() {
        super.initOther()

        // ===========
        // = 时间校验 =
        // ===========

        HttpURLConnectionUtils.getNetTime(object : HttpURLConnectionUtils.TimeCallback {
            override fun onResponse(time: Long) {
                val curTime = System.currentTimeMillis()
                if (time >= 1) {
                    val diffTime = abs(curTime - time)
                    // 判断是否误差超过 10 秒
                    if (diffTime >= 10000L) {
                        ToastUtils.showShort("当前时间与网络时间不一致, 误差: ${diffTime / 1000} 秒")
                    }
                }
            }

            override fun onFail(e: Exception) {
                DevLogEngine.getEngine().eTag(TAG, e, "getNetTime")
            }
        })

        // ===========
        // = 申请权限 =
        // ===========

        DevPermissionEngine.getEngine().request(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            object : IPermissionEngine.Callback {
                override fun onGranted() {
                    DevLogEngine.getEngine().d("permission granted")
                }

                override fun onDenied(
                    grantedList: List<String>,
                    deniedList: List<String>,
                    notFoundList: List<String>
                ) {
                    val builder = StringBuilder()
                        .append("permission")
                        .append("\ngrantedList: ")
                        .append(grantedList.toTypedArray().contentToString())
                        .append("\ndeniedList: ")
                        .append(deniedList.toTypedArray().contentToString())
                        .append("\nnotFoundList: ")
                        .append(notFoundList.toTypedArray().contentToString())
                    DevLogEngine.getEngine().d(builder.toString())
                    // 拒绝了则再次请求处理
                    DevPermissionEngine.getEngine()
                        .againRequest(this@MainActivity, this, deniedList)
                    ToastUtils.showShort("请开启读写手机存储权限.")
                }
            }
        )
    }

    override fun initValue() {
        super.initValue()
        // 设置 Android 版本信息
        binding.vidAmAndroidTv.text = AppCommonUtils.convertSDKVersion()
        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(ButtonList.getMainButtonValues())
        binding.vidBaseRecy.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.setOnItemChildClickListener { _, _, position ->
            val buttonValue = buttonAdapter.getItem(position)
            when (buttonValue.type) {
                ButtonValue.MODULE_FRAMEWORK,
                ButtonValue.MODULE_LIB,
                ButtonValue.MODULE_UI,
                ButtonValue.MODULE_OTHER,
                ButtonValue.MODULE_DEV_WIDGET -> {
                    SkipUtils.startActivity(ModuleActivity::class.java, buttonValue)
                }
                ButtonValue.MODULE_DEV_ENVIRONMENT -> {
                    SkipUtils.startActivity(DevEnvironmentLibActivity::class.java, buttonValue)
                }
                else -> ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件")
            }
        }
    }
}