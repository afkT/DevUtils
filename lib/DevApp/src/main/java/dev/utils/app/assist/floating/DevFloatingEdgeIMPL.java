package dev.utils.app.assist.floating;

import android.graphics.Point;
import android.view.View;

import dev.utils.app.BarUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: DevApp 悬浮窗边缘检测辅助类实现
 * @author Ttt
 */
public class DevFloatingEdgeIMPL
        implements IFloatingEdge {

    @Override
    public Point calculateEdge(
            View view,
            int x,
            int y
    ) {
        if (x <= 0) x = 0;
        if (y <= 0) y = 0;

        // 宽度 ( X 轴 ) 计算
        int viewWidth   = ViewUtils.getWidth(view);
        int screenWidth = ScreenUtils.getScreenWidth();

        // 高度 ( Y 轴 ) 计算
        int viewHeight      = ViewUtils.getHeight(view);
        int screenHeight    = ScreenUtils.getScreenHeight();
        int statusBarHeight = BarUtils.getStatusBarHeight2();

        int diffWidth  = screenWidth - viewWidth;
        int diffHeight = screenHeight - statusBarHeight - viewHeight;

        if (x >= diffWidth) x = diffWidth;
        if (y >= diffHeight) y = diffHeight;

        return new Point(x, y);
    }
}