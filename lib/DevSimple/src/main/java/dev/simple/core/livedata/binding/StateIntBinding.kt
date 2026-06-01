package dev.simple.core.livedata.binding

import dev.simple.core.livedata.StateInt

/**
 * detail: StateInt DataBinding 取值辅助
 * @author Ttt
 * <pre>
 *     供布局表达式直接对状态值做判断，避免在 XML 中 import [StateInt]
 *     入参须传 LiveData 的当前值 ( 如 `@{StateIntBinding.isSuccess(liveData.value)}` )，
 *     而非 LiveData 对象本身，DataBinding 才会观察值变化并实时刷新；
 *     若传 LiveData 对象调用其实例方法 ( 如 `isSUCCESSState()` ) 则只在变量重新赋值时计算一次。
 * </pre>
 */
object StateIntBinding {

    /**
     * 判断状态值是否等于目标状态
     * @param state 当前状态值，可为 null
     * @param target 目标状态值
     * @return `true` 相等, `false` 不相等
     */
    @JvmStatic
    fun isState(
        state: Int?,
        target: Int
    ): Boolean {
        return state == target
    }

    // ===========
    // = 状态快捷 =
    // ===========

    // NORMAL
    @JvmStatic
    fun isNormal(state: Int?): Boolean = isState(state, StateInt.NORMAL)

    // ING
    @JvmStatic
    fun isIng(state: Int?): Boolean = isState(state, StateInt.ING)

    // SUCCESS
    @JvmStatic
    fun isSuccess(state: Int?): Boolean = isState(state, StateInt.SUCCESS)

    // FAIL
    @JvmStatic
    fun isFail(state: Int?): Boolean = isState(state, StateInt.FAIL)

    // ERROR
    @JvmStatic
    fun isError(state: Int?): Boolean = isState(state, StateInt.ERROR)

    // START
    @JvmStatic
    fun isStart(state: Int?): Boolean = isState(state, StateInt.START)

    // RESTART
    @JvmStatic
    fun isRestart(state: Int?): Boolean = isState(state, StateInt.RESTART)

    // END
    @JvmStatic
    fun isEnd(state: Int?): Boolean = isState(state, StateInt.END)

    // PAUSE
    @JvmStatic
    fun isPause(state: Int?): Boolean = isState(state, StateInt.PAUSE)

    // RESUME
    @JvmStatic
    fun isResume(state: Int?): Boolean = isState(state, StateInt.RESUME)

    // STOP
    @JvmStatic
    fun isStop(state: Int?): Boolean = isState(state, StateInt.STOP)

    // CANCEL
    @JvmStatic
    fun isCancel(state: Int?): Boolean = isState(state, StateInt.CANCEL)

    // CREATE
    @JvmStatic
    fun isCreate(state: Int?): Boolean = isState(state, StateInt.CREATE)

    // DESTROY
    @JvmStatic
    fun isDestroy(state: Int?): Boolean = isState(state, StateInt.DESTROY)

    // RECYCLE
    @JvmStatic
    fun isRecycle(state: Int?): Boolean = isState(state, StateInt.RECYCLE)

    // INIT
    @JvmStatic
    fun isInit(state: Int?): Boolean = isState(state, StateInt.INIT)

    // ENABLED
    @JvmStatic
    fun isEnabled(state: Int?): Boolean = isState(state, StateInt.ENABLED)

    // ENABLING
    @JvmStatic
    fun isEnabling(state: Int?): Boolean = isState(state, StateInt.ENABLING)

    // DISABLED
    @JvmStatic
    fun isDisabled(state: Int?): Boolean = isState(state, StateInt.DISABLED)

    // DISABLING
    @JvmStatic
    fun isDisabling(state: Int?): Boolean = isState(state, StateInt.DISABLING)

    // CONNECTED
    @JvmStatic
    fun isConnected(state: Int?): Boolean = isState(state, StateInt.CONNECTED)

    // CONNECTING
    @JvmStatic
    fun isConnecting(state: Int?): Boolean = isState(state, StateInt.CONNECTING)

    // DISCONNECTED
    @JvmStatic
    fun isDisconnected(state: Int?): Boolean = isState(state, StateInt.DISCONNECTED)

    // SUSPENDED
    @JvmStatic
    fun isSuspended(state: Int?): Boolean = isState(state, StateInt.SUSPENDED)

    // UNKNOWN
    @JvmStatic
    fun isUnknown(state: Int?): Boolean = isState(state, StateInt.UNKNOWN)

    // INSERT
    @JvmStatic
    fun isInsert(state: Int?): Boolean = isState(state, StateInt.INSERT)

    // DELETE
    @JvmStatic
    fun isDelete(state: Int?): Boolean = isState(state, StateInt.DELETE)

    // UPDATE
    @JvmStatic
    fun isUpdate(state: Int?): Boolean = isState(state, StateInt.UPDATE)

    // SELECT
    @JvmStatic
    fun isSelect(state: Int?): Boolean = isState(state, StateInt.SELECT)

