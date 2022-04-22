package dev.http.progress

import android.os.Parcelable
import dev.utils.DevFinal
import dev.utils.common.FileUtils
import dev.utils.common.NumberUtils
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
    // 最近一次触发大小 ( 最近一次被调用的间隔时间内上传、下载的 byte 长度 )
    private var lastSize: Long,
    // 最近一次刷新、触发时间 ( 毫秒 )
    private var lastRefreshTime: Long,
    // 当前状态
    private var status: Int,
    // 上传、下载网速信息
    private val speed: Speed
) : Parcelable {

    constructor(id: Long = System.currentTimeMillis()) : this(
        id, 0L, 0L, 0L, 0L,
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

    /**
     * 获取上传、下载网速信息
     * @return Speed
     */
    fun getSpeed(): Speed {
        return speed
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
        private var speedValue: Long,
        // 网速做平滑的缓存, 避免抖动过大
        private val speedBuffer: MutableList<Long>,
        // 网速缓存数据存储数量
        private var bufferSize: Int
    ) : Parcelable {

        constructor() : this(0L, mutableListOf(), 10)

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 获取上传、下载网速信息 ( byte/s )
         * @return 网速信息 ( byte/s )
         * 可搭配 [FileUtils.formatByteMemorySize] 使用
         */
        fun getSpeed(): Long {
            return speedValue
        }

        /**
         * 获取上传、下载网速信息 ( byte/s )
         * @param number 保留小数位数
         * @return 网速信息 ( byte/s )
         */
        fun getSpeedFormat(number: Int = 1): String {
            return FileUtils.formatByteMemorySize(
                number, speedValue.toDouble()
            ) + "/s"
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

        // ==========
        // = 内部方法 =
        // ==========

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
            var sum: Long = 0
            for (speedTemp in speedBuffer) {
                sum += speedTemp
            }
            return sum / speedBuffer.size
        }
    }
}