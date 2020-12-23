package dev.other;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;

/**
 * detail: MMKV 工具类
 * @author Ttt
 * <pre>
 *     支持组件化 module 存储、以及默认通用 mmkv 对象
 *     <p></p>
 *     基于 mmap 的高性能通用 key-value 组件
 *     @see <a href="https://github.com/Tencent/MMKV/blob/master/readme_cn.md"/>
 *     [Google] 再见 SharedPreferences 拥抱 Jetpack DataStore
 *     @see <a href="https://juejin.im/post/6881442312560803853"/>
 *     [Google] 再见 SharedPreferences 拥抱 Jetpack DataStore ( 二 )
 *     @see <a href="https://juejin.im/post/6888847647802097672"/>
 *     <p></p>
 *     {@link MMKVUtils#defaultHolder()}.encode/decodeXxx
 *     {@link MMKVUtils#get(String)}.encode/decodeXxx
 * </pre>
 */
public final class MMKVUtils {

    private MMKVUtils() {
    }

    // 日志 TAG
    private static final String TAG = MMKVUtils.class.getSimpleName();

    // 持有类存储 Key-Holder
    private static final Map<String, Holder> HOLDER_MAP     = new HashMap<>();
    // Default MMKV Holder
    private static       Holder              DEFAULT_HOLDER = null;

    /**
     * 初始化方法 ( 必须调用 )
     * @param context {@link Context}
     */
    public static void init(final Context context) {
        // 初始化 MMKV 并设置日志级别
        String rootDir = MMKV.initialize(context, MMKVLogLevel.LevelNone);
        LogPrintUtils.dTag(TAG, "MMKV rootDir: %s", rootDir);

//        // 设置打印日志级别
//        MMKV.setLogLevel(MMKVLogLevel.LevelNone);

//        https://github.com/Tencent/MMKV/wiki/android_advance_cn
//        // 视项目需求进行注册监听、数据恢复等
//        MMKV.registerHandler();
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 是否存在指定 Key 的 MMKV Holder
     * @param key Key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean containsMMKV(final String key) {
        return HOLDER_MAP.containsKey(key);
    }

    /**
     * 通过 Key 获取 MMKV Holder
     * @param key Key
     * @return {@link Holder}
     */
    public static Holder get(final String key) {
        if (containsMMKV(key)) return HOLDER_MAP.get(key);
        return putHolder(key);
    }

    /**
     * 保存自定义 MMKV Holder
     * @param key Key
     * @return {@link Holder}
     */
    public static Holder putHolder(final String key) {
        return putHolder(key, MMKV.mmkvWithID(key, MMKV.MULTI_PROCESS_MODE));
    }

    /**
     * 保存自定义 MMKV Holder
     * @param key  Key
     * @param mmkv {@link MMKV}
     * @return {@link Holder}
     */
    public static Holder putHolder(
            final String key,
            final MMKV mmkv
    ) {
        Holder holder = new Holder(mmkv);
        HOLDER_MAP.put(key, holder);
        return holder;
    }

    // =

    /**
     * 获取 Default MMKV Holder
     * @return {@link Holder}
     */
    public static Holder defaultHolder() {
        if (DEFAULT_HOLDER == null) {
            MMKV mmkv = MMKV.mmkvWithID(TAG, MMKV.MULTI_PROCESS_MODE);
            DEFAULT_HOLDER = new Holder(mmkv);
        }
        return DEFAULT_HOLDER;
    }

    // =============
    // = 内部封装类 =
    // =============

    /**
     * detail: MMKV 持有类
     * @author Ttt
     * <pre>
     *     提供常用方法, 可根据需求自行添加修改或通过 {@link #getMMKV()} 进行操作
     * </pre>
     */
    public static final class Holder {

        // MMKV
        private final MMKV mmkv;

        public Holder(MMKV mmkv) {
            this.mmkv = mmkv;
        }

        /**
         * 获取 MMKV
         * @return {@link MMKV}
         */
        public MMKV getMMKV() {
            return mmkv;
        }

        /**
         * 获取 MMKV mmap id
         * @return mmap id
         */
        public String mmapID() {
            if (isMMKVEmpty()) return null;
            return mmkv.mmapID();
        }

        // ===========
        // = 其他操作 =
        // ===========

        /**
         * 判断 MMKV 是否为 null
         * @return {@code true} yes, {@code false} no
         */
        public boolean isMMKVEmpty() {
            return mmkv == null;
        }

        /**
         * 判断 MMKV 是否不为 null
         * @return {@code true} yes, {@code false} no
         */
        public boolean isMMKVNotEmpty() {
            return mmkv != null;
        }

        /**
         * 是否存在指定 Key value
         * @param key Key
         * @return {@code true} yes, {@code false} no
         */
        public boolean containsKey(String key) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.containsKey(key);
        }

        /**
         * 通过 key 移除 value
         * @param key Key
         * @return {@code true} success, {@code false} fail
         */
        public boolean removeValueForKey(String key) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            mmkv.removeValueForKey(key);
            return true;
        }

