package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import dev.utils.JCLogUtils;

/**
 * detail: 克隆相关工具类
 * Created by Ttt
 */
public final class CloneUtils {

    private CloneUtils() {
    }

    // 日志TAG
    private static final String TAG = CloneUtils.class.getSimpleName();

    /**
     * 进行克隆
     * @param data
     * @param <T>
     * @return
     */
    public static <T> T deepClone(final Serializable data) {
        if (data == null) return null;
        return (T) bytes2Object(serializable2Bytes(data));
    }

    /**
     * 通过序列化实体类, 获取对应的byte数组数据
     * @param serializable
     * @return
     */
    private static byte[] serializable2Bytes(final Serializable serializable) {
        if (serializable == null) return null;
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
            oos.writeObject(serializable);
            return baos.toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "serializable2Bytes");
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
     * 通过 byte数据, 生成Object对象
     * @param bytes
     * @return
     */
    private static Object bytes2Object(final byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytes2Object");
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
}
