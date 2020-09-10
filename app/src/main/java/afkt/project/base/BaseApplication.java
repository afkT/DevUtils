package afkt.project.base;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;

import androidx.multidex.MultiDexApplication;

import com.tencent.mmkv.MMKV;

import afkt.project.R;
import afkt.project.base.config.AppConfig;
import afkt.project.base.config.PathConfig;
import afkt.project.db.GreenManager;
import afkt.project.function.http.RetrofitUtils;
import dev.DevUtils;
import dev.assist.WebViewAssist;
import dev.environment.DevEnvironment;
import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;
import dev.environment.listener.OnEnvironmentChangeListener;
import dev.other.GlideUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.ActivityUtils;
import dev.utils.app.AppCommonUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.CrashUtils;
import dev.utils.app.PathUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.logger.LogConfig;
import dev.utils.app.logger.LogLevel;
import dev.utils.common.DateUtils;
import dev.utils.common.FileRecordUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.assist.TimeCounter;
import dev.widget.assist.ViewAssist;
import dev.widget.function.StateLayout;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * detail: Base Application
 * @author Ttt
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化计时器
        TimeCounter timeCounter = new TimeCounter();

        // ============
        // = DevUtils =
        // ============

//        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
//        DevUtils.init(this.getApplicationContext());
        // 初始化日志配置
        DevLogger.init(
                new LogConfig().logLevel(LogLevel.DEBUG)
                        .tag(AppConfig.LOG_TAG)
                        .sortLog(true) // 美化日志, 边框包围
                        .methodCount(0)
        );
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog();
        DevUtils.openDebug();

        // 可进行日志拦截编码
        // DevLogger.setPrint(new DevLogger.Print());
        // JCLogUtils.setPrint(new JCLogUtils.Print());
        LogPrintUtils.setPrint(new LogPrintUtils.Print() {
            @Override
            public void printLog(int logType, String tag, String message) {
                // 防止 null 处理
                if (message == null) return;
                // 进行编码处理
                message = StringUtils.strEncode(message, "UTF-8");
                // 获取日志类型
                switch (logType) {
                    case Log.VERBOSE:
                        Log.v(tag, message);
                        break;
                    case Log.DEBUG:
                        Log.d(tag, message);
                        break;
                    case Log.INFO:
                        Log.i(tag, message);
                        break;
                    case Log.WARN:
                        Log.w(tag, message);
                        break;
                    case Log.ERROR:
                        Log.e(tag, message);
                        break;
                    case Log.ASSERT:
                        Log.wtf(tag, message);
                        break;
                    default:
                        Log.wtf(tag, message);
                        break;
                }
            }
        });

        // ==============
        // = 初始化操作 =
        // ==============

        // 初始化
        init();

        // 属于 Debug 才打印信息
        if (isDebug()) {
            printProInfo(timeCounter);
        }
    }

    /**
     * 是否 Debug 模式
     * @return {@code true} yes, {@code false} no
     */
    public static final boolean isDebug() {
        return DevUtils.isDebug();
    }

    /**
     * 打印项目信息
     * @param timeCounter {@link TimeCounter}
     */
    private void printProInfo(TimeCounter timeCounter) {
        StringBuilder builder = new StringBuilder();
        builder.append("项目名: " + ResourceUtils.getString(R.string.str_app_name));
        builder.append("\nSDK: " + Build.VERSION.SDK_INT + "(" + AppCommonUtils.convertSDKVersion(Build.VERSION.SDK_INT) + ")");
        builder.append("\nPackageName: " + AppUtils.getPackageName());
        builder.append("\nVersionCode: " + AppUtils.getAppVersionCode());
        builder.append("\nVersionName: " + AppUtils.getAppVersionName());
        builder.append("\nDevUtils 版本: " + DevUtils.getDevAppUtilsVersion());
        builder.append("\nDevJava 版本: " + DevUtils.getDevJavaUtilsVersion());
        builder.append("\n时间: " + DateUtils.getDateNow());
        builder.append("\n初始化耗时(毫秒): " + timeCounter.duration());
        DevLogger.i(builder.toString());
    }

    // ==============
    // = 初始化方法 =
    // ==============

    /**
     * 统一初始化方法
     */
    private void init() {
        // 初始化项目文件夹
        PathConfig.createFolder();
        // 插入设备信息
        FileRecordUtils.setInsertInfo(AppCommonUtils.getAppDeviceInfo());
        // 初始化 Glide
        GlideUtils.init(this);
        // 初始化状态布局配置
        initStateLayout();
        // 初始化异常捕获处理
        initCrash();
        // 初始化 WebView 辅助类全局配置
        initWebViewBuilder();
        // 初始化其他 lib
        initOther();
    }

    /**
     * 初始化状态布局配置
     */
    private void initStateLayout() {
        StateLayout.Global global = new StateLayout.Global(new StateLayout.Listener() {
            @Override
            public void onRemove(StateLayout layout, int type, boolean removeView) {
                if (removeView) layout.gone();
            }

            @Override
            public void onNotFound(StateLayout layout, int type) {
                layout.gone();
            }

            @Override
            public void onChange(StateLayout layout, int type, int oldType, View view) {
                if (type == ViewAssist.TYPE_EMPTY_DATA) { // NO_DATA
                    View vid_slnd_tips_tv = ViewUtils.findViewById(view, R.id.vid_slnd_tips_tv);
                    TextViewUtils.setText(vid_slnd_tips_tv, "暂无数据");
                } else if (type == ViewAssist.TYPE_FAILED) { // FAIL
                    View vid_slf_tips_tv = ViewUtils.findViewById(view, R.id.vid_slf_tips_tv);
                    TextViewUtils.setText(vid_slf_tips_tv, "请求失败, 请稍后重试!");
                }
            }
        })
                .register(ViewAssist.TYPE_ING, R.layout.state_layout_ing)
                .register(ViewAssist.TYPE_FAILED, R.layout.state_layout_fail)
                .register(ViewAssist.TYPE_EMPTY_DATA, R.layout.state_layout_no_data);
        // 设置全局配置
        StateLayout.setGlobal(global);
    }

    /**
     * 初始化异常捕获处理
     */
    private void initCrash() {
        // 捕获异常处理 => 在 BaseApplication 中调用
        CrashUtils.getInstance().init(getApplicationContext(), new CrashUtils.CrashCatchListener() {
            @Override
            public void handleException(Throwable ex) {
                // 保存日志信息
                FileRecordUtils.saveErrorLog(ex, PathConfig.AEP_ERROR_PATH, "crash_" + DateUtils.getDateNow() + ".txt");
            }

            @Override
            public void uncaughtException(Context context, Thread thread, Throwable ex) {
//                // 退出 JVM (Java 虚拟机 ) 释放所占内存资源, 0 表示正常退出、非 0 的都为异常退出
//                System.exit(-1);
//                // 从操作系统中结束掉当前程序的进程
//                android.os.Process.killProcess(android.os.Process.myPid());
                // 关闭 APP
                ActivityUtils.getManager().exitApplication();
                // 可开启定时任务, 延迟几秒启动 APP
            }
        });
    }

    /**
     * 初始化 WebView 辅助类全局配置
     */
    private void initWebViewBuilder() {
        WebViewAssist.Builder builder = new WebViewAssist.Builder();
        builder.setBuiltInZoomControls(true) // 显示内置缩放工具
                .setDisplayZoomControls(true) // 显示缩放工具
                .setAppCachePath(PathUtils.getInternal().getAppCachePath("cache")) // Application Caches 地址
                .setDatabasePath(PathUtils.getInternal().getAppCachePath("db")) // 数据库缓存路径
                .setRenderPriority(WebSettings.RenderPriority.HIGH) // 渲染优先级高
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING) // 基础布局算法
                .setOnApplyListener(new WebViewAssist.Builder.OnApplyListener() {
                    @Override
                    public void onApply(WebViewAssist webViewAssist, WebViewAssist.Builder builder) {
                        DevLogger.d("WebViewAssist Builder onApply");
                        if (webViewAssist != null) {
//                            // 自己控制配置
//                            WebSettings webSettings = webViewAssist.getSettings();
//                            if (webSettings != null) {
//                                webSettings.setAllowFileAccess(true);
//                            }
                        }
                    }
                });
        WebViewAssist.setGlobalBuilder(builder);
    }

    /**
     * 初始化其他 lib
     */
    private void initOther() {
        // xCrash 提供捕获 java 崩溃、native 崩溃和 ANR 的能力, 不需要 root 权限或任何系统权限
        xcrash.XCrash.init(this);

        // 初始化 MMKV
        String rootDir = MMKV.initialize(this);
        DevLogger.d("MMKV rootDir: " + rootDir);

        // https://github.com/JessYanCoding/AndroidAutoSize/blob/master/demo-subunits/src/main/java/me/jessyan/autosize/demo/subunits/BaseApplication.java
        // 可不调用, 默认开启 DP 转换
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true);

        // 初始化 GreenDao
        GreenManager.init(this);

//        // 初始化 OkGo
//        OkGoUtils.initOkGo(this);

        // 初始化 Retrofit
        RetrofitUtils.getInstance().initRetrofit();

        // 环境 ( 服务器地址 ) 改变通知
        DevEnvironment.addOnEnvironmentChangeListener(new OnEnvironmentChangeListener() {
            @Override
            public void onEnvironmentChanged(ModuleBean module, EnvironmentBean oldEnvironment, EnvironmentBean newEnvironment) {
                // 改变地址重新初始化
                RetrofitUtils.getInstance().initRetrofit().resetAPIService();
            }
        });
    }
}