        /**
         * 通过 key 数组移除 value
         * @param keys key 数组
         * @return {@code true} success, {@code false} fail
         */
        public boolean removeValuesForKeys(String[] keys) {
            if (isMMKVEmpty()) return false;
            if (keys == null) return false;
            mmkv.removeValuesForKeys(keys);
            return true;
        }

        /**
         * 同步操作
         * @return {@code true} success, {@code false} fail
         */
        public boolean sync() {
            if (isMMKVEmpty()) return false;
            mmkv.sync();
            return true;
        }

        /**
         * 异步操作
         * @return {@code true} success, {@code false} fail
         */
        public boolean async() {
            if (isMMKVEmpty()) return false;
            mmkv.async();
            return true;
        }

        // =======
        // = 存储 =
        // =======

        public boolean encode(
                String key,
                boolean value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                int value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                long value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                float value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                double value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                String value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                Set<String> value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            if (value == null) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                byte[] value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            if (value == null) return false;
            return mmkv.encode(key, value);
        }

        public boolean encode(
                String key,
                Parcelable value
        ) {
            if (isMMKVEmpty()) return false;
            if (StringUtils.isEmpty(key)) return false;
            if (value == null) return false;
            return mmkv.encode(key, value);
        }

        // =======
        // = 读取 =
        // =======

        public boolean decodeBool(String key) {
            return decodeBool(key, false);
        }

        public boolean decodeBool(
                String key,
                boolean defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeBool(key, defaultValue);
        }

        public int decodeInt(String key) {
            return decodeInt(key, 0);
        }

        public int decodeInt(
                String key,
                int defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeInt(key, defaultValue);
        }

        public long decodeLong(String key) {
            return decodeLong(key, 0L);
        }

        public long decodeLong(
                String key,
                long defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeLong(key, defaultValue);
        }

        public float decodeFloat(String key) {
            return decodeFloat(key, 0.0F);
        }

        public float decodeFloat(
                String key,
                float defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeFloat(key, defaultValue);
        }

        public double decodeDouble(String key) {
            return decodeDouble(key, 0.0D);
        }

        public double decodeDouble(
                String key,
                double defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeDouble(key, defaultValue);
        }

        public String decodeString(String key) {
            return decodeString(key, null);
        }

        public String decodeString(
                String key,
                String defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeString(key, defaultValue);
        }

        public Set<String> decodeStringSet(String key) {
            return decodeStringSet(key, null);
        }

        public Set<String> decodeStringSet(
                String key,
                Set<String> defaultValue
        ) {
            return decodeStringSet(key, defaultValue, HashSet.class);
        }

        public Set<String> decodeStringSet(
                String key,
                Set<String> defaultValue,
                Class<? extends Set> cls
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeStringSet(key, defaultValue, cls);
        }

        public byte[] decodeBytes(String key) {
            return decodeBytes(key, null);
        }

        public byte[] decodeBytes(
                String key,
                byte[] defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeBytes(key, defaultValue);
        }

        public <T extends Parcelable> T decodeParcelable(
                String key,
                Class<T> tClass
        ) {
            return decodeParcelable(key, tClass, null);
        }

        public <T extends Parcelable> T decodeParcelable(
                String key,
                Class<T> tClass,
                T defaultValue
        ) {
            if (isMMKVEmpty()) return defaultValue;
            if (StringUtils.isEmpty(key)) return defaultValue;
            return mmkv.decodeParcelable(key, tClass, defaultValue);
        }
    }
}