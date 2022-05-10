package dev.http.progress

import android.os.Handler
import android.os.SystemClock

// ==========
// = 更新进度 =
// ==========

/**
 * 更新进度信息并返回是否允许通知
 * @param progress 进度信息存储类
 * @param refreshTime 回调刷新时间 ( 毫秒 )
 * @param writeSize 写入数据大小
 * @return `true` yes, `false` no
 */
internal fun changeProgress(
    progress: Progress,
    refreshTime: Long,
    writeSize: Long
): Boolean {
    return changeProgress(progress, refreshTime, writeSize, progress.getTotalSize())
}

/**
 * 更新进度信息并返回是否允许通知
 * @param progress 进度信息存储类
 * @param refreshTime 回调刷新时间 ( 毫秒 )
 * @param writeSize 写入数据大小
 * @param totalSize 数据总长度
 * @return `true` yes, `false` no
 */
internal fun changeProgress(
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
        return true
    }
    return false
}

// ==========
// = 事件通知 =
// ==========

/**
 * 回调方法
 * @param notifyStatus 通知状态
 * @param callback 上传、下载回调接口
 * @param handler 回调 UI 线程通知
 */
private fun Progress.callback(
    notifyStatus: Int,
    callback: Progress.Callback?,
    handler: Handler?
) {
    // 准备通知状态与当前状态相同才触发回调, 防止多次触发当前状态回调
    if (notifyStatus == getStatus()) {
        callback?.let { itCallback ->
            handler?.post {
                innerCallback(notifyStatus, itCallback)
            } ?: innerCallback(notifyStatus, itCallback)
        }
    }
}

/**
 * 回调方法
 * @param status Progress 当前状态
 * @param callback 上传、下载回调接口
 * status 不通过 [Progress.getStatus] 获取, 而是通过传参判断
 * 是防止线程触发回调中进行更新状态, 导致跳过 START 回调
 */
private fun Progress.innerCallback(
    status: Int,
    callback: Progress.Callback
) {
    when (status) {
        Progress.START -> {
            callback.onStart(this)
        }
        Progress.ING -> {
            callback.onProgress(this)
        }
        Progress.ERROR -> {
            callback.onError(this)
            callback.onEnd(this)
        }
        Progress.FINISH -> {
            callback.onFinish(this)
            callback.onEnd(this)
        }
    }
}

// ===============
// = 更新状态并通知 =
// ===============

/**
 * 设置为 [Progress.START] 状态并且进行通知
 * @param callback 上传、下载回调接口
 * @param handler  回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
 */
internal fun Progress.toStartAndCallback(
    callback: Progress.Callback?,
    handler: Handler?
) {
    if (toStart()) callback(Progress.START, callback, handler)
}

/**
 * 设置为 [Progress.ING] 状态并且进行通知
 * @param callback 上传、下载回调接口
 * @param handler  回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
 */
internal fun Progress.toIngAndCallback(
    callback: Progress.Callback?,
    handler: Handler?
) {
    toIng()
    callback(Progress.ING, callback, handler)
}

/**
 * 设置为 [Progress.ERROR] 状态并且进行通知
 * @param exception 进度异常信息
 * @param callback  上传、下载回调接口
 * @param handler   回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
 */
internal fun Progress.toErrorAndCallback(
    exception: Throwable,
    callback: Progress.Callback?,
    handler: Handler?
) {
    if (toError(exception)) callback(Progress.ERROR, callback, handler)
}

/**
 * 设置为 [Progress.FINISH] 状态并且进行通知
 * @param callback 上传、下载回调接口
 * @param handler  回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
 */
internal fun Progress.toFinishAndCallback(
    callback: Progress.Callback?,
    handler: Handler?
) {
    if (toFinish()) callback(Progress.FINISH, callback, handler)
}

// =============
// = 流式更新状态 =
// =============

/**
 * 设置为 [Progress.ING] 状态
 * @return Progress
 */
internal fun Progress.flowIng(): Progress {
    toIng()
    return this
}