package dev.utils.common.thread;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * detail: 线程池管理类
 * Created by Ttt
 */
public final class DevThreadManager {

    private DevThreadManager() {
    }

    // 默认通用线程池 = 通过 CPU 自动处理
    private static final DevThreadPool sDevThreadPool = new DevThreadPool(DevThreadPool.DevThreadPoolType.CALC_CPU);
    // 线程池数据
    private static final LinkedHashMap<String, DevThreadPool> mapThreads = new LinkedHashMap<>();
    // 配置数据
    private static final Map<String, Object> mapConfig = new HashMap<>();

    /**
     * 获取 DevThreadManager 实例
     * @param nThreads
     * @return
     */
    public static synchronized DevThreadPool getInstance(int nThreads) {
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
     * @param key
     * @return
     */
    public static synchronized DevThreadPool getInstance(String key) {
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

    // ===

    /**
     * 初始化配置信息
     * @param mapConfig
     */
    public static void initConfig(Map<String, Object> mapConfig) {
        if (mapConfig != null) {
            mapConfig.putAll(mapConfig);
        }
    }

    /**
     * 添加配置信息
     * @param key
     * @param val
     */
    public static void putConfig(String key, Object val) {
        mapConfig.put(key, val);
    }

    /**
     * 移除配置信息
     * @param key
     */
    public static void removeConfig(String key) {
        mapConfig.remove(key);
    }
}
