package dev.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * detail: 自动调节高度 WebView
 * @author Ttt
 */
public class AdjustHeightWebView extends WebView {

    public AdjustHeightWebView(Context context) {
        super(context);
    }

    public AdjustHeightWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustHeightWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}