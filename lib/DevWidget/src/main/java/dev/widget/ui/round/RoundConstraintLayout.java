package dev.widget.ui.round;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * detail: 圆角 ConstraintLayout
 * @author Ttt
 * <pre>
 *     注意事项:
 *     因为该控件的圆角采用 View 的 background 实现, 所以与原生的 android:background 有冲突
 *     如果在 xml 中用 android:background 指定 background, 该 background 不会生效
 *     <p></p>
 *     该类使用 QMUI QMUIRoundButtonDrawable 代码, 减少非必要代码依赖
 *     <p></p>
 *     app:dev_backgroundColor=""
 *     app:dev_borderColor=""
 *     app:dev_borderWidth=""
 *     app:dev_isRadiusAdjustBounds=""
 *     app:dev_radius=""
 *     app:dev_radiusLeftBottom=""
 *     app:dev_radiusLeftTop=""
 *     app:dev_radiusRightBottom=""
 *     app:dev_radiusRightTop=""
 * </pre>
 */
public class RoundConstraintLayout
        extends ConstraintLayout {

    public RoundConstraintLayout(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public RoundConstraintLayout(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public RoundConstraintLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundConstraintLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化
     * @param context      {@link Context}
     * @param attrs        {@link AttributeSet}
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    private void initAttrs(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes
    ) {
        RoundDrawable bg = RoundDrawable.fromAttributeSet(context, attrs, defStyleAttr, defStyleRes);
        RoundDrawable.setBackgroundKeepingPadding(this, bg);
    }
}