package dev.utils.app.share;

import android.content.Context;

import java.util.HashMap;

/**
 * detail: IPreference 持有类，内部返回实现类
 * Created by Ttt
 */
class IPreferenceHolder {

    /** HashMap 保存持有对象 */
    private static final HashMap<String, IPreference> hashMap = new HashMap<>();

    /**
     * 初始化
     * @param context
     */
    public static IPreference getPreference(Context context) {
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
     * @param fName
     */
    public static IPreference getPreference(Context context, String fName) {
        // 判断是否为存在对应的持有类
        IPreference ipref = hashMap.get(fName);
        // 判断是否为存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context, fName);
        hashMap.put(fName, ipref);
        return ipref;
    }

    /**
     * 初始化
     * @param context
     * @param fName
     * @param mode
     */
    public static IPreference getPreference(Context context, String fName, int mode) {
        String key = fName + "_" + mode;
        // 判断是否为存在对应的持有类
        IPreference ipref = hashMap.get(key);
        // 判断是否为存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context, fName, mode);
        hashMap.put(key, ipref);
        return ipref;
    }

}
