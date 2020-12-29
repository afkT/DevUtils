package dev.widget.adjust;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * detail: 自动调节高度 GridView
 * @author Ttt
 * @Deprecated 推荐使用 NestedScrollView + RecyclerView 实现
 */
@Deprecated
public class AdjustHeightGridView
        extends GridView {

    public AdjustHeightGridView(Context context) {
        super(context);
    }

    public AdjustHeightGridView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public AdjustHeightGridView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdjustHeightGridView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}