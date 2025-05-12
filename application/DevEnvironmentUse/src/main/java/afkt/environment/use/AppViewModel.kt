package afkt.environment.use

import afkt.environment.use.base.BaseViewModel
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kongzue.dialogx.dialogs.InputDialog
import dev.environment.DevEnvironment
import dev.environment.DevEnvironmentUtils
import dev.environment.bean.EnvironmentBean
import dev.environment.listener.OnEnvironmentChangeListener
import dev.expand.engine.log.log_dTag
import dev.expand.engine.toast.toast_showLong
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

    private val _clickCustomEvent = MutableLiveData<Unit>()
    val clickCustomEvent: LiveData<Unit> get() = _clickCustomEvent

    // DevEnvironment:Version
    val devEnvironmentVersion = ObservableField(
        "DevEnvironment:${DevEnvironmentUtils.getDevEnvironmentVersion()}"
    )

    // 内置切换环境 Activity【默认实现】
    val clickDefaultIMPL = View.OnClickListener { view ->
        // 跳转 DevEnvironment Activity
        DevEnvironmentUtils.start(view.context)
    }

    //【自定义】切换环境【UI、功能】
    val clickCustomIMPL = View.OnClickListener { view ->
        _clickCustomEvent.value = Unit
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
//                    // ...
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

    // 是否 releaseAnnotationProcessor、kaptRelease 构建
    val clickReleaseBuild = View.OnClickListener { view ->
        val isRelease = DevEnvironment.isRelease()
        toast_showShort(text = (if (isRelease) "release build" else "debug build"))
    }

    // 获取【Service】Release Environment
    val clickGetServiceReleaseEnvironment = View.OnClickListener { view ->
        val serviceRelease = DevEnvironment.getServiceReleaseEnvironment()
//        val serviceReleaseValue = serviceRelease.value
        toast_showLong(text = "Service【Module】Release Environment：\n$serviceRelease")
    }

    // 获取【Service】Selected Environment
    val clickGetServiceSelectedEnvironment = View.OnClickListener { view ->
        val serviceSelected = DevEnvironment.getServiceEnvironment(view.context)
//        val serviceSelectedValue = DevEnvironment.getServiceEnvironmentValue(view.context)
        toast_showLong(text = "Service【Module】Selected Environment：\n$serviceSelected")
    }

    // 是否【Service】注解配置 Environment
    val clickCheckServiceAnnotationEnvironment = View.OnClickListener { view ->
        // 判断当前选中的 Environment 是否属于通过 @Environment 注解配置的环境，而不是通过自定义设置
        val isAnnotation = DevEnvironment.isServiceAnnotation(view.context)
        toast_showShort(text = (if (isAnnotation) "属于注解配置" else "属于自定义配置"))
    }

    // 重置【Service】Selected Environment
    val clickResetServiceSelectedEnvironment = View.OnClickListener { view ->
        // 重置当前选中的 Environment 默认设置为 Release Environment
        val result = DevEnvironment.resetService(view.context)
        toast_showShort(text = (if (result) "重置成功" else "重置失败"))
    }

    // =================
    // = CustomFragment =
    // =================

    private val _clickBackEvent = MutableLiveData<Unit>()
    val clickBackEvent: LiveData<Unit> get() = _clickBackEvent

    /**
     * 点击顶部返回按钮
     */
    fun postClickBack() {
        _clickBackEvent.value = Unit
    }

    // 自定义 UI Adapter 模型
    val customAdapterModel = CustomAdapterModel()
}