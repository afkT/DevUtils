package dev.utils.app.info;

import androidx.annotation.Keep;
import androidx.annotation.StringRes;

import java.io.Serializable;

import dev.utils.app.ResourceUtils;

/**
 * detail: 键对值实体类
 * @author Ttt
 */
public class KeyValue
        implements Serializable {

    @Keep
    protected String key;
    @Keep
    protected String value;

    /**
     * 构造函数
     * @param key   key
     * @param value value
     */
    public KeyValue(
            final String key,
            final String value
    ) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取 key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取 value
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }

    /**
     * 通过 resId 设置 key
     * @param resId R.string.id
     * @param value value
     * @return {@link KeyValue}
     */
    public static KeyValue get(
            @StringRes final int resId,
            final String value
    ) {
        return new KeyValue(ResourceUtils.getString(resId), value);
    }
}