package dev.utils.app.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

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

    }

    public void removeForKeys(String[] keys) {

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

    // 文件后缀
    private static final String DATA_EXTENSION   = ".data";
    private static final String CONFIG_EXTENSION = ".config";

    /**
     * 获取 Key 数据文件
     * @param path 文件地址
     * @param key  存储 key
     * @return Key 数据文件
     */
    public static File getKeyDataFile(
            final String path,
            final String key
    ) {
        if (key == null) return null;
        return FileUtils.getFile(path, key + DATA_EXTENSION);
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
        if (key == null) return 0L;
        return FileUtils.getFileLength(getKeyDataFile(path, key));
    }
}