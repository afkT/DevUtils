package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: 克隆工具类
 * @author Ttt
 */
public final class CloneUtils {

    private CloneUtils() {
    }

    // 日志 TAG
    private static final String TAG = CloneUtils.class.getSimpleName();

    /**
     * 进行克隆
     * @param data Object implements {@link Serializable}
     * @param <T>  泛型
     * @return 克隆后的对象
     */
    public static <T> T deepClone(final Serializable data) {
        if (data == null) return null;
        return (T) bytesToObject(serializableToBytes(data));
    }

    /**
     * 通过序列化实体类, 获取对应的 byte[] 数据
     * @param serializable Object implements {@link Serializable}
     * @return 克隆后 byte[]
     */
    public static byte[] serializableToBytes(final Serializable serializable) {
        if (serializable == null) return null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(serializable);
            return baos.toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "serializableToBytes");
            return null;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    // =

    /**
     * 进行克隆
     * @param map   存储集合
     * @param datas 需要克隆的数据源
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean deepClone(final Map<K, V> map, final Map<K, V> datas) {
        if (map != null && datas != null && datas.size() > 0) {
            Iterator<Map.Entry<K, V>> iterator = datas.entrySet().iterator();
            while (iterator.hasNext()) {
                try {
                    Map.Entry<K, V> entry = iterator.next();
                    // 获取 key
                    K key = entry.getKey();
                    // 克隆对象
                    V cloneObj = (V) bytesToObject(serializableToBytes((Serializable) entry.getValue()));
                    if (cloneObj != null) {
                        // 保存到集合
                        map.put(key, cloneObj);
                    }
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "deepClone");
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 进行克隆
     * @param collection 存储集合
     * @param datas      需要克隆的数据源
     * @param <T>        泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean deepClone(final Collection<T> collection, final Collection<T> datas) {
        if (collection != null && datas != null && datas.size() > 0) {
            Iterator<T> iterator = datas.iterator();
            while (iterator.hasNext()) {
                try {
                    // 克隆对象
                    T cloneObj = (T) bytesToObject(serializableToBytes((Serializable) iterator.next()));
                    if (cloneObj != null) {
                        collection.add(cloneObj);
                    }
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "deepClone");
                }
            }
            return true;
        }
        return false;
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ================
    // = ConvertUtils =
    // ================

    /**
     * byte[] 转为 Object
     * @param bytes byte[]
     * @return {@link Object}
     */
    private static Object bytesToObject(final byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToObject");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }
}