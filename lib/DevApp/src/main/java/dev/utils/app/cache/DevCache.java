package dev.utils.app.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 缓存工具类
 * @author 杨福海 (michael) www.yangfuhai.com
 * @author Ttt ( 重写、规范注释、逻辑判断等 )
 */
public final class DevCache {

    private DevCache() {
    }

    // 日志 TAG
    private static final String TAG = DevCache.class.getSimpleName();
    // 缓存文件名
    private static final String DEF_FILE_NAME = DevCache.class.getSimpleName();
    // 过期小时 ( 单位秒 ) = 1 小时
    public static final int TIME_HOUR = 60 * 60;
    // 一天 24 小时
    public static final int TIME_DAY = TIME_HOUR * 24;
    // 缓存最大值 50 MB
    private static final int MAX_SIZE = 1000 * 1000 * 50;
    // 不限制存放数据的数量
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    // 不同地址配置缓存对象
    private static Map<String, DevCache> sInstanceMaps = new HashMap<>();
    // 缓存管理类
    private DevCacheManager mCache;
    // 缓存地址
    private static File sContextCacheDir = null;

    /**
     * 内部处理防止 Context 为 null 崩溃问题
     * @param context {@link Context}
     * @return {@link Context}
     */
    private static Context getContext(final Context context) {
        if (context != null) {
            return context;
        } else {
            // 设置全局 Context
            return DevUtils.getContext();
        }
    }

    /**
     * 获取缓存地址
     * @param context {@link Context}
     * @return 应用缓存地址
     */
    public static File getCacheDir(final Context context) {
        if (sContextCacheDir == null) {
            sContextCacheDir = getContext(context).getCacheDir();
        }
        return sContextCacheDir;
    }

    /**
     * 默认缓存地址
     * @param context {@link Context}
     * @return {@link DevCache}
     */
    public static DevCache get(final Context context) {
        return get(context, DEF_FILE_NAME);
    }

    /**
     * 获取缓存地址
     * @param context   {@link Context}
     * @param cacheName 缓存文件名
     * @return {@link DevCache}
     */
    public static DevCache get(final Context context, final String cacheName) {
        if (cacheName == null) return null;
        // 进行处理
        File file = new File(getCacheDir(context), cacheName);
        // 获取默认地址
        return get(file, MAX_SIZE, MAX_COUNT);
    }

    /**
     * 设置自定义缓存地址
     * @param cacheDir 缓存文件地址
     * @return {@link DevCache}
     */
    public static DevCache get(final File cacheDir) {
        return get(cacheDir, MAX_SIZE, MAX_COUNT);
    }

    /**
     * 自定义缓存大小
     * @param context  {@link Context}
     * @param maxSize  文件最大大小
     * @param maxCount 最大存储数量
     * @return {@link DevCache}
     */
    public static DevCache get(final Context context, final long maxSize, final int maxCount) {
        File file = new File(getCacheDir(context), DEF_FILE_NAME);
        return get(file, maxSize, maxCount);
    }

    /**
     * 自定义缓存地址、大小等
     * @param cacheDir 缓存文件地址
     * @param maxSize  文件最大大小
     * @param maxCount 最大存储数量
     * @return {@link DevCache}
     */
    public static DevCache get(final File cacheDir, final long maxSize, final int maxCount) {
        if (cacheDir == null) return null;
        // 判断是否存在缓存信息
        DevCache manager = sInstanceMaps.get(cacheDir.getAbsoluteFile() + myPid());
        if (manager == null) {
            // 初始化新的缓存信息, 并且保存
            manager = new DevCache(cacheDir, maxSize, maxCount);
            sInstanceMaps.put(cacheDir.getAbsolutePath() + myPid(), manager);
        }
        return manager;
    }

    /**
     * 获取进程 id - android.os.Process.myPid()
     * @return 进程 id
     */
    private static String myPid() {
        return "_" + android.os.Process.myPid();
    }

    /**
     * 最终初始化方法
     * @param cacheDir 缓存文件地址
     * @param maxSize  文件最大大小
     * @param maxCount 最大存储数量
     * @return {@link DevCache} 缓存工具类对象
     */
    private DevCache(final File cacheDir, final long maxSize, final int maxCount) {
        Exception e = null;
        if (cacheDir == null) {
            e = new Exception("cacheDir is null");
        } else if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            e = new Exception("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        if (e != null) {
            LogPrintUtils.eTag(TAG, e, "private DevCache()");
        }
        mCache = new DevCacheManager(cacheDir, maxSize, maxCount);
    }

