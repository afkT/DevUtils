package com.dev.utils.cache;

import android.content.Context;

import java.io.File;
import java.io.Serializable;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.SDCardUtils;
import dev.utils.app.cache.DevCache;
import dev.utils.app.logger.DevLogger;

/**
 * detail: 缓存使用方法
 * Created by Ttt
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
        final Context mContext = DevUtils.getContext();

        // 初始化
        CacheVo cacheVo = new CacheVo("测试持久化");
        // 打印信息
        LogPrintUtils.dTag(TAG, "保存前: " + cacheVo.toString());
        // 保存数据
        DevCache.get(mContext).put("ctv", cacheVo);
        // 重新获取
        CacheVo ctv = (CacheVo) DevCache.get(mContext).getAsObject("ctv");
        // 打印获取后的数据
        DevLogger.dTag(TAG, "保存后: " + ctv.toString());
        // 设置保存有效时间 -> 5秒
        DevCache.get(mContext).put("ctva", new CacheVo("测试有效时间"), 1);

        // 保存到指定文件夹下
        DevCache.get(new File(SDCardUtils.getSDCardPath(), "Cache")).put("key", "保存数据");

        // 延迟后
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 延迟 1.5 已经过期再去获取
                    Thread.sleep(1500);
                    // 获取数据
                    CacheVo ctva = (CacheVo) DevCache.get(mContext).getAsObject("ctva");
                    // 判断是否过期
                    DevLogger.dTag(TAG, "是否过期: " + (ctva == null));
                } catch (Exception e) {
                }
            }
        }).start();
    }

    /**
     * 缓存实体类
     */
    static class CacheVo implements Serializable {

        String name;

        long time;

        public CacheVo(String name) {
            this.name = name;
            this.time = System.currentTimeMillis();
        }

        public CacheVo(String name, long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public String toString() {
            return "name: " + name + ", time: " + time;
        }
    }
}
