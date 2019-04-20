package dev.utils.app.share;

import android.content.Context;

import java.util.HashMap;

/**
 * detail: IPreference 持有类，内部返回实现类
 * @author Ttt
 */
class IPreferenceHolder {

    // HashMap 保存持有对象
    private static final HashMap<String, IPreference> hashMap = new HashMap<>();

    /**
     * 初始化
     * @param context
     */
    public static IPreference getPreference(final Context context) {
        // 判断是否为存在对应的持有类
        IPreference ipref = hashMap.get(null);
        // 判断是否为存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context);
        hashMap.put(null, ipref);
        return ipref;
    }

    /**
     * 初始化
     * @param context
     * @param fileName
     */
    public static IPreference getPreference(final Context context, final String fileName) {
        // 判断是否为存在对应的持有类
        IPreference ipref = hashMap.get(fileName);
        // 判断是否为存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context, fileName);
        hashMap.put(fileName, ipref);
        return ipref;
    }

    /**
     * 初始化
     * @param context
     * @param fileName
     * @param mode
     */
    public static IPreference getPreference(final Context context, final String fileName, final int mode) {
        String key = fileName + "_" + mode;
        // 判断是否为存在对应的持有类
        IPreference ipref = hashMap.get(key);
        // 判断是否为存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context, fileName, mode);
        hashMap.put(key, ipref);
        return ipref;
    }

}
