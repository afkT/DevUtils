package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.Margins
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.simple.interfaces.BindingConsumer
import dev.utils.app.ClickUtils

// ================================
// = View Listener BindingAdapter =
// ================================

/**
 * [View] 点击、长按、触摸与触摸区域扩大的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_click_*`、`binding_long_click`、`binding_touch`、
 * `binding_touch_area_*`；实现主要对应 [dev.utils.app.ClickUtils] 中适合在单节点上表达的 API。
 * <pre>
 *     未封装全局 [ClickUtils.setCheckViewId]、[ClickUtils.setGlobalIntervalTime]、
 *     [ClickUtils.get] / [ClickUtils.remove]、[ClickUtils.isFastDoubleClick] 无 View 上下文配置、
 *     [ClickUtils.OnCountClickListener] / [ClickUtils.OnMultiClickListener] 等抽象监听与模块级辅助类操作。
 *     列表项带业务载荷的点击仍推荐在 item 布局中用 `android:onClick` 配合 [BindingConsumer]；
 *     本文件 `binding_click` 系列入参为 [BindingConsumer] 且泛型为 [View]，表示当前节点上的点击回调。
 *     需对「空点击监听」等同值多次触发时，使用 `binding_click_empty_ts`（判定同 [qualifiesBindingAction]）。
 * </pre>
 */

// ==========
// = 点击监听 =
// ==========

/**
 * 通过数据绑定设置单击监听。
 * <pre>
 *     布局属性 binding_click；`null` 时不修改；委托 [ClickUtils.setOnClick]。
 *     无防抖，每次点击均回调；需防抖见 [bindingClickDebounce]。
 * </pre>
 *
 * @param consumer 有效点击时回调当前 [View]
 */
@BindingAdapter("binding_click")
fun View.bindingClick(consumer: BindingConsumer<View>?) {
    if (consumer == null) return
    ClickUtils.setOnClick(
        this,
        View.OnClickListener { consumer.accept(it) },
    )
}

/**
 * 通过数据绑定设置防抖单击监听。
 * <pre>
 *     布局属性 binding_click_debounce、binding_click_debounce_check_view_id（requireAll 为 false）。
 *     委托 [ClickUtils.OnDebouncingClickListener] 与 [ClickUtils.setOnClick]；
 *     checkViewId 缺省为 true，与 [ClickUtils.setCheckViewId] 全局默认一致时按 viewId 区分连点。
 * </pre>
 *
 * @param consumer 有效（非连点）点击时回调当前 [View]
 * @param checkViewId 是否按 [View.getId] 参与防抖
 */
@BindingAdapter(
    value = [
        "binding_click_debounce",
        "binding_click_debounce_check_view_id",
    ],
    requireAll = false,
)
fun View.bindingClickDebounce(
    consumer: BindingConsumer<View>?,
    checkViewId: Boolean?,
) {
    if (consumer == null) return
    val checkId = checkViewId ?: true
    ClickUtils.setOnClick(
        this,
        object : ClickUtils.OnDebouncingClickListener(checkId) {
            override fun doClick(view: View) {
                consumer.accept(view)
            }
        },
    )
}

/**
 * 通过数据绑定设置空点击监听以消费点击事件。
 * <pre>
 *     布局属性 binding_click_empty；为 true 时委托 [ClickUtils.EMPTY_CLICK] 与 [ClickUtils.setOnClick]。
 *     若需同值多次触发，改用 binding_click_empty_ts 并绑定正时间戳。
 * </pre>
 *
 * @param apply 为 true 时应用空监听
 */
@BindingAdapter("binding_click_empty")
fun View.bindingClickEmpty(apply: Boolean?) {
    if (apply != true) return
    ClickUtils.setOnClick(this, ClickUtils.EMPTY_CLICK)
}

/**
 * 通过数据绑定以时间戳设置空点击监听。
 * <pre>
 *     布局属性 binding_click_empty_ts；判定同 [qualifiesBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 `System.currentTimeMillis` 或 ViewModel 内递增值
 */
@BindingAdapter("binding_click_empty_ts")
fun View.bindingClickEmptyTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    ClickUtils.setOnClick(this, ClickUtils.EMPTY_CLICK)
}

// ============
// = 长按与触摸 =
// ============

/**
 * 通过数据绑定设置长按监听。
 * <pre>
 *     布局属性 binding_long_click；`null` 时不修改；委托 [ClickUtils.setOnLongClick]。
 *     回调后固定向系统返回 `true` 表示已消费长按。
 * </pre>
 *
 * @param consumer 长按时回调当前 [View]
 */
@BindingAdapter("binding_long_click")
fun View.bindingLongClick(consumer: BindingConsumer<View>?) {
    if (consumer == null) return
    ClickUtils.setOnLongClick(
        this,
        View.OnLongClickListener {
            consumer.accept(it)
            true
        },
    )
}

/**
 * 通过数据绑定设置触摸监听。
 * <pre>
 *     布局属性 binding_touch；`null` 时不修改；委托 [ClickUtils.setOnTouch]。
 * </pre>
 *
 * @param listener [View.OnTouchListener]，返回值语义与系统一致
 */
@BindingAdapter("binding_touch")
fun View.bindingTouch(listener: View.OnTouchListener?) {
    if (listener == null) return
    ClickUtils.setOnTouch(this, listener)
}

// =============
// = 触摸区域扩大 =
// =============

/**
 * 通过数据绑定为四边统一扩大可点击区域。
 * <pre>
 *     布局属性 binding_touch_area_expand_px；`null` 时不修改；
 *     委托 [ClickUtils.addTouchArea]（四边同值）；扩大范围受父布局边界限制。
 * </pre>
 *
 * @param expandPx 四边扩大像素值
 */
@BindingAdapter("binding_touch_area_expand_px")
fun View.bindingTouchAreaExpandPx(expandPx: Int?) {
    if (expandPx == null) return
    ClickUtils.addTouchArea(this, expandPx)
}

/**
 * 通过数据绑定按四边分别扩大可点击区域。
 * <pre>
 *     布局属性 binding_touch_area_expand；[Margins] 为 `null` 时不修改；
 *     委托 [ClickUtils.addTouchArea]；字段为 left、top、right、bottom 扩大像素。
 * </pre>
 *
 * @param expand 四边扩大量封装
 */
@BindingAdapter("binding_touch_area_expand")
fun View.bindingTouchAreaExpand(expand: Margins?) {
    if (expand == null) return
    ClickUtils.addTouchArea(
        this,
        expand.left,
        expand.top,
        expand.right,
        expand.bottom,
    )
}