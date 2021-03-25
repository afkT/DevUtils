package dev.utils.app.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 缓存管理类
 * @author Ttt
 */
final class DevCacheManager {

    // 缓存地址
    private final String mCachePath;

    public DevCacheManager(String cachePath) {
        this.mCachePath = cachePath;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取缓存地址
     * @return 缓存地址
     */
    public String getCachePath() {
        return mCachePath;
    }

    // =

    public void remove(String key) {
        deleteFile(key);
    }

    public void removeForKeys(String[] keys) {
        if (keys == null) return;
        for (String key : keys) {
            deleteFile(key);
        }
    }

    public boolean contains(String key) {
        return false;
    }

    public boolean isDue(String key) {
        return false;
    }

    public void clear() {

    }

    public void clearDue() {

    }

    public void clearInvalid() {

    }

    public void clearType(int type) {

    }

    public DevCache.Data getItemByKey(String key) {
        return null;
    }

    public List<DevCache.Data> getKeys() {
        return null;
    }

    public List<DevCache.Data> getPermanentKeys() {
        return null;
    }

    public int getCount() {
        return 0;
    }

    public long getSize() {
        return 0;
    }

    public boolean put(
            String key,
            int value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            long value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            float value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            double value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            boolean value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            String value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            byte[] value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Bitmap value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Drawable value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Serializable value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Parcelable value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            JSONObject value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            JSONArray value,
            long validTime
    ) {
        return false;
    }

    public <T> boolean put(
            String key,
            T value,
            long validTime
    ) {
        return false;
    }

    public int getInt(String key) {
        return 0;
    }

    public long getLong(String key) {
        return 0;
    }

    public float getFloat(String key) {
        return 0;
    }

    public double getDouble(String key) {
        return 0;
    }

    public boolean getBoolean(String key) {
        return false;
    }

    public String getString(String key) {
        return null;
    }

    public byte[] getBytes(String key) {
        return new byte[0];
    }

    public Bitmap getBitmap(String key) {
        return null;
    }

    public Drawable getDrawable(String key) {
        return null;
    }

    public Serializable getSerializable(String key) {
        return null;
    }

    public Parcelable getParcelable(String key) {
        return null;
    }

    public JSONObject getJSONObject(String key) {
        return null;
    }

    public JSONArray getJSONArray(String key) {
        return null;
    }

    public <T> T getEntity(
            String key,
            Type typeOfT
    ) {
        return null;
    }

    public int getInt(
            String key,
            int defaultValue
    ) {
        return 0;
    }

    public long getLong(
            String key,
            long defaultValue
    ) {
        return 0;
    }

    public float getFloat(
            String key,
            float defaultValue
    ) {
        return 0;
    }

    public double getDouble(
            String key,
            double defaultValue
    ) {
        return 0;
    }

    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return false;
    }

    public String getString(
            String key,
            String defaultValue
    ) {
        return null;
    }

    public byte[] getBytes(
            String key,
            byte[] defaultValue
    ) {
        return new byte[0];
    }

    public Bitmap getBitmap(
            String key,
            Bitmap defaultValue
    ) {
        return null;
    }

    public Drawable getDrawable(
            String key,
            Drawable defaultValue
    ) {
        return null;
    }

    public Serializable getSerializable(
            String key,
            Serializable defaultValue
    ) {
        return null;
    }

    public Parcelable getParcelable(
            String key,
            Parcelable defaultValue
    ) {
        return null;
    }

    public JSONObject getJSONObject(
            String key,
            JSONObject defaultValue
    ) {
        return null;
    }

    public JSONArray getJSONArray(
            String key,
            JSONArray defaultValue
    ) {
        return null;
    }

    public <T> T getEntity(
            String key,
            Type typeOfT,
            T defaultValue
    ) {
        return null;
    }

    // ===============
    // = 内部处理方法 =
    // ===============

    // 日志 TAG
    private final String TAG = DevCacheManager.class.getSimpleName();

