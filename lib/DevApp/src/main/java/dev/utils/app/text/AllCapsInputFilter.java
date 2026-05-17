package dev.utils.app.text;

import android.os.Build;
import android.text.InputFilter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Locale;

/**
 * detail: 输入自动转大写
 * @author Ttt
 * <pre>
 *     继承 {@link InputFilter.AllCaps}。
 * </pre>
 */
public class AllCapsInputFilter
        extends InputFilter.AllCaps {

    /**
     * 使用系统默认 Locale 转大写
     */
    public AllCapsInputFilter() {
        super();
    }

    /**
     * 使用指定 Locale 转大写
     * @param locale 大写转换使用的 {@link Locale}
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public AllCapsInputFilter(@NonNull final Locale locale) {
        super(locale);
    }
}