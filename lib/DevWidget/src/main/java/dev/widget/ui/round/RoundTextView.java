package dev.widget.ui.round;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * detail: 圆角 TextView
 * @author Ttt
 */
public class RoundTextView
        extends AppCompatTextView {

    public RoundTextView(Context context) {
        super(context);
        initAttrs(context, null, 0);
    }

    public RoundTextView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public RoundTextView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, 0);
    }

    /**
     * 初始化
     * @param context      {@link Context}
     * @param attrs        {@link AttributeSet}
     * @param defStyleAttr 默认样式 {@link AttributeSet}
     */
    private void initAttrs(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        RoundDrawable bg = RoundDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        RoundDrawable.setBackgroundKeepingPadding(this, bg);
    }
}