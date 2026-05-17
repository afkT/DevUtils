package dev.utils.app;

import android.text.InputFilter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.utils.app.text.FrontSpaceInputFilter;
import dev.utils.app.text.MaxLengthInputFilter;
import dev.utils.app.text.NoEnterInputFilter;

/**
 * detail: InputFilter 组合与快捷设置工具类
 * @author Ttt
 */
public final class InputFilterUtils {

    private InputFilterUtils() {
    }

    // =================
    // = 常用 Filter 单例 =
    // =================

    /**
     * 禁止回车换行
     */
    public static final InputFilter NO_ENTER    = new NoEnterInputFilter();
    /**
     * 禁止首字符为空格
     */
    public static final InputFilter FRONT_SPACE = new FrontSpaceInputFilter();

    // =

    /**
     * 合并多个 InputFilter
     * <pre>
     *     入参中的 null 元素会被忽略。
     * </pre>
     * @param filters 待合并的过滤器
     * @return 合并后的 {@link InputFilter} 数组，无有效元素时返回空数组
     */
    public static InputFilter[] merge(final InputFilter... filters) {
        if (filters == null || filters.length == 0) return new InputFilter[0];
        List<InputFilter> list = new ArrayList<>();
        for (InputFilter filter : filters) {
            if (filter != null) list.add(filter);
        }
        return list.toArray(new InputFilter[0]);
    }

    /**
     * 在已有 filters 后追加
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
            list.addAll(Arrays.asList(original));
        }
        if (append != null) {
            for (InputFilter filter : append) {
                if (filter != null) list.add(filter);
            }
        }
        return list.toArray(new InputFilter[0]);
    }

    /**
     * 设置 EditText 的 filters
     * @param editText {@link EditText}
     * @param filters  过滤器
     * @return {@link EditText} 入参本身，便于链式调用
     */
    public static EditText setFilters(
            final EditText editText,
            final InputFilter... filters
    ) {
        if (editText != null) {
            editText.setFilters(merge(filters));
        }
        return editText;
    }

    // =================
    // = 与 EditTextView 对齐的预设 =
    // =================

    /**
     * 单行输入常用组合：禁止回车、禁止首空格、最大长度
     * @param maxLength 最大字符长度
     * @return {@link InputFilter} 预设数组
     */
    public static InputFilter[] singleLineWithMaxLength(final int maxLength) {
        return merge(
                NO_ENTER,
                FRONT_SPACE,
                new MaxLengthInputFilter(maxLength)
        );
    }

    /**
     * 多行输入常用组合：禁止首空格、最大长度 ( 允许换行 )
     * @param maxLength 最大字符长度
     * @return {@link InputFilter} 预设数组
     */
    public static InputFilter[] multiLineWithMaxLength(final int maxLength) {
        return merge(
                FRONT_SPACE,
                new MaxLengthInputFilter(maxLength)
        );
    }

    /**
     * 单行输入：禁止回车、禁止首空格 ( 无长度限制 )
     * @return {@link InputFilter} 预设数组
     */
    public static InputFilter[] singleLineDefault() {
        return merge(NO_ENTER, FRONT_SPACE);
    }
}