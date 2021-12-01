package dev.utils.app.assist.floating;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * detail: 悬浮窗触摸辅助类接口
 * @author Ttt
 * <pre>
 *     {@link FloatingWindowManagerAssist2} 所需接口方法
 * </pre>
 */
public interface IFloatingActivity {

    /**
     * 获取悬浮窗依附的 Activity
     * @return {@link Activity}
     */
    Activity getAttachActivity();

    /**
     * 获取悬浮窗 Map Key
     * @return 悬浮窗 Map Key
     */
    String getMapFloatingKey();

    /**
     * 获取悬浮窗 Map Value View
     * @return 悬浮窗 Map Value View
     */
    View getMapFloatingView();

    /**
     * 获取悬浮窗 View LayoutParams
     * @return 悬浮窗 View LayoutParams
     */
    ViewGroup.LayoutParams getMapFloatingViewLayoutParams();
}