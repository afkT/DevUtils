package dev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import androidx.core.content.FileProvider;

import java.io.File;

import dev.utils.BuildConfig;
import dev.utils.JCLogUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.UriUtils;
import dev.utils.app.assist.lifecycle.AbstractActivityLifecycle;
import dev.utils.app.assist.lifecycle.ActivityLifecycleAssist;
import dev.utils.app.assist.lifecycle.ActivityLifecycleFilter;
import dev.utils.app.assist.lifecycle.ActivityLifecycleGet;
import dev.utils.app.assist.lifecycle.ActivityLifecycleNotify;
import dev.utils.app.assist.record.AppRecordInsert;
import dev.utils.app.toast.toaster.DevToast;
import dev.utils.common.FileUtils;
import dev.utils.common.assist.record.FileRecordUtils;

/**
 * detail: 开发工具类
 * @author Ttt
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 *     DevBase README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md"/>
 *     DevBaseMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md"/>
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevHttpCapture Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md"/>
 *     DevHttpManager Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md"/>
 *     DevRetrofit Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 *     DevWidget Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md"/>
 *     DevEnvironment Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/Environment"/>
 * </pre>
 */
public final class DevUtils {

    private DevUtils() {
    }

    // 日志 TAG
    public static final String TAG = DevUtils.class.getSimpleName();

    // 全局 Application 对象
    private static Application sApplication;
    // 全局 Context - getApplicationContext()
    private static Context     sContext;
    // 是否内部 Debug 模式
    private static boolean     sDebug = false;

    /**
     * 初始化方法 ( 必须调用 )
     * @param context {@link Context}
     */
    public static void init(final Context context) {
        if (context == null) return;

        // 初始化全局 Context
        initContext(context);
        // 初始化全局 Application
        initApplication(context);
        // 注册 Activity 生命周期监听
        getLifecycleAssist().registerActivityLifecycleCallbacks();

        // =================
        // = 初始化工具类相关 =
        // =================

        // 初始化 Record
        FileRecordUtils.setRecordInsert(new AppRecordInsert(false));
        // 初始化 Toast
        DevToast.initialize(context);

        // ============
        // = Java Log =
        // ============

        // 设置 Java 模块日志信息在 logcat 输出
        JCLogUtils.setPrint((logType, tag, message) -> {
            switch (logType) {
                case JCLogUtils.INFO:
                    LogPrintUtils.iTag(tag, message);
                case JCLogUtils.ERROR:
                    LogPrintUtils.eTag(tag, message);
                    break;
                case JCLogUtils.DEBUG:
                default:
                    LogPrintUtils.dTag(tag, message);
                    break;
            }
        });
    }

    /**
     * 初始化全局 Context
     * @param context {@link Context}
     */
    private static void initContext(final Context context) {
        if (DevUtils.sContext == null && context != null) {
            DevUtils.sContext = context.getApplicationContext();
        }
    }

    /**
     * 初始化全局 Application
     * @param context {@link Context}
     */
    private static void initApplication(final Context context) {
        if (DevUtils.sApplication == null && context != null) {
            try {
                DevUtils.sApplication = (Application) context.getApplicationContext();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 获取全局 Context
     * @return {@link Context}
     */
    public static Context getContext() {
        return DevUtils.sContext;
    }

    /**
     * 获取 Context ( 判断 null, 视情况返回全局 Context )
     * @param context {@link Context}
     * @return {@link Context}
     */
    public static Context getContext(final Context context) {
        return (context != null) ? context : DevUtils.sContext;
    }

    /**
     * 获取全局 Application
     * @return {@link Application}
     */
    public static Application getApplication() {
        if (DevUtils.sApplication != null) return DevUtils.sApplication;
        try {
            Application application = getApplicationByReflect();
            init(application); // 初始化操作
            return application;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getApplication");
        }
        return null;
    }

    // =

    /**
     * 反射获取 Application
     * @return {@link Application}
     */
    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app    = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getApplicationByReflect");
        }
        throw new NullPointerException("u should init first");
    }

    // =

    /**
     * 获取 Handler
     * @return {@link Handler}
     */
    public static Handler getHandler() {
        return HandlerUtils.getMainHandler();
    }

    /**
     * 获取 Handler ( 判断 null, 视情况返回全局 Handler )
     * @param handler {@link Handler}
     * @return {@link Handler}
     */
    public static Handler getHandler(final Handler handler) {
        if (handler != null) return handler;
        return HandlerUtils.getMainHandler();
    }

    /**
     * 执行 UI 线程任务
     * @param runnable 线程任务
     */
    public static void runOnUiThread(final Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
    }

    /**
     * 执行 UI 线程任务 ( 延时执行 )
     * @param runnable    线程任务
     * @param delayMillis 延时执行时间 ( 毫秒 )
     */
    public static void runOnUiThread(
            final Runnable runnable,
            final long delayMillis
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis);
    }

