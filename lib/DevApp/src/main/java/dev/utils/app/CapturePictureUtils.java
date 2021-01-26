package dev.utils.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import dev.utils.LogPrintUtils;
import dev.utils.common.ArrayUtils;
import dev.utils.common.NumberUtils;

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
    private static Bitmap.Config BITMAP_CONFIG    = Bitmap.Config.RGB_565;
    // Canvas 背景色
    private static int           BACKGROUND_COLOR = Color.TRANSPARENT;
    // 画笔
    private static Paint         PAINT            = new Paint();

    // ===========
    // = 配置相关 =
    // ===========

    /**
     * 设置 Bitmap Config
     * @param config {@link Bitmap.Config}
     */
    public static void setBitmapConfig(final Bitmap.Config config) {
        if (config == null) return;
        BITMAP_CONFIG = config;
    }

    /**
     * 设置 Canvas 背景色
     * @param backgroundColor 背景色
     */
    public static void setBackgroundColor(@ColorInt final int backgroundColor) {
        BACKGROUND_COLOR = backgroundColor;
    }

    /**
     * 设置画笔
     * @param paint {@link Paint}
     */
    public static void setPaint(final Paint paint) {
        if (paint == null) return;
        PAINT = paint;
    }

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
            int[] widthHeight = ScreenUtils.getScreenWidthHeight();

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
            int[] widthHeight = ScreenUtils.getScreenWidthHeight();
            // 获取状态栏高度
            int statusBarHeight = BarUtils.getStatusBarHeight();

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
    public static Bitmap snapshotByWebView(
            final WebView webView,
            final int maxHeight
    ) {
        return snapshotByWebView(webView, maxHeight, BITMAP_CONFIG, 0f);
    }

    /**
     * 截图 WebView
     * @param webView {@link WebView}
     * @param scale   缩放比例
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(
            final WebView webView,
            final float scale
    ) {
        return snapshotByWebView(webView, Integer.MAX_VALUE, BITMAP_CONFIG, scale);
    }

    /**
     * 截图 WebView
     * @param webView   {@link WebView}
     * @param maxHeight 最大高度
     * @param config    {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(
            final WebView webView,
            final int maxHeight,
            final Bitmap.Config config
    ) {
        return snapshotByWebView(webView, maxHeight, config, 0f);
    }

    /**
     * 截图 WebView
     * <pre>
     *     TODO
     *      在 Android 5.0 及以上版本, Android 对 WebView 进行了优化, 为了减少内存使用和提高性能
     *      使用 WebView 加载网页时只绘制显示部分, 如果我们不做处理, 就会出现只截到屏幕内显示的 WebView 内容, 其它部分是空白的情况
     *      通过调用 WebView.enableSlowWholeDocumentDraw() 方法可以关闭这种优化, 但要注意的是, 该方法需要在 WebView 实例被创建前就要调用,
     *      否则没有效果, 所以我们在 WebView 实例被创建前加入代码
     *     {@link CapturePictureUtils#enableSlowWholeDocumentDraw}
     * </pre>
     * @param webView   {@link WebView}
     * @param maxHeight 最大高度
     * @param config    {@link Bitmap.Config}
     * @param scale     缩放比例
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(
            final WebView webView,
            final int maxHeight,
            final Bitmap.Config config,
            final float scale
    ) {
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
                    int width  = webView.getWidth();
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
                    LogPrintUtils.eTag(TAG, e, "snapshotByWebView SDK_INT >= 21(5.0)");
                }
            } else {
                try {
                    Picture picture = webView.capturePicture();
                    int     width   = picture.getWidth();
                    int     height  = picture.getHeight();
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
                    LogPrintUtils.eTag(TAG, e, "snapshotByWebView SDK_INT < 21(5.0)");
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
    public static Bitmap snapshotByView(
            final View view,
            final Bitmap.Config config
    ) {
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
    public static Bitmap snapshotByLinearLayout(
            final LinearLayout linearLayout,
            final Bitmap.Config config
    ) {
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
    public static Bitmap snapshotByFrameLayout(
            final FrameLayout frameLayout,
            final Bitmap.Config config
    ) {
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
    public static Bitmap snapshotByRelativeLayout(
            final RelativeLayout relativeLayout,
            final Bitmap.Config config
    ) {
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
    public static Bitmap snapshotByScrollView(
            final ScrollView scrollView,
            final Bitmap.Config config
    ) {
        if (scrollView == null || config == null) return null;
        try {
            View view   = scrollView.getChildAt(0);
            int  width  = view.getWidth();
            int  height = view.getHeight();

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
    public static Bitmap snapshotByHorizontalScrollView(
            final HorizontalScrollView scrollView,
            final Bitmap.Config config
    ) {
        if (scrollView == null || config == null) return null;
        try {
            View view   = scrollView.getChildAt(0);
            int  width  = view.getWidth();
            int  height = view.getHeight();

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
    public static Bitmap snapshotByNestedScrollView(
            final NestedScrollView scrollView,
            final Bitmap.Config config
    ) {
        if (scrollView == null || config == null) return null;
        try {
            View view   = scrollView.getChildAt(0);
            int  width  = view.getWidth();
            int  height = view.getHeight();

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
    public static Bitmap snapshotByListView(
            final ListView listView,
            final Bitmap.Config config
    ) {
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
                WidgetUtils.measureView(childView, listView.getWidth());
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
    public static Bitmap snapshotByGridView(
            final GridView gridView,
            final Bitmap.Config config
    ) {
        return snapshotByGridView(gridView, config, false);
    }

    /**
     * 通过 GridView 绘制为 Bitmap
     * @param gridView       {@link GridView}
     * @param config         {@link Bitmap.Config}
     * @param listViewEffect 是否保存 ListView 效果 ( 每个 Item 铺满 )
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByGridView(
            final GridView gridView,
            final Bitmap.Config config,
            final boolean listViewEffect
    ) {
        if (gridView == null || config == null) return null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return null;
        try {
            // Adapter
            ListAdapter listAdapter = gridView.getAdapter();
            // Item 总条数
            int itemCount = listAdapter.getCount();
            // 没数据则直接跳过
            if (itemCount == 0) return null;
            // 高度
            int height = 0;
            // 获取一共多少列
            int numColumns = gridView.getNumColumns();
            // 每列之间的间隔 |
            int horizontalSpacing = gridView.getHorizontalSpacing();
            // 每行之间的间隔 -
            int verticalSpacing = gridView.getVerticalSpacing();
            // View Bitmaps
            Bitmap[] bitmaps = new Bitmap[itemCount];

            // 效果处理 ( ListView 效果 Item 铺满 )
            if (listViewEffect) {
                // 循环绘制每个 Item 并保存 Bitmap
                for (int i = 0; i < itemCount; i++) {
                    View childView = listAdapter.getView(i, null, gridView);
                    WidgetUtils.measureView(childView, gridView.getWidth());
                    bitmaps[i] = canvasBitmap(childView, config);
                    height += childView.getMeasuredHeight();
                }
                // 追加子项间分隔符占用的高度
                height += (verticalSpacing * (itemCount - 1));
                int width = gridView.getMeasuredWidth();
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                // 累加高度
                int appendHeight = 0;
                for (int i = 0, len = bitmaps.length; i < len; i++) {
                    Bitmap bmp = bitmaps[i];
                    canvas.drawBitmap(bmp, 0, appendHeight, PAINT);
                    appendHeight += (bmp.getHeight() + verticalSpacing);
                    // 释放资源
                    bmp.recycle();
                    bmp = null;
                }
                return bitmap;
            } else {
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
                        // 小于总数才处理
                        if (position < itemCount) {
                            View childView = listAdapter.getView(position, null, gridView);
                            WidgetUtils.measureView(childView, childWidth);
                            bitmaps[position] = canvasBitmap(childView, config);

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
                int width = gridView.getMeasuredWidth();
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                // 累加高度
                int appendHeight = 0;
                // 循环每一行绘制每个 Item Bitmap
                for (int i = 0; i < lineNumber; i++) {
                    // 获取每一行最长列的高度
                    int itemHeight = rowHeightArrays[i];
                    // 循环列数
                    for (int j = 0; j < numColumns; j++) {
                        // 获取对应的索引
                        int position = i * numColumns + j;
                        // 小于总数才处理
                        if (position < itemCount) {
                            Bitmap bmp = bitmaps[position];
                            // 计算边距
                            int    left   = j * (horizontalSpacing + childWidth);
                            Matrix matrix = new Matrix();
                            matrix.postTranslate(left, appendHeight);
                            // 绘制到 Bitmap
                            canvas.drawBitmap(bmp, matrix, PAINT);
                            // 释放资源
                            bmp.recycle();
                            bmp = null;
                        }

                        // 记录高度并累加
                        if (j == numColumns - 1) {
                            appendHeight += itemHeight + verticalSpacing;
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
    public static Bitmap snapshotByRecyclerView(
            final RecyclerView recyclerView,
            final Bitmap.Config config
    ) {
        return snapshotByRecyclerView(recyclerView, config, 0, 0);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * @param recyclerView {@link RecyclerView}
     * @param spacing      每列之间的间隔 |
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(
            final RecyclerView recyclerView,
            final int spacing
    ) {
        return snapshotByRecyclerView(recyclerView, BITMAP_CONFIG, spacing, spacing);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * @param recyclerView {@link RecyclerView}
     * @param config       {@link Bitmap.Config}
     * @param spacing      每列之间的间隔 |
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(
            final RecyclerView recyclerView,
            final Bitmap.Config config,
            final int spacing
    ) {
        return snapshotByRecyclerView(recyclerView, config, spacing, spacing);
    }

    /**
     * 通过 RecyclerView 绘制为 Bitmap
     * <pre>
     *     不支持含 ItemDecoration 截图
     *     如果数据太多推荐 copy 代码, 修改为保存每个 Item Bitmap 到本地, 并在绘制时获取绘制
     * </pre>
     * @param recyclerView      {@link RecyclerView}
     * @param config            {@link Bitmap.Config}
     * @param verticalSpacing   每行之间的间隔 -
     * @param horizontalSpacing 每列之间的间隔 |
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByRecyclerView(
            final RecyclerView recyclerView,
            final Bitmap.Config config,
            final int verticalSpacing,
            final int horizontalSpacing
    ) {
        if (recyclerView == null || config == null) return null;
        try {
            // 获取适配器
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            // 获取布局管理器
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager != null && adapter != null) {
                // 判断布局类型
                if (layoutManager instanceof GridLayoutManager) {
                    return snapshotByRecyclerView_GridLayoutManager(recyclerView,
                            config, verticalSpacing, horizontalSpacing);
                } else if (layoutManager instanceof LinearLayoutManager) {
                    return snapshotByRecyclerView_LinearLayoutManager(recyclerView,
                            config, verticalSpacing, horizontalSpacing);
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    return snapshotByRecyclerView_StaggeredGridLayoutManager(recyclerView,
                            config, verticalSpacing, horizontalSpacing);
                }
                throw new Exception(String.format("Not Supported %s LayoutManager", layoutManager.getClass().getSimpleName()));
            } else {
                throw new Exception("Adapter or LayoutManager is Null");
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByRecyclerView");
        }
        return null;
    }

    // =================
    // = LayoutManager =
    // =================

    /**
     * 通过 RecyclerView GridLayoutManager 绘制为 Bitmap
     * @param recyclerView      {@link RecyclerView}
     * @param config            {@link Bitmap.Config}
     * @param verticalSpacing   每行之间的间隔 -
     * @param horizontalSpacing 每列之间的间隔 |
     * @return {@link Bitmap}
     */
    private static Bitmap snapshotByRecyclerView_GridLayoutManager(
            final RecyclerView recyclerView,
            final Bitmap.Config config,
            final int verticalSpacing,
            final int horizontalSpacing
    ) {
        // 计算思路
        // = 竖屏 =
        // 每个 Item 宽度最大值固定为 (RecyclerView 宽度 - ( 列数 - 1) * 每列边距 ) / 列数
        // 循环保存每行最大高度, 并累加每行之间的间隔, 用于 Bitmap 高度, 宽度用 RecyclerView 宽度
        // = 横屏 =
        // 循环保存每一行宽度以及每一行 ( 横着一行 ) 最大高度, 并且累加每行、每列之间的间隔
        // 用于 Bitmap 高度, 宽度用 ( 每一行宽度累加值 ) 最大值
        try {
            // 获取适配器
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            // Item 总条数
            int itemCount = adapter.getItemCount();
            // 没数据则直接跳过
            if (itemCount == 0) return null;
            // 宽高
            int width = 0, height = 0;
            // View Bitmaps
            Bitmap[] bitmaps = new Bitmap[itemCount];
            // 获取布局管理器 ( 判断横竖布局 )
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            boolean           vertical          = (gridLayoutManager.getOrientation() == RecyclerView.VERTICAL);
            // 获取一共多少列
            int spanCount = gridLayoutManager.getSpanCount();
            // 获取倍数 ( 行数 )
            int lineNumber = NumberUtils.getMultiple(itemCount, spanCount);
            if (vertical) {

                // ===========
                // = 竖向滑动 =
                // ===========

                // 计算总共的宽度 (GridView 宽度 - 列分割间距 ) / spanCount
                int childWidth = (recyclerView.getWidth() - (spanCount - 1) * horizontalSpacing) / spanCount;
                // 记录每行最大高度
                int[] rowHeightArrays = new int[lineNumber];
                // 临时高度 ( 保存行中最高的列高度 )
                int tempHeight;
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
                            WidgetUtils.measureView(childView, childWidth);
                            bitmaps[position] = canvasBitmap(childView, config);
                            int itemHeight = childView.getMeasuredHeight();
                            // 保留最大高度
                            tempHeight = Math.max(itemHeight, tempHeight);
                        }

                        // 记录高度并累加
                        if (j == spanCount - 1) {
                            height += tempHeight;
                            rowHeightArrays[i] = tempHeight;
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
                // 累加高度
                int appendHeight = 0;
                for (int i = 0; i < lineNumber; i++) {
                    // 获取每行中最高的列高度
                    int rowHeight = rowHeightArrays[i];
                    // 循环列数
                    for (int j = 0; j < spanCount; j++) {
                        // 获取对应的索引
                        int position = i * spanCount + j;
                        // 小于总数才处理
                        if (position < itemCount) {
                            Bitmap bmp = bitmaps[position];
                            // 计算边距
                            int    left   = j * (horizontalSpacing + childWidth);
                            Matrix matrix = new Matrix();
                            matrix.postTranslate(left, appendHeight);
                            // 绘制到 Bitmap
                            canvas.drawBitmap(bmp, matrix, PAINT);
                            // 释放资源
                            bmp.recycle();
                            bmp = null;
                        }

                        // 记录高度并累加
                        if (j == spanCount - 1) {
                            appendHeight += (rowHeight + verticalSpacing);
                        }
                    }
                }
                return bitmap;
            } else {

                // ===========
                // = 横向滑动 =
                // ===========

                // 获取行数
                lineNumber = Math.min(spanCount, itemCount);
                // 记录每一行宽度
                int[] rowWidthArrays = new int[lineNumber];
                // 记录每一行高度
                int[] rowHeightArrays = new int[lineNumber];
                // 获取一共多少列
                int numColumns = NumberUtils.getMultiple(itemCount, lineNumber);
                // 临时高度 ( 保存行中最高的列高度 )
                int tempHeight;
                for (int i = 0; i < lineNumber; i++) {
                    // 清空高度
                    tempHeight = 0;
                    // 循环列数
                    for (int j = 0; j < numColumns; j++) {
                        // 获取对应的索引
                        int position = j * lineNumber + i;
                        // 小于总数才处理
                        if (position < itemCount) {
                            RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(position));
                            adapter.onBindViewHolder(holder, position);
                            View childView = holder.itemView;
                            WidgetUtils.measureView(childView, 0);
                            bitmaps[position] = canvasBitmap(childView, config);
                            rowWidthArrays[i] += childView.getMeasuredWidth();
                            int itemHeight = childView.getMeasuredHeight();
                            // 保留最大高度
                            tempHeight = Math.max(itemHeight, tempHeight);
                        }

                        // 最后记录处理
                        if (j == numColumns - 1) {
                            height += tempHeight;
                            width = Math.max(width, rowWidthArrays[i]);
                            rowHeightArrays[i] = tempHeight;
                        }
                    }
                }

                // 追加子项间分隔符占用的高、宽
                height += (verticalSpacing * (lineNumber - 1));
                width += (horizontalSpacing * (numColumns - 1));
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                // 累加宽、高
                int appendWidth = 0, appendHeight = 0;
                for (int i = 0; i < lineNumber; i++) {
                    // 获取每行中最高的列高度
                    int rowHeight = rowHeightArrays[i];
                    // 循环列数
                    for (int j = 0; j < numColumns; j++) {
                        // 获取对应的索引
                        int position = j * lineNumber + i;
                        // 小于总数才处理
                        if (position < itemCount) {
                            Bitmap bmp = bitmaps[position];
                            // 计算边距
                            int    left   = appendWidth + (j * horizontalSpacing);
                            Matrix matrix = new Matrix();
                            matrix.postTranslate(left, appendHeight);
                            // 绘制到 Bitmap
                            canvas.drawBitmap(bmp, matrix, PAINT);
                            // 累加 Bitmap 宽度
                            appendWidth += bmp.getWidth();
                            // 释放资源
                            bmp.recycle();
                            bmp = null;
                        }

                        // 记录高度并累加
                        if (j == numColumns - 1) {
                            appendWidth = 0;
                            appendHeight += (rowHeight + verticalSpacing);
                        }
                    }
                }
                return bitmap;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByRecyclerView_GridLayoutManager");
        }
        return null;
    }

    /**
     * 通过 RecyclerView LinearLayoutManager 绘制为 Bitmap
     * @param recyclerView      {@link RecyclerView}
     * @param config            {@link Bitmap.Config}
     * @param verticalSpacing   每行之间的间隔 -
     * @param horizontalSpacing 每列之间的间隔 |
     * @return {@link Bitmap}
     */
    private static Bitmap snapshotByRecyclerView_LinearLayoutManager(
            final RecyclerView recyclerView,
            final Bitmap.Config config,
            final int verticalSpacing,
            final int horizontalSpacing
    ) {
        // 计算思路
        // = 竖屏 =
        // 循环保存每一个 Item View 高度, 并累加每行之间的间隔,
        // 用于 Bitmap 高度, 宽度用 RecyclerView 宽度
        // = 横屏 =
        // 循环保存每一个 Item View 宽度, 并累加每列之间的间隔, 且记录最高的列
        // 用于 Bitmap 高度, 宽度用累加出来的值
        try {
            // 获取适配器
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            // Item 总条数
            int itemCount = adapter.getItemCount();
            // 没数据则直接跳过
            if (itemCount == 0) return null;
            // 宽高
            int width = 0, height = 0;
            // View Bitmaps
            Bitmap[] bitmaps = new Bitmap[itemCount];
            // 获取布局管理器 ( 判断横竖布局 )
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            boolean             vertical            = (linearLayoutManager.getOrientation() == RecyclerView.VERTICAL);
            if (vertical) {

                // ===========
                // = 竖向滑动 =
                // ===========

                for (int i = 0; i < itemCount; i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.onBindViewHolder(holder, i);
                    View childView = holder.itemView;
                    WidgetUtils.measureView(childView, recyclerView.getWidth());
                    bitmaps[i] = canvasBitmap(childView, config);
                    height += childView.getMeasuredHeight();
                }

                // 追加子项间分隔符占用的高度
                height += (verticalSpacing * (itemCount - 1));
                width = recyclerView.getMeasuredWidth();
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                // 累加高度
                int appendHeight = 0;
                for (int i = 0; i < itemCount; i++) {
                    Bitmap bmp = bitmaps[i];
                    canvas.drawBitmap(bmp, 0, appendHeight, PAINT);
                    appendHeight += (bmp.getHeight() + verticalSpacing);
                    // 释放资源
                    bmp.recycle();
                    bmp = null;
                }
                return bitmap;
            } else {

                // ===========
                // = 横向滑动 =
                // ===========

                // 临时高度 ( 保存行中最高的列高度 )
                int tempHeight = 0;
                for (int i = 0; i < itemCount; i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.onBindViewHolder(holder, i);
                    View childView = holder.itemView;
                    WidgetUtils.measureView(childView, 0);
                    bitmaps[i] = canvasBitmap(childView, config);
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
                // 累加宽度
                int appendWidth = 0;
                for (int i = 0; i < itemCount; i++) {
                    Bitmap bmp = bitmaps[i];
                    canvas.drawBitmap(bmp, appendWidth, 0, PAINT);
                    appendWidth += (bmp.getWidth() + horizontalSpacing);
                    // 释放资源
                    bmp.recycle();
                    bmp = null;
                }
                return bitmap;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByRecyclerView_LinearLayoutManager");
        }
        return null;
    }

    /**
     * 通过 RecyclerView StaggeredGridLayoutManager 绘制为 Bitmap
     * @param recyclerView      {@link RecyclerView}
     * @param config            {@link Bitmap.Config}
     * @param verticalSpacing   每行之间的间隔 -
     * @param horizontalSpacing 每列之间的间隔 |
     * @return {@link Bitmap}
     */
    private static Bitmap snapshotByRecyclerView_StaggeredGridLayoutManager(
            final RecyclerView recyclerView,
            final Bitmap.Config config,
            final int verticalSpacing,
            final int horizontalSpacing
    ) {
        // 计算思路
        // = 竖屏 =
        // 每个 Item 宽度最大值固定为 (RecyclerView 宽度 - ( 列数 - 1) * 每列边距 ) / 列数
        // 循环保存每一个 Item View 高度, 并创建数组记录每一列待绘制高度, 实现瀑布流高度补差
        // 并通过该数组 ( 每列待绘制高度数组 ) 获取最大值, 用做 Bitmap 高度, 绘制则还是按以上规则高度补差累加
        // = 横屏 =
        // 循环保存每一个 Item View 宽度、高度, 并创建数组记录每一列待绘制宽度, 实现瀑布流高度补差
        // 并通过该数组 ( 每列待绘制宽度数组 ) 获取最大值, 用做 Bitmap 高度, 绘制则还是按以上规则宽度补差累加
        try {
            // 获取适配器
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            // Item 总条数
            int itemCount = adapter.getItemCount();
            // 没数据则直接跳过
            if (itemCount == 0) return null;
            // 宽高
            int width, height = 0;
            // View Bitmaps
            Bitmap[] bitmaps = new Bitmap[itemCount];
            // 获取布局管理器 ( 判断横竖布局 )
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            boolean                    vertical                   = (staggeredGridLayoutManager.getOrientation() == 1);
            // 获取一共多少列
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            // 获取倍数 ( 行数 )
            int lineNumber = NumberUtils.getMultiple(itemCount, spanCount);
            if (vertical) {

                // ===========
                // = 竖向滑动 =
                // ===========

                // 计算总共的宽度 - (GridView 宽度 - 列分割间距 ) / spanCount
                int childWidth = (recyclerView.getWidth() - (spanCount - 1) * horizontalSpacing) / spanCount;
                // 记录每个 Item 高度
                int[] itemHeightArrays = new int[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.onBindViewHolder(holder, i);
                    View childView = holder.itemView;
                    WidgetUtils.measureView(childView, childWidth);
                    bitmaps[i] = canvasBitmap(childView, config);
                    itemHeightArrays[i] = childView.getMeasuredHeight();
                }

                // 记录每列 Item 个数
                int[] columnsItemNumberArrays = new int[spanCount];
                // 记录每列总高度
                int[] columnsHeightArrays = new int[spanCount];
                // 循环高度, 计算绘制位置
                for (int i = 0; i < itemCount; i++) {
                    // 获取最小高度索引
                    int minIndex = ArrayUtils.getMinimumIndex(columnsHeightArrays);
                    // 累加高度
                    columnsHeightArrays[minIndex] += itemHeightArrays[i];
                    // 累加数量
                    columnsItemNumberArrays[minIndex] += 1;
                }

                // 计算高度 ( 追加子项间分隔符占用的高度 )
                if (lineNumber >= 2) {
                    // 循环追加子项间分隔符占用的高度
                    for (int i = 0; i < spanCount; i++) {
                        columnsHeightArrays[i] += (columnsItemNumberArrays[i] - 1) * verticalSpacing;
                    }
                }

                // 获取列最大高度索引
                int columnsHeightMaxIndex = ArrayUtils.getMaximumIndex(columnsHeightArrays);
                // 获取最大高度值
                int maxColumnsHeight = columnsHeightArrays[columnsHeightMaxIndex];
                // 使用最大值
                height = maxColumnsHeight;
                width = recyclerView.getMeasuredWidth();

                // 清空绘制时累加计算
                columnsHeightArrays = new int[spanCount];
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                // 循环绘制
                for (int i = 0; i < itemCount; i++) {
                    // 获取最小高度索引
                    int minIndex = ArrayUtils.getMinimumIndex(columnsHeightArrays);
                    // 计算边距
                    int    left   = minIndex * (horizontalSpacing + childWidth);
                    Matrix matrix = new Matrix();
                    matrix.postTranslate(left, columnsHeightArrays[minIndex]);
                    // 绘制到 Bitmap
                    Bitmap bmp = bitmaps[i];
                    canvas.drawBitmap(bmp, matrix, PAINT);
                    // 累加高度
                    columnsHeightArrays[minIndex] += (itemHeightArrays[i] + verticalSpacing);
                    // 释放资源
                    bmp.recycle();
                    bmp = null;
                }
                return bitmap;
            } else {

                // ===========
                // = 横向滑动 =
                // ===========

                // 获取行数
                lineNumber = Math.min(spanCount, itemCount);
                // 记录每个 Item 宽度
                int[] itemWidthArrays = new int[itemCount];
                // 记录每个 Item 高度
                int[] itemHeightArrays = new int[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.onBindViewHolder(holder, i);
                    View childView = holder.itemView;
                    WidgetUtils.measureView(childView, 0);
                    bitmaps[i] = canvasBitmap(childView, config);
                    itemWidthArrays[i] = childView.getMeasuredWidth();
                    itemHeightArrays[i] = childView.getMeasuredHeight();
                }

                // 记录每行向上距离
                int[] columnsTopArrays = new int[lineNumber];
                // 记录每行 Item 个数
                int[] columnsItemNumberArrays = new int[lineNumber];
                // 记录每行总宽度
                int[] columnsWidthArrays = new int[lineNumber];
                // 记录每行最大高度
                int[] columnsHeightArrays = new int[lineNumber];
                // 循环宽度, 计算绘制位置
                for (int i = 0; i < itemCount; i++) {
                    // 获取最小宽度索引
                    int minIndex = ArrayUtils.getMinimumIndex(columnsWidthArrays);
                    // 累加宽度
                    columnsWidthArrays[minIndex] += itemWidthArrays[i];
                    // 累加数量
                    columnsItemNumberArrays[minIndex] += 1;
                    // 保存每行最大高度
                    columnsHeightArrays[minIndex] = Math.max(itemHeightArrays[i], columnsHeightArrays[minIndex]);
                }

                // 循环追加子项间分隔符占用的宽度
                for (int i = 0; i < lineNumber; i++) {
                    if (columnsItemNumberArrays[i] > 1) {
                        columnsWidthArrays[i] += (columnsItemNumberArrays[i] - 1) * horizontalSpacing;
                    }
                    if (i > 0) {
                        columnsTopArrays[i] = height + (i * verticalSpacing);
                    }
                    // 累加每行高度
                    height += columnsHeightArrays[i];
                }

                // 获取最大宽值
                int maxColumnsWidth = columnsWidthArrays[ArrayUtils.getMaximumIndex(columnsWidthArrays)];
                // 使用最大值
                height += (lineNumber - 1) * verticalSpacing;
                width = maxColumnsWidth;
                // 清空绘制时累加计算
                columnsWidthArrays = new int[lineNumber];
                // 创建位图
                Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(BACKGROUND_COLOR);
                // 循环绘制
                for (int i = 0; i < itemCount; i++) {
                    // 获取最小宽度索引
                    int    minIndex = ArrayUtils.getMinimumIndex(columnsWidthArrays);
                    Matrix matrix   = new Matrix();
                    matrix.postTranslate(columnsWidthArrays[minIndex], columnsTopArrays[minIndex]);
                    // 绘制到 Bitmap
                    Bitmap bmp = bitmaps[i];
                    canvas.drawBitmap(bmp, matrix, PAINT);
                    // 累加宽度
                    columnsWidthArrays[minIndex] += (itemWidthArrays[i] + horizontalSpacing);
                    // 释放资源
                    bmp.recycle();
                    bmp = null;
                }
                return bitmap;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapshotByRecyclerView_StaggeredGridLayoutManager");
        }
        return null;
    }

    // ===============
    // = 内部私有方法 =
    // ===============

    /**
     * 绘制 Bitmap
     * @param childView {@link View}
     * @param config    {@link Bitmap.Config}
     * @return {@link Bitmap}
     */
    private static Bitmap canvasBitmap(
            final View childView,
            final Bitmap.Config config
    ) {
        Bitmap bitmap = Bitmap.createBitmap(childView.getMeasuredWidth(), childView.getMeasuredHeight(), config);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(BACKGROUND_COLOR);
        childView.draw(canvas);
        return bitmap;
    }
}