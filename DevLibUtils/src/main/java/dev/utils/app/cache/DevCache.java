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
 * @author 杨福海(michael) www.yangfuhai.com
 * @author Ttt (重写、规范注释、逻辑判断等)
 */
public final class DevCache {

    private DevCache() {
    }

    // 日志 TAG
    private static final String TAG = DevCache.class.getSimpleName();
    // 缓存文件名
    private static final String DF_FILE_NAME = DevCache.class.getSimpleName();
    // 过期小时(单位秒) = 1小时
    public static final int TIME_HOUR = 60 * 60;
    // 一天24小时
    public static final int TIME_DAY = TIME_HOUR * 24;
    // 缓存最大值(50mb)
    private static final int MAX_SIZE = 1000 * 1000 * 50;
    // 不限制存放数据的数量
    private static final int maxCount = Integer.MAX_VALUE;
    // 不同地址配置缓存对象
    private static Map<String, DevCache> mInstanceMap = new HashMap<>();
    // 缓存管理类
    private DevCacheManager mCache;
    // 缓存地址
    private static File contextCacheDir = null;

    /**
     * 内部处理防止 Context 为 null 崩溃问题
     * @param context Context
     * @return {@link Context}
     */
    private static Context getContext(final Context context) {
        if (context != null) {
            return context;
        } else {
            // 设置全局Context
            return DevUtils.getContext();
        }
    }

    /**
     * 获取缓存地址
     * @param context Context
     * @return 应用缓存地址
     */
    public static File getCacheDir(final Context context) {
        if (contextCacheDir == null) {
            contextCacheDir = getContext(context).getCacheDir();
        }
        return contextCacheDir;
    }

    /**
     * 默认缓存地址
     * @param context Context
     * @return {@link DevCache}
     */
    public static DevCache get(final Context context) {
        return get(context, DF_FILE_NAME);
    }

    /**
     * 获取缓存地址
     * @param context   Context
     * @param cacheName 缓存文件名
     * @return {@link DevCache}
     */
    public static DevCache get(final Context context, final String cacheName) {
        if (cacheName == null) return null;
        // 进行处理
        File file = new File(getCacheDir(context), cacheName);
        // 获取默认地址
        return get(file, MAX_SIZE, maxCount);
    }

    /**
     * 设置自定义缓存地址
     * @param cacheDir 缓存文件地址
     * @return {@link DevCache}
     */
    public static DevCache get(final File cacheDir) {
        return get(cacheDir, MAX_SIZE, maxCount);
    }

    /**
     * 自定义缓存大小
     * @param context  Context
     * @param maxSize  文件最大大小
     * @param maxCount 最大存储数量
     * @return {@link DevCache}
     */
    public static DevCache get(final Context context, final long maxSize, final int maxCount) {
        File file = new File(getCacheDir(context), DF_FILE_NAME);
        return get(file, maxSize, maxCount);
    }

    /**
     * 自定义缓存地址、大小等
     * @param cacheDir
     * @param maxSize
     * @param maxCount
     * @return {@link DevCache}
     */
    public static DevCache get(final File cacheDir, final long maxSize, final int maxCount) {
        if (cacheDir == null) return null;
        // 判断是否存在缓存信息
        DevCache manager = mInstanceMap.get(cacheDir.getAbsoluteFile() + myPid());
        if (manager == null) {
            // 初始化新的缓存信息, 并且保存
            manager = new DevCache(cacheDir, maxSize, maxCount);
            mInstanceMap.put(cacheDir.getAbsolutePath() + myPid(), manager);
        }
        return manager;
    }

    /**
     * 获取进程pid - android.os.Process.myPid()
     * @return 进程id
     */
    private static String myPid() {
        return "_" + android.os.Process.myPid();
    }

