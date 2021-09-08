package dev.utils.app.helper.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;

import dev.utils.app.ClickUtils;
import dev.utils.app.ImageViewUtils;
import dev.utils.app.ListViewUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.dev.DevHelper;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.common.ForUtils;

/**
 * detail: View 链式调用快捷设置 Helper 类
 * @author Ttt
 */
public final class QQQQQQQQQQQ
        implements IHelperByView<QQQQQQQQQQQ> {

    private QQQQQQQQQQQ() {
    }

    // ViewHelper
    private static final QQQQQQQQQQQ HELPER = new QQQQQQQQQQQ();

    /**
     * 获取单例 QQQQQQQQQQQ
     * @return {@link QQQQQQQQQQQ}
     */
    public static QQQQQQQQQQQ get() {
        return HELPER;
    }

    // ===========
    // = IHelper =
    // ===========

    /**
     * 获取 DevHelper
     * @return {@link DevHelper}
     */
    @Override
    public DevHelper devHelper() {
        return DevHelper.get();
    }

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    @Override
    public QuickHelper quickHelper(View target) {
        return QuickHelper.get(target);
    }

    /**
     * 获取 ViewHelper
     * @return {@link ViewHelper}
     */
    @Override
    public ViewHelper viewHelper() {
        return ViewHelper.get();
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
    public QQQQQQQQQQQ addTouchArea(
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
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @param views  View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ addTouchArea(
            int top,
            int bottom,
            int left,
            int right,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ClickUtils.addTouchArea(value, top, bottom, left, right), views
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
    public QQQQQQQQQQQ setOnClick(
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
    public QQQQQQQQQQQ setOnLongClick(
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
    public QQQQQQQQQQQ setOnTouch(
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
    public QQQQQQQQQQQ setId(
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
    public QQQQQQQQQQQ setClipChildren(
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
    public QQQQQQQQQQQ removeAllViews(ViewGroup... viewGroups) {
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
    public QQQQQQQQQQQ addView(
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
    public QQQQQQQQQQQ addView(
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
    public QQQQQQQQQQQ addView(
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
    public QQQQQQQQQQQ addView(
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
    public QQQQQQQQQQQ addView(
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
    public QQQQQQQQQQQ setLayoutParams(
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
    public QQQQQQQQQQQ setWidthHeight(
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
    public QQQQQQQQQQQ setWidthHeight(
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
    public QQQQQQQQQQQ setWeight(
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
    public QQQQQQQQQQQ setWidth(
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
    public QQQQQQQQQQQ setWidth(
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
    public QQQQQQQQQQQ setHeight(
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
    public QQQQQQQQQQQ setHeight(
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
    public QQQQQQQQQQQ setMinimumWidth(
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
    public QQQQQQQQQQQ setMinimumHeight(
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
    public QQQQQQQQQQQ setAlpha(
            @FloatRange(from = 0.0, to = 1.0) float alpha,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setAlpha(value, alpha), views
        );
        return this;
    }

    /**
     * 设置 View Tag
     * @param view   View
     * @param object Tag
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setTag(
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
    public QQQQQQQQQQQ setScrollX(
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
    public QQQQQQQQQQQ setScrollY(
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
    public QQQQQQQQQQQ setDescendantFocusability(
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
    public QQQQQQQQQQQ setOverScrollMode(
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
    public QQQQQQQQQQQ setHorizontalScrollBarEnabled(
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
    public QQQQQQQQQQQ setVerticalScrollBarEnabled(
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
    public QQQQQQQQQQQ setScrollContainer(
            boolean isScrollContainer,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setScrollContainer(value, isScrollContainer), views
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
    public QQQQQQQQQQQ setNextFocusForwardId(
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
    public QQQQQQQQQQQ setNextFocusDownId(
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
    public QQQQQQQQQQQ setNextFocusLeftId(
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
    public QQQQQQQQQQQ setNextFocusRightId(
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
    public QQQQQQQQQQQ setNextFocusUpId(
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
    public QQQQQQQQQQQ setRotation(
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
    public QQQQQQQQQQQ setRotationX(
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
    public QQQQQQQQQQQ setRotationY(
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
    public QQQQQQQQQQQ setScaleX(
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
    public QQQQQQQQQQQ setScaleY(
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
    public QQQQQQQQQQQ setTextAlignment(
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
    public QQQQQQQQQQQ setTextDirection(
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
    public QQQQQQQQQQQ setPivotX(
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
    public QQQQQQQQQQQ setPivotY(
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
    public QQQQQQQQQQQ setTranslationX(
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
    public QQQQQQQQQQQ setTranslationY(
            float translationY,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setTranslationY(value, translationY), views
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
    public QQQQQQQQQQQ setLayerType(
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
    public QQQQQQQQQQQ requestLayout(View... views) {
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
    public QQQQQQQQQQQ requestFocus(View... views) {
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
    public QQQQQQQQQQQ clearFocus(View... views) {
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
    public QQQQQQQQQQQ setFocusableInTouchMode(
            boolean focusableInTouchMode,
            View... views
    ) {
        ForUtils.forSimpleArgs(
                value -> ViewUtils.setFocusableInTouchMode(focusableInTouchMode), views
        );
        return this;
    }

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setFocusable(
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
    public QQQQQQQQQQQ toggleFocusable(View... views) {
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
    public QQQQQQQQQQQ setSelected(
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
    public QQQQQQQQQQQ toggleSelected(View... views) {
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
    public QQQQQQQQQQQ setEnabled(
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
    public QQQQQQQQQQQ toggleEnabled(View... views) {
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
    public QQQQQQQQQQQ setClickable(
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
    public QQQQQQQQQQQ toggleClickable(View... views) {
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
    public QQQQQQQQQQQ setLongClickable(
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
    public QQQQQQQQQQQ toggleLongClickable(View... views) {
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
    public QQQQQQQQQQQ setVisibilitys(
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
    public QQQQQQQQQQQ setVisibilitys(
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
    public QQQQQQQQQQQ setVisibilityINs(
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
    public QQQQQQQQQQQ toggleVisibilitys(
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
    public QQQQQQQQQQQ toggleVisibilitys(
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
    public QQQQQQQQQQQ toggleVisibilitys(
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
    public QQQQQQQQQQQ reverseVisibilitys(
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
    public QQQQQQQQQQQ reverseVisibilitys(
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
    public QQQQQQQQQQQ reverseVisibilitys(
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
    public QQQQQQQQQQQ reverseVisibilitys(
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
    public QQQQQQQQQQQ toggleViews(
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
    public QQQQQQQQQQQ removeSelfFromParent(View... views) {
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
    public QQQQQQQQQQQ requestLayoutParent(
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
    public QQQQQQQQQQQ measureView(
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
    public QQQQQQQQQQQ measureView(
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
    public QQQQQQQQQQQ setLayoutGravity(
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
    public QQQQQQQQQQQ setLayoutGravity(
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
    public QQQQQQQQQQQ setMarginLeft(
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
    public QQQQQQQQQQQ setMarginLeft(
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
    public QQQQQQQQQQQ setMarginTop(
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
    public QQQQQQQQQQQ setMarginTop(
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
    public QQQQQQQQQQQ setMarginRight(
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
    public QQQQQQQQQQQ setMarginRight(
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
    public QQQQQQQQQQQ setMarginBottom(
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
    public QQQQQQQQQQQ setMarginBottom(
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
    public QQQQQQQQQQQ setMargin(
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
    public QQQQQQQQQQQ setMargin(
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
    public QQQQQQQQQQQ setMargin(
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
    public QQQQQQQQQQQ setPaddingLeft(
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
    public QQQQQQQQQQQ setPaddingLeft(
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
    public QQQQQQQQQQQ setPaddingTop(
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
    public QQQQQQQQQQQ setPaddingTop(
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
    public QQQQQQQQQQQ setPaddingRight(
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
    public QQQQQQQQQQQ setPaddingRight(
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
    public QQQQQQQQQQQ setPaddingBottom(
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
    public QQQQQQQQQQQ setPaddingBottom(
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
    public QQQQQQQQQQQ setPadding(
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
    public QQQQQQQQQQQ setPadding(
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
    public QQQQQQQQQQQ setPadding(
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
    public QQQQQQQQQQQ addRules(
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
    public QQQQQQQQQQQ addRules(
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
    public QQQQQQQQQQQ removeRules(
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
    public QQQQQQQQQQQ setAnimation(
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
    public QQQQQQQQQQQ clearAnimation(View... views) {
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
    public QQQQQQQQQQQ startAnimation(View... views) {
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
    public QQQQQQQQQQQ startAnimation(
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
    public QQQQQQQQQQQ cancelAnimation(View... views) {
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
    public QQQQQQQQQQQ setBackground(
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
    public QQQQQQQQQQQ setBackgroundColor(
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
    public QQQQQQQQQQQ setBackgroundResource(
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
    public QQQQQQQQQQQ setBackgroundTintList(
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
    public QQQQQQQQQQQ setBackgroundTintMode(
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
    public QQQQQQQQQQQ setForeground(
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
    public QQQQQQQQQQQ setForegroundGravity(
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
    public QQQQQQQQQQQ setForegroundTintList(
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
    public QQQQQQQQQQQ setForegroundTintMode(
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
     * View 着色处理
     * @param color 颜色值
     * @param views View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setColorFilter(
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
    public QQQQQQQQQQQ setColorFilter(
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
    public QQQQQQQQQQQ setColorFilter(
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
    public QQQQQQQQQQQ setColorFilter(
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
    public QQQQQQQQQQQ setProgressDrawable(
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
    public QQQQQQQQQQQ setBarProgress(
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
    public QQQQQQQQQQQ setBarMax(
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
    public QQQQQQQQQQQ setBarValue(
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
    public QQQQQQQQQQQ smoothScrollToPosition(
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
    public QQQQQQQQQQQ scrollToPosition(
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
    public QQQQQQQQQQQ smoothScrollToTop(View... views) {
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
    public QQQQQQQQQQQ scrollToTop(View... views) {
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
    public QQQQQQQQQQQ smoothScrollToBottom(View... views) {
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
    public QQQQQQQQQQQ scrollToBottom(View... views) {
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
    public QQQQQQQQQQQ smoothScrollTo(
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
    public QQQQQQQQQQQ smoothScrollBy(
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
    public QQQQQQQQQQQ fullScroll(
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
    public QQQQQQQQQQQ scrollTo(
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
    public QQQQQQQQQQQ scrollBy(
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
    public QQQQQQQQQQQ setAdjustViewBounds(
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
    public QQQQQQQQQQQ setMaxHeight(
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
    public QQQQQQQQQQQ setMaxWidth(
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
    public QQQQQQQQQQQ setImageLevel(
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
    public QQQQQQQQQQQ setImageBitmap(
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
    public QQQQQQQQQQQ setImageDrawable(
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
    public QQQQQQQQQQQ setImageResource(
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
    public QQQQQQQQQQQ setImageMatrix(
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
    public QQQQQQQQQQQ setImageTintList(
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
    public QQQQQQQQQQQ setImageTintMode(
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
     * 设置 ImageView 缩放类型
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setScaleType(
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
    public QQQQQQQQQQQ setBackgroundResources(
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
    public QQQQQQQQQQQ setBackgroundResources(
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
    public QQQQQQQQQQQ setImageResources(
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
    public QQQQQQQQQQQ setImageResources(
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
    public QQQQQQQQQQQ setImageBitmaps(
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
    public QQQQQQQQQQQ setImageBitmaps(
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
    public QQQQQQQQQQQ setImageDrawables(
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
    public QQQQQQQQQQQ setImageDrawables(
            Drawable drawable,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setImageDrawables(drawable, isVisibility, views);
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    @Override
    public QQQQQQQQQQQ setScaleTypes(
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
    public QQQQQQQQQQQ setScaleTypes(
            ImageView.ScaleType scaleType,
            int isVisibility,
            View... views
    ) {
        ImageViewUtils.setScaleTypes(scaleType, isVisibility, views);
        return this;
    }
}