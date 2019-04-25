package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
     * 通过序列化实体类, 获取对应的byte数组数据
     * @param serializable Object implements {@link Serializable}
     * @return 克隆后 byte[]
     */
    public static byte[] serializableToBytes(final Serializable serializable) {
        if (serializable == null) return null;
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
            oos.writeObject(serializable);
            return baos.toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "serializableToBytes");
            return null;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 通过 byte 数据, 生成 Object 对象
     * @param data byte[]
     * @return {@link Object}
     */
    public static Object bytesToObject(final byte[] data) {
        if (data == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return ois.readObject();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToObject");
            return null;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
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
     */
    public static <K, V> void deepClone(final Map<K, V> map, final Map<K, V> datas) {
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
        }
    }

    /**
     * 进行克隆
     * @param collection 存储集合
     * @param datas      需要克隆的数据源
     * @param <T>        泛型
     */
    public static <T> void deepClone(final Collection<T> collection, final Collection<T> datas) {
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
        }
    }
}
