package dev.utils.app;

import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.utils.LogPrintUtils;
import dev.utils.app.text.input_filter.AlphanumericInputFilter;
import dev.utils.app.text.input_filter.ChineseMobilePhoneInputFilter;
import dev.utils.app.text.input_filter.SearchKeywordInputFilter;
import dev.utils.app.text.input_filter.ByteLengthInputFilter;
import dev.utils.app.text.input_filter.ChineseAddressInputFilter;
import dev.utils.app.text.input_filter.ChineseOnlyInputFilter;
import dev.utils.app.text.input_filter.DecimalInputFilter;
import dev.utils.app.text.input_filter.EmailInputFilter;
import dev.utils.app.text.input_filter.EmojiInputFilter;
import dev.utils.app.text.input_filter.FrontSpaceInputFilter;
import dev.utils.app.text.input_filter.HexInputFilter;
import dev.utils.app.text.input_filter.IntegerInputFilter;
import dev.utils.app.text.input_filter.MaxLengthInputFilter;
import dev.utils.app.text.input_filter.MaxLinesInputFilter;
import dev.utils.app.text.input_filter.NoConsecutiveSpaceInputFilter;
import dev.utils.app.text.input_filter.NoEnterInputFilter;
import dev.utils.app.text.input_filter.NoSpaceInputFilter;
import dev.utils.app.text.input_filter.RangeValueInputFilter;
import dev.utils.app.text.input_filter.UrlInputFilter;
import dev.utils.app.text.input_filter.UsernameInputFilter;

/**
 * detail: InputFilter 组合与快捷设置工具类
 * @author Ttt
 */
public final class InputFilterUtils {

    private InputFilterUtils() {
    }

    // 日志 TAG
    private static final String TAG = InputFilterUtils.class.getSimpleName();

    // ================
    // = 获取 TextView =
    // ================

    /**
     * 获取 TextView
     * @param view {@link View}
     * @param <T>  泛型
     * @return {@link TextView}
     */
    public static <T extends TextView> T getTextView(final View view) {
        if (view instanceof TextView) {
            try {
                return (T) view;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTextView");
            }
        }
        return null;
    }

    // =======================
    // = TextView 设置 Filter =
    // =======================

    // ==============
    // = setFilters =
    // ==============

    /**
     * 设置 InputFilter ( 覆盖原有 )
     * <pre>
     *     内部会先对入参执行 {@link InputFilterUtils#distinctFilters(InputFilter...)}，忽略 null 并去除重复引用。
     * </pre>
     * @param textView {@link TextView}
     * @param filters  过滤器
     * @param <T>      泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean setFilters(
            final T textView,
            final InputFilter... filters
    ) {
        if (textView == null) return false;
        try {
            textView.setFilters(distinctFilters(filters));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setFilters");
        }
        return false;
    }

    /**
     * 设置 InputFilter ( 覆盖原有 )
     * @param view    {@link View}
     * @param filters 过滤器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setFilters(
            final View view,
            final InputFilter... filters
    ) {
        return setFilters(getTextView(view), filters);
    }

    // =================
    // = appendFilters =
    // =================

    /**
     * 追加 InputFilter ( 保留原有并在末尾追加 )
     * <pre>
     *     追加段中的 null 会被忽略；与已有列表同一引用的 filter 不会重复追加。
     * </pre>
     * @param textView {@link TextView}
     * @param filters  待追加的过滤器
     * @param <T>      泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean appendFilters(
            final T textView,
            final InputFilter... filters
    ) {
        if (textView == null) return false;
        try {
            textView.setFilters(append(textView.getFilters(), filters));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "appendFilters");
        }
        return false;
    }

    /**
     * 追加 InputFilter ( 保留原有并在末尾追加 )
     * @param view    {@link View}
     * @param filters 待追加的过滤器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean appendFilters(
            final View view,
            final InputFilter... filters
    ) {
        return appendFilters(getTextView(view), filters);
    }

    // ================
    // = clearFilters =
    // ================

    /**
     * 清空 InputFilter
     * @param textView {@link TextView}
     * @param <T>      泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean clearFilters(final T textView) {
        if (textView == null) return false;
        try {
            textView.setFilters(new InputFilter[0]);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "clearFilters");
        }
        return false;
    }

    /**
     * 清空 InputFilter
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean clearFilters(final View view) {
        return clearFilters(getTextView(view));
    }

    // ================
    // = mergeFilters =
    // ================

    /**
     * 按 Class 合并并设置 InputFilter ( 保留原有，同类型替换 )
     * <pre>
     *     经 {@link #mergeByClass(InputFilter[], InputFilter...)} 合并：入参 null 忽略；
     *     与已有 filter 的运行时 {@link Class} 相同时，用新实例原位替换（如更新 {@link MaxLengthInputFilter} 最大长度）。
     *     仅精确匹配 {@code filter.getClass()}，子类与父类视为不同类型。
     *     与 {@link #appendFilters(TextView, InputFilter...)} 不同：后者按引用去重追加，本方法按 Class 替换。
     * </pre>
     * @param textView {@link TextView}
     * @param filters  待合并的过滤器
     * @param <T>      泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T extends TextView> boolean mergeFilters(
            final T textView,
            final InputFilter... filters
    ) {
        if (textView == null) return false;
        try {
            textView.setFilters(mergeByClass(textView.getFilters(), filters));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "mergeFilters");
        }
        return false;
    }

    /**
     * 按 Class 合并并设置 InputFilter ( 保留原有，同类型替换 )
     * @param view    {@link View}
     * @param filters 待合并的过滤器
     * @return {@code true} success, {@code false} fail
     */
    public static boolean mergeFilters(
            final View view,
            final InputFilter... filters
    ) {
        return mergeFilters(getTextView(view), filters);
    }

