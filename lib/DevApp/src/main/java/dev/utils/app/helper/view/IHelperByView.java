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
    T addTouchAreas(
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
    T addTouchAreas(
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
    T setOnClicks(
            View.OnClickListener listener,
            View... views
    );

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnLongClicks(
            View.OnLongClickListener listener,
            View... views
    );

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return Helper
     */
    T setOnTouchs(
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
    T setClipChildrens(
            boolean clipChildren,
            ViewGroup... viewGroups
    );

    /**
     * 移除全部子 View
     * @param viewGroups {@link ViewGroup}
     * @return Helper
     */
    T removeAllViews(final ViewGroup... viewGroups);

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
    T setWidthHeights(
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
    T setWidthHeights(
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
    T setWidths(
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
    T setWidths(
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
    T setHeights(
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
    T setHeights(
            int height,
            boolean nullNewLP,
            View... views
    );

    /**
     * 设置 View 最小高度
     * @param view      {@link View}
     * @param minHeight 最小高度
     * @return Helper
     */
    T setMinimumHeight(
            View view,
            int minHeight
    );

    /**
     * 设置 View 最小宽度
     * @param view     {@link View}
     * @param minWidth 最小宽度
     * @return Helper
     */
    T setMinimumWidth(
            View view,
            int minWidth
    );

    /**
     * 设置 View 透明度
     * @param view  View
     * @param alpha 透明度
     * @return Helper
     */
    T setAlpha(
            View view,
            @FloatRange(from = 0.0, to = 1.0) float alpha
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
     * @param view  {@link View}
     * @param value X 轴坐标
     * @return Helper
     */
    T setScrollX(
            View view,
            int value
    );

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param view  {@link View}
     * @param value Y 轴坐标
     * @return Helper
     */
    T setScrollY(
            View view,
            int value
    );

    /**
     * 设置 ViewGroup 和其子控件两者之间的关系
     * <pre>
     *     beforeDescendants : ViewGroup 会优先其子类控件而获取到焦点
     *     afterDescendants : ViewGroup 只有当其子类控件不需要获取焦点时才获取焦点
     *     blocksDescendants : ViewGroup 会覆盖子类控件而直接获得焦点
     *     android:descendantFocusability="blocksDescendants"
     * </pre>
     * @param viewGroup    {@link ViewGroup}
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @return Helper
     */
    T setDescendantFocusability(
            ViewGroup viewGroup,
            int focusability
    );

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param view           {@link View}
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @return Helper
     */
    T setOverScrollMode(
            View view,
            int overScrollMode
    );

    /**
     * 设置是否绘制横向滚动条
     * @param view                       {@link View}
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    T setHorizontalScrollBarEnabled(
            View view,
            boolean horizontalScrollBarEnabled
    );

    /**
     * 设置是否绘制垂直滚动条
     * @param view                     {@link View}
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    T setVerticalScrollBarEnabled(
            View view,
            boolean verticalScrollBarEnabled
    );

    /**
     * 设置 View 滚动效应
     * @param view              {@link View}
     * @param isScrollContainer 是否需要滚动效应
     * @return Helper
     */
    T setScrollContainer(
            View view,
            boolean isScrollContainer
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
     * @param view     {@link View}
     * @param rotation 旋转度数
     * @return Helper
     */
    T setRotation(
            View view,
            float rotation
    );

    /**
     * 设置 View 水平旋转度数
     * @param view      {@link View}
     * @param rotationX 水平旋转度数
     * @return Helper
     */
    T setRotationX(
            View view,
            float rotationX
    );

    /**
     * 设置 View 竖直旋转度数
     * @param view      {@link View}
     * @param rotationY 竖直旋转度数
     * @return Helper
     */
    T setRotationY(
            View view,
            float rotationY
    );

    /**
     * 设置 View 水平方向缩放比例
     * @param view   View
     * @param scaleX 水平方向缩放比例
     * @return Helper
     */
    T setScaleX(
            View view,
            float scaleX
    );

    /**
     * 设置 View 竖直方向缩放比例
     * @param view   View
     * @param scaleY 竖直方向缩放比例
     * @return Helper
     */
    T setScaleY(
            View view,
            float scaleY
    );

    /**
     * 设置文本的显示方式
     * @param view          {@link View}
     * @param textAlignment 文本的显示方式
     * @return Helper
     */
    T setTextAlignment(
            View view,
            int textAlignment
    );

    /**
     * 设置文本的显示方向
     * @param view          {@link View}
     * @param textDirection 文本的显示方向
     * @return Helper
     */
    T setTextDirection(
            View view,
            int textDirection
    );

    /**
     * 设置水平方向偏转量
     * @param view   View
     * @param pivotX 水平方向偏转量
     * @return Helper
     */
    T setPivotX(
            View view,
            float pivotX
    );

    /**
     * 设置竖直方向偏转量
     * @param view   View
     * @param pivotY 竖直方向偏转量
     * @return Helper
     */
    T setPivotY(
            View view,
            float pivotY
    );

    /**
     * 设置水平方向的移动距离
     * @param view         {@link View}
     * @param translationX 水平方向的移动距离
     * @return Helper
     */
    T setTranslationX(
            View view,
            float translationX
    );

    /**
     * 设置竖直方向的移动距离
     * @param view         {@link View}
     * @param translationY 竖直方向的移动距离
     * @return Helper
     */
    T setTranslationY(
            View view,
            float translationY
    );

    /**
     * 设置 View 硬件加速类型
     * @param view      {@link View}
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return Helper
     */
    T setLayerType(
            View view,
            int layerType,
            Paint paint
    );

    /**
     * 请求重新对 View 布局
     * @param view {@link View}
     * @return Helper
     */
    T requestLayout(final View view);

    /**
     * View 请求获取焦点
     * @param view {@link View}
     * @return Helper
     */
    T requestFocus(final View view);

    /**
     * View 清除焦点
     * @param view {@link View}
     * @return Helper
     */
    T clearFocus(final View view);

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
    T toggleFocusable(final View... views);

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
    T toggleSelected(final View... views);

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
    T toggleEnabled(final View... views);

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
    T toggleClickable(final View... views);

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
    T toggleLongClickable(final View... views);

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @return Helper
     */
    T setVisibility(
            boolean isVisibility,
            View view
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return Helper
     */
    T setVisibility(
            int isVisibility,
            View view
    );

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param view         {@link View}
     * @return Helper
     */
    T setVisibilityIN(
            boolean isVisibility,
            View view
    );

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
     * @param view         {@link View}
     * @return Helper
     */
    T toggleView(
            boolean isChange,
            int isVisibility,
            View view
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
     * @param view {@link View}
     * @return Helper
     */
    T removeSelfFromParent(final View view);

    /**
     * View 请求更新
     * @param view      {@link View}
     * @param allParent 是否全部父布局 View 都请求
     * @return Helper
     */
    T requestLayoutParent(
            View view,
            boolean allParent
    );

    /**
     * 测量 View
     * @param view           {@link View}
     * @param specifiedWidth 指定宽度
     * @return Helper
     */
    T measureView(
            View view,
            int specifiedWidth
    );

    /**
     * 测量 View
     * @param view            {@link View}
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return Helper
     */
    T measureView(
            View view,
            int specifiedWidth,
            int specifiedHeight
    );

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return Helper
     */
    T setLayoutGravity(
            View view,
            int gravity
    );

    /**
     * 设置 View Layout Gravity
     * @param view         {@link View}
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return Helper
     */
    T setLayoutGravity(
            View view,
            int gravity,
            boolean isReflection
    );

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @return Helper
     */
    T setMarginLeft(
            View view,
            int leftMargin
    );

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return Helper
     */
    T setMarginLeft(
            View view,
            int leftMargin,
            boolean reset
    );

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @return Helper
     */
    T setMarginTop(
            View view,
            int topMargin
    );

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return Helper
     */
    T setMarginTop(
            View view,
            int topMargin,
            boolean reset
    );

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @return Helper
     */
    T setMarginRight(
            View view,
            int rightMargin
    );

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return Helper
     */
    T setMarginRight(
            View view,
            int rightMargin,
            boolean reset
    );

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @return Helper
     */
    T setMarginBottom(
            View view,
            int bottomMargin
    );

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return Helper
     */
    T setMarginBottom(
            View view,
            int bottomMargin,
            boolean reset
    );

    /**
     * 设置 Margin 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return Helper
     */
    T setMargin(
            View view,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param margin Margin
     * @return Helper
     */
    T setMargin(
            View view,
            int margin
    );

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return Helper
     */
    T setMargin(
            View view,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 Margin 边距
     * @param views     View[]
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return Helper
     */
    T setMargin(
            View[] views,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param margin Margin
     * @return Helper
     */
    T setMargin(
            View[] views,
            int margin
    );

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return Helper
     */
    T setMargin(
            View[] views,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @return Helper
     */
    T setPaddingLeft(
            View view,
            int leftPadding
    );

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingLeft(
            View view,
            int leftPadding,
            boolean reset
    );

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @return Helper
     */
    T setPaddingTop(
            View view,
            int topPadding
    );

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingTop(
            View view,
            int topPadding,
            boolean reset
    );

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @return Helper
     */
    T setPaddingRight(
            View view,
            int rightPadding
    );

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingRight(
            View view,
            int rightPadding,
            boolean reset
    );

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @return Helper
     */
    T setPaddingBottom(
            View view,
            int bottomPadding
    );

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingBottom(
            View view,
            int bottomPadding,
            boolean reset
    );

    /**
     * 设置 Padding 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return Helper
     */
    T setPadding(
            View view,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Padding 边距
     * @param view    {@link View}
     * @param padding Padding
     * @return Helper
     */
    T setPadding(
            View view,
            int padding
    );

    /**
     * 设置 Padding 边距
     * @param view   {@link View}
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return Helper
     */
    T setPadding(
            View view,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 Padding 边距
     * @param views     View[]
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return Helper
     */
    T setPadding(
            View[] views,
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Padding 边距
     * @param views   View[]
     * @param padding Padding
     * @return Helper
     */
    T setPadding(
            View[] views,
            int padding
    );

    /**
     * 设置 Padding 边距
     * @param views  View[]
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return Helper
     */
    T setPadding(
            View[] views,
            int left,
            int top,
            int right,
            int bottom
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
     * @param view {@link View}
     * @return Helper
     */
    T clearAnimation(final View view);

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
     * @param view {@link View}
     * @return Helper
     */
    T cancelAnimation(final View view);

    /**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     * @return Helper
     */
    T setBackground(
            View view,
            Drawable background
    );

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     * @return Helper
     */
    T setBackgroundColor(
            View view,
            @ColorInt int color
    );

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return Helper
     */
    T setBackgroundResource(
            View view,
            @DrawableRes int resId
    );

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return Helper
     */
    T setBackgroundTintList(
            View view,
            ColorStateList tint
    );

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setBackgroundTintMode(
            View view,
            PorterDuff.Mode tintMode
    );

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     * @return Helper
     */
    T setForeground(
            View view,
            Drawable foreground
    );

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     * @return Helper
     */
    T setForegroundGravity(
            View view,
            int gravity
    );

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return Helper
     */
    T setForegroundTintList(
            View view,
            ColorStateList tint
    );

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setForegroundTintMode(
            View view,
            PorterDuff.Mode tintMode
    );

    /**
     * View 着色处理
     * @param view  {@link View}
     * @param color 颜色值
     * @return Helper
     */
    T setColorFilter(
            View view,
            @ColorInt int color
    );

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return Helper
     */
    T setColorFilter(
            View view,
            Drawable drawable,
            @ColorInt int color
    );

    /**
     * View 着色处理
     * @param view        {@link View}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    T setColorFilter(
            View view,
            ColorFilter colorFilter
    );

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param view        {@link View}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    T setColorFilter(
            View view,
            Drawable drawable,
            ColorFilter colorFilter
    );

    /**
     * 设置 ProgressBar 进度条样式
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @return Helper
     */
    T setProgressDrawable(
            View view,
            Drawable drawable
    );

    /**
     * 设置 ProgressBar 进度值
     * @param view     {@link View}
     * @param progress 当前进度
     * @return Helper
     */
    T setBarProgress(
            View view,
            int progress
    );

    /**
     * 设置 ProgressBar 最大值
     * @param view {@link View}
     * @param max  最大值
     * @return Helper
     */
    T setBarMax(
            View view,
            int max
    );

    /**
     * 设置 ProgressBar 最大值
     * @param view     {@link View}
     * @param progress 当前进度
     * @param max      最大值
     * @return Helper
     */
    T setBarValue(
            View view,
            int progress,
            int max
    );
}