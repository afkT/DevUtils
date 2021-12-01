package dev.utils.app.assist.floating;

/**
 * detail: 悬浮窗操作辅助类接口
 * @author Ttt
 * <pre>
 *     {@link FloatingWindowManagerAssist2} 所需接口方法
 * </pre>
 */
public interface IFloatingOperate {

    /**
     * 移除悬浮窗 View
     * @param floatingActivity 悬浮窗辅助类接口
     * @return {@code true} success, {@code false} fail
     */
    boolean removeFloatingView(IFloatingActivity floatingActivity);

    /**
     * 添加悬浮窗 View
     * @param floatingActivity 悬浮窗辅助类接口
     * @return {@code true} success, {@code false} fail
     */
    boolean addFloatingView(IFloatingActivity floatingActivity);

    /**
     * 移除所有悬浮窗 View
     */
    void removeAllFloatingView();

    /**
     * 是否处理悬浮 View 添加操作
     * @return {@code true} yes, {@code false} no
     */
    boolean isNeedsAdd();

    /**
     * 设置是否处理悬浮 View 添加操作
     * @param needsAdd {@code true} yes, {@code false} no
     */
    void setNeedsAdd(boolean needsAdd);
}