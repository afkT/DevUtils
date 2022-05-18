package afkt.project.base.http

import dev.DevHttpManager
import dev.http.progress.Progress
import dev.http.progress.ProgressOperation
import dev.kotlin.engine.log.log_dTag
import dev.utils.common.MapUtils
import dev.utils.common.StringUtils
import dev.utils.common.ThrowableUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * detail: DevHttpManager Progress 监听使用示范
 * @author Ttt
 */
class ProgressManagerUse private constructor() {

    companion object {

        private val instance: ProgressManagerUse by lazy { ProgressManagerUse() }

        fun operation(): ProgressOperation {
            return instance.operation()
        }
    }

    // 日志 TAG
    private val TAG = ProgressManagerUse::class.java.simpleName

    // 模拟链接
    private val url = "https://developer.android.com/docs?type=1&abc=afkt"

    // =============
    // = 模拟使用方法 =
    // =============

    fun use() {
        /**
         * 需要切换内部实现方式, 必须先调用该方法
         * 实现方式差异可以查看 [ProgressOperation] 类注释
         * 可不调用默认使用 PLAN_A
         */
        mOperation.setPlanType(ProgressOperation.PLAN_A)
        mOperation.setPlanType(ProgressOperation.PLAN_B)

        // 进行拦截器包装 ( 必须调用 )
        val okHttpClient = mOperation.wrap(OkHttpClient.Builder()).build()

        // 基于 OkHttp 库, 不同库封装使用不同, 只要使用 wrap build 后的 client 就能够实现监听
        val retrofit = Retrofit.Builder()
            // Gson 解析
            .addConverterFactory(GsonConverterFactory.create())
            // OkHttpClient
            .client(okHttpClient)
            // 服务器地址
            .baseUrl("")
            .build()

        // 添加指定 url 上行监听事件
        mOperation.addRequestListener(url, progressCallback)
        // 添加指定 url 下行监听事件
        mOperation.addResponseListener(url, progressCallback)
    }

    // =====================
    // = ProgressOperation =
    // =====================

    /**
     * 对外提供操作对象
     * @return ProgressOperation
     */
    fun operation(): ProgressOperation {
        return mOperation
    }

    // Progress Operation
    private val mOperation: ProgressOperation by lazy {
        // 监听上下行
        DevHttpManager.PM.putOperationTypeAll("MODULE_NAME")
//        // 监听上行
//        DevHttpManager.PM.putOperationTypeRequest("MODULE_NAME")
//        // 监听下行
//        DevHttpManager.PM.putOperationTypeResponse("MODULE_NAME")
    }

    // ==========
    // = 内部方法 =
    // ==========

    // Progress Callback 模拟监听
    private val progressCallback: Progress.Callback by lazy {
        createCallback { extras ->
            // 判断请求链接是否一致
            if (StringUtils.isNotEmpty(url)
                && StringUtils.equals(url, extras.getUrl())
            ) {
                val urlParams = extras.getUrlExtras().urlParams
                // 判断参数 ( 便于演示实际应该是传参 or 静态存储 )
                if (StringUtils.equals("afkt", MapUtils.get(urlParams, "abc"))) {
                    // 还可以其他判断, 符合条件返回 true
                    return@createCallback true
                }
            }
            return@createCallback false
        }
    }

