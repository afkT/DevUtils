package dev.utils.app.helper.view;

import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;

import dev.utils.app.helper.IHelper;

/**
 * detail: ViewHelper 接口
 * @author Ttt
 */
public interface IHelperByView<T>
        extends IHelper<T> {

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @param views View[]
     * @return Helper
     */
    T addTouchArea(
            int range,
            View... views
    );

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @param views  View[]
     * @return Helper
     */
    T addTouchArea(
            int top,
            int bottom,
            int left,
            int right,
            View... views
    );

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnClick(
            View.OnClickListener listener,
            View... views
    );

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnLongClick(
            View.OnLongClickListener listener,
            View... views
    );

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnTouch(
            View.OnTouchListener listener,
            View... views
    );

    // =============
    // = ViewUtils =
    // =============

    /**
     * 设置 View Id
     * @param view {@link View}
     * @param id   View Id
     * @return Helper
     */
    T setId(
            View view,
            int id
    );

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @param viewGroups   ViewGroup[]
     * @return Helper
     */
    T setClipChildren(
            boolean clipChildren,
            ViewGroup... viewGroups
    );

    /**
     * 移除全部子 View
     * @param viewGroups ViewGroup[]
     * @return Helper
     */
    T removeAllViews(ViewGroup... viewGroups);

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @return Helper
     */
    T addView(
            ViewGroup viewGroup,
            View child
    );

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param index     添加位置索引
     * @return Helper
     */
    T addView(
            ViewGroup viewGroup,
            View child,
            int index
    );

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param index     添加位置索引
     * @param params    LayoutParams
     * @return Helper
     */
    T addView(
            ViewGroup viewGroup,
            View child,
            int index,
            ViewGroup.LayoutParams params
    );

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param params    LayoutParams
     * @return Helper
     */
    T addView(
            ViewGroup viewGroup,
            View child,
            ViewGroup.LayoutParams params
    );

    /**
     * 添加 View
     * @param viewGroup {@link ViewGroup}
     * @param child     待添加 View
     * @param width     View 宽度
     * @param height    View 高度
     * @return Helper
     */
    T addView(
            ViewGroup viewGroup,
            View child,
            int width,
            int height
    );

    /**
     * 设置 View LayoutParams
     * @param view   {@link View}
     * @param params LayoutParams
     * @return Helper
     */
    T setLayoutParams(
            View view,
            ViewGroup.LayoutParams params
    );

    /**
     * 设置 View[] 宽度、高度
     * @param width  View 宽度
     * @param height View 高度
     * @param views  View[]
     * @return Helper
     */
    T setWidthHeight(
            int width,
            int height,
            View... views
    );

    /**
     * 设置 View[] 宽度、高度
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @param views     View[]
     * @return Helper
     */
    T setWidthHeight(
            int width,
            int height,
            boolean nullNewLP,
            View... views
    );

    /**
     * 设置 View weight 权重
     * @param weight 权重比例
     * @param views  View[]
     * @return Helper
     */
    T setWeight(
            float weight,
            View... views
    );

    /**
     * 设置 View 宽度
     * @param width View 宽度
     * @param views View[]
     * @return Helper
     */
    T setWidth(
            int width,
            View... views
    );

    /**
     * 设置 View 宽度
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @param views     View[]
     * @return Helper
     */
    T setWidth(
            int width,
            boolean nullNewLP,
            View... views
    );

    /**
     * 设置 View 高度
     * @param height View 高度
     * @param views  View[]
     * @return Helper
     */
    T setHeight(
            int height,
            View... views
    );

    /**
     * 设置 View 高度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @param views     View[]
     * @return Helper
     */
    T setHeight(
            int height,
            boolean nullNewLP,
            View... views
    );

    /**
     * 设置 View 最小宽度
     * @param minWidth 最小宽度
     * @param views    View[]
     * @return Helper
     */
    T setMinimumWidth(
            int minWidth,
            View... views
    );

    /**
     * 设置 View 最小高度
     * @param minHeight 最小高度
     * @param views     View[]
     * @return Helper
     */
    T setMinimumHeight(
            int minHeight,
            View... views
    );

    /**
     * 设置 View 透明度
     * @param alpha 透明度
     * @param views View[]
     * @return Helper
     */
    T setAlpha(
            @FloatRange(from = 0.0, to = 1.0) float alpha,
            View... views
    );

    /**
     * 设置 View Tag
     * @param view   View
     * @param object Tag
     * @return Helper
     */
    T setTag(
            View view,
            Object object
    );

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param value X 轴坐标
     * @param views View[]
     * @return Helper
     */
    T setScrollX(
            int value,
            View... views
    );

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param value Y 轴坐标
     * @param views View[]
     * @return Helper
     */
    T setScrollY(
            int value,
            View... views
    );

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
    T setDescendantFocusability(
            int focusability,
            ViewGroup... viewGroups
    );

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
    T setOverScrollMode(
            int overScrollMode,
            View... views
    );

    /**
     * 设置是否绘制横向滚动条
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @param views                      View[]
     * @return Helper
     */
    T setHorizontalScrollBarEnabled(
            boolean horizontalScrollBarEnabled,
            View... views
    );

    /**
     * 设置是否绘制垂直滚动条
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @param views                    View[]
     * @return Helper
     */
    T setVerticalScrollBarEnabled(
            boolean verticalScrollBarEnabled,
            View... views
    );

    /**
     * 设置 View 滚动效应
     * @param isScrollContainer 是否需要滚动效应
     * @param views             View[]
     * @return Helper
     */
    T setScrollContainer(
            boolean isScrollContainer,
            View... views
    );

    /**
     * 设置下一个获取焦点的 View id
     * @param view               {@link View}
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusForwardId(
            View view,
            @IdRes int nextFocusForwardId
    );

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusDownId(
            View view,
            @IdRes int nextFocusDownId
    );

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusLeftId(
            View view,
            @IdRes int nextFocusLeftId
    );

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             {@link View}
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusRightId(
            View view,
            @IdRes int nextFocusRightId
    );

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          {@link View}
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusUpId(
            View view,
            @IdRes int nextFocusUpId
    );

    /**
     * 设置 View 旋转度数
     * @param rotation 旋转度数
     * @param views    View[]
     * @return Helper
     */
    T setRotation(
            float rotation,
            View... views
    );

    /**
     * 设置 View 水平旋转度数
     * @param rotationX 水平旋转度数
     * @param views     View[]
     * @return Helper
     */
    T setRotationX(
            float rotationX,
            View... views
    );

    /**
     * 设置 View 竖直旋转度数
     * @param rotationY 竖直旋转度数
     * @param views     View[]
     * @return Helper
     */
    T setRotationY(
            float rotationY,
            View... views
    );

    /**
     * 设置 View 水平方向缩放比例
     * @param scaleX 水平方向缩放比例
     * @param views  View[]
     * @return Helper
     */
    T setScaleX(
            float scaleX,
            View... views
    );

    /**
     * 设置 View 竖直方向缩放比例
     * @param scaleY 竖直方向缩放比例
     * @param views  View[]
     * @return Helper
     */
    T setScaleY(
            float scaleY,
            View... views
    );

    /**
     * 设置文本的显示方式
     * @param textAlignment 文本的显示方式
     * @param views         View[]
     * @return Helper
     */
    T setTextAlignment(
            int textAlignment,
            View... views
    );

    /**
     * 设置文本的显示方向
     * @param textDirection 文本的显示方向
     * @param views         View[]
     * @return Helper
     */
    T setTextDirection(
            int textDirection,
            View... views
    );

    /**
     * 设置水平方向偏转量
     * @param pivotX 水平方向偏转量
     * @param views  View[]
     * @return Helper
     */
    T setPivotX(
            float pivotX,
            View... views
    );

    /**
     * 设置竖直方向偏转量
     * @param pivotY 竖直方向偏转量
     * @param views  View[]
     * @return Helper
     */
    T setPivotY(
            float pivotY,
            View... views
    );

    /**
     * 设置水平方向的移动距离
     * @param translationX 水平方向的移动距离
     * @param views        View[]
     * @return Helper
     */
    T setTranslationX(
            float translationX,
            View... views
    );

    /**
     * 设置竖直方向的移动距离
     * @param translationY 竖直方向的移动距离
     * @param views        View[]
     * @return Helper
     */
    T setTranslationY(
            float translationY,
            View... views
    );

    /**
     * 设置 View 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @param views     View[]
     * @return Helper
     */
    T setLayerType(
            int layerType,
            Paint paint,
            View... views
    );

    /**
     * 请求重新对 View 布局
     * @param views View[]
     * @return Helper
     */
    T requestLayout(View... views);

    /**
     * View 请求获取焦点
     * @param views View[]
     * @return Helper
     */
    T requestFocus(View... views);

    /**
     * View 清除焦点
     * @param views View[]
     * @return Helper
     */
    T clearFocus(View... views);

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @param views                View[]
     * @return Helper
     */
    T setFocusableInTouchMode(
            boolean focusableInTouchMode,
            View... views
    );

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
     * @return Helper
     */
    T setFocusable(
            boolean focusable,
            View... views
    );

    /**
     * 切换获取焦点状态
     * @param views View[]
     * @return Helper
     */
    T toggleFocusable(View... views);

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @param views    View[]
     * @return Helper
     */
    T setSelected(
            boolean selected,
            View... views
    );

    /**
     * 切换选中状态
     * @param views View[]
     * @return Helper
     */
    T toggleSelected(View... views);

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @param views   View[]
     * @return Helper
     */
    T setEnabled(
            boolean enabled,
            View... views
    );

    /**
     * 切换 View 是否启用状态
     * @param views View[]
     * @return Helper
     */
    T toggleEnabled(View... views);

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @param views     View[]
     * @return Helper
     */
    T setClickable(
            boolean clickable,
            View... views
    );

    /**
     * 切换 View 是否可以点击状态
     * @param views View[]
     * @return Helper
     */
    T toggleClickable(View... views);

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @param views         View[]
     * @return Helper
     */
    T setLongClickable(
            boolean longClickable,
            View... views
    );

    /**
     * 切换 View 是否可以长按状态
     * @param views View[]
     * @return Helper
     */
    T toggleLongClickable(View... views);

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return Helper
     */
    T setVisibilitys(
            boolean isVisibility,
            View... views
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setVisibilitys(
            int isVisibility,
            View... views
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param views        View[]
     * @return Helper
     */
    T setVisibilityINs(
            boolean isVisibility,
            View... views
    );

    /**
     * 切换 View 显示的状态
     * @param view  {@link View}
     * @param views View[]
     * @return Helper
     */
    T toggleVisibilitys(
            View view,
            View... views
    );

    /**
     * 切换 View 显示的状态
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    T toggleVisibilitys(
            View[] viewArrays,
            View... views
    );

    /**
     * 切换 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    T toggleVisibilitys(
            int state,
            View[] viewArrays,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return Helper
     */
    T reverseVisibilitys(
            int state,
            View[] viewArrays,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArrays   View[]
     * @param views        View[]
     * @return Helper
     */
    T reverseVisibilitys(
            boolean isVisibility,
            View[] viewArrays,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view  {@link View}
     * @param views View[]
     * @return Helper
     */
    T reverseVisibilitys(
            int state,
            View view,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     * @return Helper
     */
    T reverseVisibilitys(
            boolean isVisibility,
            View view,
            View... views
    );

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T toggleViews(
            boolean isChange,
            int isVisibility,
            View... views
    );

    /**
     * 把自身从父 View 中移除
     * @param views View[]
     * @return Helper
     */
    T removeSelfFromParent(View... views);

    /**
     * View 请求更新
     * @param allParent 是否全部父布局 View 都请求
     * @param views     View[]
     * @return Helper
     */
    T requestLayoutParent(
            boolean allParent,
            View... views
    );

    /**
     * 测量 View
     * @param specifiedWidth 指定宽度
     * @param views          View[]
     * @return Helper
     */
    T measureView(
            int specifiedWidth,
            View... views
    );

    /**
     * 测量 View
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @param views           View[]
     * @return Helper
     */
    T measureView(
            int specifiedWidth,
            int specifiedHeight,
            View... views
    );

    /**
     * 设置 View Layout Gravity
     * @param gravity Gravity
     * @param views   View[]
     * @return Helper
     */
    T setLayoutGravity(
            int gravity,
            View... views
    );

    /**
     * 设置 View Layout Gravity
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @param views        View[]
     * @return Helper
     */
    T setLayoutGravity(
            int gravity,
            boolean isReflection,
            View... views
    );

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param views      View[]
     * @return Helper
     */
    T setMarginLeft(
            int leftMargin,
            View... views
    );

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @param views      View[]
     * @return Helper
     */
    T setMarginLeft(
            int leftMargin,
            boolean reset,
            View... views
    );

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param views     View[]
     * @return Helper
     */
    T setMarginTop(
            int topMargin,
            View... views
    );

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @param views     View[]
     * @return Helper
     */
    T setMarginTop(
            int topMargin,
            boolean reset,
            View... views
    );

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param views       View[]
     * @return Helper
     */
    T setMarginRight(
            int rightMargin,
            View... views
    );

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @param views       View[]
     * @return Helper
     */
    T setMarginRight(
            int rightMargin,
            boolean reset,
            View... views
    );

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param views        View[]
     * @return Helper
     */
    T setMarginBottom(
            int bottomMargin,
            View... views
    );

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @param views        View[]
     * @return Helper
     */
    T setMarginBottom(
            int bottomMargin,
            boolean reset,
            View... views
    );

    /**
     * 设置 Margin 边距
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @param views     View[]
     * @return Helper
     */
    T setMargin(
            int leftRight,
            int topBottom,
            View... views
    );

    /**
     * 设置 Margin 边距
     * @param margin Margin
     * @param views  View[]
     * @return Helper
     */
    T setMargin(
            int margin,
            View... views
    );

    /**
     * 设置 Margin 边距
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @param views  View[]
     * @return Helper
     */
    T setMargin(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    );

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param views       View[]
     * @return Helper
     */
    T setPaddingLeft(
            int leftPadding,
            View... views
    );

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @param views       View[]
     * @return Helper
     */
    T setPaddingLeft(
            int leftPadding,
            boolean reset,
            View... views
    );

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param views      View[]
     * @return Helper
     */
    T setPaddingTop(
            int topPadding,
            View... views
    );

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @param views      View[]
     * @return Helper
     */
    T setPaddingTop(
            int topPadding,
            boolean reset,
            View... views
    );

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param views        View[]
     * @return Helper
     */
    T setPaddingRight(
            int rightPadding,
            View... views
    );

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @param views        View[]
     * @return Helper
     */
    T setPaddingRight(
            int rightPadding,
            boolean reset,
            View... views
    );

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param views         View[]
     * @return Helper
     */
    T setPaddingBottom(
            int bottomPadding,
            View... views
    );

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @param views         View[]
     * @return Helper
     */
    T setPaddingBottom(
            int bottomPadding,
            boolean reset,
            View... views
    );

    /**
     * 设置 Padding 边距
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @param views     View[]
     * @return Helper
     */
    T setPadding(
            int leftRight,
            int topBottom,
            View... views
    );

    /**
     * 设置 Padding 边距
     * @param padding Padding
     * @param views   View[]
     * @return Helper
     */
    T setPadding(
            int padding,
            View... views
    );

    /**
     * 设置 Padding 边距
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @param views  View[]
     * @return Helper
     */
    T setPadding(
            int left,
            int top,
            int right,
            int bottom,
            View... views
    );

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return Helper
     */
    T addRules(
            int verb,
            View... views
    );

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @param views   View[]
     * @return Helper
     */
    T addRules(
            int verb,
            int subject,
            View... views
    );

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return Helper
     */
    T removeRules(
            int verb,
            View... views
    );

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return Helper
     */
    T setAnimation(
            View view,
            Animation animation
    );

    /**
     * 清空动画
     * @param views View[]
     * @return Helper
     */
    T clearAnimation(View... views);

    /**
     * 启动动画
     * @param views View[]
     * @return Helper
     */
    T startAnimation(View... views);

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return Helper
     */
    T startAnimation(
            View view,
            Animation animation
    );

    /**
     * 取消动画
     * @param views View[]
     * @return Helper
     */
    T cancelAnimation(View... views);

    /**
     * 设置背景图片
     * @param background 背景图片
     * @param views      View[]
     * @return Helper
     */
    T setBackground(
            Drawable background,
            View... views
    );

    /**
     * 设置背景颜色
     * @param color 背景颜色
     * @param views View[]
     * @return Helper
     */
    T setBackgroundColor(
            @ColorInt int color,
            View... views
    );

    /**
     * 设置背景资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    T setBackgroundResource(
            @DrawableRes int resId,
            View... views
    );

    /**
     * 设置背景着色颜色
     * @param tint  着色颜色
     * @param views View[]
     * @return Helper
     */
    T setBackgroundTintList(
            ColorStateList tint,
            View... views
    );

    /**
     * 设置背景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @param views    View[]
     * @return Helper
     */
    T setBackgroundTintMode(
            PorterDuff.Mode tintMode,
            View... views
    );

    /**
     * 设置前景图片
     * @param foreground 前景图片
     * @param views      View[]
     * @return Helper
     */
    T setForeground(
            Drawable foreground,
            View... views
    );

    /**
     * 设置前景重心
     * @param gravity 重心
     * @param views   View[]
     * @return Helper
     */
    T setForegroundGravity(
            int gravity,
            View... views
    );

    /**
     * 设置前景着色颜色
     * @param tint  着色颜色
     * @param views View[]
     * @return Helper
     */
    T setForegroundTintList(
            ColorStateList tint,
            View... views
    );

    /**
     * 设置前景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @param views    View[]
     * @return Helper
     */
    T setForegroundTintMode(
            PorterDuff.Mode tintMode,
            View... views
    );

    /**
     * View 着色处理
     * @param color 颜色值
     * @param views View[]
     * @return Helper
     */
    T setColorFilter(
            @ColorInt int color,
            View... views
    );

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @param views    View[]
     * @return Helper
     */
    T setColorFilter(
            Drawable drawable,
            @ColorInt int color,
            View... views
    );

    /**
     * View 着色处理
     * @param colorFilter 颜色过滤 ( 效果 )
     * @param views       View[]
     * @return Helper
     */
    T setColorFilter(
            ColorFilter colorFilter,
            View... views
    );

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @param views       View[]
     * @return Helper
     */
    T setColorFilter(
            Drawable drawable,
            ColorFilter colorFilter,
            View... views
    );

    /**
     * 设置 ProgressBar 进度条样式
     * @param drawable {@link Drawable}
     * @param views    View[]
     * @return Helper
     */
    T setProgressDrawable(
            Drawable drawable,
            View... views
    );

    /**
     * 设置 ProgressBar 进度值
     * @param progress 当前进度
     * @param views    View[]
     * @return Helper
     */
    T setBarProgress(
            int progress,
            View... views
    );

    /**
     * 设置 ProgressBar 最大值
     * @param max   最大值
     * @param views View[]
     * @return Helper
     */
    T setBarMax(
            int max,
            View... views
    );

    /**
     * 设置 ProgressBar 最大值
     * @param progress 当前进度
     * @param max      最大值
     * @param views    View[]
     * @return Helper
     */
    T setBarValue(
            int progress,
            int max,
            View... views
    );
}