package dev.capture;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.DevHttpCapture;
import dev.callback.DevCallback;
import dev.capture.compiler.R;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.app.ClickUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.assist.ActivityManagerAssist;
import dev.utils.common.CollectionUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.MapUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.comparator.sort.WindowsExplorerStringSimpleComparator;

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

    // ===================
    // = Activity 管理控制 =
    // ===================

    // ActivityManagerAssist 实例
    private volatile ActivityManagerAssist sManagerInstance;

    /**
     * 获取 ActivityManagerAssist 管理实例
     * @return {@link ActivityManagerAssist}
     */
    private ActivityManagerAssist getManager() {
        if (sManagerInstance == null) {
            synchronized (ActivityManagerAssist.class) {
                if (sManagerInstance == null) {
                    sManagerInstance = new ActivityManagerAssist();
                }
            }
        }
        return sManagerInstance;
    }

    /**
     * 添加 Activity
     * @param activity {@link Activity}
     */
    void addActivity(final Activity activity) {
        getManager().addActivity(activity);
    }

    /**
     * 移除 Activity
     * @param activity {@link Activity}
     */
    void removeActivity(final Activity activity) {
        getManager().removeActivity(activity);
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        getManager().finishAllActivity();
    }

    // ========
    // = GSON =
    // ========

    // JSON 字符串转 T Object
    private final Gson FROM_GSON   = createGson().create();
    // JSON 缩进
    private final Gson INDENT_GSON = createGson().setPrettyPrinting().create();

    /**
     * 创建 GsonBuilder
     * @return {@link GsonBuilder}
     */
    GsonBuilder createGson() {
        return new GsonBuilder().serializeNulls();
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    String toJsonIndent(final String json) {
        return toJsonIndent(json, INDENT_GSON);
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @param gson {@link Gson}
     * @return JSON String
     */
    String toJsonIndent(
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
                LogPrintUtils.eTag(TAG, e, "toJsonIndent");
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
    <T> T fromJson(
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
    <T> T fromJson(
            final String json,
            final Class<T> classOfT,
            final Gson gson
    ) {
        if (gson != null) {
            try {
                return gson.fromJson(json, classOfT);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "fromJson");
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
        if (TextUtils.isEmpty(moduleName)) return;
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
    UrlFunctionGet getUrlFunction(final String moduleName) {
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
    void clearCallback() {
        mCallbackLists.clear();
    }

    /**
     * 移除回调 ( 关闭页面调用 )
     * @param callback 回调事件
     */
    void removeCallback(final DevCallback<Boolean> callback) {
        if (callback == null) return;
        mCallbackLists.remove(callback);
    }

    /**
     * 添加回调
     * @param callback 回调事件
     */
    void addCallback(final DevCallback<Boolean> callback) {
        if (callback == null) return;
        if (mCallbackLists.contains(callback)) return;
        mCallbackLists.add(callback);
    }

    /**
     * 通知回调
     * @param isQuerying 是否查询中
     * @param size       数据数量
     */
    void notifyCallback(
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
    void queryData(
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
    void clearData() {
        mDataMaps.clear();
    }

    /**
     * 是否查询中
     * @return {@code true} yes, {@code false} no
     */
    boolean isQuerying() {
        return mQuerying;
    }

    // ==========
    // = 数据转换 =
    // ==========

    // Windows 目录资源文件名排序比较器
    private final WindowsExplorerStringSimpleComparator COMPARATOR = new WindowsExplorerStringSimpleComparator();

    /**
     * 获取首页数据源
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 首页数据源
     */
    List<Items.MainItem> getMainData(final String moduleName) {
        List<Items.MainItem> lists = new ArrayList<>();
        // 判断是否显示指定模块
        if (TextUtils.isEmpty(moduleName)) {
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
        // 进行排序
        Collections.sort(lists, (o1, o2) -> COMPARATOR.compare(o1.moduleName, o2.moduleName));
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
    List<Items.FileItem> getFileData(final String json) {
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
                String requestHeader = mapToString(
                        captureInfo.requestHeader
                ).toString();
                if (StringUtils.isNotEmpty(requestHeader)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_request_header),
                            requestHeader
                    ));
                }

                // 请求 Body
                String requestBody = mapToString(
                        captureInfo.requestBody
                ).toString();
                if (StringUtils.isNotEmpty(requestBody)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_request_body),
                            requestBody
                    ));
                }

                // 响应状态
                String responseStatus = mapToString(
                        captureInfo.responseStatus
                ).toString();
                if (StringUtils.isNotEmpty(responseStatus)) {
                    lists.add(new Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_response_status),
                            responseStatus
                    ));
                }

                // 响应 Header
                String responseHeader = mapToString(
                        captureInfo.responseHeader
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
    List<Items.GroupItem> getDateData(
            final String moduleName,
            final String date,
            final Items.DataType dataType,
            final Items.GroupType groupType
    ) {
        List<Items.GroupItem> dataLists = new ArrayList<>();
        // 通过时间 ( yyyyMMdd ) 获取抓包存储 Item
        CaptureItem captureItem = getCaptureItemByDate(moduleName, date);
        if (captureItem == null) return dataLists;

        Map<String, List<CaptureFile>> tempMaps = new LinkedHashMap<>();
        if (dataType == Items.DataType.T_ALL) {
            // 以全部数据为展示
            tempMaps.putAll(captureItem.getData());
        } else {
            // 以指定时间数据为展示
            for (Map.Entry<String, List<CaptureFile>> entry : captureItem.getData().entrySet()) {
                if (CollectionUtils.isNotEmpty(entry.getValue())) {
                    if (Items.convertDataType(entry.getKey()) == dataType) {
                        tempMaps.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        // 以时间为分组 ( 展开条标题 )
        if (groupType == Items.GroupType.T_TIME) {
            for (Map.Entry<String, List<CaptureFile>> entry : tempMaps.entrySet()) {
                if (CollectionUtils.isNotEmpty(entry.getValue())) {
                    dataLists.add(new Items.GroupItem(entry.getKey(), entry.getValue()));
                }
            }
        } else {
            // 以请求链接为分组 ( 展开条标题 )
            Map<String, List<CaptureFile>> urlMaps = new LinkedHashMap<>();
            for (List<CaptureFile> lists : tempMaps.values()) {
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
                dataLists.add(item);
            }
        }
        return dataLists;
    }

    // =============
    // = 接口所属功能 =
    // =============

    // 接口所属功能注释缓存
    private final Map<String, String> mFunctionCacheMaps = new HashMap<>();

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
                String convertUrlKey = Items.convertUrlKey(captureInfo.requestUrl);
                String cacheFunction = mFunctionCacheMaps.get(convertUrlKey);
                String function = urlFunction.toUrlFunction(
                        captureFile.getModuleName(), captureInfo.requestUrl,
                        convertUrlKey, captureInfo.requestMethod, cacheFunction
                );
                // 两个值不同才进行变更
                if (!StringUtils.equals(cacheFunction, function)) {
                    mFunctionCacheMaps.put(convertUrlKey, function);
                }
                return function;
            }
        }
        return null;
    }

    /**
     * 获取接口所属功能
     * @param captureFile 抓包存储文件
     * @return 接口所属功能
     */
    String getUrlFunctionByFile(final CaptureFile captureFile) {
        if (captureFile != null) {
            String convertUrlKey = Items.convertUrlKey(captureFile.getUrl());
            return getUrlFunctionByFile(captureFile, convertUrlKey);
        }
        return null;
    }

    /**
     * 获取接口所属功能
     * @param captureFile   抓包存储文件
     * @param convertUrlKey 拆分 Url 用于匹配接口所属功能注释
     * @return 接口所属功能
     */
    private String getUrlFunctionByFile(
            final CaptureFile captureFile,
            final String convertUrlKey
    ) {
        if (captureFile != null) {
            // 接口所属功能
            UrlFunctionGet urlFunction = UtilsCompiler.getInstance().getUrlFunction(
                    captureFile.getModuleName()
            );
            if (urlFunction != null) {
                String cacheFunction = mFunctionCacheMaps.get(convertUrlKey);
                String function = urlFunction.toUrlFunction(
                        captureFile.getModuleName(), captureFile.getUrl(),
                        convertUrlKey, captureFile.getMethod(), cacheFunction
                );
                // 两个值不同才进行变更
                if (!StringUtils.equals(cacheFunction, function)) {
                    mFunctionCacheMaps.put(convertUrlKey, function);
                }
                return function;
            }
        }
        return null;
    }

    // ==========
    // = 刷新处理 =
    // ==========

    // 刷新点击 ( 双击 ) 辅助类
    static final ClickUtils.ClickAssist sRefreshClick = new ClickUtils.ClickAssist(10000L);

    /**
     * 重置刷新点击处理
     */
    void resetRefreshClick() {
        sRefreshClick.reset().setIntervalTime(10000L);
    }

    // ============
    // = MapUtils =
    // ============

    /**
     * 键值对拼接
     * @param map {@link Map}
     * @param <K> key
     * @param <V> value
     * @return {@link StringBuilder}
     */
    private <K, V> StringBuilder mapToString(final Map<K, V> map) {
        return mapToString(map, new StringBuilder());
    }

    /**
     * 键值对拼接
     * @param map     {@link Map}
     * @param builder Builder
     * @param <K>     key
     * @param <V>     value
     * @return {@link StringBuilder}
     */
    private <K, V> StringBuilder mapToString(
            final Map<K, V> map,
            final StringBuilder builder
    ) {
        if (map != null && builder != null) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                builder.append(ConvertUtils.toString(entry.getKey()));
                builder.append(": ");
                builder.append(ConvertUtils.toString(entry.getValue()));
                // 如果还有下一行则追加换行
                if (iterator.hasNext()) {
                    builder.append(DevFinal.SYMBOL.NEW_LINE);
                }
            }
        }
        return builder;
    }
}