package ktx.dev.other

import org.greenrobot.eventbus.EventBus

/**
 * detail: EventBus 工具类
 * @author Ttt
 * eventbus: 注册后才能接收到该事件
 * postSticky: 事件消费者在事件发布之后才注册也能接收到该事件
 */
object EventBusUtils {

    /**
     * 注册 EventBus
     * @param subscriber 订阅者
     */
    fun register(subscriber: Any?) {
        val eventBus = EventBus.getDefault()
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber)
        }
    }

    /**
     * 解绑 EventBus
     * @param subscriber 订阅者
     */
    fun unregister(subscriber: Any?) {
        val eventBus = EventBus.getDefault()
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber)
        }
    }

    // =========
    // = Event =
    // =========

    /**
     * 发送事件消息
     * @param event Event
     */
    fun post(event: Any?) {
        EventBus.getDefault().post(event)
    }

    /**
     * 取消事件传送
     * @param event Event
     */
    fun cancelEventDelivery(event: Any?) {
        EventBus.getDefault().cancelEventDelivery(event)
    }

    // =

    /**
     * 发送粘性事件消息
     * @param event Event
     */
    fun postSticky(event: Any?) {
        EventBus.getDefault().postSticky(event)
    }

    /**
     * 移除指定的粘性订阅事件
     * @param eventType Event Type
     */
    fun <T> removeStickyEvent(eventType: Class<T>?) {
        val stickyEvent = EventBus.getDefault().getStickyEvent(eventType)
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent)
        }
    }

    /**
     * 移除所有的粘性订阅事件
     */
    fun removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents()
    }
}