    /**
     * 最终初始化方法
     * @param cacheDir
     * @param maxSize
     * @param maxCount
     * @return {@link DevCache} 缓存工具类对象
     */
    private DevCache(final File cacheDir, final long maxSize, final int maxCount) {
        if (cacheDir == null) {
            new Exception("cacheDir is null");
        } else if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            new Exception("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        mCache = new DevCacheManager(cacheDir, maxSize, maxCount);
    }

    /**
     * Provides a means to save a cached file before the data are available.
     * Since writing about the file is complete, and its close method is called,
     * its contents will be registered in the cache
     */
    class xFileOutputStream extends FileOutputStream {
        File file;

        public xFileOutputStream(File file) throws FileNotFoundException {
            super(file);
            this.file = file;
        }

        public void close() throws IOException {
            super.close();
            mCache.put(file);
        }
    }

    // ===================
    // = String数据 读写 =
    // ===================

    /**
     * 保存 String 数据到缓存中
     * @param key   保存的key
     * @param value 保存的String数据
     */
    public void put(final String key, final String value) {
        File file = mCache.newFile(key);
        if (file == null || value == null) {
            return;
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file), 1024);
            out.write(value);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put");
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
            mCache.put(file);
        }
    }

    /**
     * 保存 String 数据到缓存中
     * @param key      保存的key
     * @param value    保存的String数据
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final String value, final int saveTime) {
        if (key != null && value != null) {
            put(key, DevCacheUtils.newStringWithDateInfo(saveTime, value));
        }
    }

    /**
     * 读取 String 数据
     * @param key
     * @return
     */
    public String getAsString(final String key) {
        File file = mCache.get(key);
        if (file == null) {
            return null;
        }
        if (!file.exists())
            return null;
        boolean removeFile = false;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String readString = "";
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                readString += currentLine;
            }
            if (!DevCacheUtils.isDue(readString)) {
                return DevCacheUtils.clearDateInfo(readString);
            } else {
                LogPrintUtils.dTag(TAG, "getAsString key: " + key + " -> 文件已过期");
                removeFile = true;
                return null;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAsString");
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
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
     * @param key   保存的key
     * @param value 保存的JSON数据
     */
    public void put(final String key, final JSONObject value) {
        if (value != null) {
            try {
                put(key, value.toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONObject");
            }
        }
    }

    /**
     * 保存 JSONObject 数据到缓存中
     * @param key      保存的key
     * @param value    保存的JSONObject数据
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final JSONObject value, final int saveTime) {
        if (value != null) {
            try {
                put(key, value.toString(), saveTime);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONObject");
            }
        }
    }

    /**
     * 读取 JSONObject 数据
     * @param key
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
     * @param key   保存的key
     * @param value 保存的JSONArray数据
     */
    public void put(final String key, final JSONArray value) {
        if (value != null) {
            try {
                put(key, value.toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONArray");
            }
        }
    }

    /**
     * 保存 JSONArray 数据到缓存中
     * @param key      保存的key
     * @param value    保存的JSONArray数据
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final JSONArray value, final int saveTime) {
        if (value != null) {
            try {
                put(key, value.toString(), saveTime);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "put JSONArray");
            }
        }
    }

    /**
     * 读取 JSONArray 数据
     * @param key
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
     * @param key  保存的key
     * @param data 保存的数据
     */
    public void put(final String key, final byte[] data) {
        if (key == null || data == null) {
            return;
        }
        File file = mCache.newFile(key);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put byte[]");
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
            mCache.put(file);
        }
    }

    /**
     * 返回缓存流写入数据对象
     * @param key 保存的key
     * @return
     * @throws FileNotFoundException
     */
    public OutputStream put(final String key) throws FileNotFoundException {
        File file = mCache.newFile(key);
        if (file != null) {
            return new xFileOutputStream(file);
        }
        return null;
    }

    /**
     * 获取对应key File 输入流
     * @param key 保存的key
     * @return
     * @throws FileNotFoundException
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
     * @param key      保存的key
     * @param data     保存的数据
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final byte[] data, final int saveTime) {
        put(key, DevCacheUtils.newByteArrayWithDateInfo(saveTime, data));
    }

    /**
     * 获取 byte[] 数据
     * @param key
     * @return
     */
    public byte[] getAsBinary(final String key) {
        RandomAccessFile RAFile = null;
        boolean removeFile = false;
        try {
            File file = mCache.get(key);
            if (!file.exists())
                return null;
            RAFile = new RandomAccessFile(file, "r");
            byte[] byteArray = new byte[(int) RAFile.length()];
            RAFile.read(byteArray);
            if (!DevCacheUtils.isDue(byteArray)) {
                return DevCacheUtils.clearDateInfo(byteArray);
            } else {
                LogPrintUtils.dTag(TAG, "getAsBinary - key: " + key + " -> 文件已过期");
                removeFile = true;
                return null;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAsBinary");
            return null;
        } finally {
            if (RAFile != null) {
                try {
                    RAFile.close();
                } catch (IOException e) {
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
     * @param key   保存的key
     * @param value 保存的value
     */
    public void put(final String key, final Serializable value) {
        put(key, value, -1);
    }

    /**
     * 保存 Serializable 数据到缓存中
     * @param key      保存的key
     * @param value    保存的value
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final Serializable value, final int saveTime) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            if (saveTime != -1) {
                put(key, data, saveTime);
            } else {
                put(key, data);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "put");
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 读取 Serializable 数据
     * @param key
     * @return Serializable 数据
     */
    public Object getAsObject(final String key) {
        byte[] data = getAsBinary(key);
        if (data != null) {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            try {
                bais = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bais);
                Object reObject = ois.readObject();
                return reObject;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAsObject");
                return null;
            } finally {
                if (bais != null) {
                    try {
                        bais.close();
                    } catch (IOException e) {
                    }
                }
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
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
     * 保存 bitmap 到缓存中
     * @param key   保存的key
     * @param value 保存的bitmap数据
     */
    public void put(final String key, final Bitmap value) {
        put(key, DevCacheUtils.bitmapToBytes(value));
    }

    /**
     * 保存 bitmap 到缓存中
     * @param key      保存的key
     * @param value    保存的 bitmap 数据
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final Bitmap value, final int saveTime) {
        put(key, DevCacheUtils.bitmapToBytes(value), saveTime);
    }

    /**
     * 读取 bitmap 数据
     * @param key
     * @return
     */
    public Bitmap getAsBitmap(final String key) {
        byte[] data = getAsBinary(key);
        if (data == null) {
            return null;
        }
        return DevCacheUtils.bytesToBitmap(data);
    }

    // ======================
    // = drawable 数据 读写 =
    // ======================

    /**
     * 保存 drawable 到缓存中
     * @param key   保存的key
     * @param value 保存的drawable数据
     */
    public void put(final String key, final Drawable value) {
        put(key, DevCacheUtils.drawableToBitmap(value));
    }

    /**
     * 保存 drawable 到缓存中
     * @param key      保存的key
     * @param value    保存的 drawable 数据
     * @param saveTime 保存的时间，单位 秒
     */
    public void put(final String key, final Drawable value, final int saveTime) {
        put(key, DevCacheUtils.drawableToBitmap(value), saveTime);
    }

    /**
     * 读取 Drawable 数据
     * @param key
     * @return
     */
    public Drawable getAsDrawable(final String key) {
        byte[] data = getAsBinary(key);
        if (data == null) {
            return null;
        }
        return DevCacheUtils.bitmapToDrawable(DevCacheUtils.bytesToBitmap(data));
    }

    /**
     * 获取缓存文件
     * @param key
     * @return value 缓存的文件
     */
    public File file(final String key) {
        File file = mCache.newFile(key);
        if (file != null && file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * 移除某个key
     * @param key
     * @return 是否移除成功
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
