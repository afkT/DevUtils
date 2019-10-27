package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 截图工具类
 * @author Ttt
 * <pre>
 *     截图
 *     @see <a href="https://www.cnblogs.com/angel88/p/7933437.html"/>
 *     WebView 截长图解决方案
 *     @see <a href="https://www.jianshu.com/p/0faa70e88441"/>
 *     X5 WebView 使用 snapshotWholePage 方法清晰截图
 *     @see <a href="https://www.v2ex.com/t/583020"/>
 * </pre>
 */
public final class CapturePictureUtils {

    private CapturePictureUtils() {
    }

    // 日志 TAG
    private static final String TAG = CapturePictureUtils.class.getSimpleName();
    // Bitmap Config
    private static Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    // Canvas 背景色
    private static int BACKGROUND_COLOR = Color.TRANSPARENT;
    // 画笔
    private static Paint PAINT = new Paint();

    // ========
    // = 截图 =
    // ========

    // ============
    // = Activity =
    // ============

    /**
     * 获取当前屏幕截图, 包含状态栏 ( 顶部灰色 TitleBar 高度, 没有设置 android:theme 的 NoTitleBar 时会显示 )
     * @param activity {@link Activity}
     * @return 当前屏幕截图, 包含状态栏
     */
    public static Bitmap snapshotWithStatusBar(final Activity activity) {
        try {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            // 重新创建绘图缓存, 此时的背景色是黑色
            view.buildDrawingCache();
            // 获取绘图缓存, 注意这里得到的只是一个图像的引用
            Bitmap cacheBitmap = view.getDrawingCache();
            if (cacheBitmap == null) return null;
            // 获取屏幕宽度
            int[] widthHeight = getScreenWidthHeight();

            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            // 创建新的图片
            Bitmap bitmap = Bitmap.createBitmap(cacheBitmap, 0, 0, widthHeight[0], widthHeight[1]);
            // 释放绘图资源所使用的缓存
            view.destroyDrawingCache();
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotWithStatusBar");
        }
        return null;
    }

    /**
     * 获取当前屏幕截图, 不包含状态栏 ( 如果 android:theme 全屏, 则截图无状态栏 )
     * @param activity {@link Activity}
     * @return 当前屏幕截图, 不包含状态栏
     */
    public static Bitmap snapshotWithoutStatusBar(final Activity activity) {
        try {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            // 重新创建绘图缓存, 此时的背景色是黑色
            view.buildDrawingCache();
            // 获取绘图缓存, 注意这里得到的只是一个图像的引用
            Bitmap cacheBitmap = view.getDrawingCache();
            if (cacheBitmap == null) return null;
            // 获取屏幕宽度
            int[] widthHeight = getScreenWidthHeight();
            // 获取状态栏高度
            int statusBarHeight = getStatusBarHeight(activity);

            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            // 创建新的图片
            Bitmap bitmap = Bitmap.createBitmap(cacheBitmap, 0, statusBarHeight, widthHeight[0], widthHeight[1] - statusBarHeight);
            // 释放绘图资源所使用的缓存
            view.destroyDrawingCache();
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotWithoutStatusBar");
        }
        return null;
    }

    // ===========
    // = WebView =
    // ===========

