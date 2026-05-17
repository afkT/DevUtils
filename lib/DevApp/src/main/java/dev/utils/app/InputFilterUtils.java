package dev.utils.app;

import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.utils.LogPrintUtils;
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
     *     内部会先对入参执行 {@link InputFilterUtils#merge(InputFilter...)}，忽略 null 元素。
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
            textView.setFilters(merge(filters));
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
     *     追加段中的 null 元素会被忽略。
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
     * 合并并设置 InputFilter ( 保留原有并合并入参 )
     * <pre>
     *     入参会先经 {@link InputFilterUtils#merge(InputFilter...)} 去 null，再与已有 filters 合并后设置。
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
            textView.setFilters(append(textView.getFilters(), merge(filters)));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "mergeFilters");
        }
        return false;
    }

    /**
     * 合并并设置 InputFilter ( 保留原有并合并入参 )
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

    // ==================
    // = 常用 Filter 单例 =
    // ==================

    /**
     * 禁止回车换行
     */
    public static final InputFilter NO_ENTER = new NoEnterInputFilter();

    /**
     * 禁止首字符为空格
     */
    public static final InputFilter FRONT_SPACE = new FrontSpaceInputFilter();

    /**
     * 单行输入常用组合：禁止回车、禁止首空格、最大长度
     * @param maxLength 最大字符长度
     * @return {@link InputFilter} 预设数组
     */
    public static InputFilter[] singleLineWithMaxLength(final int maxLength) {
        return merge(
                NO_ENTER, FRONT_SPACE,
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