    /**
     * 创建 Progress Callback
     * @param filter 传入方法体外部进行过滤判断是否属于需要监听的进度
     * @return Progress.Callback
     * 回调不是表示上传、下载结果, 而是表示上传、下载这个操作流程回调
     * <p></p>
     * 如何判断是否需要处理各个方法, 只需要在 [onStart] 判断 [Progress.Extras] 信息
     * 并存储当前的 [Progress.id] 其他方法都用已存储的 id 和传入的 Progress.id 对比即可
     */
    private fun createCallback(filter: (Progress.Extras) -> Boolean): Progress.Callback {
        return object : Progress.Callback {

            // ==========
            // = 内部变量 =
            // ==========

            // 存储处理的进度 id ( 用于演示多个相同请求, 参数不同情况下监听指定请求进度 )
            private var progressId = -1L

            /**
             * 有多个相同请求且参数一致, 需要监听不同进度, 则自行新增一个无用的随机参数 or 头信息进行区分
             * 不同请求则不推荐进行复用, 方便回收、判断等 ( 本身功能设计也是监听指定需求 )
             * 有复用需求可以调用 [ProgressOperation.setCallback] 设置为全局通用回调
             * 设置后, 会优先通知全局回调, 接着通知对应 url Callback List
             */

            // =====================
            // = Progress.Callback =
            // =====================

            /**
             * 是否自动释放监听对象
             * @param progress Progress
             * @return `true` yes, `false` no
             */
            override fun isAutoRecycle(progress: Progress): Boolean {
                if (progressId == progress.getId()) {
                    return true
                }
                return false
            }

            /**
             * 开始回调
             * @param progress Progress
             */
            override fun onStart(progress: Progress) {
                // 获取额外携带信息, 如 url、method、headers、param
                val extras = progress.getExtras()
                if (progressId == -1L) { // 没有更新值时才进行判断
                    if (extras != null && filter(extras)) {
                        progressId = progress.getId()
                    }
                }
                if (progressId == progress.getId()) {
                    log_dTag(
                        tag = TAG,
                        message = StringBuilder().apply {
                            append("onStart - ").append(progress.getId())
                            append(", totalSize: ").append(progress.getTotalSize())
                            append(", currentSize: ").append(progress.getCurrentSize())
                        }.toString()
                    )
                }
            }

            /**
             * 进度回调
             * @param progress Progress
             */
            override fun onProgress(progress: Progress) {
                if (progressId == progress.getId()) {
                    log_dTag(
                        tag = TAG,
                        message = StringBuilder().apply {
                            append("onProgress - ").append(progress.getId())
                            append(", totalSize: ").append(progress.getTotalSize())
                            append(", currentSize: ").append(progress.getCurrentSize())
                            append(", 网速: ").append(progress.getSpeed().getSpeedFormatSecond())
                            append(", 百分比进度: ").append(progress.getPercent())
                        }.toString()
                    )
                }
            }

            /**
             * 流程异常回调
             * @param progress Progress
             */
            override fun onError(progress: Progress) {
                if (progressId == progress.getId()) {
                    log_dTag(
                        tag = TAG,
                        message = StringBuilder().apply {
                            append("onError - ").append(progress.getId())
                            append(", totalSize: ").append(progress.getTotalSize())
                            append(", currentSize: ").append(progress.getCurrentSize())
                            append(", 网速: ").append(progress.getSpeed().getSpeedFormatSecond())
                            append(", 百分比进度: ").append(progress.getPercent())
                            append(", 异常信息: ").append(ThrowableUtils.getThrowable(progress.getException()))
                        }.toString()
                    )
                }
            }

            /**
             * 流程完成回调
             * @param progress Progress
             */
            override fun onFinish(progress: Progress) {
                if (progressId == progress.getId()) {
                    log_dTag(
                        tag = TAG,
                        message = StringBuilder().apply {
                            append("onFinish - ").append(progress.getId())
                            append(", 网速: ").append(progress.getSpeed().getSpeedFormatSecond())
                            append(", 百分比进度: ").append(progress.getPercent())
                        }.toString()
                    )
                }
            }

            /**
             * 流程结束回调
             * @param progress Progress
             * 不管是 [onError]、[onFinish] 最终都会触发该结束方法
             */
            override fun onEnd(progress: Progress) {
                if (progressId == progress.getId()) {
                    log_dTag(
                        tag = TAG,
                        message = StringBuilder().apply {
                            append("onEnd - ").append(progress.getId())
                            append(", 网速: ").append(progress.getSpeed().getSpeedFormatSecond())
                            append(", 百分比进度: ").append(progress.getPercent())
                        }.toString()
                    )
                }
            }
        }
    }
}