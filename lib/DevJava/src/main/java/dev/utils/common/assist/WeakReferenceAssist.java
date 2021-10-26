package dev.utils.common.assist;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * detail: 弱引用辅助类
 * @author Ttt
 */
public final class WeakReferenceAssist<T> {

    // 日志 TAG
    private final String TAG = WeakReferenceAssist.class.getSimpleName();

    // 弱引用存储
    private final Map<String, WeakReference<T>> mWeakMaps = new HashMap<>();

    // =============
    // = 对外公开方法 =
    // =============

    // ==========
    // = Single =
    // ==========

    /**
     * 获取单个弱引用对象
     * @return 单个弱引用对象
     */
    public WeakReference<T> getSingleWeak() {
        return getWeak(TAG);
    }

    /**
     * 获取单个弱引用对象值
     * @return 单个弱引用对象值
     */
    public T getSingleWeakValue() {
        return getWeakValue(TAG);
    }

    /**
     * 获取单个弱引用对象值
     * @param defaultValue 默认值
     * @return 单个弱引用对象值
     */
    public T getSingleWeakValue(final T defaultValue) {
        return getWeakValue(TAG, defaultValue);
    }

    // =

    /**
     * 保存单个弱引用对象值
     * @param value 待存储值
     * @return {@code true} success, {@code false} fail
     */
    public boolean setSingleWeakValue(final T value) {
        return setWeakValue(TAG, value);
    }

    /**
     * 移除单个弱引用持有对象
     * @return {@code true} success, {@code false} fail
     */
    public boolean removeSingleWeak() {
        return removeWeak(TAG);
    }

    // ===========
    // = Map Key =
    // ===========

    /**
     * 获取弱引用对象
     * @param key Key
     * @return 弱引用对象
     */
    public WeakReference<T> getWeak(final String key) {
        return mWeakMaps.get(key);
    }

    /**
     * 获取弱引用对象值
     * @param key Key
     * @return 弱引用对象值
     */
    public T getWeakValue(final String key) {
        return getWeakValue(key, null);
    }

    /**
     * 获取弱引用对象值
     * @param key          Key
     * @param defaultValue 默认值
     * @return 弱引用对象值
     */
    public T getWeakValue(
            final String key,
            final T defaultValue
    ) {
        WeakReference<T> weak = mWeakMaps.get(key);
        if (weak == null) return defaultValue;
        T value = weak.get();
        if (value != null) return value;
        return defaultValue;
    }

    // =

    /**
     * 保存弱引用对象值
     * @param key   Key
     * @param value 待存储值
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWeakValue(
            final String key,
            final T value
    ) {
        if (key == null || value == null) return false;
        mWeakMaps.put(key, new WeakReference<>(value));
        return true;
    }

    /**
     * 移除指定弱引用持有对象
     * @param key Key
     * @return {@code true} success, {@code false} fail
     */
    public boolean removeWeak(final String key) {
        if (key == null) return false;
        WeakReference<T> weak = mWeakMaps.remove(key);
        if (weak == null) return false;
        weak.clear();
        return true;
    }

    /**
     * 清空全部弱引用持有对象
     */
    public void clear() {
        List<WeakReference<T>> lists = new ArrayList<>(mWeakMaps.values());
        mWeakMaps.clear();
        for (WeakReference<T> weak : lists) {
            if (weak != null) weak.clear();
        }
        lists.clear();
    }
}