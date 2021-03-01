package dev.utils.app;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import dev.utils.LogPrintUtils;
import dev.utils.common.NumberUtils;

/**
 * detail: List View ( 列表 View ) 相关工具类
 * @author Ttt
 * <pre>
 *     Fading Edge 让你的 View 更有层次感
 *     @see <a href="https://blog.csdn.net/u012702547/article/details/52913538"/>
 *     <p></p>
 *     android:descendantFocusability="blocksDescendants"
 *     android:overScrollMode="never"
 *     android:scrollbars="none"
 *     <p></p>
 *     如果滑动到指定位置不准确可使用, 进行偏移滑动
 *     LayoutManager.scrollToPositionWithOffset(position, 0);
 * </pre>
 */
public final class ListViewUtils {

    private ListViewUtils() {
    }

    // 日志 TAG
    private static final String TAG = ListViewUtils.class.getSimpleName();

    // =============
    // = List View =
    // =============

    /**
     * 获取 Adapter Item 总数
     * @param view {@link View}
     * @return Adapter Item 总数
     */
    public static int getItemCount(final View view) {
        if (view != null) {
            if (view instanceof RecyclerView) {
                RecyclerView         recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter      = recyclerView.getAdapter();
                return (adapter != null) ? adapter.getItemCount() : 0;
            } else if (view instanceof ListView) {
                ListView    listView    = (ListView) view;
                ListAdapter listAdapter = listView.getAdapter();
                return (listAdapter != null) ? listAdapter.getCount() : 0;
            } else if (view instanceof GridView) {
                GridView    gridView    = (GridView) view;
                ListAdapter listAdapter = gridView.getAdapter();
                return (listAdapter != null) ? listAdapter.getCount() : 0;
            }
        }
        return 0;
    }