    /**
     * 关闭 WebView 优化
     * <pre>
     *     推荐在 setContentView 前调用
     *     {@link CapturePictureUtils#snapshotByWebView}
     * </pre>
     */
    public static void enableSlowWholeDocumentDraw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
    }

    /**
     * 截图 WebView
     * @param webView {@link WebView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView) {
        return snapshotByWebView(webView, Integer.MAX_VALUE, BITMAP_CONFIG, 0f);
    }

    /**
     * 截图 WebView
     * @param webView   {@link WebView}
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView, final int maxHeight) {
        return snapshotByWebView(webView, maxHeight, BITMAP_CONFIG, 0f);
    }

    /**
     * 截图 WebView
     * @param webView {@link WebView}
     * @param scale   缩放比例
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView, final float scale) {
        return snapshotByWebView(webView, Integer.MAX_VALUE, BITMAP_CONFIG, scale);
    }

    /**
     * 截图 WebView
     * @param webView   {@link WebView}
     * @param maxHeight 最大高度
     * @param config    {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView, final int maxHeight, final Bitmap.Config config) {
        return snapshotByWebView(webView, maxHeight, config, 0f);
    }

    /**
     * 截图 WebView
     * <pre>
     *     TODO 在 Android 5.0 及以上版本, Android 对 WebView 进行了优化, 为了减少内存使用和提高性能
     *     TODO 使用 WebView 加载网页时只绘制显示部分, 如果我们不做处理, 就会出现只截到屏幕内显示的 WebView 内容, 其它部分是空白的情况
     *     TODO 通过调用 WebView.enableSlowWholeDocumentDraw() 方法可以关闭这种优化, 但要注意的是, 该方法需要在 WebView 实例被创建前就要调用,
     *     TODO 否则没有效果, 所以我们在 WebView 实例被创建前加入代码
     *     {@link CapturePictureUtils#enableSlowWholeDocumentDraw}
     * </pre>
     * @param webView   {@link WebView}
     * @param maxHeight 最大高度
     * @param config    {@link Bitmap.Config}
     * @param scale     缩放比例
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView, final int maxHeight,
                                           final Bitmap.Config config, final float scale) {
        if (webView != null && config != null) {
            // Android 5.0 以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    float newScale = scale;
                    if (newScale <= 0) {
                        // 该方法已抛弃, 可通过 setWebViewClient
                        // onScaleChanged(WebView view, float oldScale, float newScale)
                        // 存储并传入 newScale
                        newScale = webView.getScale();
                    }
                    int width = webView.getWidth();
                    int height = (int) (webView.getContentHeight() * newScale + 0.5);
                    // 重新设置高度
                    height = (height > maxHeight) ? maxHeight : height;
                    // 创建位图
                    Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawColor(BACKGROUND_COLOR);
                    webView.draw(canvas);
                    return bitmap;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "snapshotByWebView - SDK_INT >= 21(5.0)");
                }
            } else {
                try {
                    Picture picture = webView.capturePicture();
                    int width = picture.getWidth();
                    int height = picture.getHeight();
                    if (width > 0 && height > 0) {
                        // 重新设置高度
                        height = (height > maxHeight) ? maxHeight : height;
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawColor(BACKGROUND_COLOR);
                        picture.draw(canvas);
                        return bitmap;
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "snapshotByWebView - SDK_INT < 21(5.0)");
                }
            }
        }
        return null;
    }

    // ========
    // = View =
    // ========

    /**
     * 通过 View 绘制为 Bitmap
     * @param view {@link View}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByView(final View view) {
        return snapshotByView(view, BITMAP_CONFIG);
    }

    /**
     * 通过 View 绘制为 Bitmap
     * @param view   {@link View}
     * @param config {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByView(final View view, final Bitmap.Config config) {
        if (view == null || config == null) return null;
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), config);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(BACKGROUND_COLOR);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByView");
        }
        return null;
    }

    /**
     * 通过 View Cache 绘制为 Bitmap
     * @param view {@link View}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByViewCache(final View view) {
        if (view == null) return null;
        try {
            // 清除视图焦点
            view.clearFocus();
            // 将视图设为不可点击
            view.setPressed(false);

            // 获取视图是否可以保存画图缓存
            boolean willNotCache = view.willNotCacheDrawing();
            view.setWillNotCacheDrawing(false);

            // 获取绘制缓存位图的背景颜色
            int color = view.getDrawingCacheBackgroundColor();
            // 设置绘图背景颜色
            view.setDrawingCacheBackgroundColor(0);
            if (color != 0) { // 获取的背景不是黑色的则释放以前的绘图缓存
                view.destroyDrawingCache(); // 释放绘图资源所使用的缓存
            }

            // 重新创建绘图缓存, 此时的背景色是黑色
            view.buildDrawingCache();
            // 获取绘图缓存, 注意这里得到的只是一个图像的引用
            Bitmap cacheBitmap = view.getDrawingCache();
            if (cacheBitmap == null) return null;

            Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
            // 释放位图内存
            view.destroyDrawingCache();
            // 回滚以前的缓存设置、缓存颜色设置
            view.setWillNotCacheDrawing(willNotCache);
            view.setDrawingCacheBackgroundColor(color);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByViewCache");
        }
        return null;
    }

    // ================
    // = LinearLayout =
    // ================

    /**
     * 通过 LinearLayout 绘制为 Bitmap
     * @param linearLayout {@link LinearLayout}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByLinearLayout(final LinearLayout linearLayout) {
        return snapshotByLinearLayout(linearLayout, BITMAP_CONFIG);
    }

    /**
     * 通过 LinearLayout 绘制为 Bitmap
     * <pre>
     *     LinearLayout 容器中不能有诸如 ListView、GridView、WebView 这样的高度可变的控件
     * </pre>
     * @param linearLayout {@link LinearLayout}
     * @param config       {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByLinearLayout(final LinearLayout linearLayout, final Bitmap.Config config) {
        return snapshotByView(linearLayout, config);
    }

    // ===============
    // = FrameLayout =
    // ===============

    /**
     * 通过 FrameLayout 绘制为 Bitmap
     * @param frameLayout {@link FrameLayout}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByFrameLayout(final FrameLayout frameLayout) {
        return snapshotByFrameLayout(frameLayout, BITMAP_CONFIG);
    }

    /**
     * 通过 FrameLayout 绘制为 Bitmap
     * <pre>
     *     FrameLayout 容器中不能有诸如 ListView、GridView、WebView 这样的高度可变的控件
     * </pre>
     * @param frameLayout {@link FrameLayout}
     * @param config      {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByFrameLayout(final FrameLayout frameLayout, final Bitmap.Config config) {
        return snapshotByView(frameLayout, config);
    }

    // ==================
    // = RelativeLayout =
    // ==================

    /**
     * 通过 RelativeLayout 绘制为 Bitmap
     * @param relativeLayout {@link RelativeLayout}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRelativeLayout(final RelativeLayout relativeLayout) {
        return snapshotByRelativeLayout(relativeLayout, BITMAP_CONFIG);
    }

    /**
     * 通过 RelativeLayout 绘制为 Bitmap
     * <pre>
     *     RelativeLayout 容器中不能有诸如 ListView、GridView、WebView 这样的高度可变的控件
     * </pre>
     * @param relativeLayout {@link RelativeLayout}
     * @param config         {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRelativeLayout(final RelativeLayout relativeLayout, final Bitmap.Config config) {
        return snapshotByView(relativeLayout, config);
    }

    // ==============
    // = ScrollView =
    // ==============

    /**
     * 通过 ScrollView 绘制为 Bitmap
     * @param scrollView {@link ScrollView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByScrollView(final ScrollView scrollView) {
        return snapshotByScrollView(scrollView, BITMAP_CONFIG);
    }

    /**
     * 通过 ScrollView 绘制为 Bitmap
     * <pre>
     *     ScrollView 容器中不能有诸如 ListView、GridView、WebView 这样的高度可变的控件
     * </pre>
     * @param scrollView {@link ScrollView}
     * @param config     {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByScrollView(final ScrollView scrollView, final Bitmap.Config config) {
        if (scrollView == null || config == null) return null;
        try {
            View view = scrollView.getChildAt(0);
            int width = view.getWidth();
            int height = view.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(BACKGROUND_COLOR);
            scrollView.layout(0, 0, scrollView.getMeasuredWidth(),
                    scrollView.getMeasuredHeight());
            scrollView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByScrollView");
        }
        return null;
    }

    // ========================
    // = HorizontalScrollView =
    // ========================

    /**
     * 通过 HorizontalScrollView 绘制为 Bitmap
     * @param scrollView {@link HorizontalScrollView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByHorizontalScrollView(final HorizontalScrollView scrollView) {
        return snapshotByHorizontalScrollView(scrollView, BITMAP_CONFIG);
    }

    /**
     * 通过 HorizontalScrollView 绘制为 Bitmap
     * @param scrollView {@link HorizontalScrollView}
     * @param config     {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByHorizontalScrollView(final HorizontalScrollView scrollView, final Bitmap.Config config) {
        if (scrollView == null || config == null) return null;
        try {
            View view = scrollView.getChildAt(0);
            int width = view.getWidth();
            int height = view.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(BACKGROUND_COLOR);
            scrollView.layout(0, 0, scrollView.getMeasuredWidth(),
                    scrollView.getMeasuredHeight());
            scrollView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByHorizontalScrollView");
        }
        return null;
    }

    // ====================
    // = NestedScrollView =
    // ====================

    /**
     * 通过 NestedScrollView 绘制为 Bitmap
     * @param scrollView {@link NestedScrollView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByNestedScrollView(final NestedScrollView scrollView) {
        return snapshotByNestedScrollView(scrollView, BITMAP_CONFIG);
    }

    /**
     * 通过 NestedScrollView 绘制为 Bitmap
     * @param scrollView {@link NestedScrollView}
     * @param config     {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByNestedScrollView(final NestedScrollView scrollView, final Bitmap.Config config) {
        if (scrollView == null || config == null) return null;
        try {
            View view = scrollView.getChildAt(0);
            int width = view.getWidth();
            int height = view.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(BACKGROUND_COLOR);
            scrollView.layout(0, 0, scrollView.getMeasuredWidth(),
                    scrollView.getMeasuredHeight());
            scrollView.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByNestedScrollView");
        }
        return null;
    }

    // ================
    // = RecyclerView =
    // ================

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * @param recyclerView {@link RecyclerView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(final RecyclerView recyclerView) {
        return snapshotByRecyclerView(recyclerView, BITMAP_CONFIG, 0, 0);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * @param recyclerView {@link RecyclerView}
     * @param config       {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(final RecyclerView recyclerView,
                                                final Bitmap.Config config) {
        return snapshotByRecyclerView(recyclerView, config, 0, 0);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * @param recyclerView {@link RecyclerView}
     * @param spacing 每列之间的间隔 |
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(final RecyclerView recyclerView, final int spacing) {
        return snapshotByRecyclerView(recyclerView, BITMAP_CONFIG, spacing, spacing);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * @param recyclerView {@link RecyclerView}
     * @param config       {@link Bitmap.Config}
     * @param spacing 每列之间的间隔 |
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(final RecyclerView recyclerView,
                                                final Bitmap.Config config, final int spacing) {
        return snapshotByRecyclerView(recyclerView, config, spacing, spacing);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * <pre>
     *     不支持含 ItemDecoration 截图
     * </pre>
     * @param recyclerView {@link RecyclerView}
     * @param config       {@link Bitmap.Config}
     * @param horizontalSpacing 每列之间的间隔 |
     * @param verticalSpacing 每行之间的间隔 -
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(final RecyclerView recyclerView,
                                                final Bitmap.Config config,
                                                final int horizontalSpacing,
                                                final int verticalSpacing) {
        if (recyclerView == null || config == null) return null;
        try {
            // 获取适配器
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            // 获取布局管理器
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager != null && adapter != null) {
                int width = 0, height = 0;
                // Item 总条数
                int itemCount = adapter.getItemCount();
                // 没数据则直接跳过
                if (itemCount == 0) return null;
                // View Bitmaps
                Bitmap[] bitmaps = new Bitmap[itemCount];
                // 获取类型
                Class clazz = layoutManager.getClass();
                // 判断横竖布局
                boolean vertical;
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    int orientation = linearLayoutManager.getOrientation();
                    vertical = (orientation == 1);
                } else {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int orientation = staggeredGridLayoutManager.getOrientation();
                    vertical = (orientation == 1);
                }
                // 判断布局类型
                if (clazz == LinearLayoutManager.class) {
                    if (vertical) {
                        // ============
                        // = 竖向滑动 =
                        // ============

                        for (int i = 0; i < itemCount; i++) {
                            RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                            adapter.onBindViewHolder(holder, i);
                            View childView = holder.itemView;
                            measureChild(childView, recyclerView.getWidth());

                            Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
                            Canvas canvas = new Canvas(bitmap);
                            canvas.drawColor(BACKGROUND_COLOR);
                            childView.draw(canvas);

                            bitmaps[i] = bitmap;
                            height += childView.getMeasuredHeight();
                        }

                        // 追加子项间分隔符占用的高度
                        height += (verticalSpacing * (itemCount - 1));
                        width = recyclerView.getMeasuredWidth();
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawColor(BACKGROUND_COLOR);
                        int iHeight = 0;
                        for (int i = 0, len = bitmaps.length; i < len; i++) {
                            Bitmap bmp = bitmaps[i];
                            canvas.drawBitmap(bmp, 0, iHeight, PAINT);
                            iHeight += (bmp.getHeight() + (i * verticalSpacing));
                            // 释放资源
                            bmp.recycle();
                            bmp = null;
                        }
                        return bitmap;
                    } else {
                        // ============
                        // = 横向滑动 =
                        // ============

                        // 临时高度 - 保存一行中最长列的高度
                        int tempHeight = 0;
                        // 循环每一行绘制每个 Item 并保存 Bitmap
                        for (int i = 0; i < itemCount; i++) {
                            RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                            adapter.onBindViewHolder(holder, i);
                            View childView = holder.itemView;
                            measureChild(childView, 0);

                            Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
                            Canvas canvas = new Canvas(bitmap);
                            canvas.drawColor(BACKGROUND_COLOR);
                            childView.draw(canvas);

                            bitmaps[i] = bitmap;
                            width += childView.getMeasuredWidth();
                            int itemHeight = childView.getMeasuredHeight();
                            // 保留最大高度
                            tempHeight = Math.max(itemHeight, tempHeight);
                        }

                        // 追加子项间分隔符占用的宽度
                        width += (horizontalSpacing * (itemCount - 1));
                        height = tempHeight;
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawColor(BACKGROUND_COLOR);
                        int iWidth = 0;
                        for (int i = 0, len = bitmaps.length; i < len; i++) {
                            Bitmap bmp = bitmaps[i];
                            canvas.drawBitmap(bmp, iWidth, 0, PAINT);
                            iWidth += (bmp.getWidth() + (i * horizontalSpacing));
                            // 释放资源
                            bmp.recycle();
                            bmp = null;
                        }
                        return bitmap;
                    }
                } else if (clazz == GridLayoutManager.class) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    // 获取一共多少列
                    int spanCount = gridLayoutManager.getSpanCount();
                    // 获取倍数 ( 行数 )
                    int lineNumber = 0;

                    if (vertical) {
                        // ============
                        // = 竖向滑动 =
                        // ============

                        // 获取倍数 ( 行数 )
                        lineNumber = getMultiple(itemCount, spanCount);
                        // 计算总共的宽度 - (GridView 宽度 - 列分割间距 ) / spanCount
                        int childWidth = (recyclerView.getWidth() - (spanCount - 1) * horizontalSpacing) / spanCount;
                        // 记录每一行高度
                        int[] itemHeightArrays = new int[lineNumber];
                        // 临时高度 - 保存一行中最长列的高度
                        int tempHeight = 0;

                        // 循环每一行绘制每个 Item 并保存 Bitmap
                        for (int i = 0; i < lineNumber; i++) {
                            // 清空高度
                            tempHeight = 0;
                            // 循环列数
                            for (int j = 0; j < spanCount; j++) {
                                // 获取对应的索引
                                int position = i * spanCount + j;
                                // 小于总数才处理
                                if (position < itemCount) {
                                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(position));
                                    adapter.onBindViewHolder(holder, position);
                                    View childView = holder.itemView;
                                    childView.measure(View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY),
                                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                    childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());

                                    Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
                                    Canvas canvas = new Canvas(bitmap);
                                    canvas.drawColor(BACKGROUND_COLOR);
                                    childView.draw(canvas);

                                    bitmaps[position] = bitmap;
                                    int itemHeight = childView.getMeasuredHeight();
                                    // 保留最大高度
                                    tempHeight = Math.max(itemHeight, tempHeight);
                                }

                                // 最后记录高度并累加
                                if (j == spanCount - 1) {
                                    height += tempHeight;
                                    itemHeightArrays[i] = tempHeight;
                                }
                            }
                        }

                        // 追加子项间分隔符占用的高度
                        height += (verticalSpacing * (lineNumber - 1));
                        width = recyclerView.getMeasuredWidth();
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawColor(BACKGROUND_COLOR);
                        int iHeight = 0;
                        // 循环每一行绘制每个 Item Bitmap
                        for (int i = 0; i < lineNumber; i++) {
                            // 获取每一行最长列的高度
                            int itemHeight = itemHeightArrays[i];
                            // 循环列数
                            for (int j = 0; j < spanCount; j++) {
                                // 获取对应的索引
                                int position = i * spanCount + j;
                                // 小于总数才处理
                                if (position < itemCount) {
                                    Bitmap bmp = bitmaps[position];
                                    // 计算一下边距
                                    int left = j * (horizontalSpacing + childWidth);
                                    Matrix matrix = new Matrix();
                                    matrix.postTranslate(left, iHeight);
                                    // 绘制到 Bitmap
                                    canvas.drawBitmap(bmp, matrix, PAINT);
                                    // 释放资源
//                                    bmp.recycle();
                                    bmp = null;
                                }

                                // 最后记录高度并累加
                                if (j == spanCount - 1) {
                                    iHeight += itemHeight + verticalSpacing;
                                }
                            }
                        }
                        return bitmap;
                    } else {
                        // ============
                        // = 横向滑动 =
                        // ============

                        // 获取行数
                        lineNumber = Math.min(spanCount, itemCount);
                        // 记录每一行宽度
                        int[] itemWidthArrays = new int[lineNumber];
                        // 记录每一行高度
                        int[] itemHeightArrays = new int[lineNumber];
                        // 获取一共多少列
                        int numColumns = getMultiple(itemCount, lineNumber);
                        // 临时高度 - 保存一行中最长列的高度
                        int tempHeight = 0;

                        // 循环每一行绘制每个 Item 并保存 Bitmap
                        for (int i = 0; i < lineNumber; i++) {
                            for (int j = 0; j < numColumns; j++) {
                                // 获取对应的索引
                                int position = j * lineNumber + i;
                                // 小于总数才处理
                                if (position < itemCount) {
                                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(position));
                                    adapter.onBindViewHolder(holder, position);
                                    View childView = holder.itemView;
                                    childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                    childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());

                                    Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
                                    Canvas canvas = new Canvas(bitmap);
                                    canvas.drawColor(BACKGROUND_COLOR);
                                    childView.draw(canvas);

                                    bitmaps[position] = bitmap;
                                    int itemWidth = childView.getMeasuredWidth();
                                    int itemHeight = childView.getMeasuredHeight();
                                    // 累加宽度
                                    itemWidthArrays[i] += itemWidth;
                                    // 保留最大高度
                                    tempHeight = Math.max(itemHeight, tempHeight);
                                }

                                // 最后记录处理
                                if (j == numColumns - 1) {
                                    height += tempHeight;
                                    width = Math.max(width, itemWidthArrays[i]);
                                    itemHeightArrays[i] = tempHeight;
                                }
                            }
                        }

                        // 追加子项间分隔符占用的宽高
                        width += (horizontalSpacing * (numColumns - 1));
                        height += (verticalSpacing * (numColumns - 1));

                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawColor(BACKGROUND_COLOR);
                        int iWidth = 0, iHeight = 0;

                        // 循环每一行绘制每个 Item 并保存 Bitmap
                        for (int i = 0; i < lineNumber; i++) {
                            int itemHeight = itemHeightArrays[i];

                            for (int j = 0; j < numColumns; j++) {
                                // 获取对应的索引
                                int position = j * lineNumber + i;
                                // 小于总数才处理
                                if (position < itemCount) {
                                    Bitmap bmp = bitmaps[position];
                                    // 计算一下边距
                                    int left = iWidth + (j * horizontalSpacing);
                                    Matrix matrix = new Matrix();
                                    matrix.postTranslate(left, iHeight);
                                    // 绘制到 Bitmap
                                    canvas.drawBitmap(bmp, matrix, PAINT);
                                    // 累加 Bitmap 宽度
                                    iWidth += bmp.getWidth();
                                    // 释放资源
//                                    bmp.recycle();
                                    bmp = null;

                                }

                                // 最后记录处理
                                if (j == numColumns - 1) {
                                    iWidth = 0;
                                    iHeight += itemHeight + (verticalSpacing * i);
                                }
                            }
                        }
                        return bitmap;
                    }
                } else if (clazz == StaggeredGridLayoutManager.class) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    // 获取一共多少列
                    int spanCount = staggeredGridLayoutManager.getSpanCount();
                    // 获取倍数 ( 行数 )
                    int lineNumber = 0;

                    if (vertical) {
                        // ============
                        // = 竖向滑动 =
                        // ============

                        // 获取倍数 ( 行数 )
                        lineNumber = getMultiple(itemCount, spanCount);
                        // 计算总共的宽度 - (GridView 宽度 - 列分割间距 ) / spanCount
                        int childWidth = (recyclerView.getWidth() - (spanCount - 1) * horizontalSpacing) / spanCount;
                        // 记录每个 Item 高度
                        int[] itemHeightArrays = new int[itemCount];

                        // 循环每一行绘制每个 Item 并保存 Bitmap
                        for (int i = 0; i < lineNumber; i++) {
                            // 循环列数
                            for (int j = 0; j < spanCount; j++) {
                                // 获取对应的索引
                                int position = i * spanCount + j;
                                // 小于总数才处理
                                if (position < itemCount) {
                                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(position));
                                    adapter.onBindViewHolder(holder, position);
                                    View childView = holder.itemView;
                                    childView.measure(View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY),
                                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                    childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());

                                    Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
                                    Canvas canvas = new Canvas(bitmap);
                                    canvas.drawColor(BACKGROUND_COLOR);
                                    childView.draw(canvas);

                                    bitmaps[position] = bitmap;
                                    int itemHeight = childView.getMeasuredHeight();
                                    itemHeightArrays[position] = itemHeight;
                                }
                            }
                        }

                        // 记录每列总高度
                        int[] columnsHeightArrays = new int[spanCount];
                        // 循环图片高度, 计算绘制位置
                        for (int i = 0; i < itemCount; i++) {
                            // 获取最小高度索引
                            int minIndex = getMinimumIndex(columnsHeightArrays);
                            // 累加高度
                            columnsHeightArrays[minIndex] += itemHeightArrays[i];
                        }

                        int maxIndex = getMaximumIndex(columnsHeightArrays);
                        // 追加子项间分隔符占用的高度
                        height = columnsHeightArrays[maxIndex] + (verticalSpacing * (spanCount - 1));
                        width = recyclerView.getMeasuredWidth();

                        columnsHeightArrays = new int[spanCount];
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawColor(BACKGROUND_COLOR);
                        // 循环绘制
                        for (int i = 0; i < itemCount; i++) {
                            // 获取最小高度索引
                            int minIndex = getMinimumIndex(columnsHeightArrays);
                            // 计算一下边距
                            int left = minIndex * (horizontalSpacing + childWidth);
                            Matrix matrix = new Matrix();
                            matrix.postTranslate(left, columnsHeightArrays[minIndex]);
                            // 绘制到 Bitmap
                            Bitmap bmp = bitmaps[i];
                            canvas.drawBitmap(bmp, matrix, PAINT);
                            // 累加高度
                            columnsHeightArrays[minIndex] += itemHeightArrays[i];
                        }
                        return bitmap;
                    } else {
                        // ============
                        // = 横向滑动 =
                        // ============

                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByRecyclerView");
        }
        return null;
    }

    // ============
    // = ListView =
    // ============

    /**
     * 通过 ListView 绘制为 Bitmap
     * @param listView {@link ListView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByListView(final ListView listView) {
        return snapshotByListView(listView, BITMAP_CONFIG);
    }

    /**
     * 通过 ListView 绘制为 Bitmap
     * @param listView {@link ListView}
     * @param config   {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByListView(final ListView listView, final Bitmap.Config config) {
        if (listView == null || config == null) return null;
        try {
            // Adapter
            ListAdapter listAdapter = listView.getAdapter();
            // Item 总条数
            int itemCount = listAdapter.getCount();
            // 没数据则直接跳过
            if (itemCount == 0) return null;
            // 高度
            int height = 0;
            // 获取子项间分隔符占用的高度
            int dividerHeight = listView.getDividerHeight();
            // View Bitmaps
            Bitmap[] bitmaps = new Bitmap[itemCount];

            // 循环绘制每个 Item 并保存 Bitmap
            for (int i = 0; i < itemCount; i++) {
                View childView = listAdapter.getView(i, null, listView);
                measureChild(childView, listView.getWidth());
                bitmaps[i] = canvasBitmap(childView, config);
                height += childView.getMeasuredHeight();
            }
            // 追加子项间分隔符占用的高度
            height += (dividerHeight * (itemCount - 1));
            int width = listView.getMeasuredWidth();
            // 创建位图
            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(BACKGROUND_COLOR);
            // 累加高度
            int appendHeight = 0;
            for (int i = 0, len = bitmaps.length; i < len; i++) {
                Bitmap bmp = bitmaps[i];
                canvas.drawBitmap(bmp, 0, appendHeight, PAINT);
                appendHeight += (bmp.getHeight() + dividerHeight);
                // 释放资源
                bmp.recycle();
                bmp = null;
            }
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByListView");
        }
        return null;
    }

    // ============
    // = GridView =
    // ============

    /**
     * 通过 GridView 绘制为 Bitmap
     * @param gridView {@link GridView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByGridView(final GridView gridView) {
        return snapshotByGridView(gridView, BITMAP_CONFIG, false);
    }

    /**
     * 通过 GridView 绘制为 Bitmap
     * @param gridView {@link GridView}
     * @param config   {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByGridView(final GridView gridView, final Bitmap.Config config) {
        return snapshotByGridView(gridView, config, false);
    }

    /**
     * 通过 GridView 绘制为 Bitmap
     * @param gridView       {@link GridView}
     * @param config         {@link Bitmap.Config}
     * @param listViewEffect 是否保存 ListView 效果 ( 每个 Item 铺满 )
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByGridView(final GridView gridView, final Bitmap.Config config, final boolean listViewEffect) {
        if (gridView == null || config == null) return null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return null;
        try {
            int height = 0;
            // 获取一共多少列
            int numColumns = gridView.getNumColumns();
            // 每列之间的间隔 |
            int horizontalSpacing = gridView.getHorizontalSpacing();
            // 每行之间的间隔 -
            int verticalSpacing = gridView.getVerticalSpacing();
            // Adapter
            ListAdapter listAdapter = gridView.getAdapter();
            // Item 总条数
            int itemCount = listAdapter.getCount();
            // 没数据则直接跳过
            if (itemCount == 0) return null;
            // View Bitmaps
            Bitmap[] bitmaps = new Bitmap[itemCount];

            // 效果处理 - ListView 效果 Item 铺满
            if (listViewEffect) {
                // 循环绘制每个 Item 并保存 Bitmap
                for (int i = 0; i < itemCount; i++) {
                    View childView = listAdapter.getView(i, null, gridView);
                    childView.measure(View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                    childView.setDrawingCacheEnabled(true);
                    childView.buildDrawingCache();

                    bitmaps[i] = childView.getDrawingCache();
                    height += childView.getMeasuredHeight();
                }
                // 追加子项间分隔符占用的高度
                height += (verticalSpacing * (itemCount - 1));

                int width = gridView.getMeasuredWidth();
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                int iHeight = 0;
                for (int i = 0, len = bitmaps.length; i < len; i++) {
                    Bitmap bmp = bitmaps[i];
                    canvas.drawBitmap(bmp, 0, iHeight, PAINT);
                    iHeight += (bmp.getHeight() + verticalSpacing);
                    // 释放资源
                    bmp.recycle();
                    bmp = null;
                }
                return bitmap;
            } else {
                // 获取倍数 ( 行数 )
                int lineNumber = getMultiple(itemCount, numColumns);
                // 计算总共的宽度 - (GridView 宽度 - 列分割间距 ) / numColumns
                int childWidth = (gridView.getWidth() - (numColumns - 1) * horizontalSpacing) / numColumns;

                // 记录每一行高度
                int[] itemHeightArrays = new int[lineNumber];
                // 临时高度 - 保存一行中最长列的高度
                int tempHeight = 0;
                // 循环每一行绘制每个 Item 并保存 Bitmap
                for (int i = 0; i < lineNumber; i++) {
                    // 清空高度
                    tempHeight = 0;
                    // 循环列数
                    for (int j = 0; j < numColumns; j++) {
                        // 获取对应的索引
                        int position = i * numColumns + j;
                        // 小于总数才处理
                        if (position < itemCount) {
                            View childView = listAdapter.getView(position, null, gridView);
                            childView.measure(View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY),
                                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                            childView.setDrawingCacheEnabled(true);
                            childView.buildDrawingCache();

                            bitmaps[position] = childView.getDrawingCache();
                            int itemHeight = childView.getMeasuredHeight();
                            // 保留最大高度
                            tempHeight = Math.max(itemHeight, tempHeight);
                        }

                        // 最后记录高度并累加
                        if (j == numColumns - 1) {
                            height += tempHeight;
                            itemHeightArrays[i] = tempHeight;
                        }
                    }
                }
                // 追加子项间分隔符占用的高度
                height += (verticalSpacing * (lineNumber - 1));

                int width = gridView.getMeasuredWidth();
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                int iHeight = 0;
                // 循环每一行绘制每个 Item Bitmap
                for (int i = 0; i < lineNumber; i++) {
                    // 获取每一行最长列的高度
                    int itemHeight = itemHeightArrays[i];
                    // 循环列数
                    for (int j = 0; j < numColumns; j++) {
                        // 获取对应的索引
                        int position = i * numColumns + j;
                        // 小于总数才处理
                        if (position < itemCount) {
                            Bitmap bmp = bitmaps[position];
                            // 计算一下边距
                            int left = j * (horizontalSpacing + childWidth);
                            Matrix matrix = new Matrix();
                            matrix.postTranslate(left, iHeight);
                            // 绘制到 Bitmap
                            canvas.drawBitmap(bmp, matrix, PAINT);
                            // 释放资源
//                            bmp.recycle();
                            bmp = null;
                        }

                        // 最后记录高度并累加
                        if (j == numColumns - 1) {
                            iHeight += itemHeight + verticalSpacing;
                        }
                    }
                }
                return bitmap;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByGridView");
        }
        return null;
    }

    // ================
    // = 内部私有方法 =
    // ================

    /**
     * 绘制 Bitmap
     * @param childView {@link View}
     * @param config {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    private static Bitmap canvasBitmap(final View childView, final Bitmap.Config config){
        Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(BACKGROUND_COLOR);
        childView.draw(canvas);
        return bitmap;
    }

    /**
     * 测量 View
     * @param childView {@link View}
     * @param specifiedWidth 指定高度
     */
    private static void measureChild(final View childView, final int specifiedWidth) {
        ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
        // MeasureSpec
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        // 如果大于 0
        if (specifiedWidth > 0) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(specifiedWidth, View.MeasureSpec.EXACTLY);
        }
        // 判断是否存在自定义宽高
        if (layoutParams != null) {
            int width = layoutParams.width;
            int height = layoutParams.height;
            if (width > 0 && height > 0) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            } else if (width > 0) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            } else if (height > 0){
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            }
        }
        childView.measure(widthMeasureSpec, heightMeasureSpec);
        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = ScreenUtils =
    // ===============

    /**
     * 获取屏幕宽高
     * @return int[], 0 = 宽度, 1 = 高度
     */
    private static int[] getScreenWidthHeight() {
        try {
            WindowManager windowManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                DisplayMetrics displayMetrics = DevUtils.getContext().getResources().getDisplayMetrics();
                return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
            }
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            return new int[]{point.x, point.y};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenWidthHeight");
        }
        return new int[]{0, 0};
    }

    /**
     * 获取应用区域 TitleBar 高度 ( 顶部灰色 TitleBar 高度, 没有设置 android:theme 的 NoTitleBar 时会显示 )
     * @param activity {@link Activity}
     * @return 应用区域 TitleBar 高度
     */
    private static int getStatusBarHeight(final Activity activity) {
        try {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.top;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStatusBarHeight");
        }
        return 0;
    }

    // ===============
    // = NumberUtils =
    // ===============

    /**
     * 获取倍数 ( 自动补 1)
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    private static int getMultiple(final int value, final int divisor) {
        if (value <= 0 || divisor <= 0) return 0;
        if (value <= divisor) return 1;
        return (value % divisor == 0) ? (value / divisor) : (value / divisor) + 1;
    }

    // ==============
    // = ArrayUtils =
    // ==============

    /**
     * 获取数组中最小值索引
     * @param data 数组
     * @return 最小值索引
     */
    private static int getMinimumIndex(final int[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int index = 0;
                int temp = data[index];
                for (int i = 1; i < len; i++) {
                    int value = data[i];
                    if (value < temp) {
                        index = i;
                        temp = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最大值索引
     * @param data 数组
     * @return 最大值索引
     */
    private static int getMaximumIndex(final int[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int index = 0;
                int temp = data[index];
                for (int i = 1; i < len; i++) {
                    int value = data[i];
                    if (value > temp) {
                        index = i;
                        temp = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }
}