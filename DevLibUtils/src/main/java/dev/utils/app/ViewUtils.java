package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: View 操作相关工具类
 * @author Ttt
 */
public final class ViewUtils {

    private ViewUtils() {
    }

    // 日志 TAG
    private static final String TAG = ViewUtils.class.getSimpleName();

    /**
     * 获取 Context
     * @param view {@link View}
     * @return {@link Context}
     */
    public static Context getContext(final View view) {
        if (view != null) {
            return view.getContext();
        }
        return null;
    }

    /**
     * 获取 View context 所属的 Activity
     * @param view {@link View}
     * @return {@link Activity}
     */
    public static Activity getActivity(final View view) {
        if (view != null) {
            try {
                Context context = view.getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof Activity) {
                        return (Activity) context;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getActivity");
            }
        }
        return null;
    }

    // =

    /**
     * 获取 View
     * @param resource R.layout.id
     * @return {@link View}
     */
    public static View inflate(@LayoutRes final int resource) {
        return inflate(resource, null);
    }

    /**
     * 获取 View
     * @param resource R.layout.id
     * @param root     {@link ViewGroup}
     * @return {@link View}
     */
    public static View inflate(@LayoutRes final int resource, final ViewGroup root) {
        try {
            return ((LayoutInflater) DevUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource, root);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "inflate");
        }
        return null;
    }

    // ======================
    // = 初始化 View 操作等 =
    // ======================

    /**
     * 初始化 View
     * @param view {@link View}
     * @param id   R.id.viewId
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T findViewById(final View view, final int id) {
        if (view != null) {
            try {
                return view.findViewById(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "findViewById");
            }
        }
        return null;
    }

    /**
     * 初始化 View
     * @param window {@link Window}
     * @param id     R.id.viewId
     * @param <T>    泛型
     * @return {@link View}
     */
    public static <T extends View> T findViewById(final Window window, final int id) {
        if (window != null) {
            try {
                return window.findViewById(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "findViewById");
            }
        }
        return null;
    }

    /**
     * 初始化 View
     * @param activity {@link Activity}
     * @param id       R.id.viewId
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends View> T findViewById(final Activity activity, final int id) {
        if (activity != null) {
            try {
                return activity.findViewById(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "findViewById");
            }
        }
        return null;
    }

    /**
     * 转换 View
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T convertView(final View view) {
        try {
            return (T) view;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "convertView");
        }
        return null;
    }

    // =============
    // = View 判空 =
    // =============

    /**
     * 判断 View 是否为 null
     * @param view {@link View}
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final View view) {
        return view == null;
    }

    /**
     * 判断 View 是否为 null
     * @param views View[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view == null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 判断 View 是否不为 null
     * @param view {@link View}
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final View view) {
        return view != null;
    }

    /**
     * 判断 View 是否不为 null
     * @param views View[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view == null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views View[]
     * @return {@code true} 可获取, {@code false} 不可获取
     */
    public static boolean setFocusable(final boolean focusable, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setFocusable(focusable);
                }
            }
        }
        return focusable;
    }

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @param views View[]
     * @return {@code true} 选中, {@code false} 非选中
     */
    public static boolean setSelected(final boolean selected, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setSelected(selected);
                }
            }
        }
        return selected;
    }

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @param views View[]
     * @return {@code true} 启用, {@code false} 禁用
     */
    public static boolean setEnabled(final boolean enabled, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setEnabled(enabled);
                }
            }
        }
        return enabled;
    }

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @param views View[]
     * @return {@code true} 可点击, {@code false} 不可点击
     */
    public static boolean setClickable(final boolean clickable, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setClickable(clickable);
                }
            }
        }
        return clickable;
    }

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @param views View[]
     * @return {@code true} 可长按, {@code false} 不可长按
     */
    public static boolean setLongClickable(final boolean longClickable, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setLongClickable(longClickable);
                }
            }
        }
        return longClickable;
    }

    // =================
    // = View 显示状态 =
    // =================

    /**
     * 判断 View 是否显示
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibility(final View view) {
        return isVisibility(view, true);
    }

    /**
     * 判断 View 是否显示
     * @param view         {@link View}
     * @param defaultValue view 为 null 默认值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibility(final View view, final boolean defaultValue) {
        if (view != null) {
            return (view.getVisibility() == View.VISIBLE);
        }
        return defaultValue;
    }

    /**
     * 判断 View 是否都显示
     * @param views View[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilitys(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view == null || view.getVisibility() != View.VISIBLE) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 判断 View 是否隐藏占位
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilityIN(final View view) {
        return isVisibilityIN(view, false);
    }

    /**
     * 判断 View 是否隐藏占位
     * @param view         {@link View}
     * @param defaultValue view 为 null 默认值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilityIN(final View view, final boolean defaultValue) {
        if (view != null) {
            return (view.getVisibility() == View.INVISIBLE);
        }
        return defaultValue;
    }

    /**
     * 判断 View 是否都隐藏占位
     * @param views View[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilityINs(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view == null || view.getVisibility() != View.INVISIBLE) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 判断 View 是否隐藏
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilityGone(final View view) {
        return isVisibilityGone(view, false);
    }

    /**
     * 判断 View 是否隐藏
     * @param view         {@link View}
     * @param defaultValue view 为 null 默认值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilityGone(final View view, final boolean defaultValue) {
        if (view != null) {
            return (view.getVisibility() == View.GONE);
        }
        return defaultValue;
    }

    /**
     * 判断 View 是否都隐藏
     * @param views View[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVisibilityGones(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view == null || view.getVisibility() != View.GONE) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取显示的状态 (View.VISIBLE : View.GONE)
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @return 显示的状态 {@link View#VISIBLE}、{@link View#GONE}
     */
    public static int getVisibility(final boolean isVisibility) {
        return isVisibility ? View.VISIBLE : View.GONE;
    }

    /**
     * 获取显示的状态 (View.VISIBLE : View.INVISIBLE)
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @return 显示的状态 {@link View#VISIBLE}、{@link View#INVISIBLE}
     */
    public static int getVisibilityIN(final boolean isVisibility) {
        return isVisibility ? View.VISIBLE : View.INVISIBLE;
    }

    // =

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @return isVisibility
     */
    public static boolean setVisibility(final boolean isVisibility, final View view) {
        if (view != null) {
            view.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
        return isVisibility;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibility(final int isVisibility, final View view) {
        if (view != null) {
            view.setVisibility(isVisibility);
        }
        return (isVisibility == View.VISIBLE);
    }

    // =

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibilitys(final boolean isVisibility, final View... views) {
        return setVisibilitys(getVisibility(isVisibility), views);
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibilitys(final int isVisibility, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setVisibility(isVisibility);
                }
            }
        }
        return (isVisibility == View.VISIBLE);
    }

    // =

    /**
     * 切换 View 显示的状态
     * @param view  {@link View}
     * @param views View[]
     */
    public static void toggleVisibilitys(final View view, final View... views) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        setVisibilitys(View.GONE, views);
    }

    /**
     * 切换 View 显示的状态
     * @param viewArys View[]
     * @param views    View[]
     */
    public static void toggleVisibilitys(final View[] viewArys, final View... views) {
        toggleVisibilitys(View.GONE, viewArys, views);
    }

    /**
     * 切换 View 显示的状态
     * @param status   {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArys View[]
     * @param views    View[]
     */
    public static void toggleVisibilitys(final int status, final View[] viewArys, final View... views) {
        // 默认显示
        setVisibilitys(View.VISIBLE, viewArys);
        // 根据状态处理
        setVisibilitys(status, views);
    }

    // =

    /**
     * 反转 View 显示的状态
     * @param status   {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArys View[]
     * @param views    View[]
     */
    public static void reverseVisibilitys(final int status, final View[] viewArys, final View... views) {
        reverseVisibilitys(status == View.VISIBLE, viewArys, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArys     View[]
     * @param views        View[]
     */
    public static void reverseVisibilitys(final boolean isVisibility, final View[] viewArys, final View... views) {
        // 默认处理第一个数组
        setVisibilitys(isVisibility, viewArys);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
    }

    /**
     * 反转 View 显示的状态
     * @param status {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view   {@link View}
     * @param views  View[]
     */
    public static void reverseVisibilitys(final int status, final View view, final View... views) {
        reverseVisibilitys(status == View.VISIBLE, view, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     */
    public static void reverseVisibilitys(final boolean isVisibility, final View view, final View... views) {
        // 默认处理第一个 View
        setVisibilitys(isVisibility, view);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
    }

    // =

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return isChange
     */
    public static boolean toggleView(final boolean isChange, final int isVisibility, final View view) {
        if (isChange && view != null) {
            view.setVisibility(isVisibility);
        }
        return isChange;
    }

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return isChange
     */
    public static boolean toggleViews(final boolean isChange, final int isVisibility, final View... views) {
        if (isChange && views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setVisibility(isVisibility);
                }
            }
        }
        return isChange;
    }

    // =

    /**
     * 把自身从父 View 中移除
     * @param view {@link View}
     */
    public static void removeSelfFromParent(final View view) {
        if (view != null) {
            try {
                ViewParent parent = view.getParent();
                if (parent != null && parent instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) parent;
                    group.removeView(view);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeSelfFromParent");
            }
        }
    }

    /**
     * 判断触点是否落在该 View 上
     * @param ev   {@link MotionEvent}
     * @param view 待判断 {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isTouchInView(final MotionEvent ev, final View view) {
        if (ev != null && view != null) {
            int[] locations = new int[2];
            view.getLocationOnScreen(locations);
            float motionX = ev.getRawX();
            float motionY = ev.getRawY();
            return motionX >= locations[0] && motionX <= (locations[0] + view.getWidth())
                    && motionY >= locations[1] && motionY <= (locations[1] + view.getHeight());
        }
        return false;
    }

    /**
     * View 请求更新
     * @param view      {@link View}
     * @param allParent 是否全部父布局 View 都请求
     */
    public static void requestLayoutParent(final View view, final boolean allParent) {
        if (view != null) {
            ViewParent parent = view.getParent();
            while (parent != null && parent instanceof View) {
                if (!parent.isLayoutRequested()) {
                    parent.requestLayout();
                    if (!allParent) {
                        break;
                    }
                }
                parent = parent.getParent();
            }
        }
    }

    /**
     * 测量 View
     * @param view {@link View}
     * @return int[] 0 = 宽度, 1 = 高度
     */
    public static int[] measureView(final View view) {
        if (view != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
                int height = layoutParams.height;
                int heightSpec;
                if (height > 0) {
                    heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                } else {
                    heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                }
                view.measure(widthSpec, heightSpec);
                return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "measureView");
            }
        }
        return new int[]{0, 0};
    }

    /**
     * 获取 View 的宽度
     * @param view {@link View}
     * @return View 的宽度
     */
    public static int getMeasuredWidth(final View view) {
        if (view != null) {
            measureView(view);
            return view.getMeasuredWidth();
        }
        return 0;
    }

    /**
     * 获取 View 的高度
     * @param view {@link View}
     * @return View 的高度
     */
    public static int getMeasuredHeight(final View view) {
        if (view != null) {
            measureView(view);
            return view.getMeasuredHeight();
        }
        return 0;
    }

    // ===============
    // = View Margin =
    // ===============

    /**
     * 获取 View Margin
     * @param view {@link View}
     * @return new int[] {left, top right, bottom}
     */
    public static int[] getMargin(final View view) {
        int[] margins = new int[]{0, 0, 0, 0};
        if (view != null) {
            // 判断是否属于 ViewGroup.MarginLayoutParams
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                try {
                    ViewGroup.MarginLayoutParams layoutParams = ((ViewGroup.MarginLayoutParams) view.getLayoutParams());
                    margins[0] = layoutParams.leftMargin;
                    margins[1] = layoutParams.topMargin;
                    margins[2] = layoutParams.rightMargin;
                    margins[3] = layoutParams.bottomMargin;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getMargin");
                }
            }
        }
        return margins;
    }

    // =

    /**
     * 设置 Margin 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(final View view, final int leftRight, final int topBottom) {
        return setMargin(view, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param margin Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(final View view, final int margin) {
        return setMargin(view, margin, margin, margin, margin);
    }

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(final View view, final int left, final int top, final int right, final int bottom) {
        if (view != null) {
            // 判断是否属于 ViewGroup.MarginLayoutParams
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                try {
                    // 设置边距
                    ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(left, top, right, bottom);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setMargin");
                }
            }
        }
        return false;
    }

    // =

    /**
     * 设置 Margin 边距
     * @param views     View[]
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     */
    public static void setMargin(final View[] views, final int leftRight, final int topBottom) {
        setMargin(views, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param margin Margin
     */
    public static void setMargin(final View[] views, final int margin) {
        setMargin(views, margin, margin, margin, margin);
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     */
    public static void setMargin(final View[] views, final int left, final int top, final int right, final int bottom) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                setMargin(views[i], left, top, right, bottom);
            }
        }
    }

    // ================
    // = View Padding =
    // ================

    /**
     * 获取 View Padding
     * @param view {@link View}
     * @return new int[] {left, top right, bottom}
     */
    public static int[] getPadding(final View view) {
        int[] paddings = new int[]{0, 0, 0, 0};
        if (view != null) {
            paddings[0] = view.getPaddingLeft();
            paddings[1] = view.getPaddingTop();
            paddings[2] = view.getPaddingRight();
            paddings[3] = view.getPaddingBottom();
        }
        return paddings;
    }

    // =

    /**
     * 设置 Padding 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(final View view, final int leftRight, final int topBottom) {
        return setPadding(view, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Padding 边距
     * @param view    {@link View}
     * @param padding Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(final View view, final int padding) {
        return setPadding(view, padding, padding, padding, padding);
    }

    /**
     * 设置 Padding 边距
     * @param view   {@link View}
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(final View view, final int left, final int top, final int right, final int bottom) {
        if (view != null) {
            try {
                view.setPadding(left, top, right, bottom);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setPadding");
            }
        }
        return false;
    }

    // =

    /**
     * 设置 Padding 边距
     * @param views     View[]
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     */
    public static void setPadding(final View[] views, final int leftRight, final int topBottom) {
        setPadding(views, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Padding 边距
     * @param views   View[]
     * @param padding Padding
     */
    public static void setPadding(final View[] views, final int padding) {
        setPadding(views, padding, padding, padding, padding);
    }

    /**
     * 设置 Padding 边距
     * @param views  View[]
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     */
    public static void setPadding(final View[] views, final int left, final int top, final int right, final int bottom) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                setPadding(views[i], left, top, right, bottom);
            }
        }
    }

    // ============
    // = ListView =
    // ============

    /**
     * 计算 ListView Item 高度
     * @param listView {@link ListView}
     * @return ListView Item 高度
     */
    public static int calcListViewItemHeight(final ListView listView) {
        return calcListViewItemHeight(listView, false);
    }

    /**
     * 计算 ListView Item 高度
     * @param listView  {@link ListView}
     * @param setParams 是否 setLayoutParams
     * @return ListView Item 高度
     */
    public static int calcListViewItemHeight(final ListView listView, final boolean setParams) {
        // 获取 Adapter
        ListAdapter listAdapter = listView.getAdapter();
        // 防止为 null
        if (listAdapter == null) {
            return 0;
        }
        // 获取总体高度
        int totalHeight = 0;
        // 遍历累加
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // 累加 Item 高度
            totalHeight += getItemHeighet(listAdapter, listView, i);
        }
        // =
        // 获取子项间分隔符占用的高度
        // listView.getDividerHeight();
        // 累加分割线高度
        totalHeight += (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // 判断是否需要设置高度
        if (setParams) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight;
            listView.setLayoutParams(params);
        }
        // 返回总体高度
        return totalHeight;
    }

    // ============
    // = GridView =
    // ============

    /**
     * 计算 GridView Item 高度
     * @param gridView   {@link GridView}
     * @param numColumns GridView 行数
     * @return GridView Item 高度
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int calcGridViewItemHeight(final GridView gridView, final int numColumns) {
        return calcGridViewItemHeight(gridView, numColumns, false);
    }

    /**
     * 计算 GridView Item 高度
     * @param gridView   {@link GridView}
     * @param numColumns GridView 行数
     * @param setParams  是否 setLayoutParams
     * @return GridView Item 高度
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int calcGridViewItemHeight(final GridView gridView, final int numColumns, final boolean setParams) {
        // 获取 Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        // 防止为 null
        if (listAdapter == null) {
            return 0;
        }
        // 最高高度
        int singleMax = 0;
        // 获取总体高度
        int totalHeight = 0;
        // 获取总数
        int count = listAdapter.getCount();
        // 判断是否整数
        count = (count % numColumns == 0) ? (count / numColumns) : (count / numColumns + 1);
        // 遍历累加
        for (int i = 0; i < count; i++) {
            // 默认表示第一个的高度
            singleMax = getItemHeighet(listAdapter, gridView, i * numColumns);
            // 遍历判断
            for (int eqI = 1; eqI < numColumns; eqI++) {
                // 临时高度
                int tempHeight = 0;
                // 进行判断处理
                if (i * numColumns + eqI <= count) {
                    // 获取对应的高度
                    tempHeight = getItemHeighet(listAdapter, gridView, i * numColumns + eqI);
                }
                // 判断是否在最大高度
                if (tempHeight > singleMax) {
                    singleMax = tempHeight;
                }
            }
            // 累加 Item 高度
            totalHeight += singleMax;
        }
        // 每列之间的间隔 |
        // int hSpaec = gridView.getHorizontalSpacing();
        // 每行之间的间隔 -
        int vSpace = gridView.getVerticalSpacing();
        // 最后获取整个 GridView 完整显示需要的高度
        totalHeight += (vSpace * (count - 1));
        // 判断是否需要设置高度
        if (setParams) {
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = totalHeight;
            gridView.setLayoutParams(params);
        }
        // 返回总体高度
        return totalHeight;
    }

    // =

    /**
     * 获取单独一个 Item 高度
     * @param absViews {@link AbsListView}
     * @param pos      索引
     * @return Item 高度
     */
    public static int getItemHeighet(final AbsListView absViews, final int pos) {
        return getItemHeighet(absViews, pos, 0);
    }

    /**
     * 获取单独一个 Item 高度
     * @param absViews      {@link AbsListView}
     * @param pos           索引
     * @param defaultHeight 默认高度
     * @return Item 高度
     */
    public static int getItemHeighet(final AbsListView absViews, final int pos, final int defaultHeight) {
        if (absViews != null) {
            return getItemHeighet(absViews.getAdapter(), absViews, pos, defaultHeight);
        }
        return defaultHeight;
    }

    /**
     * 获取单独一个 Item 高度
     * @param listAdapter {@link ListAdapter}
     * @param absViews    {@link AbsListView}
     * @param pos         索引
     * @return Item 高度
     */
    public static int getItemHeighet(final ListAdapter listAdapter, final AbsListView absViews, final int pos) {
        return getItemHeighet(listAdapter, absViews, pos, 0);
    }

    /**
     * 获取单独一个 Item 高度
     * @param listAdapter   {@link ListAdapter}
     * @param absViews      {@link AbsListView}
     * @param pos           索引
     * @param defaultHeight 默认高度
     * @return Item 高度
     */
    public static int getItemHeighet(final ListAdapter listAdapter, final AbsListView absViews, final int pos, final int defaultHeight) {
        try {
            // listAdapter.getCount() 返回数据项的数目
            View listItem = listAdapter.getView(pos, null, absViews);
            // 计算子项 View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            return listItem.getMeasuredHeight();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getItemHeighet");
            return defaultHeight;
        }
    }
}
