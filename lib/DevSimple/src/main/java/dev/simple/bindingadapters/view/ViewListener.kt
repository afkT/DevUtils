package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.Margins
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.simple.interfaces.BindingConsumer
import dev.utils.app.ClickUtils
import dev.utils.app.ClickUtils.*

// ================================
// = View Listener BindingAdapter =
// ================================

/**
 * View 点击、长按、触摸与触摸区域扩大的 Data Binding 适配集合。
 * <pre>
 *     布局属性前缀为 binding_click_*、binding_long_click、binding_touch、
 *     binding_touch_area_*；实现主要对应 [dev.utils.app.ClickUtils] 中适合在单节点上表达的 API。
 *     未封装全局 [ClickUtils.setCheckViewId]、[ClickUtils.setGlobalIntervalTime]、
 *     [ClickUtils.get] / [ClickUtils.remove]、[ClickUtils.isFastDoubleClick] 无 View 上下文配置等模块级操作。
 *     自定义防抖/计数/多次点击监听见 binding_click_debounce_listener、
 *     binding_click_count_listener、binding_click_multi_listener。
 *     列表项带业务载荷的点击仍推荐在 item 布局中用 `android:onClick` 配合 [BindingConsumer]；
 *     本文件 binding_click 系列入参为 [BindingConsumer] 且泛型为 View，表示当前节点上的点击回调。
 *     需对「空点击监听」等同值多次触发时，使用 binding_click_empty_ts（判定同 [qualifiesBindingAction]）。
 * </pre>
 */

// ==========
// = 点击监听 =
// ==========

/**
 * 通过数据绑定设置带防抖的点击回调
 * <pre>
 *     对应布局属性 binding_click、binding_click_interval，requireAll 为 false。
 *     intervalTime 大于 0 时通过 [ClickUtils.isFastDoubleClick] 防抖；consumer 为空时不设置监听。
 * </pre>
 *
 * @param consumer 有效点击时回调当前视图
 * @param intervalTime 防抖间隔毫秒，小于等于 0 时不防抖
 */
@BindingAdapter(
    value = ["binding_click", "binding_click_interval"],
    requireAll = false
)
fun View.bindingClick(
    consumer: BindingConsumer<View>?,
    intervalTime: Long
) {
    if (consumer == null) return
    ClickUtils.setOnClick(this) {
        if (intervalTime > 0L && ClickUtils.isFastDoubleClick(this, intervalTime)) {
            return@setOnClick
        }
        consumer.accept(it)
    }
}

/**
 * 通过数据绑定设置带防抖辅助类的点击回调
 * <pre>
 *     对应布局属性 binding_click、binding_click_assist，requireAll 为 false。
 *     assist 非 null 时通过 [ClickAssist.isFastDoubleClick] 无参防抖；consumer 为空时不设置监听。
 * </pre>
 *
 * @param consumer 有效点击时回调当前视图
 * @param assist 防抖辅助类，为 null 时不防抖
 */
@BindingAdapter(
    value = ["binding_click", "binding_click_assist"],
    requireAll = false
)
fun View.bindingClickAssist(
    consumer: BindingConsumer<View>?,
    assist: ClickUtils.ClickAssist?
) {
    if (consumer == null) return
    ClickUtils.setOnClick(this) {
        if (assist != null && assist.isFastDoubleClick) {
            return@setOnClick
        }
        consumer.accept(it)
    }
}

/**
 * 通过数据绑定设置防抖单击监听
 * <pre>
 *     布局属性 binding_click_debounce、binding_click_debounce_check_view_id（requireAll 为 false）。
 *     委托 [ClickUtils.OnDebouncingClickListener] 与 [ClickUtils.setOnClick]；
 *     checkViewId 缺省为 true，与 [ClickUtils.setCheckViewId] 全局默认一致时按 viewId 区分连点。
 * </pre>
 *
 * @param consumer 有效（非连点）点击时回调当前视图
 * @param checkViewId 是否按视图 id 参与防抖
 */
