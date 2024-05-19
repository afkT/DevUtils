package afkt.project.base.project.ext

import afkt.project.data_model.button.ButtonValue
import android.content.Intent
import com.therouter.TheRouter
import dev.DevUtils
import dev.utils.DevFinal
import dev.utils.app.AppUtils


// ==========
// = 跳转通用 =
// ==========

/**
 * Router 跳转方法
 */
fun ButtonValue.routerActivity() {
    TheRouter.build(this.path)
        .withInt(DevFinal.STR.TYPE, this.type)
        .withString(DevFinal.STR.TITLE, this.text)
        .navigation(DevUtils.getContext())
}

/**
 * 跳转方法
 * @param clazz       跳转
 * @return `true` success, `false` fail
 */
fun ButtonValue.classStartActivity(clazz: Class<*>?): Boolean {
    val intent = Intent(DevUtils.getContext(), clazz)
    intent.putExtra(DevFinal.STR.TYPE, this.type)
    intent.putExtra(DevFinal.STR.TITLE, this.text)
    return AppUtils.startActivity(intent)
}