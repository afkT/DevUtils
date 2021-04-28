package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.item.ButtonList.eventButtonValues
import afkt.project.model.item.ButtonValue
import afkt.project.ui.adapter.ButtonAdapter
import dev.base.DevObject
import dev.callback.DevItemClickCallback
import dev.engine.log.DevLogEngine
import dev.other.EventBusUtils
import dev.utils.app.toast.ToastTintUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * detail: EventBusUtils
 * @author Ttt
 * [ToastTintUse]
 */
class EventBusActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        // 初始化布局管理器、适配器
        val buttonAdapter = ButtonAdapter(eventButtonValues)
        binding.vidBvrRecy.adapter = buttonAdapter
        buttonAdapter.itemCallback = object : DevItemClickCallback<ButtonValue>() {
            override fun onItemClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
                when (buttonValue.type) {
                    ButtonValue.BTN_EVENT_REGISTER -> {
                        showToast(true, "注册成功")
                        EventBusUtils.register(this@EventBusActivity)
                    }
                    ButtonValue.BTN_EVENT_UNREGISTER -> {
                        showToast(true, "解绑成功")
                        EventBusUtils.unregister(this@EventBusActivity)
                    }
                    ButtonValue.BTN_EVENT_CLEAN_STICKY -> {
                        showToast(true, "清空全部粘性事件成功")
                        EventBusUtils.removeAllStickyEvents()
                    }
                    ButtonValue.BTN_EVENT_SEND -> {
                        showToast(true, "发送事件成功")
                        val event = DevObject<String>()
                        event.setCode(1).setObject("正常消息")
                        EventBusUtils.post(event)
                    }
                    ButtonValue.BTN_EVENT_SEND_STICKY -> {
                        showToast(true, "发送粘性事件成功")
                        val eventSticky = DevObject<String>()
                        eventSticky.setCode(2).setObject("粘性消息")
                        EventBusUtils.postSticky(eventSticky)
                    }
                    else -> ToastTintUtils.warning("未处理 ${buttonValue.text} 事件")
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBus(event: DevObject<String>) {
        // 打印数据
        DevLogEngine.getEngine().dTag(TAG, "value %s", event.getObject())
        // 进行提示
        ToastTintUtils.normal(event.code + "." + event.getObject())
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEventBusSticky(event: DevObject<String>) {
        // 打印数据
        DevLogEngine.getEngine().dTag(TAG, "value %s", event.getObject())
        // 进行提示
        ToastTintUtils.warning(event.code + "." + event.getObject())

//        // 接收到后, 需要把旧的粘性事件删除 - 否则每次注册都会触发发送的粘性消息
//        EventBusUtils.removeStickyEvent(event.javaClass)
    }
}