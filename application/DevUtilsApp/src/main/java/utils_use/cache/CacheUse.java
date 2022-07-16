package utils_use.cache;

import static dev.kotlin.engine.log.LogKt.log_dTag;

import java.io.File;
import java.io.Serializable;

import dev.utils.app.PathUtils;
import dev.utils.app.cache.DevCache;

/**
 * detail: 缓存使用方法
 * @author Ttt
 */
public final class CacheUse {

    private CacheUse() {
    }

    // 日志 TAG
    private static final String TAG = CacheUse.class.getSimpleName();

    /**
     * 缓存使用方法
     */
    private void cacheUse() {
        // 初始化
        CacheVo cacheVo = new CacheVo("测试持久化");
        // 打印信息
        log_dTag(TAG, null, "保存前: %s", cacheVo.toString());
        // 保存数据
        DevCache.newCache().put("ctv", cacheVo, -1);
        // 重新获取
        CacheVo ctv = (CacheVo) DevCache.newCache().getSerializable("ctv");
        // 打印获取后的数据
        log_dTag(TAG, null, "保存后: %s", ctv.toString());
        // 设置保存有效时间 5秒
        DevCache.newCache().put("ctva", new CacheVo("测试有效时间"), 1);

        // 保存到指定文件夹下
        DevCache.newCache(
                new File(PathUtils.getSDCard().getSDCardPath(), "Cache").getAbsolutePath()
        ).put("key", "保存数据", -1);

        // 延迟后
        new Thread(() -> {
            try {
                // 延迟 1.5 已经过期再去获取
                Thread.sleep(1500);
                // 获取数据
                CacheVo ctva = (CacheVo) DevCache.newCache().getSerializable("ctva");
                // 判断是否过期
                log_dTag(TAG, null, "是否过期: %s", (ctva == null));
            } catch (Exception ignored) {
            }
        }).start();
    }

    /**
     * detail: 缓存实体类
     * @author Ttt
     */
    static class CacheVo
            implements Serializable {

        String name;

        long time;

        public CacheVo(String name) {
            this.name = name;
            this.time = System.currentTimeMillis();
        }

        public CacheVo(
                String name,
                long time
        ) {
            this.name = name;
            this.time = time;
        }

        @Override
        public String toString() {
            return "name: " + name + ", time: " + time;
        }
    }
}