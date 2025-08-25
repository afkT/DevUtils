package afkt.environment.use

import afkt.environment.use.base.BaseViewModel
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kongzue.dialogx.dialogs.InputDialog
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.toast.toast_showLong
import dev.engine.extensions.toast.toast_showShort
import dev.environment.DevEnvironment
import dev.environment.DevEnvironmentUtils
import dev.environment.bean.EnvironmentBean
import dev.environment.listener.OnEnvironmentChangeListener
import dev.utils.common.StringUtils

class MainViewModel : BaseViewModel() {

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
                toast_showShort(text = "设置【全部】成功")
                // 跳转进行查看更新值
                clickCustomIMPL.onClick(view)
                return@setOkButton false
            }
    }

    // 重置【全部】Selected Environment
    val clickResetAllSelectedEnvironment = View.OnClickListener { view ->
        val result = DevEnvironment.reset(view.context)
        toast_showShort(text = (if (result) "重置【全部】成功" else "重置【全部】失败"))
        // 跳转进行查看更新值
        clickCustomIMPL.onClick(view)
        /**
         * 重置不是变更，所以不会触发 EnvironmentChangeListener 事件
         * 可以在调用重置方法后自行实现后续逻辑等同 Listener 通知
         */
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
        toast_showShort(text = (if (result) "重置【Service】成功" else "重置【Service】失败"))
        /**
         * 重置不是变更，所以不会触发 EnvironmentChangeListener 事件
         * 可以在调用重置方法后自行实现后续逻辑等同 Listener 通知
         */
    }

    // =================
    // = CustomFragment =
    // =================

    // 自定义 UI Adapter 模型
    val customAdapterModel = CustomAdapterModel()

    // ==============
    // = 使用示例介绍 =
    // ==============

    private val tests = arrayOf(

        "每个 <b>@Module</b> 都会生成七个方法，以 Service Module 演示操作为例, 具体查看示例代码",
        "1. <font color='red'>getXXModule</font> 获取对应 Module 映射实体类 ModuleBean",
        "2. <font color='red'>getXXReleaseEnvironment</font> 获取对应 Module isRelease 值为 true 的 Environment 映射实体类 EnvironmentBean",
        "3. <font color='red'>getXXEnvironment</font> 获取对应 Module 选中的 Environment ( 默认选中 isRelease 值为 true 的 <b>@Environment</b> )",
        "4. <font color='red'>getXXEnvironmentValue</font> 获取对应 Module 选中的 Environment Value",
        "5. <font color='red'>setXXEnvironment</font> 设置对应 Module 选中的 Environment",
        "6. <font color='red'>resetXX</font> 用于删除对应 Module 选中的 Environment Config File",
        "7. <font color='red'>isXXAnnotation</font> 用于判断对应 Module 选中的 Environment 是否属于注解环境配置 ( <b>是否通过 @Environment 注解</b> )",

        // =======
        // = 依赖 =
        // =======

        "<font color='blue'><b>DevEnvironmentCompiler、DevEnvironmentCompilerRelease 编译区别：具体查看 README</b></font>",
        "<font color='red'><b>DevEnvironmentCompilerRelease</b></font> 属于 Release ( 打包 / 编译 ) 注解处理器，使用该方式编译生成的 DevEnvironment.java 类，<b>每个 Module 只会生成一个常量 Environment，并且无法进行修改设置</b>",
        "<font color='red'><b>DevEnvironmentCompiler</b></font> 属于 Debug ( 打包 / 编译 ) 注解处理器，使用该方式编译生成的 DevEnvironment.java 类，允许设置选中的环境 ( `setXXEnvironment` 通过该方法设置，只有使用该注解编译才会实现该方法代码 )",

        // ============
        // = 可视化操作 =
        // ============

        "<font color='teal'><b>DevEnvironment 提供了可视化操作、代码操作两种方式，进行修改环境配置信息【具体查看示例代码】</b></font>",
        "需依赖 <font color='red'><b>'io.github.afkt:DevEnvironment:version'</b></font>，<font color='blue'>内置可视化操作【强制竖屏】</font>，对于有横屏需求的项目，<font color='purple'>参考【自定义】切换环境【UI、功能】按钮实现代码</font>",
        "【前提】修改环境变量配置，需通过依赖 <font color='red'><b>DevEnvironmentCompiler</b></font> 进行编译"
    )

    // 使用示例介绍文案
    val useExampleText = ObservableField(StringBuilder().apply {
//        // DevEnvironment - Android 环境配置切换库
//        implementation 'io.github.afkt:DevEnvironment:version'
//        kaptDebug 'io.github.afkt:DevEnvironmentCompiler:version'
//        kaptRelease 'io.github.afkt:DevEnvironmentCompilerRelease:version'

        for (i in 0..7) {
            append("${tests[i]}<br>")
        }

        append("<p></p><p></p>")
        append(tests[8])
        append("<p>${tests[9]}</p>")
        append("<p>${tests[10]}</p>")

        append("<p></p><p></p>")
        append(tests[11])
        append("<p>${tests[12]}</p>")
        append(tests[13])

        /**
         * 每个 @Module 都会生成七个方法，以 Service Module 演示操作为例, 具体查看示例代码
         * 1. getXXModule() => 获取对应 Module 映射实体类 ModuleBean
         * 2. getXXReleaseEnvironment() => 获取对应 Module isRelease 值为 true 的 Environment 映射实体类 EnvironmentBean
         * 3. getXXEnvironment() => 获取对应 Module 选中的 Environment ( 默认选中 isRelease 值为 true 的 @Environment )
         * 4. getXXEnvironmentValue() => 获取对应 Module 选中的 Environment Value
         * 5. setXXEnvironment() => 设置对应 Module 选中的 Environment
         * 6. resetXX() => 用于删除对应 Module 选中的 Environment Config File
         * 7. isXXAnnotation() => 用于判断对应 Module 选中的 Environment 是否属于注解环境配置 ( 是否通过 @Environment 注解 )
         */

        /**
         * DevEnvironmentCompiler、DevEnvironmentCompilerRelease 编译区别：具体查看 README
         *
         * DevEnvironmentCompilerRelease 属于 Release ( 打包 / 编译 ) 注解处理器
         * 使用该方式编译生成的 DevEnvironment.java 类，
         * 每个 Module 只会生成一个常量 Environment，并且无法进行修改设置
         *
         * DevEnvironmentCompiler 属于 Debug ( 打包 / 编译 ) 注解处理器
         * 使用该方式编译生成的 DevEnvironment.java 类，
         * 允许设置选中的环境 ( `setXXEnvironment` 通过该方法设置，只有使用该注解编译才会实现该方法代码 )
         */

        /**
         * DevEnvironment 提供了可视化操作、代码操作两种方式，进行修改环境配置信息【具体查看示例代码】
         *
         * 需依赖 'io.github.afkt:DevEnvironment:version' 内置可视化操作【强制竖屏】
         * 对于有横屏需求的项目，参考【自定义】切换环境【UI、功能】按钮实现代码
         *
         * 【前提】修改环境变量配置，需通过依赖 kapt 'io.github.afkt:DevEnvironmentCompiler:version' 进行编译
         */
    }.toString())
}