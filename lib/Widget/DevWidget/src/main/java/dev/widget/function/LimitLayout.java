package dev.widget.function;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import dev.utils.app.WidgetUtils;
import dev.widget.R;

/**
 * detail: 自定义 FrameLayout 设置最大、最小宽高
 * @author Ttt
 * <pre>
 *     app:dev_maxWidth=""
 *     app:dev_maxHeight=""
 * </pre>
 */
public class LimitLayout extends FrameLayout {

    // 最大显示宽度
    private int mMaxWidth = WidgetUtils.DEF_VALUE;
    // 最大显示高度
    private int mMaxHeight = WidgetUtils.DEF_VALUE;

    public LimitLayout(Context context) {
        super(context);
    }

    public LimitLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
        mMaxWidth = a.getLayoutDimension(R.styleable.DevWidget_dev_maxWidth, WidgetUtils.DEF_VALUE);
        mMaxHeight = a.getLayoutDimension(R.styleable.DevWidget_dev_maxHeight, WidgetUtils.DEF_VALUE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] measureSpecs = WidgetUtils.viewMeasure(this, widthMeasureSpec, heightMeasureSpec, mMaxWidth, mMaxHeight);
        super.onMeasure(measureSpecs[0], measureSpecs[1]);
    }

    /**
     * 获取 View 最大显示宽度
     * @return View 最大显示宽度
     */
    public int getMaxWidth() {
        return mMaxWidth;
    }

    /**
     * 设置 View 最大显示宽度
     * @param maxWidth View 最大显示宽度
     * @return {@link LimitLayout}
     */
    public LimitLayout setMaxWidth(int maxWidth) {
        this.mMaxWidth = maxWidth;
        return this;
    }

    /**
     * 获取 View 最大显示高度
     * @return View 最大显示高度
     */
    public int getMaxHeight() {
        return mMaxHeight;
    }

    /**
     * 设置 View 最大显示高度
     * @param maxHeight View 最大显示高度
     * @return {@link LimitLayout}
     */
    public LimitLayout setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        return this;
    }
}