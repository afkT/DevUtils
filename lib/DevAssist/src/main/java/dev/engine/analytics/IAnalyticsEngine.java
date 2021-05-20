package dev.engine.analytics;

/**
 * detail: Analytics Engine 接口
 * @author Ttt
 */
public interface IAnalyticsEngine<Item extends IAnalyticsEngine.EngineItem> {

    /**
     * detail: Analytics Item
     * @author Ttt
     */
    class EngineItem {
    }

    /**
     * 数据统计 ( 埋点 ) 方法
     * @param item {@link EngineItem}
     * @return {@code true} success, {@code false} fail
     */
    boolean track(Item item);
}