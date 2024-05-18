package afkt.project.feature.dev_environment

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.data_model.button.ButtonList.moduleDevEnvironmentButtonValues
import afkt.project.data_model.button.ButtonValue
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import com.therouter.router.Route
import dev.callback.DevItemClickCallback
import dev.environment.DevEnvironment
import dev.environment.DevEnvironmentUtils
import dev.environment.bean.EnvironmentBean
import dev.expand.engine.log.log_dTag
import dev.utils.app.ActivityUtils
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: DevEnvironment 环境配置切换库
 * @author Ttt
 */
@Route(path = RouterPath.DEV_LIBS.DevEnvironmentLibActivity_PATH)
class DevEnvironmentLibActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        ButtonAdapter(moduleDevEnvironmentButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    val result: Boolean
                    when (buttonValue.type) {
                        ButtonValue.BTN_DEV_ENVIRONMENT -> {
                            result = DevEnvironmentUtils.start(mContext) {
                                ActivityUtils.getManager().exitApplication()
                            }
                            showToast(result, "跳转成功", "跳转失败")
                        }

                        ButtonValue.BTN_USE_CUSTOM -> {
                            // 如果准备设置环境等于当前选中的环境, 则会返回 false
                            val custom =
                                EnvironmentBean(
                                    "自定义配置", "https://custom.com",
                                    "动态自定义", DevEnvironment.getServiceModule()
                                )
                            result = DevEnvironment.setServiceEnvironment(mContext, custom)
                            showToast(result, "设置成功", "设置失败")
                        }

                        else -> ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)

        // 环境改变通知
        DevEnvironment.addOnEnvironmentChangeListener { module, oldEnvironment, newEnvironment -> // 可以进行重新请求等操作
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

                val content = toString()
                ToastTintUtils.normal(content)
                TAG.log_dTag(
                    message = content
                )
            }
        }
    }
}