    // 文件后缀
    private static final String DATA_EXTENSION   = ".data";
    private static final String CONFIG_EXTENSION = ".config";

    /**
     * 获取 Key 数据文件
     * @param key 存储 key
     * @return Key 数据文件
     */
    private File getKeyDataFile(final String key) {
        if (TextUtils.isEmpty(key)) return null;
        return FileUtils.getFile(mCachePath, key + DATA_EXTENSION);
    }

    /**
     * 获取 Key 配置文件
     * @param key 存储 key
     * @return Key 配置文件
     */
    private File getKeyConfigFile(final String key) {
        if (TextUtils.isEmpty(key)) return null;
        return FileUtils.getFile(mCachePath, key + CONFIG_EXTENSION);
    }

    /**
     * 获取存储数据大小
     * @param path 文件地址
     * @param key  存储 key
     * @return 存储数据大小
     */
    public static long getDataFileSize(
            final String path,
            final String key
    ) {
        if (TextUtils.isEmpty(key)) return 0L;
        return FileUtils.getFileLength(
                FileUtils.getFile(path, key + DATA_EXTENSION)
        );
    }

    /**
     * 删除 Key 配置、数据文件
     * @param key 存储 key
     */
    private void deleteFile(final String key) {
        if (TextUtils.isEmpty(key)) return;
        File dataFile   = getKeyDataFile(key);
        File configFile = getKeyConfigFile(key);
        FileUtils.deleteFiles(dataFile, configFile);
    }

    /**
     * 判断是否存在 Key 配置、数据文件
     * @param key 存储 key
     * @return {@code true} yes, {@code false} no
     */
    private boolean isExistKeyFile(final String key) {
        if (TextUtils.isEmpty(key)) return false;
        return FileUtils.isFileExists(getKeyDataFile(key))
                && FileUtils.isFileExists(getKeyConfigFile(key));
    }

    // ========
    // = 配置 =
    // ========

    // Data JSON Format
    private final String DATA_FORMAT = "{\"key\":\"%s\",\"type\":%d,\"saveTime\":%d,\"validTime\":%d,\"lastModified\":%d}";

    /**
     * Data Format JSON String
     * @param data 数据源
     * @return JSON String
     */
    private String toString(final DevCache.Data data) {
        return String.format(DATA_FORMAT, data.getKey(),
                data.getType(), data.getSaveTime(),
                data.getValidTime(), data.getLastModified()
        );
    }

    /**
     * 读取配置初始化 Data
     * @param key 存储 key
     * @return {@link DevCache.Data}
     */
    private DevCache.Data getData(final String key) {
        if (!isExistKeyFile(key)) return null;
        try {
            File       configFile = getKeyConfigFile(key);
            String     config     = new String(readFileBytes(configFile));
            JSONObject jsonObject = new JSONObject(config);
            if (jsonObject.has("key")
                    && jsonObject.has("type")
                    && jsonObject.has("saveTime")
                    && jsonObject.has("validTime")
                    && jsonObject.has("lastModified")
            ) {
                String _key         = jsonObject.getString("key");
                int    type         = jsonObject.getInt("type");
                long   saveTime     = jsonObject.getLong("saveTime");
                long   validTime    = jsonObject.getLong("validTime");
                long   lastModified = jsonObject.getLong("lastModified");
                return new DevCache.Data(mCachePath, _key,
                        type, saveTime, validTime, lastModified);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getData");
        }
        return null;
    }

    /**
     * 将 byte[] 写入文件
     * @param file  待写入文件
     * @param bytes 待写入数据
     * @return {@code true} success, {@code false} fail
     */
    private boolean saveFileBytes(
            final File file,
            final byte[] bytes
    ) {
        return FileUtils.saveFile(file, bytes);
    }

    /**
     * 读取文件 byte[]
     * @param file 待读取文件
     * @return 文件 byte[]
     */
    private byte[] readFileBytes(final File file) {
        return FileUtils.readFileBytes(file);
    }
}