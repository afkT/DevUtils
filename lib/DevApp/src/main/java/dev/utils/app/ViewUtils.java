package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.anim.AnimationUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.FieldUtils;

/**
 * detail: View 操作相关工具类
 * @author Ttt
 * <pre>
 *     组件设置 setCompoundDrawables 不生效解决办法
 *     @see <a href="https://blog.csdn.net/qq_26971803/article/details/54347598"/>
 *     Android 常用布局属性
 *     @see <a href="https://www.jianshu.com/p/78e2dfb6d244"/>
 *     Android 应用坐标系统全面详解
 *     @see <a href="https://blog.csdn.net/yanbober/article/details/50419117"/>
 *     <p></p>
 *     RelativeLayout 的特有属性
 *     属性值为 true、false
 *     android:layout_centerHrizontal 位于父控件的横向中间位置
 *     android:layout_centerVertical 位于父控件的纵向中间位置
 *     android:layout_centerInparent 位于父控件的纵横向中间位置
 *     android:layout_alignParentBottom 贴紧父元素的下边缘
 *     android:layout_alignParentLeft 贴紧父元素的左边缘
 *     android:layout_alignParentRight 贴紧父元素的右边缘
 *     android:layout_alignParentTop 贴紧父元素的上边缘
 *     android:layout_alignParentStart 将控件开始位置与父控件的开始位置对齐
 *     android:layout_alignParentEnd 将控件结束位置与父控件的结束位置对齐
 *     属性值为引用 id
 *     android:layout_below 在某元素的下方
 *     android:layout_above 在某元素的的上方
 *     android:layout_toLeftOf 在某元素的左边
 *     android:layout_toRightOf 在某元素的右边
 *     android:layout_toStartOf 在某元素的开始位置
 *     android:layout_toEndOf 在某元素的结束位置
 *     android:layout_alignTop 本元素的上边缘和某元素的的上边缘对齐
 *     android:layout_alignLeft 本元素的左边缘和某元素的的左边缘对齐
 *     android:layout_alignBottom 本元素的下边缘和某元素的的下边缘对齐
 *     android:layout_alignRight 本元素的右边缘和某元素的的右边缘对齐
 *     android:layout_alignStart 本元素与某元素开始位置对齐
 *     android:layout_alignEnd 本元素与某元素结束位置对齐
 *     android:layout_alignBaseline 将当前控件的基线与指定 id 控件 t 的基线对齐
 * </pre>
 */
public final class ViewUtils {

    private ViewUtils() {
    }

    // 日志 TAG
    private static final String TAG          = ViewUtils.class.getSimpleName();
    // MATCH_PARENT
    public static final  int    MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    // WRAP_CONTENT
    public static final  int    WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

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

    /**
     * 获取 View
     * @param context  {@link Context}
     * @param resource R.layout.id
     * @return {@link View}
     */
    public static View inflate(final Context context, @LayoutRes final int resource) {
        return inflate(context, resource, null, false);
    }

    /**
     * 获取 View
     * @param context  {@link Context}
     * @param resource R.layout.id
     * @param root     {@link ViewGroup}
     * @return {@link View}
     */
    public static View inflate(final Context context, @LayoutRes final int resource, final ViewGroup root) {
        return inflate(context, resource, root, root != null);
    }

