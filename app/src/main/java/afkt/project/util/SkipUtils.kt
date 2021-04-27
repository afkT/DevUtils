package afkt.project.util

import afkt.project.model.item.ButtonValue
import android.content.Intent
import dev.DevUtils
import dev.utils.DevFinal
import dev.utils.app.AppUtils

/**
 * detail: 跳转 工具类
 * @author Ttt
 */
object SkipUtils {

    /**
     * 跳转方法
     * @param clazz       跳转
     * @param buttonValue 按钮参数
     * @return `true` success, `false` fail
     */
    @JvmStatic
    fun startActivity(
        clazz: Class<*>?,
        buttonValue: ButtonValue
    ): Boolean {
        val intent = Intent(DevUtils.getContext(), clazz)
        intent.putExtra(DevFinal.TYPE, buttonValue.type)
        intent.putExtra(DevFinal.TITLE, buttonValue.text)
        return AppUtils.startActivity(intent)
    }
}