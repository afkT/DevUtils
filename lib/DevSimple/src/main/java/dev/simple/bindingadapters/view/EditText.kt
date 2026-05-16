package dev.simple.bindingadapters.view

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextWatcher
import android.text.method.KeyListener
import android.text.method.TransformationMethod
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.EtMaxLengthAndText
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.app.EditTextUtils

// ===========================
// = EditText BindingAdapter =
// ===========================

/**
 * [EditText] 的 Data Binding 适配集合。
 *
 * 布局属性统一为 `binding_et_*`；实现主要对应 [dev.utils.app.EditTextUtils] 中适合在单节点上表达的 API。
 * <pre>
 *     未封装 `get*` 系列、`getEditText`、多 `View…` / `EditText…` 变参批量 `setTexts`、
 *     `createDigitsKeyListener` 工厂与 [EditTextUtils.DevTextWatcher] 抽象类等。
 *     与 [TextView.kt] 的 `binding_tv_*` 并存时按控件语义择一，避免同一语义重复绑定。
 *     需对光标置顶/置底等仅接收者副作用多次触发时，使用 `_ts` 属性（判定同 [qualifiesBindingAction]）。
 * </pre>
 */

/** 键入预设：仅字母；与 `binding_et_key_listener_preset` 搭配。 */
const val BINDING_ET_KEY_LISTENER_PRESET_LETTERS = 1

/** 键入预设：仅数字；与 `binding_et_key_listener_preset` 搭配。 */
const val BINDING_ET_KEY_LISTENER_PRESET_NUMBERS = 2

/** 键入预设：字母与数字；与 `binding_et_key_listener_preset` 搭配。 */
const val BINDING_ET_KEY_LISTENER_PRESET_NUMBERS_AND_LETTERS = 3

// ============
// = 长度与输入 =
// ============

/**
 * 通过数据绑定设置最大输入长度。
 * <pre>
 *     布局属性 `binding_et_max_length`；`null` 时不修改；委托 [EditTextUtils.setMaxLength]。
 * </pre>
 *
 * @param maxLength 最大长度
 */
@BindingAdapter("binding_et_max_length")
fun EditText.bindingETMaxLength(maxLength: Int?) {
    if (maxLength == null) return
    EditTextUtils.setMaxLength(this, maxLength)
}

/**
 * 通过数据绑定同时设置最大长度与正文。
 * <pre>
 *     布局属性 `binding_et_max_length_and_text`；`null` 跳过；委托 [EditTextUtils.setMaxLengthAndText]。
 * </pre>
 *
 * @param value 长度与正文封装，可为 null
 */
@BindingAdapter("binding_et_max_length_and_text")
fun EditText.bindingETMaxLengthAndText(value: EtMaxLengthAndText?) {
    if (value == null) return
    EditTextUtils.setMaxLengthAndText(this, value.content, value.maxLength)
}

/**
 * 通过数据绑定设置输入类型。
 * <pre>
 *     布局属性 `binding_et_input_type`；`null` 时不修改；委托 [EditTextUtils.setInputType]。
 * </pre>
 *
 * @param type [android.text.InputType] 常量组合
 */
@BindingAdapter("binding_et_input_type")
fun EditText.bindingETInputType(type: Int?) {
    if (type == null) return
    EditTextUtils.setInputType(this, type)
}

/**
 * 通过数据绑定设置软键盘 IME 选项。
 * <pre>
 *     布局属性 `binding_et_ime_options`；`null` 时不修改；委托 [EditTextUtils.setImeOptions]。
 * </pre>
 *
 * @param imeOptions IME 标志组合
 */
@BindingAdapter("binding_et_ime_options")
fun EditText.bindingETImeOptions(imeOptions: Int?) {
    if (imeOptions == null) return
    EditTextUtils.setImeOptions(this, imeOptions)
}

/**
 * 通过数据绑定切换密码明文与密文显示。
 * <pre>
 *     布局属性 `binding_et_display_password`、`binding_et_display_password_cursor_bottom`（后者可选，未指定为 true）；
 *     `displayPassword` 为 null 时不修改；委托 [EditTextUtils.setTransformationMethod] 密码切换重载。
 * </pre>
 *
 * @param displayPassword 是否显示密码明文
 * @param cursorBottom 切换后是否将光标移到底部；false 时保持原光标位置
 */
@BindingAdapter(
    value = ["binding_et_display_password", "binding_et_display_password_cursor_bottom"],
    requireAll = false,
)
fun EditText.bindingETDisplayPassword(
    displayPassword: Boolean?,
    cursorBottom: Boolean?,
) {
    if (displayPassword == null) return
    EditTextUtils.setTransformationMethod(this, displayPassword, cursorBottom ?: true)
}

