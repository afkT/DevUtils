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
) : ValueLiveData<Int>(_value) {

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
        return setValue(state)
    }

    /**
     * 设置为 XXX 状态 ( 子线程 )
     * @return `true` success, `false` fail
     */
    open fun postXXX(state: Int): Boolean {
        return postValue(state)
    }

    /**
     * 设置为 XXX 状态 ( 智能线程判断 )
     * @return `true` success, `false` fail
     */
    open fun smartUpdateXXX(state: Int): Boolean {
        return smartUpdateValue(state)
    }

    // =

    // NORMAL
    open fun isNORMALState(): Boolean = isEqual(StateInt.NORMAL)
    open fun setNORMAL(): Boolean = setValue(StateInt.NORMAL)
    open fun postNORMAL(): Boolean = postValue(StateInt.NORMAL)
    open fun smartUpdateNORMAL(): Boolean = smartUpdateValue(StateInt.NORMAL)

    // ING
    open fun isINGState(): Boolean = isEqual(StateInt.ING)
    open fun setING(): Boolean = setValue(StateInt.ING)
    open fun postING(): Boolean = postValue(StateInt.ING)
    open fun smartUpdateING(): Boolean = smartUpdateValue(StateInt.ING)

    // SUCCESS
    open fun isSUCCESSState(): Boolean = isEqual(StateInt.SUCCESS)
    open fun setSUCCESS(): Boolean = setValue(StateInt.SUCCESS)
    open fun postSUCCESS(): Boolean = postValue(StateInt.SUCCESS)
    open fun smartUpdateSUCCESS(): Boolean = smartUpdateValue(StateInt.SUCCESS)

    // FAIL
    open fun isFAILState(): Boolean = isEqual(StateInt.FAIL)
    open fun setFAIL(): Boolean = setValue(StateInt.FAIL)
    open fun postFAIL(): Boolean = postValue(StateInt.FAIL)
    open fun smartUpdateFAIL(): Boolean = smartUpdateValue(StateInt.FAIL)

    // ERROR
    open fun isERRORState(): Boolean = isEqual(StateInt.ERROR)
    open fun setERROR(): Boolean = setValue(StateInt.ERROR)
    open fun postERROR(): Boolean = postValue(StateInt.ERROR)
    open fun smartUpdateERROR(): Boolean = smartUpdateValue(StateInt.ERROR)

    // START
    open fun isSTARTState(): Boolean = isEqual(StateInt.START)
    open fun setSTART(): Boolean = setValue(StateInt.START)
    open fun postSTART(): Boolean = postValue(StateInt.START)
    open fun smartUpdateSTART(): Boolean = smartUpdateValue(StateInt.START)

    // RESTART
    open fun isRESTARTState(): Boolean = isEqual(StateInt.RESTART)
    open fun setRESTART(): Boolean = setValue(StateInt.RESTART)
    open fun postRESTART(): Boolean = postValue(StateInt.RESTART)
    open fun smartUpdateRESTART(): Boolean = smartUpdateValue(StateInt.RESTART)

    // END
    open fun isENDState(): Boolean = isEqual(StateInt.END)
    open fun setEND(): Boolean = setValue(StateInt.END)
    open fun postEND(): Boolean = postValue(StateInt.END)
    open fun smartUpdateEND(): Boolean = smartUpdateValue(StateInt.END)

    // PAUSE
    open fun isPAUSEState(): Boolean = isEqual(StateInt.PAUSE)
    open fun setPAUSE(): Boolean = setValue(StateInt.PAUSE)
    open fun postPAUSE(): Boolean = postValue(StateInt.PAUSE)
    open fun smartUpdatePAUSE(): Boolean = smartUpdateValue(StateInt.PAUSE)

    // RESUME
    open fun isRESUMEState(): Boolean = isEqual(StateInt.RESUME)
    open fun setRESUME(): Boolean = setValue(StateInt.RESUME)
    open fun postRESUME(): Boolean = postValue(StateInt.RESUME)
    open fun smartUpdateRESUME(): Boolean = smartUpdateValue(StateInt.RESUME)

    // STOP
    open fun isSTOPState(): Boolean = isEqual(StateInt.STOP)
    open fun setSTOP(): Boolean = setValue(StateInt.STOP)
    open fun postSTOP(): Boolean = postValue(StateInt.STOP)
    open fun smartUpdateSTOP(): Boolean = smartUpdateValue(StateInt.STOP)

    // CANCEL
    open fun isCANCELState(): Boolean = isEqual(StateInt.CANCEL)
    open fun setCANCEL(): Boolean = setValue(StateInt.CANCEL)
    open fun postCANCEL(): Boolean = postValue(StateInt.CANCEL)
    open fun smartUpdateCANCEL(): Boolean = smartUpdateValue(StateInt.CANCEL)

    // CREATE
    open fun isCREATEState(): Boolean = isEqual(StateInt.CREATE)
    open fun setCREATE(): Boolean = setValue(StateInt.CREATE)
    open fun postCREATE(): Boolean = postValue(StateInt.CREATE)
    open fun smartUpdateCREATE(): Boolean = smartUpdateValue(StateInt.CREATE)

    // DESTROY
    open fun isDESTROYState(): Boolean = isEqual(StateInt.DESTROY)
    open fun setDESTROY(): Boolean = setValue(StateInt.DESTROY)
    open fun postDESTROY(): Boolean = postValue(StateInt.DESTROY)
    open fun smartUpdateDESTROY(): Boolean = smartUpdateValue(StateInt.DESTROY)

    // RECYCLE
    open fun isRECYCLEState(): Boolean = isEqual(StateInt.RECYCLE)
    open fun setRECYCLE(): Boolean = setValue(StateInt.RECYCLE)
    open fun postRECYCLE(): Boolean = postValue(StateInt.RECYCLE)
    open fun smartUpdateRECYCLE(): Boolean = smartUpdateValue(StateInt.RECYCLE)

    // INIT
    open fun isINITState(): Boolean = isEqual(StateInt.INIT)
    open fun setINIT(): Boolean = setValue(StateInt.INIT)
    open fun postINIT(): Boolean = postValue(StateInt.INIT)
    open fun smartUpdateINIT(): Boolean = smartUpdateValue(StateInt.INIT)

    // ENABLED
    open fun isENABLEDState(): Boolean = isEqual(StateInt.ENABLED)
    open fun setENABLED(): Boolean = setValue(StateInt.ENABLED)
    open fun postENABLED(): Boolean = postValue(StateInt.ENABLED)
    open fun smartUpdateENABLED(): Boolean = smartUpdateValue(StateInt.ENABLED)

    // ENABLING
    open fun isENABLINGState(): Boolean = isEqual(StateInt.ENABLING)
    open fun setENABLING(): Boolean = setValue(StateInt.ENABLING)
    open fun postENABLING(): Boolean = postValue(StateInt.ENABLING)
    open fun smartUpdateENABLING(): Boolean = smartUpdateValue(StateInt.ENABLING)

    // DISABLED
    open fun isDISABLEDState(): Boolean = isEqual(StateInt.DISABLED)
    open fun setDISABLED(): Boolean = setValue(StateInt.DISABLED)
    open fun postDISABLED(): Boolean = postValue(StateInt.DISABLED)
    open fun smartUpdateDISABLED(): Boolean = smartUpdateValue(StateInt.DISABLED)

    // DISABLING
    open fun isDISABLINGState(): Boolean = isEqual(StateInt.DISABLING)
    open fun setDISABLING(): Boolean = setValue(StateInt.DISABLING)
    open fun postDISABLING(): Boolean = postValue(StateInt.DISABLING)
    open fun smartUpdateDISABLING(): Boolean = smartUpdateValue(StateInt.DISABLING)

    // CONNECTED
    open fun isCONNECTEDState(): Boolean = isEqual(StateInt.CONNECTED)
    open fun setCONNECTED(): Boolean = setValue(StateInt.CONNECTED)
    open fun postCONNECTED(): Boolean = postValue(StateInt.CONNECTED)
    open fun smartUpdateCONNECTED(): Boolean = smartUpdateValue(StateInt.CONNECTED)

    // CONNECTING
    open fun isCONNECTINGState(): Boolean = isEqual(StateInt.CONNECTING)
    open fun setCONNECTING(): Boolean = setValue(StateInt.CONNECTING)
    open fun postCONNECTING(): Boolean = postValue(StateInt.CONNECTING)
    open fun smartUpdateCONNECTING(): Boolean = smartUpdateValue(StateInt.CONNECTING)

    // DISCONNECTED
    open fun isDISCONNECTEDState(): Boolean = isEqual(StateInt.DISCONNECTED)
    open fun setDISCONNECTED(): Boolean = setValue(StateInt.DISCONNECTED)
    open fun postDISCONNECTED(): Boolean = postValue(StateInt.DISCONNECTED)
    open fun smartUpdateDISCONNECTED(): Boolean = smartUpdateValue(StateInt.DISCONNECTED)

    // SUSPENDED
    open fun isSUSPENDEDState(): Boolean = isEqual(StateInt.SUSPENDED)
    open fun setSUSPENDED(): Boolean = setValue(StateInt.SUSPENDED)
    open fun postSUSPENDED(): Boolean = postValue(StateInt.SUSPENDED)
    open fun smartUpdateSUSPENDED(): Boolean = smartUpdateValue(StateInt.SUSPENDED)

    // UNKNOWN
    open fun isUNKNOWNState(): Boolean = isEqual(StateInt.UNKNOWN)
    open fun setUNKNOWN(): Boolean = setValue(StateInt.UNKNOWN)
    open fun postUNKNOWN(): Boolean = postValue(StateInt.UNKNOWN)
    open fun smartUpdateUNKNOWN(): Boolean = smartUpdateValue(StateInt.UNKNOWN)

    // INSERT
    open fun isINSERTState(): Boolean = isEqual(StateInt.INSERT)
    open fun setINSERT(): Boolean = setValue(StateInt.INSERT)
    open fun postINSERT(): Boolean = postValue(StateInt.INSERT)
    open fun smartUpdateINSERT(): Boolean = smartUpdateValue(StateInt.INSERT)

    // DELETE
    open fun isDELETEState(): Boolean = isEqual(StateInt.DELETE)
    open fun setDELETE(): Boolean = setValue(StateInt.DELETE)
    open fun postDELETE(): Boolean = postValue(StateInt.DELETE)
    open fun smartUpdateDELETE(): Boolean = smartUpdateValue(StateInt.DELETE)

    // UPDATE
    open fun isUPDATEState(): Boolean = isEqual(StateInt.UPDATE)
    open fun setUPDATE(): Boolean = setValue(StateInt.UPDATE)
    open fun postUPDATE(): Boolean = postValue(StateInt.UPDATE)
    open fun smartUpdateUPDATE(): Boolean = smartUpdateValue(StateInt.UPDATE)

    // SELECT
    open fun isSELECTState(): Boolean = isEqual(StateInt.SELECT)
    open fun setSELECT(): Boolean = setValue(StateInt.SELECT)
    open fun postSELECT(): Boolean = postValue(StateInt.SELECT)
    open fun smartUpdateSELECT(): Boolean = smartUpdateValue(StateInt.SELECT)

    // ENCRYPT
    open fun isENCRYPTState(): Boolean = isEqual(StateInt.ENCRYPT)
    open fun setENCRYPT(): Boolean = setValue(StateInt.ENCRYPT)
    open fun postENCRYPT(): Boolean = postValue(StateInt.ENCRYPT)
    open fun smartUpdateENCRYPT(): Boolean = smartUpdateValue(StateInt.ENCRYPT)

    // DECRYPT
    open fun isDECRYPTState(): Boolean = isEqual(StateInt.DECRYPT)
    open fun setDECRYPT(): Boolean = setValue(StateInt.DECRYPT)
    open fun postDECRYPT(): Boolean = postValue(StateInt.DECRYPT)
    open fun smartUpdateDECRYPT(): Boolean = smartUpdateValue(StateInt.DECRYPT)

    // RESET
    open fun isRESETState(): Boolean = isEqual(StateInt.RESET)
    open fun setRESET(): Boolean = setValue(StateInt.RESET)
    open fun postRESET(): Boolean = postValue(StateInt.RESET)
    open fun smartUpdateRESET(): Boolean = smartUpdateValue(StateInt.RESET)

    // CLOSE
    open fun isCLOSEState(): Boolean = isEqual(StateInt.CLOSE)
    open fun setCLOSE(): Boolean = setValue(StateInt.CLOSE)
    open fun postCLOSE(): Boolean = postValue(StateInt.CLOSE)
    open fun smartUpdateCLOSE(): Boolean = smartUpdateValue(StateInt.CLOSE)

    // OPEN
    open fun isOPENState(): Boolean = isEqual(StateInt.OPEN)
    open fun setOPEN(): Boolean = setValue(StateInt.OPEN)
    open fun postOPEN(): Boolean = postValue(StateInt.OPEN)
    open fun smartUpdateOPEN(): Boolean = smartUpdateValue(StateInt.OPEN)

    // EXIT
    open fun isEXITState(): Boolean = isEqual(StateInt.EXIT)
    open fun setEXIT(): Boolean = setValue(StateInt.EXIT)
    open fun postEXIT(): Boolean = postValue(StateInt.EXIT)
    open fun smartUpdateEXIT(): Boolean = smartUpdateValue(StateInt.EXIT)

    // NEXT
    open fun isNEXTState(): Boolean = isEqual(StateInt.NEXT)
    open fun setNEXT(): Boolean = setValue(StateInt.NEXT)
    open fun postNEXT(): Boolean = postValue(StateInt.NEXT)
    open fun smartUpdateNEXT(): Boolean = smartUpdateValue(StateInt.NEXT)

    // NONE
    open fun isNONEState(): Boolean = isEqual(StateInt.NONE)
    open fun setNONE(): Boolean = setValue(StateInt.NONE)
    open fun postNONE(): Boolean = postValue(StateInt.NONE)
    open fun smartUpdateNONE(): Boolean = smartUpdateValue(StateInt.NONE)

    // FINISH
    open fun isFINISHState(): Boolean = isEqual(StateInt.FINISH)
    open fun setFINISH(): Boolean = setValue(StateInt.FINISH)
    open fun postFINISH(): Boolean = postValue(StateInt.FINISH)
    open fun smartUpdateFINISH(): Boolean = smartUpdateValue(StateInt.FINISH)

    // WAITING
    open fun isWAITINGState(): Boolean = isEqual(StateInt.WAITING)
    open fun setWAITING(): Boolean = setValue(StateInt.WAITING)
    open fun postWAITING(): Boolean = postValue(StateInt.WAITING)
    open fun smartUpdateWAITING(): Boolean = smartUpdateValue(StateInt.WAITING)

    // COMPLETE
    open fun isCOMPLETEState(): Boolean = isEqual(StateInt.COMPLETE)
    open fun setCOMPLETE(): Boolean = setValue(StateInt.COMPLETE)
    open fun postCOMPLETE(): Boolean = postValue(StateInt.COMPLETE)
    open fun smartUpdateCOMPLETE(): Boolean = smartUpdateValue(StateInt.COMPLETE)
}