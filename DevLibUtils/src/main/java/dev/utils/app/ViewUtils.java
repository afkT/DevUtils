package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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

import dev.utils.LogPrintUtils;

/**
 * detail: View 操作相关工具类
 * Created by Ttt
 */
public final class ViewUtils {

    private ViewUtils() {
    }

    // 日志TAG
    private static final String TAG = ViewUtils.class.getSimpleName();

    /**
     * 获取上下文
     * @param view
     * @return
     */
    public static Context getContext(View view){
        if (view != null){
            return view.getContext();
        }
        return null;
    }

    /**
     * 判断View 是否为null
     * @param view
     * @return
     */
    public static boolean isEmpty(View view){
        return view == null;
    }

    /**
     * 判断View 是否为null
     * @param views
     * @return
     */
    public static boolean isEmpty(View... views){
        if (views != null && views.length != 0){
            for (int i = 0, len = views.length; i < len; i++){
                View view = views[i];
                if (view == null){
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
    public static boolean isVisibility(View view){
        return isVisibility(view, true);
    }

    /**
     * 判断 View 是否显示
     * @param view
     * @param isDf
     * @return
     */
    public static boolean isVisibility(View view, boolean isDf){
        if (view != null){
            // 判断是否显示
            return (view.getVisibility() == View.VISIBLE);
        }
        // 出现意外返回默认值
        return isDf;
    }

    /**
     * 判断 View 是否都显示显示
     * @param views
     * @return
     */
    public static boolean isVisibilitys(View... views){
        if (views != null && views.length != 0){
            for (int i = 0, len = views.length; i < len; i++){
                View view = views[i];
                if (view != null && view.getVisibility() == View.VISIBLE){
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
    public static boolean isVisibilityIN(View view){
        return isVisibilityIN(view, false);
    }

    /**
     * 判断View 是否隐藏占位
     * @param view
     * @param isDf
     * @return
     */
    public static boolean isVisibilityIN(View view, boolean isDf){
        if (view != null){
            // 判断是否显示
            return (view.getVisibility() == View.INVISIBLE);
        }
        // 出现意外返回默认值
        return isDf;
    }

    /**
     * 判断View 是否隐藏
     * @param view
     * @return
     */
    public static boolean isVisibilityGone(View view){
        return isVisibilityGone(view, false);
    }

    /**
     * 判断View 是否隐藏
     * @param view
     * @param isDf
     * @return
     */
    public static boolean isVisibilityGone(View view, boolean isDf){
        if (view != null){
            // 判断是否显示
            return (view.getVisibility() == View.GONE);
        }
        // 出现意外返回默认值
        return isDf;
    }

    // ==

    /**
     * 获取显示的状态 (View.VISIBLE : View.GONE)
     * @param isVisibility
     * @return
     */
    public static int getVisibility(boolean isVisibility){
        return isVisibility ? View.VISIBLE : View.GONE;
    }

    /**
     * 获取显示的状态 (View.VISIBLE : View.INVISIBLE)
     * @param isVisibility
     * @return
     */
    public static int getVisibilityIN(boolean isVisibility){
        return isVisibility ? View.VISIBLE : View.INVISIBLE;
    }

    // --

    /**
     * 设置View显示状态
     * @param isVisibility
     * @param view
     */
    public static boolean setVisibility(boolean isVisibility, View view){
        if (view != null){
            view.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
        return isVisibility;
    }

    /**
     * 设置View显示的状态
     * @param isVisibility
     * @param view
     */
    public static boolean setVisibility(int isVisibility, View view){
        if (view != null){
            view.setVisibility(isVisibility);
        }
        return (isVisibility == View.VISIBLE);
    }

    // --

    /**
     * 设置View 显示的状态
     * @param isVisibility
     * @param views
     */
    public static boolean setVisibilitys(boolean isVisibility, View... views){
        return setVisibilitys(getVisibility(isVisibility), views);
    }

    /**
     * 设置View 显示的状态
     * @param isVisibility
     * @param views
     */
    public static boolean setVisibilitys(int isVisibility, View... views){
        if (views != null && views.length != 0){
            for (int i = 0, len = views.length; i < len; i++){
                View view = views[i];
                if (view != null){
                    view.setVisibility(isVisibility);
                }
            }
        }
        return (isVisibility == View.VISIBLE);
    }

    /**
     * 切换View 显示的状态
     * @param view
     * @param views
     */
    public static void toggleVisibilitys(View view, View... views){
        if (view != null){
            view.setVisibility(View.VISIBLE);
        }
        setVisibilitys(View.GONE, views);
    }

    /**
     * 切换View 显示的状态
     * @param viewArys
     * @param views
     */
    public static void toggleVisibilitys(View[] viewArys, View... views){
        toggleVisibilitys(viewArys, View.GONE, views);
    }

    /**
     * 切换View 显示的状态
     * @param viewArys
     * @param status
     * @param views
     */
    public static void toggleVisibilitys(View[] viewArys, int status, View... views){
        // 默认前面显示
        setVisibilitys(View.VISIBLE, viewArys);
        // 更具状态处理
        setVisibilitys(status, views);
    }

    // ==

    /**
     * 切换View状态
     * @param isChange 是否改变
     * @param isVisibility 是否显示
     * @param view 需要判断的View
     * @return
     */
    public static boolean toogleView(boolean isChange, int isVisibility, View view){
        if (isChange && view != null){
            view.setVisibility(isVisibility);
        }
        return isChange;
    }

    // --

    /**
     * 设置View 图片资源
     * @param draw
     * @param views
     */
    public static void setViewImageRes(int draw, ImageView... views){
        setViewImageRes(draw, View.VISIBLE, views);
    }

    /**
     * 设置View 图片资源
     * @param draw
     * @param isVisibility
     * @param views
     */
    public static void setViewImageRes(int draw, int isVisibility, ImageView... views){
        if (views != null && views.length != 0){
            for (int i = 0, len = views.length; i < len; i++){
                ImageView view = views[i];
                if (view != null){
                    try {
                        // 设置背景
                        view.setImageResource(draw);
                        // 是否显示
                        view.setVisibility(isVisibility);
                    } catch (Exception e){
                        LogPrintUtils.eTag(TAG, e, "setViewImageRes");
                    }
                }
            }
        }
    }

    // == 初始化View操作等 ==

    /**
     * 初始化View
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(View view, int id) {
        return view.findViewById(id);
    }

    /**
     * 初始化View
     * @param window
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(Window window, int id){
        return window.findViewById(id);
    }

    /**
     * 初始化View
     * @param activity
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(Activity activity, int id) {
        return activity.findViewById(id);
    }

    /**
     * 把自身从父View中移除
     * @param view
     */
    public static void removeSelfFromParent(View view) {
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
     * @param v
     * @return
     */
    public static boolean isTouchInView(MotionEvent ev, View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth())
                && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
    }

    /**
     * View 改变请求
     * @param view
     * @param isAll
     */
    public static void requestLayoutParent(View view, boolean isAll) {
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
     * 测量 view
     * @param view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 获取view的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        measureView(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获取view的高度
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取view的上下文
     * @param view
     * @return
     */
    public static Activity getActivity(View view) {
        try {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getActivity");
        }
        return null;
    }

    // === ListView ===

    /**
     * 计算ListView Item 高度
     * @param listView
     */
    public static int calcListViewItemHeight(ListView listView) {
        return calcListViewItemHeight(listView, false);
    }

    /**
     * 计算ListView Item 高度
     * @param listView
     * @param isSet 是否 setLayoutParams
     * ==
     * hint: 解决 ScrollView 嵌套 ListView 时, 会无法正确的计算ListView的大小
     */
    public static int calcListViewItemHeight(ListView listView, boolean isSet) {
        // 获取 Adapter
        ListAdapter listAdapter = listView.getAdapter();
        // 防止为null
        if (listAdapter == null) {
            return 0 ;
        }
        // 获取总体高度
        int totalHeight = 0;
        // 遍历累加
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // 累加 Item 高度
            totalHeight += getItemHeighet(listAdapter, listView, i);
        }
        // ==
        // 获取子项间分隔符占用的高度
        // listView.getDividerHeight();
        // 累加分割线高度
        totalHeight += (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // 判断是否需要设置高度
        if (isSet){
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight;
            listView.setLayoutParams(params);
        }
        // 返回总体高度
        return totalHeight;
    }

    // === GridView ===

    /**
     * 计算GridView Item 高度
     * @param gridView
     * @param numColumns
     */
    public static int calcGridViewItemHeight(GridView gridView, int numColumns) {
        return calcGridViewItemHeight(gridView, numColumns, false);
    }

    /**
     * 计算GridView Item 高度
     * @param gridView
     * @param numColumns
     * @param isSet 是否 setLayoutParams
     * ==
     * hint: 解决 ScrollView 嵌套 GridView 时, 会无法正确的计算ListView的大小
     */
    public static int calcGridViewItemHeight(GridView gridView, int numColumns, boolean isSet) {
        // 获取 Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        // 防止为null
        if (listAdapter == null) {
            return 0 ;
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
            for (int eqI = 1; eqI < numColumns; eqI++){
                // 临时高度
                int tempHeight = 0;
                // 进行判断处理
                if (i * numColumns + eqI <= count){
                    // 获取对应的高度
                    tempHeight = getItemHeighet(listAdapter, gridView, i * numColumns + eqI);
                }
                // 判断是否在最大高度
                if (tempHeight > singleMax){
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
        if (isSet){
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = totalHeight;
            gridView.setLayoutParams(params);
        }
        // 返回总体高度
        return totalHeight;
    }

    // ==

    /**
     * 获取单独一个Item 高度
     * @param absViews
     * @param pos
     * @return
     */
    public static int getItemHeighet(AbsListView absViews, int pos){
        if (absViews != null){
            return getItemHeighet(absViews.getAdapter(), absViews, pos, 0);
        }
        return 0;
    }

    /**
     * 获取单独一个Item 高度
     * @param absViews
     * @param pos
     * @param dfHeight
     * @return
     */
    public static int getItemHeighet(AbsListView absViews, int pos, int dfHeight){
        if (absViews != null){
            return getItemHeighet(absViews.getAdapter(), absViews, pos, dfHeight);
        }
        return dfHeight;
    }

    /**
     * 获取单独一个Item 高度
     * @param listAdapter
     * @param absViews
     * @param pos
     * @return
     */
    public static int getItemHeighet(ListAdapter listAdapter, AbsListView absViews, int pos){
        return getItemHeighet(listAdapter, absViews, pos, 0);
    }

    /**
     * 获取单独一个Item 高度
     * @param listAdapter
     * @param absViews
     * @param pos
     * @param dfHeight
     * @return
     */
    public static int getItemHeighet(ListAdapter listAdapter, AbsListView absViews, int pos, int dfHeight){
        try {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(pos, null, absViews);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            return listItem.getMeasuredHeight();
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "getItemHeighet");
            return dfHeight;
        }
    }
}
