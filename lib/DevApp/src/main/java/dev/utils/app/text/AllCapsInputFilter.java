package dev.utils.app.text;

import android.os.Build;
import android.text.InputFilter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Locale;

/**
 * detail: 输入自动转大写 ( 继承 {@link InputFilter.AllCaps} )
 * @author Ttt
 */
public class AllCapsInputFilter
        extends InputFilter.AllCaps {

    /**
     * 构造函数
     */
    public AllCapsInputFilter() {
        super();
    }

    /**
     * 构造函数
     * @param locale 大写转换使用的 {@link Locale}
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public AllCapsInputFilter(@NonNull final Locale locale) {
        super(locale);
    }
}