package dev.simple.bindingadapters.attribute

/**
 * 最大长度与待设正文，用于单次绑定调用 [dev.utils.app.EditTextUtils.setMaxLengthAndText]。
 * <pre>
 *     与布局属性 `binding_et_max_length_and_text` 搭配。
 * </pre>
 *
 * @property content 文本，可为空
 * @property maxLength 最大长度，须为正数方与工具类行为一致
 */
data class EtMaxLengthAndText(
    val content: CharSequence?,
    val maxLength: Int,
)