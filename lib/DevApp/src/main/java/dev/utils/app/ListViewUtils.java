package dev.utils.app;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import dev.utils.LogPrintUtils;

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
     * 滑动到指定索引 ( 有滚动过程 )
     * @param view     {@link View}
     * @param position 索引
     * @param <T>      泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollToPosition(final T view, final int position) {
        if (view != null) {
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
    public static <T extends View> T scrollToPosition(final T view, final int position) {
        if (view != null) {
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

    // ==============
    // = 滑动到顶部 =
    // ==============

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

    // ==============
    // = 滑动到底部 =
    // ==============

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部)
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
                RecyclerView recyclerView = ((RecyclerView) view);
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter != null && adapter.getItemCount() > 0) {
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (view instanceof ListView) {
                ListView listView = ((ListView) view);
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0) {
                    listView.smoothScrollToPosition(listAdapter.getCount() - 1);
                }
            } else if (view instanceof GridView) {
                GridView gridView = ((GridView) view);
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
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部)
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
                RecyclerView recyclerView = ((RecyclerView) view);
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter != null && adapter.getItemCount() > 0) {
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (view instanceof ListView) {
                ListView listView = ((ListView) view);
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter != null && listAdapter.getCount() > 0) {
                    listView.setSelection(listAdapter.getCount() - 1);
                }
            } else if (view instanceof GridView) {
                GridView gridView = ((GridView) view);
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
     * 滚动到指定位置 ( 有滚动过程 ) - 相对于初始位置移动
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollTo(final T view, final int x, final int y) {
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
     * 滚动到指定位置 ( 有滚动过程 ) - 相对于上次移动的最后位置移动
     * @param view {@link View}
     * @param x    X 轴开始坐标
     * @param y    Y 轴开始坐标
     * @param <T>  泛型
     * @return {@link View}
     */
    public static <T extends View> T smoothScrollBy(final T view, final int x, final int y) {
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
    public static <T extends View> T fullScroll(final T view, final int direction) {
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

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // =============
    // = ViewUtils =
    // =============

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
     * @param view         {@link ViewGroup}
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @param <T>          泛型
     * @return {@link ViewGroup}
     */
    public static <T extends ViewGroup> T setDescendantFocusability(final T view, final int focusability) {
        try {
            if (view != null) view.setDescendantFocusability(focusability);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setDescendantFocusability");
        }
        return view;
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
}