/**
 * 通过数据绑定设置自定义 [TransformationMethod]。
 * <pre>
 *     布局属性 `binding_et_transformation_method`；`null` 时不修改；委托 [EditTextUtils.setTransformationMethod]。
 * </pre>
 *
 * @param method 显示转换实例
 */
@BindingAdapter("binding_et_transformation_method")
fun EditText.bindingETTransformationMethod(method: TransformationMethod?) {
    if (method == null) return
    EditTextUtils.setTransformationMethod(this, method)
}

// ==============
// = KeyListener =
// ==============

/**
 * 通过数据绑定设置自定义键入监听器。
 * <pre>
 *     布局属性 `binding_et_key_listener`；`null` 时不修改；委托 [EditTextUtils.setKeyListener]。
 * </pre>
 *
 * @param listener 监听器实例（通常在代码中构造后传入绑定）
 */
@BindingAdapter("binding_et_key_listener")
fun EditText.bindingETKeyListener(listener: KeyListener?) {
    if (listener == null) return
    EditTextUtils.setKeyListener(this, listener)
}

/**
 * 通过数据绑定按允许字符集字符串限制键入。
 * <pre>
 *     布局属性 `binding_et_key_listener_digits`；`null` 时不修改；委托 [EditTextUtils.setKeyListener] 字符串重载。
 * </pre>
 *
 * @param accepted 允许输入的字符，如 `0123456789`
 */
@BindingAdapter("binding_et_key_listener_digits")
fun EditText.bindingETKeyListenerDigits(accepted: String?) {
    if (accepted == null) return
    EditTextUtils.setKeyListener(this, accepted)
}

/**
 * 通过数据绑定按预设套用 [android.text.method.DigitsKeyListener]。
 * <pre>
 *     布局属性 `binding_et_key_listener_preset`；取值为 [BINDING_ET_KEY_LISTENER_PRESET_LETTERS]（1）、
 *     [BINDING_ET_KEY_LISTENER_PRESET_NUMBERS]（2）、[BINDING_ET_KEY_LISTENER_PRESET_NUMBERS_AND_LETTERS]（3），其它值忽略；
 *     委托 [EditTextUtils.getLettersKeyListener]、[EditTextUtils.getNumberKeyListener]、
 *     [EditTextUtils.getNumberAndLettersKeyListener] 与 [EditTextUtils.setKeyListener]。
 * </pre>
 *
 * @param preset 预设类型
 */
@BindingAdapter("binding_et_key_listener_preset")
fun EditText.bindingETKeyListenerPreset(preset: Int?) {
    if (preset == null) return
    when (preset) {
        BINDING_ET_KEY_LISTENER_PRESET_LETTERS ->
            EditTextUtils.setKeyListener(this, EditTextUtils.getLettersKeyListener())

        BINDING_ET_KEY_LISTENER_PRESET_NUMBERS ->
            EditTextUtils.setKeyListener(this, EditTextUtils.getNumberKeyListener())

        BINDING_ET_KEY_LISTENER_PRESET_NUMBERS_AND_LETTERS ->
            EditTextUtils.setKeyListener(this, EditTextUtils.getNumberAndLettersKeyListener())

        else -> Unit
    }
}

// ============
// = 文本与插入 =
// ============

/**
 * 通过数据绑定设置文本及是否将光标移到末尾。
 * <pre>
 *     布局属性 `binding_et_text`、`binding_et_text_select_to_end`（后者可选，未指定时按 true）；
 *     委托 [EditTextUtils.setText]；`content` 为 null 时按空串写入。
 * </pre>
 *
 * @param content 文本
 * @param selectToEnd 是否在设置后将光标置于文末
 */
@BindingAdapter(
    value = ["binding_et_text", "binding_et_text_select_to_end"],
    requireAll = false,
)
fun EditText.bindingETText(
    content: CharSequence?,
    selectToEnd: Boolean?,
) {
    EditTextUtils.setText(this, content ?: "", selectToEnd ?: true)
}

/**
 * 通过数据绑定以时间戳清空输入框文本。
 * <pre>
 *     布局属性 `binding_et_clear_text_ts`；判定同 [qualifiesBindingAction]；
 *     与 `binding_et_text` 可并存；每次正时间戳写入空串并将光标置于文末。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_et_clear_text_ts")
fun EditText.bindingETClearTextTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    EditTextUtils.setText(this, "", true)
}

/**
 * 通过数据绑定在光标处或指定下标插入文本。
 * <pre>
 *     布局属性 `binding_et_insert_text`、`binding_et_insert_at`（可选，未指定则在当前 selection 插入）、
 *     `binding_et_insert_select_to_end`（可选，未指定为 true）；委托 [EditTextUtils.insert]。
 * </pre>
 *
 * @param text 待插入内容，空则忽略
 * @param at 插入起始下标，null 表示使用当前光标
 * @param selectToEnd 插入后是否将光标移到文末
 */
