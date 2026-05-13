package dev.simple.bindingadapters.view

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.method.KeyListener
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import dev.utils.LogPrintUtils
import dev.utils.app.EditTextUtils

// =============================
// = EditText BindingAdapter =
// =============================

private const val TAG = "Dev_EditText_BindingAdapter"

/** 键入预设取值：仅字母；与布局属性 binding_et_key_listener_preset 搭配。 */
const val BINDING_ET_KEY_LISTENER_PRESET_LETTERS = 1

/** 键入预设取值：仅数字；与布局属性 binding_et_key_listener_preset 搭配。 */
const val BINDING_ET_KEY_LISTENER_PRESET_NUMBERS = 2

/** 键入预设取值：字母与数字；与布局属性 binding_et_key_listener_preset 搭配。 */
const val BINDING_ET_KEY_LISTENER_PRESET_NUMBERS_AND_LETTERS = 3

// =============
// = 常用输入 =
// =============

/**
 * 通过数据绑定设置最大输入长度。
 * <pre>
 *     布局属性 binding_et_max_length；内部委托 EditTextUtils.setMaxLength。
 * </pre>
 *
 * @param maxLength 最大长度
 */
@BindingAdapter("binding_et_max_length")
fun EditText.bindingETMaxLength(maxLength: Int) {
    EditTextUtils.setMaxLength(this, maxLength)
}

/**
 * 通过数据绑定设置输入类型。
 * <pre>
 *     布局属性 binding_et_input_type；内部委托 EditTextUtils.setInputType。
 * </pre>
 *
 * @param type InputType 常量组合
 */
@BindingAdapter("binding_et_input_type")
fun EditText.bindingETInputType(type: Int) {
    EditTextUtils.setInputType(this, type)
}

/**
 * 通过数据绑定设置输入法动作选项。
 * <pre>
 *     布局属性 binding_et_ime_options；内部委托 EditTextUtils.setImeOptions。
 * </pre>
 *
 * @param imeOptions ImeOptions 标志组合
 */
@BindingAdapter("binding_et_ime_options")
fun EditText.bindingETImeOptions(imeOptions: Int) {
    EditTextUtils.setImeOptions(this, imeOptions)
}

/**
 * 通过数据绑定切换密码明文与密文显示。
 * <pre>
 *     布局属性 binding_et_display_password、binding_et_display_password_cursor_bottom（后者可选，未指定为 true）；
 *     内部委托 EditTextUtils.setTransformationMethod。
 * </pre>
 *
 * @param displayPassword 是否显示密码明文
 * @param cursorBottom 切换后是否将光标移到底部；false 时保持原光标位置
 */
@BindingAdapter(
    value = ["binding_et_display_password", "binding_et_display_password_cursor_bottom"],
    requireAll = false
)
fun EditText.bindingETDisplayPassword(
    displayPassword: Boolean,
    cursorBottom: Boolean?
) {
    EditTextUtils.setTransformationMethod(this, displayPassword, cursorBottom ?: true)
}

/**
 * 通过数据绑定设置自定义键入监听器。
 * <pre>
 *     布局属性 binding_et_key_listener；null 时不修改；内部委托 EditTextUtils.setKeyListener。
 *     入参类型见 [KeyListener]。
 * </pre>
 *
 * @param listener 监听器实例（通常在代码中构造后传入绑定）
 */
@BindingAdapter("binding_et_key_listener")
fun EditText.bindingETKeyListener(listener: KeyListener?) {
    if (listener != null) {
        EditTextUtils.setKeyListener(this, listener)
    }
}

/**
 * 通过数据绑定按预设套用数字键入监听器。
 * <pre>
 *     布局属性 binding_et_key_listener_preset；取值为 BINDING_ET_KEY_LISTENER_PRESET_LETTERS（1）、
 *     BINDING_ET_KEY_LISTENER_PRESET_NUMBERS（2）、BINDING_ET_KEY_LISTENER_PRESET_NUMBERS_AND_LETTERS（3），其它值忽略；
 *     类型见 [android.text.method.DigitsKeyListener]；内部委托 EditTextUtils.getLettersKeyListener、
 *     getNumberKeyListener、getNumberAndLettersKeyListener 与 setKeyListener。
 * </pre>
 *
 * @param preset 预设类型
 */
