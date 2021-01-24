package dev.widget.function;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

import dev.widget.R;

/**
 * detail: 自定义 EditText 右边 Icon
 * @author Ttt
 * <pre>
 *     // 设置触发范围倍数
 *     app:dev_rangeMultiple=""
 *     // 控制是否显示右边 Icon
 *     app:dev_drawRightIcon=""
 *     // 设置右边 Icon
 *     android:drawableRight="@drawable/xx"
 *     输入监听可调用 {@link #addTextChangedListener} 或 {@link #setTextWatcher}
 * </pre>
 */
public class RightIconEditText
        extends AppCompatEditText {

    // drawable ( left、top、right、bottom 四个方向图片 )
    private Drawable mLeft, mTop, mRight, mBottom;
    // Right Drawable 自身坐标信息
    private Rect        mRightBounds;
    // 右边 Icon 触发范围倍数
    private float       mRangeMultiple   = 2.0f;
    // 是否绘制右边 Icon
    private boolean     mIsDrawRightIcon = true;
    // 输入监听回调
    private TextWatcher mTextWatcher;

    public RightIconEditText(Context context) {
        super(context);
        init(context, null);
    }

    public RightIconEditText(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init(context, attrs);
    }

    public RightIconEditText(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public void setCompoundDrawables(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    ) {
        if (mLeft == null) this.mLeft = left;
        if (mTop == null) this.mTop = top;
        if (mRight == null) this.mRight = right;
        if (mBottom == null) this.mBottom = bottom;
        super.setCompoundDrawables(left, top, mIsDrawRightIcon ? right : null, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 判断抬起时, 是否在 right drawable 上
        if (mIsDrawRightIcon && mRight != null && event.getAction() == MotionEvent.ACTION_UP) {
            // 获取 drawable 自身坐标信息
            this.mRightBounds = mRight.getBounds();
            mRight.getIntrinsicWidth();
            // 获取触摸坐标
            int eventX = (int) event.getX();
            int width  = mRightBounds.width();
            int right  = getRight();
            int left   = getLeft();
            // icon 点击范围 ( 用于小 Icon 扩大触发范围使用 )
            int iconRange = (int) (width * mRangeMultiple);
            // 判断是否按在 right drawable 上
            if (eventX > (right - left - iconRange)) {
                setText(""); // 清空内容
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize()
            throws Throwable {
        super.finalize();
        // GC Finalize Daemon 回收资源
        this.mLeft = null;
        this.mTop = null;
        this.mRight = null;
        this.mBottom = null;
        this.mRightBounds = null;
    }

    /**
     * 获取右边 Icon 触发范围倍数
     * @return 右边 Icon 触发范围倍数
     */
    public float getRangeMultiple() {
        return mRangeMultiple;
    }

    /**
     * 设置右边 Icon 触发范围倍数
     * @param rangeMultiple 触发范围倍数
     * @return {@link RightIconEditText}
     */
    public RightIconEditText setRangeMultiple(float rangeMultiple) {
        this.mRangeMultiple = rangeMultiple;
        return this;
    }

    /**
     * 是否绘制右边 Icon
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDrawRightIcon() {
        return mIsDrawRightIcon;
    }

    /**
     * 设置是否绘制右边 Icon
     * @param drawRightIcon {@code true} yes, {@code false} no
     * @return {@link RightIconEditText}
     */
    public RightIconEditText setDrawRightIcon(boolean drawRightIcon) {
        this.mIsDrawRightIcon = drawRightIcon;
        postInvalidate();
        return this;
    }

    /**
     * 获取右边 Icon Drawable
     * @return {@link Drawable}
     */
    public Drawable getRightIcon() {
        return mRight;
    }

    /**
     * 设置右边 Icon Drawable
     * @param right 右边 Icon
     * @return {@link RightIconEditText}
     */
    public RightIconEditText setRightIcon(Drawable right) {
        this.mRight = right;
        drawOperate();
        return this;
    }

    /**
     * 设置输入监听回调
     * @param textWatcher {@link TextWatcher}
     * @return {@link RightIconEditText}
     */
    public RightIconEditText setTextWatcher(TextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
        return this;
    }

    // =

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    private void init(
            Context context,
            AttributeSet attrs
    ) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mRangeMultiple = a.getFloat(R.styleable.DevWidget_dev_rangeMultiple, 2.0f);
            mIsDrawRightIcon = a.getBoolean(R.styleable.DevWidget_dev_drawRightIcon, true);
            a.recycle();
        }
        drawOperate();
        addTextChangedListener(_listener);
    }

    /**
     * 绘制操作 ( 控制右边 Icon 显示 )
     */
    private void drawOperate() {
        setCompoundDrawables(mLeft, mTop, (length() == 0) ? null : mRight, mBottom);
    }

    /**
     * 内部 TextWatcher
     */
    private final TextWatcher _listener = new TextWatcher() {
        /**
         * 在文本变化前调用
         * @param s     修改之前的文字
         * @param start 字符串中即将发生修改的位置
         * @param count 字符串中即将被修改的文字的长度, 如果是新增的话则为 0
         * @param after 被修改的文字修改之后的长度, 如果是删除的话则为 0
         */
        @Override
        public void beforeTextChanged(
                CharSequence s,
                int start,
                int count,
                int after
        ) {
            if (mTextWatcher != null) {
                mTextWatcher.beforeTextChanged(s, start, count, after);
            }
        }

        /**
         * 在文本变化后调用
         * @param s      改变后的字符串
         * @param start  有变动的字符串的位置
         * @param before 被改变的字符串长度, 如果是新增则为 0
         * @param count  添加的字符串长度, 如果是删除则为 0
         */
        @Override
        public void onTextChanged(
                CharSequence s,
                int start,
                int before,
                int count
        ) {
            if (mTextWatcher != null) {
                mTextWatcher.onTextChanged(s, start, before, count);
            }
        }

        /**
         * 在文本变化后调用
         * @param s 修改后的文字
         */
        @Override
        public void afterTextChanged(Editable s) {
            if (mTextWatcher != null) {
                mTextWatcher.afterTextChanged(s);
            }
            drawOperate();
        }
    };
}