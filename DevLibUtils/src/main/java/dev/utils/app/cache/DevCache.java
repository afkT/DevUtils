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
 * Created by 杨福海(michael) www.yangfuhai.com
 * Update to Ttt
 */
public final class DevCache {

    private DevCache() {
    }

//    // ==== 缓存状态标记 ====
//    // 存在数据
//    public static final int EXIST = 1;
//    // 不存在数据
//    public static final int NOT_EXIST = 2;
//    // 存在数据 - 但是已过期
//    public static final int OVERDUE = 3;
//    // 保存成功
//    public static final int SAVE_SUC = 4;
//    // 保存失败
//    public static final int SAVE_FAIL = 5;
    // ===

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
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    // 不同地址配置缓存对象
    private static Map<String, DevCache> mInstanceMap = new HashMap<>();
    // 缓存管理类
    private DevCacheManager mCache;
    // 缓存地址
    private static File ctxCacheDir = null;

    /**
     * 内部处理防止 Context 为 null 崩溃问题
     * @return {@link Context}
     */
    private static Context getContext(Context context) {
        if (context != null) {
            return context;
        } else {
            // 设置全局Context
            return DevUtils.getContext();
        }
    }

    /**
     * 获取缓存地址
     * @param ctx
     * @return 应用缓存地址
     */
    public static File getCacheDir(Context ctx) {
        if (ctxCacheDir == null) {
            ctxCacheDir = getContext(ctx).getCacheDir();
        }
        return ctxCacheDir;
    }

    /**
     * 默认缓存地址
     * @param ctx
     * @return {@link DevCache} 缓存工具类对象
     */
    public static DevCache get(Context ctx) {
        return get(ctx, DF_FILE_NAME);
    }

    /**
     * 获取缓存地址
     * @param ctx
     * @param cacheName
     * @return {@link DevCache} 缓存工具类对象
     */
    public static DevCache get(Context ctx, String cacheName) {
        // 进行处理
        File file = new File(getCacheDir(ctx), cacheName);
        // 获取默认地址
        return get(file, MAX_SIZE, MAX_COUNT);
    }

    /**
     * 设置自定义缓存地址
     * @param cacheDir
     * @return {@link DevCache} 缓存工具类对象
     */
    public static DevCache get(File cacheDir) {
        return get(cacheDir, MAX_SIZE, MAX_COUNT);
    }

    /**
     * 自定义缓存大小
     * @param ctx
     * @param max_zise
     * @param max_count
     * @return {@link DevCache} 缓存工具类对象
     */
    public static DevCache get(Context ctx, long max_zise, int max_count) {
        File file = new File(getCacheDir(ctx), DF_FILE_NAME);
        // -
        return get(file, max_zise, max_count);
    }

    /**
     * 自定义缓存地址、大小等
     * @param cacheDir
     * @param max_zise
     * @param max_count
     * @return {@link DevCache} 缓存工具类对象
     */
    public static DevCache get(File cacheDir, long max_zise, int max_count) {
        // 判断是否存在缓存信息
        DevCache manager = mInstanceMap.get(cacheDir.getAbsoluteFile() + myPid());
        if (manager == null) {
            // 初始化新的缓存信息, 并且保存
            manager = new DevCache(cacheDir, max_zise, max_count);
            mInstanceMap.put(cacheDir.getAbsolutePath() + myPid(), manager);
        }
        return manager;
    }

    /**
     * 获取进程pid
     * @return _android.os.Process.myPid()
     */
    private static String myPid() {
        return "_" + android.os.Process.myPid();
    }

