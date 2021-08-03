package dev.utils.app.share;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * detail: SharedPreferences 操作接口具体实现类
 * @author Ttt
 * <pre>
 *     1.apply 没有返回值而 commit 返回 boolean 表明修改是否提交成功
 *     2.apply 是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而 commit 是同步的提交到硬件磁盘
 *     3.apply 方法不会提示任何失败的提示 apply 的效率高一些, 如果没有必要确认是否提交成功建议使用 apply
 * </pre>
 */
final class PreferenceImpl
        implements IPreference {

    // 文件名
    private static final String            NAME               = "SPConfig";
    // SharedPreferences 对象
    private final        SharedPreferences mPreferences;
    // 默认值
    private final        int               INTEGER_DEFAULT    = -1;
    private final        long              LONG_DEFAULT       = -1L;
    private final        float             FLOAT_DEFAULT      = -1F;
    private final        double            DOUBLE_DEFAULT     = -1D;
    private final        boolean           BOOLEAN_DEFAULT    = false;
    private final        String            STRING_DEFAULT     = null;
    private final        Set<String>       STRING_SET_DEFAULT = null;

    // ==========
    // = 构造函数 =
    // ==========

    /**
     * 构造函数
     * @param context {@link Context}
     */
    public PreferenceImpl(final Context context) {
        mPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 构造函数
     * @param context  {@link Context}
     * @param fileName 文件名
     */
    public PreferenceImpl(
            final Context context,
            final String fileName
    ) {
        mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 构造函数
     * @param context  {@link Context}
     * @param fileName 文件名
     * @param mode     SharedPreferences 操作模式
     */
    public PreferenceImpl(
            final Context context,
            final String fileName,
            final int mode
    ) {
        mPreferences = context.getSharedPreferences(fileName, mode);
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 保存数据
     * @param editor {@link SharedPreferences.Editor}
     * @param key    保存的 key
     * @param object 保存的 value
     */
    private void put(
            final SharedPreferences.Editor editor,
            final String key,
            final Object object
    ) {
        // key 不为 null 时再存入, 否则不存储
        if (key != null && object != null) {
            if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Double) {
                editor.putLong(key, Double.doubleToRawLongBits((Double) object));
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof String) {
                editor.putString(key, String.valueOf(object));
            } else if (object instanceof Set) {
                try {
                    editor.putStringSet(key, (Set<String>) object);
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * 根据 key 和 数据类型 取出数据
     * @param key          保存的 key
     * @param type         数据类型 {@link DataType}
     * @param defaultValue 默认值
     * @return 指定 key 存储的数据 ( 传入的 type 类型 )
     */
    private Object getValue(
            final String key,
            final DataType type,
            final Object defaultValue
    ) {
        switch (type) {
            case INTEGER:
                int intValue = INTEGER_DEFAULT;
                if (defaultValue instanceof Integer) intValue = (Integer) defaultValue;
                return mPreferences.getInt(key, intValue);
            case LONG:
                long longValue = LONG_DEFAULT;
                if (defaultValue instanceof Long) longValue = (Long) defaultValue;
                return mPreferences.getLong(key, longValue);
            case FLOAT:
                float floatValue = FLOAT_DEFAULT;
                if (defaultValue instanceof Float) floatValue = (Float) defaultValue;
                return mPreferences.getFloat(key, floatValue);
            case DOUBLE:
                double doubleValue = DOUBLE_DEFAULT;
                if (defaultValue instanceof Double) doubleValue = (Double) defaultValue;
                long value = mPreferences.getLong(key, Double.doubleToLongBits(doubleValue));
                return Double.longBitsToDouble(value);
            case BOOLEAN:
                boolean booleanValue = BOOLEAN_DEFAULT;
                if (defaultValue instanceof Boolean) booleanValue = (Boolean) defaultValue;
                return mPreferences.getBoolean(key, booleanValue);
            case STRING:
                String stringValue = STRING_DEFAULT;
                if (defaultValue instanceof String) stringValue = (String) defaultValue;
                return mPreferences.getString(key, stringValue);
            case STRING_SET:
                Set<String> stringSetValue = STRING_SET_DEFAULT;
                try {
                    if (defaultValue instanceof Set) stringSetValue = (Set<String>) defaultValue;
                } catch (Exception ignored) {
                }
                return mPreferences.getStringSet(key, stringSetValue);
            default:
                return null;
        }
    }

    /**
     * detail: 默认比较器, 当存储 List 集合中的 String 类型数据时, 没有指定比较器, 就使用默认比较器
     * @author Ttt
     */
    static class ComparatorImpl
            implements Comparator<String> {
        @Override
        public int compare(
                String lhs,
                String rhs
        ) {
            return lhs.compareTo(rhs);
        }
    }

    // =============
    // = 接口实现方法 =
    // =============

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @param <T>   泛型
     */
    @Override
    public <T> void put(
            final String key,
            final T value
    ) {
        SharedPreferences.Editor edit = mPreferences.edit();
        put(edit, key, value);
        edit.apply();
    }

    /**
     * 保存 Map 集合 ( 只能是 Integer、Long、Boolean、Float、String、Set)
     * @param map {@link Map}
     * @param <T> 泛型
     */
    @Override
    public <T> void putAll(final Map<String, T> map) {
        SharedPreferences.Editor edit = mPreferences.edit();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key   = entry.getKey();
            Object value = entry.getValue();
            put(edit, key, value);
        }
        edit.apply();
    }

    /**
     * 保存 List 集合
     * @param key  保存的 key
     * @param list 保存的 value
     */
    @Override
    public void putAll(
            final String key,
            final List<String> list
    ) {
        putAll(key, list, new ComparatorImpl());
    }

    /**
     * 保存 List 集合, 并且自定义保存顺序
     * @param key        保存的 key
     * @param list       保存的 value
     * @param comparator 排序 {@link Comparator}
     */
    @Override
    public void putAll(
            final String key,
            final List<String> list,
            final Comparator<String> comparator
    ) {
        Set<String> set = new TreeSet<>(comparator);
        set.addAll(list);
        mPreferences.edit().putStringSet(key, set).apply();
    }

    /**
     * 根据 key 获取数据
     * @param key          保存的 key
     * @param type         数据类型
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return 存储的数据
     */
    @Override
    public <T> T get(
            final String key,
            final DataType type,
            final Object defaultValue
    ) {
        return (T) getValue(key, type, defaultValue);
    }

    /**
     * 获取全部数据
     * @return 存储的数据
     */
    @Override
    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    /**
     * 获取 List 集合
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public List<String> getAll(final String key) {
        List<String> list = new ArrayList<>();
        Set<String>  set  = getSet(key);
        if (set != null) list.addAll(set);
        return list;
    }

    /**
     * 移除数据
     * @param key 保存的 key
     */
    @Override
    public void remove(final String key) {
        mPreferences.edit().remove(key).apply();
    }

    /**
     * 移除集合的数据
     * @param keys 保存的 key 集合
     */
    @Override
    public void removeAll(final List<String> keys) {
        SharedPreferences.Editor edit = mPreferences.edit();
        for (String key : keys) {
            edit.remove(key);
        }
        edit.apply();
    }

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    @Override
    public void removeAll(final String[] keys) {
        removeAll(Arrays.asList(keys));
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean contains(final String key) {
        return mPreferences.contains(key);
    }

    /**
     * 清除全部数据
     */
    @Override
    public void clear() {
        mPreferences.edit().clear().apply();
    }

    // =

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public int getInt(final String key) {
        return getInt(key, INTEGER_DEFAULT);
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public long getLong(final String key) {
        return getLong(key, LONG_DEFAULT);
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public float getFloat(final String key) {
        return getFloat(key, FLOAT_DEFAULT);
    }

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public double getDouble(final String key) {
        return getDouble(key, DOUBLE_DEFAULT);
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public boolean getBoolean(final String key) {
        return getBoolean(key, BOOLEAN_DEFAULT);
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public String getString(final String key) {
        return getString(key, STRING_DEFAULT);
    }

    /**
     * 获取 Set 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    @Override
    public Set<String> getSet(final String key) {
        return getSet(key, STRING_SET_DEFAULT);
    }

    // =

    /**
     * 获取 int 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public int getInt(
            final String key,
            final int defaultValue
    ) {
        return get(key, DataType.INTEGER, defaultValue);
    }

    /**
     * 获取 long 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public long getLong(
            final String key,
            final long defaultValue
    ) {
        return get(key, DataType.LONG, defaultValue);
    }

    /**
     * 获取 float 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public float getFloat(
            final String key,
            final float defaultValue
    ) {
        return get(key, DataType.FLOAT, defaultValue);
    }

    /**
     * 获取 double 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public double getDouble(
            final String key,
            final double defaultValue
    ) {
        return get(key, DataType.DOUBLE, defaultValue);
    }

    /**
     * 获取 boolean 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public boolean getBoolean(
            final String key,
            final boolean defaultValue
    ) {
        return get(key, DataType.BOOLEAN, defaultValue);
    }

    /**
     * 获取 String 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public String getString(
            final String key,
            final String defaultValue
    ) {
        return get(key, DataType.STRING, defaultValue);
    }

    /**
     * 获取 Set 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    @Override
    public Set<String> getSet(
            final String key,
            final Set<String> defaultValue
    ) {
        return get(key, DataType.STRING_SET, defaultValue);
    }
}