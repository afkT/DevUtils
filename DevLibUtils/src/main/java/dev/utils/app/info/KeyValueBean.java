package dev.utils.app.info;

import android.support.annotation.Keep;
import android.support.annotation.StringRes;

import dev.DevUtils;

/**
 * detail: 键对值 实体类
 * @author Ttt
 */
public class KeyValueBean {

    @Keep // 键 - 提示
    protected String key = "";
    @Keep // 值 - 参数值
    protected String value = "";

    /**
     * 构造函数
     * @param key
     * @param value
     */
    public KeyValueBean(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取 key
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取 value
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }

    /**
     * 通过 resId 设置key, 并且初始化 KeyValueBean
     * @param resId
     * @param value
     * @return
     */
    public static KeyValueBean get(final @StringRes int resId, final String value) {
        return new KeyValueBean(DevUtils.getContext().getString(resId), value);
    }
}