@BindingAdapter(
    value = [
        "binding_click_debounce",
        "binding_click_debounce_check_view_id",
    ],
    requireAll = false
)
fun View.bindingClickDebounce(
    consumer: BindingConsumer<View>?,
    checkViewId: Boolean?
) {
    if (consumer == null) return
    val checkId = checkViewId ?: true
    ClickUtils.setOnClick(
        this, object : ClickUtils.OnDebouncingClickListener(checkId) {
            override fun doClick(view: View) {
                consumer.accept(view)
            }
        }
    )
}

/**
 * 通过数据绑定设置自定义防抖单击监听
 * <pre>
 *     布局属性 binding_click_debounce_listener；为 null 时不设置监听。
 *     委托 [ClickUtils.setOnClick]；可在监听内自定义 [ClickAssist]、checkViewId 及 [OnDebouncingClickListener.doInvalidClick]。
 * </pre>
 *
 * @param listener 防抖点击监听，由调用方构造并绑定
 */
@BindingAdapter("binding_click_debounce_listener")
fun View.bindingClickDebounceListener(
    listener: ClickUtils.OnDebouncingClickListener?
) {
    if (listener == null) return
    ClickUtils.setOnClick(this, listener)
}

/**
 * 通过数据绑定设置自定义计数点击监听
 * <pre>
 *     布局属性 binding_click_count_listener；为 null 时不设置监听。
 *     委托 [ClickUtils.setOnClick]；可在监听内自定义 [ClickAssist] 及有效/无效点击回调。
 * </pre>
 *
 * @param listener 计数点击监听，由调用方构造并绑定
 */
@BindingAdapter("binding_click_count_listener")
fun View.bindingClickCountListener(
    listener: ClickUtils.OnCountClickListener?
) {
    if (listener == null) return
    ClickUtils.setOnClick(this, listener)
}

/**
 * 通过数据绑定设置自定义多次点击监听
 * <pre>
 *     布局属性 binding_click_multi_listener；为 null 时不设置监听。
 *     委托 [ClickUtils.setOnClick]；连点达指定次数后触发 [OnMultiClickListener.doMultiClick]。
 * </pre>
 *
 * @param listener 多次点击监听，由调用方构造并绑定
 */
@BindingAdapter("binding_click_multi_listener")
fun View.bindingClickMultiListener(
    listener: ClickUtils.OnMultiClickListener?
) {
    if (listener == null) return
    ClickUtils.setOnClick(this, listener)
}

/**
 * 通过数据绑定设置空点击监听以消费点击事件
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
 * 通过数据绑定以时间戳设置空点击监听
 * <pre>
 *     布局属性 binding_click_empty_ts；判定同 [qualifiesBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 System.currentTimeMillis 或 ViewModel 内递增值
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
 * 通过数据绑定设置长按监听
 * <pre>
 *     布局属性 binding_long_click；为 null 时不修改；委托 [ClickUtils.setOnLongClick]。
 *     回调后固定向系统返回 true 表示已消费长按。
 * </pre>
 *
 * @param consumer 长按时回调当前视图
 */
@BindingAdapter("binding_long_click")
fun View.bindingLongClick(consumer: BindingConsumer<View>?) {
    if (consumer == null) return
    ClickUtils.setOnLongClick(
        this,
        View.OnLongClickListener {
            consumer.accept(it)
            true
        }
    )
}

/**
 * 通过数据绑定设置触摸监听
 * <pre>
 *     布局属性 binding_touch；为 null 时不修改；委托 [ClickUtils.setOnTouch]。
 *     返回值语义与系统 OnTouchListener 一致。
 * </pre>
 *
 * @param listener 触摸监听器
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
 * 通过数据绑定为四边统一扩大可点击区域
 * <pre>
 *     布局属性 binding_touch_area_expand_px；为 null 时不修改；
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
 * 通过数据绑定按四边分别扩大可点击区域
 * <pre>
 *     布局属性 binding_touch_area_expand；expand 为 null 时不修改；
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
        expand.bottom
    )
}