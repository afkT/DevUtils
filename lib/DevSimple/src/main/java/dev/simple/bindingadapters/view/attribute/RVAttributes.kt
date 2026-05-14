package dev.simple.bindingadapters.view.attribute

import androidx.recyclerview.widget.RecyclerView

/**
 * 单次绑定中同时携带触发时间戳与 adapter 索引，用于 item 级通知类操作。
 * <pre>
 *     配合 `binding_rv_notify_item_removed`、`binding_rv_notify_item_inserted` 等 RecyclerView 绑定扩展；
 *     时间戳判定与 `shouldTriggerBindingAction` 一致，便于在 ViewModel 中多次触发同索引命令。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行底层通知
 * @property position 目标 adapter 索引
 */
data class RvAdapterNotifyItemAt(
    val timestamp: Long,
    val position: Int,
)

/**
 * 单次绑定中同时携带触发时间戳与移动起止索引，用于 notifyItemMoved。
 * <pre>
 *     配合 binding_rv_notify_item_moved；委托 [dev.utils.app.RecyclerViewUtils.notifyItemMoved]。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property fromPosition 当前索引
 * @property toPosition 目标索引
 */
data class RvAdapterNotifyItemMovedAt(
    val timestamp: Long,
    val fromPosition: Int,
    val toPosition: Int,
)

/**
 * 单次绑定中携带待添加的 ItemDecoration 与可选插入下标。
 * <pre>
 *     配合 binding_rv_item_decoration_add；index 为 null 时走无下标重载，非 null 时走带 index 的 `RecyclerView.addItemDecoration`。
 * </pre>
 *
 * @property decor 待添加的装饰实例
 * @property index 插入位置，为 null 时追加到末尾语义由系统实现决定
 */
data class RvItemDecorationAddOp(
    val decor: RecyclerView.ItemDecoration,
    val index: Int? = null,
)