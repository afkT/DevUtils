package afkt.project.base.project

import afkt.project.model.data.button.ButtonValue
import afkt.project.model.adapter.ButtonAdapter
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.therouter.TheRouter
import dev.DevUtils
import dev.callback.DevItemClickCallback
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

// ============
// = 适配器相关 =
// ============

/**
 * 初始化布局管理器、适配器
 * @receiver RecyclerView
 * @param buttons MutableList<ButtonValue>
 */
fun RecyclerView.bindAdapter(
    buttons: List<ButtonValue>,
    callback: ((ButtonValue) -> Unit) = {
        it.routerActivity()
    }
) {
    bindAdapterLong(buttons, callback) { _ -> }
}

/**
 * 初始化布局管理器、适配器
 * @receiver RecyclerView
 * @param buttons MutableList<ButtonValue>
 */
fun RecyclerView.bindAdapterLong(
    buttons: List<ButtonValue>,
    callback: ((ButtonValue) -> Unit) = {
        it.routerActivity()
    },
    longCallback: ((ButtonValue) -> Unit) = {
    }
) {
    ButtonAdapter(buttons)
        .setItemCallback(object : DevItemClickCallback<ButtonValue>() {
            override fun onItemClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
                callback.invoke(buttonValue)
            }

            override fun onItemLongClick(
                buttonValue: ButtonValue,
                param: Int
            ) {
                longCallback.invoke(buttonValue)
            }
        }).bindAdapter(this)
}