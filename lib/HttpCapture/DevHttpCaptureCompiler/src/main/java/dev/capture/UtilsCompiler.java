package dev.capture;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.DevHttpCapture;
import dev.callback.DevCallback;
import dev.utils.LogPrintUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.common.CollectionUtils;
import dev.utils.common.StringUtils;

public final class UtilsCompiler {

    // 日志 TAG
    private static final String TAG = UtilsCompiler.class.getSimpleName();

    // Utils 实例
    private static volatile UtilsCompiler sInstance;

    /**
     * 获取 Utils 实例
     * @return {@link UtilsCompiler}
     */
    public static UtilsCompiler getInstance() {
        if (sInstance == null) {
            synchronized (UtilsCompiler.class) {
                if (sInstance == null) {
                    sInstance = new UtilsCompiler();
                }
            }
        }
        return sInstance;
    }

    // =================
    // = Activity 栈处理 =
    // =================

    // Activity 栈 ( 后进先出 )
    private final Stack<Activity> mActivityStacks = new Stack<>();

    /**
     * 添加 Activity
     * @param activity {@link Activity}
     */
    protected void addActivity(final Activity activity) {
        if (activity != null) {
            synchronized (mActivityStacks) {
                if (mActivityStacks.contains(activity)) {
                    return;
                }
                mActivityStacks.add(activity);
            }
        }
    }