@BindingAdapter(
    value = [
        "binding_et_insert_text",
        "binding_et_insert_at",
        "binding_et_insert_select_to_end",
    ],
    requireAll = false,
)
fun EditText.bindingETInsert(
    text: CharSequence?,
    at: Int?,
    selectToEnd: Boolean?,
) {
    if (text.isNullOrEmpty()) return
    val move = selectToEnd ?: true
    if (at != null) {
        EditTextUtils.insert(this, text, at, move)
    } else {
        EditTextUtils.insert(this, text, move)
    }
}

// =======
// = 光标 =
// =======

/**
 * 通过数据绑定设置是否显示光标。
 * <pre>
 *     布局属性 `binding_et_cursor_visible`；`null` 时不修改；委托 [EditTextUtils.setCursorVisible]。
 * </pre>
 *
 * @param visible 是否显示光标
 */
@BindingAdapter("binding_et_cursor_visible")
fun EditText.bindingETCursorVisible(visible: Boolean?) {
    if (visible == null) return
    EditTextUtils.setCursorVisible(this, visible)
}

/**
 * 通过数据绑定按资源 ID 设置文本光标 Drawable。
 * <pre>
 *     布局属性 `binding_et_text_cursor_drawable_res`；API 29 以下忽略；`null` 时不修改；
 *     委托 [EditTextUtils.setTextCursorDrawable] 资源重载。
 * </pre>
 *
 * @param resId 光标 drawable 资源 ID
 */
@BindingAdapter("binding_et_text_cursor_drawable_res")
fun EditText.bindingETTextCursorDrawableRes(@DrawableRes resId: Int?) {
    if (resId == null) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
    EditTextUtils.setTextCursorDrawable(this, resId)
}

/**
 * 通过数据绑定设置文本光标 Drawable。
 * <pre>
 *     布局属性 `binding_et_text_cursor_drawable`；API 29 以下忽略；`null` 时不修改；
 *     委托 [EditTextUtils.setTextCursorDrawable] Drawable 重载。
 * </pre>
 *
 * @param drawable 光标 Drawable
 */
@BindingAdapter("binding_et_text_cursor_drawable")
fun EditText.bindingETTextCursorDrawable(drawable: Drawable?) {
    if (drawable == null) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
    EditTextUtils.setTextCursorDrawable(this, drawable)
}

/**
 * 通过数据绑定设置光标位置。
 * <pre>
 *     布局属性 `binding_et_selection`；`null` 时不修改；委托 [EditTextUtils.setSelection]。
 * </pre>
 *
 * @param index 光标下标
 */
@BindingAdapter("binding_et_selection")
fun EditText.bindingETSelection(index: Int?) {
    if (index == null) return
    EditTextUtils.setSelection(this, index)
}

/**
 * 通过数据绑定以时间戳将光标置于首位。
 * <pre>
 *     布局属性 `binding_et_selection_to_top_ts`；判定同 [qualifiesBindingAction]；委托 [EditTextUtils.setSelectionToTop]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_et_selection_to_top_ts")
fun EditText.bindingETSelectionToTopTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    EditTextUtils.setSelectionToTop(this)
}

/**
 * 通过数据绑定以时间戳将光标置于文末。
 * <pre>
 *     布局属性 `binding_et_selection_to_bottom_ts`；判定同 [qualifiesBindingAction]；委托 [EditTextUtils.setSelectionToBottom]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_et_selection_to_bottom_ts")
fun EditText.bindingETSelectionToBottomTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    EditTextUtils.setSelectionToBottom(this)
}

// ==============
// = TextWatcher =
// ==============

/**
 * 通过数据绑定添加 [TextWatcher]。
 * <pre>
 *     布局属性 `binding_et_add_text_watcher`；`null` 时不修改；委托 [EditTextUtils.addTextChangedListener]。
 *     宜在代码中持有同一 watcher 实例，避免重复绑定导致多次注册。
 * </pre>
 *
 * @param watcher 输入监听器
 */
@BindingAdapter("binding_et_add_text_watcher")
fun EditText.bindingETAddTextWatcher(watcher: TextWatcher?) {
    if (watcher == null) return
    EditTextUtils.addTextChangedListener(this, watcher)
}

/**
 * 通过数据绑定移除 [TextWatcher]。
 * <pre>
 *     布局属性 `binding_et_remove_text_watcher`；`null` 时不修改；委托 [EditTextUtils.removeTextChangedListener]。
 * </pre>
 *
 * @param watcher 待移除的监听器，须与添加时为同一实例
 */
@BindingAdapter("binding_et_remove_text_watcher")
fun EditText.bindingETRemoveTextWatcher(watcher: TextWatcher?) {
    if (watcher == null) return
    EditTextUtils.removeTextChangedListener(this, watcher)
}