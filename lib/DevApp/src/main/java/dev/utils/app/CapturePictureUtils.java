package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 截图工具类
 * @author Ttt
 * <pre>
 *     截图
 *     @see <a href="https://www.cnblogs.com/angel88/p/7933437.html"/>
 * </pre>
 */
public final class CapturePictureUtils {

    // 日志 TAG
    private static final String TAG = CapturePictureUtils.class.getSimpleName();

    private CapturePictureUtils() {
    }

    // ========
    // = 截图 =
    // ========

    // ===========
    // = WebView =
    // ===========

    /**
     * 截图 WebView
     * @param webView {@link WebView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView) {
        return snapshotByWebView(webView, Integer.MAX_VALUE, Bitmap.Config.RGB_565);
    }

    /**
     * 截图 WebView
     * @param webView {@link WebView}
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView, final int maxHeight) {
        return snapshotByWebView(webView, maxHeight, Bitmap.Config.RGB_565);
    }

    /**
     * 截图 WebView
     * @param webView   {@link WebView}
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap snapshotByWebView(final WebView webView, final int maxHeight, final Bitmap.Config config) {
        if (webView != null && config != null) {
            // Android 5.0 以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    // 获取 Picture 对象
                    Picture picture = webView.capturePicture();
                    int width = picture.getWidth();
                    int height = picture.getHeight();
                    if (width > 0 && height > 0) {
                        // 重新设置高度
                        height = (height > maxHeight) ? maxHeight : height;
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        picture.draw(canvas);
                        return bitmap;
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "snapshotByWebView - capturePicture()");
                }
            } else {
                try {
                    // 获取 Picture 对象
                    Picture picture = webView.capturePicture();
                    int width = picture.getWidth();
                    int height = picture.getHeight();
                    if (width > 0 && height > 0) {
                        // 重新设置高度
                        height = (height > maxHeight) ? maxHeight : height;
                        // 创建位图
                        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
                        Canvas canvas = new Canvas(bitmap);
                        picture.draw(canvas);
                        return bitmap;
                    }
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "snapshotByWebView - capturePicture()");
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
        if (view == null) return null;
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
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
}
