package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.BaseApplication
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ButtonAdapter
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.model.item.RouterPath
import com.therouter.router.Route
import dev.callback.DevItemClickCallback
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 奔溃日志捕获
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.CrashCatchActivity_PATH)
class CrashCatchActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        /**
         * 捕获异常处理 CrashUtils.getInstance().initialize()
         * 参考 [BaseApplication.initCrash]
         */

        // 初始化布局管理器、适配器
        ButtonAdapter(ButtonList.crashButtonValues)
            .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
                override fun onItemClick(
                    buttonValue: ButtonValue,
                    param: Int
                ) {
                    when (buttonValue.type) {
                        ButtonValue.BTN_CRASH_CLICK_CATCH -> {
                            val data: String? = null
                            data!!.split(",".toRegex()).toTypedArray()
                        }

                        else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                    }
                }
            }).bindAdapter(binding.vidRv)
    }
}