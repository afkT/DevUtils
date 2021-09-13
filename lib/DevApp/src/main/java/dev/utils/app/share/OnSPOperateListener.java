package dev.utils.app.share;

import java.util.List;
import java.util.Map;

/**
 * detail: SharedPreferences 操作监听器
 * @author Ttt
 * <pre>
 *     慎用 ( 最好只用于监听等观察行为 ), 防止在通知方法中再次操作导致死循环的可能性
 * </pre>
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
     * @param maps       传入集合参数
     */
    void onPutByMap(
            IPreference preference,
            Map<String, Object> maps
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

    /**
     * remove 操作回调 ( 循环 List 触发 )
     * @param preference SharedPreferences 操作接口
     * @param lists      传入集合参数
     */
    void onRemoveByList(
            IPreference preference,
            List<String> lists
    );

    /**
     * 清除全部数据
     */
    void clear();

    /**
     * get 操作回调
     * @param preference   SharedPreferences 操作接口
     * @param dataType     数据类型
     * @param key          操作的 key
     * @param value        获取的 value
     * @param defaultValue 默认值
     */
    void onGet(
            IPreference preference,
            IPreference.DataType dataType,
            String key,
            Object value,
            Object defaultValue
    );
}