    // ==========
    // = 通用方法 =
    // ==========

    /**
     * 合并多个 InputFilter 并去重
     * <pre>
     *     入参中的 null 会被忽略；与列表中已存在的 filter 为同一引用（{@code ==}）时只保留一份，顺序以首次出现为准。
     *     与 {@link #mergeByClass(InputFilter[], InputFilter...)} 不同：本方法按引用去重，不按 Class 替换。
     *     与 {@link #mergeFilters(TextView, InputFilter...)} 不同：本方法只处理入参数组，不读写 {@link TextView}。
     * </pre>
     * @param filters 待合并的过滤器
     * @return 合并去重后的 {@link InputFilter} 数组，无有效元素时返回空数组
     */
    public static InputFilter[] distinctFilters(final InputFilter... filters) {
        if (filters == null || filters.length == 0) return new InputFilter[0];
        List<InputFilter> list = new ArrayList<>();
        addDistinct(list, filters);
        return list.toArray(new InputFilter[0]);
    }

    /**
     * 在已有 filters 后追加（忽略 null，同一引用不重复追加）
     * <pre>
     *     {@code original} 为 null 时视为无原有项；{@code append} 为 null 或空时不追加。
     *     追加段中的 null 忽略；与列表中同一引用（{@code ==}）的 filter 不会重复追加。
     * </pre>
     * @param original 原有 filters
     * @param append   追加的 filters
     * @return 合并后的 {@link InputFilter} 数组
     */
    public static InputFilter[] append(
            final InputFilter[] original,
            final InputFilter... append
    ) {
        List<InputFilter> list = new ArrayList<>();
        if (original != null) {
            addDistinct(list, original);
        }
        if (append != null) {
            addDistinct(list, append);
        }
        return list.toArray(new InputFilter[0]);
    }

    /**
     * 按运行时 Class 合并 InputFilter
     * <pre>
     *     null 忽略。列表中已存在相同 {@code filter.getClass()} 时，用新 filter 原位替换；否则追加到末尾。
     *     先处理 {@code original}，再按入参顺序处理 {@code incoming}；同批入参里重复 Class 时以后者为准。
     *     仅精确匹配 {@code getClass()}，子类不会替换父类实例，反之亦然。
     * </pre>
     * @param original 原有 filters
     * @param incoming 待合并的 filters
     * @return 合并后的 {@link InputFilter} 数组
     */
    public static InputFilter[] mergeByClass(
            final InputFilter[] original,
            final InputFilter... incoming
    ) {
        List<InputFilter> list = new ArrayList<>();
        if (original != null) {
            putByClass(list, original);
        }
        if (incoming != null) {
            putByClass(list, incoming);
        }
        return list.toArray(new InputFilter[0]);
    }

    /**
     * 将 filters 中非 null 且未在 list 中出现过的元素按顺序加入 list
     * @param list    目标列表
     * @param filters 待加入的过滤器
     */
    private static void addDistinct(
            final List<InputFilter> list,
            final InputFilter... filters
    ) {
        if (filters == null) return;
        for (InputFilter filter : filters) {
            if (filter != null && !list.contains(filter)) {
                list.add(filter);
            }
        }
    }

