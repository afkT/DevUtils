package afkt.project.ui

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList
import afkt.project.model.item.ButtonValue
import afkt.project.ui.activity.ViewAssistActivity
import afkt.project.ui.activity.ViewAssistRecyclerViewLoadActivity
import afkt.project.ui.adapter.ButtonAdapter
import afkt.project.util.SkipUtils.startActivity
import dev.callback.DevItemClickCallback
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: Button 列表 Activity
 * @author Ttt
 */
class ButtonItemActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(ButtonList.getButtonValues(moduleType))
        binding.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.itemCallback = object : DevItemClickCallback<ButtonValue>() {
            override fun onItemClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
                when (buttonValue.type) {
                    ButtonValue.BTN_VIEW_ASSIST_RECYCLER -> startActivity(
                        ViewAssistRecyclerViewLoadActivity::class.java, buttonValue
                    )
                    ButtonValue.BTN_VIEW_ASSIST_ERROR, ButtonValue.BTN_VIEW_ASSIST_EMPTY, ButtonValue.BTN_VIEW_ASSIST_CUSTOM -> startActivity(
                        ViewAssistActivity::class.java, buttonValue
                    )
                    else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                }
            }
        }
        // 注册观察者
        registerAdapterDataObserver(binding.vidBvrRecy, true)
    }
}