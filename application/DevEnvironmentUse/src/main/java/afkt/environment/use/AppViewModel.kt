package afkt.environment.use

import afkt.environment.use.base.BaseViewModel
import android.view.View
import com.kongzue.dialogx.dialogs.InputDialog
import dev.environment.DevEnvironment
import dev.environment.DevEnvironmentUtils
import dev.environment.bean.EnvironmentBean
import dev.environment.listener.OnEnvironmentChangeListener
import dev.expand.engine.log.log_dTag
import dev.expand.engine.toast.toast_showShort
import dev.utils.common.StringUtils

/**
 * detail: 整个 App ViewModel
 * @author Ttt
 */
class AppViewModel : BaseViewModel() {

    // 日志 TAG
    private val TAG = AppViewModel::class.java.simpleName

    // 环境改变触发事件
    private val changeListener =
        OnEnvironmentChangeListener { module, oldEnvironment, newEnvironment ->
            // 可以进行重新请求等操作
            StringBuilder().apply {
                append("module")
                append("\nname: ").append(module.name)
                append("\nalias: ").append(module.alias)
                append("\n\n")
                append("oldEnvironment")
                append("\nname: ").append(oldEnvironment.name)
                append("\nvalue: ").append(oldEnvironment.value)
                append("\nalias: ").append(oldEnvironment.alias)
                append("\n\n")
                append("newEnvironment")
                append("\nname: ").append(newEnvironment.name)
                append("\nvalue: ").append(newEnvironment.value)
                append("\nalias: ").append(newEnvironment.alias)
                TAG.log_dTag(message = toString())
            }
        }

    init {
        // 添加模块环境改变触发事件
        DevEnvironment.addOnEnvironmentChangeListener(changeListener)
    }

    // ===============
    // = MainFragment =
    // ===============

    // 内置切换环境 Activity【默认实现】
    val clickDefaultIMPL = View.OnClickListener { view ->
        // 跳转 DevEnvironment Activity
        DevEnvironmentUtils.start(view.context)
    }

    //【自定义配置】动态配置【Service】Module
    val clickSetServiceModule = View.OnClickListener { view ->
        InputDialog.show(
            "自定义配置", "动态配置【Service】Module 配置值",
            "确定", "取消", "https://"
        ).setCancelable(false)
            .setOkButton { baseDialog, v, input ->
                if (StringUtils.isEmpty(input)) {
                    toast_showShort(text = "请输入环境配置值")
                    return@setOkButton true
                }
                val module = DevEnvironment.getServiceModule()
                // 如果准备设置环境等于当前选中的环境, 则会返回 false
                val custom = EnvironmentBean(
                    "自定义配置", input, "动态配置", module
                )
                // 设置 Service [ Module ] Selected Environment Bean
                val result = DevEnvironment.setServiceEnvironment(
                    view.context, custom
                )
                toast_showShort(text = (if (result) "设置成功" else "设置失败"))
                // 跳转进行查看更新值
                clickDefaultIMPL.onClick(view)
                return@setOkButton false
            }
    }

    //【自定义配置】动态配置【全部】Module
    val clickSetAllModule = View.OnClickListener { view ->
        InputDialog.show(
            "自定义配置", "动态配置【全部】Module 配置值",
            "确定", "取消", "https://"
        ).setCancelable(false)
            .setOkButton { baseDialog, v, input ->
                if (StringUtils.isEmpty(input)) {
                    toast_showShort(text = "请输入环境配置值")
                    return@setOkButton true
                }
                DevEnvironment.getModuleList().forEach { module ->
                    // 如果准备设置环境等于当前选中的环境, 则会返回 false
                    val custom = EnvironmentBean(
                        "自定义配置", input, "动态配置", module
                    )
//                    // 方式一
//                    if (module.name == "Service") {
//                        // 设置 Service [ Module ] Selected Environment Bean
//                        DevEnvironment.setServiceEnvironment(
//                            view.context, custom
//                        )
//                    }
                    // 方式二
                    DevEnvironmentUtils.setModuleEnvironment(
                        view.context, custom
                    )
                }
                toast_showShort(text = "设置成功")
                // 跳转进行查看更新值
                clickDefaultIMPL.onClick(view)
                return@setOkButton false
            }
    }
}