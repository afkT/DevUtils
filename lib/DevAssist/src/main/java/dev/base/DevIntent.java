package dev.base;

import android.content.Intent;
import android.os.Bundle;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.utils.common.StringUtils;

/**
 * detail: Intent 传参读写辅助类
 * @author Ttt
 * <pre>
 *     统一存储为 String 需要转换其他类型则可通过
 *     {@link dev.utils.common.ConvertUtils} 进行转换
 *     或者自行通过 JSON 映射实体类等
 *     <p></p>
 *     可存储 key、value 为 null 数据 ( 提供方法清除 null 数据 )
 *     <p></p>
 *     自动读取 Intent、Bundle 数据进行填充
 *     仅支持 String、Integer、Long、Double、Float、Boolean 类型
 *     并自动存储为 String
 *     <p></p>
 *     通过 {@link #insert()} 可将 Map 数据插入到 Intent、Bundle 中
 * </pre>
 */
public final class DevIntent {

    // 存储数据 Map
    private final LinkedHashMap<String, String> mDataMaps = new LinkedHashMap<>();

    private DevIntent() {
    }

    // ==========
    // = 静态方法 =
    // ==========

    /**
     * 创建 DevIntent
     * @return {@link DevIntent}
     */
    public static DevIntent with() {
        return new DevIntent();
    }

    /**
     * 创建 DevIntent
     * @param intent {@link Intent}
     * @return {@link DevIntent}
     */
    public static DevIntent with(final Intent intent) {
        return new DevIntent().reader(intent);
    }

    /**
     * 创建 DevIntent
     * @param bundle {@link Bundle}
     * @return {@link DevIntent}
     */
    public static DevIntent with(final Bundle bundle) {
        return new DevIntent().reader(bundle);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 插入数据
     * @param intent {@link Intent}
     * @return {@link Intent}
     */
    public Intent insert(final Intent intent) {
        if (intent != null) {
            for (Map.Entry<String, String> entry : mDataMaps.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        return intent;
    }

    /**
     * 插入数据
     * @return {@link Bundle}
     */
    public Bundle insert() {
        return insert(new Bundle());
    }

    /**
     * 插入数据
     * @param bundle {@link Bundle}
     * @return {@link Bundle}
     */
    public Bundle insert(final Bundle bundle) {
        if (bundle != null) {
            for (Map.Entry<String, String> entry : mDataMaps.entrySet()) {
                bundle.putString(entry.getKey(), entry.getValue());
            }
        }
        return bundle;
    }

    // =

    /**
     * 读取数据并存储
     * @param intent {@link Intent}
     * @return {@link DevIntent}
     */
    public DevIntent reader(final Intent intent) {
        if (intent != null) {
            return reader(intent.getExtras());
        }
        return this;
    }

    /**
     * 读取数据并存储
     * @param bundle {@link Bundle}
     * @return {@link DevIntent}
     */
    public DevIntent reader(final Bundle bundle) {
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                if (value == null) {
                    continue;
                }
                if (value instanceof String) {
                    put(key, String.valueOf(value));
                } else if (value instanceof Integer) {
                    put(key, String.valueOf(value));
                } else if (value instanceof Long) {
                    put(key, String.valueOf(value));
                } else if (value instanceof Double) {
                    put(key, String.valueOf(value));
                } else if (value instanceof Float) {
                    put(key, String.valueOf(value));
                } else if (value instanceof Boolean) {
                    put(key, String.valueOf(value));
                }
            }
        }
        return this;
    }

    // =======
    // = 通用 =
    // =======

    /**
     * 获取存储数据 Map
     * @return 存储数据 Map
     */
    public Map<String, String> getDataMaps() {
        return mDataMaps;
    }

    /**
     * 是否存在 Key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public boolean containsKey(final String key) {
        return mDataMaps.containsKey(key);
    }

    /**
     * 是否存在 Value
     * @param value 保存的 value
     * @return {@code true} yes, {@code false} no
     */
    public boolean containsValue(final String value) {
        return mDataMaps.containsValue(value);
    }

    /**
     * 对应 Key 保存的 Value 是否为 null
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNullValue(final String key) {
        return get(key) == null;
    }

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @return {@link DevIntent}
     */
    public DevIntent put(
            final String key,
            final String value
    ) {
        mDataMaps.put(key, value);
        return this;
    }

    /**
     * 保存集合数据
     * @param map {@link Map}
     * @return {@link DevIntent}
     */
    public DevIntent putAll(final Map<String, String> map) {
        if (map != null) mDataMaps.putAll(map);
        return this;
    }

    /**
     * 移除数据
     * @param key 保存的 key
     * @return {@link DevIntent}
     */
    public DevIntent remove(final String key) {
        mDataMaps.remove(key);
        return this;
    }

    /**
     * 移除集合数据
     * @param keys 保存的 key 集合
     * @return {@link DevIntent}
     */
    public DevIntent removeAll(final List<String> keys) {
        if (keys != null) {
            for (String key : keys) {
                mDataMaps.remove(key);
            }
        }
        return this;
    }

    /**
     * 获取对应 Key 保存的 Value
     * @param key 保存的 key
     * @return 保存的 value
     */
    public String get(final String key) {
        return mDataMaps.get(key);
    }

    /**
     * 清空数据
     * @return {@link DevIntent}
     */
    public DevIntent clear() {
        mDataMaps.clear();
        return this;
    }

    // =

    /**
     * 清除 null 数据
     * <pre>
     *     key、value 只要其中一个为 null 就清除
     * </pre>
     * @return {@link DevIntent}
     */
    public DevIntent clearNull() {
        return clearNullKey().clearNullValue();
    }

    /**
     * 清除 null Key 数据
     * @return {@link DevIntent}
     */
    public DevIntent clearNullKey() {
        return remove(null);
    }

    /**
     * 清除 null Value 数据
     * <pre>
     *     value 只要为 null 就清除
     * </pre>
     * @return {@link DevIntent}
     */
    public DevIntent clearNullValue() {
        Iterator<Map.Entry<String, String>> iterator = mDataMaps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getValue() == null) {
                iterator.remove();
            }
        }
        return this;
    }

    // =

    /**
     * 清除 empty 数据
     * <pre>
     *     key、value 只要其中一个为 empty ( null、"" ) 就清除
     * </pre>
     * @return {@link DevIntent}
     */
    public DevIntent clearEmpty() {
        return clearEmptyKey().clearEmptyValue();
    }

    /**
     * 清除 empty Key 数据
     * @return {@link DevIntent}
     */
    public DevIntent clearEmptyKey() {
        return remove(null).remove("");
    }

    /**
     * 清除 empty Value 数据
     * <pre>
     *     value 只要为 empty ( null、"" ) 就清除
     * </pre>
     * @return {@link DevIntent}
     */
    public DevIntent clearEmptyValue() {
        Iterator<Map.Entry<String, String>> iterator = mDataMaps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (StringUtils.isEmpty(entry.getValue())) {
                iterator.remove();
            }
        }
        return this;
    }
}