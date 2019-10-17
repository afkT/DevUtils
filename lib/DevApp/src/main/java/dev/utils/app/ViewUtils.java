package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: View 操作相关工具类
 * @author Ttt
 * <pre>
 *     组件设置 setCompoundDrawables 不生效解决办法
 *     @see <a href="https://blog.csdn.net/qq_26971803/article/details/54347598"/>
 * </pre>
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
        return inflate(resource, null, false);
    }

    /**
     * 获取 View
     * @param resource R.layout.id
     * @param root     {@link ViewGroup}
     * @return {@link View}
     */
    public static View inflate(@LayoutRes final int resource, final ViewGroup root) {
        return inflate(resource, root, root != null);
    }

    /**
     * 获取 View
     * @param resource     R.layout.id
     * @param root         {@link ViewGroup}
     * @param attachToRoot 是否添加到 root 上
     * @return {@link View}
     */
    public static View inflate(@LayoutRes final int resource, final ViewGroup root, final boolean attachToRoot) {
        try {
            return LayoutInflater.from(DevUtils.getContext()).inflate(resource, root, attachToRoot);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "inflate");
        }
        return null;
    }

    // =

    /**
     * 获取指定 View 父布局
     * @param view {@link View}
     * @param <T>  泛型
     * @return View
     */
    public static <T extends View> T getParent(final View view) {
        if (view != null) {
            try {
                return (T) view.getParent();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getParent");
            }
        }
        return null;
    }

    // =

    /**
     * 获取指定索引 View
     * @param viewGroup {@link ViewGroup}
     * @param <T>       泛型
     * @return View
     */
    public static <T extends View> T getChildAt(final ViewGroup viewGroup) {
        return getChildAt(viewGroup, 0);
    }

    /**
     * 获取指定索引 View
     * @param viewGroup {@link ViewGroup}
     * @param index     索引
     * @param <T>       泛型
     * @return View
     */
    public static <T extends View> T getChildAt(final ViewGroup viewGroup, final int index) {
        if (viewGroup != null && index >= 0) {
            try {
                return (T) viewGroup.getChildAt(index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getChildAt");
            }
        }
        return null;
    }

    /**
     * 移除全部子 View
     * @param viewGroup {@link ViewGroup}
     * @param <T>       泛型
     * @return 传入 View
     */
    public static <T extends ViewGroup> T removeAllViews(final T viewGroup) {
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        return viewGroup;
    }

    // =

    /**
     * 获取 LayoutParams
     * @param view {@link View}
     * @param <T>  泛型
     * @return LayoutParams
     */
    public static <T> T getLayoutParams(final View view) {
        if (view != null) {
            try {
                return (T) view.getLayoutParams();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getLayoutParams");
            }
        }
        return null;
    }

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLayoutParams(final View view, final ViewGroup.LayoutParams params) {
        if (view != null) {
            try {
                view.setLayoutParams(params);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setLayoutParams");
            }
        }
        return false;
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
    public static <T extends View> T findViewById(final View view, @IdRes final int id) {
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
    public static <T extends View> T findViewById(final Window window, @IdRes final int id) {
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
    public static <T extends View> T findViewById(final Activity activity, @IdRes final int id) {
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
     * 获取 View 最小高度
     * @param view View
     * @return View 最小高度
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getMinimumHeight(final View view) {
        if (view != null) {
            return view.getMinimumHeight();
        }
        return -1;
    }

    /**
     * 设置 View 最小高度
     * @param view      View
     * @param minHeight 最小高度
     * @return View
     */
    public static View setMinimumHeight(final View view, final int minHeight) {
        if (view != null) {
            view.setMinimumHeight(minHeight);
        }
        return view;
    }

    /**
     * 获取 View 最小宽度
     * @param view View
     * @return View 最小宽度
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getMinimumWidth(final View view) {
        if (view != null) {
            return view.getMinimumWidth();
        }
        return -1;
    }

    /**
     * 设置 View 最小宽度
     * @param view     View
     * @param minWidth 最小宽度
     * @return View
     */
    public static View setMinimumWidth(final View view, final int minWidth) {
        if (view != null) {
            view.setMinimumWidth(minWidth);
        }
        return view;
    }

    // =

    /**
     * 获取 View 透明度
     * @param view View
     * @return 透明度
     */
    public static float getAlpha(final View view) {
        if (view != null) {
            return view.getAlpha();
        }
        return 1.0f;
    }

    /**
     * 设置 View 透明度
     * @param view  View
     * @param alpha 透明度
     * @return View
     */
    public static View setAlpha(final View view, @FloatRange(from = 0.0, to = 1.0) final float alpha) {
        if (view != null) {
            view.setAlpha(alpha);
        }
        return view;
    }

    /**
     * 获取 View Tag
     * @param view View
     * @return Tag
     */
    public static Object getTag(final View view) {
        if (view != null) {
            return view.getTag();
        }
        return null;
    }

    /**
     * 设置 View Tag
     * @param view   View
     * @param object Tag
     * @return View
     */
    public static View setTag(final View view, final Object object) {
        if (view != null) {
            view.setTag(object);
        }
        return view;
    }

    // =

    /**
     * 设置 View 滚动效应
     * @param view              View
     * @param isScrollContainer 是否需要滚动效应
     * @return View
     */
    public static View setScrollContainer(final View view, final boolean isScrollContainer) {
        if (view != null) {
            view.setScrollContainer(isScrollContainer);
        }
        return view;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param view               View
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return View
     */
    public static View setNextFocusForwardId(final View view, @IdRes final int nextFocusForwardId) {
        if (view != null) {
            view.setNextFocusForwardId(nextFocusForwardId);
        }
        return view;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            View
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return View
     */
    public static View setNextFocusDownId(final View view, @IdRes final int nextFocusDownId) {
        if (view != null) {
            view.setNextFocusDownId(nextFocusDownId);
        }
        return view;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            View
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return View
     */
    public static View setNextFocusLeftId(final View view, @IdRes final int nextFocusLeftId) {
        if (view != null) {
            view.setNextFocusLeftId(nextFocusLeftId);
        }
        return view;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             View
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return View
     */
    public static View setNextFocusRightId(final View view, @IdRes final int nextFocusRightId) {
        if (view != null) {
            view.setNextFocusRightId(nextFocusRightId);
        }
        return view;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          View
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return View
     */
    public static View setNextFocusUpId(final View view, @IdRes final int nextFocusUpId) {
        if (view != null) {
            view.setNextFocusUpId(nextFocusUpId);
        }
        return view;
    }

    /**
     * 设置 View 旋转度数
     * @param view     View
     * @param rotation 旋转度数
     * @return View
     */
    public static View setRotation(final View view, final float rotation) {
        if (view != null) {
            view.setRotation(rotation);
        }
        return view;
    }

    /**
     * 设置 View 水平旋转度数
     * @param view      View
     * @param rotationX 水平旋转度数
     * @return View
     */
    public static View setRotationX(final View view, final float rotationX) {
        if (view != null) {
            view.setRotationX(rotationX);
        }
        return view;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param view      View
     * @param rotationY 竖直旋转度数
     * @return View
     */
    public static View setRotationY(final View view, final float rotationY) {
        if (view != null) {
            view.setRotationY(rotationY);
        }
        return view;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param view   View
     * @param scaleX 水平方向缩放比例
     * @return View
     */
    public static View setScaleX(final View view, final float scaleX) {
        if (view != null) {
            view.setScaleX(scaleX);
        }
        return view;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param view   View
     * @param scaleY 竖直方向缩放比例
     * @return View
     */
    public static View setScaleY(final View view, final float scaleY) {
        if (view != null) {
            view.setScaleY(scaleY);
        }
        return view;
    }

    /**
     * 设置文本的显示方式
     * @param view          View
     * @param textAlignment 文本的显示方式
     * @return View
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View setTextAlignment(final View view, final int textAlignment) {
        if (view != null) {
            view.setTextAlignment(textAlignment);
        }
        return view;
    }

    /**
     * 设置文本的显示方向
     * @param view          View
     * @param textDirection 文本的显示方向
     * @return View
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View setTextDirection(final View view, final int textDirection) {
        if (view != null) {
            view.setTextDirection(textDirection);
        }
        return view;
    }

    /**
     * 设置水平方向偏转量
     * @param view   View
     * @param pivotX 水平方向偏转量
     * @return View
     */
    public static View setPivotX(final View view, final float pivotX) {
        if (view != null) {
            view.setPivotX(pivotX);
        }
        return view;
    }

    /**
     * 设置竖直方向偏转量
     * @param view   View
     * @param pivotY 竖直方向偏转量
     * @return View
     */
    public static View setPivotY(final View view, final float pivotY) {
        if (view != null) {
            view.setPivotY(pivotY);
        }
        return view;
    }

    /**
     * 设置水平方向的移动距离
     * @param view         View
     * @param translationX 水平方向的移动距离
     * @return View
     */
    public static View setTranslationX(final View view, final float translationX) {
        if (view != null) {
            view.setTranslationX(translationX);
        }
        return view;
    }

    /**
     * 设置竖直方向的移动距离
     * @param view         View
     * @param translationY 竖直方向的移动距离
     * @return View
     */
    public static View setTranslationY(final View view, final float translationY) {
        if (view != null) {
            view.setTranslationY(translationY);
        }
        return view;
    }

    /**
     * 设置 View 硬件加速类型
     * @param view      View
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return View
     */
    public static View setLayerType(final View view, final int layerType, final Paint paint) {
        if (view != null) {
            view.setLayerType(layerType, paint);
        }
        return view;
    }

    // =

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
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
     * @param views    View[]
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
     * @param views   View[]
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
     * @param views     View[]
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
     * @param views         View[]
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
     * @return isVisibility
     */
    public static boolean reverseVisibilitys(final int status, final View[] viewArys, final View... views) {
        return reverseVisibilitys(status == View.VISIBLE, viewArys, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArys     View[]
     * @param views        View[]
     * @return isVisibility
     */
    public static boolean reverseVisibilitys(final boolean isVisibility, final View[] viewArys, final View... views) {
        // 默认处理第一个数组
        setVisibilitys(isVisibility, viewArys);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
        return isVisibility;
    }

    /**
     * 反转 View 显示的状态
     * @param status {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view   {@link View}
     * @param views  View[]
     * @return isVisibility
     */
    public static boolean reverseVisibilitys(final int status, final View view, final View... views) {
        return reverseVisibilitys(status == View.VISIBLE, view, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     * @return isVisibility
     */
    public static boolean reverseVisibilitys(final boolean isVisibility, final View view, final View... views) {
        // 默认处理第一个 View
        setVisibilitys(isVisibility, view);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
        return isVisibility;
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
     * @return View
     */
    public static View removeSelfFromParent(final View view) {
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
        return view;
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
     * @return View
     */
    public static View requestLayoutParent(final View view, final boolean allParent) {
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
        return view;
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

    // ==================
    // = Layout Gravity =
    // ==================

    /**
     * 获取 View Layout Gravity
     * @param view {@link View}
     * @return Layout Gravity
     */
    public static int getLayoutGravity(final View view) {
        if (view != null) {
            try {
                if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    return ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity;
                } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                    return ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity;
                } else if (view.getLayoutParams() instanceof ViewPager.LayoutParams) {
                    return ((ViewPager.LayoutParams) view.getLayoutParams()).gravity;
                } else if (view.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                    return ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).gravity;
                } else if (view.getLayoutParams() instanceof DrawerLayout.LayoutParams) {
                    return ((DrawerLayout.LayoutParams) view.getLayoutParams()).gravity;
                } else {
                    // 抛出不支持的类型
                    throw new Exception("layoutParams:" + view.getLayoutParams().toString());
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getLayoutGravity");
            }
        }
        return -1;
    }

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLayoutGravity(final View view, final int gravity) {
        if (view != null) {
            try {
                if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = gravity;
                } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                    ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = gravity;
                } else if (view.getLayoutParams() instanceof ViewPager.LayoutParams) {
                    ((ViewPager.LayoutParams) view.getLayoutParams()).gravity = gravity;
                } else if (view.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                    ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).gravity = gravity;
                } else if (view.getLayoutParams() instanceof DrawerLayout.LayoutParams) {
                    ((DrawerLayout.LayoutParams) view.getLayoutParams()).gravity = gravity;
                } else {
                    // 抛出不支持的类型
                    throw new Exception("layoutParams:" + view.getLayoutParams().toString());
                }
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setLayoutGravity");
            }
        }
        return false;
    }

    // ===============
    // = View Margin =
    // ===============

    /**
     * 获取 View Left Margin
     * @param view {@link View}
     * @return Left Margin
     */
    public static int getMarginLeft(final View view) {
        return getMargin(view)[0];
    }

    /**
     * 获取 View Top Margin
     * @param view {@link View}
     * @return Top Margin
     */
    public static int getMarginTop(final View view) {
        return getMargin(view)[1];
    }

    /**
     * 获取 View Right Margin
     * @param view {@link View}
     * @return Right Margin
     */
    public static int getMarginRight(final View view) {
        return getMargin(view)[2];
    }

    /**
     * 获取 View Bottom Margin
     * @param view {@link View}
     * @return Bottom Margin
     */
    public static int getMarginBottom(final View view) {
        return getMargin(view)[3];
    }

    // =

    /**
     * 获取 View Margin
     * @param view {@link View}
     * @return new int[] {left, top, right, bottom}
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
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginLeft(final View view, final int leftMargin) {
        return setMarginLeft(view, leftMargin, true);
    }

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginLeft(final View view, final int leftMargin, final boolean reset) {
        if (view != null) {
            // 判断是否属于 ViewGroup.MarginLayoutParams
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                if (reset) {
                    try {
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(leftMargin, 0, 0, 0);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginLeft");
                    }
                } else {
                    try {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        // 设置边距
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams())
                                .setMargins(leftMargin, layoutParams.topMargin,
                                        layoutParams.rightMargin, layoutParams.bottomMargin);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginLeft");
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginTop(final View view, final int topMargin) {
        return setMarginTop(view, topMargin, true);
    }

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginTop(final View view, final int topMargin, final boolean reset) {
        if (view != null) {
            // 判断是否属于 ViewGroup.MarginLayoutParams
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                if (reset) {
                    try {
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(0, topMargin, 0, 0);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginTop");
                    }
                } else {
                    try {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        // 设置边距
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams())
                                .setMargins(layoutParams.leftMargin, topMargin,
                                        layoutParams.rightMargin, layoutParams.bottomMargin);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginTop");
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginRight(final View view, final int rightMargin) {
        return setMarginRight(view, rightMargin, true);
    }

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginRight(final View view, final int rightMargin, final boolean reset) {
        if (view != null) {
            // 判断是否属于 ViewGroup.MarginLayoutParams
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                if (reset) {
                    try {
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(0, 0, rightMargin, 0);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginRight");
                    }
                } else {
                    try {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        // 设置边距
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams())
                                .setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                                        rightMargin, layoutParams.bottomMargin);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginRight");
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginBottom(final View view, final int bottomMargin) {
        return setMarginBottom(view, bottomMargin, true);
    }

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginBottom(final View view, final int bottomMargin, final boolean reset) {
        if (view != null) {
            // 判断是否属于 ViewGroup.MarginLayoutParams
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                if (reset) {
                    try {
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(0, 0, 0, bottomMargin);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginBottom");
                    }
                } else {
                    try {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        // 设置边距
                        ((ViewGroup.MarginLayoutParams) view.getLayoutParams())
                                .setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                                        layoutParams.rightMargin, bottomMargin);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setMarginBottom");
                    }
                }
            }
        }
        return false;
    }

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
                    return true;
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
     * 获取 View Left Padding
     * @param view {@link View}
     * @return Left Padding
     */
    public static int getPaddingLeft(final View view) {
        return getPadding(view)[0];
    }

    /**
     * 获取 View Top Padding
     * @param view {@link View}
     * @return Top Padding
     */
    public static int getPaddingTop(final View view) {
        return getPadding(view)[1];
    }

    /**
     * 获取 View Right Padding
     * @param view {@link View}
     * @return Right Padding
     */
    public static int getPaddingRight(final View view) {
        return getPadding(view)[2];
    }

    /**
     * 获取 View Bottom Padding
     * @param view {@link View}
     * @return Bottom Padding
     */
    public static int getPaddingBottom(final View view) {
        return getPadding(view)[3];
    }

    /**
     * 获取 View Padding
     * @param view {@link View}
     * @return new int[] {left, top, right, bottom}
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
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingLeft(final View view, final int leftPadding) {
        return setPaddingLeft(view, leftPadding, true);
    }

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingLeft(final View view, final int leftPadding, final boolean reset) {
        if (view != null) {
            if (reset) {
                try {
                    view.setPadding(leftPadding, 0, 0, 0);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingLeft");
                }
            } else {
                try {
                    view.setPadding(leftPadding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingLeft");
                }
            }
        }
        return false;
    }

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingTop(final View view, final int topPadding) {
        return setPaddingTop(view, topPadding, true);
    }

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingTop(final View view, final int topPadding, final boolean reset) {
        if (view != null) {
            if (reset) {
                try {
                    view.setPadding(0, topPadding, 0, 0);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingTop");
                }
            } else {
                try {
                    view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), view.getPaddingBottom());
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingTop");
                }
            }
        }
        return false;
    }

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingRight(final View view, final int rightPadding) {
        return setPaddingRight(view, rightPadding, true);
    }

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingRight(final View view, final int rightPadding, final boolean reset) {
        if (view != null) {
            if (reset) {
                try {
                    view.setPadding(0, 0, rightPadding, 0);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingRight");
                }
            } else {
                try {
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), rightPadding, view.getPaddingBottom());
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingRight");
                }
            }
        }
        return false;
    }

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingBottom(final View view, final int bottomPadding) {
        return setPaddingBottom(view, bottomPadding, true);
    }

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingBottom(final View view, final int bottomPadding, final boolean reset) {
        if (view != null) {
            if (reset) {
                try {
                    view.setPadding(0, 0, 0, bottomPadding);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingBottom");
                }
            } else {
                try {
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), bottomPadding);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPaddingBottom");
                }
            }
        }
        return false;
    }

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
                return true;
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

    // =====================
    // = CompoundDrawables =
    // =====================

    /**
     * 获取 CompoundDrawables
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return Drawable[] { left, top, right, bottom }
     */
    public static <T extends TextView> Drawable[] getCompoundDrawables(final T textView) {
        if (textView != null) {
            return textView.getCompoundDrawables();
        }
        return new Drawable[]{null, null, null, null};
    }

    /**
     * 获取 CompoundDrawables Padding
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return CompoundDrawables Padding
     */
    public static <T extends TextView> int getCompoundDrawablePadding(final T textView) {
        if (textView != null) {
            return textView.getCompoundDrawablePadding();
        }
        return 0;
    }

    // ========================
    // = setCompoundDrawables =
    // ========================

    /**
     * 设置 Left CompoundDrawables
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByLeft(final T textView, final Drawable left) {
        return setCompoundDrawables(textView, left, null, null, null);
    }

    /**
     * 设置 Top CompoundDrawables
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByTop(final T textView, final Drawable top) {
        return setCompoundDrawables(textView, null, top, null, null);
    }

    /**
     * 设置 Right CompoundDrawables
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByRight(final T textView, final Drawable right) {
        return setCompoundDrawables(textView, null, null, right, null);
    }

    /**
     * 设置 Bottom CompoundDrawables
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesByBottom(final T textView, final Drawable bottom) {
        return setCompoundDrawables(textView, null, null, null, bottom);
    }

    /**
     * 设置 CompoundDrawables
     * <pre>
     *     CompoundDrawable 的大小控制是通过 drawable.setBounds() 控制
     *     需要先设置 Drawable 的 setBounds
     * </pre>
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param top      top Drawable
     * @param right    right Drawable
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawables(final T textView,
                                                              final Drawable left, final Drawable top,
                                                              final Drawable right, final Drawable bottom) {
        if (textView != null) {
            try {
                textView.setCompoundDrawables(left, top, right, bottom);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setCompoundDrawables");
            }
        }
        return textView;
    }

    // ===========================================
    // = setCompoundDrawablesWithIntrinsicBounds =
    // ===========================================

    /**
     * 设置 Left CompoundDrawables - 按照原有比例大小显示图片
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByLeft(final T textView, final Drawable left) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, left, null, null, null);
    }

    /**
     * 设置 Top CompoundDrawables - 按照原有比例大小显示图片
     * @param textView {@link TextView}
     * @param top      top Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByTop(final T textView, final Drawable top) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, null, top, null, null);
    }

    /**
     * 设置 Right CompoundDrawables - 按照原有比例大小显示图片
     * @param textView {@link TextView}
     * @param right    right Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByRight(final T textView, final Drawable right) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, null, null, right, null);
    }

    /**
     * 设置 Bottom CompoundDrawables - 按照原有比例大小显示图片
     * @param textView {@link TextView}
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBoundsByBottom(final T textView, final Drawable bottom) {
        return setCompoundDrawablesWithIntrinsicBounds(textView, null, null, null, bottom);
    }

    /**
     * 设置 CompoundDrawables - 按照原有比例大小显示图片
     * @param textView {@link TextView}
     * @param left     left Drawable
     * @param top      top Drawable
     * @param right    right Drawable
     * @param bottom   bottom Drawable
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends TextView> T setCompoundDrawablesWithIntrinsicBounds(final T textView,
                                                                                 final Drawable left, final Drawable top,
                                                                                 final Drawable right, final Drawable bottom) {
        if (textView != null) {
            try {
                textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setCompoundDrawablesWithIntrinsicBounds");
            }
        }
        return textView;
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