    /**
     * 获取指定索引 Item View
     * @param view     {@link View}
     * @param position 索引
     * @return {@link View}
     */
    public static View getItemView(
            final View view,
            final int position
    ) {
        if (view != null && position >= 0) {
            if (view instanceof RecyclerView) {
                RecyclerView         recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter      = recyclerView.getAdapter();
                if (adapter != null && adapter.getItemCount() > 0 && position < adapter.getItemCount()) {
                    try {
                        RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(position));
                        adapter.onBindViewHolder(holder, position);
                        return holder.itemView;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "getItemView - RecyclerView");
                    }
                }
            } else if (view instanceof ListView) {
                ListView    listView    = (ListView) view;
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0 && position < listAdapter.getCount()) {
                    try {
                        return listAdapter.getView(position, null, listView);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "getItemView - ListView");
                    }
                }
            } else if (view instanceof GridView) {
                GridView    gridView    = (GridView) view;
                ListAdapter listAdapter = gridView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0 && position < listAdapter.getCount()) {
                    try {
                        return listAdapter.getView(position, null, gridView);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "getItemView - GridView");
                    }
                }
            }
        }
        return null;
    }

    // ========
    // = 滑动 =
    // ========

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollToPosition(
            final T view,
            final int position
    ) {
        if (view != null && position >= 0) {
            if (view instanceof RecyclerView) {
                ((RecyclerView) view).smoothScrollToPosition(position);
            } else if (view instanceof ListView) {
                ((ListView) view).smoothScrollToPosition(position);
            } else if (view instanceof GridView) {
                ((GridView) view).smoothScrollToPosition(position);
            }
        }
        return view;
    }

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends View> T scrollToPosition(
            final T view,
            final int position
    ) {
        if (view != null && position >= 0) {
            if (view instanceof RecyclerView) {
                ((RecyclerView) view).scrollToPosition(position);
            } else if (view instanceof ListView) {
                ((ListView) view).setSelection(position);
            } else if (view instanceof GridView) {
                ((GridView) view).setSelection(position);
            }
        }
        return view;
    }

    // =============
    // = 滑动到顶部 =
    // =============

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollToTop(final T view) {
        if (view != null) {
            if (view instanceof RecyclerView || view instanceof ListView || view instanceof GridView) {
                smoothScrollToPosition(view, 0);
            } else { // 其他 View
                smoothScrollTo(view, 0, 0);
            }
        }
        return view;
    }

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T scrollToTop(final T view) {
        if (view != null) {
            if (view instanceof RecyclerView || view instanceof ListView || view instanceof GridView) {
                scrollToPosition(view, 0);
            } else { // 其他 View
                scrollTo(view, 0, 0);
            }
        }
        return view;
    }

    // =============
    // = 滑动到底部 =
    // =============

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部 )
     *     smoothScrollToBottom(view)
     *     smoothScrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollToBottom(final T view) {
        if (view != null) {
            if (view instanceof RecyclerView) {
                RecyclerView         recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter      = recyclerView.getAdapter();
                if (adapter != null && adapter.getItemCount() > 0) {
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (view instanceof ListView) {
                ListView    listView    = (ListView) view;
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0) {
                    listView.smoothScrollToPosition(listAdapter.getCount() - 1);
                }
            } else if (view instanceof GridView) {
                GridView    gridView    = (GridView) view;
                ListAdapter listAdapter = gridView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0) {
                    gridView.smoothScrollToPosition(listAdapter.getCount() - 1);
                }
            } else { // 其他 View
                fullScroll(view, View.FOCUS_DOWN);
            }
        }
        return view;
    }

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T scrollToBottom(final T view) {
        if (view != null) {
            if (view instanceof RecyclerView) {
                RecyclerView         recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter      = recyclerView.getAdapter();
                if (adapter != null && adapter.getItemCount() > 0) {
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (view instanceof ListView) {
                ListView    listView    = (ListView) view;
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0) {
                    listView.setSelection(listAdapter.getCount() - 1);
                }
            } else if (view instanceof GridView) {
                GridView    gridView    = (GridView) view;
                ListAdapter listAdapter = gridView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0) {
                    gridView.setSelection(listAdapter.getCount() - 1);
                }
            } else { // 其他 View
                fullScroll(view, View.FOCUS_DOWN);
            }
        }
        return view;
    }

    // ==============
    // = ScrollView =
    // ==============

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollTo(
            final T view,
            final int x,
            final int y
    ) {
        if (view != null) {
            if (view instanceof NestedScrollView) {
                ((NestedScrollView) view).smoothScrollTo(x, y);
            } else if (view instanceof ScrollView) {
                ((ScrollView) view).smoothScrollTo(x, y);
            } else if (view instanceof HorizontalScrollView) {
                ((HorizontalScrollView) view).smoothScrollTo(x, y);
            }
        }
        return view;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollBy(
            final T view,
            final int x,
            final int y
    ) {
        if (view != null) {
            if (view instanceof NestedScrollView) {
                ((NestedScrollView) view).smoothScrollBy(x, y);
            } else if (view instanceof ScrollView) {
                ((ScrollView) view).smoothScrollBy(x, y);
            } else if (view instanceof HorizontalScrollView) {
                ((HorizontalScrollView) view).smoothScrollBy(x, y);
            } else if (view instanceof RecyclerView) {
                ((RecyclerView) view).smoothScrollBy(x, y);
            } else if (view instanceof ListView) {
                ((ListView) view).smoothScrollBy(y, 200); // PositionScroller.SCROLL_DURATION
            } else if (view instanceof GridView) {
                ((GridView) view).smoothScrollBy(y, 200);
            }
        }
        return view;
    }

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param view      {@link View}
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @param <T>       泛型
     * @return {@link View}
     */
    public static <T extends View> T fullScroll(
            final T view,
            final int direction
    ) {
        if (view != null) {
            if (view instanceof NestedScrollView) {
                ((NestedScrollView) view).fullScroll(direction);
            } else if (view instanceof ScrollView) {
                ((ScrollView) view).fullScroll(direction);
            } else if (view instanceof HorizontalScrollView) {
                ((HorizontalScrollView) view).fullScroll(direction);
            }
        }
        return view;
    }

    // =============
    // = ViewUtils =
    // =============

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link View}
     */
    public static View scrollTo(
            final View view,
            final int x,
            final int y
    ) {
        return ViewUtils.scrollTo(view, x, y);
    }

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @return {@link View}
     */
    public static View scrollBy(
            final View view,
            final int x,
            final int y
    ) {
        return ViewUtils.scrollBy(view, x, y);
    }

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param view  {@link View}
     * @param value X 轴坐标
     * @return {@link View}
     */
    public static View setScrollX(
            final View view,
            final int value
    ) {
        return ViewUtils.setScrollX(view, value);
    }

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param view  {@link View}
     * @param value Y 轴坐标
     * @return {@link View}
     */
    public static View setScrollY(
            final View view,
            final int value
    ) {
        return ViewUtils.setScrollY(view, value);
    }

    /**
     * 获取 View 滑动的 X 轴坐标
     * @param view {@link View}
     * @return 滑动的 X 轴坐标
     */
    public static int getScrollX(final View view) {
        return ViewUtils.getScrollX(view);
    }

    /**
     * 获取 View 滑动的 Y 轴坐标
     * @param view {@link View}
     * @return 滑动的 Y 轴坐标
     */
    public static int getScrollY(final View view) {
        return ViewUtils.getScrollY(view);
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
     * @param view         {@link ViewGroup}
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @param <T>          泛型
     * @return {@link ViewGroup}
     */
    public static <T extends ViewGroup> T setDescendantFocusability(
            final T view,
            final int focusability
    ) {
        return ViewUtils.setDescendantFocusability(view, focusability);
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
    public static <T extends View> T setOverScrollMode(
            final T view,
            final int overScrollMode
    ) {
        return ViewUtils.setOverScrollMode(view, overScrollMode);
    }

    // ===========
    // = 计算高度 =
    // ===========

    // ============
    // = ListView =
    // ============

    /**
     * 计算 ListView 高度
     * @param listView {@link ListView}
     * @return ListView 高度
     */
    public static int calcListViewHeight(final ListView listView) {
        return calcListViewHeight(listView, false);
    }

    /**
     * 计算 ListView 高度
     * @param listView  {@link ListView}
     * @param setParams 是否 setLayoutParams
     * @return ListView 高度
     */
    public static int calcListViewHeight(
            final ListView listView,
            final boolean setParams
    ) {
        if (listView == null) return 0;
        try {
            // Adapter
            ListAdapter listAdapter = listView.getAdapter();
            // Item 总条数
            int itemCount = listAdapter.getCount();
            // 没数据则直接跳过
            if (itemCount == 0) return 0;
            // 高度
            int height = 0;
            // 获取子项间分隔符占用的高度
            int dividerHeight = listView.getDividerHeight();

            // 循环绘制每个 Item 并保存 Bitmap
            for (int i = 0; i < itemCount; i++) {
                View childView = listAdapter.getView(i, null, listView);
                WidgetUtils.measureView(childView, listView.getWidth());
                height += childView.getMeasuredHeight();
            }
            // 追加子项间分隔符占用的高度
            height += (dividerHeight * (itemCount - 1));

            // 是否需要设置高度
            if (setParams) {
                ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
                layoutParams.height = height;
                listView.setLayoutParams(layoutParams);
            }
            return height;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "calcListViewHeight");
        }
        return 0;
    }

    // ============
    // = GridView =
    // ============

    /**
     * 计算 GridView 高度
     * @param gridView {@link GridView}
     * @return GridView 高度
     */
    public static int calcGridViewHeight(final GridView gridView) {
        return calcGridViewHeight(gridView, false);
    }

    /**
     * 计算 GridView 高度
     * @param gridView  {@link GridView}
     * @param setParams 是否 setLayoutParams
     * @return GridView 高度
     */
    public static int calcGridViewHeight(
            final GridView gridView,
            final boolean setParams
    ) {
        if (gridView == null) return 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return 0;
        try {
            // Adapter
            ListAdapter listAdapter = gridView.getAdapter();
            // Item 总条数
            int itemCount = listAdapter.getCount();
            // 没数据则直接跳过
            if (itemCount == 0) return 0;
            // 高度
            int height = 0;
            // 获取一共多少列
            int numColumns = gridView.getNumColumns();
            // 每列之间的间隔 |
            int horizontalSpacing = gridView.getHorizontalSpacing();
            // 每行之间的间隔 -
            int verticalSpacing = gridView.getVerticalSpacing();
            // 获取倍数 ( 行数 )
            int lineNumber = NumberUtils.getMultiple(itemCount, numColumns);
            // 计算总共的宽度 (GridView 宽度 - 列分割间距 ) / numColumns
            int childWidth = (gridView.getWidth() - (numColumns - 1) * horizontalSpacing) / numColumns;

            // 记录每行最大高度
            int[] rowHeightArrays = new int[lineNumber];
            // 临时高度 ( 保存行中最高的列高度 )
            int tempHeight;
            // 循环每一行绘制每个 Item 并保存 Bitmap
            for (int i = 0; i < lineNumber; i++) {
                // 清空高度
                tempHeight = 0;
                // 循环列数
                for (int j = 0; j < numColumns; j++) {
                    // 获取对应的索引
                    int position = i * numColumns + j;
                    // 如果大于总数据则跳过
                    if (position < itemCount) {
                        View childView = listAdapter.getView(position, null, gridView);
                        WidgetUtils.measureView(childView, childWidth);

                        int itemHeight = childView.getMeasuredHeight();
                        // 保留最大高度
                        tempHeight = Math.max(itemHeight, tempHeight);
                    }

                    // 记录高度并累加
                    if (j == numColumns - 1) {
                        height += tempHeight;
                        rowHeightArrays[i] = tempHeight;
                    }
                }
            }
            // 追加子项间分隔符占用的高度
            height += (verticalSpacing * (lineNumber - 1));

            // 是否需要设置高度
            if (setParams) {
                ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                layoutParams.height = height;
                gridView.setLayoutParams(layoutParams);
            }
            return height;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "calcGridViewHeight");
        }
        return 0;
    }
}