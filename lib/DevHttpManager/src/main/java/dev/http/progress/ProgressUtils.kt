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
 * @param callback 上传、下载回调接口
 * @param handler 回调 UI 线程通知
 */
internal fun Progress.callback(
    callback: Progress.Callback?,
    handler: Handler?
) {
    callback?.let { itCallback ->
        handler?.post {
            innerCallback(itCallback)
        } ?: innerCallback(itCallback)
    }
}

/**
 * 回调方法
 * @param callback 上传、下载回调接口
 */
private fun Progress.innerCallback(callback: Progress.Callback) {
    when (getStatus()) {
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