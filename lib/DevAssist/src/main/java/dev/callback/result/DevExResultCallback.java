package dev.callback.result;

/**
 * detail: 通用结果回调类 ( 针对 DevResultCallback 进行扩展 )
 * @author Ttt
 * <pre>
 *     针对 DevResultCallback 进行扩展, 实现多个地方共用一个回调, 并且区分各个结果回调等
 * </pre>
 */
public abstract class DevExResultCallback<T> extends DevResultCallback<T> {

    public DevExResultCallback() {
        super();
    }

    public DevExResultCallback(T value) {
        super(value);
    }

    public DevExResultCallback(T value, Object object) {
        super(value, object);
    }

    public DevExResultCallback(T value, Object object, String tag) {
        super(value, object, tag);
    }

    // =====================
    // = DevResultCallback =
    // =====================

    /**
     * 结果回调通知
     * @param str   返回数据
     * @param msg   返回信息
     * @param value 返回值
     */
    @Override
    public void onResult(String str, String msg, T value) {
    }

    // ============
    // = 扩展回调 =
    // ============

    /**
     * 结果回调通知
     * @param type  类型
     * @param str   返回数据
     * @param msg   返回信息
     * @param value 返回值
     */
    public void onResult(int type, String str, String msg, T value) {
    }

    /**
     * 异常回调通知
     * @param type 类型
     * @param e    异常信息
     * @param <E>  泛型
     */
    public <E extends Throwable> void onError(int type, E e) {
    }

    /**
     * 失败回调通知
     * @param type      类型
     * @param fail      失败信息
     * @param errorCode 错误 code
     */
    public void onFailure(int type, String fail, String errorCode) {
    }

    // ============
    // = 扩展处理 =
    // ============

    /**
     * detail: 数据业务抽象类
     * @author Ttt
     * <pre>
     *     处理数据业务类, 主要处理结果中参数的传递处理保存等
     * </pre>
     */
    public static abstract class ExpandResult {
    }

    // =

    // 数据业务对象
    private ExpandResult mExpandResult;

    /**
     * 获取实体类
     * @return {@link ExpandResult}
     */
    public final ExpandResult getExpandResult() {
        return mExpandResult;
    }

    /**
     * 设置实体类
     * @param expandResult 数据业务抽象类
     * @return {@link DevExResultCallback}
     */
    public final DevExResultCallback<T> setExpandResult(final ExpandResult expandResult) {
        this.mExpandResult = expandResult;
        return this;
    }
}
