package dev.utils.app;

import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
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
        return distinctFilters(
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
        return distinctFilters(
                FRONT_SPACE,
                new MaxLengthInputFilter(maxLength)
        );
    }

    /**
     * 单行输入：禁止回车、禁止首空格 ( 无长度限制 )
     * @return {@link InputFilter} 预设数组
     */
    public static InputFilter[] singleLineDefault() {
        return distinctFilters(NO_ENTER, FRONT_SPACE);
    }
}