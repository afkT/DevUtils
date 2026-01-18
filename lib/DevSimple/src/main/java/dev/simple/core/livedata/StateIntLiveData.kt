package dev.simple.core.livedata

/**
 * detail: 状态类型值
 * @author Ttt
 */
object StateInt {

    // 默认状态
    const val NORMAL = 1020301

    // 操作中
    const val ING = 1020302

    // 操作成功
    const val SUCCESS = 1020303

    // 操作失败
    const val FAIL = 1020304

    // 操作异常
    const val ERROR = 1020305

    // 开始操作
    const val START = 1020306

    // 重新开始操作
    const val RESTART = 1020307

    // 操作结束
    const val END = 1020308

    // 操作暂停
    const val PAUSE = 1020309

    // 操作恢复 ( 继续 )
    const val RESUME = 1020310

    // 操作停止
    const val STOP = 1020311

    // 操作取消
    const val CANCEL = 1020312

    // 创建
    const val CREATE = 1020313

    // 销毁
    const val DESTROY = 1020314

    // 回收
    const val RECYCLE = 1020315

    // 初始化
    const val INIT = 1020316

    // 已打开
    const val ENABLED = 1020317

    // 正在打开
    const val ENABLING = 1020318

    // 已关闭
    const val DISABLED = 1020319

    // 正在关闭
    const val DISABLING = 1020320

    // 连接成功
    const val CONNECTED = 1020321

    // 连接中
    const val CONNECTING = 1020322

    // 连接失败、断开
    const val DISCONNECTED = 1020323

    // 暂停、延迟
    const val SUSPENDED = 1020324

    // 未知
    const val UNKNOWN = 1020325

    // 新增
    const val INSERT = 1020326

    // 删除
    const val DELETE = 1020327

    // 更新
    const val UPDATE = 1020328

    // 查询
    const val SELECT = 1020329

    // 加密
    const val ENCRYPT = 1020330

    // 解密
    const val DECRYPT = 1020331

    // 重置
    const val RESET = 1020332

    // 关闭
    const val CLOSE = 1020333

    // 打开
    const val OPEN = 1020334

    // 退出
    const val EXIT = 1020335

    // 下一步
    const val NEXT = 1020336

    // 无任何
    const val NONE = 1020337

    // 结束
    const val FINISH = 1020338

    // 等待中
    const val WAITING = 1020339

    // 完成
    const val COMPLETE = 1020340
}

/**
 * detail: State Int LiveData
 * @author Ttt
 */
