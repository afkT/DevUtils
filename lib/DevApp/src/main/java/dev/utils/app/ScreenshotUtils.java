package dev.utils.app;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import java.util.Arrays;

import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 截图监听工具类
 * @author Ttt
 * <pre>
 *     View 截图使用 {@link CapturePictureUtils}
 *     <p></p>
 *     {@link ScreenshotUtils} 使用方法
 *     ScreenshotUtils.getInstance().setListener(new ScreenshotUtils.OnScreenshotListener() {
 *             @Override
 *             public void onScreenshot(Uri contentUri, boolean selfChange, long rowId, String dataPath, long dateTaken) {
 *             }
 *         }).startListen();
 *     <p></p>
 *     注意事项
 *     1.部分机型会多次触发 onChange 需自行处理
 *     2.目前 onChecker 使用时间 + 文件前缀校验, 可自行新增截图宽高校验
 *     3.支持自定义 ScreenshotChecker 截图校验逻辑接口, 可自行实现特殊校验需求
 *     4.如截图无触发先检查读写权限、以及 notifyForDescendants 传参为 true 处理
 * </pre>
 */
public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    // 日志 TAG
    private static final String TAG = ScreenshotUtils.class.getSimpleName();

    // ScreenshotUtils 实例
    private static volatile ScreenshotUtils sInstance;

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
         * @param selfChange True if this is a self-change notification
         * @param rowId      数据 id
         * @param dataPath   数据路径 ( 截图路径 )
         * @param dateTaken  截图时间
         */
        void onScreenshot(
                Uri contentUri,
                boolean selfChange,
                long rowId,
                String dataPath,
                long dateTaken
        );
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
        void onChange(
                Uri contentUri,
                boolean selfChange
        );

        /**
         * 检查方法
         * @param contentUri 监听 Uri
         * @param selfChange True if this is a self-change notification
         * @param rowId      数据 id
         * @param dataPath   数据路径 ( 截图路径 )
         * @param dateTaken  截图时间
         */
        void onChecker(
                Uri contentUri,
                boolean selfChange,
                long rowId,
                String dataPath,
                long dateTaken
        );
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
    private class MediaContentObserver
            extends ContentObserver {

        // 监听 Uri
        private final Uri mContentUri;

        public MediaContentObserver(
                Uri contentUri,
                Handler handler
        ) {
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
    private boolean registerContentObserver(
            final ContentResolver resolver,
            final Handler handler,
            final boolean notifyForDescendants
    ) {
        if (resolver == null) return false;
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
    // 是否判断文件名前缀
    private boolean              mCheckPrefix = true;
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
     * 是否判断文件名前缀
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCheckPrefix() {
        return mCheckPrefix;
    }

    /**
     * 设置是否判断文件名前缀
     * @param checkPrefix 是否判断文件名前缀
     * @return {@link ScreenshotUtils}
     */
    public ScreenshotUtils setCheckPrefix(final boolean checkPrefix) {
        this.mCheckPrefix = checkPrefix;
        return this;
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
    public boolean startListen(
            final boolean notifyForDescendants,
            final Handler handler
    ) {
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
    // 排序字段
    public static final  String   SORT_ORDER        = MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1";
    // 截图关键字前缀判断
    public static final  String   PREFIX_SCREEN     = "screen";
    // 检测间隔时间
    public static final  long     INTERVAL_TIME     = 8000L;

    // 截图校验接口
    public static final ScreenshotChecker CHECKER = new ScreenshotChecker() {
        @Override
        public void onChange(
                Uri contentUri,
                boolean selfChange
        ) {
            handleMediaContentChange(contentUri, selfChange, SORT_ORDER, this);
        }

        @Override
        public void onChecker(
                Uri contentUri,
                boolean selfChange,
                long rowId,
                String dataPath,
                long dateTaken
        ) {
            handleMediaChecker(
                    contentUri, selfChange, rowId, dataPath, dateTaken,
                    getInstance().mStartListenTime, INTERVAL_TIME,
                    getInstance().isCheckPrefix(), PREFIX_SCREEN,
                    getInstance().getListener()
            );
        }
    };

    /**
     * 内容变更处理
     * @param contentUri 监听 Uri
     * @param selfChange True if this is a self-change notification
     * @param sortOrder  排序方式
     * @param checker    搜索成功则会触发 {@link ScreenshotChecker#onChecker} 方法
     */
    public static void handleMediaContentChange(
            final Uri contentUri,
            final boolean selfChange,
            final String sortOrder,
            final ScreenshotChecker checker
    ) {
        Cursor cursor = ContentResolverUtils.query(contentUri, MEDIA_PROJECTIONS,
                null, null, sortOrder);
        try {
            if (cursor == null) {
                LogPrintUtils.dTag(TAG, "搜索失败 uri: %s, projection: %s",
                        contentUri, Arrays.toString(MEDIA_PROJECTIONS));
                return;
            }
            if (!cursor.moveToFirst()) {
                LogPrintUtils.dTag(TAG, "搜索成功, 但无符合条件数据 uri: %s, projection: %s",
                        contentUri, Arrays.toString(MEDIA_PROJECTIONS));
                return;
            }
            // 获取数据
            long rowId = cursor.getLong(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)
            );
            String data = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            );
            long dateTaken = cursor.getLong(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)
            );
            if (checker != null) {
                checker.onChecker(
                        contentUri, selfChange, rowId, data, dateTaken
                );
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handleMediaContentChange");
        } finally {
            CloseUtils.closeIOQuietly(cursor);
        }
    }

    /**
     * 内容校验处理
     * @param contentUri      监听 Uri
     * @param selfChange      True if this is a self-change notification
     * @param rowId           数据 id
     * @param dataPath        数据路径 ( 截图路径 )
     * @param dateTaken       截图时间
     * @param startListenTime 开始监听时间 ( 防止出现开始监听时间前的资源 )
     * @param intervalTime    文件创建时间间隔
     * @param checkPrefix     是否判断文件名前缀
     * @param keyWork         文件名前缀判断
     * @param listener        校验成功则会触发 {@link OnScreenshotListener#onScreenshot} 方法
     * @return {@code true} yes, {@code false} no
     */
    public static boolean handleMediaChecker(
            final Uri contentUri,
            final boolean selfChange,
            final long rowId,
            final String dataPath,
            final long dateTaken,
            final long startListenTime,
            final long intervalTime,
            final boolean checkPrefix,
            final String keyWork,
            final OnScreenshotListener listener
    ) {
        // ===========
        // = 时间判断 =
        // ===========

        if (dateTaken <= 0) {
            LogPrintUtils.dTag(TAG, "创建时间异常 dateTaken: %s", dateTaken);
            return false;
        }

        if (dateTaken < startListenTime) { // 创建时间小于开始监听时间
            LogPrintUtils.dTag(TAG,
                    "开始监听时间校验不通过 dateTaken: %s, startListenTime: %s, diff: %s",
                    dateTaken, startListenTime, (dateTaken - startListenTime)
            );
            return false;
        }

        // 获取当前时间
        long curTime = System.currentTimeMillis();
        if (curTime - dateTaken > intervalTime) { // 文件创建时间超过间隔时间
            LogPrintUtils.dTag(TAG,
                    "文件间隔时间校验不通过 dateTaken: %s, curTime: %s, intervalTime: %s, diff: %s",
                    dateTaken, curTime, intervalTime, (curTime - dateTaken)
            );
            return false;
        }

        // ===========
        // = 文件前缀 =
        // ===========

        if (checkPrefix) { // 如果检验前缀才进行处理
            String fileName = FileUtils.getFileName(dataPath);
            // 前缀判断结果
            boolean checkResult = (
                    fileName != null && keyWork != null
                            && fileName.toLowerCase().startsWith(keyWork.toLowerCase())
            );
            if (!checkResult) {
                LogPrintUtils.dTag(TAG,
                        "文件前缀校验不通过 dataPath: %s, fileName: %s, keyWork: %s",
                        dataPath, fileName, keyWork
                );
                return false;
            }
        }

        // 触发回调
        if (listener != null) {
            listener.onScreenshot(contentUri, selfChange, rowId, dataPath, dateTaken);
        }
        return true;
    }
}