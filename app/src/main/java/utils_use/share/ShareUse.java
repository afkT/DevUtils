package utils_use.share;

import android.content.Context;

import dev.utils.app.share.SPUtils;
import dev.utils.app.share.SharedUtils;

/**
 * detail: SharedPreferences 使用方法
 * @author Ttt
 */
public final class ShareUse {

    private ShareUse() {
    }

    public static void shareUse(Context context) {
        // 具体实现方法 基于 PreferenceImpl 实现

        // 存在可调用的方法 IPreference

        // SharedUtils 二次分装 SPUtils, 快捷调用

        SharedUtils.put("aa", "aa");
        SharedUtils.put("ac", 123);

        // ===========
        // = SPUtils =
        // ===========

        // 想要自定义 模式, 名字等
        SPUtils.getPreference(context).put("aa", 1);
        SPUtils.getPreference(context, "xxx").put("aa", 1);
        SPUtils.getPreference(context, "xxxxx", Context.MODE_PRIVATE).put("aa", 1);

//        // 默认值如下
//        switch (type) {
//            case INTEGER:
//                return preferences.getInt(key, -1);
//            case FLOAT:
//                return preferences.getFloat(key, -1F);
//            case BOOLEAN:
//                return preferences.getBoolean(key, false);
//            case LONG:
//                return preferences.getLong(key, -1L);
//            case STRING:
//                return preferences.getString(key, null);
//            case STRING_SET:
//                return preferences.getStringSet(key, null);
//            default: // 默认取出 String 类型的数据
//                return null;
//        }
    }
}