package dev.utils.app.share;

/**
 * detail: SharedPreferences 操作监听器
 * @author Ttt
 */
public interface OnSPOperateListener {

    /**
     * put 操作回调
     * @param preference SharedPreferences 操作接口
     * @param dataType   数据类型
     * @param key        保存的 key
     * @param value      保存的 value
     */
    void onPut(
            IPreference preference,
            IPreference.DataType dataType,
            String key,
            Object value
    );

    /**
     * put 操作回调 ( 循环 Map 触发 )
     * @param preference SharedPreferences 操作接口
     * @param dataType   数据类型
     * @param key        保存的 key
     * @param value      保存的 value
     */
    void onPutByMap(
            IPreference preference,
            IPreference.DataType dataType,
            String key,
            Object value
    );

    /**
     * remove 操作回调
     * @param preference SharedPreferences 操作接口
     * @param key        操作的 key
     */
    void onRemove(
            IPreference preference,
            String key
    );
}