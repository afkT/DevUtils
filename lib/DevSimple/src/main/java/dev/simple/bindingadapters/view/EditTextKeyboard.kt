package dev.simple.bindingadapters.view

import android.widget.EditText
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.EtKeyboardOpenDelayAt
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.app.KeyBoardUtils

// ===================================
// = EditText Keyboard BindingAdapter =
// ===================================

/**
 * [EditText] 软键盘（[KeyBoardUtils]）的 Data Binding 适配集合。
 *
 * 布局属性为 `binding_et_open_keyboard_ts`、`binding_et_open_keyboard_delay`、`binding_et_close_keyboard_ts`；
 * 实现对应 [KeyBoardUtils] 中 **以当前 [EditText] 为接收者** 的打开/关闭 API。
 * <pre>
 *     未封装 `openKeyboard()` / `closeKeyboard()` 无参、`Activity` / `Dialog` 作用域、
 *     `setSoftInputMode`、`registerSoftInputListener*`、`toggleKeyboard` 等 Window 级或全局操作。
 *     与 [EditTextView] 的 `binding_et_text`、`binding_et_clear_text_ts` 等可并存；打开键盘前可在 VM 侧先设焦点或文案。
 *     需多次打开/关闭时使用 `_ts` 或 [EtKeyboardOpenDelayAt]（判定同 [qualifiesBindingAction]）。
 * </pre>
 */

// ==========
// = 打开键盘 =
// ==========

/**
 * 通过数据绑定以时间戳打开软键盘。
 * <pre>
 *     布局属性 `binding_et_open_keyboard_ts`；判定同 [qualifiesBindingAction]；
 *     委托 [KeyBoardUtils.openKeyboard]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_et_open_keyboard_ts")
fun EditText.bindingETOpenKeyboardTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    KeyBoardUtils.openKeyboard(this)
}

/**
 * 通过数据绑定延时打开软键盘。
 * <pre>
 *     布局属性 `binding_et_open_keyboard_delay`；`payload` 为 null 或时间戳无效时跳过；
 *     委托 [KeyBoardUtils.openKeyboardDelay]（含 requestFocus 与光标置末）。
 * </pre>
 *
 * @param payload 延时打开命令封装，可为 null
 */
@BindingAdapter("binding_et_open_keyboard_delay")
fun EditText.bindingETOpenKeyboardDelay(payload: EtKeyboardOpenDelayAt?) {
    val p = payload ?: return
    if (!p.timestamp.qualifiesBindingAction()) return
    KeyBoardUtils.openKeyboardDelay(this, p.delayMillis)
}

// ==========
// = 关闭键盘 =
// ==========

/**
 * 通过数据绑定以时间戳关闭软键盘。
 * <pre>
 *     布局属性 `binding_et_close_keyboard_ts`；判定同 [qualifiesBindingAction]；
 *     委托 [KeyBoardUtils.closeKeyboard]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_et_close_keyboard_ts")
fun EditText.bindingETCloseKeyboardTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    KeyBoardUtils.closeKeyboard(this)
}