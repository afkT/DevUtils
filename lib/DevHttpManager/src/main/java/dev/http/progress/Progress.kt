package dev.http.progress

import android.os.Parcelable
import android.os.SystemClock
import dev.utils.DevFinal
import dev.utils.common.FileUtils
import dev.utils.common.NumberUtils
import dev.utils.common.assist.url.UrlExtras
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * detail: 进度信息存储类
 * @author Ttt
 */
@Parcelize
class Progress private constructor(
    // 是否上行进度 => `true` 上行, `false` 下行
    private val isRequest: Boolean,
    // 进度 id
    private val id: Long,
    // 创建时间 ( 时间戳 )
    private val date: Long,
    // 数据总长度
    private var totalSize: Long,
    // 当前已上传、下载总长度
    private var currentSize: Long,
    // 最近一次触发大小 ( 最近一次被调用的间隔时间内上传、下载的 byte 长度 )
    private var lastSize: Long,
    // 最近一次刷新、触发时间 ( 毫秒 )
    private var lastRefreshTime: Long,
    // 当前状态
    private var status: Int,
    // 进度异常信息
    private var exception: Throwable?,
    // 上传、下载网速信息
    private val speed: Speed,
    // 额外携带信息
    private var extras: Extras?
) : Parcelable {

    constructor(isRequest: Boolean) : this(
        isRequest, UUID.randomUUID().hashCode().toLong(),
        System.currentTimeMillis(),
        0L, 0L, 0L, 0L,
        NORMAL, null, Speed(), null
    )

    companion object {

        // 默认状态 ( 暂未进行操作 )
        const val NORMAL = DevFinal.INT.NORMAL

        // 开始上传、下载
        const val START = DevFinal.INT.START

        // 上传、下载中
        const val ING = DevFinal.INT.ING

        // 异常 ( 流程失败 )
        const val ERROR = DevFinal.INT.ERROR

        // 完成 ( 流程完成 )
        const val FINISH = DevFinal.INT.FINISH

        // 回调刷新时间 ( 毫秒 )
        const val REFRESH_TIME = 300L
    }

    // ========================
    // = Progress - 对外公开方法 =
    // ========================

    // 拼接 UUID id-date
    @IgnoredOnParcel
    private val uuidString: String by lazy {
        "$id-$date"
    }

    /**
     * 获取 UUID
     * @return uuid
     */
    fun getUUID(): String {
        return uuidString
    }

    // =

    /**
     * 是否上行进度信息
     * @return `true` yes, `false` no
     */
    fun isRequest(): Boolean {
        return isRequest
    }

    /**
     * 是否下行进度信息
     * @return `true` yes, `false` no
     */
    fun isResponse(): Boolean {
        return !isRequest
    }

    /**
     * 获取进度 id
     * @return id
     */
    fun getId(): Long {
        return id
    }

    /**
     * 获取创建时间 ( 时间戳 )
     * @return 创建时间 ( 时间戳 )
     */
    fun getDate(): Long {
        return date
    }

    /**
     * 获取数据总长度
     * @return 数据总长度
     */
    fun getTotalSize(): Long {
        return totalSize
    }

    /**
     * 获取数据总长度格式化信息
     * @param number 保留小数位数
     * @return 数据总长度格式化信息
     */
    fun getTotalSizeFormat(number: Int = 1): String {
        return FileUtils.formatByteMemorySize(
            number, totalSize.toDouble()
        )
    }

    /**
     * 获取当前已上传、下载总长度
     * @return 当前已上传、下载总长度
     */
    fun getCurrentSize(): Long {
        return currentSize
    }

    /**
     * 获取当前已上传、下载总长度格式化信息
     * @param number 保留小数位数
     * @return 当前已上传、下载总长度格式化信息
     */
    fun getCurrentSizeFormat(number: Int = 1): String {
        return FileUtils.formatByteMemorySize(
            number, currentSize.toDouble()
        )
    }

    /**
     * 获取最近一次触发大小 ( 最近一次被调用的间隔时间内上传、下载的 byte 长度 )
     * @return 最近一次被调用的间隔时间内上传、下载的 byte 长度
     */
    fun getLastSize(): Long {
        return lastSize
    }

    /**
     * 获取最近一次刷新、触发时间 ( 毫秒 )
     * @return 最近一次刷新、触发时间 ( 毫秒 )
     */
    fun getLastRefreshTime(): Long {
        return lastRefreshTime
    }

    /**
     * 获取当前状态
     * @return 当前状态
     */
    fun getStatus(): Int {
        return status
    }

    /**
     * 获取进度异常信息
     * @return 进度异常信息
     */
    fun getException(): Throwable? {
        return exception
    }

    /**
     * 获取上传、下载网速信息
     * @return Speed
     */
    fun getSpeed(): Speed {
        return speed
    }

    /**
     * 获取额外携带信息
     * @return Extras
     */
    fun getExtras(): Extras? {
        return extras
    }

    // =

    /**
     * 是否默认状态 ( 暂未进行操作 )
     * @return `true` yes, `false` no
     */
    fun isNORMAL(): Boolean {
        return status == NORMAL
    }

    /**
     * 是否开始上传、下载状态
     * @return `true` yes, `false` no
     */
    fun isSTART(): Boolean {
        return status == START
    }

    /**
     * 是否上传、下载中状态
     * @return `true` yes, `false` no
     */
    fun isING(): Boolean {
        return status == ING
    }

    /**
     * 是否异常状态
     * @return `true` yes, `false` no
     */
    fun isERROR(): Boolean {
        return status == ERROR
    }

    /**
     * 是否完成状态
     * @return `true` yes, `false` no
     */
    fun isFINISH(): Boolean {
        return status == FINISH
    }

    /**
     * 是否结束状态 ( 用于表示整个过程已结束 )
     * @return `true` yes, `false` no
     */
    fun isEND(): Boolean {
        return isERROR() || isFINISH()
    }

    // =

    /**
     * 计算百分比值 ( 最大 100% )
     * @return 百分比值
     */
    fun getPercent(): Int {
        return NumberUtils.percentI(
            currentSize.toDouble(), totalSize.toDouble()
        )
    }

    /**
     * 计算百分比值 ( 最大 100% )
     * @return 百分比值
     */
    fun getPercentD(): Double {
        return NumberUtils.percentD(
            currentSize.toDouble(), totalSize.toDouble()
        )
    }

    /**
     * 判断数据总长度与当前已上传、下载总长度是否一样大小
     * @return `true` yes, `false` no
     */
    fun isSizeSame(): Boolean {
        return totalSize > 0 && totalSize == currentSize
    }

    /**
     * 判断数据长度是否异常
     * @return `true` yes, `false` no
     */
    fun isSizeError(): Boolean {
        return totalSize < 0 || currentSize > totalSize
    }

    // ============
    // = 其他扩展类 =
    // ============

    /**
     * detail: 上传、下载网速信息
     * @author Ttt
     */
    @Parcelize
    class Speed private constructor(
        // 网速 byte/s
        private var speedValue: Long,
        // 网速做平滑的缓存, 避免抖动过大
        private val speedBuffer: MutableList<Long>,
        // 网速缓存数据存储数量
        private var bufferSize: Int
    ) : Parcelable {

        constructor() : this(0L, mutableListOf(), 10)

        // =====================
        // = Speed - 对外公开方法 =
        // =====================

        /**
         * 获取上传、下载网速信息 ( byte/s )
         * @return 网速信息 ( byte/s )
         * 可搭配 [FileUtils.formatByteMemorySize] 使用
         */
        fun getSpeed(): Long {
            return speedValue
        }

        /**
         * 获取上传、下载网速信息格式化信息
         * @param number 保留小数位数
         * @return 上传、下载网速信息格式化信息
         */
        fun getSpeedFormat(number: Int = 1): String {
            return FileUtils.formatByteMemorySize(
                number, speedValue.toDouble()
            )
        }

        /**
         * 获取上传、下载网速信息格式化信息 ( byte/s )
         * @param number 保留小数位数
         * @return 上传、下载网速信息格式化信息 ( byte/s )
         */
        fun getSpeedFormatSecond(number: Int = 1): String {
            return "${getSpeedFormat(number)}/s"
        }

        /**
         * 获取网速缓存数据存储数量
         * @return 数据存储数量
         */
        fun getBufferSize(): Int {
            return bufferSize
        }

        /**
         * 设置网速缓存数据存储数量
         * @param size 缓存数量
         * @return Speed
         * <p></p>
         * 允许设置为负数 ( 小于等于 0 则会清空缓存数据并且不进行存储操作 )
         */
        fun setBufferSize(size: Int): Speed {
            synchronized(this) {
                bufferSize = size.coerceAtLeast(0)
                if (bufferSize <= 0) {
                    speedValue = 0
                    speedBuffer.clear()
                    return this
                }
                val diffSize = speedBuffer.size - bufferSize
                if (diffSize > 0) {
                    for (i in 0 until diffSize) {
                        speedBuffer.removeAt(0)
                    }
                }
                refreshSpeed()
                return this
            }
        }

        // ==================
        // = Speed - 内部方法 =
        // ==================

        /**
         * 存储网速信息并刷新网速信息
         * @param speed 网速 byte/s
         */
        internal fun bufferSpeed(speed: Long) {
            synchronized(this) {
                if (bufferSize <= 0) {
                    return@synchronized
                }
                speedBuffer.add(speed)
                if (speedBuffer.size > bufferSize) {
                    speedBuffer.removeAt(0)
                }
                refreshSpeed()
            }
        }

        /**
         * 计算并刷新网速信息
         */
        private fun refreshSpeed() {
            speedValue = calculateSpeed()
        }

        /**
         * 计算平均网速
         * @return 平均网速 byte/s
         */
        private fun calculateSpeed(): Long {
            var sum = 0L
            for (speedTemp in speedBuffer) {
                sum += speedTemp
            }
            return sum / speedBuffer.size
        }
    }

    /**
     * detail: 额外携带信息
     * @author Ttt
     */
    @Parcelize
    class Extras constructor(
        // 请求链接
        private val url: String,
        // 请求方法
        private val method: String,
        // 请求头信息
        private val headers: Map<String, String>
    ) : Parcelable {

        // Url 携带信息解析
        @IgnoredOnParcel
        private val innerUrlExtras: UrlExtras by lazy {
            UrlExtras(url)
        }

        // ======================
        // = Extras - 对外公开方法 =
        // ======================

        /**
         * 获取请求链接
         * @return 请求链接
         */
        fun getUrl(): String {
            return url
        }

        /**
         * 获取请求方法
         * @return 请求方法
         */
        fun getMethod(): String {
            return method
        }

        /**
         * 获取请求头信息
         * @return 请求头信息
         */
        fun getHeaders(): Map<String, String> {
            return headers
        }

        /**
         * 获取 Url 携带信息解析
         * @return UrlExtras
         */
        fun getUrlExtras(): UrlExtras {
            return innerUrlExtras
        }
    }

    // =====================
    // = Progress - 内部方法 =
    // =====================

    /**
     * 设置数据总长度
     * @param value 数据总长度
     * @return Progress
     */
    internal fun setTotalSize(value: Long): Progress {
        totalSize = value
        return this
    }

    /**
     * 设置当前已上传、下载总长度
     * @param value 当前已上传、下载总长度
     * @return Progress
     */
    internal fun setCurrentSize(value: Long): Progress {
        currentSize = value
        return this
    }

    /**
     * 设置最近一次触发大小 ( 最近一次被调用的间隔时间内上传、下载的 byte 长度 )
     * @param value 最近一次被调用的间隔时间内上传、下载的 byte 长度
     * @return Progress
     */
    internal fun setLastSize(value: Long): Progress {
        lastSize = value
        return this
    }

    /**
     * 设置最近一次刷新、触发时间 ( 毫秒 )
     * @param value 最近一次刷新、触发时间 ( 毫秒 )
     * @return Progress
     */
    internal fun setLastRefreshTime(value: Long): Progress {
        lastRefreshTime = value
        return this
    }

    /**
     * 设置进度异常信息
     * @param value 进度异常信息
     * @return Progress
     */
    internal fun setException(value: Throwable): Progress {
        exception = value
        return this
    }

    /**
     * 设置额外携带信息
     * @param value 额外携带信息
     * @return Progress
     */
    internal fun setExtras(value: Extras?): Progress {
        extras = value
        return this
    }

    // ==========
    // = 更新状态 =
    // ==========

    /**
     * 设置当前状态
     * @param value 当前状态
     * @return Progress
     */
    private fun setStatus(value: Int): Progress {
        status = value
        return this
    }

    /**
     * 设置为 [Progress.START] 状态
     * @return `true` success, `false` fail
     */
    internal fun toStart(): Boolean {
        if (isNORMAL()) {
            setStatus(START)
                .setLastRefreshTime(SystemClock.elapsedRealtime())
            return true
        }
        return false
    }

    /**
     * 设置为 [Progress.ING] 状态
     * @return `true` success, `false` fail
     */
    internal fun toIng(): Boolean {
        if (isSTART()) {
            setStatus(ING)
            return true
        }
        return false
    }

    /**
     * 设置为 [Progress.ERROR] 状态
     * @param exception 进度异常信息
     * @return `true` success, `false` fail
     */
    internal fun toError(exception: Throwable): Boolean {
        if (isING()) {
            setStatus(ERROR).setException(exception)
            return true
        }
        return false
    }

    /**
     * 设置为 [Progress.FINISH] 状态
     * @return `true` success, `false` fail
     */
    internal fun toFinish(): Boolean {
        if (isING()) {
            setStatus(FINISH)
            return true
        }
        return false
    }

    // ==========
    // = 接口事件 =
    // ==========

    /**
     * detail: 上传、下载回调接口
     * @author Ttt
     * 回调不是表示上传、下载结果, 而是表示上传、下载这个操作流程回调
     * <p></p>
     * 如何判断是否需要处理各个方法, 只需要在 [onStart] 判断 [Progress.Extras] 信息
     * 并存储当前的 [Progress.id] 其他方法都用已存储的 id 和传入的 Progress.id 对比即可
     */
    interface Callback {

        /**
         * 是否自动释放监听对象
         * @param progress Progress
         * @return `true` yes, `false` no
         */
        fun isAutoRecycle(progress: Progress): Boolean {
            return true
        }

        /**
         * 开始回调
         * @param progress Progress
         */
        fun onStart(progress: Progress)

        /**
         * 进度回调
         * @param progress Progress
         */
        fun onProgress(progress: Progress)

        /**
         * 流程异常回调
         * @param progress Progress
         */
        fun onError(progress: Progress)

        /**
         * 流程完成回调
         * @param progress Progress
         */
        fun onFinish(progress: Progress)

        /**
         * 流程结束回调
         * @param progress Progress
         * 不管是 [onError]、[onFinish] 最终都会触发该结束方法
         */
        fun onEnd(progress: Progress)
    }
}