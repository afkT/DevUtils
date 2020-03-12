package dev.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import dev.utils.app.TextViewUtils;

/**
 * detail: 换行通知 TextView
 * @author Ttt
 */
public class LineTextView extends AppCompatTextView {

    // 是否换行
    private boolean mIsNewLine = false;
    // 换行回调事件
    private OnNewLineCallBack mNewLineCallBack;

    public LineTextView(Context context) {
        this(context, null);
    }

    public LineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画笔
        Paint paint = getPaint();
        // 文本
        String text = this.getText().toString();
        // 计算文本一共多少行
        int line = TextViewUtils.calcTextLine(paint, text, getWidth() - getPaddingLeft() - getPaddingRight());
        // 是否换行
        mIsNewLine = line > 1;

        // 触发回调
        if (mNewLineCallBack != null) {
            mNewLineCallBack.onNewLine(mIsNewLine, line);
        }
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 判断是否换行
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNewLine() {
        return mIsNewLine;
    }

    /**
     * 设置换行回调事件
     * @param newLineCallBack {@link OnNewLineCallBack}
     * @return {@link LineTextView}
     */
    public LineTextView setNewLineCallBack(OnNewLineCallBack newLineCallBack) {
        this.mNewLineCallBack = newLineCallBack;
        return this;
    }

    /**
     * detail: 换行回调事件
     * @author Ttt
     */
    public interface OnNewLineCallBack {

        /**
         * 换行触发
         * @param isNewLine 是否换行
         * @param line      行数
         */
        void onNewLine(boolean isNewLine, int line);
    }
}