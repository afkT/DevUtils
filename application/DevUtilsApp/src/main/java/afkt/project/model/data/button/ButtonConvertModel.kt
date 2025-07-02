package afkt.project.model.data.button

import afkt.project.model.adapter.ButtonAdapterModel
import dev.utils.common.StringUtils

/**
 * 批量处理 Button Item
 * @param name 前缀名
 * 减少时间及维护难度，统一进行循环批量处理
 */
private fun ButtonAdapterModel._batchItems(name: String): ButtonAdapterModel {
    if (StringUtils.isEmpty(name)) return this
    val lists = mutableListOf<ButtonEnum>()
    ButtonEnum.entries.forEach { btn ->
        if (btn.name.startsWith(name, true)) {
            lists.add(btn)
        }
    }
    addAllAndClear(lists)
    return this
}

// =============================
// = Button Adapter 数据模型转换 =
// =============================

// 首页模块入口
fun ButtonAdapterModel.convertItemsModule() = _batchItems("MODULE_")

// Lib Utils 列表
fun ButtonAdapterModel.convertItemsLib() = _batchItems("BTN_LIB_")

// DevAssist Engine 实现
fun ButtonAdapterModel.convertItemsEngine() = _batchItems("BTN_ENGINE_")