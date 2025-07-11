package afkt.project.feature.other_function.service

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.base.project.bindAdapter
import afkt.project.model.data.button.ButtonList.accessibilityListenerServiceButtonValues
import afkt.project.model.data.button.ButtonValue
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.accessibility.AccessibilityEvent
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.utils.app.AppUtils
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 无障碍监听服务 ( AccessibilityListenerService )
 * @author Ttt
 * @see https://www.jianshu.com/p/981e7de2c7be
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
@Route(path = RouterPath.OTHER_FUNCTION.AccessibilityListenerServiceActivity_PATH)
class AccessibilityListenerServiceActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is AccessibilityListenerServiceActivity) {
                it.apply {
                    binding.vidRv.bindAdapter(accessibilityListenerServiceButtonValues) { buttonValue ->
                        when (buttonValue.type) {
                            ButtonValue.BTN_ACCESSIBILITY_SERVICE_CHECK -> {
                                val check = AccessibilityListenerService.isAccessibilitySettingsOn(
                                    AppUtils.getPackageName()
                                )
                                showToast(check, "已开启无障碍功能", "未开启无障碍功能")
                            }

                            ButtonValue.BTN_ACCESSIBILITY_SERVICE_REGISTER -> {
                                if (!AccessibilityListenerService.checkAccessibility()) {
                                    showToast(false, "请先开启无障碍功能")
                                } else {
                                    showToast(true, "绑定无障碍监听服务成功, 请查看 Logcat")
                                    // 注册监听
                                    AccessibilityListenerService.startService()
                                }
                            }

                            ButtonValue.BTN_ACCESSIBILITY_SERVICE_UNREGISTER -> {
                                showToast(true, "注销无障碍监听服务成功")
                                // 注销监听
                                AccessibilityListenerService.stopService()
                            }

                            else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                        }
                    }
                }
            }
        }
    ) {

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        AccessibilityListenerService.setListener(null)
        AccessibilityListenerService.stopService()
    }

    override fun initListener() {
        super.initListener()

        // 设置监听事件
        AccessibilityListenerService.setListener(object :
            AccessibilityListenerService.Listener {
            override fun onAccessibilityEvent(
                accessibilityEvent: AccessibilityEvent?,
                accessibilityListenerService: AccessibilityListenerService?
            ) {
                val builder = StringBuilder()
                    .append("onAccessibilityEvent")
                    .append("\naccessibilityEvent: ")
                    .append(accessibilityEvent)
                TAG.log_dTag(
                    message = builder.toString()
                )
            }

            override fun onInterrupt() {
                super.onInterrupt()
                TAG.log_dTag(
                    message = "onInterrupt"
                )
            }

            override fun onServiceCreated(service: AccessibilityListenerService?) {
                super.onServiceCreated(service)
                TAG.log_dTag(
                    message = "onServiceCreated"
                )
            }

            override fun onServiceDestroy() {
                super.onServiceDestroy()
                TAG.log_dTag(
                    message = "onServiceDestroy"
                )
            }
        })
    }
}