    // ENCRYPT
    @JvmStatic
    fun isEncrypt(state: Int?): Boolean = isState(state, StateInt.ENCRYPT)

    // DECRYPT
    @JvmStatic
    fun isDecrypt(state: Int?): Boolean = isState(state, StateInt.DECRYPT)

    // RESET
    @JvmStatic
    fun isReset(state: Int?): Boolean = isState(state, StateInt.RESET)

    // CLOSE
    @JvmStatic
    fun isClose(state: Int?): Boolean = isState(state, StateInt.CLOSE)

    // OPEN
    @JvmStatic
    fun isOpen(state: Int?): Boolean = isState(state, StateInt.OPEN)

    // EXIT
    @JvmStatic
    fun isExit(state: Int?): Boolean = isState(state, StateInt.EXIT)

    // NEXT
    @JvmStatic
    fun isNext(state: Int?): Boolean = isState(state, StateInt.NEXT)

    // NONE
    @JvmStatic
    fun isNone(state: Int?): Boolean = isState(state, StateInt.NONE)

    // FINISH
    @JvmStatic
    fun isFinish(state: Int?): Boolean = isState(state, StateInt.FINISH)

    // WAITING
    @JvmStatic
    fun isWaiting(state: Int?): Boolean = isState(state, StateInt.WAITING)

    // COMPLETE
    @JvmStatic
    fun isComplete(state: Int?): Boolean = isState(state, StateInt.COMPLETE)

    // LOADING
    @JvmStatic
    fun isLoading(state: Int?): Boolean = isState(state, StateInt.LOADING)

    // EMPTY
    @JvmStatic
    fun isEmpty(state: Int?): Boolean = isState(state, StateInt.EMPTY)

    // TIMEOUT
    @JvmStatic
    fun isTimeout(state: Int?): Boolean = isState(state, StateInt.TIMEOUT)

    // DISCONNECTING
    @JvmStatic
    fun isDisconnecting(state: Int?): Boolean = isState(state, StateInt.DISCONNECTING)

    // REFRESHING
    @JvmStatic
    fun isRefreshing(state: Int?): Boolean = isState(state, StateInt.REFRESHING)

    // LOAD_MORE
    @JvmStatic
    fun isLoadMore(state: Int?): Boolean = isState(state, StateInt.LOAD_MORE)

    // NO_MORE
    @JvmStatic
    fun isNoMore(state: Int?): Boolean = isState(state, StateInt.NO_MORE)

    // VALID
    @JvmStatic
    fun isValid(state: Int?): Boolean = isState(state, StateInt.VALID)

    // INVALID
    @JvmStatic
    fun isInvalid(state: Int?): Boolean = isState(state, StateInt.INVALID)

    // FOUND
    @JvmStatic
    fun isFound(state: Int?): Boolean = isState(state, StateInt.FOUND)

    // NOT_FOUND
    @JvmStatic
    fun isNotFound(state: Int?): Boolean = isState(state, StateInt.NOT_FOUND)

    // GRANTED
    @JvmStatic
    fun isGranted(state: Int?): Boolean = isState(state, StateInt.GRANTED)

    // DENIED
    @JvmStatic
    fun isDenied(state: Int?): Boolean = isState(state, StateInt.DENIED)

    // PREPARING
    @JvmStatic
    fun isPreparing(state: Int?): Boolean = isState(state, StateInt.PREPARING)

    // PREPARED
    @JvmStatic
    fun isPrepared(state: Int?): Boolean = isState(state, StateInt.PREPARED)

    // PLAYING
    @JvmStatic
    fun isPlaying(state: Int?): Boolean = isState(state, StateInt.PLAYING)

    // BUFFERING
    @JvmStatic
    fun isBuffering(state: Int?): Boolean = isState(state, StateInt.BUFFERING)

    // SEEKING
    @JvmStatic
    fun isSeeking(state: Int?): Boolean = isState(state, StateInt.SEEKING)

    // RECORDING
    @JvmStatic
    fun isRecording(state: Int?): Boolean = isState(state, StateInt.RECORDING)

    // DOWNLOADING
    @JvmStatic
    fun isDownloading(state: Int?): Boolean = isState(state, StateInt.DOWNLOADING)

    // UPLOADING
    @JvmStatic
    fun isUploading(state: Int?): Boolean = isState(state, StateInt.UPLOADING)

    // DOWNLOADED
    @JvmStatic
    fun isDownloaded(state: Int?): Boolean = isState(state, StateInt.DOWNLOADED)

    // UPLOADED
    @JvmStatic
    fun isUploaded(state: Int?): Boolean = isState(state, StateInt.UPLOADED)

    // ANIMATING
    @JvmStatic
    fun isAnimating(state: Int?): Boolean = isState(state, StateInt.ANIMATING)

    // REPEAT
    @JvmStatic
    fun isRepeat(state: Int?): Boolean = isState(state, StateInt.REPEAT)
}