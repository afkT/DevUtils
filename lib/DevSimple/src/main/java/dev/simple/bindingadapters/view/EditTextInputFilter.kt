package dev.simple.bindingadapters.view

import android.text.InputFilter
import android.widget.EditText
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.ETIFSpec
import dev.simple.bindingadapters.attribute.EtInputFilterPresetSpec
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.app.InputFilterUtils

// ======================================
// = EditText InputFilter BindingAdapter =
// ======================================

/**
 * [EditText] 的 InputFilter Data Binding 适配集合。
 *
 * 布局属性统一为 `binding_et_input_filter_*`；实现对应 [dev.utils.app.InputFilterUtils] 中适合在单节点上表达的 API。
 * <pre>
 *     未封装 `getTextView`、`distinctFilters`、`append`、`mergeByClass` 等纯静态数组工具（须在 ViewModel 中组装后绑 `binding_et_input_filters`）。
 *     与 [EditText.kt] 的 `binding_et_max_length`（[dev.utils.app.EditTextUtils]）语义不同：本文件为过滤器数组与组合预设。
 *     需多次清空过滤器时使用 `binding_et_input_filters_clear_ts`（判定同 [qualifiesBindingAction]）。
 * </pre>
 */

// =================
// = 过滤器数组 CRUD =
// =================

/**
 * 通过数据绑定覆盖设置 InputFilter 数组。
 * <pre>
 *     布局属性 `binding_et_input_filters`；`null` 时不修改；委托 [InputFilterUtils.setFilters]。
 * </pre>
 *
 * @param filters 过滤器数组，空数组表示清空
 */
@BindingAdapter("binding_et_input_filters")
fun EditText.bindingETInputFilters(filters: Array<InputFilter>?) {
    if (filters == null) return
    InputFilterUtils.setFilters(this, *filters)
}

/**
 * 通过数据绑定在末尾追加 InputFilter。
 * <pre>
 *     布局属性 `binding_et_input_filters_append`；`null` 时不修改；委托 [InputFilterUtils.appendFilters]。
 * </pre>
 *
 * @param filters 待追加的过滤器
 */
@BindingAdapter("binding_et_input_filters_append")
fun EditText.bindingETInputFiltersAppend(filters: Array<InputFilter>?) {
    if (filters == null) return
    InputFilterUtils.appendFilters(this, *filters)
}

/**
 * 通过数据绑定按运行时 Class 合并 InputFilter（同类型原位替换）。
 * <pre>
 *     布局属性 `binding_et_input_filters_merge`；`null` 时不修改；委托 [InputFilterUtils.mergeFilters]。
 * </pre>
 *
 * @param filters 待合并的过滤器
 */
@BindingAdapter("binding_et_input_filters_merge")
fun EditText.bindingETInputFiltersMerge(filters: Array<InputFilter>?) {
    if (filters == null) return
    InputFilterUtils.mergeFilters(this, *filters)
}

/**
 * 通过数据绑定以时间戳清空全部 InputFilter。
 * <pre>
 *     布局属性 `binding_et_input_filters_clear_ts`；判定同 [qualifiesBindingAction]；委托 [InputFilterUtils.clearFilters]。
 *     与 `binding_et_input_filters` 可并存；每次正时间戳清空过滤器列表。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_et_input_filters_clear_ts")
fun EditText.bindingETInputFiltersClearTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    InputFilterUtils.clearFilters(this)
}

// ==========
// = 组合预设 =
// ==========

/**
 * 通过数据绑定按预设类型套用 InputFilter 组合（覆盖原有）。
 * <pre>
 *     布局属性 `binding_et_input_filter_preset` 及可选参数属性（`requireAll = false`）；
 *     预设常量见本文件 `BINDING_ET_INPUT_FILTER_PRESET_*`；解析后委托 [InputFilterUtils.setFilters]。
 *     亦可使用 `binding_et_input_filter_preset_spec` 单次传入 [EtInputFilterPresetSpec]。
 * </pre>
 *
 * @param preset 预设类型，`null` 时跳过
 * @param maxLength 最大字符长度
 * @param maxLines 最大行数
 * @param maxDigits 最大位数
 * @param integerDigits 小数整数部分最大位数
 * @param decimalDigits 小数小数部分最大位数
 * @param minValue 数值下限
 * @param maxValue 数值上限
 * @param maxByteLength 最大显示字节长度
 */
@BindingAdapter(
    value = [
        "binding_et_input_filter_preset",
        "binding_et_input_filter_preset_max_length",
        "binding_et_input_filter_preset_max_lines",
        "binding_et_input_filter_preset_max_digits",
        "binding_et_input_filter_preset_integer_digits",
        "binding_et_input_filter_preset_decimal_digits",
        "binding_et_input_filter_preset_min_value",
        "binding_et_input_filter_preset_max_value",
        "binding_et_input_filter_preset_max_byte_length",
    ],
    requireAll = false,
)
fun EditText.bindingETInputFilterPreset(
    preset: Int?,
    maxLength: Int?,
    maxLines: Int?,
    maxDigits: Int?,
    integerDigits: Int?,
    decimalDigits: Int?,
    minValue: Double?,
    maxValue: Double?,
    maxByteLength: Int?,
) {
    if (preset == null) return
    val spec = EtInputFilterPresetSpec(
        preset = preset,
        maxLength = maxLength,
        maxLines = maxLines,
        maxDigits = maxDigits,
        integerDigits = integerDigits,
        decimalDigits = decimalDigits,
        minValue = minValue,
        maxValue = maxValue,
        maxByteLength = maxByteLength,
    )
    applyInputFilterPresetSpec(spec)
}

