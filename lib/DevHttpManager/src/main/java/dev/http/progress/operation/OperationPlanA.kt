package dev.http.progress.operation

import dev.http.progress.Progress
import dev.http.progress.ProgressOperation
import dev.utils.common.StringUtils

/**
 * detail: Progress Operation 实现方式一
 * @author Ttt
 * 实现方式差异可以查看 [ProgressOperation] 类注释
 */
internal class OperationPlanA constructor(
    key: String,
    // 全局默认操作对象
    globalDefault: Boolean,
    // 内部拦截器监听类型
    type: Int
) : BaseOperation(key, globalDefault, type, ProgressOperation.PLAN_A) {

    // 上行 ( 上传、请求 ) 监听回调 ( key = url, value = Progress.Callback )
    private val mRequestListeners = HashMap<String, MutableList<Progress.Callback?>>()

    // 下行 ( 下载、响应 ) 监听回调
    private val mResponseListeners = HashMap<String, MutableList<Progress.Callback?>>()

    // =================
    // = BaseOperation =
    // =================

    /**
     * 获取对应方案回调实现
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param extras 额外携带信息
     * @return Progress.Callback
     */
    override fun getPlanCallback(
        isRequest: Boolean,
        extras: Progress.Extras?
    ): Progress.Callback {
        return innerCallback
    }

    /**
     * 添加指定 url 监听事件
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     * 会清空 url 字符串全部空格、Tab、换行符, 如有特殊符号需提前自行转义
     */
    override fun addListener(
        isRequest: Boolean,
        url: String,
        callback: Progress.Callback
    ): Boolean {
        val newUrl = StringUtils.clearSpaceTabLine(url)
        if (StringUtils.isNotEmpty(newUrl)) {
            val map = listenerMap(isRequest)
            map[newUrl]?.let { list ->
                if (!list.contains(callback)) {
                    list.add(callback)
                }
                return true
            }
            map[newUrl] = mutableListOf(callback)
            return true
        }
        return false
    }

    /**
     * 清空指定 url 所有监听事件
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param url 请求 url
     * @return `true` success, `false` fail
     */
    override fun clearListener(
        isRequest: Boolean,
        url: String
    ): Boolean {
        val newUrl = StringUtils.clearSpaceTabLine(url)
        if (StringUtils.isNotEmpty(newUrl)) {
            val map = listenerMap(isRequest)
            map.remove(newUrl)?.clear()
            return true
        }
        return false
    }

    /**
     * 清空指定 url 所有监听事件
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param progress Progress
     * @return `true` success, `false` fail
     */
    override fun clearListener(
        isRequest: Boolean,
        progress: Progress?
    ): Boolean {
        return clearListener(isRequest, getUrlByPrefix(progress))
    }

    /**
     * 移除指定 url 监听事件
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun removeListener(
        isRequest: Boolean,
        url: String,
        callback: Progress.Callback
    ): Boolean {
        val newUrl = StringUtils.clearSpaceTabLine(url)
        if (StringUtils.isNotEmpty(newUrl)) {
            val map = listenerMap(isRequest)
            return map[newUrl]?.remove(callback) ?: false
        }
        return false
    }

    /**
     * 移除指定 url 监听事件
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun removeListener(
        isRequest: Boolean,
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean {
        return removeListener(isRequest, getUrlByPrefix(progress), callback)
    }

    /**
     * 移除指定 url 监听事件
     * @param progress Progress
     * @param recycleList 待释放回调 List
     * @return `true` success, `false` fail
     */
    override fun removeRecycleList(
        progress: Progress,
        recycleList: List<Progress.Callback>
    ): Boolean {
        if (recycleList.isNotEmpty()) {
            val url = getUrlByPrefix(progress)
            val newUrl = StringUtils.clearSpaceTabLine(url)
            if (StringUtils.isNotEmpty(newUrl)) {
                val map = listenerMap(progress.isRequest())
                return map[newUrl]?.removeAll(recycleList) ?: false
            }
        }
        return false
    }

    /**
     * 根据请求 url 获取对应的监听事件集合
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @param url 请求 url
     * @return Array<Progress.Callback?>
     */
    override fun getCallbackList(
        isRequest: Boolean,
        url: String
    ): Array<Progress.Callback?> {
        val newUrl = StringUtils.clearSpaceTabLine(url)
        if (StringUtils.isNotEmpty(newUrl)) {
            val map = listenerMap(isRequest)
            map[newUrl]?.let {
                return it.toTypedArray()
            }
        }
        return arrayOf()
    }

    /**
     * 根据请求 url 获取对应的监听事件集合
     * @param progress Progress
     * @return Array<Progress.Callback?>
     */
    override fun getCallbackList(progress: Progress): Array<Progress.Callback?> {
        return getCallbackList(progress.isRequest(), getUrlByPrefix(progress))
    }

    /**
     * 释放废弃资源
     */
    override fun recycleDeprecated() {
        mRequestListeners.clear()
        mResponseListeners.clear()
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 Callback Map
     * @param isRequest `true` 上行 ( 上传、请求 ), `false` 下行 ( 下载、响应 )
     * @return HashMap<String, List<Progress.Callback?>>
     */
    private fun listenerMap(isRequest: Boolean): HashMap<String, MutableList<Progress.Callback?>> {
        return if (isRequest) mRequestListeners else mResponseListeners
    }

    /**
     * detail: 内部 Progress 回调
     * @author Ttt
     */
    private val innerCallback: Progress.Callback by lazy {
        object : Progress.Callback {
            override fun onStart(progress: Progress) {
                if (isDeprecated()) return

                // 全局 Progress Callback
                getCallback()?.onStart(progress)

                // 根据请求 url 获取对应的监听事件集合
                val array = getCallbackList(progress)
                array.forEach {
                    it?.onStart(progress)
                }
            }

            override fun onProgress(progress: Progress) {
                if (isDeprecated()) return

                // 全局 Progress Callback
                getCallback()?.onProgress(progress)

                // 根据请求 url 获取对应的监听事件集合
                val array = getCallbackList(progress)
                array.forEach {
                    it?.onProgress(progress)
                }
            }

            override fun onError(progress: Progress) {
                if (isDeprecated()) return

                // 全局 Progress Callback
                getCallback()?.onError(progress)

                // 根据请求 url 获取对应的监听事件集合
                val array = getCallbackList(progress)
                array.forEach {
                    it?.onError(progress)
                }
            }

            override fun onFinish(progress: Progress) {
                if (isDeprecated()) return

                // 全局 Progress Callback
                getCallback()?.onFinish(progress)

                // 根据请求 url 获取对应的监听事件集合
                val array = getCallbackList(progress)
                array.forEach {
                    it?.onFinish(progress)
                }
            }

            override fun onEnd(progress: Progress) {
                if (isDeprecated()) return

                // 全局 Progress Callback
                getCallback()?.onEnd(progress)

                // 需要自动销毁的 list
                val recycleList = mutableListOf<Progress.Callback>()
                // 根据请求 url 获取对应的监听事件集合
                val array = getCallbackList(progress)
                array.forEach {
                    it?.let { callback ->
                        callback.onEnd(progress)
                        if (callback.isAutoRecycle(progress)) {
                            recycleList.add(callback)
                        }
                    }
                }
                removeRecycleList(progress, recycleList)
            }
        }
    }
}