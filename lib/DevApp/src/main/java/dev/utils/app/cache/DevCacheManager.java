package dev.utils.app.cache;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * detail: 缓存管理类
 * @author 杨福海 (michael) www.yangfuhai.com
 * @author Ttt
 */
final class DevCacheManager {

    // 总缓存大小
    private final AtomicLong      mCacheSize;
    // 总缓存的文件总数
    private final AtomicInteger   mCacheCount;
    // 大小限制
    private final long            mSizeLimit;
    // 文件总数限制
    private final int             mCountLimit;
    // 保存文件时间信息 ( 文件地址, 文件最后使用时间 )
    private final Map<File, Long> mLastUsageDateMaps = Collections.synchronizedMap(new HashMap<>());
    // 文件目录
    private final File            mCacheDir;

    /**
     * 构造函数
     * @param cacheDir   存储地址
     * @param sizeLimit  文件大小限制
     * @param countLimit 文件总数限制
     */
    protected DevCacheManager(
            final File cacheDir,
            final long sizeLimit,
            final int countLimit
    ) {
        this.mCacheDir = cacheDir;
        this.mSizeLimit = sizeLimit;
        this.mCountLimit = countLimit;
        mCacheSize = new AtomicLong();
        mCacheCount = new AtomicInteger();
        // 计算文件信息等
        calculateCacheSizeAndCacheCount();
    }

    /**
     * 计算 cacheSize 和 cacheCount
     */
    private void calculateCacheSizeAndCacheCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int size = 0, count = 0;
                if (mCacheDir != null) {
                    File[] cachedFiles = mCacheDir.listFiles();
                    if (cachedFiles != null) {
                        for (File cachedFile : cachedFiles) {
                            size += calculateSize(cachedFile);
                            count += 1;
                            mLastUsageDateMaps.put(cachedFile, cachedFile.lastModified());
                        }
                        mCacheSize.set(size);
                        mCacheCount.set(count);
                    }
                }
            }
        }).start();
    }

    /**
     * 添加文件
     * @param file 文件
     */
    protected void put(final File file) {
        if (file == null) return;
        // 获取文件总数
        int curCacheCount = mCacheCount.get();
        // 判断是否超过数量限制
        while (curCacheCount + 1 > mCountLimit) {
            // 删除文件, 并获取删除的文件大小
            long freedSize = removeNext();
            // 删除文件大小
            mCacheSize.addAndGet(-freedSize);
            // 递减文件
            curCacheCount = mCacheCount.addAndGet(-1);
        }
        // 累加文件数量
        mCacheCount.addAndGet(1);
        // 计算文件总大小
        long valueSize = calculateSize(file);
        // 判断当前缓存大小
        long curCacheSize = mCacheSize.get();
        // 判断是否超过大小限制
        while (curCacheSize + valueSize > mSizeLimit) {
            long freedSize = removeNext();
            curCacheSize = mCacheSize.addAndGet(-freedSize);
        }
        mCacheSize.addAndGet(valueSize);

        Long currentTime = System.currentTimeMillis();
        file.setLastModified(currentTime);
        mLastUsageDateMaps.put(file, currentTime);
    }

    /**
     * 获取文件
     * @param key 保存的 key
     * @return {@link File}
     */
    protected File get(final String key) {
        File file = newFile(key);
        if (file != null) {
            Long currentTime = System.currentTimeMillis();
            file.setLastModified(currentTime);
            mLastUsageDateMaps.put(file, currentTime);
        }
        return file;
    }

    /**
     * 创建文件对象
     * @param key 保存的 key
     * @return {@link File}
     */
    protected File newFile(final String key) {
        if (mCacheDir != null && key != null) {
            return new File(mCacheDir, String.valueOf(key.hashCode()));
        }
        return null;
    }

    /**
     * 删除文件
     * @param key 保存的 key
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    protected boolean remove(final String key) {
        File file = get(key);
        if (file != null) {
            return file.delete();
        }
        return false;
    }

    /**
     * 清空全部缓存数据
     */
    protected void clear() {
        mLastUsageDateMaps.clear();
        mCacheSize.set(0);
        File[] files = mCacheDir.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
    }

    /**
     * 移除旧的文件
     * @return 移除的文件大小
     */
    private long removeNext() {
        if (mLastUsageDateMaps.isEmpty()) {
            return 0;
        }
        Long                       oldestUsage      = null;
        File                       mostLongUsedFile = null;
        Set<Map.Entry<File, Long>> entries          = mLastUsageDateMaps.entrySet();
        synchronized (mLastUsageDateMaps) {
            for (Map.Entry<File, Long> entry : entries) {
                if (mostLongUsedFile == null) {
                    mostLongUsedFile = entry.getKey();
                    oldestUsage = entry.getValue();
                } else {
                    Long lastValueUsage = entry.getValue();
                    if (lastValueUsage < oldestUsage) {
                        oldestUsage = lastValueUsage;
                        mostLongUsedFile = entry.getKey();
                    }
                }
            }
        }

        long fileSize = calculateSize(mostLongUsedFile);
        if (mostLongUsedFile.delete()) {
            mLastUsageDateMaps.remove(mostLongUsedFile);
        }
        return fileSize;
    }

    /**
     * 计算文件大小
     * @param file 文件
     * @return 文件大小
     */
    private long calculateSize(final File file) {
        if (file != null) {
            return file.length();
        }
        return 0L;
    }
}