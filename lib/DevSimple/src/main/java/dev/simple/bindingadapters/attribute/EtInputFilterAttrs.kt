package dev.simple.bindingadapters.attribute

/**
 * InputFilter 预设与参数封装，用于单次绑定套用 [dev.utils.app.InputFilterUtils] 组合预设。
 * <pre>
 *     与布局属性 `binding_et_input_filter_preset_spec` 搭配；
 *     `preset` 取 `EditTextInputFilter.kt` 中 `BINDING_ET_INPUT_FILTER_PRESET_*` 常量。
 *     各参数字段按预设类型择一填写，未使用的字段可省略。
 * </pre>
 *
 * @property preset 预设类型
 * @property maxLength 最大字符长度（昵称、邮箱、单行限长等）
 * @property maxLines 最大行数（多行评论、地址等）
 * @property maxDigits 最大位数（整数、手机号、验证码等）
 * @property integerDigits 小数整数部分最大位数
 * @property decimalDigits 小数小数部分最大位数
 * @property minValue 数值闭区间下限（金额类）
 * @property maxValue 数值闭区间上限（金额类）
 * @property maxByteLength 显示字节长度上限（短信计字等）
 */
data class EtInputFilterPresetSpec(
    val preset: Int,
    val maxLength: Int? = null,
    val maxLines: Int? = null,
    val maxDigits: Int? = null,
    val integerDigits: Int? = null,
    val decimalDigits: Int? = null,
    val minValue: Double? = null,
    val maxValue: Double? = null,
    val maxByteLength: Int? = null,
)

/**
 * detail: InputFilter 预设类型
 * @author Ttt
 */
object ETIFSpec {

    /** 单行输入基础组合；与 `binding_et_input_filter_preset` 搭配。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_SINGLE_LINE_DEFAULT = 1

    /** 单行输入并限制最大字符长度；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_SINGLE_LINE_MAX_LENGTH = 2

    /** 多行输入基础组合。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_MULTI_LINE_DEFAULT = 3

    /** 多行输入并限制最大字符长度；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_MULTI_LINE_MAX_LENGTH = 4

    /** 多行输入并限制最大字符长度与最大行数；需 `max_length` 与 `max_lines`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_MULTI_LINE_MAX_LINES = 5

    /** 昵称类单行输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_NICKNAME = 6

    /** 评论类多行输入；需 `max_length` 与 `max_lines`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_COMMENT = 7

    /** 搜索关键词单行输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_SEARCH_KEYWORD = 8

    /** 禁止空格的单行输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_NO_SPACE_SINGLE_LINE = 9

    /** 用户名输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_USERNAME = 10

    /** 字母数字密码输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_PASSWORD_ALPHANUMERIC = 11

    /** 邮箱输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_EMAIL = 12

    /** URL 输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_URL = 13

    /** 非负整数输入；需 `binding_et_input_filter_preset_max_digits`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_INTEGER = 14

    /** 非负小数输入；需 `integer_digits` 与 `decimal_digits`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_DECIMAL = 15

    /** 金额类小数输入；需 `integer_digits`、`decimal_digits`、`min_value`、`max_value`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_AMOUNT = 16

    /** 短信验证码数字输入；需 `binding_et_input_filter_preset_max_digits`（固定位数）。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_VERIFY_CODE = 17

    /** 手机号数字输入，默认最多 11 位；可选 `max_digits` 覆盖位数。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_PHONE_NUMBER = 18

    /** 仅中文输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_CHINESE_ONLY = 19

    /** 国内地址单行输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_CHINESE_ADDRESS = 20

    /** 国内地址多行输入；需 `max_length` 与 `max_lines`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_CHINESE_ADDRESS_MULTILINE = 21

    /** 十六进制输入；需 `binding_et_input_filter_preset_max_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_HEX = 22

    /** 按显示字节长度限制的单行输入；需 `binding_et_input_filter_preset_max_byte_length`。 */
    const val BINDING_ET_INPUT_FILTER_PRESET_BYTE_LENGTH_SINGLE_LINE = 23
}