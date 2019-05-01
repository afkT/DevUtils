package dev.utils.common.thread;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: 线程池管理类
 * @author Ttt
 */
public final class DevThreadManager {

    private DevThreadManager() {
    }

    // 默认通用线程池 - 通过 CPU 自动处理
    private static final DevThreadPool sDevThreadPool = new DevThreadPool(DevThreadPool.DevThreadPoolType.CALC_CPU);
    // 线程池数据
    private static final LinkedHashMap<String, DevThreadPool> mapThreads = new LinkedHashMap<>();
    // 配置数据
    private static final Map<String, Object> mapConfig = new HashMap<>();

    /**
     * 获取 DevThreadManager 实例
     * @param nThreads 线程数量
     * @return {@link DevThreadPool}
     */
    public static synchronized DevThreadPool getInstance(final int nThreads) {
        // 初始化key
        String key = "n_" + nThreads;
        // 如果不为null, 则直接返回
        DevThreadPool devThreadPool = mapThreads.get(key);
        if (devThreadPool != null) {
            return devThreadPool;
        }
        devThreadPool = new DevThreadPool(nThreads);
        mapThreads.put(key, devThreadPool);
        return devThreadPool;
    }

    /**
     * 获取 DevThreadManager 实例
     * @param key 线程配置 key {@link DevThreadPool.DevThreadPoolType} or int-Integer
     * @return {@link DevThreadPool}
     */
    public static synchronized DevThreadPool getInstance(final String key) {
        // 如果不为null, 则直接返回
        DevThreadPool devThreadPool = mapThreads.get(key);
        if (devThreadPool != null) {
            return devThreadPool;
        }
        Object obj = mapConfig.get(key);
        if (obj != null) {
            try {
                // 判断是否属于线程池类型
                if (obj instanceof DevThreadPool.DevThreadPoolType) {
                    devThreadPool = new DevThreadPool((DevThreadPool.DevThreadPoolType) obj);
                } else if (obj instanceof Integer) {
                    devThreadPool = new DevThreadPool((Integer) obj);
                } else { // 其他类型, 统一转换 Integer
                    devThreadPool = new DevThreadPool(Integer.parseInt((String) obj));
                }
                if (devThreadPool != null) {
                    mapThreads.put(key, devThreadPool);
                    return devThreadPool;
                }
            } catch (Exception e) {
                return sDevThreadPool;
            }
        }
        return sDevThreadPool;
    }

    // =

    /**
     * 初始化配置信息
     * @param mapConfig 线程配置信息 Map
     */
    public static void initConfig(final Map<String, Object> mapConfig) {
        if (mapConfig != null) {
            mapConfig.putAll(mapConfig);
        }
    }

    /**
     * 添加配置信息
     * @param key   线程配置 key
     * @param value 线程配置 value
     */
    public static void putConfig(final String key, final Object value) {
        mapConfig.put(key, value);
    }

    /**
     * 移除配置信息
     * @param key 线程配置 key
     */
    public static void removeConfig(final String key) {
        mapConfig.remove(key);
    }
}
