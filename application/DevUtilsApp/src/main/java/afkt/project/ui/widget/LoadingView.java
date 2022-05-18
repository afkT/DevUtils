package afkt.project.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import afkt.project.R;
import afkt.project.ui.widget.render.LoadingDrawable;
import afkt.project.ui.widget.render.LoadingRenderer;
import afkt.project.ui.widget.render.LoadingRendererFactory;

import static dev.kotlin.engine.log.LogKt.log_e;

/**
 * detail: LoadingView
 * @author dinuscxj
 * <pre>
 *     @see <a href="https://github.com/dinuscxj/LoadingDrawable"/>
 * </pre>
 */
public class LoadingView
        extends AppCompatImageView {
    private LoadingDrawable mLoadingDrawable;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(
            Context context,
            AttributeSet attrs
    ) {
        try {
            TypedArray      a                 = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
            int             loadingRendererId = a.getInt(R.styleable.LoadingView_loading_renderer, 0);
            LoadingRenderer loadingRenderer   = LoadingRendererFactory.createLoadingRenderer(context, loadingRendererId);
            setLoadingRenderer(loadingRenderer);
            a.recycle();
        } catch (Exception e) {
            log_e(null, e);
        }
    }

    public void setLoadingRenderer(LoadingRenderer loadingRenderer) {
        mLoadingDrawable = new LoadingDrawable(loadingRenderer);
        setImageDrawable(mLoadingDrawable);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    protected void onVisibilityChanged(
            View changedView,
            int visibility
    ) {
        super.onVisibilityChanged(changedView, visibility);

        final boolean visible = visibility == VISIBLE && getVisibility() == VISIBLE;
        if (visible) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    private void startAnimation() {
        if (mLoadingDrawable != null) {
            mLoadingDrawable.start();
        }
    }

    private void stopAnimation() {
        if (mLoadingDrawable != null) {
            mLoadingDrawable.stop();
        }
    }
}