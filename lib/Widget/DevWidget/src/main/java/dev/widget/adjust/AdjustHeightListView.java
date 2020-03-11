package dev.widget.adjust;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * detail: 自动调节高度 ListView
 * @author Ttt
 */
public class AdjustHeightListView extends ListView {

    public AdjustHeightListView(Context context) {
        super(context);
    }

    public AdjustHeightListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustHeightListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}