package dev.http.progress

import android.os.SystemClock

/**
 * detail: 进度快捷操作内部工具类
 * @author Ttt
 */
internal class ProgressUtils private constructor() {

    companion object {

        val instance: ProgressUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ProgressUtils()
        }
    }

    // ==========
    // = 更新状态 =
    // ==========

    /**
     * 设置为 [Progress.START] 状态
     * @param progress Progress
     * @param totalSize 数据总长度
     * @return ProgressUtils
     */
    fun toStart(
        progress: Progress,
        totalSize: Long
    ): ProgressUtils {
        if (progress.isNORMAL()) {
            progress.setStatus(Progress.START)
                .setTotalSize(totalSize)
                .setLastRefreshTime(SystemClock.elapsedRealtime())
        }
        return this
    }

    /**
     * 设置为 [Progress.ING] 状态
     * @param progress Progress
     * @return ProgressUtils
     */
    fun toIng(progress: Progress): ProgressUtils {
        if (progress.isSTART()) {
            progress.setStatus(Progress.ING)
        }
        return this
    }

    /**
     * 设置为 [Progress.ERROR] 状态
     * @param progress Progress
     * @param exception 进度异常信息
     * @return ProgressUtils
     */
    fun toError(
        progress: Progress,
        exception: Throwable
    ): ProgressUtils {
        if (progress.isING()) {
            progress.setStatus(Progress.ERROR)
                .setException(exception)
        }
        return this
    }

    /**
     * 设置为 [Progress.FINISH] 状态
     * @param progress Progress
     * @return ProgressUtils
     */
    fun toFinish(progress: Progress): ProgressUtils {
        if (progress.isING()) {
            progress.setStatus(Progress.FINISH)
        }
        return this
    }

    // ==========
    // = 更新进度 =
    // ==========

    /**
     * 更新进度信息并返回是否进行通知
     * @param progress 进度信息存储类
     * @param refreshTime 回调刷新时间 ( 毫秒 )
     * @param writeSize 写入数据大小
     * @return `true` yes, `false` no
     */
    fun changeProgress(
        progress: Progress,
        refreshTime: Long,
        writeSize: Long
    ): Boolean {
        return changeProgress(progress, refreshTime, writeSize, progress.getTotalSize())
    }

    /**
     * 更新进度信息并返回是否进行通知
     * @param progress 进度信息存储类
     * @param refreshTime 回调刷新时间 ( 毫秒 )
     * @param writeSize 写入数据大小
     * @param totalSize 数据总长度
     * @return `true` yes, `false` no
     */
    fun changeProgress(
        progress: Progress,
        refreshTime: Long,
        writeSize: Long,
        totalSize: Long
    ): Boolean {
        progress.setTotalSize(totalSize)
            .setCurrentSize(progress.getCurrentSize() + writeSize)
            .setLastSize(progress.getLastSize() + writeSize)

        val currentTime = SystemClock.elapsedRealtime()
        var diffTime = currentTime - progress.getLastRefreshTime()
        // 判断当前时间 - 最近一次刷新时间是否超过回调间隔时间
        val isNotify = (diffTime >= refreshTime)
        val isFinish = (progress.getCurrentSize() == totalSize)
        if (isNotify || isFinish) {
            if (diffTime == 0L) diffTime = 1L
            val lastSize = progress.getLastSize()
            // 存储网速信息并刷新网速信息 byte/s
            progress.getSpeed().bufferSpeed(
                lastSize * 1000L / diffTime
            )
            progress.setLastSize(0L)
                .setLastRefreshTime(currentTime)
            // 如果完成了则更新完成状态
            if (isFinish) toFinish(progress)
            return true
        }
        return false
    }

    // ==========
    // = 事件通知 =
    // ==========
}