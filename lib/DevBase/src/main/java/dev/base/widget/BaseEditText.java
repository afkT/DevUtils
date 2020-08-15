package dev.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * detail: Base EditText
 * @author Ttt
 * <pre>
 *     便于全局控制, 替换字体、样式等
 * </pre>
 */
public class BaseEditText extends AppCompatEditText {

    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
