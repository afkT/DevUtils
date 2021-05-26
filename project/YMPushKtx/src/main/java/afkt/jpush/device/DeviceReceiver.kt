package afkt.jpush.device

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import dev.utils.app.logger.DevLogger

/**
 * detail: 设备管理广播监听
 * @author Ttt
 */
class DeviceReceiver : DeviceAdminReceiver() {

    // 日志 TAG
    private val TAG = DeviceReceiver::class.java.simpleName

    override fun onEnabled(
        context: Context,
        intent: Intent
    ) {
        DevLogger.dTag(TAG, "[onEnabled] 设备管理: 可用")
    }

    override fun onDisabled(
        context: Context,
        intent: Intent
    ) {
        DevLogger.dTag(TAG, "[onDisabled] 设备管理: 不可用")
    }

    override fun onDisableRequested(
        context: Context,
        intent: Intent
    ): CharSequence {
        DevLogger.dTag(TAG, "[onDisableRequested] 设备管理: ACTION_DEVICE_ADMIN_DISABLE_REQUESTED")
        return "这是一个可选的消息, 警告有关禁止用户的请求"
    }

    override fun onPasswordChanged(
        context: Context,
        intent: Intent,
        user: UserHandle
    ) {
        DevLogger.dTag(TAG, "[onPasswordChanged] 设备管理: 密码己修改")
    }

    override fun onPasswordFailed(
        context: Context,
        intent: Intent,
        user: UserHandle
    ) {
        DevLogger.dTag(TAG, "[onPasswordFailed] 设备管理: 修改密码失败")
    }

    override fun onPasswordSucceeded(
        context: Context,
        intent: Intent,
        user: UserHandle
    ) {
        DevLogger.dTag(TAG, "[onPasswordSucceeded] 设备管理: 修改密码成功")
    }

    override fun onPasswordExpiring(
        context: Context,
        intent: Intent,
        user: UserHandle
    ) {
        DevLogger.dTag(TAG, "[onPasswordExpiring] 设备管理: 密码即将到期")
    }
}