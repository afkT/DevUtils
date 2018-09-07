package dev.utils.app.info;

import android.support.annotation.StringRes;

import dev.DevUtils;

/**
 * detail: 键对值 实体类
 * Created by Ttt
 */
public class KeyValueBean {

    // 键 - 提示
    protected String key = "";
    // 值 - 参数值
    protected String value = "";

    /**
     * 构造函数
     * @param key
     * @param value
     */
    public KeyValueBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }

    public static KeyValueBean get(@StringRes int resId, String value) {
        return new KeyValueBean(DevUtils.getContext().getString(resId), value);
    }
}