    /**
     * 获取 View
     * <pre>
     *     获取含有 android.support View 需要传入当前 Activity Context
     * </pre>
     * @param context      {@link Context}
     * @param resource     R.layout.id
     * @param root         {@link ViewGroup}
     * @param attachToRoot 是否添加到 root 上
     * @return {@link View}
     */
    public static View inflate(final Context context, @LayoutRes final int resource, final ViewGroup root, final boolean attachToRoot) {
        try {
            return LayoutInflater.from(context).inflate(resource, root, attachToRoot);
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
     * @return {@link View}
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

    /**
     * 获取 android.R.id.content View
     * @param activity {@link Activity}
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends View> T getContentView(final Activity activity) {
        return ViewUtils.findViewById(activity, android.R.id.content);
    }

    /**
     * 获取 android.R.id.content View
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T getContentView(final View view) {
        if (view != null) {
            try {
                ViewParent parent = view.getParent();
                while (parent != null && parent instanceof View) {
                    View root = (View) parent;
                    if (root.getId() == android.R.id.content) {
                        return (T) root;
                    }
                    parent = parent.getParent();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getContentView");
            }
        }
        return null;
    }

    /**
     * 获取指定 View 根布局 ( 最底层布局 )
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T getRootParent(final View view) {
        if (view != null) {
            try {
                View root = null;
                ViewParent parent = view.getParent();
                while (parent != null && parent instanceof View) {
                    root = (View) parent;
                    parent = parent.getParent();
                }
                return (T) root;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getRootParent");
            }
        }
        return null;
    }

    /**
     * 获取是否限制子 View 在其边界内绘制
     * @param viewGroup {@link ViewGroup}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean getClipChildren(final ViewGroup viewGroup) {
        if (viewGroup != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return viewGroup.getClipChildren();
            }
        }
        return true;
    }

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param viewGroup    {@link ViewGroup}
     * @param clipChildren {@code true} yes, {@code false} no
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setClipChildren(final ViewGroup viewGroup, final boolean clipChildren) {
        if (viewGroup != null) {
            viewGroup.setClipChildren(clipChildren);
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取子 View 总数
     * @param viewGroup {@link ViewGroup}
     * @return 子 View 总数
     */
    public static int getChildCount(final ViewGroup viewGroup) {
        if (viewGroup != null) {
            return viewGroup.getChildCount();
        }
        return 0;
    }

    /**
     * 获取指定索引 View
     * @param viewGroup {@link ViewGroup}
     * @param <T>       泛型
     * @return {@link View}
     */
    public static <T extends View> T getChildAt(final ViewGroup viewGroup) {
        return getChildAt(viewGroup, 0);
    }

    /**
     * 获取指定索引 View
     * @param viewGroup {@link ViewGroup}
     * @param index     索引
     * @param <T>       泛型
     * @return {@link View}
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

    /**
     * 获取全部子 View
     * @param viewGroup {@link ViewGroup}
     * @return View[]
     */
    public static View[] getChilds(final ViewGroup viewGroup) {
        View[] views = new View[getChildCount(viewGroup)];
        for (int i = 0, len = views.length; i < len; i++) {
            views[i] = getChildAt(viewGroup, i);
        }
        return views;
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
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final View view) {
        return view == null;
    }

    /**
     * 判断 View 是否为 null
     * @param views View[]
     * @return {@code true} yes, {@code false} no
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
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final View view) {
        return view != null;
    }

    /**
     * 判断 View 是否不为 null
     * @param views View[]
     * @return {@code true} yes, {@code false} no
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
     * 获取 View 宽高
     * @param view {@link View}
     * @return int[], 0 = 宽度, 1 = 高度
     */
    public static int[] getWidthHeight(final View view) {
        if (view != null) {
            return new int[]{view.getWidth(), view.getHeight()};
        }
        return new int[]{0, 0};
    }

    /**
     * 设置 View 宽度、高度
     * @param view   {@link View}
     * @param width  View 宽度
     * @param height View 高度
     * @return {@link View}
     */
    public static View setWidthHeight(final View view, final int width, final int height) {
        return setWidthHeight(view, width, height, true);
    }

    /**
     * 设置 View 宽度、高度
     * @param view      {@link View}
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link View}
     */
    public static View setWidthHeight(final View view, final int width, final int height, final boolean nullNewLP) {
        if (view != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = width;
                    layoutParams.height = height;
                    view.setLayoutParams(layoutParams);
                } else if (nullNewLP) {
                    layoutParams = new ViewGroup.LayoutParams(width, height);
                    view.setLayoutParams(layoutParams);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setWidthHeight");
            }
        }
        return view;
    }

    // =

    /**
     * 获取 View 宽度
     * @param view {@link View}
     * @return View 宽度
     */
    public static int getWidth(final View view) {
        if (view != null) {
            return view.getWidth();
        }
        return 0;
    }

    /**
     * 设置 View 宽度
     * @param view  {@link View}
     * @param width View 宽度
     * @return {@link View}
     */
    public static View setWidth(final View view, final int width) {
        return setWidth(view, width, true);
    }

    /**
     * 设置 View 宽度
     * @param view      {@link View}
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link View}
     */
    public static View setWidth(final View view, final int width, final boolean nullNewLP) {
        if (view != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = width;
                    view.setLayoutParams(layoutParams);
                } else if (nullNewLP) {
                    layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                    view.setLayoutParams(layoutParams);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setWidth");
            }
        }
        return view;
    }

    // =

    /**
     * 获取 View 高度
     * @param view {@link View}
     * @return View 高度
     */
    public static int getHeight(final View view) {
        if (view != null) {
            return view.getHeight();
        }
        return 0;
    }

    /**
     * 设置 View 高度
     * @param view   {@link View}
     * @param height View 高度
     * @return {@link View}
     */
    public static View setHeight(final View view, final int height) {
        return setHeight(view, height, true);
    }