    /**
     * 移除 Activity
     * @param activity {@link Activity}
     */
    protected void removeActivity(final Activity activity) {
        if (activity != null) {
            synchronized (mActivityStacks) {
                int index = mActivityStacks.indexOf(activity);
                if (index == -1) {
                    return;
                }
                try {
                    mActivityStacks.remove(index);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "removeActivity");
                }
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        try {
            synchronized (mActivityStacks) {
                // 保存新的堆栈, 防止出现同步问题
                Stack<Activity> stack = new Stack<>();
                stack.addAll(mActivityStacks);
                // 清空全部, 便于后续操作处理
                mActivityStacks.clear();
                // 进行遍历移除
                Iterator<Activity> iterator = stack.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                        // 删除对应的 Item
                        iterator.remove();
                    }
                }
                // 移除数据, 并且清空内存
                stack.clear();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "finishAllActivity");
        }
    }

    // ==============
    // = 接口所属功能 =
    // ==============

    // key = url, value = 接口所属功能注释
    private final Map<String, String> URL_FUNCTION_MAP = new LinkedHashMap<>();

    /**
     * 添加接口所属功能注释
     * <pre>
     *     url 匹配规则 ( 拆分 ? 前为 key 进行匹配 )
     * </pre>
     * @param url      请求接口链接
     * @param function 接口所属功能注释
     */
    public void putUrlFunction(
            final String url,
            final String function
    ) {
        if (StringUtils.isSpace(url)) return;
        URL_FUNCTION_MAP.put(url, function);
    }

    /**
     * 移除接口所属功能注释
     * @param url 请求接口链接
     */
    public void removeUrlFunction(final String url) {
        URL_FUNCTION_MAP.remove(url);
    }

    // ============
    // = Callback =
    // ============

    // 监听回调
    private final List<DevCallback<Boolean>> mCallbackLists = new CopyOnWriteArrayList<>();

    /**
     * 移除所有回调
     */
    protected void clearCallback() {
        mCallbackLists.clear();
    }

    /**
     * 移除回调 ( 关闭页面调用 )
     * @param callback 回调事件
     */
    protected void removeCallback(final DevCallback<Boolean> callback) {
        if (callback == null) return;
        mCallbackLists.remove(callback);
    }

    /**
     * 添加回调
     * @param callback 回调事件
     */
    protected void addCallback(final DevCallback<Boolean> callback) {
        if (callback == null) return;
        if (mCallbackLists.contains(callback)) return;
        mCallbackLists.add(callback);
    }

    /**
     * 通知回调
     * @param isQuerying 是否查询中
     * @param size       数据数量
     */
    protected void notifyCallback(
            final boolean isQuerying,
            final int size
    ) {
        for (DevCallback<Boolean> callback : mCallbackLists) {
            HandlerUtils.postRunnable(() -> {
                try {
                    callback.callback(isQuerying, size);
                } catch (Exception ignored) {
                }
            });
        }
    }

    // ==========
    // = 数据获取 =
    // ==========

    // 是否查询中
    private       boolean                        mQuerying = false;
    // 数据源
    private final Map<String, List<CaptureItem>> mDataMaps = new LinkedHashMap<>();

    /**
     * 查询数据
     * @param callback  回调事件
     * @param isRefresh 是否刷新操作
     */
    protected void queryData(
            final DevCallback<Boolean> callback,
            final boolean isRefresh
    ) {
        addCallback(callback);
        int size = mDataMaps.size();
        if (mQuerying) {
            notifyCallback(true, size);
            return;
        }
        // 如果存在数据且非刷新操作表示需要获取数据
        if (size != 0 && !isRefresh) {
            notifyCallback(false, size);
            return;
        }
        mQuerying = true;
        // 触发通知表示查询中
        notifyCallback(true, size);
        // 后台读取数据
        new Thread(() -> {
            Map<String, List<CaptureItem>> maps = DevHttpCapture.getAllModule(false);
            mDataMaps.clear();
            mDataMaps.putAll(maps);
            mQuerying = false;
            notifyCallback(false, mDataMaps.size());
            clearCallback();
        }).start();
    }

    /**
     * 移除所有数据
     */
    protected void clearData() {
        mDataMaps.clear();
    }

    // ==========
    // = 数据转换 =
    // ==========

    /**
     * 获取首页数据源
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 首页数据源
     */
    protected List<Items.MainItem> getMainData(final String moduleName) {
        List<Items.MainItem> lists = new ArrayList<>();
        // 判断是否显示指定模块
        if (StringUtils.isEmpty(moduleName)) {
            for (Map.Entry<String, List<CaptureItem>> entry : mDataMaps.entrySet()) {
                if (CollectionUtils.isNotEmpty(entry.getValue())) {
                    lists.add(new Items.MainItem(entry.getKey(), entry.getValue()));
                }
            }
        } else {
            List<CaptureItem> data = mDataMaps.get(moduleName);
            if (CollectionUtils.isNotEmpty(data)) {
                lists.add(new Items.MainItem(moduleName, data));
            }
        }
        return lists;
    }

    /**
     * 通过时间 ( yyyyMMdd ) 获取抓包存储 Item
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param date       yyyyMMdd
     * @return 抓包存储 Item
     */
    private CaptureItem getCaptureItemByDate(
            final String moduleName,
            final String date
    ) {
        if (StringUtils.isNotEmpty(date)) {
            List<CaptureItem> data = mDataMaps.get(moduleName);
            if (CollectionUtils.isNotEmpty(data)) {
                for (CaptureItem item : data) {
                    if (item != null && date.equals(item.getYyyyMMdd())) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取对应时间 ( yyyyMMdd ) 指定筛选条件抓包列表数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param date       yyyyMMdd
     * @return 指定筛选条件抓包列表数据
     */
    protected List<Items.GroupItem> getDateData(
            final String moduleName,
            final String date
    ) {
        List<Items.GroupItem> lists = new ArrayList<>();
        // 通过时间 ( yyyyMMdd ) 获取抓包存储 Item
        CaptureItem captureItem = getCaptureItemByDate(moduleName, date);

        for (Map.Entry<String, List<CaptureFile>> entry : captureItem.getData().entrySet()) {
            if (CollectionUtils.isNotEmpty(entry.getValue())) {
                lists.add(new Items.GroupItem(entry.getKey(), entry.getValue()));
            }
        }
        return lists;
    }
}