@BindingAdapter("binding_et_key_listener_preset")
fun EditText.bindingETKeyListenerPreset(preset: Int) {
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

// =============
// = 文本与插入 =
// =============

/**
 * 通过数据绑定设置文本及是否将光标移到末尾。
 * <pre>
 *     布局属性 binding_et_text、binding_et_text_select_to_end（后者可选，未指定时按 true）；内部委托 EditTextUtils.setText。
 *     content 为 null 时按空串写入。
 * </pre>
 *
 * @param content 文本
 * @param selectToEnd 是否在设置后将光标置于文末
 */
@BindingAdapter(
    value = ["binding_et_text", "binding_et_text_select_to_end"],
    requireAll = false
)
fun EditText.bindingETText(
    content: CharSequence?,
    selectToEnd: Boolean?
) {
    EditTextUtils.setText(this, content ?: "", selectToEnd ?: true)
}

/**
 * 通过数据绑定在光标处或指定下标插入文本。
 * <pre>
 *     布局属性 binding_et_insert_text、binding_et_insert_at（可选，未指定则在当前 selection 插入）、
 *     binding_et_insert_select_to_end（可选，未指定为 true）；内部委托 EditTextUtils.insert。
 * </pre>
 *
 * @param text 待插入内容，空则忽略
 * @param at 插入起始下标，null 表示使用当前光标
 * @param selectToEnd 插入后是否将光标移到文末
 */
@BindingAdapter(
    value = ["binding_et_insert_text", "binding_et_insert_at", "binding_et_insert_select_to_end"],
    requireAll = false
)
fun EditText.bindingETInsert(
    text: CharSequence?,
    at: Int?,
    selectToEnd: Boolean?
) {
    if (text.isNullOrEmpty()) return
    val move = selectToEnd ?: true
    if (at != null) {
        EditTextUtils.insert(this, text, at, move)
    } else {
        EditTextUtils.insert(this, text, move)
    }
}

/**
 * 通过数据绑定限制长度并设置内容。
 * <pre>
 *     布局属性 binding_et_max_length_bind、binding_et_max_length_bind_text；均需绑定且 max 大于 0，内部委托 EditTextUtils.setMaxLengthAndText。
 * </pre>
 *
 * @param maxLength 最大长度
 * @param content 文本，null 按空串
 */
@BindingAdapter(
    value = ["binding_et_max_length_bind", "binding_et_max_length_bind_text"],
    requireAll = true
)
fun EditText.bindingETMaxLengthAndText(
    maxLength: Int,
    content: CharSequence?
) {
    if (maxLength <= 0) return
    try {
        EditTextUtils.setMaxLengthAndText(this, content ?: "", maxLength)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingETMaxLengthAndText")
    }
}

// =============
// = 光标与外观 =
// =============

/**
 * 通过数据绑定控制光标是否可见。
 * <pre>
 *     布局属性 binding_et_cursor_visible；内部委托 EditTextUtils.setCursorVisible。
 * </pre>
 *
 * @param visible 是否显示光标
 */
@BindingAdapter("binding_et_cursor_visible")
fun EditText.bindingETCursorVisible(visible: Boolean) {
    EditTextUtils.setCursorVisible(this, visible)
}

/**
 * 通过数据绑定设置文本光标 drawable（资源 id）。
 * <pre>
 *     布局属性 binding_et_text_cursor_drawable_res；API 29 以下忽略；resId 为 0 时不调用。
 *     内部委托 EditTextUtils.setTextCursorDrawable（int 重载）。
 * </pre>
 *
 * @param resId 光标资源
 */
@BindingAdapter("binding_et_text_cursor_drawable_res")
fun EditText.bindingETTextCursorDrawableRes(@DrawableRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && resId != 0) {
        EditTextUtils.setTextCursorDrawable(this, resId)
    }
}

/**
 * 通过数据绑定设置文本光标图形。
 * <pre>
 *     布局属性 binding_et_text_cursor_drawable；API 29 以下忽略；null 时不调用。
 *     类型见 [Drawable]；内部委托 EditTextUtils.setTextCursorDrawable（Drawable 重载）。
 * </pre>
 *
 * @param drawable 光标图形
 */
@BindingAdapter("binding_et_text_cursor_drawable")
fun EditText.bindingETTextCursorDrawable(drawable: Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && drawable != null) {
        EditTextUtils.setTextCursorDrawable(this, drawable)
    }
}

/**
 * 通过数据绑定将光标移到首位（为 true 时执行）。
 * <pre>
 *     布局属性 binding_et_selection_to_top；内部委托 EditTextUtils.setSelectionToTop。
 * </pre>
 *
 * @param toTop 是否移到首位
 */
@BindingAdapter("binding_et_selection_to_top")
fun EditText.bindingETSelectionToTop(toTop: Boolean) {
    if (toTop) {
        EditTextUtils.setSelectionToTop(this)
    }
}

/**
 * 通过数据绑定将光标移到末位（为 true 时执行）。
 * <pre>
 *     布局属性 binding_et_selection_to_bottom；内部委托 EditTextUtils.setSelectionToBottom。
 * </pre>
 *
 * @param toBottom 是否移到末位
 */
@BindingAdapter("binding_et_selection_to_bottom")
fun EditText.bindingETSelectionToBottom(toBottom: Boolean) {
    if (toBottom) {
        EditTextUtils.setSelectionToBottom(this)
    }
}

/**
 * 通过数据绑定设置光标位置。
 * <pre>
 *     布局属性 binding_et_selection_index；内部委托 EditTextUtils.setSelection。
 * </pre>
 *
 * @param index 光标索引
 */
@BindingAdapter("binding_et_selection_index")
fun EditText.bindingETSelectionIndex(index: Int) {
    EditTextUtils.setSelection(this, index)
}

/**
 * 通过数据绑定按允许字符集设置数字键入监听器。
 * <pre>
 *     布局属性 binding_et_key_listener_accepted；如 "0123456789"；类型见 [android.text.method.DigitsKeyListener]；
 *     内部委托 EditTextUtils.setKeyListener（String 重载）。
 * </pre>
 *
 * @param accepted 允许的字符序列，null 时不修改
 */
@BindingAdapter("binding_et_key_listener_accepted")
fun EditText.bindingETKeyListenerAccepted(accepted: String?) {
    if (accepted != null) {
        EditTextUtils.setKeyListener(this, accepted)
    }
}

/**
 * 通过数据绑定按字符数组设置数字键入监听器。
 * <pre>
 *     布局属性 binding_et_key_listener_accepted_chars；类型见 [android.text.method.DigitsKeyListener]；
 *     内部委托 EditTextUtils.setKeyListener（char[] 重载）。
 * </pre>
 *
 * @param accepted 允许的字符序列，null 或空时不修改
 */
@BindingAdapter("binding_et_key_listener_accepted_chars")
fun EditText.bindingETKeyListenerAcceptedChars(accepted: CharSequence?) {
    if (!accepted.isNullOrEmpty()) {
        EditTextUtils.setKeyListener(this, accepted.toString().toCharArray())
    }
}