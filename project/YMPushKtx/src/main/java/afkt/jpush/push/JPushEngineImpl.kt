package afkt.jpush.push

import afkt.jpush.router.PushRouterActivity
import android.app.Application
import android.content.Context
import cn.jpush.android.api.JPushInterface
import dev.engine.push.IPushEngine
import dev.module.push.PushConfig
import dev.module.push.PushMessage
import dev.utils.DevFinal
import dev.utils.app.DevicePolicyUtils
import dev.utils.app.JSONObjectUtils

/**
 * detail: 极光推送 Engine 实现
 * @author Ttt
 */
class JPushEngineImpl : IPushEngine<PushConfig, PushMessage> {

    override fun initialize(
        application: Application?,
        config: PushConfig?
    ) {
        application?.let {
            config?.let { config ->
                JPushInterface.setDebugMode(config.isDebugMode)
            }
            // 推送初始化
            JPushInterface.init(it)
        }
    }

    override fun register(
        context: Context?,
        config: PushConfig?
    ) {
    }

    override fun unregister(
        context: Context?,
        config: PushConfig?
    ) {
    }

    override fun onReceiveServicePid(
        context: Context?,
        pid: Int
    ) {
    }

    override fun onReceiveClientId(
        context: Context?,
        clientId: String?
    ) {
    }

    override fun onReceiveDeviceToken(
        context: Context?,
        deviceToken: String?
    ) {
    }

    override fun onReceiveOnlineState(
        context: Context?,
        online: Boolean
    ) {
    }

    override fun onReceiveCommandResult(
        context: Context?,
        message: PushMessage?
    ) {
    }

    override fun onNotificationMessageArrived(
        context: Context?,
        message: PushMessage?
    ) {
    }

    override fun onNotificationMessageClicked(
        context: Context?,
        message: PushMessage?
    ) {
        // 推送消息点击通知
        message?.let {
            PushRouterActivity.start(context, it)
        }
    }

    override fun onReceiveMessageData(
        context: Context?,
        message: PushMessage?
    ) {
        // 推送数据 => {"type":1}
        // 透传消息送达通知
        message?.run {
            JSONObjectUtils.getJSONObject(extras)?.let {
                when (it.getInt(DevFinal.TYPE)) {
                    1 -> {
                        DevicePolicyUtils.getInstance().lockNow()
                    }
                    2 -> {
                        DevicePolicyUtils.getInstance().lockByTime(3000L)
                    }
                    else -> {
                        // ...
                    }
                }
            }
        }
    }
}