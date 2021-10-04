package dev.capture;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.DevHttpCapture;
import dev.callback.DevCallback;
import dev.capture.compiler.R;
import dev.utils.JCLogUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.common.CollectionUtils;
import dev.utils.common.MapUtils;
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

    // ========
    // = GSON =
    // ========

    // JSON 字符串转 T Object
    private final Gson FROM_GSON   = createGson(true).create();
    // JSON 缩进
    private final Gson INDENT_GSON = createGson(true).setPrettyPrinting().create();

    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return {@link GsonBuilder}
     */
    protected GsonBuilder createGson(final boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder;
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    protected String toJsonIndent(final String json) {
        return toJsonIndent(json, INDENT_GSON);
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @param gson {@link Gson}
     * @return JSON String
     */
    protected String toJsonIndent(
            final String json,
            final Gson gson
    ) {
        if (gson != null) {
            try {
                JsonReader reader = new JsonReader(new StringReader(json));
                reader.setLenient(true);
                JsonElement jsonElement = JsonParser.parseReader(reader);
                return gson.toJson(jsonElement);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent");
            }
        }
        return null;
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    protected <T> T fromJson(
            final String json,
            final Class<T> classOfT
    ) {
        return fromJson(json, classOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param gson     {@link Gson}
     * @param <T>      泛型
     * @return instance of type
     */
    protected <T> T fromJson(
            final String json,
            final Class<T> classOfT,
            final Gson gson
    ) {
        if (gson != null) {
            try {
                return gson.fromJson(json, classOfT);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "fromJson");
            }
        }
        return null;
    }

    // ==============
    // = 接口所属功能 =
    // ==============

    // key = moduleName, value = 接口所属功能注释获取
    private final Map<String, UrlFunctionGet> URL_FUNCTION_MAP = new LinkedHashMap<>();

    /**
     * 添加接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param function   接口所属功能注释获取
     */
    public void putUrlFunction(
            final String moduleName,
            final UrlFunctionGet function
    ) {
        if (StringUtils.isSpace(moduleName)) return;
        URL_FUNCTION_MAP.put(moduleName, function);
    }

    /**
     * 移除接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     */
    public void removeUrlFunction(final String moduleName) {
        URL_FUNCTION_MAP.remove(moduleName);
    }

    /**
     * 获取接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 接口所属功能注释获取
     */
    protected UrlFunctionGet getUrlFunction(final String moduleName) {
        return URL_FUNCTION_MAP.get(moduleName);
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
     * 获取抓包文件数据
     * @param json 抓包文件 JSON 格式数据
     * @return 抓包文件数据
     */
    protected List<Items.FileItem> getFileData(final String json) {
        List<Items.FileItem> lists       = new ArrayList<>();
        CaptureFile          captureFile = fromJson(json, CaptureFile.class);
        if (captureFile != null) {
            CaptureInfo captureInfo = captureFile.getCaptureInfo();
            if (captureInfo != null) {

                // 接口所属功能
                String function = getUrlFunctionByInfo(captureFile, captureInfo);
                if (StringUtils.isNotEmpty(function)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_url_function),
                            function
                    ));
                }

                // 请求方式
                lists.add(new Items.FileItem(
                        ResourceUtils.getString(R.string.dev_http_capture_request_method),
                        captureInfo.requestMethod
                ));

                // 请求 URL
                lists.add(new Items.FileItem(
                        ResourceUtils.getString(R.string.dev_http_capture_request_url),
                        captureInfo.requestUrl
                ));

                // 请求 Header
                String requestHeader = MapUtils.mapToString(
                        captureInfo.requestHeader, ": "
                ).toString();
                if (StringUtils.isNotEmpty(requestHeader)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_request_header),
                            requestHeader
                    ));
                }

                // 请求 Body
                String requestBody = MapUtils.mapToString(
                        captureInfo.requestBody, ": "
                ).toString();
                if (StringUtils.isNotEmpty(requestBody)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_request_body),
                            requestBody
                    ));
                }

                // 响应状态
                String responseStatus = MapUtils.mapToString(
                        captureInfo.responseStatus, ": "
                ).toString();
                if (StringUtils.isNotEmpty(responseStatus)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_response_status),
                            responseStatus
                    ));
                }

                // 响应 Header
                String responseHeader = MapUtils.mapToString(
                        captureInfo.responseHeader, ": "
                ).toString();
                if (StringUtils.isNotEmpty(responseHeader)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_response_header),
                            responseHeader
                    ));
                }

                // 响应 Body
                lists.add(new Items.FileItem(
                        ResourceUtils.getString(R.string.dev_http_capture_response_body),
                        UtilsCompiler.getInstance().toJsonIndent(captureInfo.responseBody)
                ));
            }
        }
        return lists;
    }

    /**
     * 获取对应时间 ( yyyyMMdd ) 指定筛选条件抓包列表数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param date       yyyyMMdd
     * @param dataType   数据来源类型
     * @param groupType  分组条件类型
     * @return 指定筛选条件抓包列表数据
     */
    protected List<Items.GroupItem> getDateData(
            final String moduleName,
            final String date,
            final Items.DataType dataType,
            final Items.GroupType groupType
    ) {
        List<Items.GroupItem> datas = new ArrayList<>();
        // 通过时间 ( yyyyMMdd ) 获取抓包存储 Item
        CaptureItem captureItem = getCaptureItemByDate(moduleName, date);

        // 以全部数据为展示
        if (dataType == Items.DataType.T_ALL) {
            // 以时间为分组 ( 展开条标题 )
            if (groupType == Items.GroupType.T_TIME) {
                for (Map.Entry<String, List<CaptureFile>> entry : captureItem.getData().entrySet()) {
                    if (CollectionUtils.isNotEmpty(entry.getValue())) {
                        datas.add(new Items.GroupItem(entry.getKey(), entry.getValue()));
                    }
                }
            } else {
                // 以请求链接为分组 ( 展开条标题 )
                Map<String, List<CaptureFile>> urlMaps = new LinkedHashMap<>();
                for (List<CaptureFile> lists : captureItem.getData().values()) {
                    if (CollectionUtils.isNotEmpty(lists)) {
                        for (CaptureFile captureFile : lists) {
                            String urlKey = Items.convertUrlKey(captureFile.getUrl());
                            MapUtils.putToList(urlMaps, urlKey, captureFile);
                        }
                    }
                }
                // 保存处理后的数据
                for (Map.Entry<String, List<CaptureFile>> entry : urlMaps.entrySet()) {
                    CaptureFile     captureFile = entry.getValue().get(0);
                    String          function    = getUrlFunctionByFile(captureFile, entry.getKey());
                    Items.GroupItem item        = new Items.GroupItem(entry.getKey(), entry.getValue());
                    item.setFunction(function);
                    datas.add(item);
                }
            }
        }
        return datas;
    }

    // =============
    // = 接口所属功能 =
    // =============

    /**
     * 获取接口所属功能
     * @param captureFile 抓包存储文件
     * @param captureInfo 抓包数据
     * @return 接口所属功能
     */
    private String getUrlFunctionByInfo(
            final CaptureFile captureFile,
            final CaptureInfo captureInfo
    ) {
        if (captureFile != null && captureInfo != null) {
            // 接口所属功能
            UrlFunctionGet urlFunction = UtilsCompiler.getInstance().getUrlFunction(
                    captureFile.getModuleName()
            );
            if (urlFunction != null) {
                return urlFunction.toUrlFunction(
                        captureFile.getModuleName(), captureInfo.requestUrl,
                        Items.convertUrlKey(captureInfo.requestUrl),
                        captureInfo.requestMethod
                );
            }
        }
        return null;
    }

    /**
     * 获取接口所属功能
     * @param captureFile 抓包存储文件
     * @param urlKey      拆分 Url 用于匹配接口所属功能注释
     * @return 接口所属功能
     */
    private String getUrlFunctionByFile(
            final CaptureFile captureFile,
            final String urlKey
    ) {
        if (captureFile != null) {
            // 接口所属功能
            UrlFunctionGet urlFunction = UtilsCompiler.getInstance().getUrlFunction(
                    captureFile.getModuleName()
            );
            if (urlFunction != null) {
                return urlFunction.toUrlFunction(
                        captureFile.getModuleName(), captureFile.getUrl(),
                        urlKey, captureFile.getMethod()
                );
            }
        }
        return null;
    }
}