open class StateIntLiveData(
    _value: Int = StateInt.NORMAL
) : PayloadLiveData<Int>(_value) {

    /**
     * 是否 XXX 状态
     * @return `true` yes, `false` no
     */
    open fun isXXXState(state: Int): Boolean {
        return isEqual(state)
    }

    /**
     * 设置为 XXX 状态 ( 主线程 )
     * @return `true` success, `false` fail
     */
    open fun setXXX(state: Int): Boolean {
        return setPayload(state)
    }

    /**
     * 设置为 XXX 状态 ( 子线程 )
     * @return `true` success, `false` fail
     */
    open fun postXXX(state: Int): Boolean {
        return postPayload(state)
    }

    /**
     * 设置为 XXX 状态 ( 智能线程判断 )
     * @return `true` success, `false` fail
     */
    open fun smartUpdateXXX(state: Int): Boolean {
        return smartUpdatePayload(state)
    }

    // =

    // NORMAL
    open fun isNORMALState(): Boolean = isEqual(StateInt.NORMAL)
    open fun setNORMAL(): Boolean = setPayload(StateInt.NORMAL)
    open fun postNORMAL(): Boolean = postPayload(StateInt.NORMAL)
    open fun smartUpdateNORMAL(): Boolean = smartUpdatePayload(StateInt.NORMAL)

    // ING
    open fun isINGState(): Boolean = isEqual(StateInt.ING)
    open fun setING(): Boolean = setPayload(StateInt.ING)
    open fun postING(): Boolean = postPayload(StateInt.ING)
    open fun smartUpdateING(): Boolean = smartUpdatePayload(StateInt.ING)

    // SUCCESS
    open fun isSUCCESSState(): Boolean = isEqual(StateInt.SUCCESS)
    open fun setSUCCESS(): Boolean = setPayload(StateInt.SUCCESS)
    open fun postSUCCESS(): Boolean = postPayload(StateInt.SUCCESS)
    open fun smartUpdateSUCCESS(): Boolean = smartUpdatePayload(StateInt.SUCCESS)

    // FAIL
    open fun isFAILState(): Boolean = isEqual(StateInt.FAIL)
    open fun setFAIL(): Boolean = setPayload(StateInt.FAIL)
    open fun postFAIL(): Boolean = postPayload(StateInt.FAIL)
    open fun smartUpdateFAIL(): Boolean = smartUpdatePayload(StateInt.FAIL)

    // ERROR
    open fun isERRORState(): Boolean = isEqual(StateInt.ERROR)
    open fun setERROR(): Boolean = setPayload(StateInt.ERROR)
    open fun postERROR(): Boolean = postPayload(StateInt.ERROR)
    open fun smartUpdateERROR(): Boolean = smartUpdatePayload(StateInt.ERROR)

    // START
    open fun isSTARTState(): Boolean = isEqual(StateInt.START)
    open fun setSTART(): Boolean = setPayload(StateInt.START)
    open fun postSTART(): Boolean = postPayload(StateInt.START)
    open fun smartUpdateSTART(): Boolean = smartUpdatePayload(StateInt.START)

    // RESTART
    open fun isRESTARTState(): Boolean = isEqual(StateInt.RESTART)
    open fun setRESTART(): Boolean = setPayload(StateInt.RESTART)
    open fun postRESTART(): Boolean = postPayload(StateInt.RESTART)
    open fun smartUpdateRESTART(): Boolean = smartUpdatePayload(StateInt.RESTART)

    // END
    open fun isENDState(): Boolean = isEqual(StateInt.END)
    open fun setEND(): Boolean = setPayload(StateInt.END)
    open fun postEND(): Boolean = postPayload(StateInt.END)
    open fun smartUpdateEND(): Boolean = smartUpdatePayload(StateInt.END)

    // PAUSE
    open fun isPAUSEState(): Boolean = isEqual(StateInt.PAUSE)
    open fun setPAUSE(): Boolean = setPayload(StateInt.PAUSE)
    open fun postPAUSE(): Boolean = postPayload(StateInt.PAUSE)
    open fun smartUpdatePAUSE(): Boolean = smartUpdatePayload(StateInt.PAUSE)

    // RESUME
    open fun isRESUMEState(): Boolean = isEqual(StateInt.RESUME)
    open fun setRESUME(): Boolean = setPayload(StateInt.RESUME)
    open fun postRESUME(): Boolean = postPayload(StateInt.RESUME)
    open fun smartUpdateRESUME(): Boolean = smartUpdatePayload(StateInt.RESUME)

    // STOP
    open fun isSTOPState(): Boolean = isEqual(StateInt.STOP)
    open fun setSTOP(): Boolean = setPayload(StateInt.STOP)
    open fun postSTOP(): Boolean = postPayload(StateInt.STOP)
    open fun smartUpdateSTOP(): Boolean = smartUpdatePayload(StateInt.STOP)

    // CANCEL
    open fun isCANCELState(): Boolean = isEqual(StateInt.CANCEL)
    open fun setCANCEL(): Boolean = setPayload(StateInt.CANCEL)
    open fun postCANCEL(): Boolean = postPayload(StateInt.CANCEL)
    open fun smartUpdateCANCEL(): Boolean = smartUpdatePayload(StateInt.CANCEL)

    // CREATE
    open fun isCREATEState(): Boolean = isEqual(StateInt.CREATE)
    open fun setCREATE(): Boolean = setPayload(StateInt.CREATE)
    open fun postCREATE(): Boolean = postPayload(StateInt.CREATE)
    open fun smartUpdateCREATE(): Boolean = smartUpdatePayload(StateInt.CREATE)

    // DESTROY
    open fun isDESTROYState(): Boolean = isEqual(StateInt.DESTROY)
    open fun setDESTROY(): Boolean = setPayload(StateInt.DESTROY)
    open fun postDESTROY(): Boolean = postPayload(StateInt.DESTROY)
    open fun smartUpdateDESTROY(): Boolean = smartUpdatePayload(StateInt.DESTROY)

    // RECYCLE
    open fun isRECYCLEState(): Boolean = isEqual(StateInt.RECYCLE)
    open fun setRECYCLE(): Boolean = setPayload(StateInt.RECYCLE)
    open fun postRECYCLE(): Boolean = postPayload(StateInt.RECYCLE)
    open fun smartUpdateRECYCLE(): Boolean = smartUpdatePayload(StateInt.RECYCLE)

    // INIT
    open fun isINITState(): Boolean = isEqual(StateInt.INIT)
    open fun setINIT(): Boolean = setPayload(StateInt.INIT)
    open fun postINIT(): Boolean = postPayload(StateInt.INIT)
    open fun smartUpdateINIT(): Boolean = smartUpdatePayload(StateInt.INIT)

    // ENABLED
    open fun isENABLEDState(): Boolean = isEqual(StateInt.ENABLED)
    open fun setENABLED(): Boolean = setPayload(StateInt.ENABLED)
    open fun postENABLED(): Boolean = postPayload(StateInt.ENABLED)
    open fun smartUpdateENABLED(): Boolean = smartUpdatePayload(StateInt.ENABLED)

    // ENABLING
    open fun isENABLINGState(): Boolean = isEqual(StateInt.ENABLING)
    open fun setENABLING(): Boolean = setPayload(StateInt.ENABLING)
    open fun postENABLING(): Boolean = postPayload(StateInt.ENABLING)
    open fun smartUpdateENABLING(): Boolean = smartUpdatePayload(StateInt.ENABLING)

    // DISABLED
    open fun isDISABLEDState(): Boolean = isEqual(StateInt.DISABLED)
    open fun setDISABLED(): Boolean = setPayload(StateInt.DISABLED)
    open fun postDISABLED(): Boolean = postPayload(StateInt.DISABLED)
    open fun smartUpdateDISABLED(): Boolean = smartUpdatePayload(StateInt.DISABLED)

    // DISABLING
    open fun isDISABLINGState(): Boolean = isEqual(StateInt.DISABLING)
    open fun setDISABLING(): Boolean = setPayload(StateInt.DISABLING)
    open fun postDISABLING(): Boolean = postPayload(StateInt.DISABLING)
    open fun smartUpdateDISABLING(): Boolean = smartUpdatePayload(StateInt.DISABLING)

    // CONNECTED
    open fun isCONNECTEDState(): Boolean = isEqual(StateInt.CONNECTED)
    open fun setCONNECTED(): Boolean = setPayload(StateInt.CONNECTED)
    open fun postCONNECTED(): Boolean = postPayload(StateInt.CONNECTED)
    open fun smartUpdateCONNECTED(): Boolean = smartUpdatePayload(StateInt.CONNECTED)

    // CONNECTING
    open fun isCONNECTINGState(): Boolean = isEqual(StateInt.CONNECTING)
    open fun setCONNECTING(): Boolean = setPayload(StateInt.CONNECTING)
    open fun postCONNECTING(): Boolean = postPayload(StateInt.CONNECTING)
    open fun smartUpdateCONNECTING(): Boolean = smartUpdatePayload(StateInt.CONNECTING)

    // DISCONNECTED
    open fun isDISCONNECTEDState(): Boolean = isEqual(StateInt.DISCONNECTED)
    open fun setDISCONNECTED(): Boolean = setPayload(StateInt.DISCONNECTED)
    open fun postDISCONNECTED(): Boolean = postPayload(StateInt.DISCONNECTED)
    open fun smartUpdateDISCONNECTED(): Boolean = smartUpdatePayload(StateInt.DISCONNECTED)

    // SUSPENDED
    open fun isSUSPENDEDState(): Boolean = isEqual(StateInt.SUSPENDED)
    open fun setSUSPENDED(): Boolean = setPayload(StateInt.SUSPENDED)
    open fun postSUSPENDED(): Boolean = postPayload(StateInt.SUSPENDED)
    open fun smartUpdateSUSPENDED(): Boolean = smartUpdatePayload(StateInt.SUSPENDED)

    // UNKNOWN
    open fun isUNKNOWNState(): Boolean = isEqual(StateInt.UNKNOWN)
    open fun setUNKNOWN(): Boolean = setPayload(StateInt.UNKNOWN)
    open fun postUNKNOWN(): Boolean = postPayload(StateInt.UNKNOWN)
    open fun smartUpdateUNKNOWN(): Boolean = smartUpdatePayload(StateInt.UNKNOWN)

    // INSERT
    open fun isINSERTState(): Boolean = isEqual(StateInt.INSERT)
    open fun setINSERT(): Boolean = setPayload(StateInt.INSERT)
    open fun postINSERT(): Boolean = postPayload(StateInt.INSERT)
    open fun smartUpdateINSERT(): Boolean = smartUpdatePayload(StateInt.INSERT)

    // DELETE
    open fun isDELETEState(): Boolean = isEqual(StateInt.DELETE)
    open fun setDELETE(): Boolean = setPayload(StateInt.DELETE)
    open fun postDELETE(): Boolean = postPayload(StateInt.DELETE)
    open fun smartUpdateDELETE(): Boolean = smartUpdatePayload(StateInt.DELETE)

    // UPDATE
    open fun isUPDATEState(): Boolean = isEqual(StateInt.UPDATE)
    open fun setUPDATE(): Boolean = setPayload(StateInt.UPDATE)
    open fun postUPDATE(): Boolean = postPayload(StateInt.UPDATE)
    open fun smartUpdateUPDATE(): Boolean = smartUpdatePayload(StateInt.UPDATE)

    // SELECT
    open fun isSELECTState(): Boolean = isEqual(StateInt.SELECT)
    open fun setSELECT(): Boolean = setPayload(StateInt.SELECT)
    open fun postSELECT(): Boolean = postPayload(StateInt.SELECT)
    open fun smartUpdateSELECT(): Boolean = smartUpdatePayload(StateInt.SELECT)

    // ENCRYPT
    open fun isENCRYPTState(): Boolean = isEqual(StateInt.ENCRYPT)
    open fun setENCRYPT(): Boolean = setPayload(StateInt.ENCRYPT)
    open fun postENCRYPT(): Boolean = postPayload(StateInt.ENCRYPT)
    open fun smartUpdateENCRYPT(): Boolean = smartUpdatePayload(StateInt.ENCRYPT)

    // DECRYPT
    open fun isDECRYPTState(): Boolean = isEqual(StateInt.DECRYPT)
    open fun setDECRYPT(): Boolean = setPayload(StateInt.DECRYPT)
    open fun postDECRYPT(): Boolean = postPayload(StateInt.DECRYPT)
    open fun smartUpdateDECRYPT(): Boolean = smartUpdatePayload(StateInt.DECRYPT)

    // RESET
    open fun isRESETState(): Boolean = isEqual(StateInt.RESET)
    open fun setRESET(): Boolean = setPayload(StateInt.RESET)
    open fun postRESET(): Boolean = postPayload(StateInt.RESET)
    open fun smartUpdateRESET(): Boolean = smartUpdatePayload(StateInt.RESET)

    // CLOSE
    open fun isCLOSEState(): Boolean = isEqual(StateInt.CLOSE)
    open fun setCLOSE(): Boolean = setPayload(StateInt.CLOSE)
    open fun postCLOSE(): Boolean = postPayload(StateInt.CLOSE)
    open fun smartUpdateCLOSE(): Boolean = smartUpdatePayload(StateInt.CLOSE)

    // OPEN
    open fun isOPENState(): Boolean = isEqual(StateInt.OPEN)
    open fun setOPEN(): Boolean = setPayload(StateInt.OPEN)
    open fun postOPEN(): Boolean = postPayload(StateInt.OPEN)
    open fun smartUpdateOPEN(): Boolean = smartUpdatePayload(StateInt.OPEN)

    // EXIT
    open fun isEXITState(): Boolean = isEqual(StateInt.EXIT)
    open fun setEXIT(): Boolean = setPayload(StateInt.EXIT)
    open fun postEXIT(): Boolean = postPayload(StateInt.EXIT)
    open fun smartUpdateEXIT(): Boolean = smartUpdatePayload(StateInt.EXIT)

    // NEXT
    open fun isNEXTState(): Boolean = isEqual(StateInt.NEXT)
    open fun setNEXT(): Boolean = setPayload(StateInt.NEXT)
    open fun postNEXT(): Boolean = postPayload(StateInt.NEXT)
    open fun smartUpdateNEXT(): Boolean = smartUpdatePayload(StateInt.NEXT)

    // NONE
    open fun isNONEState(): Boolean = isEqual(StateInt.NONE)
    open fun setNONE(): Boolean = setPayload(StateInt.NONE)
    open fun postNONE(): Boolean = postPayload(StateInt.NONE)
    open fun smartUpdateNONE(): Boolean = smartUpdatePayload(StateInt.NONE)

    // FINISH
    open fun isFINISHState(): Boolean = isEqual(StateInt.FINISH)
    open fun setFINISH(): Boolean = setPayload(StateInt.FINISH)
    open fun postFINISH(): Boolean = postPayload(StateInt.FINISH)
    open fun smartUpdateFINISH(): Boolean = smartUpdatePayload(StateInt.FINISH)

    // WAITING
    open fun isWAITINGState(): Boolean = isEqual(StateInt.WAITING)
    open fun setWAITING(): Boolean = setPayload(StateInt.WAITING)
    open fun postWAITING(): Boolean = postPayload(StateInt.WAITING)
    open fun smartUpdateWAITING(): Boolean = smartUpdatePayload(StateInt.WAITING)

    // COMPLETE
    open fun isCOMPLETEState(): Boolean = isEqual(StateInt.COMPLETE)
    open fun setCOMPLETE(): Boolean = setPayload(StateInt.COMPLETE)
    open fun postCOMPLETE(): Boolean = postPayload(StateInt.COMPLETE)
    open fun smartUpdateCOMPLETE(): Boolean = smartUpdatePayload(StateInt.COMPLETE)
}