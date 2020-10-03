package afkt.project.util;

import android.content.Intent;

import afkt.project.base.constants.KeyConstants;
import afkt.project.model.item.ButtonValue;
import dev.DevUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 跳转 工具类
 * @author Ttt
 */
public final class SkipUtils {

    private SkipUtils() {
    }

    /**
     * 跳转方法
     * @param clazz       跳转
     * @param buttonValue 按钮参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startActivity(Class clazz, ButtonValue buttonValue) {
        Intent intent = new Intent(DevUtils.getContext(), clazz);
        intent.putExtra(KeyConstants.Common.KEY_TYPE, buttonValue.type);
        intent.putExtra(KeyConstants.Common.KEY_TITLE, buttonValue.text);
        return AppUtils.startActivity(intent);
    }
}