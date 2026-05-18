package dev.utils.app.text.input_filter;

import android.os.Build;
import android.text.InputFilter;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Locale;

/**
 * detail: 输入自动转小写 
 * @author Ttt
 * <pre>
 *     与 {@link AllCapsInputFilter} 对应；系统无 {@code InputFilter.AllLower}，故自行实现。
 * </pre>
 */
public class AllLowerCaseInputFilter
        implements InputFilter {

    @Nullable
    private final Locale mLocale;

    /**
     * 按字符转小写
     */
    public AllLowerCaseInputFilter() {
        mLocale = null;
    }

    /**
     * 使用指定 Locale 转小写 
     * @param locale 小写转换使用的 {@link Locale}
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public AllLowerCaseInputFilter(@NonNull final Locale locale) {
        mLocale = locale;
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
        if (source == null) return null;
        try {
            boolean needLower = false;
            for (int i = start; i < end; i++) {
                if (Character.isUpperCase(source.charAt(i))) {
                    needLower = true;
                    break;
                }
            }
            if (!needLower) return null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && mLocale != null) {
                return source.subSequence(start, end).toString().toLowerCase(mLocale);
            }
            StringBuilder builder = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                builder.append(Character.toLowerCase(source.charAt(i)));
            }
            return builder;
        } catch (Throwable ignored) {
            return null;
        }
    }
}