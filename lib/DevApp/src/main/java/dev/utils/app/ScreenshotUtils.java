package dev.utils.app;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import dev.utils.LogPrintUtils;

/**
 * detail: 截图监听工具类
 * @author Ttt
 * <pre>
 *     截图使用 {@link CapturePictureUtils}
 * </pre>
 */
public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    // 日志 TAG
    private static final String TAG = ScreenshotUtils.class.getSimpleName();

    // ScreenshotUtils 实例
    private volatile static ScreenshotUtils sInstance;

    /**
     * 获取 ScreenshotUtils 实例
     * @return {@link ScreenshotUtils}
     */
    public static ScreenshotUtils getInstance() {
        if (sInstance == null) {
            synchronized (ScreenshotUtils.class) {
                if (sInstance == null) {
                    sInstance = new ScreenshotUtils();
                }
            }
        }
        return sInstance;
    }

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 截图校验成功回调接口
     * @author Ttt
     */
    public interface OnScreenshotListener {

        /**
         * 截图校验成功回调
         * @param contentUri 监听 Uri
         * @param rowId      数据 id
         * @param dataPath   数据路径 ( 截图路径 )
         * @param dateTaken  截图时间
         */
        void onScreenshot(Uri contentUri, long rowId, String dataPath, String dateTaken);
    }

    /**
     * detail: 截图校验接口
     * @author Ttt
     */
    public interface ScreenshotChecker {

        /**
         * 内容变更通知
         * @param contentUri 监听 Uri
         * @param selfChange True if this is a self-change notification
         */
        void onChange(Uri contentUri, boolean selfChange);

        /**
         * 检查方法
         * @param contentUri 监听 Uri
         * @param selfChange True if this is a self-change notification
         * @param rowId      数据 id
         * @param dataPath   数据路径 ( 截图路径 )
         * @param dateTaken  截图时间
         */
        void onChecker(Uri contentUri, boolean selfChange,
                       long rowId, String dataPath, String dateTaken);
    }

    // ===========
    // = 事件操作 =
    // ===========

    // 内部存储器内容观察者
    private MediaContentObserver mInternalObserver;
    // 外部存储器内容观察者
    private MediaContentObserver mExternalObserver;

    /**
     * detail: 媒体内容观察者 ( 监听媒体数据库改变 )
     * @author Ttt
     */
    private class MediaContentObserver extends ContentObserver {

        // 监听 Uri
        private Uri mContentUri;

        public MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            mContentUri = contentUri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // 触发回调
            getScreenshotChecker().onChange(mContentUri, selfChange);
        }
    }

    /**
     * 注册 ContentResolver 内容改变监听事件
     * @param resolver             {@link ContentResolver}
     * @param handler              {@link Handler}
     * @param notifyForDescendants 是否精准匹配 Uri
     * @return {@code true} success, {@code false} fail
     */
    private boolean registerContentObserver(final ContentResolver resolver, final Handler handler,
                                            final boolean notifyForDescendants) {
        if (resolver == null || handler == null) return false;
        // 注销内容观察者
        unregisterContentObserver(resolver);
        // 创建内容观察者
        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, handler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, handler);
        // 注册内容观察者
        resolver.registerContentObserver(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                notifyForDescendants, mInternalObserver
        );
        resolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                notifyForDescendants, mExternalObserver
        );
        return true;
    }

    /**
     * 注销 ContentResolver 内容改变监听事件
     * @param resolver {@link ContentResolver}
     * @return {@code true} success, {@code false} fail
     */
    private boolean unregisterContentObserver(final ContentResolver resolver) {
        if (resolver == null) return false;
        // 注销内容观察者
        if (mInternalObserver != null) {
            try {
                resolver.unregisterContentObserver(mInternalObserver);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "unregisterContentObserver");
            }
            mInternalObserver = null;
        }
        if (mExternalObserver != null) {
            try {
                resolver.unregisterContentObserver(mExternalObserver);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "unregisterContentObserver");
            }
            mExternalObserver = null;
        }
        return true;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    // 开始监听时间
    private long                 mStartListenTime;
    // 截图校验接口
    private ScreenshotChecker    mScreenshotChecker;
    // 截图校验成功回调接口
    private OnScreenshotListener mListener;

    /**
     * 获取开始监听时间
     * @return 开始监听时间
     */
    public long getStartListenTime() {
        return mStartListenTime;
    }

    /**
     * 获取截图校验接口
     * @return {@link ScreenshotChecker}
     */
    public ScreenshotChecker getScreenshotChecker() {
        if (mScreenshotChecker != null) {
            return mScreenshotChecker;
        }
        return CHECKER;
    }

    /**
     * 设置截图校验接口
     * @param checker {@link ScreenshotChecker}
     * @return {@link ScreenshotUtils}
     */
    public ScreenshotUtils setScreenshotChecker(final ScreenshotChecker checker) {
        this.mScreenshotChecker = checker;
        return this;
    }

    /**
     * 获取截图校验成功回调接口
     * @return {@link OnScreenshotListener}
     */
    public OnScreenshotListener getListener() {
        return mListener;
    }

    /**
     * 设置截图校验成功回调接口
     * @param listener {@link OnScreenshotListener}
     * @return {@link ScreenshotUtils}
     */
    public ScreenshotUtils setListener(final OnScreenshotListener listener) {
        this.mListener = listener;
        return this;
    }

    // =

    /**
     * 启动截图监听
     * @return {@code true} success, {@code false} fail
     */
    public boolean startListen() {
        return startListen(true, HandlerUtils.getMainHandler());
    }

    /**
     * 启动截图监听
     * @param notifyForDescendants 是否精准匹配 Uri
     * @return {@code true} success, {@code false} fail
     */
    public boolean startListen(final boolean notifyForDescendants) {
        return startListen(notifyForDescendants, HandlerUtils.getMainHandler());
    }

    /**
     * 启动截图监听
     * @param notifyForDescendants 是否精准匹配 Uri
     * @param handler              {@link Handler}
     * @return {@code true} success, {@code false} fail
     */
    public boolean startListen(final boolean notifyForDescendants, final Handler handler) {
        this.mStartListenTime = System.currentTimeMillis();
        return registerContentObserver(
                ResourceUtils.getContentResolver(), handler, notifyForDescendants
        );
    }

    /**
     * 停止截图监听
     * @return {@code true} success, {@code false} fail
     */
    public boolean stopListen() {
        return unregisterContentObserver(ResourceUtils.getContentResolver());
    }

    // ===========
    // = 内部处理 =
    // ===========

    // 读取媒体数据库时需要读取的列
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };
    // 截图关键字前缀判断
    public static final  String   PREFIX_SCREEN     = "screen";
    // 检测间隔时间
    public static final  long     INTERVAL_TIME     = 8000L;

    // 截图校验接口
    public static final ScreenshotChecker CHECKER = new ScreenshotChecker() {
        @Override
        public void onChange(Uri contentUri, boolean selfChange) {

        }

        @Override
        public void onChecker(Uri contentUri, boolean selfChange, long rowId, String dataPath, String dateTaken) {

        }
    };
}