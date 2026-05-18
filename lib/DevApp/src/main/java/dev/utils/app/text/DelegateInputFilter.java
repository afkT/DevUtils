package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 委托型输入过滤，构造时传入单字符谓词与/或全文校验 
 * @author Ttt
 * <pre>
 *     用于替代「一规则一类」的薄包装 Filter；
 *     单字符规则示例：{@code new DelegateInputFilter(InputFilterCharUtils::isHex)}；
 *     全文规则示例：{@code new DelegateInputFilter(merged -> !merged.startsWith("0") || merged.length() <= 1)}。
 * </pre>
 */
public class DelegateInputFilter
        implements InputFilter {

    private final InputFilterCharUtils.CharPredicate     mCharPredicate;
    private final InputFilterCharUtils.FullTextValidator mFullTextValidator;

    /**
     * 仅单字符谓词 
     * @param charPredicate 单字符谓词
     */
    public DelegateInputFilter(final InputFilterCharUtils.CharPredicate charPredicate) {
        this(charPredicate, null);
    }

    /**
     * 仅全文校验 
     * @param fullTextValidator 合并后全文校验
     */
    public DelegateInputFilter(final InputFilterCharUtils.FullTextValidator fullTextValidator) {
        this(null, fullTextValidator);
    }

    /**
     * 构造函数 
     * @param charPredicate     单字符谓词，可为 null
     * @param fullTextValidator 全文校验，可为 null
     */
    public DelegateInputFilter(
            final InputFilterCharUtils.CharPredicate charPredicate,
            final InputFilterCharUtils.FullTextValidator fullTextValidator
    ) {
        if (charPredicate == null && fullTextValidator == null) {
            throw new IllegalArgumentException("charPredicate and fullTextValidator cannot both be null");
        }
        mCharPredicate     = charPredicate;
        mFullTextValidator = fullTextValidator;
    }

    /**
     * 过滤本次输入片段 
     * @param source 新输入内容
     * @param start  新输入起始下标
     * @param end    新输入结束下标，不含
     * @param dest   已有文本
     * @param dstart 替换区间起始
     * @param dend   替换区间结束，不含
     * @return 过滤后的替换内容，null 表示接受原输入
     */
    @Override
    public CharSequence filter(
            CharSequence source,
            int start,
            int end,
            Spanned dest,
            int dstart,
            int dend
    ) {
        return InputFilterCharUtils.applyCharAndFullTextFilters(
                source, start, end, dest, dstart, dend,
                mCharPredicate, mFullTextValidator
        );
    }
}