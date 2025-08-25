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
public class FloatingWindowManagerAssist2
        implements IFloatingOperate {

    // 日志 TAG
    private final String            TAG       = FloatingWindowManagerAssist2.class.getSimpleName();
    // 悬浮窗 View Map
    private final Map<String, View> mViewMaps = new HashMap<>();
    // 是否处理悬浮 View 添加操作
    private       boolean           mNeedsAdd = true;

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 移除悬浮窗 View
     * @param floatingActivity 悬浮窗辅助类接口
     * @return {@code true} success, {@code false} fail
     */
    @Override
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
     * @param floatingActivity 悬浮窗辅助类接口
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addFloatingView(final IFloatingActivity floatingActivity) {
        if (!mNeedsAdd) return false;
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
            } else {
                // 更新悬浮窗 View Layout
                updateViewLayout(floatingActivity, view);
            }
        }
        return false;
    }

    /**
     * 移除所有悬浮窗 View
     */
    @Override
    public void removeAllFloatingView() {
        List<View> views = new ArrayList<>(mViewMaps.values());
        mViewMaps.clear();
        Iterator<View> iterator = views.iterator();
        while (iterator.hasNext()) {
            ViewUtils.removeSelfFromParent(iterator.next());
            iterator.remove();
        }
    }

    /**
     * 更新悬浮窗 View Layout
     * <pre>
     *     如需更新 View 坐标
     *     则重写该方法, 搭配 {@link DevFloatingTouchImpl2} 使用
     * </pre>
     * @param floatingActivity 悬浮窗辅助类接口
     * @param view             {@link View}
     */
    @Override
    public void updateViewLayout(
            IFloatingActivity floatingActivity,
            View view
    ) {
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 是否处理悬浮 View 添加操作
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isNeedsAdd() {
        return mNeedsAdd;
    }

    /**
     * 设置是否处理悬浮 View 添加操作
     * @param needsAdd {@code true} yes, {@code false} no
     */
    @Override
    public void setNeedsAdd(final boolean needsAdd) {
        this.mNeedsAdd = needsAdd;
    }

    // ==========
    // = 内部方法 =
    // ==========

    // =====================
    // = IFloatingActivity =
    // =====================

    /**
     * 获取悬浮窗依附的 Activity
     * @param floatingActivity 悬浮窗辅助类接口
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
     * @param floatingActivity 悬浮窗辅助类接口
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
     * @param floatingActivity 悬浮窗辅助类接口
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
     * @param floatingActivity 悬浮窗辅助类接口
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