package dev;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import dev.utils.BuildConfig;
import dev.utils.JCLogUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.AnalysisRecordUtils;
import dev.utils.app.FileRecordUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.cache.DevCache;
import dev.utils.app.logger.DevLoggerUtils;
import dev.utils.app.share.SharedUtils;

/**
 * detail: 开发工具类
 * Created by Ttt
 */
public final class DevUtils {

    /** 禁止构造对象,保证只有一个实例 */
    private DevUtils() {
    }

//    // DevUtils 实例
//    private static DevUtils INSTANCE = new DevUtils();
//
//    /** 获取 DevUtils 实例 ,单例模式 */
//    public static DevUtils getInstance() {
//        return INSTANCE;
//    }

    // ---

    /** 全局 Application 对象 */
    private static Application sApplication;
    /** 全局上下文 - getApplicationContext() */
    private static Context sContext;
    /** 获取当前线程,主要判断是否属于主线程 */
    private static Thread sUiThread;
    /** 全局Handler,便于子线程快捷操作等 */
    private static Handler sHandler;
    /** 是否内部debug模式 */
    private static boolean debug = false;

    /**
     * 默认初始化方法 - 必须调用 - Application.onCreate 中调用
     * @param context 上下文
     */
    public static void init(Context context) {
        // 设置全局上下文
        initContext(context);
        // 初始化全局 Application
        initApplication(context);
        // 初始化Shared 工具类
        SharedUtils.init(context);
        // 初始化记录文件配置
        FileRecordUtils.appInit();
        // 初始化记录工具类
        AnalysisRecordUtils.init(context);
        // 初始化 DevLogger 配置
        DevLoggerUtils.appInit(context);
        // 初始化Handler工具类
        HandlerUtils.init(context);
        // 初始化缓存类
        DevCache.get(context);
        // 保存当前线程信息
        sUiThread = Thread.currentThread();
        // 初始化全局Handler - 主线程
        sHandler = new Handler(Looper.getMainLooper());
        // 注册 Activity 生命周期监听
        registerActivityLifecycleCallbacks(sApplication);
    }

    /**
     * 初始化全局上下文
     * @param context
     */
    private static void initContext(Context context) {
        // 如果为null, 才进行判断处理
        if (DevUtils.sContext == null){
            // 防止传进来的为null
            if (context == null) {
                return;
            }
            DevUtils.sContext = context.getApplicationContext();
        }
    }

    /**
     * 初始化全局 Application
     * @param context
     */
    private static void initApplication(Context context) {
        // 如果为null, 才进行判断处理
        if (DevUtils.sApplication == null){
            if (context == null){
                return;
            }
            Application mApplication = null;
            try {
                mApplication = (Application) context.getApplicationContext();
            } catch (Exception e){
            }
            // 防止传进来的为null
            if (mApplication == null) {
                return;
            }
            DevUtils.sApplication = mApplication;
        }
    }

    /**
     * 获取全局上下文
     * @return
     */
    public static Context getContext() {
        return DevUtils.sContext;
    }

    /**
     * 获取上下文(判断null,视情况返回全局上下文)
     * @param context
     */
    public static Context getContext(Context context) {
        // 进行判断
        if (context != null){
            return context;
        }
        return DevUtils.sContext;
    }

    /**
     * 获取全局 Application
     * @return
     */
    public static Application getApplication(){
        return DevUtils.sApplication;
    }

    /**
     * 获取Handler
     * @return
     */
    public static Handler getHandler(){
        if (sHandler == null){
            // 初始化全局Handler - 主线程
            sHandler = new Handler(Looper.getMainLooper()); //Looper.myLooper();
        }
        return sHandler;
    }

    /**
     * 执行UI 线程任务 =>  Activity 的 runOnUiThread(Runnable)
     * @param action 若当前非UI线程则切换到UI线程执行
     */
    public static void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != sUiThread) {
            sHandler.post(action);
        } else {
            action.run();
        }
    }

    /**
     * 执行UI 线程任务 => 延时执行
     * @param action
     * @param delayMillis
     */
    public static void runOnUiThread(Runnable action, long delayMillis){
        sHandler.postDelayed(action, delayMillis);
    }

    /**
     * 打开日志
     */
    public static void openLog() {
        // 专门打印 Android 日志信息
        LogPrintUtils.setPrintLog(true);
        // 专门打印 Java 日志信息
        JCLogUtils.setPrintLog(true);
    }

    /**
     * 标记debug模式
     */
    public static void openDebug() {
        DevUtils.debug = true;
    }

    /**
     * 判断是否Debug模式
     * @return
     */
    public static boolean isDebug() {
        return debug;
    }

    // ==================
    // ==== Activity ====
    // ==================

    /**
     * 注册绑定Activity 生命周期事件处理
     * @param application
     */
    private static void registerActivityLifecycleCallbacks(Application application){
        if (application != null){
            // 先移除旧的监听
            application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
            // 绑定新的监听
            application.registerActivityLifecycleCallbacks(lifecycleCallbacks);
        }
    }

    /** 保留当前(前台) Activity */
    private static Activity sCurActivity = null;

    /**
     * 对Activity的生命周期事件进行集中处理。
     * http://blog.csdn.net/tongcpp/article/details/40344871
     */
    private static Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            // 保存当前Activity
            DevUtils.sCurActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    /**
     * 获取当前Activity
     * @return
     */
    public static Activity getCurActivity(){
        return DevUtils.sCurActivity;
    }

    /**
     * 判断是否相同的 Activity
     * @param activity
     * @return
     */
    public static boolean isSameActivity(Activity activity){
        if (activity != null && DevUtils.sCurActivity != null){
            try {
                return DevUtils.sCurActivity.getClass().getName().equals(activity.getClass().getName());
            } catch (Exception e){
            }
        }
        return false;
    }

    // == 工具类版本 ==

    /**
     * 获取工具类版本
     * @return
     */
    public static String getUtilsVersion(){
        return BuildConfig.VERSION_NAME;
    }
}
