package dev.widget.function;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import dev.utils.app.WidgetUtils;
import dev.widget.utils.WidgetAttrs;

/**
 * detail: 自定义 FrameLayout 设置最大、最小宽高
 * @author Ttt
 * <pre>
 *     app:dev_maxWidth=""
 *     app:dev_maxHeight=""
 * </pre>
 */
public class LimitLayout
        extends FrameLayout {

    private WidgetAttrs mWidgetAttrs;

    public LimitLayout(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public LimitLayout(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public LimitLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LimitLayout(
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
        mWidgetAttrs = new WidgetAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        int[] measureSpecs = WidgetUtils.viewMeasure(
                this, widthMeasureSpec, heightMeasureSpec,
                mWidgetAttrs.getMaxWidth(), mWidgetAttrs.getMaxHeight()
        );
        super.onMeasure(measureSpecs[0], measureSpecs[1]);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 View 最大显示宽度
     * @return View 最大显示宽度
     */
    public int getMaxWidth() {
        return mWidgetAttrs.getMaxWidth();
    }

    /**
     * 设置 View 最大显示宽度
     * @param maxWidth View 最大显示宽度
     * @return {@link LimitLayout}
     */
    public LimitLayout setMaxWidth(int maxWidth) {
        mWidgetAttrs.setMaxWidth(maxWidth);
        return this;
    }

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mWidgetAttrs.getMaxHeight();
    }

    /**
     * 设置 View 最大显示高度
     * @param maxHeight View 最大显示高度
     * @return {@link LimitLayout}
     */
    public LimitLayout setMaxHeight(final int maxHeight) {
        mWidgetAttrs.setMaxHeight(maxHeight);
        return this;
    }
}