    /**
     * 开启日志开关
     */
    public static void openLog() {
        // 专门打印 Android 日志信息
        LogPrintUtils.setPrintLog(true);
        // 专门打印 Java 日志信息
        JCLogUtils.setPrintLog(true);
    }

    /**
     * 标记 Debug 模式
     */
    public static void openDebug() {
        DevUtils.sDebug = true;
    }

    /**
     * 判断是否 Debug 模式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDebug() {
        return sDebug;
    }

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevApp 版本号
     * @return DevApp versionCode
     */
    public static int getDevAppVersionCode() {
        return BuildConfig.DevApp_VersionCode;
    }

    /**
     * 获取 DevApp 版本
     * @return DevApp versionName
     */
    public static String getDevAppVersion() {
        return BuildConfig.DevApp_Version;
    }

    /**
     * 获取 DevJava 版本号
     * @return DevJava version
     */
    public static int getDevJavaVersionCode() {
        return BuildConfig.DevJava_VersionCode;
    }

    /**
     * 获取 DevJava 版本
     * @return DevJava version
     */
    public static String getDevJavaVersion() {
        return BuildConfig.DevJava_Version;
    }

    // ================
    // = Activity 监听 =
    // ================

    // ActivityLifecycleAssist 实例
    private static volatile ActivityLifecycleAssist sInstance;

    /**
     * 获取 ActivityLifecycleAssist 管理实例
     * @return {@link ActivityLifecycleAssist}
     */
    private static ActivityLifecycleAssist getLifecycleAssist() {
        if (sInstance == null) {
            synchronized (ActivityLifecycleAssist.class) {
                if (sInstance == null) {
                    sInstance = new ActivityLifecycleAssist();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取 Activity 生命周期 相关信息获取接口类
     * @return {@link ActivityLifecycleGet}
     */
    public static ActivityLifecycleGet getActivityLifecycleGet() {
        return getLifecycleAssist().getActivityLifecycleGet();
    }

    /**
     * 获取 Activity 生命周期 事件监听接口类
     * @return {@link ActivityLifecycleNotify}
     */
    public static ActivityLifecycleNotify getActivityLifecycleNotify() {
        return getLifecycleAssist().getActivityLifecycleNotify();
    }

    /**
     * 获取 Top Activity
     * @return {@link Activity}
     */
    public static Activity getTopActivity() {
        return getLifecycleAssist().getTopActivity();
    }

    /**
     * 设置 Activity 生命周期 过滤判断接口
     * @param activityLifecycleFilter Activity 过滤判断接口
     */
    public static void setActivityLifecycleFilter(final ActivityLifecycleFilter activityLifecycleFilter) {
        getLifecycleAssist().setActivityLifecycleFilter(activityLifecycleFilter);
    }

    /**
     * 设置 ActivityLifecycle 监听回调
     * @param abstractActivityLifecycle Activity 生命周期监听类
     */
    public static void setAbstractActivityLifecycle(final AbstractActivityLifecycle abstractActivityLifecycle) {
        getLifecycleAssist().setAbstractActivityLifecycle(abstractActivityLifecycle);
    }

    // ================
    // = FileProvider =
    // ================

    // 获取 lib utils fileProvider
    public static final String LIB_FILE_PROVIDER = "devapp.provider";

    /**
     * 获取 FileProvider Authority
     * @return FileProvider Authority
     */
    public static String getAuthority() {
        try {
            return DevUtils.getContext().getPackageName() + "." + LIB_FILE_PROVIDER;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAuthority");
        }
        return null;
    }

    /**
     * 获取 FileProvider File Uri
     * @param file 文件
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForFile(final File file) {
        return UriUtils.getUriForFile(file, getAuthority());
    }

    /**
     * 获取 FileProvider File Path Uri
     * @param filePath 文件路径
     * @return 指定文件 {@link Uri}
     */
    public static Uri getUriForPath(final String filePath) {
        return UriUtils.getUriForFile(FileUtils.getFileByPath(filePath), getAuthority());
    }

    /**
     * detail: FileProvider
     * @author Ttt
     */
    public static final class FileProviderDevApp
            extends FileProvider {
        @Override
        public boolean onCreate() {
            init(getContext().getApplicationContext());
            return true;
        }
    }
}