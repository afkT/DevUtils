package afkt.project.model.button

import dev.utils.common.StringUtils

/**
 * 批量处理 Button Item
 * @param name 前缀名
 * @param listener Item 点击事件
 * 减少时间及维护难度，统一进行循环批量处理
 */
private fun ButtonAdapterModel._batchItems(
    name: String,
    listener: ButtonClick = DEFAULT_CLICK
): ButtonAdapterModel {
    if (StringUtils.isEmpty(name)) return this
    val lists = mutableListOf<ButtonEnum>()
    ButtonEnum.entries.forEach { btn ->
        if (btn.name.startsWith(name, true)) {
            lists.add(btn)
        }
    }
    addAllAndClear(lists)
    // 设置点击事件
    setOnItemClick(listener)
    return this
}

// =============================
// = Button Adapter 数据模型转换 =
// =============================

// 首页模块入口
fun ButtonAdapterModel.convertItemsModule() = _batchItems("MODULE_")

// Lib Utils 列表入口
fun ButtonAdapterModel.convertItemsLibUtilsMain(
    listener: ButtonClick
) = _batchItems(
    "BTN_LIB_UTILS_", listener
)

// DevAssist Engine 列表入口
fun ButtonAdapterModel.convertItemsDevAssistEngineMain(
    listener: ButtonClick
) = _batchItems(
    "BTN_DEV_ASSIST_ENGINE_", listener
)

// DevWidget 列表入口
fun ButtonAdapterModel.convertItemsDevWidgetMain() = _batchItems(
    "BTN_DEV_WIDGET_"
)

// UI Effect 列表入口
fun ButtonAdapterModel.convertItemsUIEffectMain() = _batchItems(
    "BTN_UI_EFFECT_"
)

// Other Function 列表入口
fun ButtonAdapterModel.convertItemsOtherFunctionMain() = _batchItems(
    "BTN_OTHER_FUN_"
)