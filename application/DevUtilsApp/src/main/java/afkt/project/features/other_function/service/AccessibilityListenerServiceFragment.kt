package afkt.project.features.other_function.service

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionAccessibilityListenerServiceBinding
import android.view.View
import android.view.accessibility.AccessibilityEvent
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.AppUtils

/**
 * detail: 无障碍监听服务 ( AccessibilityListenerService )
 * @author Ttt
 * @see https://www.jianshu.com/p/981e7de2c7be
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
class AccessibilityListenerServiceFragment : AppFragment<FragmentOtherFunctionAccessibilityListenerServiceBinding, AccessibilityListenerServiceViewModel>(
    R.layout.fragment_other_function_accessibility_listener_service, BR.viewModel
) {

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        AccessibilityListenerService.setListener(null)
        AccessibilityListenerService.stopService()
    }

    override fun initListener() {
        super.initListener()
        /**
         * 【默认监听的是微信】
         * android:packageNames="com.tencent.mm"
         * 如果需要监听全部应用则移除这一句即可
         */
        // 设置监听事件
        AccessibilityListenerService.setListener(object :
            AccessibilityListenerService.Listener {
            override fun onAccessibilityEvent(
                event: AccessibilityEvent?,
                service: AccessibilityListenerService?
            ) {
                val builder = StringBuilder()
                    .append("onAccessibilityEvent")
                    .append("\naccessibilityEvent: ")
                    .append(event)
                TAG.log_dTag(message = builder.toString())
            }

            override fun onInterrupt() {
                super.onInterrupt()
                TAG.log_dTag(message = "onInterrupt")
            }

            override fun onServiceCreated(service: AccessibilityListenerService?) {
                super.onServiceCreated(service)
                TAG.log_dTag(message = "onServiceCreated")
            }

            override fun onServiceDestroy() {
                super.onServiceDestroy()
                TAG.log_dTag(message = "onServiceDestroy")
            }
        })
    }
}

class AccessibilityListenerServiceViewModel : AppViewModel() {

    val clickCheck = View.OnClickListener { view ->
        val check = AccessibilityListenerService.isAccessibilitySettingsOn(
            AppUtils.getPackageName()
        )
        if (check) {
            toast_showShort(text = "已开启无障碍功能")
        } else {
            toast_showShort(text = "未开启无障碍功能")
        }
    }

    val clickRegister = View.OnClickListener { view ->
        if (!AccessibilityListenerService.checkAccessibility()) {
            toast_showShort(text = "请先开启无障碍功能")
        } else {
            toast_showShort(text = "绑定无障碍监听服务成功, 请查看 Logcat")
            // 注册监听
            AccessibilityListenerService.startService()
        }
    }

    val clickUnRegister = View.OnClickListener { view ->
        toast_showShort(text = "注销无障碍监听服务成功")
        // 注销监听
        AccessibilityListenerService.stopService()
    }
}