package dev.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * detail: 自定义右边清空 EditText
 * @author Ttt
 * <pre>
 *     android:drawableRight="@drawable/xx"
 *     输入监听可以 addTextChangedListener 或 setTextWatcher
 * </pre>
 */
public class RightClearEditText extends AppCompatEditText implements TextWatcher {

    // drawable - left、top、right、bootom 四个方向图片
    private Drawable mLeft, mTop, mRight, mBottom;
    // Right Drawable 自身坐标信息
    private Rect mRightBounds;
    // 判断是否绘制右边图标
    private boolean mIsDrawRightIcon = true;
    // 输入监听
    private TextWatcher mTextWatcher;

    public RightClearEditText(Context context) {
        super(context);
        // 初始化
        init();
    }

    public RightClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化
        init();
    }

    public RightClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 绘制操作
        drawableOperate();
        // 增加文本监听器.
        addTextChangedListener(this);
    }

    /**
     * 绘制操作 ( 输入框右边的图标显示控制 )
     */
    private void drawableOperate() {
        if (length() == 0) {
            setCompoundDrawables(mLeft, mTop, null, mBottom);
        } else {
            setCompoundDrawables(mLeft, mTop, mRight, mBottom);
        }
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
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
            int width = mRightBounds.width();
            int right = getRight();
            int left = getLeft();
            // 判断是否在 right drawable 上
            if (eventX > (right - width * 2 - left)) {
                setText(""); // 清空内容
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // GC Finalize Daemon 回收资源
        this.mLeft = null;
        this.mTop = null;
        this.mRight = null;
        this.mBottom = null;
        this.mRightBounds = null;
    }

    /**
     * 是否绘制右边图标
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDrawRightIcon() {
        return mIsDrawRightIcon;
    }

    /**
     * 设置是否绘制右边图标
     * @param drawRightIcon {@code true} yes, {@code false} no
     * @return {@link RightClearEditText}
     */
    public RightClearEditText setDrawRightIcon(boolean drawRightIcon) {
        mIsDrawRightIcon = drawRightIcon;
        postInvalidate();
        return this;
    }

    /**
     * 设置右边按钮图片
     * @param right 右边清空按钮
     * @return {@link RightClearEditText}
     */
    public RightClearEditText setRightDrawable(Drawable right) {
        this.mRight = right;
        // 绘制操作
        drawableOperate();
        return this;
    }

    /**
     * 设置输入监听回调
     * @param textWatcher {@link TextWatcher}
     * @return {@link RightClearEditText}
     */
    public RightClearEditText setTextWatcher(TextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
        return this;
    }

    // ===============
    // = TextWatcher =
    // ===============

    /**
     * 在文本变化前调用
     * @param s     修改之前的文字
     * @param start 字符串中即将发生修改的位置
     * @param count 字符串中即将被修改的文字的长度, 如果是新增的话则为 0
     * @param after 被修改的文字修改之后的长度, 如果是删除的话则为 0
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
    public void onTextChanged(CharSequence s, int start, int before, int count) {
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
        drawableOperate();
    }
}