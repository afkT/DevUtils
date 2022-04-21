package dev.http.progress

import android.os.Parcelable
import dev.utils.DevFinal
import dev.utils.common.NumberUtils
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * detail: 进度信息存储类
 * @author Ttt
 */
@Parcelize
class Progress private constructor(
    // 进度 id ( 当前时间戳, 值越大说明该请求越新 - 同时可以当做 date 使用 )
    private val id: Long,
    // 数据总长度
    private var totalSize: Long,
    // 当前已上传、下载总长度
    private var currentSize: Long,
    // 最近一次触发大小 ( 最近一次被调用的间隔时间内上传或下载的 byte 长度 )
    private var lastSize: Long,
    // 最近一次刷新、触发时间 ( 毫秒 )
    private var lastRefreshTime: Long,
    // 当前状态
    private var status: Int,
    // 上传、下载网速信息
    private val speed: Speed
) : Parcelable {

    constructor(id: Long = System.currentTimeMillis()) : this(
        id, 0, 0, 0, 0,
        NORMAL, Speed()
    )

    companion object {

        // 默认状态 ( 暂未进行操作 )
        const val NORMAL = DevFinal.INT.NORMAL

        // 开始上传、下载
        const val START = DevFinal.INT.START

        // 上传、下载中
        const val ING = DevFinal.INT.ING

        // 出现错误异常 ( 也表示移除结束 )
        const val ERROR = DevFinal.INT.ERROR

        // 完成 ( 成功结束 )
        const val FINISH = DevFinal.INT.FINISH
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取进度 id ( timestamp )
     * @return id
     */
    fun getId(): Long {
        return id
    }

    /**
     * 获取数据总长度
     * @return 数据总长度
     */
    fun getTotalSize(): Long {
        return totalSize
    }

    /**
     * 获取当前已上传、下载总长度
     * @return 当前已上传、下载总长度
     */
    fun getCurrentSize(): Long {
        return currentSize
    }

    /**
     * 获取最近一次触发大小 ( 最近一次被调用的间隔时间内上传或下载的 byte 长度 )
     * @return 最近一次被调用的间隔时间内上传或下载的 byte 长度
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
     * 是否错误异常状态
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
     * @return Boolean
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

    // ==========
    // = 内部方法 =
    // ==========


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
        private val speed: Long,
        // 网速做平滑的缓存, 避免抖动过快
        private val speedBuffer: List<Long>
    ) : Parcelable {

        constructor() : this(0, mutableListOf())

        // 缓存数量存储限制最多条数
        @IgnoredOnParcel
        private val BUFFER_MAX_SIZE = 10
    }
}