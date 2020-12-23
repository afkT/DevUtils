package dev.utils.app.share;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * detail: IPreference 持有类, 内部返回实现类
 * @author Ttt
 */
class IPreferenceHolder {

    protected IPreferenceHolder() {
    }

    // HashMap 保存持有对象
    private static final Map<String, IPreference> sHashMaps = new HashMap<>();

    /**
     * 获取 IPreference (SharedPreferences) 操作接口
     * @param context {@link Context}
     * @return {@link IPreference}
     */
    public static IPreference getPreference(final Context context) {
        // 判断是否存在对应的持有类
        IPreference ipref = sHashMaps.get(null);
        // 判断是否存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context);
        sHashMaps.put(null, ipref);
        return ipref;
    }

    /**
     * 获取 IPreference (SharedPreferences) 操作接口
     * @param context  {@link Context}
     * @param fileName 文件名
     * @return {@link IPreference}
     */
    public static IPreference getPreference(
            final Context context,
            final String fileName
    ) {
        // 判断是否存在对应的持有类
        IPreference ipref = sHashMaps.get(fileName);
        // 判断是否存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context, fileName);
        sHashMaps.put(fileName, ipref);
        return ipref;
    }

    /**
     * 获取 IPreference (SharedPreferences) 操作接口
     * @param context  {@link Context}
     * @param fileName 文件名
     * @param mode     SharedPreferences 操作模式
     * @return {@link IPreference}
     */
    public static IPreference getPreference(
            final Context context,
            final String fileName,
            final int mode
    ) {
        String key = fileName + "_" + mode;
        // 判断是否存在对应的持有类
        IPreference ipref = sHashMaps.get(key);
        // 判断是否存在
        if (ipref != null) {
            return ipref;
        }
        // 初始化并保存
        ipref = new PreferenceImpl(context, fileName, mode);
        sHashMaps.put(key, ipref);
        return ipref;
    }
}