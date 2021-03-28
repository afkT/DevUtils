package dev.widget.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import dev.utils.app.ViewUtils;

/**
 * detail: 自动同比放大 ImageView
 * <pre>
 *     铺满宽度, 高度根据比例自动缩放
 *     如需要圆角, 推荐使用 CardView 包装进行裁剪 View, 减少因全图展示对 Bitmap 操作出现 OOM
 * </pre>
 * @author Ttt
 */
public class ResizableImageView
        extends AppCompatImageView {

    // 缩放后的高度
    private int                    mZoomHeight;
    // 宽高监听
    private ViewUtils.OnWHListener mWHListener;

    public ResizableImageView(Context context) {
        super(context);
    }

    public ResizableImageView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
    }

    public ResizableImageView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        Drawable bg = getDrawable();
        if (bg != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            // 高度根据使得图片的宽度充满屏幕计算而得
            int height = (int) Math.ceil((float) width * (float) bg.getIntrinsicHeight() / (float) bg.getIntrinsicWidth());
            // 保存缩放后的高度
            this.mZoomHeight = height;
            setMeasuredDimension(width, height);
            // 触发回调
            if (mWHListener != null) {
                mWHListener.onWidthHeight(this, width, height);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取缩放后的高度
     * @return 缩放后的高度
     */
    public int getZoomHeight() {
        return mZoomHeight;
    }

    /**
     * 获取宽高监听事件
     * @return {@link dev.utils.app.ViewUtils.OnWHListener}
     */
    public ViewUtils.OnWHListener getWHListener() {
        return mWHListener;
    }

    /**
     * 设置宽高监听事件
     * @param whListener {@link dev.utils.app.ViewUtils.OnWHListener}
     * @return {@link ResizableImageView}
     */
    public ResizableImageView setWHListener(ViewUtils.OnWHListener whListener) {
        this.mWHListener = whListener;
        return this;
    }
}