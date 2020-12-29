package dev.widget.adjust;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * detail: 自动调节高度 ListView
 * @author Ttt
 * @Deprecated 推荐使用 NestedScrollView + RecyclerView 实现
 */
@Deprecated
public class AdjustHeightListView
        extends ListView {

    public AdjustHeightListView(Context context) {
        super(context);
    }

    public AdjustHeightListView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public AdjustHeightListView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdjustHeightListView(
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