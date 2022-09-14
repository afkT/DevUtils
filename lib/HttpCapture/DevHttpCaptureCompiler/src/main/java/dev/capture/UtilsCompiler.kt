package dev.capture

import android.app.Activity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import dev.DevHttpCapture
import dev.callback.DevCallback
import dev.capture.compiler.R
import dev.capture.model.Items
import dev.utils.LogPrintUtils
import dev.utils.app.ClickUtils.ClickAssist
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.assist.ActivityManagerAssist
import dev.utils.common.CollectionUtils
import dev.utils.common.MapUtils
import dev.utils.common.StringUtils
import dev.utils.common.comparator.sort.WindowsExplorerStringSimpleComparator
import java.io.StringReader
import java.util.concurrent.CopyOnWriteArrayList

internal object UtilsCompiler {

    // 日志 TAG
    private val TAG = UtilsCompiler::class.java.simpleName

    // ===================
    // = Activity 管理控制 =
    // ===================

    // ActivityManagerAssist 实例
    private val mManager = ActivityManagerAssist()

    /**
     * 添加 Activity
     * @param activity [Activity]
     */
    fun addActivity(activity: Activity?) {
        mManager.addActivity(activity)
    }

    /**
     * 移除 Activity
     * @param activity [Activity]
     */
    fun removeActivity(activity: Activity?) {
        mManager.removeActivity(activity)
    }

    /**
     * 结束所有 Activity
     */
    fun finishAllActivity() {
        mManager.finishAllActivity()
    }

    // ========
    // = GSON =
    // ========

    // JSON 字符串转 T Object
    private val FROM_GSON = createGson().create()

    // JSON 缩进
    private val INDENT_GSON = createGson().setPrettyPrinting().create()

