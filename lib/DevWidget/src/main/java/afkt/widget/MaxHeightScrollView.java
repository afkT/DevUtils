package afkt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * detail: 最大高度限制 ScrollView
 * @author Ttt
 */
public class MaxHeightScrollView extends ScrollView {

    // 最大高度
    private int mMaxHeight = 0;

    public MaxHeightScrollView(Context context) {
        this(context, null);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 获取 View 最大高度
     * @return View 最大高度
     */
    public int getMaxHeight() {
        return mMaxHeight;
    }

    /**
     * 设置 View 最大高度
     * @param maxHeight View 最大高度
     * @return {@link MaxHeightScrollView}
     */
    public MaxHeightScrollView setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        return this;
    }
}