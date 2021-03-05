package dev.widget.ui.round;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * detail: 圆角 RelativeLayout
 * @author Ttt
 */
public class RoundRelativeLayout
        extends RelativeLayout {

    public RoundRelativeLayout(Context context) {
        super(context);
        initAttrs(context, null, 0);
    }

    public RoundRelativeLayout(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public RoundRelativeLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundRelativeLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs, defStyleAttr);
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