    /**
     * 创建 GsonBuilder
     * @return [GsonBuilder]
     */
    private fun createGson(): GsonBuilder {
        return GsonBuilder().serializeNulls()
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    private fun toJsonIndent(json: String?): String? {
        try {
            val reader = JsonReader(StringReader(json))
            reader.isLenient = true
            val jsonElement = JsonParser.parseReader(reader)
            return INDENT_GSON.toJson(jsonElement)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "toJsonIndent")
        }
        return null
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT [Class] T
     * @param <T>      泛型
     * @return instance of type
     */
    private fun <T> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        try {
            return FROM_GSON.fromJson(json, classOfT)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "fromJson")
        }
        return null
    }

    // ==============
    // = 接口所属功能 =
    // ==============

    // key = moduleName, value = 接口所属功能注释获取
    private val URL_FUNCTION_MAP = linkedMapOf<String, UrlFunctionGet?>()

    /**
     * 添加接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param function   接口所属功能注释获取
     */
    fun putUrlFunction(
        moduleName: String,
        function: UrlFunctionGet
    ) {
        if (StringUtils.isEmpty(moduleName)) return
        URL_FUNCTION_MAP[moduleName] = function
    }

    /**
     * 移除接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     */
    fun removeUrlFunction(moduleName: String) {
        if (StringUtils.isEmpty(moduleName)) return
        URL_FUNCTION_MAP.remove(moduleName)
    }

    /**
     * 获取接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 接口所属功能注释获取
     */
    fun getUrlFunction(moduleName: String): UrlFunctionGet? {
        if (StringUtils.isEmpty(moduleName)) return null
        return URL_FUNCTION_MAP[moduleName]
    }

    // ============
    // = Callback =
    // ============

    // 监听回调
    private val mCallbackLists = CopyOnWriteArrayList<DevCallback<Boolean>>()

    /**
     * 移除所有回调
     */
    fun clearCallback() {
        mCallbackLists.clear()
    }

    /**
     * 移除回调 ( 关闭页面调用 )
     * @param callback 回调事件
     */
    fun removeCallback(callback: DevCallback<Boolean>) {
        mCallbackLists.remove(callback)
    }

    /**
     * 添加回调
     * @param callback 回调事件
     */
    fun addCallback(callback: DevCallback<Boolean>) {
        if (mCallbackLists.contains(callback)) return
        mCallbackLists.add(callback)
    }

    /**
     * 通知回调
     * @param isQuerying 是否查询中
     * @param size       数据数量
     */
    fun notifyCallback(
        isQuerying: Boolean,
        size: Int
    ) {
        for (callback in mCallbackLists) {
            HandlerUtils.postRunnable {
                try {
                    callback.callback(isQuerying, size)
                } catch (ignored: Exception) {
                }
            }
        }
    }

    // ==========
    // = 数据获取 =
    // ==========

    // 是否查询中
    private var mQuerying = false

    // 数据源
    private val mDataMaps = linkedMapOf<String, MutableList<CaptureItem>>()

    /**
     * 查询数据
     * @param callback  回调事件
     * @param isRefresh 是否刷新操作
     */
    fun queryData(
        callback: DevCallback<Boolean>,
        isRefresh: Boolean
    ) {
        addCallback(callback)
        val size = mDataMaps.size
        if (mQuerying) {
            notifyCallback(true, size)
            return
        }
        // 如果存在数据且非刷新操作表示需要获取数据
        if (size != 0 && !isRefresh) {
            notifyCallback(false, size)
            return
        }
        mQuerying = true
        // 触发通知表示查询中
        notifyCallback(true, size)
        // 后台读取数据
        Thread {
            val maps = DevHttpCapture.utils().getAllModule(false)
            mDataMaps.clear()
            mDataMaps.putAll(maps)
            mQuerying = false
            notifyCallback(false, mDataMaps.size)
            clearCallback()
        }.start()
    }

    /**
     * 移除所有数据
     */
    fun clearData() {
        mDataMaps.clear()
    }

    /**
     * 是否查询中
     * @return `true` yes, `false` no
     */
    fun isQuerying(): Boolean {
        return mQuerying
    }

    // ==========
    // = 数据转换 =
    // ==========

    // Windows 目录资源文件名排序比较器
    private val COMPARATOR = WindowsExplorerStringSimpleComparator()

    /**
     * 获取首页数据源
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 首页数据源
     */
    fun getMainData(moduleName: String): List<Items.MainItem> {
        val lists = mutableListOf<Items.MainItem>()
        // 判断是否显示指定模块
        if (StringUtils.isEmpty(moduleName)) {
            mDataMaps.forEach { entry ->
                if (CollectionUtils.isNotEmpty(entry.value)) {
                    lists.add(Items.MainItem(entry.key, entry.value))
                }
            }
        } else {
            mDataMaps[moduleName]?.let {
                if (CollectionUtils.isNotEmpty(it)) {
                    lists.add(Items.MainItem(moduleName, it))
                }
            }
        }
        // 进行排序
        lists.sortWith { o1, o2 -> COMPARATOR.compare(o1.moduleName, o2.moduleName) }
        return lists
    }

    /**
     * 通过时间 ( yyyyMMdd ) 获取抓包存储 Item
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param date       yyyyMMdd
     * @return 抓包存储 Item
     */
    private fun getCaptureItemByDate(
        moduleName: String,
        date: String
    ): CaptureItem? {
        if (StringUtils.isNotEmpty(date)) {
            mDataMaps[moduleName]?.let { list ->
                if (CollectionUtils.isNotEmpty(list)) {
                    for (item in list) {
                        if (date == item.yyyyMMdd) {
                            return item
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * 获取抓包文件数据
     * @param json 抓包文件 JSON 格式数据
     * @return 抓包文件数据
     */
    fun getFileData(json: String?): List<Items.FileItem> {
        val lists = mutableListOf<Items.FileItem>()
        val captureFile = fromJson(json, CaptureFile::class.java)
        if (captureFile != null) {
            captureFile.getCaptureInfo()?.let { captureInfo ->
                // 接口所属功能
                val function = getUrlFunctionByInfo(captureFile, captureInfo)
                if (StringUtils.isNotEmpty(function)) {
                    lists.add(
                        Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_url_function),
                            function ?: ""
                        )
                    )
                }

                // 请求链接
                lists.add(
                    Items.FileItem(
                        ResourceUtils.getString(R.string.dev_http_capture_request_url),
                        captureInfo.requestUrl
                    )
                )

                // 请求方法
                lists.add(
                    Items.FileItem(
                        ResourceUtils.getString(R.string.dev_http_capture_request_method),
                        captureInfo.requestMethod
                    )
                )

                // 请求头信息
                val requestHeader = mapToString(captureInfo.requestHeader).toString()
                if (StringUtils.isNotEmpty(requestHeader)) {
                    lists.add(
                        Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_request_header),
                            requestHeader
                        )
                    )
                }

                // 请求数据
                val requestBody = mapToString(captureInfo.requestBody).toString()
                if (StringUtils.isNotEmpty(requestBody)) {
                    lists.add(
                        Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_request_body),
                            requestBody
                        )
                    )
                }

                // 响应状态
                val responseStatus = mapToString(captureInfo.responseStatus).toString()
                if (StringUtils.isNotEmpty(responseStatus)) {
                    lists.add(
                        Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_response_status),
                            responseStatus
                        )
                    )
                }

                // 响应头信息
                val responseHeader = mapToString(captureInfo.responseHeader).toString()
                if (StringUtils.isNotEmpty(responseHeader)) {
                    lists.add(
                        Items.FileItem(
                            ResourceUtils.getString(R.string.dev_http_capture_response_header),
                            responseHeader
                        )
                    )
                }

                // 响应数据
                lists.add(
                    Items.FileItem(
                        ResourceUtils.getString(R.string.dev_http_capture_response_body),
                        toJsonIndent(captureInfo.responseBody) ?: ""
                    )
                )
            }
        }
        return lists
    }

    /**
     * 获取对应时间 ( yyyyMMdd ) 指定筛选条件抓包列表数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param date       yyyyMMdd
     * @param dataType   数据来源类型
     * @param groupType  分组条件类型
     * @return 指定筛选条件抓包列表数据
     */
    fun getDateData(
        moduleName: String,
        date: String,
        dataType: Items.DataType,
        groupType: Items.GroupType
    ): List<Items.GroupItem> {
        val dataLists = mutableListOf<Items.GroupItem>()
        // 通过时间 ( yyyyMMdd ) 获取抓包存储 Item
        getCaptureItemByDate(moduleName, date)?.let { captureItem ->
            val tempMaps = linkedMapOf<String, List<CaptureFile>>()
            if (dataType === Items.DataType.T_ALL) {
                // 以全部数据为展示
                tempMaps.putAll(captureItem.data)
            } else {
                // 以指定时间数据为展示
                captureItem.data.forEach { entry ->
                    if (CollectionUtils.isNotEmpty(entry.value)) {
                        if (Items.convertDataType(entry.key) === dataType) {
                            tempMaps[entry.key] = entry.value
                        }
                    }
                }
            }
            // 以时间为分组 ( 展开条标题 )
            if (groupType == Items.GroupType.T_TIME) {
                tempMaps.forEach { entry ->
                    dataLists.add(Items.GroupItem(entry.key, entry.value))
                }
            } else {
                // 以请求链接为分组 ( 展开条标题 )
                val urlMaps = linkedMapOf<String, MutableList<CaptureFile>>()
                tempMaps.values.forEach { lists ->
                    for (captureFile in lists) {
                        val urlKey = Items.convertUrlKey(captureFile.getUrl())
                        MapUtils.putToList(urlMaps, urlKey, captureFile)
                    }
                }
                urlMaps.forEach { entry ->
                    val captureFile = entry.value[0]
                    val function = getUrlFunctionByFile(captureFile, entry.key)
                    val item = Items.GroupItem(entry.key, entry.value)
                    item.setFunction(function)
                    dataLists.add(item)
                }
            }
        }
        return dataLists
    }

    // =============
    // = 接口所属功能 =
    // =============

    // 接口所属功能注释缓存
    private val mFunctionCacheMaps = mutableMapOf<String, String>()

    /**
     * 获取接口所属功能
     * @param captureFile 抓包存储文件
     * @param captureInfo 抓包数据
     * @return 接口所属功能
     */
    private fun getUrlFunctionByInfo(
        captureFile: CaptureFile,
        captureInfo: CaptureInfo
    ): String? {
        // 接口所属功能
        return getUrlFunction(captureFile.getModuleName())?.let {
            val convertUrlKey = Items.convertUrlKey(captureInfo.requestUrl)
            val cacheFunction = mFunctionCacheMaps[convertUrlKey]
            val function = it.toUrlFunction(
                captureFile.getModuleName(), captureFile.getUrl(),
                captureFile.getMethod(), convertUrlKey, cacheFunction
            )
            // 两个值不同才进行变更
            if (!StringUtils.equalsNotNull(cacheFunction, function)) {
                if (convertUrlKey != null && function != null) {
                    mFunctionCacheMaps[convertUrlKey] = function
                }
            }
            return function
        }
    }

    /**
     * 获取接口所属功能
     * @param captureFile 抓包存储文件
     * @return 接口所属功能
     */
    fun getUrlFunctionByFile(captureFile: CaptureFile?): String? {
        if (captureFile != null) {
            val convertUrlKey = Items.convertUrlKey(captureFile.getUrl())
            return getUrlFunctionByFile(captureFile, convertUrlKey)
        }
        return null
    }

    /**
     * 获取接口所属功能
     * @param captureFile   抓包存储文件
     * @param convertUrlKey 拆分 Url 用于匹配接口所属功能注释
     * @return 接口所属功能
     */
    private fun getUrlFunctionByFile(
        captureFile: CaptureFile?,
        convertUrlKey: String?
    ): String? {
        if (captureFile != null) {
            // 接口所属功能
            return getUrlFunction(
                captureFile.getModuleName()
            )?.let {
                val cacheFunction = mFunctionCacheMaps[convertUrlKey]
                val function = it.toUrlFunction(
                    captureFile.getModuleName(), captureFile.getUrl(),
                    captureFile.getMethod(), convertUrlKey, cacheFunction
                )
                // 两个值不同才进行变更
                if (!StringUtils.equalsNotNull(cacheFunction, function)) {
                    if (convertUrlKey != null && function != null) {
                        mFunctionCacheMaps[convertUrlKey] = function
                    }
                }
                return function
            }
        }
        return null
    }

    // ==========
    // = 刷新处理 =
    // ==========

    // 刷新点击 ( 双击 ) 辅助类
    val REFRESH_CLICK = ClickAssist(10000L)

    /**
     * 重置刷新点击处理
     */
    fun resetRefreshClick() {
        REFRESH_CLICK.reset().setIntervalTime(10000L)
    }

    // ============
    // = MapUtils =
    // ============

    /**
     * 键值对拼接
     * @param map [Map]
     * @return [StringBuilder]
     */
    private fun mapToString(map: LinkedHashMap<String, String>): StringBuilder {
//        map.forEach {
//            map[it.key] = toJsonIndent(it.value) ?: it.value
//        }
        return MapUtils.mapToString(map, ": ")
    }
}