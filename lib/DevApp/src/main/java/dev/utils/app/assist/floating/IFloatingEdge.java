package dev.utils.app.assist.floating;

import android.graphics.Point;
import android.view.View;

/**
 * detail: 悬浮窗边缘检测接口
 * @author Ttt
 * <pre>
 *     {@link FloatingWindowManagerAssist2}
 *     {@link DevFloatingTouchIMPL2}
 *     悬浮窗触摸使用
 * </pre>
 */
public interface IFloatingEdge {

    /**
     * 计算悬浮窗边缘检测坐标
     * @param view 待计算 View
     * @param x    待变更 X 轴坐标
     * @param y    待变更 Y 轴坐标
     * @return 计算处理后的坐标值
     */
    Point calculateEdge(
            View view,
            int x,
            int y
    );
}