    /**
     * Provides a means to save a cached file before the data are available.
     * Since writing about the file is complete, and its close method is called,
     * its contents will be registered in the cache
     */
    class xFileOutputStream extends FileOutputStream {

        private File file;

        public xFileOutputStream(File file) throws FileNotFoundException {
            super(file);
            this.file = file;
        }

        public void close() throws IOException {
            super.close();
            mCache.put(file);
        }
    }

    // ====================
    // = string 数据 读写 =
    // ====================

    /**
     * 保存 String 数据到缓存中
     * @param key   保存的 key
     * @param value 保存的 String 数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final String value) {
        File file = mCache.newFile(key);
        if (file == null || value == null) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file), 1024);
            bw.write(value);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put");
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                } catch (Exception e) {
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception e) {
                }
            }
            mCache.put(file);
        }
        return false;
    }

    /**
     * 保存 String 数据到缓存中
     * @param key      保存的 key
     * @param value    保存的 String 数据
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final String value, final int saveTime) {
        if (key != null && value != null) {
            return put(key, DevCacheUtils.newStringWithDateInfo(saveTime, value));
        }
        return false;
    }

    /**
     * 读取 String 数据
     * @param key 保存的 key
     * @return 字符串数据
     */
    public String getAsString(final String key) {
        File file = mCache.get(key);
        if (file == null) {
            return null;
        }
        if (!file.exists())
            return null;
        boolean removeFile = false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                builder.append(currentLine);
            }
            // 读取内容
            String readString = builder.toString();
            if (!DevCacheUtils.isDue(readString)) {
                return DevCacheUtils.clearDateInfo(readString);
            } else {
                LogPrintUtils.dTag(TAG, "getAsString key: " + key + " file has expired");
                removeFile = true;
                return null;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAsString");
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (removeFile)
                remove(key);
        }
    }

    // ========================
    // = JSONObject 数据 读写 =
    // ========================

    /**
     * 保存 JSONObject 数据到缓存中
     * @param key   保存的 key
     * @param value 保存的 JSONObject 数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final JSONObject value) {
        if (value != null) {
            try {
                return put(key, value.toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONObject");
            }
        }
        return false;
    }

    /**
     * 保存 JSONObject 数据到缓存中
     * @param key      保存的 key
     * @param value    保存的 JSONObject 数据
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final JSONObject value, final int saveTime) {
        if (value != null) {
            try {
                return put(key, value.toString(), saveTime);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONObject");
            }
        }
        return false;
    }

    /**
     * 读取 JSONObject 数据
     * @param key 保存的 key
     * @return {@link JSONObject}
     */
    public JSONObject getAsJSONObject(final String key) {
        String json = getAsString(key);
        if (json != null) {
            try {
                return new JSONObject(json);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAsJSONObject");
            }
        }
        return null;
    }

    // =======================
    // = JSONArray 数据 读写 =
    // =======================

    /**
     * 保存 JSONArray 数据到缓存中
     * @param key   保存的 key
     * @param value 保存的 JSONArray 数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final JSONArray value) {
        if (value != null) {
            try {
                return put(key, value.toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONArray");
            }
        }
        return false;
    }

    /**
     * 保存 JSONArray 数据到缓存中
     * @param key      保存的 key
     * @param value    保存的 JSONArray 数据
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final JSONArray value, final int saveTime) {
        if (value != null) {
            try {
                return put(key, value.toString(), saveTime);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONArray");
            }
        }
        return false;
    }

    /**
     * 读取 JSONArray 数据
     * @param key 保存的 key
     * @return {@link JSONArray}
     */
    public JSONArray getAsJSONArray(final String key) {
        String json = getAsString(key);
        if (json != null) {
            try {
                return new JSONArray(json);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAsJSONArray");
            }
        }
        return null;
    }

    // ==================
    // = byte 数据 读写 =
    // ==================

    /**
     * 保存 byte 数据到缓存中
     * @param key  保存的 key
     * @param data 保存的数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final byte[] data) {
        if (key == null || data == null) return false;
        File file = mCache.newFile(key);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put byte[]");
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                } catch (Exception e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
            mCache.put(file);
        }
        return false;
    }

    /**
     * 返回缓存流写入数据对象
     * @param key 保存的 key
     * @return {@link OutputStream}
     * @throws FileNotFoundException 文件不存在
     */
    public OutputStream put(final String key) throws FileNotFoundException {
        File file = mCache.newFile(key);
        if (file != null) {
            return new xFileOutputStream(file);
        }
        return null;
    }

    /**
     * 获取对应 key 的 File 输入流
     * @param key 保存的 key
     * @return {@link InputStream}
     * @throws FileNotFoundException 文件不存在
     */
    public InputStream get(final String key) throws FileNotFoundException {
        File file = mCache.get(key);
        if (file != null && file.exists()) {
            return new FileInputStream(file);
        }
        return null;
    }

    /**
     * 保存 byte 数据到缓存中
     * @param key      保存的 key
     * @param data     保存的数据
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final byte[] data, final int saveTime) {
        return put(key, DevCacheUtils.newByteArrayWithDateInfo(saveTime, data));
    }

    /**
     * 获取 byte[] 数据
     * @param key 保存的 key
     * @return byte[]
     */
    public byte[] getAsBinary(final String key) {
        RandomAccessFile raFile = null;
        boolean removeFile = false;
        try {
            File file = mCache.get(key);
            if (!file.exists())
                return null;
            raFile = new RandomAccessFile(file, "r");
            byte[] byteArray = new byte[(int) raFile.length()];
            raFile.read(byteArray);
            if (!DevCacheUtils.isDue(byteArray)) {
                return DevCacheUtils.clearDateInfo(byteArray);
            } else {
                LogPrintUtils.dTag(TAG, "getAsBinary - key: " + key + " file has expired");
                removeFile = true;
                return null;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAsBinary");
            return null;
        } finally {
            if (raFile != null) {
                try {
                    raFile.close();
                } catch (Exception e) {
                }
            }
            if (removeFile)
                remove(key);
        }
    }

    // ====================
    // = 序列化 数据 读写 =
    // ====================

    /**
     * 保存 Serializable 数据到缓存中
     * @param key   保存的 key
     * @param value 保存的 value
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final Serializable value) {
        return put(key, value, -1);
    }

    /**
     * 保存 Serializable 数据到缓存中
     * @param key      保存的 key
     * @param value    保存的 value
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final Serializable value, final int saveTime) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            if (saveTime != -1) {
                put(key, data, saveTime);
            } else {
                put(key, data);
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * 读取 Serializable 数据
     * @param key 保存的 key
     * @return {@link Serializable} Object
     */
    public Object getAsObject(final String key) {
        byte[] data = getAsBinary(key);
        if (data != null) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(data));
                return ois.readObject();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAsObject");
                return null;
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return null;
    }

    // ====================
    // = bitmap 数据 读写 =
    // ====================

    /**
     * 保存 Bitmap 到缓存中
     * @param key   保存的 key
     * @param value 保存的 bitmap 数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final Bitmap value) {
        return put(key, DevCacheUtils.bitmapToBytes(value));
    }

    /**
     * 保存 Bitmap 到缓存中
     * @param key      保存的 key
     * @param value    保存的 bitmap 数据
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final Bitmap value, final int saveTime) {
        return put(key, DevCacheUtils.bitmapToBytes(value), saveTime);
    }

    /**
     * 读取 Bitmap 数据
     * @param key 保存的 key
     * @return {@link Bitmap}
     */
    public Bitmap getAsBitmap(final String key) {
        byte[] data = getAsBinary(key);
        if (data == null) return null;
        return DevCacheUtils.bytesToBitmap(data);
    }

    // ======================
    // = drawable 数据 读写 =
    // ======================

    /**
     * 保存 Drawable 到缓存中
     * @param key   保存的 key
     * @param value 保存的 drawable 数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final Drawable value) {
        return put(key, DevCacheUtils.drawableToBitmap(value));
    }

    /**
     * 保存 Drawable 到缓存中
     * @param key      保存的 key
     * @param value    保存的 drawable 数据
     * @param saveTime 保存的时间, 单位: 秒
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(final String key, final Drawable value, final int saveTime) {
        return put(key, DevCacheUtils.drawableToBitmap(value), saveTime);
    }

    /**
     * 读取 Drawable 数据
     * @param key 保存的 key
     * @return {@link Drawable}
     */
    public Drawable getAsDrawable(final String key) {
        byte[] data = getAsBinary(key);
        if (data == null) return null;
        return DevCacheUtils.bitmapToDrawable(DevCacheUtils.bytesToBitmap(data));
    }

    /**
     * 获取缓存文件
     * @param key 保存的 key
     * @return 缓存的文件
     */
    public File file(final String key) {
        File file = mCache.newFile(key);
        if (file != null && file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * 移除某个 key 的数据
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public boolean remove(final String key) {
        return mCache.remove(key);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        mCache.clear();
    }
}