    /**
     * 按 Class 写入 list：同 Class 原位替换，否则追加
     * <pre>
     *     {@code filters} 为 null 时直接返回；元素为 null 时跳过。
     *     仅精确匹配 {@code filter.getClass()}，子类与父类视为不同类型。
     * </pre>
     * @param list    目标列表
     * @param filters 待写入的过滤器
     */
    private static void putByClass(
            final List<InputFilter> list,
            final InputFilter... filters
    ) {
        if (filters == null) return;
        for (InputFilter filter : filters) {
            if (filter == null) continue;
            Class<?> clazz    = filter.getClass();
            boolean  replaced = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClass() == clazz) {
                    list.set(i, filter);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                list.add(filter);
            }
        }
    }

    // =================
    // = Filter 组合预设 =
    // =================

    // =============
    // = 单行 / 多行 =
    // =============

    /**
     * 单行输入基础组合
     * <pre>
     *     禁止回车换行、禁止首字符为空格，不限长度。
     * </pre>
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] singleLineDefault() {
        return singleLineBaseFilters();
    }

    /**
     * 单行输入并限制最大字符长度
     * <pre>
     *     在 {@link #singleLineDefault()} 基础上追加 {@link MaxLengthInputFilter}。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] singleLineWithMaxLength(final int maxLength) {
        return append(singleLineBaseFilters(), new MaxLengthInputFilter(maxLength));
    }

    /**
     * 多行输入基础组合
     * <pre>
     *     禁止首字符为空格，允许换行，不限长度。
     * </pre>
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] multiLineDefault() {
        return multiLineBaseFilters();
    }

    /**
     * 多行输入并限制最大字符长度
     * <pre>
     *     在 {@link #multiLineDefault()} 基础上追加 {@link MaxLengthInputFilter}。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] multiLineWithMaxLength(final int maxLength) {
        return append(multiLineBaseFilters(), new MaxLengthInputFilter(maxLength));
    }

    /**
     * 多行输入并限制最大字符长度与最大行数
     * <pre>
     *     在 {@link #multiLineWithMaxLength(int)} 基础上追加 {@link MaxLinesInputFilter}。
     * </pre>
     * @param maxLength 最大字符长度
     * @param maxLines  最大行数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] multiLineWithMaxLines(
            final int maxLength,
            final int maxLines
    ) {
        return append(
                multiLineWithMaxLength(maxLength),
                new MaxLinesInputFilter(maxLines)
        );
    }

    // =============
    // = 文本 / 社交 =
    // =============

    /**
     * 昵称类单行输入组合
     * <pre>
     *     单行基础规则，并禁止 Emoji、限制最大字符长度。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] nickname(final int maxLength) {
        return append(
                singleLineWithMaxLength(maxLength),
                new EmojiInputFilter()
        );
    }

    /**
     * 评论类多行输入组合
     * <pre>
     *     多行长度与行数限制，允许 Emoji 与换行。
     * </pre>
     * @param maxLength 最大字符长度
     * @param maxLines  最大行数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] comment(
            final int maxLength,
            final int maxLines
    ) {
        return multiLineWithMaxLines(maxLength, maxLines);
    }

    /**
     * 搜索关键词单行输入组合
     * <pre>
     *     单行基础规则，仅保留搜索常用字符，禁止连续空格，并限制最大字符长度。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] searchKeyword(final int maxLength) {
        return append(
                singleLineWithMaxLength(maxLength),
                new SearchKeywordInputFilter(),
                new NoConsecutiveSpaceInputFilter()
        );
    }

    /**
     * 禁止空格的单行输入组合
     * <pre>
     *     适用于密码、验证码等不允许任何空白的单行场景。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] noSpaceSingleLine(final int maxLength) {
        return append(
                singleLineWithMaxLength(maxLength),
                new NoSpaceInputFilter()
        );
    }

    // =============
    // = 账号 / 链接 =
    // =============

    /**
     * 用户名输入组合
     * <pre>
     *     单行基础规则，仅允许字母、数字与下划线，并限制最大字符长度。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] username(final int maxLength) {
        return append(
                singleLineWithMaxLength(maxLength),
                new UsernameInputFilter()
        );
    }

    /**
     * 字母数字密码输入组合
     * <pre>
     *     禁止空格的单行输入，且仅允许英文字母与数字。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] passwordAlphanumeric(final int maxLength) {
        return append(
                noSpaceSingleLine(maxLength),
                new AlphanumericInputFilter()
        );
    }

    /**
     * 邮箱输入组合
     * <pre>
     *     禁止空格的单行输入，仅保留邮箱常用字符。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] email(final int maxLength) {
        return append(
                noSpaceSingleLine(maxLength),
                new EmailInputFilter()
        );
    }

    /**
     * URL 输入组合
     * <pre>
     *     禁止空格的单行输入，仅保留 URL 常用字符。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] url(final int maxLength) {
        return append(
                noSpaceSingleLine(maxLength),
                new UrlInputFilter()
        );
    }

    // =======
    // = 数值 =
    // =======

    /**
     * 非负整数输入组合
     * <pre>
     *     仅数字，可限制最大位数；不含符号与小数点。
     * </pre>
     * @param maxDigits 最大位数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] integer(final int maxDigits) {
        return distinctFilters(new IntegerInputFilter(maxDigits));
    }

    /**
     * 非负小数输入组合
     * <pre>
     *     数字与一个小数点，可分别限制整数位与小数位长度。
     * </pre>
     * @param integerDigits 整数部分最大位数
     * @param decimalDigits 小数部分最大位数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] decimal(
            final int integerDigits,
            final int decimalDigits
    ) {
        return distinctFilters(new DecimalInputFilter(integerDigits, decimalDigits));
    }

    /**
     * 金额类小数输入组合
     * <pre>
     *     在 {@link #decimal(int, int)} 基础上追加闭区间数值限制。
     *     建议失焦或提交时再做格式化与最终校验。
     * </pre>
     * @param integerDigits 整数部分最大位数
     * @param decimalDigits 小数部分最大位数
     * @param minValue      允许的最小值
     * @param maxValue      允许的最大值
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] amount(
            final int integerDigits,
            final int decimalDigits,
            final double minValue,
            final double maxValue
    ) {
        return append(
                decimal(integerDigits, decimalDigits),
                new RangeValueInputFilter(minValue, maxValue)
        );
    }

    /**
     * 短信验证码数字输入组合
     * <pre>
     *     禁止空格的单行输入，且仅允许数字并限制固定位数。
     * </pre>
     * @param length 验证码位数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] verifyCode(final int length) {
        return append(
                noSpaceSingleLine(length),
                new IntegerInputFilter(length)
        );
    }

    /**
     * 手机号数字输入组合
     * <pre>
     *     禁止空格的单行输入，{@link ChineseMobilePhoneInputFilter} 默认最多 11 位。
     * </pre>
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] phoneNumber() {
        return phoneNumber(11);
    }

    /**
     * 手机号数字输入组合
     * <pre>
     *     禁止空格的单行输入，{@link ChineseMobilePhoneInputFilter} 限制位数与号段。
     * </pre>
     * @param maxDigits 最大位数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] phoneNumber(final int maxDigits) {
        return append(
                noSpaceSingleLine(maxDigits),
                new ChineseMobilePhoneInputFilter(maxDigits)
        );
    }

    // =============
    // = 中文 / 地址 =
    // =============

    /**
     * 仅中文输入组合
     * <pre>
     *     单行基础规则，仅允许 CJK 相关字符，并限制最大字符长度。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] chineseOnly(final int maxLength) {
        return append(
                singleLineWithMaxLength(maxLength),
                new ChineseOnlyInputFilter()
        );
    }

    /**
     * 国内地址单行输入组合
     * <pre>
     *     单行基础规则，允许中文、英文、数字与常见地址符号，并限制最大字符长度。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] chineseAddress(final int maxLength) {
        return append(
                singleLineWithMaxLength(maxLength),
                new ChineseAddressInputFilter()
        );
    }

    /**
     * 国内地址多行输入组合
     * <pre>
     *     多行长度与行数限制，允许中文、英文、数字与常见地址符号。
     * </pre>
     * @param maxLength 最大字符长度
     * @param maxLines  最大行数
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] chineseAddressMultiline(
            final int maxLength,
            final int maxLines
    ) {
        return append(
                multiLineWithMaxLines(maxLength, maxLines),
                new ChineseAddressInputFilter()
        );
    }

    // =======
    // = 其它 =
    // =======

    /**
     * 十六进制输入组合
     * <pre>
     *     禁止空格的单行输入，仅允许 0-9 与 a-f、A-F，并限制最大字符长度。
     * </pre>
     * @param maxLength 最大字符长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] hex(final int maxLength) {
        return append(
                noSpaceSingleLine(maxLength),
                new HexInputFilter()
        );
    }

    /**
     * 按显示字节长度限制的单行输入组合
     * <pre>
     *     单行基础规则，中文等 CJK 计 2、其余计 1，常用于短信字数统计场景。
     * </pre>
     * @param maxByteLength 最大显示字节长度
     * @return 预设 {@link InputFilter} 数组
     */
    public static InputFilter[] byteLengthSingleLine(final int maxByteLength) {
        return append(
                singleLineBaseFilters(),
                new ByteLengthInputFilter(maxByteLength)
        );
    }

    // ==========
    // = 内部组合 =
    // ==========

    /**
     * 单行基础过滤器组合
     * @return 禁止回车与首字符空格的 {@link InputFilter} 数组
     */
    private static InputFilter[] singleLineBaseFilters() {
        return distinctFilters(
                new NoEnterInputFilter(),
                new FrontSpaceInputFilter()
        );
    }

    /**
     * 多行基础过滤器组合
     * @return 禁止首字符空格的 {@link InputFilter} 数组
     */
    private static InputFilter[] multiLineBaseFilters() {
        return distinctFilters(new FrontSpaceInputFilter());
    }
}