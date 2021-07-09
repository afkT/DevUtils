package dev.utils.app;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import dev.utils.LogPrintUtils;

/**
 * detail: RecyclerView 工具类
 * @author Ttt
 * <pre>
 *     RecyclerView 截图使用 {@link CapturePictureUtils}
 *     RecyclerView 滑动使用 {@link ListViewUtils}
 * </pre>
 */
public final class RecyclerViewUtils {

    private RecyclerViewUtils() {
    }

    // 日志 TAG
    private static final String TAG = RecyclerViewUtils.class.getSimpleName();

    // ====================
    // = 获取 RecyclerView =
    // ====================

    /**
     * 获取 RecyclerView
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link RecyclerView}
     */
    public static <T extends RecyclerView> T getRecyclerView(final View view) {
        if (view != null) {
            try {
                return (T) view;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getRecyclerView");
            }
        }
        return null;
    }

    // =================
    // = LayoutManager =
    // =================

    /**
     * 设置 RecyclerView LayoutManager
     * @param view          {@link View}
     * @param layoutManager LayoutManager
     * @return {@link View}
     */
    public static View setLayoutManager(
            final View view,
            final RecyclerView.LayoutManager layoutManager
    ) {
        setLayoutManager(getRecyclerView(view), layoutManager);
        return view;
    }

    /**
     * 设置 RecyclerView LayoutManager
     * @param recyclerView  {@link RecyclerView}
     * @param layoutManager LayoutManager
     * @param <T>           泛型
     * @return {@link RecyclerView}
     */
    public static <T extends RecyclerView> T setLayoutManager(
            final T recyclerView,
            final RecyclerView.LayoutManager layoutManager
    ) {
        if (recyclerView != null && layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
        return recyclerView;
    }

    /**
     * 获取 RecyclerView LayoutManager
     * @param view {@link View}
     * @return LayoutManager
     */
    public static RecyclerView.LayoutManager getLayoutManager(final View view) {
        return getLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView LayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return LayoutManager
     */
    public static RecyclerView.LayoutManager getLayoutManager(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            return recyclerView.getLayoutManager();
        }
        return null;
    }

    // =

    /**
     * 获取 LinearLayoutManager
     * @param view {@link View}
     * @return {@link LinearLayoutManager}
     */
    public static LinearLayoutManager getLinearLayoutManager(final View view) {
        return getLinearLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 LinearLayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return {@link LinearLayoutManager}
     */
    public static LinearLayoutManager getLinearLayoutManager(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        return null;
    }

    /**
     * 获取 GridLayoutManager
     * @param view {@link View}
     * @return {@link GridLayoutManager}
     */
    public static GridLayoutManager getGridLayoutManager(final View view) {
        return getGridLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 GridLayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return {@link GridLayoutManager}
     */
    public static GridLayoutManager getGridLayoutManager(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof GridLayoutManager) {
            return (GridLayoutManager) layoutManager;
        }
        return null;
    }

    /**
     * 获取 StaggeredGridLayoutManager
     * @param view {@link View}
     * @return {@link StaggeredGridLayoutManager}
     */
    public static StaggeredGridLayoutManager getStaggeredGridLayoutManager(final View view) {
        return getStaggeredGridLayoutManager(getRecyclerView(view));
    }

    /**
     * 获取 StaggeredGridLayoutManager
     * @param recyclerView {@link RecyclerView}
     * @return {@link StaggeredGridLayoutManager}
     */
    public static StaggeredGridLayoutManager getStaggeredGridLayoutManager(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return (StaggeredGridLayoutManager) layoutManager;
        }
        return null;
    }

    // ===============
    // = Orientation =
    // ===============

    /**
     * 获取 RecyclerView Orientation
     * @param view {@link View}
     * @return Orientation
     */
    @RecyclerView.Orientation
    public static int getOrientation(final View view) {
        return getOrientation(getRecyclerView(view));
    }

    /**
     * 获取 RecyclerView Orientation
     * @param recyclerView {@link RecyclerView}
     * @return Orientation
     */
    @RecyclerView.Orientation
    public static int getOrientation(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager(recyclerView);
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return RecyclerView.VERTICAL;
    }

    // =

    /**
     * 校验 RecyclerView Orientation 是否为 HORIZONTAL
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollHorizontally(final View view) {
        return canScrollHorizontally(getRecyclerView(view));
    }

    /**
     * 校验 RecyclerView Orientation 是否为 HORIZONTAL
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollHorizontally(final RecyclerView recyclerView) {
        return getOrientation(recyclerView) == RecyclerView.HORIZONTAL;
    }

    /**
     * 校验 RecyclerView Orientation 是否为 VERTICAL
     * @param view {@link View}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollVertically(final View view) {
        return canScrollVertically(getRecyclerView(view));
    }

    /**
     * 校验 RecyclerView Orientation 是否为 VERTICAL
     * @param recyclerView {@link RecyclerView}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canScrollVertically(final RecyclerView recyclerView) {
        return getOrientation(recyclerView) == RecyclerView.VERTICAL;
    }
}