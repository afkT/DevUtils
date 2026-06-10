package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEnginePushBinding
import android.view.View
import dev.engine.extensions.push.push_onReceiveClientId
import dev.engine.extensions.push.push_onReceiveDeviceToken
import dev.engine.extensions.push.push_onReceiveOnlineState
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: Push Engine 推送平台处理
 * @author Ttt
 * Push Engine 默认未配置实现, 需依赖具体推送平台后注册实现
 */
class PushFragment : AppFragment<FragmentDevEnginePushBinding, PushViewModel>(
    R.layout.fragment_dev_engine_push, BR.viewModel
)

/**
 * detail: Push Engine 推送平台处理 ViewModel
 * @author Ttt
 */
class PushViewModel : AppViewModel() {

    val clickClientId = View.OnClickListener { view ->
        view.context.push_onReceiveClientId(clientId = "mock_client_id")
        "onReceiveClientId 已调用".toast_showShort()
    }

    val clickDeviceToken = View.OnClickListener { view ->
        view.context.push_onReceiveDeviceToken(deviceToken = "mock_device_token")
        "onReceiveDeviceToken 已调用".toast_showShort()
    }

    val clickOnlineState = View.OnClickListener { view ->
        view.context.push_onReceiveOnlineState(online = true)
        "onReceiveOnlineState 已调用".toast_showShort()
    }
}