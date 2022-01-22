package dev.base;

/**
 * detail: 变量操作基类扩展类
 * @author Ttt
 * <pre>
 *     {@link DevVariable} 变量操作基类基础上进行扩展
 *     支持通过接口方式进行创建存储值
 * </pre>
 */
public class DevVariableExt<K, V, P> {

    // 变量操作基类
    private final DevVariable<K, V> mVariable = new DevVariable<>();
    // 变量创建器
    private       Creator<K, V, P>  mCreator  = null;

    // ========
    // = 创建器 =
    // ========

    /**
     * detail: 变量创建器
     * @author Ttt
     */
    public interface Creator<K, V, P> {

        /**
         * 创建存储值
         * @param key   存储 key
         * @param param 额外参数
         * @return 存储值
         */
        V create(
                K key,
                P param
        );
    }

    /**
     * 获取变量创建器
     * @return {@link Creator}
     */
    public Creator<K, V, P> getCreator() {
        return mCreator;
    }

    /**
     * 设置变量创建器
     * @param creator {@link Creator}
     * @return {@link DevVariableExt}
     */
    public DevVariableExt<K, V, P> setCreator(final Creator<K, V, P> creator) {
        this.mCreator = creator;
        return this;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取变量操作基类
     * @return {@link DevVariable}
     */
    public DevVariable<K, V> getVariable() {
        return mVariable;
    }

    /**
     * 通过 key 获取 value
     * @param key Key
     * @return Value
     */
    public V getVariableValue(final K key) {
        return getVariableValue(key, null);
    }

    /**
     * 通过 key 获取 value
     * @param key   Key
     * @param param 额外参数
     * @return Value
     */
    public V getVariableValue(
            final K key,
            final P param
    ) {
        V value = mVariable.getVariableValue(key);
        if (value != null) return value;
        if (mCreator != null) {
            value = mCreator.create(key, param);
            if (value != null) {
                mVariable.putVariable(key, value);
            }
        }
        return value;
    }
}