/**
 * 通过数据绑定按封装参数套用 InputFilter 组合预设（覆盖原有）。
 * <pre>
 *     布局属性 `binding_et_input_filter_preset_spec`；`null` 时跳过；委托 [InputFilterUtils.setFilters]。
 * </pre>
 *
 * @param spec 预设与参数封装
 */
@BindingAdapter("binding_et_input_filter_preset_spec")
fun EditText.bindingETInputFilterPresetSpec(spec: EtInputFilterPresetSpec?) {
    if (spec == null) return
    applyInputFilterPresetSpec(spec)
}

// ==========
// = 内部解析 =
// ==========

/**
 * 将 [EtInputFilterPresetSpec] 解析为预设过滤器数组并覆盖设置到当前 [EditText]。
 */
private fun EditText.applyInputFilterPresetSpec(spec: EtInputFilterPresetSpec) {
    val filters = resolveInputFilterPreset(spec) ?: return
    InputFilterUtils.setFilters(this, *filters)
}

/**
 * 按预设类型解析 [InputFilterUtils] 组合结果。
 *
 * @param spec 预设与参数
 * @return 预设过滤器数组；参数不足或未知预设时为 null
 */
private fun resolveInputFilterPreset(spec: EtInputFilterPresetSpec): Array<InputFilter>? {
    return try {
        when (spec.preset) {
            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_SINGLE_LINE_DEFAULT ->
                InputFilterUtils.singleLineDefault()

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_SINGLE_LINE_MAX_LENGTH -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.singleLineWithMaxLength(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_MULTI_LINE_DEFAULT ->
                InputFilterUtils.multiLineDefault()

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_MULTI_LINE_MAX_LENGTH -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.multiLineWithMaxLength(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_MULTI_LINE_MAX_LINES -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                val lines = spec.maxLines.positiveOrNull() ?: return null
                InputFilterUtils.multiLineWithMaxLines(len, lines)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_NICKNAME -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.nickname(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_COMMENT -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                val lines = spec.maxLines.positiveOrNull() ?: return null
                InputFilterUtils.comment(len, lines)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_SEARCH_KEYWORD -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.searchKeyword(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_NO_SPACE_SINGLE_LINE -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.noSpaceSingleLine(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_USERNAME -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.username(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_PASSWORD_ALPHANUMERIC -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.passwordAlphanumeric(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_EMAIL -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.email(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_URL -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.url(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_INTEGER -> {
                val digits = spec.maxDigits.positiveOrNull() ?: return null
                InputFilterUtils.integer(digits)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_DECIMAL -> {
                val intDigits = spec.integerDigits.positiveOrNull() ?: return null
                val decDigits = spec.decimalDigits.positiveOrNull() ?: return null
                InputFilterUtils.decimal(intDigits, decDigits)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_AMOUNT -> {
                val intDigits = spec.integerDigits.positiveOrNull() ?: return null
                val decDigits = spec.decimalDigits.positiveOrNull() ?: return null
                val min = spec.minValue ?: return null
                val max = spec.maxValue ?: return null
                InputFilterUtils.amount(intDigits, decDigits, min, max)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_VERIFY_CODE -> {
                val length = spec.maxDigits.positiveOrNull() ?: return null
                InputFilterUtils.verifyCode(length)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_PHONE_NUMBER -> {
                val digits = spec.maxDigits.positiveOrNull()
                if (digits == null) {
                    InputFilterUtils.phoneNumber()
                } else {
                    InputFilterUtils.phoneNumber(digits)
                }
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_CHINESE_ONLY -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.chineseOnly(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_CHINESE_ADDRESS -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.chineseAddress(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_CHINESE_ADDRESS_MULTILINE -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                val lines = spec.maxLines.positiveOrNull() ?: return null
                InputFilterUtils.chineseAddressMultiline(len, lines)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_HEX -> {
                val len = spec.maxLength.positiveOrNull() ?: return null
                InputFilterUtils.hex(len)
            }

            ETIFSpec.BINDING_ET_INPUT_FILTER_PRESET_BYTE_LENGTH_SINGLE_LINE -> {
                val bytes = spec.maxByteLength.positiveOrNull() ?: return null
                InputFilterUtils.byteLengthSingleLine(bytes)
            }

            else -> null
        }
    } catch (_: Exception) {
        null
    }
}

/** 正整数参数，否则视为无效。 */
private fun Int?.positiveOrNull(): Int? = this?.takeIf { it > 0 }