package dev.utils.app.assist.floating;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dev.utils.LogPrintUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: 悬浮窗管理辅助类 ( 无需权限依赖 Activity )
 * @author Ttt
 * <pre>
 *     添加到 Activity content View 中
 * </pre>
 */
public final class FloatingWindowManagerAssist2 {

    // 日志 TAG
    private final String            TAG       = FloatingWindowManagerAssist2.class.getSimpleName();
    // 悬浮窗 View Map
    private final Map<String, View> mViewMaps = new HashMap<>();
    // 是否处理悬浮 View 添加操作
    private       boolean           needsAdd  = true;

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 移除悬浮窗 View
     * @param floatingActivity 悬浮窗触摸辅助类接口
     * @return {@code true} success, {@code false} fail
     */
    public boolean removeFloatingView(final IFloatingActivity floatingActivity) {
        String key = getMapFloatingKey(floatingActivity);
        if (key != null) {
            View view = mViewMaps.remove(key);
            ViewUtils.removeSelfFromParent(view);
            return true;
        }
        return false;
    }

    /**
     * 添加悬浮窗 View
     * @param floatingActivity 悬浮窗触摸辅助类接口
     * @return {@code true} success, {@code false} fail
     */
    public boolean addFloatingView(final IFloatingActivity floatingActivity) {
        if (!needsAdd) return false;
        String key = getMapFloatingKey(floatingActivity);
        if (key != null) {
            View view = mViewMaps.get(key);
            if (view == null) {
                Activity activity    = getAttachActivity(floatingActivity);
                View     contentView = ViewUtils.getContentView(activity);
                if (contentView instanceof ViewGroup) {
                    View                   floatingView = getMapFloatingView(floatingActivity);
                    ViewGroup.LayoutParams params       = getMapFloatingViewLayoutParams(floatingActivity);
                    if (floatingView != null && params != null) {
                        // 添加 View
                        ViewUtils.addView((ViewGroup) contentView, floatingView, params);
                        // 存在父布局表示添加成功
                        if (floatingView.getParent() != null) {
                            mViewMaps.put(key, floatingView);
                            return true;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 移除所有悬浮窗 View
     * @return {@link FloatingWindowManagerAssist2}
     */
    public FloatingWindowManagerAssist2 removeAllFloatingView() {
        List<View> views = new ArrayList<>(mViewMaps.values());
        mViewMaps.clear();
        Iterator<View> iterator = views.iterator();
        while (iterator.hasNext()) {
            ViewUtils.removeSelfFromParent(iterator.next());
            iterator.remove();
        }
        return this;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 是否处理悬浮 View 添加操作
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNeedsAdd() {
        return needsAdd;
    }

    /**
     * 设置是否处理悬浮 View 添加操作
     * @param needsAdd {@code true} yes, {@code false} no
     * @return {@link FloatingWindowManagerAssist2}
     */
    public FloatingWindowManagerAssist2 setNeedsAdd(final boolean needsAdd) {
        this.needsAdd = needsAdd;
        return this;
    }

    // ==========
    // = 内部方法 =
    // ==========

    // =====================
    // = IFloatingActivity =
    // =====================

    /**
     * 获取悬浮窗依附的 Activity
     * @param floatingActivity 悬浮窗触摸辅助类接口
     * @return {@link Activity}
     */
    private Activity getAttachActivity(final IFloatingActivity floatingActivity) {
        if (floatingActivity != null) {
            try {
                return floatingActivity.getAttachActivity();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAttachActivity");
            }
        }
        return null;
    }

    /**
     * 获取悬浮窗 Map Key
     * @param floatingActivity 悬浮窗触摸辅助类接口
     * @return 悬浮窗 Map Key
     */
    private String getMapFloatingKey(final IFloatingActivity floatingActivity) {
        if (floatingActivity != null) {
            try {
                return floatingActivity.getMapFloatingKey();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMapFloatingKey");
            }
        }
        return null;
    }

    /**
     * 获取悬浮窗 Map Value View
     * @param floatingActivity 悬浮窗触摸辅助类接口
     * @return 悬浮窗 Map Value View
     */
    private View getMapFloatingView(final IFloatingActivity floatingActivity) {
        if (floatingActivity != null) {
            try {
                return floatingActivity.getMapFloatingView();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMapFloatingView");
            }
        }
        return null;
    }

    /**
     * 获取悬浮窗 View LayoutParams
     * @param floatingActivity 悬浮窗触摸辅助类接口
     * @return 悬浮窗 View LayoutParams
     */
    private ViewGroup.LayoutParams getMapFloatingViewLayoutParams(final IFloatingActivity floatingActivity) {
        if (floatingActivity != null) {
            try {
                return floatingActivity.getMapFloatingViewLayoutParams();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMapFloatingViewLayoutParams");
            }
        }
        return null;
    }
}