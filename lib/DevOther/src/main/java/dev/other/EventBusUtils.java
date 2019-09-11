package dev.other;

import org.greenrobot.eventbus.EventBus;

/**
 * detail: EventBus 工具类
 * @author Ttt
 * <pre>
 *     eventbus: 注册后才能接收到该事件
 *     poststicky: 事件消费者在事件发布之后才注册也能接收到该事件
 * </pre>
 */
public final class EventBusUtils {

    private EventBusUtils() {
    }

    /**
     * 注册 EventBus
     * @param subscriber 订阅者
     */
    public static void register(final Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    /**
     * 解绑 EventBus
     * @param subscriber 订阅者
     */
    public static void unregister(final Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    // =========
    // = Event =
    // =========

    /**
     * 发送事件消息
     * @param event Event
     */
    public static void post(final Object event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 取消事件传送
     * @param event Event
     */
    public static void cancelEventDelivery(final Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    // =

    /**
     * 发送粘性事件消息
     * @param event Event
     */
    public static void postSticky(final Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 移除指定的粘性订阅事件
     * @param eventType Event Type
     * @param <T>       泛型
     */
    public static <T> void removeStickyEvent(final Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    /**
     * 移除所有的粘性订阅事件
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }
}