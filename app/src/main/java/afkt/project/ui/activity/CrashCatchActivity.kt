package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 奔溃日志捕获
 * @author Ttt
 */
class CrashCatchActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        /**
         * 捕获异常处理 CrashUtils.getInstance().init()
         * 参考 [BaseApplication.initCrash]
         */

        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(ButtonList.getCrashButtonValues())
        binding.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.setOnItemChildClickListener { adapter, view, position ->
            val buttonValue = buttonAdapter.getItem(position)
            when (buttonValue.type) {
                ButtonValue.BTN_CRASH_CLICK_CATCH -> {
                    val data: String? = null
                    data!!.split(",".toRegex()).toTypedArray()
                }
                else -> ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件")
            }
        }
    }
}