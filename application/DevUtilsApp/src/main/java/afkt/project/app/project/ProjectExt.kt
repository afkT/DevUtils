package afkt.project.app.project

import afkt.project.model.data.button.ButtonAdapter
import afkt.project.model.data.button.ButtonValue
import androidx.recyclerview.widget.RecyclerView
import dev.callback.DevItemClickCallback

// ==========
// = 跳转通用 =
// ==========

/**
 * Router 跳转方法
 */
fun ButtonValue.routerActivity() {
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