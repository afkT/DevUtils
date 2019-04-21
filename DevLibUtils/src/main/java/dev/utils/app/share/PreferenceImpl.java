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
 *      1.apply 没有返回值而 commit 返回 boolean 表明修改是否提交成功
 *      2.apply 是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而 commit 是同步的提交到硬件磁盘
 *      3.apply 方法不会提示任何失败的提示 apply 的效率高一些，如果没有必要确认是否提交成功建议使用 apply
 * </pre>
 */
final class PreferenceImpl implements IPreference {

    // 文件名
    private static final String NAME = "SPConfig";
    // 默认SharedPreferences对象
    private SharedPreferences preferences;

    // ============
    // = 构造函数 =
    // ============

    /**
     * 初始化
     * @param context
     */
    public PreferenceImpl(final Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 初始化
     * @param context
     * @param fileName
     */
    public PreferenceImpl(final Context context, final String fileName) {
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 初始化
     * @param context
     * @param fileName
     * @param mode
     */
    public PreferenceImpl(final Context context, final String fileName, final int mode) {
        preferences = context.getSharedPreferences(fileName, mode);
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 保存数据
     * @param editor
     * @param key
     * @param object
     */
    @SuppressWarnings("unchecked")
    private void put(final SharedPreferences.Editor editor, final String key, final Object object) {
        // key 不为null时再存入，否则不存储
        if (key != null && object != null) {
            if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Set) {
                editor.putStringSet(key, (Set<String>) object);
            } else if (object instanceof String) {
                editor.putString(key, String.valueOf(object));
            }
        }
    }

    /**
     * 根据 Key 和 数据类型 取出数据
     * @param key
     * @param type
     * @return
     */
    private Object getValue(final String key, final DataType type) {
        switch (type) {
            case INTEGER:
                return preferences.getInt(key, -1);
            case FLOAT:
                return preferences.getFloat(key, -1f);
            case BOOLEAN:
                return preferences.getBoolean(key, false);
            case LONG:
                return preferences.getLong(key, -1L);
            case STRING:
                return preferences.getString(key, null);
            case STRING_SET:
                return preferences.getStringSet(key, null);
            default: // 默认取出String类型的数据
                return null;
        }
    }

    /**
     * 默认比较器，当存储List集合中的String类型数据时，没有指定比较器，就使用默认比较器
     */
    class ComparatorImpl implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    // ================
    // = 接口实现方法 =
    // ================

    @Override
    public <T> void put(final String key, final T value) {
        SharedPreferences.Editor edit = preferences.edit();
        put(edit, key, value);
        edit.apply();
    }

    @Override
    public <T> void putAll(final Map<String, T> map) {
        SharedPreferences.Editor edit = preferences.edit();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            put(edit, key, value);
        }
        edit.apply();
    }

    @Override
    public void putAll(final String key, final List<String> list) {
        putAll(key, list, new ComparatorImpl());
    }

    @Override
    public void putAll(final String key, final List<String> list, final Comparator<String> comparator) {
        Set<String> set = new TreeSet<>(comparator);
        for (String value : list) {
            set.add(value);
        }
        preferences.edit().putStringSet(key, set).apply();
    }

    @Override
    public <T> T get(final String key, final DataType type) {
        return (T) getValue(key, type);
    }

    @Override
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    @Override
    public List<String> getAll(final String key) {
        List<String> list = new ArrayList<>();
        Set<String> set = get(key, DataType.STRING_SET);
        for (String value : set) {
            list.add(value);
        }
        return list;
    }

    @Override
    public void remove(final String key) {
        preferences.edit().remove(key).apply();
    }

    @Override
    public void removeAll(final List<String> keys) {
        SharedPreferences.Editor edit = preferences.edit();
        for (String k : keys) {
            edit.remove(k);
        }
        edit.apply();
    }

    @Override
    public void removeAll(final String[] keys) {
        removeAll(Arrays.asList(keys));
    }

    @Override
    public boolean contains(final String key) {
        return preferences.contains(key);
    }

    @Override
    public void clear() {
        preferences.edit().clear().apply();
    }

    // =

    @Override
    public int getInt(final String key) {
        return get(key, DataType.INTEGER);
    }

    @Override
    public float getFloat(final String key) {
        return get(key, DataType.FLOAT);
    }

    @Override
    public long getLong(final String key) {
        return get(key, DataType.LONG);
    }

    @Override
    public boolean getBoolean(final String key) {
        return get(key, DataType.BOOLEAN);
    }

    @Override
    public String getString(final String key) {
        return get(key, DataType.STRING);
    }

    @Override
    public Set<String> getSet(final String key) {
        return get(key, DataType.STRING_SET);
    }
}
