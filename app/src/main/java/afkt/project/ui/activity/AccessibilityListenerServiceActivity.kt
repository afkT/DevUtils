package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList.accessibilityListenerServiceButtonValues
import afkt.project.model.item.ButtonValue
import android.view.accessibility.AccessibilityEvent
import com.alibaba.android.arouter.facade.annotation.Route
import dev.callback.DevItemClickCallback
import dev.engine.DevEngine
import dev.service.AccessibilityListenerService
import dev.utils.app.AppUtils
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 无障碍监听服务 ( AccessibilityListenerService )
 * @author Ttt
 * @see https://www.jianshu.com/p/981e7de2c7be
 * 所需权限
 * <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"/>
 */
@Route(path = RouterPath.AccessibilityListenerServiceActivity_PATH)
class AccessibilityListenerServiceActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onDestroy() {
        super.onDestroy()
        // 注销监听
        AccessibilityListenerService.setListener(null)
        AccessibilityListenerService.stopService()
    }

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(accessibilityListenerServiceButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_ACCESSIBILITY_SERVICE_CHECK -> {
                            val check =
                                AccessibilityListenerService.isAccessibilitySettingsOn(AppUtils.getPackageName())
                            showToast(check, "已开启无障碍功能", "未开启无障碍功能")
                        }
                        ButtonValue.BTN_ACCESSIBILITY_SERVICE_REGISTER -> {
                            if (!AccessibilityListenerService.checkAccessibility()) {
                                showToast(false, "请先开启无障碍功能")
                                return
                            }
                            showToast(true, "绑定无障碍监听服务成功, 请查看 Logcat")
                            // 注册监听
                            AccessibilityListenerService.startService()
                        }
                        ButtonValue.BTN_ACCESSIBILITY_SERVICE_UNREGISTER -> {
                            showToast(true, "注销无障碍监听服务成功")
                            // 注销监听
                            AccessibilityListenerService.stopService()
                        }
                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)
    }

    override fun initListener() {
        super.initListener()

        // 设置监听事件
        AccessibilityListenerService.setListener(object : AccessibilityListenerService.Listener {
            override fun onAccessibilityEvent(
                accessibilityEvent: AccessibilityEvent?,
                accessibilityListenerService: AccessibilityListenerService?
            ) {
                val builder = StringBuilder()
                    .append("onAccessibilityEvent")
                    .append("\naccessibilityEvent: ")
                    .append(accessibilityEvent)
                DevEngine.getLog()?.dTag(TAG, builder.toString())
            }

            override fun onInterrupt() {
                super.onInterrupt()
                DevEngine.getLog()?.dTag(TAG, "onInterrupt")
            }

            override fun onServiceCreated(service: AccessibilityListenerService?) {
                super.onServiceCreated(service)
                DevEngine.getLog()?.dTag(TAG, "onServiceCreated")
            }

            override fun onServiceDestroy() {
                super.onServiceDestroy()
                DevEngine.getLog()?.dTag(TAG, "onServiceDestroy")
            }
        })
    }
}