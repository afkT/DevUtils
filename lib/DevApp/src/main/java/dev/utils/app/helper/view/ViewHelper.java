package dev.utils.app.helper.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import dev.utils.app.ClickUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ImageViewUtils;
import dev.utils.app.ListViewUtils;
import dev.utils.app.RecyclerViewUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.BaseHelper;
import dev.utils.app.helper.flow.FlowHelper;
import dev.utils.common.ForUtils;

/**
 * detail: View 链式调用快捷设置 Helper 类
 * @author Ttt
 * <pre>
 *     通过 DevApp 工具类快捷实现
 *     <p></p>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 * </pre>
 */
public final class ViewHelper
        extends BaseHelper<ViewHelper>
        implements IHelperByView<ViewHelper> {

    private ViewHelper() {
    }

    // ViewHelper
    private static final ViewHelper HELPER = new ViewHelper();

    /**
     * 获取单例 ViewHelper
     * @return {@link ViewHelper}
     */
    public static ViewHelper get() {
        return HELPER;
    }

    // ========
    // = Flow =
    // ========

    /**
     * 执行 Action 流方法
     * @param action Action
     * @return Helper
     */
    @Override
    public ViewHelper flow(FlowHelper.Action action) {
        if (action != null) action.action();
        return this;
    }

    // ================
    // = HandlerUtils =
    // ================

    /**
     * 在主线程 Handler 中执行任务
     * @param runnable 可执行的任务
     * @return Helper
     */
    @Override
    public ViewHelper postRunnable(Runnable runnable) {
        HandlerUtils.postRunnable(runnable);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @return Helper
     */
    @Override
    public ViewHelper postRunnable(
            Runnable runnable,
            long delayMillis
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @return Helper
     */
    @Override
    public ViewHelper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval);
        return this;
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     * @param runnable    可执行的任务
     * @param delayMillis 延迟时间
     * @param number      轮询次数
     * @param interval    轮询时间
     * @param listener    结束通知
     * @return Helper
     */
    @Override
    public ViewHelper postRunnable(
            Runnable runnable,
            long delayMillis,
            int number,
            int interval,
            HandlerUtils.OnEndListener listener
    ) {
        HandlerUtils.postRunnable(runnable, delayMillis, number, interval, listener);
        return this;
    }

    /**
     * 在主线程 Handler 中清除任务
     * @param runnable 需要清除的任务
     * @return Helper
     */
    @Override
    public ViewHelper removeRunnable(Runnable runnable) {
        HandlerUtils.removeRunnable(runnable);
        return this;
    }

    // =================
    // = IHelperByView =
    // =================

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper addTouchArea(
            int range,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.addTouchArea(value, range), views
        );
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param left   left range
     * @param top    top range
     * @param right  right range
     * @param bottom bottom range
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper addTouchArea(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.addTouchArea(value, left, top, right, bottom), views
        );
        return this;
    }

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setOnClick(
            View.OnClickListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnClick(value, listener), views
        );
        return this;
    }

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setOnLongClick(
            View.OnLongClickListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnLongClick(value, listener), views
        );
        return this;
    }

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setOnTouch(
            View.OnTouchListener listener,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.setOnTouch(value, listener), views
        );
        return this;
    }

    // =============
    // = ViewUtils =
    // =============

    /**
     * 设置 View Id
     * @param view {@link View}
     * @param id   View Id
     * @return Helper
     */
    @Override
    public ViewHelper setId(
            View view,
            int id
    ) {
        ViewUtils.setId(view, id);
        return this;
    }

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @param viewGroups   ViewGroup[]
     * @return Helper
     */
    @Override
    public ViewHelper setClipChildren(
            boolean clipChildren,
            ViewGroup... viewGroups
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setClipChildren(value, clipChildren), viewGroups
        );
        return this;
    }

    /**
     * 移除全部子 View
     * @param viewGroups ViewGroup[]
     * @return Helper
     */
    @Override
    public ViewHelper removeAllViews(ViewGroup... viewGroups) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.removeAllViews(value), viewGroups
        );
        return this;
    }

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @return Helper
     */
    @Override
    public ViewHelper addView(
            ViewGroup viewGroup,
            View child
    ) {
        ViewUtils.addView(viewGroup, child);
        return this;
    }

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param index     添加位置索引
     * @return Helper
     */
    @Override
    public ViewHelper addView(
            ViewGroup viewGroup,
            View child,
            int index
    ) {
        ViewUtils.addView(viewGroup, child, index);
        return this;
    }

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param index     添加位置索引
     * @param params    LayoutParams
     * @return Helper
     */
    @Override
    public ViewHelper addView(
            ViewGroup viewGroup,
            View child,
            int index,
            ViewGroup.LayoutParams params
    ) {
        ViewUtils.addView(viewGroup, child, index, params);
        return this;
    }

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param params    LayoutParams
     * @return Helper
     */
    @Override
    public ViewHelper addView(
            ViewGroup viewGroup,
            View child,
            ViewGroup.LayoutParams params
    ) {
        ViewUtils.addView(viewGroup, child, params);
        return this;
    }

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param width     View 宽度
     * @param height    View 高度
     * @return Helper
     */
    @Override
    public ViewHelper addView(
            ViewGroup viewGroup,
            View child,
            int width,
            int height
    ) {
        ViewUtils.addView(viewGroup, child, width, height);
        return this;
    }

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return Helper
     */
    @Override
    public ViewHelper setLayoutParams(
            View view,
            ViewGroup.LayoutParams params
    ) {
        ViewUtils.setLayoutParams(view, params);
        return this;
    }

    /**
     * 设置 View[] 宽度、高度
     * @param width  View 宽度
     * @param height View 高度
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setWidthHeight(
            int width,
            int height,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setWidthHeight(value, width, height), views
        );
        return this;
    }

    /**
     * 设置 View[] 宽度、高度
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setWidthHeight(
            int width,
            int height,
            boolean nullNewLP,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setWidthHeight(value, width, height, nullNewLP), views
        );
        return this;
    }

    /**
     * 设置 View weight 权重
     * @param weight 权重比例
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setWeight(
            float weight,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setWeight(value, weight), views
        );
        return this;
    }

    /**
     * 设置 View 宽度
     * @param width View 宽度
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setWidth(
            int width,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setWidth(value, width), views
        );
        return this;
    }

    /**
     * 设置 View 宽度
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setWidth(
            int width,
            boolean nullNewLP,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setWidth(value, width, nullNewLP), views
        );
        return this;
    }

    /**
     * 设置 View 高度
     * @param height View 高度
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHeight(
            int height,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setHeight(value, height), views
        );
        return this;
    }

    /**
     * 设置 View 高度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHeight(
            int height,
            boolean nullNewLP,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setHeight(value, height, nullNewLP), views
        );
        return this;
    }

    /**
     * 设置 View 最小宽度
     * @param minWidth 最小宽度
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMinimumWidth(
            int minWidth,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMinimumWidth(value, minWidth), views
        );
        return this;
    }

    /**
     * 设置 View 最小高度
     * @param minHeight 最小高度
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMinimumHeight(
            int minHeight,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMinimumHeight(value, minHeight), views
        );
        return this;
    }

    /**
     * 设置 View 透明度
     * @param alpha 透明度
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAlpha(
            @FloatRange(from = 0.0, to = 1.0) float alpha,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setAlpha(value, alpha), views
        );
        return this;
    }

    /**
     * 设置 View TAG
     * @param view   View
     * @param object TAG
     * @return Helper
     */
    @Override
    public ViewHelper setTag(
            View view,
            Object object
    ) {
        ViewUtils.setTag(view, object);
        return this;
    }

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param value X 轴坐标
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScrollX(
            int value,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                view -> ViewUtils.setScrollX(view, value), views
        );
        return this;
    }

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param value Y 轴坐标
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScrollY(
            int value,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                view -> ViewUtils.setScrollY(view, value), views
        );
        return this;
    }

    /**
     * 设置 ViewGroup 和其子控件两者之间的关系
     * <pre>
     *     beforeDescendants : ViewGroup 会优先其子类控件而获取到焦点
     *     afterDescendants : ViewGroup 只有当其子类控件不需要获取焦点时才获取焦点
     *     blocksDescendants : ViewGroup 会覆盖子类控件而直接获得焦点
     *     android:descendantFocusability="blocksDescendants"
     * </pre>
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @param viewGroups   ViewGroup[]
     * @return Helper
     */
    @Override
    public ViewHelper setDescendantFocusability(
            int focusability,
            ViewGroup... viewGroups
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setDescendantFocusability(value, focusability), viewGroups
        );
        return this;
    }

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @param views          View[]
     * @return Helper
     */
    @Override
    public ViewHelper setOverScrollMode(
            int overScrollMode,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setOverScrollMode(value, overScrollMode), views
        );
        return this;
    }

    /**
     * 设置是否绘制横向滚动条
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @param views                      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHorizontalScrollBarEnabled(
            boolean horizontalScrollBarEnabled,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setHorizontalScrollBarEnabled(value, horizontalScrollBarEnabled), views
        );
        return this;
    }

    /**
     * 设置是否绘制垂直滚动条
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @param views                    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setVerticalScrollBarEnabled(
            boolean verticalScrollBarEnabled,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setVerticalScrollBarEnabled(value, verticalScrollBarEnabled), views
        );
        return this;
    }

    /**
     * 设置 View 滚动效应
     * @param isScrollContainer 是否需要滚动效应
     * @param views             View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScrollContainer(
            boolean isScrollContainer,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setScrollContainer(value, isScrollContainer), views
        );
        return this;
    }

    /**
     * 设置 View 是否使用 Outline 来裁剪
     * @param clipToOutline {@code true} yes, {@code false} no
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setClipToOutline(
            boolean clipToOutline,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setClipToOutline(value, clipToOutline), views
        );
        return this;
    }

    /**
     * 设置 View 轮廓裁剪、绘制
     * @param provider {@link ViewOutlineProvider}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setOutlineProvider(
            ViewOutlineProvider provider,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setOutlineProvider(value, provider), views
        );
        return this;
    }

    /**
     * 设置 View 轮廓裁剪、绘制并进行裁剪
     * @param provider {@link ViewOutlineProvider}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setOutlineProviderClip(
            ViewOutlineProvider provider,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setOutlineProviderClip(value, provider), views
        );
        return this;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param view               {@link View}
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public ViewHelper setNextFocusForwardId(
            View view,
            @IdRes int nextFocusForwardId
    ) {
        ViewUtils.setNextFocusForwardId(view, nextFocusForwardId);
        return this;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public ViewHelper setNextFocusDownId(
            View view,
            @IdRes int nextFocusDownId
    ) {
        ViewUtils.setNextFocusDownId(view, nextFocusDownId);
        return this;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public ViewHelper setNextFocusLeftId(
            View view,
            @IdRes int nextFocusLeftId
    ) {
        ViewUtils.setNextFocusLeftId(view, nextFocusLeftId);
        return this;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             {@link View}
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public ViewHelper setNextFocusRightId(
            View view,
            @IdRes int nextFocusRightId
    ) {
        ViewUtils.setNextFocusRightId(view, nextFocusRightId);
        return this;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          {@link View}
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public ViewHelper setNextFocusUpId(
            View view,
            @IdRes int nextFocusUpId
    ) {
        ViewUtils.setNextFocusUpId(view, nextFocusUpId);
        return this;
    }

    /**
     * 设置 View 旋转度数
     * @param rotation 旋转度数
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setRotation(
            float rotation,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setRotation(value, rotation), views
        );
        return this;
    }

    /**
     * 设置 View 水平旋转度数
     * @param rotationX 水平旋转度数
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setRotationX(
            float rotationX,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setRotationX(value, rotationX), views
        );
        return this;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param rotationY 竖直旋转度数
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setRotationY(
            float rotationY,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setRotationY(value, rotationY), views
        );
        return this;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param scaleX 水平方向缩放比例
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScaleX(
            float scaleX,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setScaleX(value, scaleX), views
        );
        return this;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param scaleY 竖直方向缩放比例
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScaleY(
            float scaleY,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setScaleY(value, scaleY), views
        );
        return this;
    }

    /**
     * 设置文本的显示方式
     * @param textAlignment 文本的显示方式
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextAlignment(
            int textAlignment,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setTextAlignment(value, textAlignment), views
            );
        }
        return this;
    }

    /**
     * 设置文本的显示方向
     * @param textDirection 文本的显示方向
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextDirection(
            int textDirection,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setTextDirection(value, textDirection), views
            );
        }
        return this;
    }

    /**
     * 设置水平方向偏转量
     * @param pivotX 水平方向偏转量
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPivotX(
            float pivotX,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPivotX(value, pivotX), views
        );
        return this;
    }

    /**
     * 设置竖直方向偏转量
     * @param pivotY 竖直方向偏转量
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPivotY(
            float pivotY,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPivotY(value, pivotY), views
        );
        return this;
    }

    /**
     * 设置水平方向的移动距离
     * @param translationX 水平方向的移动距离
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTranslationX(
            float translationX,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setTranslationX(value, translationX), views
        );
        return this;
    }

    /**
     * 设置竖直方向的移动距离
     * @param translationY 竖直方向的移动距离
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTranslationY(
            float translationY,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setTranslationY(value, translationY), views
        );
        return this;
    }

    /**
     * 设置 X 轴位置
     * @param x     X 轴位置
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setX(
            float x,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setX(value, x), views
        );
        return this;
    }

    /**
     * 设置 Y 轴位置
     * @param y     Y 轴位置
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setY(
            float y,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setY(value, y), views
        );
        return this;
    }

    /**
     * 设置 View 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLayerType(
            int layerType,
            Paint paint,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setLayerType(value, layerType, paint), views
        );
        return this;
    }

    /**
     * 请求重新对 View 布局
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper requestLayout(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.requestLayout(value), views
        );
        return this;
    }

    /**
     * View 请求获取焦点
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper requestFocus(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.requestFocus(value), views
        );
        return this;
    }

    /**
     * View 清除焦点
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper clearFocus(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.clearFocus(value), views
        );
        return this;
    }

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @param views                View[]
     * @return Helper
     */
    @Override
    public ViewHelper setFocusableInTouchMode(
            boolean focusableInTouchMode,
            View... views
    ) {
        ViewUtils.setFocusableInTouchMode(focusableInTouchMode, views);
        return this;
    }

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setFocusable(
            boolean focusable,
            View... views
    ) {
        ViewUtils.setFocusable(focusable, views);
        return this;
    }

    /**
     * 切换获取焦点状态
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleFocusable(View... views) {
        ViewUtils.toggleFocusable(views);
        return this;
    }

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setSelected(
            boolean selected,
            View... views
    ) {
        ViewUtils.setSelected(selected, views);
        return this;
    }

    /**
     * 切换选中状态
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleSelected(View... views) {
        ViewUtils.toggleSelected(views);
        return this;
    }

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setEnabled(
            boolean enabled,
            View... views
    ) {
        ViewUtils.setEnabled(enabled, views);
        return this;
    }

    /**
     * 切换 View 是否启用状态
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleEnabled(View... views) {
        ViewUtils.toggleEnabled(views);
        return this;
    }

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setClickable(
            boolean clickable,
            View... views
    ) {
        ViewUtils.setClickable(clickable, views);
        return this;
    }

    /**
     * 切换 View 是否可以点击状态
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleClickable(View... views) {
        ViewUtils.toggleClickable(views);
        return this;
    }

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLongClickable(
            boolean longClickable,
            View... views
    ) {
        ViewUtils.setLongClickable(longClickable, views);
        return this;
    }

    /**
     * 切换 View 是否可以长按状态
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleLongClickable(View... views) {
        ViewUtils.toggleLongClickable(views);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setVisibilitys(
            boolean isVisibility,
            View... views
    ) {
        ViewUtils.setVisibilitys(isVisibility, views);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setVisibilitys(
            int isVisibility,
            View... views
    ) {
        ViewUtils.setVisibilitys(isVisibility, views);
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setVisibilityINs(
            boolean isVisibility,
            View... views
    ) {
        ViewUtils.setVisibilityINs(isVisibility, views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param view  {@link View}
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleVisibilitys(
            View view,
            View... views
    ) {
        ViewUtils.toggleVisibilitys(view, views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleVisibilitys(
            View[] viewArrays,
            View... views
    ) {
        ViewUtils.toggleVisibilitys(viewArrays, views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleVisibilitys(
            int state,
            View[] viewArrays,
            View... views
    ) {
        ViewUtils.toggleVisibilitys(state, viewArrays, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper reverseVisibilitys(
            int state,
            View[] viewArrays,
            View... views
    ) {
        ViewUtils.reverseVisibilitys(state, viewArrays, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArrays   View[]
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper reverseVisibilitys(
            boolean isVisibility,
            View[] viewArrays,
            View... views
    ) {
        ViewUtils.reverseVisibilitys(isVisibility, viewArrays, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view  {@link View}
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper reverseVisibilitys(
            int state,
            View view,
            View... views
    ) {
        ViewUtils.reverseVisibilitys(state, view, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper reverseVisibilitys(
            boolean isVisibility,
            View view,
            View... views
    ) {
        ViewUtils.reverseVisibilitys(isVisibility, view, views);
        return this;
    }

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper toggleViews(
            boolean isChange,
            int isVisibility,
            View... views
    ) {
        ViewUtils.toggleViews(isChange, isVisibility, views);
        return this;
    }

    /**
     * 把自身从父 View 中移除
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeSelfFromParent(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.removeSelfFromParent(value), views
        );
        return this;
    }

    /**
     * View 请求更新
     * @param allParent 是否全部父布局 View 都请求
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper requestLayoutParent(
            boolean allParent,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.requestLayoutParent(value, allParent), views
        );
        return this;
    }

    /**
     * 测量 View
     * @param specifiedWidth 指定宽度
     * @param views          View[]
     * @return Helper
     */
    @Override
    public ViewHelper measureView(
            int specifiedWidth,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.measureView(value, specifiedWidth), views
        );
        return this;
    }

    /**
     * 测量 View
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @param views           View[]
     * @return Helper
     */
    @Override
    public ViewHelper measureView(
            int specifiedWidth,
            int specifiedHeight,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.measureView(value, specifiedWidth, specifiedHeight), views
        );
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param gravity Gravity
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLayoutGravity(
            int gravity,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setLayoutGravity(value, gravity), views
        );
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLayoutGravity(
            int gravity,
            boolean isReflection,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setLayoutGravity(value, gravity, isReflection), views
        );
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginLeft(
            int leftMargin,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginLeft(value, leftMargin), views
        );
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginLeft(
            int leftMargin,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginLeft(value, leftMargin, reset), views
        );
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginTop(
            int topMargin,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginTop(value, topMargin), views
        );
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginTop(
            int topMargin,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginTop(value, topMargin, reset), views
        );
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginRight(
            int rightMargin,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginRight(value, rightMargin), views
        );
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginRight(
            int rightMargin,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginRight(value, rightMargin, reset), views
        );
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginBottom(
            int bottomMargin,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginBottom(value, bottomMargin), views
        );
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMarginBottom(
            int bottomMargin,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMarginBottom(value, bottomMargin, reset), views
        );
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMargin(
            int leftRight,
            int topBottom,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMargin(value, leftRight, topBottom), views
        );
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param margin Margin
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMargin(
            int margin,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMargin(value, margin), views
        );
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMargin(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setMargin(value, left, top, right, bottom), views
        );
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingLeft(
            int leftPadding,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingLeft(value, leftPadding), views
        );
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingLeft(
            int leftPadding,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingLeft(value, leftPadding, reset), views
        );
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingTop(
            int topPadding,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingTop(value, topPadding), views
        );
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingTop(
            int topPadding,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingTop(value, topPadding, reset), views
        );
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingRight(
            int rightPadding,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingRight(value, rightPadding), views
        );
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingRight(
            int rightPadding,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingRight(value, rightPadding, reset), views
        );
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingBottom(
            int bottomPadding,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingBottom(value, bottomPadding), views
        );
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaddingBottom(
            int bottomPadding,
            boolean reset,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPaddingBottom(value, bottomPadding, reset), views
        );
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPadding(
            int leftRight,
            int topBottom,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPadding(value, leftRight, topBottom), views
        );
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param padding Padding
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPadding(
            int padding,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPadding(value, padding), views
        );
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPadding(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setPadding(value, left, top, right, bottom), views
        );
        return this;
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper addRules(
            int verb,
            View... views
    ) {
        ViewUtils.addRules(verb, views);
        return this;
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper addRules(
            int verb,
            int subject,
            View... views
    ) {
        ViewUtils.addRules(verb, subject, views);
        return this;
    }

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeRules(
            int verb,
            View... views
    ) {
        ViewUtils.removeRules(verb, views);
        return this;
    }

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return Helper
     */
    @Override
    public ViewHelper setAnimation(
            View view,
            Animation animation
    ) {
        ViewUtils.setAnimation(view, animation);
        return this;
    }

    /**
     * 清空动画
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper clearAnimation(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.clearAnimation(value), views
        );
        return this;
    }

    /**
     * 启动动画
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper startAnimation(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.startAnimation(value), views
        );
        return this;
    }

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return Helper
     */
    @Override
    public ViewHelper startAnimation(
            View view,
            Animation animation
    ) {
        ViewUtils.startAnimation(view, animation);
        return this;
    }

    /**
     * 取消动画
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper cancelAnimation(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.cancelAnimation(value), views
        );
        return this;
    }

    /**
     * 设置背景图片
     * @param background 背景图片
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackground(
            Drawable background,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setBackground(value, background), views
        );
        return this;
    }

    /**
     * 设置背景颜色
     * @param color 背景颜色
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackgroundColor(
            @ColorInt int color,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setBackgroundColor(value, color), views
        );
        return this;
    }

    /**
     * 设置背景资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackgroundResource(
            @DrawableRes int resId,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setBackgroundResource(value, resId), views
        );
        return this;
    }

    /**
     * 设置背景着色颜色
     * @param tint  着色颜色
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackgroundTintList(
            ColorStateList tint,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setBackgroundTintList(value, tint), views
            );
        }
        return this;
    }

    /**
     * 设置背景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackgroundTintMode(
            PorterDuff.Mode tintMode,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setBackgroundTintMode(value, tintMode), views
            );
        }
        return this;
    }

    /**
     * 设置前景图片
     * @param foreground 前景图片
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setForeground(
            Drawable foreground,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setForeground(value, foreground), views
            );
        }
        return this;
    }

    /**
     * 设置前景重心
     * @param gravity 重心
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setForegroundGravity(
            int gravity,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setForegroundGravity(value, gravity), views
            );
        }
        return this;
    }

    /**
     * 设置前景着色颜色
     * @param tint  着色颜色
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setForegroundTintList(
            ColorStateList tint,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setForegroundTintList(value, tint), views
            );
        }
        return this;
    }

    /**
     * 设置前景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setForegroundTintMode(
            PorterDuff.Mode tintMode,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.setForegroundTintMode(value, tintMode), views
            );
        }
        return this;
    }

    /**
     * 移除背景图片
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeBackground(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.removeBackground(value), views
        );
        return this;
    }

    /**
     * 移除背景图片 ( background、imageDrawable )
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeAllBackground(View... views) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.removeAllBackground(value), views
        );
        return this;
    }

    /**
     * 移除前景图片
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeForeground(View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ForUtils.forSimpleArgs(
                    value -> ViewUtils.removeForeground(value), views
            );
        }
        return this;
    }

    /**
     * View 着色处理
     * @param color 颜色值
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setColorFilter(
            @ColorInt int color,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setColorFilter(value, color), views
        );
        return this;
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setColorFilter(
            Drawable drawable,
            @ColorInt int color,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setColorFilter(value, drawable, color), views
        );
        return this;
    }

    /**
     * View 着色处理
     * @param colorFilter 颜色过滤 ( 效果 )
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setColorFilter(
            ColorFilter colorFilter,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setColorFilter(value, colorFilter), views
        );
        return this;
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setColorFilter(
            Drawable drawable,
            ColorFilter colorFilter,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setColorFilter(value, drawable, colorFilter), views
        );
        return this;
    }

    /**
     * 设置 ProgressBar 进度条样式
     * @param drawable {@link Drawable}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setProgressDrawable(
            Drawable drawable,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setProgressDrawable(value, drawable), views
        );
        return this;
    }

    /**
     * 设置 ProgressBar 进度值
     * @param progress 当前进度
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBarProgress(
            int progress,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setBarProgress(value, progress), views
        );
        return this;
    }

    /**
     * 设置 ProgressBar 最大值
     * @param max   最大值
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBarMax(
            int max,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setBarMax(value, max), views
        );
        return this;
    }

    /**
     * 设置 ProgressBar 最大值
     * @param progress 当前进度
     * @param max      最大值
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBarValue(
            int progress,
            int max,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setBarValue(value, progress, max), views
        );
        return this;
    }

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param position 索引
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper smoothScrollToPosition(
            int position,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.smoothScrollToPosition(value, position), views
        );
        return this;
    }

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param position 索引
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper scrollToPosition(
            int position,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.scrollToPosition(value, position), views
        );
        return this;
    }

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper smoothScrollToTop(View... views) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.smoothScrollToTop(value), views
        );
        return this;
    }

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper scrollToTop(View... views) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.scrollToTop(value), views
        );
        return this;
    }

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部 )
     *     smoothScrollToBottom(view)
     *     smoothScrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper smoothScrollToBottom(View... views) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.smoothScrollToBottom(value), views
        );
        return this;
    }

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper scrollToBottom(View... views) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.scrollToBottom(value), views
        );
        return this;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param x     X 轴开始坐标
     * @param y     Y 轴开始坐标
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper smoothScrollTo(
            int x,
            int y,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.smoothScrollTo(value, x, y), views
        );
        return this;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param x     X 轴开始坐标
     * @param y     Y 轴开始坐标
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper smoothScrollBy(
            int x,
            int y,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.smoothScrollBy(value, x, y), views
        );
        return this;
    }

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper fullScroll(
            int direction,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.fullScroll(value, direction), views
        );
        return this;
    }

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x     X 轴开始坐标
     * @param y     Y 轴开始坐标
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper scrollTo(
            int x,
            int y,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.scrollTo(value, x, y), views
        );
        return this;
    }

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x     X 轴开始坐标
     * @param y     Y 轴开始坐标
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper scrollBy(
            int x,
            int y,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ListViewUtils.scrollBy(value, x, y), views
        );
        return this;
    }

    // ==================
    // = ImageViewUtils =
    // ==================

    /**
     * 设置 ImageView 是否保持宽高比
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @param imageViews       ImageView[]
     * @return Helper
     */
    @Override
    public ViewHelper setAdjustViewBounds(
            boolean adjustViewBounds,
            ImageView... imageViews
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setAdjustViewBounds(value, adjustViewBounds), imageViews
        );
        return this;
    }

    /**
     * 设置 ImageView 最大高度
     * @param maxHeight  最大高度
     * @param imageViews ImageView[]
     * @return Helper
     */
    @Override
    public ViewHelper setMaxHeight(
            int maxHeight,
            ImageView... imageViews
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setMaxHeight(value, maxHeight), imageViews
        );
        return this;
    }

    /**
     * 设置 ImageView 最大宽度
     * @param maxWidth   最大宽度
     * @param imageViews ImageView[]
     * @return Helper
     */
    @Override
    public ViewHelper setMaxWidth(
            int maxWidth,
            ImageView... imageViews
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setMaxWidth(value, maxWidth), imageViews
        );
        return this;
    }

    /**
     * 设置 ImageView Level
     * @param level level Image
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageLevel(
            int level,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setImageLevel(value, level), views
        );
        return this;
    }

    /**
     * 设置 ImageView Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageBitmap(
            Bitmap bitmap,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setImageBitmap(value, bitmap), views
        );
        return this;
    }

    /**
     * 设置 ImageView Drawable
     * @param drawable {@link Bitmap}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageDrawable(
            Drawable drawable,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setImageDrawable(value, drawable), views
        );
        return this;
    }

    /**
     * 设置 ImageView 资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageResource(
            @DrawableRes int resId,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setImageResource(value, resId), views
        );
        return this;
    }

    /**
     * 设置 ImageView Matrix
     * @param matrix {@link Matrix}
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageMatrix(
            Matrix matrix,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setImageMatrix(value, matrix), views
        );
        return this;
    }

    /**
     * 设置 ImageView 着色颜色
     * @param tint  着色颜色
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageTintList(
            ColorStateList tint,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ForUtils.forSimpleArgs(
                    value -> ImageViewUtils.setImageTintList(value, tint), views
            );
        }
        return this;
    }

    /**
     * 设置 ImageView 着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageTintMode(
            PorterDuff.Mode tintMode,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ForUtils.forSimpleArgs(
                    value -> ImageViewUtils.setImageTintMode(value, tintMode), views
            );
        }
        return this;
    }

    /**
     * 移除 ImageView Bitmap
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeImageBitmap(View... views) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.removeImageBitmap(value), views
        );
        return this;
    }

    /**
     * 移除 ImageView Drawable
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeImageDrawable(View... views) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.removeImageDrawable(value), views
        );
        return this;
    }

    /**
     * 设置 ImageView 缩放类型
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScaleType(
            ImageView.ScaleType scaleType,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ImageViewUtils.setScaleType(value, scaleType), views
        );
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackgroundResources(
            @DrawableRes int resId,
            View... views
    ) {
        ImageViewUtils.setBackgroundResources(resId, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBackgroundResources(
            @DrawableRes int resId,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setBackgroundResources(resId, isVisibility, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageResources(
            @DrawableRes int resId,
            View... views
    ) {
        ImageViewUtils.setImageResources(resId, views);
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageResources(
            @DrawableRes int resId,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setImageResources(resId, isVisibility, views);
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageBitmaps(
            Bitmap bitmap,
            View... views
    ) {
        ImageViewUtils.setImageBitmaps(bitmap, views);
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageBitmaps(
            Bitmap bitmap,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setImageBitmaps(bitmap, isVisibility, views);
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageDrawables(
            Drawable drawable,
            View... views
    ) {
        ImageViewUtils.setImageDrawables(drawable, views);
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImageDrawables(
            Drawable drawable,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setImageDrawables(drawable, isVisibility, views);
        return this;
    }

    /**
     * 移除 View Bitmap
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeImageBitmaps(View... views) {
        ImageViewUtils.removeImageBitmaps(views);
        return this;
    }

    /**
     * 移除 View Bitmap
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeImageBitmaps(
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.removeImageBitmaps(isVisibility, views);
        return this;
    }

    /**
     * 移除 View Drawable
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeImageDrawables(View... views) {
        ImageViewUtils.removeImageDrawables(views);
        return this;
    }

    /**
     * 移除 View Drawable
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper removeImageDrawables(
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.removeImageDrawables(isVisibility, views);
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScaleTypes(
            ImageView.ScaleType scaleType,
            View... views
    ) {
        ImageViewUtils.setScaleTypes(scaleType, views);
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    @Override
    public ViewHelper setScaleTypes(
            ImageView.ScaleType scaleType,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setScaleTypes(scaleType, isVisibility, views);
        return this;
    }

    // ===============================
    // = EditTextUtils、TextViewUtils =
    // ===============================

    /**
     * 设置文本
     * @param text  TextView text
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setText(
            CharSequence text,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setText(EditTextUtils.getEditText(value), text);
                    } else {
                        TextViewUtils.setText(value, text);
                    }
                }, views
        );
        return this;
    }

    /**
     * 设置长度限制
     * @param maxLength 长度限制
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMaxLength(
            int maxLength,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setMaxLength(EditTextUtils.getEditText(value), maxLength);
                    } else {
                        TextViewUtils.setMaxLength(value, maxLength);
                    }
                }, views
        );
        return this;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param content   文本内容
     * @param maxLength 长度限制
     * @param views     View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMaxLengthAndText(
            CharSequence content,
            int maxLength,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setMaxLengthAndText(
                                EditTextUtils.getEditText(value), content, maxLength
                        );
                    } else {
                        TextViewUtils.setMaxLengthAndText(value, content, maxLength);
                    }
                }, views
        );
        return this;
    }

    /**
     * 设置输入类型
     * @param type  类型
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setInputType(
            int type,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setInputType(
                                EditTextUtils.getEditText(value), type
                        );
                    } else {
                        TextViewUtils.setInputType(value, type);
                    }
                }, views
        );
        return this;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param imeOptions 软键盘按钮类型
     * @param views      View[]
     * @return Helper
     */
    @Override
    public ViewHelper setImeOptions(
            int imeOptions,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setImeOptions(
                                EditTextUtils.getEditText(value), imeOptions
                        );
                    } else {
                        TextViewUtils.setImeOptions(value, imeOptions);
                    }
                }, views
        );
        return this;
    }

    /**
     * 设置文本视图显示转换
     * @param method {@link TransformationMethod}
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTransformationMethod(
            TransformationMethod method,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setTransformationMethod(
                                EditTextUtils.getEditText(value), method
                        );
                    } else {
                        TextViewUtils.setTransformationMethod(value, method);
                    }
                }, views
        );
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param views             View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTransformationMethod(
            boolean isDisplayPassword,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> {
                    if (value instanceof EditText) {
                        EditTextUtils.setTransformationMethod(
                                EditTextUtils.getEditText(value), isDisplayPassword
                        );
                    } else {
                        TextViewUtils.setTransformationMethod(value, isDisplayPassword);
                    }
                }, views
        );
        return this;
    }

    // =================
    // = EditTextUtils =
    // =================

    /**
     * 设置内容
     * @param content   文本内容
     * @param isSelect  是否设置光标
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setText(
            CharSequence content,
            boolean isSelect,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setText(value, content, isSelect), editTexts
        );
        return this;
    }

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param content   文本内容
     * @param isSelect  是否设置光标
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper insert(
            CharSequence content,
            boolean isSelect,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.insert(value, content, isSelect), editTexts
        );
        return this;
    }

    /**
     * 追加内容
     * @param content   文本内容
     * @param start     开始添加的位置
     * @param isSelect  是否设置光标
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper insert(
            CharSequence content,
            int start,
            boolean isSelect,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.insert(value, content, start, isSelect), editTexts
        );
        return this;
    }

    /**
     * 设置是否显示光标
     * @param visible   是否显示光标
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setCursorVisible(
            boolean visible,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setCursorVisible(value, visible), editTexts
        );
        return this;
    }

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @param editTexts          EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextCursorDrawable(
            @DrawableRes int textCursorDrawable,
            EditText... editTexts
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForUtils.forSimpleArgs(
                    value -> EditTextUtils.setTextCursorDrawable(value, textCursorDrawable), editTexts
            );
        }
        return this;
    }

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @param editTexts          EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextCursorDrawable(
            Drawable textCursorDrawable,
            EditText... editTexts
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForUtils.forSimpleArgs(
                    value -> EditTextUtils.setTextCursorDrawable(value, textCursorDrawable), editTexts
            );
        }
        return this;
    }

    /**
     * 设置光标在第一位
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setSelectionToTop(EditText... editTexts) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setSelectionToTop(value), editTexts
        );
        return this;
    }

    /**
     * 设置光标在最后一位
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setSelectionToBottom(EditText... editTexts) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setSelectionToBottom(value), editTexts
        );
        return this;
    }

    /**
     * 设置光标位置
     * @param index     光标位置
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setSelection(
            int index,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setSelection(value, index), editTexts
        );
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @param editTexts         EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setTransformationMethod(
            boolean isDisplayPassword,
            boolean isSelectBottom,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setTransformationMethod(
                        value, isDisplayPassword, isSelectBottom
                ), editTexts
        );
        return this;
    }

    /**
     * 添加输入监听事件
     * @param watcher   输入监听
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper addTextChangedListener(
            TextWatcher watcher,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.addTextChangedListener(value, watcher), editTexts
        );
        return this;
    }

    /**
     * 移除输入监听事件
     * @param watcher   输入监听
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper removeTextChangedListener(
            TextWatcher watcher,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.removeTextChangedListener(value, watcher), editTexts
        );
        return this;
    }

    /**
     * 设置 KeyListener
     * @param listener  {@link KeyListener}
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setKeyListener(
            KeyListener listener,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setKeyListener(value, listener), editTexts
        );
        return this;
    }

    /**
     * 设置 KeyListener
     * @param accepted  允许输入的内容, 如: 0123456789
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setKeyListener(
            String accepted,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setKeyListener(value, accepted), editTexts
        );
        return this;
    }

    /**
     * 设置 KeyListener
     * @param accepted  允许输入的内容
     * @param editTexts EditText[]
     * @return Helper
     */
    @Override
    public ViewHelper setKeyListener(
            char[] accepted,
            EditText... editTexts
    ) {
        ForUtils.forSimpleArgs(
                value -> EditTextUtils.setKeyListener(value, accepted), editTexts
        );
        return this;
    }

    // =================
    // = TextViewUtils =
    // =================

    /**
     * 设置 Hint 文本
     * @param text  Hint text
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHint(
            CharSequence text,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setHint(value, text), views
        );
        return this;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHintTextColors(
            @ColorInt int color,
            View... views
    ) {
        TextViewUtils.setHintTextColors(color, views);
        return this;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHintTextColors(
            ColorStateList colors,
            View... views
    ) {
        TextViewUtils.setHintTextColors(colors, views);
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextColors(
            @ColorInt int color,
            View... views
    ) {
        TextViewUtils.setTextColors(color, views);
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextColors(
            ColorStateList colors,
            View... views
    ) {
        TextViewUtils.setTextColors(colors, views);
        return this;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setHtmlTexts(
            String content,
            View... views
    ) {
        TextViewUtils.setHtmlTexts(content, views);
        return this;
    }

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTypeface(
            Typeface typeface,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTypeface(value, typeface), views
        );
        return this;
    }

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @param style    样式
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTypeface(
            Typeface typeface,
            int style,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTypeface(value, typeface, style), views
        );
        return this;
    }

    /**
     * 设置字体大小 ( px 像素 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextSizeByPx(
            float size,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTextSizeByPx(value, size), views
        );
        return this;
    }

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextSizeBySp(
            float size,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTextSizeBySp(value, size), views
        );
        return this;
    }

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextSizeByDp(
            float size,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTextSizeByDp(value, size), views
        );
        return this;
    }

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextSizeByIn(
            float size,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTextSizeByIn(value, size), views
        );
        return this;
    }

    /**
     * 设置字体大小
     * @param unit  字体参数类型
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextSize(
            int unit,
            float size,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTextSize(value, unit, size), views
        );
        return this;
    }

    /**
     * 清空 flags
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper clearFlags(View... views) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.clearFlags(value), views
        );
        return this;
    }

    /**
     * 设置 TextView flags
     * @param flags flags
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setPaintFlags(
            int flags,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setPaintFlags(value, flags), views
        );
        return this;
    }

    /**
     * 设置 TextView 抗锯齿 flags
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAntiAliasFlag(View... views) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setAntiAliasFlag(value), views
        );
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBold(View... views) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setBold(value), views
        );
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param isBold {@code true} yes, {@code false} no
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBold(
            boolean isBold,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setBold(value, isBold), views
        );
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setBold(
            Typeface typeface,
            boolean isBold,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setBold(value, typeface, isBold), views
        );
        return this;
    }

    /**
     * 设置下划线
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setUnderlineText(View... views) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setUnderlineText(value), views
        );
        return this;
    }

    /**
     * 设置下划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setUnderlineText(
            boolean isAntiAlias,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setUnderlineText(value, isAntiAlias), views
        );
        return this;
    }

    /**
     * 设置中划线
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setStrikeThruText(View... views) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setStrikeThruText(value), views
        );
        return this;
    }

    /**
     * 设置中划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setStrikeThruText(
            boolean isAntiAlias,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setStrikeThruText(value, isAntiAlias), views
        );
        return this;
    }

    /**
     * 设置文字水平间距
     * @param letterSpacing 文字水平间距
     * @param views         View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLetterSpacing(
            float letterSpacing,
            View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ForUtils.forSimpleArgs(
                    value -> TextViewUtils.setLetterSpacing(value, letterSpacing), views
            );
        }
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLineSpacing(
            float lineSpacing,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setLineSpacing(value, lineSpacing), views
        );
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 ) 、行间距倍数
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLineSpacingAndMultiplier(
            float lineSpacing,
            float multiplier,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setLineSpacingAndMultiplier(
                        value, lineSpacing, multiplier
                ), views
        );
        return this;
    }

    /**
     * 设置字体水平方向的缩放
     * @param size  缩放比例
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setTextScaleX(
            float size,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setTextScaleX(value, size), views
        );
        return this;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param includePadding 是否保留字体留白间隙区域
     * @param views          View[]
     * @return Helper
     */
    @Override
    public ViewHelper setIncludeFontPadding(
            boolean includePadding,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setIncludeFontPadding(value, includePadding), views
        );
        return this;
    }

    /**
     * 设置行数
     * @param lines 行数
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setLines(
            int lines,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setLines(value, lines), views
        );
        return this;
    }

    /**
     * 设置最大行数
     * @param maxLines 最大行数
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMaxLines(
            int maxLines,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setMaxLines(value, maxLines), views
        );
        return this;
    }

    /**
     * 设置最小行数
     * @param minLines 最小行数
     * @param views    View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMinLines(
            int minLines,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setMinLines(value, minLines), views
        );
        return this;
    }

    /**
     * 设置最大字符宽度限制
     * @param maxEms 最大字符
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMaxEms(
            int maxEms,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setMaxEms(value, maxEms), views
        );
        return this;
    }

    /**
     * 设置最小字符宽度限制
     * @param minEms 最小字符
     * @param views  View[]
     * @return Helper
     */
    @Override
    public ViewHelper setMinEms(
            int minEms,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setMinEms(value, minEms), views
        );
        return this;
    }

    /**
     * 设置指定字符宽度
     * @param ems   字符
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setEms(
            int ems,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setEms(value, ems), views
        );
        return this;
    }

    /**
     * 设置 Ellipsize 效果
     * @param where {@link TextUtils.TruncateAt}
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setEllipsize(
            TextUtils.TruncateAt where,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setEllipsize(value, where), views
        );
        return this;
    }

    /**
     * 设置自动识别文本链接
     * @param mask  {@link android.text.util.Linkify}
     * @param views View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAutoLinkMask(
            int mask,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setAutoLinkMask(value, mask), views
        );
        return this;
    }

    /**
     * 设置文本全为大写
     * @param allCaps 是否全部大写
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAllCaps(
            boolean allCaps,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setAllCaps(value, allCaps), views
        );
        return this;
    }

    /**
     * 设置 Gravity
     * @param gravity {@link android.view.Gravity}
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setGravity(
            int gravity,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setGravity(value, gravity), views
        );
        return this;
    }

    /**
     * 设置 CompoundDrawables Padding
     * @param padding   CompoundDrawables Padding
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablePadding(
            int padding,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablePadding(value, padding), textViews
        );
        return this;
    }

    /**
     * 设置 Left CompoundDrawables
     * @param left      left Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesByLeft(
            Drawable left,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesByLeft(value, left), textViews
        );
        return this;
    }

    /**
     * 设置 Top CompoundDrawables
     * @param top       top Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesByTop(
            Drawable top,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesByTop(value, top), textViews
        );
        return this;
    }

    /**
     * 设置 Right CompoundDrawables
     * @param right     right Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesByRight(
            Drawable right,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesByRight(value, right), textViews
        );
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesByBottom(
            Drawable bottom,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesByBottom(value, bottom), textViews
        );
        return this;
    }

    /**
     * 设置 CompoundDrawables
     * <pre>
     *     CompoundDrawable 的大小控制是通过 drawable.setBounds() 控制
     *     需要先设置 Drawable 的 setBounds
     *     {@link dev.utils.app.image.ImageUtils#setBounds}
     * </pre>
     * @param left      left Drawable
     * @param top       top Drawable
     * @param right     right Drawable
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawables(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawables(
                        value, left, top, right, bottom
                ), textViews
        );
        return this;
    }

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left      left Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByLeft(
            Drawable left,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByLeft(
                        value, left
                ), textViews
        );
        return this;
    }

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param top       top Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByTop(
            Drawable top,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByTop(
                        value, top
                ), textViews
        );
        return this;
    }

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param right     right Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByRight(
            Drawable right,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByRight(
                        value, right
                ), textViews
        );
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesWithIntrinsicBoundsByBottom(
            Drawable bottom,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByBottom(
                        value, bottom
                ), textViews
        );
        return this;
    }

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left      left Drawable
     * @param top       top Drawable
     * @param right     right Drawable
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    @Override
    public ViewHelper setCompoundDrawablesWithIntrinsicBounds(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom,
            TextView... textViews
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setCompoundDrawablesWithIntrinsicBounds(
                        value, left, top, right, bottom
                ), textViews
        );
        return this;
    }

    /**
     * 通过设置默认的自动调整大小配置, 决定是否自动缩放文本
     * @param autoSizeTextType 自动调整大小类型
     * @param views            View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAutoSizeTextTypeWithDefaults(
            @TextViewCompat.AutoSizeTextType int autoSizeTextType,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setAutoSizeTextTypeWithDefaults(
                        value, autoSizeTextType
                ), views
        );
        return this;
    }

    /**
     * 设置 TextView 自动调整字体大小配置
     * @param autoSizeMinTextSize     自动调整最小字体大小
     * @param autoSizeMaxTextSize     自动调整最大字体大小
     * @param autoSizeStepGranularity 自动调整大小变动粒度 ( 跨度区间值 )
     * @param unit                    字体参数类型
     * @param views                   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAutoSizeTextTypeUniformWithConfiguration(
            int autoSizeMinTextSize,
            int autoSizeMaxTextSize,
            int autoSizeStepGranularity,
            int unit,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setAutoSizeTextTypeUniformWithConfiguration(
                        value, autoSizeMinTextSize, autoSizeMaxTextSize,
                        autoSizeStepGranularity, unit
                ), views
        );
        return this;
    }

    /**
     * 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM
     * @param presetSizes 预设字体大小范围像素为单位
     * @param unit        字体参数类型
     * @param views       View[]
     * @return Helper
     */
    @Override
    public ViewHelper setAutoSizeTextTypeUniformWithPresetSizes(
            int[] presetSizes,
            int unit,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> TextViewUtils.setAutoSizeTextTypeUniformWithPresetSizes(
                        value, presetSizes, unit
                ), views
        );
        return this;
    }

    // =====================
    // = RecyclerViewUtils =
    // =====================

    /**
     * 设置 RecyclerView LayoutManager
     * @param view          {@link View}
     * @param layoutManager LayoutManager
     * @return Helper
     */
    @Override
    public ViewHelper setLayoutManager(
            View view,
            RecyclerView.LayoutManager layoutManager
    ) {
        RecyclerViewUtils.setLayoutManager(view, layoutManager);
        return this;
    }

    /**
     * 设置 GridLayoutManager SpanCount
     * @param view      {@link View}
     * @param spanCount Span Count
     * @return Helper
     */
    @Override
    public ViewHelper setSpanCount(
            View view,
            int spanCount
    ) {
        RecyclerViewUtils.setSpanCount(view, spanCount);
        return this;
    }

    /**
     * 设置 GridLayoutManager SpanCount
     * @param layoutManager LayoutManager
     * @param spanCount     Span Count
     * @return Helper
     */
    @Override
    public ViewHelper setSpanCount(
            RecyclerView.LayoutManager layoutManager,
            int spanCount
    ) {
        RecyclerViewUtils.setSpanCount(layoutManager, spanCount);
        return this;
    }

    /**
     * 设置 RecyclerView Orientation
     * @param view        {@link View}
     * @param orientation 方向
     * @return Helper
     */
    @Override
    public ViewHelper setOrientation(
            View view,
            @RecyclerView.Orientation int orientation
    ) {
        RecyclerViewUtils.setOrientation(view, orientation);
        return this;
    }

    /**
     * 设置 RecyclerView Adapter
     * @param view    {@link View}
     * @param adapter Adapter
     * @return Helper
     */
    @Override
    public ViewHelper setAdapter(
            View view,
            RecyclerView.Adapter<?> adapter
    ) {
        RecyclerViewUtils.setAdapter(view, adapter);
        return this;
    }

    /**
     * RecyclerView notifyItemRemoved
     * @param view     {@link View}
     * @param position 索引
     * @return Helper
     */
    @Override
    public ViewHelper notifyItemRemoved(
            View view,
            int position
    ) {
        RecyclerViewUtils.notifyItemRemoved(view, position);
        return this;
    }

    /**
     * RecyclerView notifyItemInserted
     * @param view     {@link View}
     * @param position 索引
     * @return Helper
     */
    @Override
    public ViewHelper notifyItemInserted(
            View view,
            int position
    ) {
        RecyclerViewUtils.notifyItemInserted(view, position);
        return this;
    }

    /**
     * RecyclerView notifyItemMoved
     * @param view         {@link View}
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return Helper
     */
    @Override
    public ViewHelper notifyItemMoved(
            View view,
            int fromPosition,
            int toPosition
    ) {
        RecyclerViewUtils.notifyItemMoved(view, fromPosition, toPosition);
        return this;
    }

    /**
     * RecyclerView notifyDataSetChanged
     * @param view {@link View}
     * @return Helper
     */
    @Override
    public ViewHelper notifyDataSetChanged(View view) {
        RecyclerViewUtils.notifyDataSetChanged(view);
        return this;
    }

    /**
     * 设置 RecyclerView LinearSnapHelper
     * @param view {@link View}
     * @return Helper
     */
    @Override
    public ViewHelper attachLinearSnapHelper(View view) {
        RecyclerViewUtils.attachLinearSnapHelper(view);
        return this;
    }

    /**
     * 设置 RecyclerView PagerSnapHelper
     * @param view {@link View}
     * @return Helper
     */
    @Override
    public ViewHelper attachPagerSnapHelper(View view) {
        RecyclerViewUtils.attachPagerSnapHelper(view);
        return this;
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    @Override
    public ViewHelper addItemDecoration(
            View view,
            RecyclerView.ItemDecoration decor
    ) {
        RecyclerViewUtils.addItemDecoration(view, decor);
        return this;
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @param index 添加索引
     * @return Helper
     */
    @Override
    public ViewHelper addItemDecoration(
            View view,
            RecyclerView.ItemDecoration decor,
            int index
    ) {
        RecyclerViewUtils.addItemDecoration(view, decor, index);
        return this;
    }

    /**
     * 移除 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    @Override
    public ViewHelper removeItemDecoration(
            View view,
            RecyclerView.ItemDecoration decor
    ) {
        RecyclerViewUtils.removeItemDecoration(view, decor);
        return this;
    }

    /**
     * 移除 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param index RecyclerView ItemDecoration 索引
     * @return Helper
     */
    @Override
    public ViewHelper removeItemDecorationAt(
            View view,
            int index
    ) {
        RecyclerViewUtils.removeItemDecorationAt(view, index);
        return this;
    }

    /**
     * 移除 RecyclerView 全部 ItemDecoration
     * @param view {@link View}
     * @return Helper
     */
    @Override
    public ViewHelper removeAllItemDecoration(View view) {
        RecyclerViewUtils.removeAllItemDecoration(view);
        return this;
    }

    /**
     * 设置 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return Helper
     */
    @Override
    public ViewHelper setOnScrollListener(
            View view,
            RecyclerView.OnScrollListener listener
    ) {
        RecyclerViewUtils.setOnScrollListener(view, listener);
        return this;
    }

    /**
     * 添加 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return Helper
     */
    @Override
    public ViewHelper addOnScrollListener(
            View view,
            RecyclerView.OnScrollListener listener
    ) {
        RecyclerViewUtils.addOnScrollListener(view, listener);
        return this;
    }

    /**
     * 移除 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return Helper
     */
    @Override
    public ViewHelper removeOnScrollListener(
            View view,
            RecyclerView.OnScrollListener listener
    ) {
        RecyclerViewUtils.removeOnScrollListener(view, listener);
        return this;
    }

    /**
     * 清空 RecyclerView ScrollListener
     * @param view {@link View}
     * @return Helper
     */
    @Override
    public ViewHelper clearOnScrollListeners(View view) {
        RecyclerViewUtils.clearOnScrollListeners(view);
        return this;
    }

    /**
     * 设置 RecyclerView 嵌套滚动开关
     * @param enabled 嵌套滚动开关
     * @param views   View[]
     * @return Helper
     */
    @Override
    public ViewHelper setNestedScrollingEnabled(
            boolean enabled,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> RecyclerViewUtils.setNestedScrollingEnabled(value, enabled), views
        );
        return this;
    }

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * @param view     {@link View}
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return Helper
     */
    @Override
    public ViewHelper forceGetViewSize(
            View view,
            SizeUtils.OnGetSizeListener listener
    ) {
        SizeUtils.forceGetViewSize(view, listener);
        return this;
    }
}