    /**
     * 设置 View 高度
     * @param view      {@link View}
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return {@link View}
     */
    public static View setHeight(final View view, final int height, final boolean nullNewLP) {
        if (view != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = height;
                    view.setLayoutParams(layoutParams);
                } else if (nullNewLP) {
                    layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
                    view.setLayoutParams(layoutParams);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setHeight");
            }
        }
        return view;
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
        return 0;
    }

    /**
     * 设置 View 最小高度
     * @param view      {@link View}
     * @param minHeight 最小高度
     * @return {@link View}
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
        return 0;
    }

    /**
     * 设置 View 最小宽度
     * @param view     {@link View}
     * @param minWidth 最小宽度
     * @return {@link View}
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
     * @return {@link View}
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
     * @return {@link View}
     */
    public static View setTag(final View view, final Object object) {
        if (view != null) {
            view.setTag(object);
        }
        return view;
    }

    // =

    /**
     * View 内容滚动位置 - 相对于初始位置移动
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link View}
     */
    public static View scrollTo(final View view, final int x, final int y) {
        if (view != null) view.scrollTo(x, y);
        return view;
    }

    /**
     * View 内部滚动位置 - 相对于上次移动的最后位置移动
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link View}
     */
    public static View scrollBy(final View view, final int x, final int y) {
        if (view != null) view.scrollBy(x, y);
        return view;
    }

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param view  {@link View}
     * @param value X 轴坐标
     * @return {@link View}
     */
    public static View setScrollX(final View view, final int value) {
        if (view != null) view.setScrollX(value);
        return view;
    }

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param view  {@link View}
     * @param value Y 轴坐标
     * @return {@link View}
     */
    public static View setScrollY(final View view, final int value) {
        if (view != null) view.setScrollY(value);
        return view;
    }

    /**
     * 获取 View 滑动的 X 轴坐标
     * @param view {@link View}
     * @return 滑动的 X 轴坐标
     */
    public static int getScrollX(final View view) {
        return view != null ? view.getScrollX() : 0;
    }

    /**
     * 获取 View 滑动的 Y 轴坐标
     * @param view {@link View}
     * @return 滑动的 Y 轴坐标
     */
    public static int getScrollY(final View view) {
        return view != null ? view.getScrollY() : 0;
    }

    // =

    /**
     * 设置 ViewGroup 和其子控件两者之间的关系
     * <pre>
     *     beforeDescendants : ViewGroup 会优先其子类控件而获取到焦点
     *     afterDescendants : ViewGroup 只有当其子类控件不需要获取焦点时才获取焦点
     *     blocksDescendants : ViewGroup 会覆盖子类控件而直接获得焦点
     *     android:descendantFocusability="blocksDescendants"
     * </pre>
     * @param viewGroup    {@link ViewGroup}
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @param <T>          泛型
     * @return {@link ViewGroup}
     */
    public static <T extends ViewGroup> T setDescendantFocusability(final T viewGroup, final int focusability) {
        try {
            if (viewGroup != null) viewGroup.setDescendantFocusability(focusability);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setDescendantFocusability");
        }
        return viewGroup;
    }

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param view           {@link View}
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @param <T>            泛型
     * @return {@link View}
     */
    public static <T extends View> T setOverScrollMode(final T view, final int overScrollMode) {
        try {
            if (view != null) view.setOverScrollMode(overScrollMode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setOverScrollMode");
        }
        return view;
    }

    // =

    /**
     * 是否绘制横向滚动条
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHorizontalScrollBarEnabled(final View view) {
        return (view != null) ? view.isHorizontalScrollBarEnabled() : false;
    }

    /**
     * 设置是否绘制横向滚动条
     * @param view                       {@link View}
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link View}
     */
    public static View setHorizontalScrollBarEnabled(final View view, final boolean horizontalScrollBarEnabled) {
        if (view != null) view.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
        return view;
    }

    /**
     * 是否绘制垂直滚动条
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVerticalScrollBarEnabled(final View view) {
        return (view != null) ? view.isVerticalScrollBarEnabled() : false;
    }

    /**
     * 设置是否绘制垂直滚动条
     * @param view                     {@link View}
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link View}
     */
    public static View setVerticalScrollBarEnabled(final View view, final boolean verticalScrollBarEnabled) {
        if (view != null) view.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
        return view;
    }

    // =

    /**
     * 获取 View 是否需要滚动效应
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isScrollContainer(final View view) {
        if (view != null) {
            return view.isScrollContainer();
        }
        return false;
    }

    /**
     * 设置 View 滚动效应
     * @param view              {@link View}
     * @param isScrollContainer 是否需要滚动效应
     * @return {@link View}
     */
    public static View setScrollContainer(final View view, final boolean isScrollContainer) {
        if (view != null) {
            view.setScrollContainer(isScrollContainer);
        }
        return view;
    }

    // =

    /**
     * 下一个获取焦点的 View id
     * @param view {@link View}
     * @return 下一个获取焦点的 View id
     */
    public static int getNextFocusForwardId(final View view) {
        if (view != null) {
            return view.getNextFocusForwardId();
        }
        return 0;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param view               {@link View}
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusForwardId(final View view, @IdRes final int nextFocusForwardId) {
        if (view != null) {
            view.setNextFocusForwardId(nextFocusForwardId);
        }
        return view;
    }

    // =

    /**
     * 向下移动焦点时, 下一个获取焦点的 View id
     * @param view {@link View}
     * @return 向下移动焦点时, 下一个获取焦点的 View id
     */
    public static int getNextFocusDownId(final View view) {
        if (view != null) {
            return view.getNextFocusDownId();
        }
        return 0;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusDownId(final View view, @IdRes final int nextFocusDownId) {
        if (view != null) {
            view.setNextFocusDownId(nextFocusDownId);
        }
        return view;
    }

    // =

    /**
     * 向左移动焦点时, 下一个获取焦点的 View id
     * @param view {@link View}
     * @return 向左移动焦点时, 下一个获取焦点的 View id
     */
    public static int getNextFocusLeftId(final View view) {
        if (view != null) {
            return view.getNextFocusLeftId();
        }
        return 0;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusLeftId(final View view, @IdRes final int nextFocusLeftId) {
        if (view != null) {
            view.setNextFocusLeftId(nextFocusLeftId);
        }
        return view;
    }

    // =

    /**
     * 向右移动焦点时, 下一个获取焦点的 View id
     * @param view {@link View}
     * @return 向右移动焦点时, 下一个获取焦点的 View id
     */
    public static int getNextFocusRightId(final View view) {
        if (view != null) {
            return view.getNextFocusRightId();
        }
        return 0;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             {@link View}
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusRightId(final View view, @IdRes final int nextFocusRightId) {
        if (view != null) {
            view.setNextFocusRightId(nextFocusRightId);
        }
        return view;
    }

    // =

    /**
     * 向上移动焦点时, 下一个获取焦点的 View id
     * @param view {@link View}
     * @return 向上移动焦点时, 下一个获取焦点的 View id
     */
    public static int getNextFocusUpId(final View view) {
        if (view != null) {
            return view.getNextFocusUpId();
        }
        return 0;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          {@link View}
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusUpId(final View view, @IdRes final int nextFocusUpId) {
        if (view != null) {
            view.setNextFocusUpId(nextFocusUpId);
        }
        return view;
    }

    // =

    /**
     * 获取 View 旋转度数
     * @param view {@link View}
     * @return View 旋转度数
     */
    public static float getRotation(final View view) {
        if (view != null) {
            return view.getRotation();
        }
        return 0f;
    }

    /**
     * 设置 View 旋转度数
     * @param view     {@link View}
     * @param rotation 旋转度数
     * @return {@link View}
     */
    public static View setRotation(final View view, final float rotation) {
        if (view != null) {
            view.setRotation(rotation);
        }
        return view;
    }

    // =

    /**
     * 获取 View 水平旋转度数
     * @param view {@link View}
     * @return View 水平旋转度数
     */
    public static float getRotationX(final View view) {
        if (view != null) {
            return view.getRotationX();
        }
        return 0f;
    }

    /**
     * 设置 View 水平旋转度数
     * @param view      {@link View}
     * @param rotationX 水平旋转度数
     * @return {@link View}
     */
    public static View setRotationX(final View view, final float rotationX) {
        if (view != null) {
            view.setRotationX(rotationX);
        }
        return view;
    }

    // =

    /**
     * 获取 View 竖直旋转度数
     * @param view {@link View}
     * @return View 竖直旋转度数
     */
    public static float getRotationY(final View view) {
        if (view != null) {
            return view.getRotationY();
        }
        return 0f;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param view      {@link View}
     * @param rotationY 竖直旋转度数
     * @return {@link View}
     */
    public static View setRotationY(final View view, final float rotationY) {
        if (view != null) {
            view.setRotationY(rotationY);
        }
        return view;
    }

    // =

    /**
     * 获取 View 水平方向缩放比例
     * @param view View
     * @return View 水平方向缩放比例
     */
    public static float getScaleX(final View view) {
        if (view != null) {
            return view.getScaleX();
        }
        return 0f;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param view   View
     * @param scaleX 水平方向缩放比例
     * @return {@link View}
     */
    public static View setScaleX(final View view, final float scaleX) {
        if (view != null) {
            view.setScaleX(scaleX);
        }
        return view;
    }

    // =

    /**
     * 获取 View 竖直方向缩放比例
     * @param view View
     * @return View 竖直方向缩放比例
     */
    public static float getScaleY(final View view) {
        if (view != null) {
            return view.getScaleY();
        }
        return 0f;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param view   View
     * @param scaleY 竖直方向缩放比例
     * @return {@link View}
     */
    public static View setScaleY(final View view, final float scaleY) {
        if (view != null) {
            view.setScaleY(scaleY);
        }
        return view;
    }

    // =

    /**
     * 获取文本的显示方式
     * @param view {@link View}
     * @return 文本的显示方式
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getTextAlignment(final View view) {
        if (view != null) {
            return view.getTextAlignment();
        }
        return 0;
    }

    /**
     * 设置文本的显示方式
     * @param view          {@link View}
     * @param textAlignment 文本的显示方式
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View setTextAlignment(final View view, final int textAlignment) {
        if (view != null) {
            view.setTextAlignment(textAlignment);
        }
        return view;
    }

    // =

    /**
     * 获取文本的显示方向
     * @param view {@link View}
     * @return 文本的显示方向
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getTextDirection(final View view) {
        if (view != null) {
            return view.getTextDirection();
        }
        return 0;
    }

    /**
     * 设置文本的显示方向
     * @param view          {@link View}
     * @param textDirection 文本的显示方向
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View setTextDirection(final View view, final int textDirection) {
        if (view != null) {
            view.setTextDirection(textDirection);
        }
        return view;
    }

    // =

    /**
     * 获取水平方向偏转量
     * @param view {@link View}
     * @return 水平方向偏转量
     */
    public static float getPivotX(final View view) {
        if (view != null) {
            return view.getPivotX();
        }
        return 0f;
    }

    /**
     * 设置水平方向偏转量
     * @param view   View
     * @param pivotX 水平方向偏转量
     * @return {@link View}
     */
    public static View setPivotX(final View view, final float pivotX) {
        if (view != null) {
            view.setPivotX(pivotX);
        }
        return view;
    }

    // =

    /**
     * 获取竖直方向偏转量
     * @param view {@link View}
     * @return 竖直方向偏转量
     */
    public static float getPivotY(final View view) {
        if (view != null) {
            return view.getPivotY();
        }
        return 0f;
    }

    /**
     * 设置竖直方向偏转量
     * @param view   View
     * @param pivotY 竖直方向偏转量
     * @return {@link View}
     */
    public static View setPivotY(final View view, final float pivotY) {
        if (view != null) {
            view.setPivotY(pivotY);
        }
        return view;
    }

    // =

    /**
     * 获取水平方向的移动距离
     * @param view {@link View}
     * @return 水平方向的移动距离
     */
    public static float getTranslationX(final View view) {
        if (view != null) {
            return view.getTranslationX();
        }
        return 0f;
    }

    /**
     * 设置水平方向的移动距离
     * @param view         {@link View}
     * @param translationX 水平方向的移动距离
     * @return {@link View}
     */
    public static View setTranslationX(final View view, final float translationX) {
        if (view != null) {
            view.setTranslationX(translationX);
        }
        return view;
    }

    // =

    /**
     * 获取竖直方向的移动距离
     * @param view {@link View}
     * @return 竖直方向的移动距离
     */
    public static float getTranslationY(final View view) {
        if (view != null) {
            return view.getTranslationY();
        }
        return 0f;
    }

    /**
     * 设置竖直方向的移动距离
     * @param view         {@link View}
     * @param translationY 竖直方向的移动距离
     * @return {@link View}
     */
    public static View setTranslationY(final View view, final float translationY) {
        if (view != null) {
            view.setTranslationY(translationY);
        }
        return view;
    }

    // =

    /**
     * 获取 View 硬件加速类型
     * @param view {@link View}
     * @return View 硬件加速类型
     */
    public static int getLayerType(final View view) {
        if (view != null) {
            return view.getLayerType();
        }
        return 0;
    }

    /**
     * 设置 View 硬件加速类型
     * @param view      {@link View}
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@link View}
     */
    public static View setLayerType(final View view, final int layerType, final Paint paint) {
        if (view != null) {
            view.setLayerType(layerType, paint);
        }
        return view;
    }

    // =

    /**
     * 请求重新对 View 布局
     * @param view {@link View}
     * @return {@link View}
     */
    public static View requestLayout(final View view) {
        if (view != null) {
            view.requestLayout();
        }
        return view;
    }

    /**
     * View 请求获取焦点
     * @param view {@link View}
     * @return {@link View}
     */
    public static View requestFocus(final View view) {
        if (view != null) {
            view.requestFocus();
        }
        return view;
    }

    /**
     * View 清除焦点
     * @param view {@link View}
     * @return {@link View}
     */
    public static View clearFocus(final View view) {
        if (view != null) {
            view.clearFocus();
        }
        return view;
    }

    /**
     * 获取 View 里获取焦点的 View
     * @param view {@link View}
     * @return {@link View}
     */
    public static View findFocus(final View view) {
        if (view != null) {
            return view.findFocus();
        }
        return null;
    }

    /**
     * 获取是否当前 View 就是焦点 View
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFocused(final View view) {
        if (view != null) {
            return view.isFocused();
        }
        return false;
    }

    /**
     * 获取当前 View 是否是焦点 View 或者子 View 里面有焦点 View
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean hasFocus(final View view) {
        if (view != null) {
            return view.hasFocus();
        }
        return false;
    }

    /**
     * 获取当前 View 或者子 View 是否可以获取焦点
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean hasFocusable(final View view) {
        if (view != null) {
            return view.hasFocusable();
        }
        return false;
    }

    // =

    /**
     * 获取 View 是否在触摸模式下获得焦点
     * @param view {@link View}
     * @return {@code true} 可获取, {@code false} 不可获取
     */
    public static boolean isFocusableInTouchMode(final View view) {
        if (view != null) {
            return view.isFocusableInTouchMode();
        }
        return false;
    }

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @param views                View[]
     * @return {@code true} 可获取, {@code false} 不可获取
     */
    public static boolean setFocusableInTouchMode(final boolean focusableInTouchMode, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setFocusableInTouchMode(focusableInTouchMode);
                }
            }
        }
        return focusableInTouchMode;
    }

    // =

    /**
     * 获取 View 是否可以获取焦点
     * @param view {@link View}
     * @return {@code true} 可获取, {@code false} 不可获取
     */
    public static boolean isFocusable(final View view) {
        if (view != null) {
            return view.isFocusable();
        }
        return false;
    }

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
     * 切换获取焦点状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleFocusable(final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setFocusable(!view.isFocusable());
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取 View 是否选中
     * @param view {@link View}
     * @return {@code true} 选中, {@code false} 非选中
     */
    public static boolean isSelected(final View view) {
        if (view != null) {
            return view.isSelected();
        }
        return false;
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
     * 切换选中状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleSelected(final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setSelected(!view.isSelected());
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取 View 是否启用
     * @param view {@link View}
     * @return {@code true} 启用, {@code false} 禁用
     */
    public static boolean isEnabled(final View view) {
        if (view != null) {
            return view.isEnabled();
        }
        return false;
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
     * 切换 View 是否启用状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleEnabled(final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setEnabled(!view.isEnabled());
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取 View 是否可以点击
     * @param view {@link View}
     * @return {@code true} 可点击, {@code false} 不可点击
     */
    public static boolean isClickable(final View view) {
        if (view != null) {
            return view.isClickable();
        }
        return false;
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
     * 切换 View 是否可以点击状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleClickable(final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setClickable(!view.isClickable());
                }
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 获取 View 是否可以长按
     * @param view {@link View}
     * @return {@code true} 可长按, {@code false} 不可长按
     */
    public static boolean isLongClickable(final View view) {
        if (view != null) {
            return view.isLongClickable();
        }
        return false;
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

    /**
     * 切换 View 是否可以长按状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleLongClickable(final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null) {
                    view.setLongClickable(!view.isLongClickable());
                }
            }
            return true;
        }
        return false;
    }

    // =================
    // = View 显示状态 =
    // =================

    /**
     * 判断 View 是否显示 ( 如果存在父级则判断父级 )
     * <pre>
     *     需要父布局已展示到 Window 上
     * </pre>
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isShown(final View view) {
        return (view != null) ? view.isShown() : false;
    }

    /**
     * 判断 View 是否都显示 ( 如果存在父级则判断父级 )
     * @param views View[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isShowns(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                if (!isShown(views[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // =

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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleVisibilitys(final View view, final View... views) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        setVisibilitys(View.GONE, views);
        return true;
    }

    /**
     * 切换 View 显示的状态
     * @param viewArys View[]
     * @param views    View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleVisibilitys(final View[] viewArys, final View... views) {
        return toggleVisibilitys(View.GONE, viewArys, views);
    }

    /**
     * 切换 View 显示的状态
     * @param state    {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArys View[]
     * @param views    View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleVisibilitys(final int state, final View[] viewArys, final View... views) {
        // 默认显示
        setVisibilitys(View.VISIBLE, viewArys);
        // 根据状态处理
        setVisibilitys(state, views);
        return true;
    }

    // =

    /**
     * 反转 View 显示的状态
     * @param state    {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArys View[]
     * @param views    View[]
     * @return isVisibility
     */
    public static boolean reverseVisibilitys(final int state, final View[] viewArys, final View... views) {
        return reverseVisibilitys(state == View.VISIBLE, viewArys, views);
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
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view  {@link View}
     * @param views View[]
     * @return isVisibility
     */
    public static boolean reverseVisibilitys(final int state, final View view, final View... views) {
        return reverseVisibilitys(state == View.VISIBLE, view, views);
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
     * @return {@link View}
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
     * @return {@link View}
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
        return WidgetUtils.measureView(view);
    }

    /**
     * 获取 View 的宽度
     * @param view {@link View}
     * @return View 的宽度
     */
    public static int getMeasuredWidth(final View view) {
        return WidgetUtils.getMeasuredWidth(view);
    }

    /**
     * 获取 View 的高度
     * @param view {@link View}
     * @return View 的高度
     */
    public static int getMeasuredHeight(final View view) {
        return WidgetUtils.getMeasuredHeight(view);
    }

    /**
     * 测量 View
     * @param view           {@link View}
     * @param specifiedWidth 指定宽度
     * @return {@code true} success, {@code false} fail
     */
    public static boolean measureView(final View view, final int specifiedWidth) {
        return WidgetUtils.measureView(view, specifiedWidth);
    }

    /**
     * 测量 View
     * @param view            {@link View}
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return {@code true} success, {@code false} fail
     */
    public static boolean measureView(final View view, final int specifiedWidth, final int specifiedHeight) {
        return WidgetUtils.measureView(view, specifiedWidth, specifiedHeight);
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
        return getLayoutGravity(view, true);
    }

    /**
     * 获取 View Layout Gravity
     * @param view         {@link View}
     * @param isReflection 是否使用反射
     * @return Layout Gravity
     */
    public static int getLayoutGravity(final View view, final boolean isReflection) {
        if (view != null && view.getLayoutParams() != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    return ((LinearLayout.LayoutParams) layoutParams).gravity;
                } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                    return ((FrameLayout.LayoutParams) layoutParams).gravity;
                } else if (layoutParams instanceof WindowManager.LayoutParams) {
                    return ((WindowManager.LayoutParams) layoutParams).gravity;
                }
                if (isReflection) {
                    Field[] fields = FieldUtils.getFields(layoutParams);
                    for (Field field : fields) {
                        if (field.getName().equals("gravity")) {
                            return (int) field.get(layoutParams);
                        }
                    }
                }
                // 抛出不支持的类型
                throw new Exception("layoutParams:" + layoutParams.toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getLayoutGravity");
            }
        }
        return 0;
    }

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLayoutGravity(final View view, final int gravity) {
        return setLayoutGravity(view, gravity, true);
    }

    /**
     * 设置 View Layout Gravity
     * @param view         {@link View}
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLayoutGravity(final View view, final int gravity, final boolean isReflection) {
        if (view != null && view.getLayoutParams() != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) layoutParams).gravity = gravity;
                    view.setLayoutParams(layoutParams);
                    return true;
                } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                    ((FrameLayout.LayoutParams) layoutParams).gravity = gravity;
                    view.setLayoutParams(layoutParams);
                    return true;
                } else if (layoutParams instanceof WindowManager.LayoutParams) {
                    ((WindowManager.LayoutParams) layoutParams).gravity = gravity;
                    view.setLayoutParams(layoutParams);
                    return true;
                }
                if (isReflection) {
                    Field[] fields = FieldUtils.getFields(layoutParams);
                    for (Field field : fields) {
                        if (field.getName().equals("gravity")) {
                            field.set(layoutParams, gravity);
                            view.setLayoutParams(layoutParams);
                            return true;
                        }
                    }
                }
                // 抛出不支持的类型
                throw new Exception("layoutParams:" + layoutParams.toString());
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

    /**
     * 获取 View Margin
     * @param view {@link View}
     * @return new int[] {left, top, right, bottom}
     */
    public static int[] getMargin(final View view) {
        int[] margin = new int[]{0, 0, 0, 0};
        if (view != null && view.getLayoutParams() != null) {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                margin[0] = layoutParams.leftMargin;
                margin[1] = layoutParams.topMargin;
                margin[2] = layoutParams.rightMargin;
                margin[3] = layoutParams.bottomMargin;
            }
        }
        return margin;
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
        if (reset) return setMargin(view, leftMargin, 0, 0, 0);
        int[] margin = getMargin(view);
        return setMargin(view, leftMargin, margin[1], margin[2], margin[3]);
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
        if (reset) return setMargin(view, 0, topMargin, 0, 0);
        int[] margin = getMargin(view);
        return setMargin(view, margin[0], topMargin, margin[2], margin[3]);
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
        if (reset) return setMargin(view, 0, 0, rightMargin, 0);
        int[] margin = getMargin(view);
        return setMargin(view, margin[0], margin[1], rightMargin, margin[3]);
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
        if (reset) return setMargin(view, 0, 0, 0, bottomMargin);
        int[] margin = getMargin(view);
        return setMargin(view, margin[0], margin[1], margin[2], bottomMargin);
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
        if (view != null && view.getLayoutParams() != null) {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                layoutParams.setMargins(left, top, right, bottom);
                view.setLayoutParams(layoutParams);
                return true;
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(final View[] views, final int leftRight, final int topBottom) {
        return setMargin(views, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param margin Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(final View[] views, final int margin) {
        return setMargin(views, margin, margin, margin, margin);
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(final View[] views, final int left, final int top, final int right, final int bottom) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                setMargin(views[i], left, top, right, bottom);
            }
            return true;
        }
        return false;
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
        int[] padding = new int[]{0, 0, 0, 0};
        if (view != null) {
            padding[0] = view.getPaddingLeft();
            padding[1] = view.getPaddingTop();
            padding[2] = view.getPaddingRight();
            padding[3] = view.getPaddingBottom();
        }
        return padding;
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
        if (reset) return setPadding(view, leftPadding, 0, 0, 0);
        int[] padding = getPadding(view);
        return setPadding(view, leftPadding, padding[1], padding[2], padding[3]);
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
        if (reset) return setPadding(view, 0, topPadding, 0, 0);
        int[] padding = getPadding(view);
        return setPadding(view, padding[0], topPadding, padding[2], padding[3]);
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
        if (reset) return setPadding(view, 0, 0, rightPadding, 0);
        int[] padding = getPadding(view);
        return setPadding(view, padding[0], padding[1], rightPadding, padding[3]);
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
        if (reset) return setPadding(view, 0, 0, 0, bottomPadding);
        int[] padding = getPadding(view);
        return setPadding(view, padding[0], padding[1], padding[2], bottomPadding);
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
            view.setPadding(left, top, right, bottom);
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 Padding 边距
     * @param views     View[]
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(final View[] views, final int leftRight, final int topBottom) {
        return setPadding(views, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Padding 边距
     * @param views   View[]
     * @param padding Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(final View[] views, final int padding) {
        return setPadding(views, padding, padding, padding, padding);
    }

    /**
     * 设置 Padding 边距
     * @param views  View[]
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(final View[] views, final int left, final int top, final int right, final int bottom) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                setPadding(views[i], left, top, right, bottom);
            }
            return true;
        }
        return false;
    }

    // ==================
    // = RelativeLayout =
    // ==================

    /**
     * 设置 RelativeLayout View 布局规则
     * @param view {@link View}
     * @param verb 布局位置
     * @return {@link View}
     */
    public static View addRule(final View view, final int verb) {
        return addRule(view, verb, -1);
    }

    /**
     * 设置 RelativeLayout View 布局规则
     * @param view    {@link View}
     * @param verb    布局位置
     * @param subject 关联 View id
     * @return {@link View}
     */
    public static View addRule(final View view, final int verb, final int subject) {
        if (view != null) {
            try {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.addRule(verb, subject);
                view.setLayoutParams(layoutParams);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addRule");
            }
        }
        return view;
    }

    /**
     * 移除 RelativeLayout View 布局规则
     * @param view {@link View}
     * @param verb 布局位置
     * @return {@link View}
     */
    public static View removeRule(final View view, final int verb) {
        if (view != null) {
            try {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.removeRule(verb);
                } else {
                    layoutParams.addRule(verb, 0);
                }
                view.setLayoutParams(layoutParams);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeRule");
            }
        }
        return view;
    }

    /**
     * 获取 RelativeLayout View 指定布局位置 View id
     * @param view {@link View}
     * @param verb 布局位置
     * @return 关联 View id
     */
    public static int getRule(final View view, final int verb) {
        if (view != null) {
            try {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return layoutParams.getRule(verb);
                } else {
                    return layoutParams.getRules()[verb];
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getRule");
            }
        }
        return 0;
    }

    // =

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addRules(final int verb, final View... views) {
        return addRules(verb, -1, views);
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @param views   View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addRules(final int verb, final int subject, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                addRule(views[i], verb, subject);
            }
            return true;
        }
        return false;
    }

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeRules(final int verb, final View... views) {
        if (views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                removeRule(views[i], verb);
            }
            return true;
        }
        return false;
    }

    // =============
    // = Animation =
    // =============

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link View}
     */
    public static View setAnimation(final View view, final Animation animation) {
        return AnimationUtils.setAnimation(view, animation);
    }

    /**
     * 获取动画
     * @param view {@link View}
     * @return {@link Animation}
     */
    public static Animation getAnimation(final View view) {
        return AnimationUtils.getAnimation(view);
    }

    /**
     * 清空动画
     * @param view {@link View}
     * @return {@link View}
     */
    public static View clearAnimation(final View view) {
        return AnimationUtils.clearAnimation(view);
    }

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link View}
     */
    public static View startAnimation(final View view, final Animation animation) {
        return AnimationUtils.startAnimation(view, animation);
    }

    /**
     * 启动动画
     * @param animation {@link Animation}
     * @param <T>       泛型
     * @return {@link Animation}
     */
    public static <T extends Animation> T startAnimation(final T animation) {
        return AnimationUtils.startAnimation(animation);
    }

    /**
     * 取消动画
     * @param view {@link View}
     * @return {@link Animation}
     */
    public static Animation cancelAnimation(final View view) {
        return AnimationUtils.cancelAnimation(view);
    }

    /**
     * 取消动画
     * @param animation {@link Animation}
     * @param <T>       泛型
     * @return {@link Animation}
     */
    public static <T extends Animation> T cancelAnimation(final T animation) {
        return AnimationUtils.cancelAnimation(animation);
    }

    // ========
    // = 背景 =
    // ========

    /**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     * @return {@link View}
     */
    public static View setBackground(final View view, final Drawable background) {
        if (view != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(background);
                } else {
                    view.setBackgroundDrawable(background);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackground");
            }
        }
        return view;
    }

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     * @return {@link View}
     */
    public static View setBackgroundColor(final View view, @ColorInt final int color) {
        if (view != null) {
            try {
                view.setBackgroundColor(color);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundColor");
            }
        }
        return view;
    }

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link View}
     */
    public static View setBackgroundResource(final View view, @DrawableRes final int resId) {
        if (view != null) {
            try {
                view.setBackgroundResource(resId);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundResource");
            }
        }
        return view;
    }

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setBackgroundTintList(final View view, final ColorStateList tint) {
        if (view != null) {
            try {
                view.setBackgroundTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundTintList");
            }
        }
        return view;
    }

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setBackgroundTintMode(final View view, final PorterDuff.Mode tintMode) {
        if (view != null) {
            try {
                view.setBackgroundTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundTintMode");
            }
        }
        return view;
    }

    // ========
    // = 前景 =
    // ========

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForeground(final View view, final Drawable foreground) {
        if (view != null) {
            try {
                view.setForeground(foreground);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForeground");
            }
        }
        return view;
    }

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForegroundGravity(final View view, final int gravity) {
        if (view != null) {
            try {
                view.setForegroundGravity(gravity);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundGravity");
            }
        }
        return view;
    }

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForegroundTintList(final View view, final ColorStateList tint) {
        if (view != null) {
            try {
                view.setForegroundTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundTintList");
            }
        }
        return view;
    }

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForegroundTintMode(final View view, final PorterDuff.Mode tintMode) {
        if (view != null) {
            try {
                view.setForegroundTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundTintMode");
            }
        }
        return view;
    }

    // ========
    // = 获取 =
    // ========

    /**
     * 获取 View 背景 Drawable
     * @param view {@link View}
     * @return 背景 Drawable
     */
    public static Drawable getBackground(final View view) {
        if (view != null) return view.getBackground();
        return null;
    }

    /**
     * 获取 View 背景着色颜色
     * @param view {@link View}
     * @return 背景着色颜色 {@link ColorStateList}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static ColorStateList getBackgroundTintList(final View view) {
        if (view != null) return view.getBackgroundTintList();
        return null;
    }

    /**
     * 获取 View 背景着色模式
     * @param view {@link View}
     * @return 背景着色模式 {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static PorterDuff.Mode getBackgroundTintMode(final View view) {
        if (view != null) return view.getBackgroundTintMode();
        return null;
    }

    // =

    /**
     * 获取 View 前景 Drawable
     * @param view {@link View}
     * @return 前景 Drawable
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Drawable getForeground(final View view) {
        if (view != null) return view.getForeground();
        return null;
    }

    /**
     * 获取 View 前景重心
     * @param view {@link View}
     * @return 前景重心 {@link Gravity}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getForegroundGravity(final View view) {
        if (view != null) return view.getForegroundGravity();
        return Gravity.FILL;
    }

    /**
     * 获取 View 前景着色颜色
     * @param view {@link View}
     * @return 前景着色颜色 {@link ColorStateList}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static ColorStateList getForegroundTintList(final View view) {
        if (view != null) return view.getForegroundTintList();
        return null;
    }

    /**
     * 获取 View 前景着色模式
     * @param view {@link View}
     * @return 前景着色模式 {@link PorterDuff.Mode}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static PorterDuff.Mode getForegroundTintMode(final View view) {
        if (view != null) return view.getForegroundTintMode();
        return null;
    }

    // ============
    // = 着色处理 =
    // ============

    /**
     * View 着色处理
     * @param view  {@link View}
     * @param color 颜色值
     * @return {@link View}
     */
    public static View setColorFilter(final View view, @ColorInt final int color) {
        return setColorFilter(view, getBackground(view), color);
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return {@link View}
     */
    public static View setColorFilter(final View view, final Drawable drawable, @ColorInt final int color) {
        try {
            setBackground(view, ImageUtils.setColorFilter(drawable, color));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setColorFilter");
        }
        return view;
    }

    // =

    /**
     * View 着色处理
     * @param view        {@link View}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link View}
     */
    public static View setColorFilter(final View view, final ColorFilter colorFilter) {
        return setColorFilter(view, getBackground(view), colorFilter);
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param view        {@link View}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link View}
     */
    public static View setColorFilter(final View view, final Drawable drawable, final ColorFilter colorFilter) {
        try {
            setBackground(view, ImageUtils.setColorFilter(drawable, colorFilter));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setColorFilter");
        }
        return view;
    }
}