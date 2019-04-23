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
import android.widget.ImageView;
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
     * @param view
     * @return
     */
    public static Context getContext(final View view) {
        if (view != null) {
            return view.getContext();
        }
        return null;
    }

    /**
     * 获取 View
     * @param resource
     * @return
     */
    public static View getView(@LayoutRes final int resource) {
        return getView(resource, null);
    }

    /**
     * 获取View
     * @param resource
     * @param root
     * @return
     */
    public static View getView(@LayoutRes final int resource, final ViewGroup root) {
        try {
            return ((LayoutInflater) DevUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource, root);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getView");
        }
        return null;
    }

    // =

    /**
     * 判断View 是否为null
     * @param view
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final View view) {
        return view == null;
    }

    /**
     * 判断View 是否为null
     * @param views
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
     * 判断View 是否显示
     * @param view
     * @return
     */
    public static boolean isVisibility(final View view) {
        return isVisibility(view, true);
    }

    /**
     * 判断 View 是否显示
     * @param view
     * @param defaultValue
     * @return
     */
    public static boolean isVisibility(final View view, final boolean defaultValue) {
        if (view != null) {
            // 判断是否显示
            return (view.getVisibility() == View.VISIBLE);
        }
        // 出现意外返回默认值
        return defaultValue;
    }

    /**
     * 判断 View 是否都显示显示
     * @param views
     * @return
     */
    public static boolean isVisibilitys(final View... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                View view = views[i];
                if (view != null && view.getVisibility() == View.VISIBLE) {
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断View 是否隐藏占位
     * @param view
     * @return
     */
    public static boolean isVisibilityIN(final View view) {
        return isVisibilityIN(view, false);
    }

    /**
     * 判断View 是否隐藏占位
     * @param view
     * @param defaultValue
     * @return
     */
    public static boolean isVisibilityIN(final View view, final boolean defaultValue) {
        if (view != null) {
            // 判断是否显示
            return (view.getVisibility() == View.INVISIBLE);
        }
        // 出现意外返回默认值
        return defaultValue;
    }

    /**
     * 判断View 是否隐藏
     * @param view
     * @return
     */
    public static boolean isVisibilityGone(final View view) {
        return isVisibilityGone(view, false);
    }

    /**
     * 判断View 是否隐藏
     * @param view
     * @param defaultValue
     * @return
     */
    public static boolean isVisibilityGone(final View view, final boolean defaultValue) {
        if (view != null) {
            // 判断是否显示
            return (view.getVisibility() == View.GONE);
        }
        // 出现意外返回默认值
        return defaultValue;
    }

    // =

    /**
     * 获取显示的状态 (View.VISIBLE : View.GONE)
     * @param isVisibility
     * @return
     */
    public static int getVisibility(final boolean isVisibility) {
        return isVisibility ? View.VISIBLE : View.GONE;
    }

    /**
     * 获取显示的状态 (View.VISIBLE : View.INVISIBLE)
     * @param isVisibility
     * @return
     */
    public static int getVisibilityIN(final boolean isVisibility) {
        return isVisibility ? View.VISIBLE : View.INVISIBLE;
    }

    // =

    /**
     * 设置View显示状态
     * @param isVisibility
     * @param view
     */
    public static boolean setVisibility(final boolean isVisibility, final View view) {
        if (view != null) {
            view.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
        return isVisibility;
    }

    /**
     * 设置View显示的状态
     * @param isVisibility
     * @param view
     */
    public static boolean setVisibility(final int isVisibility, final View view) {
        if (view != null) {
            view.setVisibility(isVisibility);
        }
        return (isVisibility == View.VISIBLE);
    }

    // =

    /**
     * 设置View 显示的状态
     * @param isVisibility
     * @param views
     */
    public static boolean setVisibilitys(final boolean isVisibility, final View... views) {
        return setVisibilitys(getVisibility(isVisibility), views);
    }

    /**
     * 设置View 显示的状态
     * @param isVisibility
     * @param views
     */
    public static boolean setVisibilitys(final int isVisibility, final View... views) {
        if (views != null && views.length != 0) {
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
     * @param view
     * @param views
     */
    public static void toggleVisibilitys(final View view, final View... views) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        setVisibilitys(View.GONE, views);
    }

    /**
     * 切换 View 显示的状态
     * @param viewArys
     * @param views
     */
    public static void toggleVisibilitys(final View[] viewArys, final View... views) {
        toggleVisibilitys(View.GONE, viewArys, views);
    }

    /**
     * 切换 View 显示的状态
     * @param viewArys
     * @param status
     * @param views
     */
    public static void toggleVisibilitys(final int status, final View[] viewArys, final View... views) {
        // 默认前面显示
        setVisibilitys(View.VISIBLE, viewArys);
        // 根据状态处理
        setVisibilitys(status, views);
    }

    // =

    /**
     * 反转 View 显示的状态
     * @param status
     * @param viewArys
     * @param views
     */
    public static void reverseVisibilitys(final int status, final View[] viewArys, final View... views) {
        reverseVisibilitys(status == View.VISIBLE, viewArys, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility
     * @param viewArys
     * @param views
     */
    public static void reverseVisibilitys(final boolean isVisibility, final View[] viewArys, final View... views) {
        // 默认处理第一个数组
        setVisibilitys(isVisibility, viewArys);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
    }

    /**
     * 反转 View 显示的状态
     * @param status
     * @param view
     * @param views
     */
    public static void reverseVisibilitys(final int status, final View view, final View... views) {
        reverseVisibilitys(status == View.VISIBLE, view, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility
     * @param view
     * @param views
     */
    public static void reverseVisibilitys(final boolean isVisibility, final View view, final View... views) {
        // 默认处理第一个 View
        setVisibilitys(isVisibility, view);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
    }

    // =

    /**
     * 切换View状态
     * @param isChange     是否改变
     * @param isVisibility 是否显示
     * @param view         需要判断的View
     * @return
     */
    public static boolean toogleView(final boolean isChange, final int isVisibility, final View view) {
        if (isChange && view != null) {
            view.setVisibility(isVisibility);
        }
        return isChange;
    }

    // =

    /**
     * 设置View 图片资源
     * @param draw
     * @param views
     */
    public static void setViewImageRes(final int draw, final ImageView... views) {
        setViewImageRes(draw, View.VISIBLE, views);
    }

    /**
     * 设置View 图片资源
     * @param draw
     * @param isVisibility
     * @param views
     */
    public static void setViewImageRes(final int draw, final int isVisibility, final ImageView... views) {
        if (views != null && views.length != 0) {
            for (int i = 0, len = views.length; i < len; i++) {
                ImageView view = views[i];
                if (view != null) {
                    try {
                        // 设置背景
                        view.setImageResource(draw);
                        // 是否显示
                        view.setVisibility(isVisibility);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setViewImageRes");
                    }
                }
            }
        }
    }

    // ====================
    // = 初始化View操作等 =
    // ====================

    /**
     * 初始化View
     * @param view
     * @param id
     * @param <T>  泛型
     * @return
     */
    public static <T extends View> T findViewById(final View view, final int id) {
        try {
            return view.findViewById(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "findViewById");
        }
        return null;
    }

    /**
     * 初始化View
     * @param window
     * @param id
     * @param <T>    泛型
     * @return
     */
    public static <T extends View> T findViewById(final Window window, final int id) {
        try {
            return window.findViewById(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "findViewById");
        }
        return null;
    }

    /**
     * 初始化View
     * @param activity
     * @param id
     * @param <T>      泛型
     * @return
     */
    public static <T extends View> T findViewById(final Activity activity, final int id) {
        try {
            return activity.findViewById(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "findViewById");
        }
        return null;
    }

    /**
     * 把自身从父View中移除
     * @param view
     */
    public static void removeSelfFromParent(final View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 判断触点是否落在该View上
     * @param ev
     * @param view
     * @return
     */
    public static boolean isTouchInView(final MotionEvent ev, final View view) {
        int[] vLoc = new int[2];
        view.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + view.getWidth())
                && motionY >= vLoc[1] && motionY <= (vLoc[1] + view.getHeight());
    }

    /**
     * View 改变请求
     * @param view
     * @param isAll
     */
    public static void requestLayoutParent(final View view, final boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }

    /**
     * 测量 View
     * @param view
     */
    public static void measureView(final View view) {
        try {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
            int lpHeight = layoutParams.height;
            int heightSpec;
            if (lpHeight > 0) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
            } else {
                heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            }
            view.measure(widthSpec, heightSpec);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "measureView");
        }
    }

    /**
     * 获取view的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(final View view) {
        measureView(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获取view的高度
     * @param view
     * @return
     */
    public static int getViewHeight(final View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取 View 的 Context (Activity)
     * @param view
     * @return
     */
    public static Activity getActivity(final View view) {
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
        return null;
    }

    // ============
    // = ListView =
    // ============

    /**
     * 计算ListView Item 高度
     * @param listView
     */
    public static int calcListViewItemHeight(final ListView listView) {
        return calcListViewItemHeight(listView, false);
    }

    /**
     * 计算ListView Item 高度
     * @param listView
     * @param isSet    是否 setLayoutParams
     */
    public static int calcListViewItemHeight(final ListView listView, final boolean isSet) {
        // 获取 Adapter
        ListAdapter listAdapter = listView.getAdapter();
        // 防止为null
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
        if (isSet) {
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
     * 计算GridView Item 高度
     * @param gridView
     * @param numColumns
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int calcGridViewItemHeight(final GridView gridView, final int numColumns) {
        return calcGridViewItemHeight(gridView, numColumns, false);
    }

    /**
     * 计算GridView Item 高度
     * @param gridView
     * @param numColumns
     * @param isSet      是否 setLayoutParams
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int calcGridViewItemHeight(final GridView gridView, final int numColumns, final boolean isSet) {
        // 获取 Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        // 防止为null
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
        //int hSpaec = gridView.getHorizontalSpacing();
        // 每行之间的间隔 -
        int vSpace = gridView.getVerticalSpacing();
        // 最后获取整个gridView完整显示需要的高度
        totalHeight += (vSpace * (count - 1));
        // 判断是否需要设置高度
        if (isSet) {
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = totalHeight;
            gridView.setLayoutParams(params);
        }
        // 返回总体高度
        return totalHeight;
    }

    // =

    /**
     * 获取单独一个Item 高度
     * @param absViews
     * @param pos
     * @return
     */
    public static int getItemHeighet(final AbsListView absViews, final int pos) {
        if (absViews != null) {
            return getItemHeighet(absViews.getAdapter(), absViews, pos, 0);
        }
        return 0;
    }

    /**
     * 获取单独一个Item 高度
     * @param absViews
     * @param pos
     * @param defaultHeight
     * @return
     */
    public static int getItemHeighet(final AbsListView absViews, final int pos, final int defaultHeight) {
        if (absViews != null) {
            return getItemHeighet(absViews.getAdapter(), absViews, pos, defaultHeight);
        }
        return defaultHeight;
    }

    /**
     * 获取单独一个Item 高度
     * @param listAdapter
     * @param absViews
     * @param pos
     * @return
     */
    public static int getItemHeighet(final ListAdapter listAdapter, final AbsListView absViews, final int pos) {
        return getItemHeighet(listAdapter, absViews, pos, 0);
    }

    /**
     * 获取单独一个Item 高度
     * @param listAdapter
     * @param absViews
     * @param pos
     * @param defaultHeight
     * @return
     */
    public static int getItemHeighet(final ListAdapter listAdapter, final AbsListView absViews, final int pos, final int defaultHeight) {
        try {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(pos, null, absViews);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            return listItem.getMeasuredHeight();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getItemHeighet");
            return defaultHeight;
        }
    }
}
