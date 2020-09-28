package dev.utils.app.assist;

import android.content.Context;
import android.content.res.Resources;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: Resources 辅助类
 * @author Ttt
 */
public final class ResourceAssist {

    // ResourceAssist 实例
    private volatile static ResourceAssist sInstance;

    /**
     * 获取 ResourceAssist 实例
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist getInstance() {
        if (sInstance == null) {
            synchronized (ResourceAssist.class) {
                if (sInstance == null) {
                    sInstance = new ResourceAssist();
                }
            }
        }
        return sInstance;
    }

    // 日志 TAG
    private static final String TAG = ResourceAssist.class.getSimpleName();

    // Resources
    private Resources mResource;
    // 应用包名
    private String    mPackageName;

    // ===========
    // = 构造函数 =
    // ===========

    private ResourceAssist() {
        this(getResources(), AppUtils.getPackageName());
    }

    private ResourceAssist(final Context context) {
        this(getResources(context), AppUtils.getPackageName());
    }

    private ResourceAssist(final Resources resource) {
        this(resource, AppUtils.getPackageName());
    }

    private ResourceAssist(final Resources resource, final String packageName) {
        this.mResource = resource;
        this.mPackageName = packageName;
    }

    // ===============
    // = 快捷获取方法 =
    // ===============

    /**
     * 获取 ResourceAssist
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get() {
        return new ResourceAssist();
    }

    /**
     * 获取 ResourceAssist
     * @param context {@link Context}
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get(final Context context) {
        return new ResourceAssist(context);
    }

    /**
     * 获取 ResourceAssist
     * @param resource {@link Resources}
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get(final Resources resource) {
        return new ResourceAssist(resource);
    }

    /**
     * 获取 ResourceAssist
     * @param resource    {@link Resources}
     * @param packageName 应用包名
     * @return {@link ResourceAssist}
     */
    public static ResourceAssist get(final Resources resource, final String packageName) {
        return new ResourceAssist(resource, packageName);
    }

    // =

    /**
     * 获取 Resources
     * @return {@link Resources}
     */
    public static Resources getResources() {
        return getResources(DevUtils.getContext());
    }

    /**
     * 获取 Resources
     * @param context {@link Context}
     * @return {@link Resources}
     */
    public static Resources getResources(final Context context) {
        try {
            return context.getResources();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResources");
        }
        return null;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 重置操作
     * @param resource    {@link Resources}
     * @param packageName 应用包名
     * @return {@link ResourceAssist}
     */
    public ResourceAssist reset(final Resources resource, final String packageName) {
        this.mResource = resource;
        this.mPackageName = packageName;
        return this;
    }
}