    /**
     * 最终初始化方法
     * @param cacheDir
     * @param max_size
     * @param max_count
     * @return {@link DevCache} 缓存工具类对象
     */
    private DevCache(File cacheDir, long max_size, int max_count) {
        if (cacheDir == null) {
            new Exception("cacheDir is null");
        } else if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            new Exception("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        mCache = new DevCacheManager(cacheDir, max_size, max_count);
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

    // =====================================
    // ========== String数据 读写 ==========
    // =====================================

    /**
     * 保存 String 数据到缓存中
     * @param key 保存的key
     * @param value 保存的String数据
     */
    public void put(String key, String value) {
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
     * @param key 保存的key
     * @param value 保存的String数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, String value, int saveTime) {
        if (key != null && value != null) {
            put(key, DevCacheUtils.newStringWithDateInfo(saveTime, value));
        }
    }

    /**
     * 读取 String 数据
     * @param key
     * @return String 数据
     */
    public String getAsString(String key) {
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

    // =====================================
    // ======= JSONObject 数据 读写 ========
    // =====================================

    /**
     * 保存 JSONObject 数据到缓存中
     * @param key 保存的key
     * @param value 保存的JSON数据
     */
    public void put(String key, JSONObject value) {
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
     * @param key 保存的key
     * @param value 保存的JSONObject数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, JSONObject value, int saveTime) {
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
    public JSONObject getAsJSONObject(String key) {
        String JSONString = getAsString(key);
        if (JSONString != null) {
            try {
                JSONObject obj = new JSONObject(JSONString);
                return obj;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAsJSONObject");
            }
        }
        return null;
    }

    // =====================================
    // ======== JSONArray 数据 读写 ========
    // =====================================

    /**
     * 保存 JSONArray 数据到缓存中
     * @param key 保存的key
     * @param value 保存的JSONArray数据
     */
    public void put(String key, JSONArray value) {
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
     * @param key 保存的key
     * @param value 保存的JSONArray数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, JSONArray value, int saveTime) {
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
    public JSONArray getAsJSONArray(String key) {
        String JSONString = getAsString(key);
        if (JSONString != null) {
            try {
                JSONArray obj = new JSONArray(JSONString);
                return obj;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getAsJSONArray");
            }
        }
        return null;
    }

    // =====================================
    // ========== byte 数据 读写 ===========
    // =====================================

    /**
     * 保存 byte 数据到缓存中
     * @param key 保存的key
     * @param value 保存的数据
     */
    public void put(String key, byte[] value) {
        if (key == null || value == null) {
            return;
        }
        File file = mCache.newFile(key);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(value);
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
     * @param key the file name.
     * @return OutputStream stream for writing data
     * @throws FileNotFoundException if the file can not be created.
     */
    public OutputStream put(String key) throws FileNotFoundException {
        File file = mCache.newFile(key);
        if (file != null) {
            return new xFileOutputStream(file);
        }
        return null;
    }

    /**
     * @param key the file name.
     * @return (InputStream or null) stream previously saved in cache.
     * @throws FileNotFoundException if the file can not be opened
     */
    public InputStream get(String key) throws FileNotFoundException {
        File file = mCache.get(key);
        if (file != null && file.exists()) {
            return new FileInputStream(file);
        }
        return null;
    }

    /**
     * 保存 byte 数据到缓存中
     * @param key 保存的key
     * @param value 保存的数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, byte[] value, int saveTime) {
        put(key, DevCacheUtils.newByteArrayWithDateInfo(saveTime, value));
    }

    /**
     * 获取 byte[] 数据
     * @param key
     * @return byte[] 数据
     */
    public byte[] getAsBinary(String key) {
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

    // =====================================
    // ========== 序列化 数据 读写 =========
    // =====================================

    /**
     * 保存 Serializable 数据到缓存中
     * @param key 保存的key
     * @param value 保存的value
     */
    public void put(String key, Serializable value) {
        put(key, value, -1);
    }

    /**
     * 保存 Serializable 数据到缓存中
     * @param key 保存的key
     * @param value 保存的value
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Serializable value, int saveTime) {
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
    public Object getAsObject(String key) {
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

    // =====================================
    // ========== bitmap 数据 读写 =========
    // =====================================

    /**
     * 保存 bitmap 到缓存中
     * @param key 保存的key
     * @param value 保存的bitmap数据
     */
    public void put(String key, Bitmap value) {
        put(key, DevCacheUtils.bitmap2Bytes(value));
    }

    /**
     * 保存 bitmap 到缓存中
     * @param key 保存的key
     * @param value 保存的 bitmap 数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Bitmap value, int saveTime) {
        put(key, DevCacheUtils.bitmap2Bytes(value), saveTime);
    }

    /**
     * 读取 bitmap 数据
     * @param key
     * @return bitmap 数据
     */
    public Bitmap getAsBitmap(String key) {
        byte[] bytes = getAsBinary(key);
        if (bytes == null) {
            return null;
        }
        return DevCacheUtils.bytes2Bimap(bytes);
    }

    // =====================================
    // ======== drawable 数据 读写 =========
    // =====================================

    /**
     * 保存 drawable 到缓存中
     * @param key 保存的key
     * @param value 保存的drawable数据
     */
    public void put(String key, Drawable value) {
        put(key, DevCacheUtils.drawable2Bitmap(value));
    }

    /**
     * 保存 drawable 到缓存中
     * @param key 保存的key
     * @param value 保存的 drawable 数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Drawable value, int saveTime) {
        put(key, DevCacheUtils.drawable2Bitmap(value), saveTime);
    }

    /**
     * 读取 Drawable 数据
     * @param key
     * @return Drawable 数据
     */
    public Drawable getAsDrawable(String key) {
        byte[] bytes = getAsBinary(key);
        if (bytes == null) {
            return null;
        }
        return DevCacheUtils.bitmap2Drawable(DevCacheUtils.bytes2Bimap(bytes));
    }

    /**
     * 获取缓存文件
     * @param key
     * @return value 缓存的文件
     */
    public File file(String key) {
        File f = mCache.newFile(key);
        if (f != null && f.exists()) {
            return f;
        }
        return null;
    }

    /**
     * 移除某个key
     * @param key
     * @return 是否移除成功
     */
    public boolean remove(String key) {
        return mCache.remove(key);
    }

    /** 清除所有数据 */
    public void clear() {
        mCache.clear();
    }
}
