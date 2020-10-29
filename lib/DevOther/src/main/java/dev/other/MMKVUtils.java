package dev.other;

import android.content.Context;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.HashMap;
import java.util.Map;

import dev.utils.app.logger.DevLogger;

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
 * </pre>
 */
public final class MMKVUtils {

    private MMKVUtils() {
    }

    // 日志 TAG
    private static final String TAG = MMKVUtils.class.getSimpleName();

    // 持有类存储 Key-Holder
    private static final Map<String, Holder> HOLDER_MAP              = new HashMap<>();
    // Map Value 为 null 是否返回 Default MMKV Holder
    private static       boolean             IS_EMPTY_RETURN_DEFAULT = true;
    // Default MMKV Holder
    private static       Holder              DEFAULT_HOLDER          = null;

    /**
     * 初始化方法 ( 必须调用 )
     * @param context {@link Context}
     */
    public static void init(final Context context) {
        // 初始化 MMKV 并设置日志级别
        String rootDir = MMKV.initialize(context, MMKVLogLevel.LevelNone);
        DevLogger.dTag(TAG, "MMKV rootDir: " + rootDir);

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
        if (IS_EMPTY_RETURN_DEFAULT) return defaultHolder();
        return null;
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
    public static Holder putHolder(final String key, final MMKV mmkv) {
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

    /**
     * Map Value 为 null 是否返回 Default MMKV Holder
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isReturnDefault() {
        return IS_EMPTY_RETURN_DEFAULT;
    }

    /**
     * 设置 Map Value 为 null 是否返回 Default MMKV Holder
     * @param emptyReturnDefault {@link #defaultHolder()}
     */
    public static void setReturnDefault(final boolean emptyReturnDefault) {
        IS_EMPTY_RETURN_DEFAULT = emptyReturnDefault;
    }

    // =============
    // = 内部封装类 =
    // =============

    /**
     * detail: MMKV 持有类
     * @author Ttt
     * <pre>
     *     只是提供部分方法, 可根据需求自行修改添加
     * </pre>
     */
    public final static class Holder {

        // MMKV
        private final MMKV mmkv;

        public Holder(MMKV mmkv) {
            this.mmkv = mmkv;
        }

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
    }
}