package dev.base.state;

import dev.base.DevState;
import dev.utils.DevFinal;

/**
 * detail: 通用状态类
 * @author Ttt
 */
public class CommonState<T> {

    // State Object
    private final DevState<T> mState = new DevState<>();

    public CommonState() {
        setNormal();
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取操作类型
     * @return 操作类型
     */
    public T getType() {
        return mState.getObject();
    }

    /**
     * 设置操作类型
     * @param type 操作类型
     * @return {@link CommonState}
     */
    public CommonState<T> setType(final T type) {
        mState.setObject(type);
        return this;
    }

    /**
     * 判断操作类型是否一致
     * @param type 待校验操作类型
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsType(final T type) {
        return mState.equalsObject(type);
    }

    // =

    /**
     * 获取操作 UUID
     * @return 操作 UUID
     */
    public long getUUID() {
        return mState.getTokenUUID();
    }

    /**
     * 获取操作 UUID ( 随机生成并赋值 )
     * @return 操作 UUID
     */
    public long randomUUID() {
        return mState.randomTokenUUID();
    }

    /**
     * 判断 UUID 是否一致
     * @param uuid 待校验操作 UUID
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsUUID(final long uuid) {
        return mState.equalsTokenUUID(uuid);
    }

    // =

    /**
     * 获取 State
     * @return State
     */
    public int getState() {
        return mState.getState();
    }

    /**
     * 设置 State
     * @param state State
     * @return {@link CommonState}
     */
    public CommonState<T> setState(final int state) {
        mState.setState(state);
        return this;
    }

    /**
     * 判断 State 是否一致
     * @param state 待校验 State
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsState(final int state) {
        return mState.equalsState(state);
    }

    // ==========
    // = 快捷方法 =
    // ==========

    // =======
    // = get =
    // =======

    /**
     * 判断是否默认状态 ( 暂未进行操作 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNormal() {
        return equalsState(DevFinal.INT.NORMAL);
    }

    /**
     * 判断是否操作中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isIng() {
        return equalsState(DevFinal.INT.ING);
    }

    /**
     * 判断是否操作成功
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSuccess() {
        return equalsState(DevFinal.INT.SUCCESS);
    }

    /**
     * 判断是否操作失败
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFail() {
        return equalsState(DevFinal.INT.FAIL);
    }

    /**
     * 判断是否操作异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isError() {
        return equalsState(DevFinal.INT.ERROR);
    }

    /**
     * 判断是否开始操作
     * @return {@code true} yes, {@code false} no
     */
    public boolean isStart() {
        return equalsState(DevFinal.INT.START);
    }

    /**
     * 判断是否重新开始操作
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRestart() {
        return equalsState(DevFinal.INT.RESTART);
    }

    /**
     * 判断是否操作结束
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEnd() {
        return equalsState(DevFinal.INT.END);
    }

    /**
     * 判断是否操作暂停
     * @return {@code true} yes, {@code false} no
     */
    public boolean isPause() {
        return equalsState(DevFinal.INT.PAUSE);
    }

    /**
     * 判断是否操作恢复 ( 继续 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isResume() {
        return equalsState(DevFinal.INT.RESUME);
    }

    /**
     * 判断是否操作停止
     * @return {@code true} yes, {@code false} no
     */
    public boolean isStop() {
        return equalsState(DevFinal.INT.STOP);
    }

    /**
     * 判断是否操作取消
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCancel() {
        return equalsState(DevFinal.INT.CANCEL);
    }

    /**
     * 判断是否创建
     * @return {@code true} yes, {@code false} no
     */
    public boolean isCreate() {
        return equalsState(DevFinal.INT.CREATE);
    }

    /**
     * 判断是否销毁
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDestroy() {
        return equalsState(DevFinal.INT.DESTROY);
    }

    /**
     * 判断是否回收
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRecycle() {
        return equalsState(DevFinal.INT.RECYCLE);
    }

    /**
     * 判断是否初始化
     * @return {@code true} yes, {@code false} no
     */
    public boolean isInit() {
        return equalsState(DevFinal.INT.INIT);
    }

    /**
     * 判断是否已打开
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEnabled() {
        return equalsState(DevFinal.INT.ENABLED);
    }

    /**
     * 判断是否正在打开
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEnabling() {
        return equalsState(DevFinal.INT.ENABLING);
    }

    /**
     * 判断是否已关闭
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDisabled() {
        return equalsState(DevFinal.INT.DISABLED);
    }

    /**
     * 判断是否正在关闭
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDisabling() {
        return equalsState(DevFinal.INT.DISABLING);
    }

    /**
     * 判断是否连接成功
     * @return {@code true} yes, {@code false} no
     */
    public boolean isConnected() {
        return equalsState(DevFinal.INT.CONNECTED);
    }

    /**
     * 判断是否连接中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isConnecting() {
        return equalsState(DevFinal.INT.CONNECTING);
    }

    /**
     * 判断是否连接失败、断开
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDisconnected() {
        return equalsState(DevFinal.INT.DISCONNECTED);
    }

    /**
     * 判断是否暂停、延迟
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSuspended() {
        return equalsState(DevFinal.INT.SUSPENDED);
    }

    /**
     * 判断是否未知
     * @return {@code true} yes, {@code false} no
     */
    public boolean isUnknown() {
        return equalsState(DevFinal.INT.UNKNOWN);
    }

    /**
     * 判断是否新增
     * @return {@code true} yes, {@code false} no
     */
    public boolean isInsert() {
        return equalsState(DevFinal.INT.INSERT);
    }

    /**
     * 判断是否删除
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDelete() {
        return equalsState(DevFinal.INT.DELETE);
    }

    /**
     * 判断是否更新
     * @return {@code true} yes, {@code false} no
     */
    public boolean isUpdate() {
        return equalsState(DevFinal.INT.UPDATE);
    }

    /**
     * 判断是否查询
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSelect() {
        return equalsState(DevFinal.INT.SELECT);
    }

    /**
     * 判断是否加密
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEncrypt() {
        return equalsState(DevFinal.INT.ENCRYPT);
    }

    /**
     * 判断是否解密
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDecrypt() {
        return equalsState(DevFinal.INT.DECRYPT);
    }

    /**
     * 判断是否重置
     * @return {@code true} yes, {@code false} no
     */
    public boolean isReset() {
        return equalsState(DevFinal.INT.RESET);
    }

    /**
     * 判断是否关闭
     * @return {@code true} yes, {@code false} no
     */
    public boolean isClose() {
        return equalsState(DevFinal.INT.CLOSE);
    }

    /**
     * 判断是否打开
     * @return {@code true} yes, {@code false} no
     */
    public boolean isOpen() {
        return equalsState(DevFinal.INT.OPEN);
    }

    /**
     * 判断是否退出
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExit() {
        return equalsState(DevFinal.INT.EXIT);
    }

    // =======
    // = set =
    // =======

    /**
     * 设置状态为默认状态 ( 暂未进行操作 )
     * @return {@link CommonState}
     */
    public CommonState<T> setNormal() {
        return setState(DevFinal.INT.NORMAL);
    }

    /**
     * 设置状态为操作中
     * @return {@link CommonState}
     */
    public CommonState<T> setIng() {
        return setState(DevFinal.INT.ING);
    }

    /**
     * 设置状态为操作成功
     * @return {@link CommonState}
     */
    public CommonState<T> setSuccess() {
        return setState(DevFinal.INT.SUCCESS);
    }

    /**
     * 设置状态为操作失败
     * @return {@link CommonState}
     */
    public CommonState<T> setFail() {
        return setState(DevFinal.INT.FAIL);
    }

    /**
     * 设置状态为操作异常
     * @return {@link CommonState}
     */
    public CommonState<T> setError() {
        return setState(DevFinal.INT.ERROR);
    }

    /**
     * 设置状态为开始操作
     * @return {@link CommonState}
     */
    public CommonState<T> setStart() {
        return setState(DevFinal.INT.START);
    }

    /**
     * 设置状态为重新开始操作
     * @return {@link CommonState}
     */
    public CommonState<T> setRestart() {
        return setState(DevFinal.INT.RESTART);
    }

    /**
     * 设置状态为操作结束
     * @return {@link CommonState}
     */
    public CommonState<T> setEnd() {
        return setState(DevFinal.INT.END);
    }

    /**
     * 设置状态为操作暂停
     * @return {@link CommonState}
     */
    public CommonState<T> setPause() {
        return setState(DevFinal.INT.PAUSE);
    }

    /**
     * 设置状态为操作恢复 ( 继续 )
     * @return {@link CommonState}
     */
    public CommonState<T> setResume() {
        return setState(DevFinal.INT.RESUME);
    }

    /**
     * 设置状态为操作停止
     * @return {@link CommonState}
     */
    public CommonState<T> setStop() {
        return setState(DevFinal.INT.STOP);
    }

    /**
     * 设置状态为操作取消
     * @return {@link CommonState}
     */
    public CommonState<T> setCancel() {
        return setState(DevFinal.INT.CANCEL);
    }

    /**
     * 设置状态为创建
     * @return {@link CommonState}
     */
    public CommonState<T> setCreate() {
        return setState(DevFinal.INT.CREATE);
    }

    /**
     * 设置状态为销毁
     * @return {@link CommonState}
     */
    public CommonState<T> setDestroy() {
        return setState(DevFinal.INT.DESTROY);
    }

    /**
     * 设置状态为回收
     * @return {@link CommonState}
     */
    public CommonState<T> setRecycle() {
        return setState(DevFinal.INT.RECYCLE);
    }

    /**
     * 设置状态为初始化
     * @return {@link CommonState}
     */
    public CommonState<T> setInit() {
        return setState(DevFinal.INT.INIT);
    }

    /**
     * 设置状态为已打开
     * @return {@link CommonState}
     */
    public CommonState<T> setEnabled() {
        return setState(DevFinal.INT.ENABLED);
    }

    /**
     * 设置状态为正在打开
     * @return {@link CommonState}
     */
    public CommonState<T> setEnabling() {
        return setState(DevFinal.INT.ENABLING);
    }

    /**
     * 设置状态为已关闭
     * @return {@link CommonState}
     */
    public CommonState<T> setDisabled() {
        return setState(DevFinal.INT.DISABLED);
    }

    /**
     * 设置状态为正在关闭
     * @return {@link CommonState}
     */
    public CommonState<T> setDisabling() {
        return setState(DevFinal.INT.DISABLING);
    }

    /**
     * 设置状态为连接成功
     * @return {@link CommonState}
     */
    public CommonState<T> setConnected() {
        return setState(DevFinal.INT.CONNECTED);
    }

    /**
     * 设置状态为连接中
     * @return {@link CommonState}
     */
    public CommonState<T> setConnecting() {
        return setState(DevFinal.INT.CONNECTING);
    }

    /**
     * 设置状态为连接失败、断开
     * @return {@link CommonState}
     */
    public CommonState<T> setDisconnected() {
        return setState(DevFinal.INT.DISCONNECTED);
    }

    /**
     * 设置状态为暂停、延迟
     * @return {@link CommonState}
     */
    public CommonState<T> setSuspended() {
        return setState(DevFinal.INT.SUSPENDED);
    }

    /**
     * 设置状态为未知
     * @return {@link CommonState}
     */
    public CommonState<T> setUnknown() {
        return setState(DevFinal.INT.UNKNOWN);
    }

    /**
     * 设置状态为新增
     * @return {@link CommonState}
     */
    public CommonState<T> setInsert() {
        return setState(DevFinal.INT.INSERT);
    }

    /**
     * 设置状态为删除
     * @return {@link CommonState}
     */
    public CommonState<T> setDelete() {
        return setState(DevFinal.INT.DELETE);
    }

    /**
     * 设置状态为更新
     * @return {@link CommonState}
     */
    public CommonState<T> setUpdate() {
        return setState(DevFinal.INT.UPDATE);
    }

    /**
     * 设置状态为查询
     * @return {@link CommonState}
     */
    public CommonState<T> setSelect() {
        return setState(DevFinal.INT.SELECT);
    }

    /**
     * 设置状态为加密
     * @return {@link CommonState}
     */
    public CommonState<T> setEncrypt() {
        return setState(DevFinal.INT.ENCRYPT);
    }

    /**
     * 设置状态为解密
     * @return {@link CommonState}
     */
    public CommonState<T> setDecrypt() {
        return setState(DevFinal.INT.DECRYPT);
    }

    /**
     * 设置状态为重置
     * @return {@link CommonState}
     */
    public CommonState<T> setReset() {
        return setState(DevFinal.INT.RESET);
    }

    /**
     * 设置状态为关闭
     * @return {@link CommonState}
     */
    public CommonState<T> setClose() {
        return setState(DevFinal.INT.CLOSE);
    }

    /**
     * 设置状态为打开
     * @return {@link CommonState}
     */
    public CommonState<T> setOpen() {
        return setState(DevFinal.INT.OPEN);
    }

    /**
     * 设置状态为退出
     * @return {@link CommonState}
     */
    public CommonState<T> setExit() {
        return setState(DevFinal.INT.EXIT);
    }
}