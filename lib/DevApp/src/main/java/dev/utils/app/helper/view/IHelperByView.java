package dev.utils.app.helper.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.ViewGroup;
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

import dev.utils.app.SizeUtils;

/**
 * detail: ViewHelper 接口
 * @author Ttt
 */
public interface IHelperByView<T> {

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
     * @param left   left range
     * @param top    top range
     * @param right  right range
     * @param bottom bottom range
     * @param views  View[]
     * @return Helper
     */
    T addTouchArea(
            int left,
            int top,
            int right,
            int bottom,
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
     * 设置 View TAG
     * @param view   View
     * @param object TAG
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
     * 设置 X 轴位置
     * @param x     X 轴位置
     * @param views View[]
     * @return Helper
     */
    T setX(
            float x,
            View... views
    );

    /**
     * 设置 Y 轴位置
     * @param y     Y 轴位置
     * @param views View[]
     * @return Helper
     */
    T setY(
            float y,
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

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param position 索引
     * @param views    View[]
     * @return Helper
     */
    T smoothScrollToPosition(
            int position,
            View... views
    );

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param position 索引
     * @param views    View[]
     * @return Helper
     */
    T scrollToPosition(
            int position,
            View... views
    );

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @param views View[]
     * @return Helper
     */
    T smoothScrollToTop(View... views);

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @param views View[]
     * @return Helper
     */
    T scrollToTop(View... views);

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
    T smoothScrollToBottom(View... views);

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
    T scrollToBottom(View... views);

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param x     X 轴开始坐标
     * @param y     Y 轴开始坐标
     * @param views View[]
     * @return Helper
     */
    T smoothScrollTo(
            int x,
            int y,
            View... views
    );

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param x     X 轴开始坐标
     * @param y     Y 轴开始坐标
     * @param views View[]
     * @return Helper
     */
    T smoothScrollBy(
            int x,
            int y,
            View... views
    );

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @param views     View[]
     * @return Helper
     */
    T fullScroll(
            int direction,
            View... views
    );

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
    T scrollTo(
            int x,
            int y,
            View... views
    );

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
    T scrollBy(
            int x,
            int y,
            View... views
    );

    // ==================
    // = ImageViewUtils =
    // ==================

    /**
     * 设置 ImageView 是否保持宽高比
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @param imageViews       ImageView[]
     * @return Helper
     */
    T setAdjustViewBounds(
            boolean adjustViewBounds,
            ImageView... imageViews
    );

    /**
     * 设置 ImageView 最大高度
     * @param maxHeight  最大高度
     * @param imageViews ImageView[]
     * @return Helper
     */
    T setMaxHeight(
            int maxHeight,
            ImageView... imageViews
    );

    /**
     * 设置 ImageView 最大宽度
     * @param maxWidth   最大宽度
     * @param imageViews ImageView[]
     * @return Helper
     */
    T setMaxWidth(
            int maxWidth,
            ImageView... imageViews
    );

    /**
     * 设置 ImageView Level
     * @param level level Image
     * @param views View[]
     * @return Helper
     */
    T setImageLevel(
            int level,
            View... views
    );

    /**
     * 设置 ImageView Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return Helper
     */
    T setImageBitmap(
            Bitmap bitmap,
            View... views
    );

    /**
     * 设置 ImageView Drawable
     * @param drawable {@link Bitmap}
     * @param views    View[]
     * @return Helper
     */
    T setImageDrawable(
            Drawable drawable,
            View... views
    );

    /**
     * 设置 ImageView 资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    T setImageResource(
            @DrawableRes int resId,
            View... views
    );

    /**
     * 设置 ImageView Matrix
     * @param matrix {@link Matrix}
     * @param views  View[]
     * @return Helper
     */
    T setImageMatrix(
            Matrix matrix,
            View... views
    );

    /**
     * 设置 ImageView 着色颜色
     * @param tint  着色颜色
     * @param views View[]
     * @return Helper
     */
    T setImageTintList(
            ColorStateList tint,
            View... views
    );

    /**
     * 设置 ImageView 着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @param views    View[]
     * @return Helper
     */
    T setImageTintMode(
            PorterDuff.Mode tintMode,
            View... views
    );

    /**
     * 设置 ImageView 缩放类型
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    T setScaleType(
            ImageView.ScaleType scaleType,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    T setBackgroundResources(
            @DrawableRes int resId,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setBackgroundResources(
            @DrawableRes int resId,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @param views View[]
     * @return Helper
     */
    T setImageResources(
            @DrawableRes int resId,
            View... views
    );

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setImageResources(
            @DrawableRes int resId,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @param views  View[]
     * @return Helper
     */
    T setImageBitmaps(
            Bitmap bitmap,
            View... views
    );

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setImageBitmaps(
            Bitmap bitmap,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @param views    View[]
     * @return Helper
     */
    T setImageDrawables(
            Drawable drawable,
            View... views
    );

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setImageDrawables(
            Drawable drawable,
            int isVisibility,
            View... views
    );

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @param views     View[]
     * @return Helper
     */
    T setScaleTypes(
            ImageView.ScaleType scaleType,
            View... views
    );

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return Helper
     */
    T setScaleTypes(
            ImageView.ScaleType scaleType,
            int isVisibility,
            View... views
    );

    // ===============================
    // = EditTextUtils、TextViewUtils =
    // ===============================

    /**
     * 设置文本
     * @param text  TextView text
     * @param views View[]
     * @return Helper
     */
    T setText(
            CharSequence text,
            View... views
    );

    /**
     * 设置长度限制
     * @param maxLength 长度限制
     * @param views     View[]
     * @return Helper
     */
    T setMaxLength(
            int maxLength,
            View... views
    );

    /**
     * 设置长度限制, 并且设置内容
     * @param content   文本内容
     * @param maxLength 长度限制
     * @param views     View[]
     * @return Helper
     */
    T setMaxLengthAndText(
            CharSequence content,
            int maxLength,
            View... views
    );

    /**
     * 设置输入类型
     * @param type  类型
     * @param views View[]
     * @return Helper
     */
    T setInputType(
            int type,
            View... views
    );

    /**
     * 设置软键盘右下角按钮类型
     * @param imeOptions 软键盘按钮类型
     * @param views      View[]
     * @return Helper
     */
    T setImeOptions(
            int imeOptions,
            View... views
    );

    /**
     * 设置文本视图显示转换
     * @param method {@link TransformationMethod}
     * @param views  View[]
     * @return Helper
     */
    T setTransformationMethod(
            TransformationMethod method,
            View... views
    );

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param views             View[]
     * @return Helper
     */
    T setTransformationMethod(
            boolean isDisplayPassword,
            View... views
    );

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
    T setText(
            CharSequence content,
            boolean isSelect,
            EditText... editTexts
    );

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param content   文本内容
     * @param isSelect  是否设置光标
     * @param editTexts EditText[]
     * @return Helper
     */
    T insert(
            CharSequence content,
            boolean isSelect,
            EditText... editTexts
    );

    /**
     * 追加内容
     * @param content   文本内容
     * @param start     开始添加的位置
     * @param isSelect  是否设置光标
     * @param editTexts EditText[]
     * @return Helper
     */
    T insert(
            CharSequence content,
            int start,
            boolean isSelect,
            EditText... editTexts
    );

    /**
     * 设置是否显示光标
     * @param visible   是否显示光标
     * @param editTexts EditText[]
     * @return Helper
     */
    T setCursorVisible(
            boolean visible,
            EditText... editTexts
    );

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @param editTexts          EditText[]
     * @return Helper
     */
    T setTextCursorDrawable(
            @DrawableRes int textCursorDrawable,
            EditText... editTexts
    );

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @param editTexts          EditText[]
     * @return Helper
     */
    T setTextCursorDrawable(
            Drawable textCursorDrawable,
            EditText... editTexts
    );

    /**
     * 设置光标在第一位
     * @param editTexts EditText[]
     * @return Helper
     */
    T setSelectionToTop(EditText... editTexts);

    /**
     * 设置光标在最后一位
     * @param editTexts EditText[]
     * @return Helper
     */
    T setSelectionToBottom(EditText... editTexts);

    /**
     * 设置光标位置
     * @param index     光标位置
     * @param editTexts EditText[]
     * @return Helper
     */
    T setSelection(
            int index,
            EditText... editTexts
    );

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @param editTexts         EditText[]
     * @return Helper
     */
    T setTransformationMethod(
            boolean isDisplayPassword,
            boolean isSelectBottom,
            EditText... editTexts
    );

    /**
     * 添加输入监听事件
     * @param watcher   输入监听
     * @param editTexts EditText[]
     * @return Helper
     */
    T addTextChangedListener(
            TextWatcher watcher,
            EditText... editTexts
    );

    /**
     * 移除输入监听事件
     * @param watcher   输入监听
     * @param editTexts EditText[]
     * @return Helper
     */
    T removeTextChangedListener(
            TextWatcher watcher,
            EditText... editTexts
    );

    /**
     * 设置 KeyListener
     * @param listener  {@link KeyListener}
     * @param editTexts EditText[]
     * @return Helper
     */
    T setKeyListener(
            KeyListener listener,
            EditText... editTexts
    );

    /**
     * 设置 KeyListener
     * @param accepted  允许输入的内容, 如: 0123456789
     * @param editTexts EditText[]
     * @return Helper
     */
    T setKeyListener(
            String accepted,
            EditText... editTexts
    );

    /**
     * 设置 KeyListener
     * @param accepted  允许输入的内容
     * @param editTexts EditText[]
     * @return Helper
     */
    T setKeyListener(
            char[] accepted,
            EditText... editTexts
    );

    // =================
    // = TextViewUtils =
    // =================

    /**
     * 设置 Hint 文本
     * @param text  Hint text
     * @param views View[]
     * @return Helper
     */
    T setHint(
            CharSequence text,
            View... views
    );

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @param views View[]
     * @return Helper
     */
    T setHintTextColors(
            @ColorInt int color,
            View... views
    );

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View[]
     * @return Helper
     */
    T setHintTextColors(
            ColorStateList colors,
            View... views
    );

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @param views View[]
     * @return Helper
     */
    T setTextColors(
            @ColorInt int color,
            View... views
    );

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @param views  View[]
     * @return Helper
     */
    T setTextColors(
            ColorStateList colors,
            View... views
    );

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @param views   View[]
     * @return Helper
     */
    T setHtmlTexts(
            String content,
            View... views
    );

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @param views    View[]
     * @return Helper
     */
    T setTypeface(
            Typeface typeface,
            View... views
    );

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @param style    样式
     * @param views    View[]
     * @return Helper
     */
    T setTypeface(
            Typeface typeface,
            int style,
            View... views
    );

    /**
     * 设置字体大小 ( px 像素 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    T setTextSizeByPx(
            float size,
            View... views
    );

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    T setTextSizeBySp(
            float size,
            View... views
    );

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    T setTextSizeByDp(
            float size,
            View... views
    );

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    T setTextSizeByIn(
            float size,
            View... views
    );

    /**
     * 设置字体大小
     * @param unit  字体参数类型
     * @param size  字体大小
     * @param views View[]
     * @return Helper
     */
    T setTextSize(
            int unit,
            float size,
            View... views
    );

    /**
     * 清空 flags
     * @param views View[]
     * @return Helper
     */
    T clearFlags(View... views);

    /**
     * 设置 TextView flags
     * @param flags flags
     * @param views View[]
     * @return Helper
     */
    T setPaintFlags(
            int flags,
            View... views
    );

    /**
     * 设置 TextView 抗锯齿 flags
     * @param views View[]
     * @return Helper
     */
    T setAntiAliasFlag(View... views);

    /**
     * 设置 TextView 是否加粗
     * @param views View[]
     * @return Helper
     */
    T setBold(View... views);

    /**
     * 设置 TextView 是否加粗
     * @param isBold {@code true} yes, {@code false} no
     * @param views  View[]
     * @return Helper
     */
    T setBold(
            boolean isBold,
            View... views
    );

    /**
     * 设置 TextView 是否加粗
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @param views    View[]
     * @return Helper
     */
    T setBold(
            Typeface typeface,
            boolean isBold,
            View... views
    );

    /**
     * 设置下划线
     * @param views View[]
     * @return Helper
     */
    T setUnderlineText(View... views);

    /**
     * 设置下划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @param views       View[]
     * @return Helper
     */
    T setUnderlineText(
            boolean isAntiAlias,
            View... views
    );

    /**
     * 设置中划线
     * @param views View[]
     * @return Helper
     */
    T setStrikeThruText(View... views);

    /**
     * 设置中划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @param views       View[]
     * @return Helper
     */
    T setStrikeThruText(
            boolean isAntiAlias,
            View... views
    );

    /**
     * 设置文字水平间距
     * @param letterSpacing 文字水平间距
     * @param views         View[]
     * @return Helper
     */
    T setLetterSpacing(
            float letterSpacing,
            View... views
    );

    /**
     * 设置文字行间距 ( 行高 )
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param views       View[]
     * @return Helper
     */
    T setLineSpacing(
            float lineSpacing,
            View... views
    );

    /**
     * 设置文字行间距 ( 行高 ) 、行间距倍数
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @param views       View[]
     * @return Helper
     */
    T setLineSpacingAndMultiplier(
            float lineSpacing,
            float multiplier,
            View... views
    );

    /**
     * 设置字体水平方向的缩放
     * @param size  缩放比例
     * @param views View[]
     * @return Helper
     */
    T setTextScaleX(
            float size,
            View... views
    );

    /**
     * 设置是否保留字体留白间隙区域
     * @param includePadding 是否保留字体留白间隙区域
     * @param views          View[]
     * @return Helper
     */
    T setIncludeFontPadding(
            boolean includePadding,
            View... views
    );

    /**
     * 设置行数
     * @param lines 行数
     * @param views View[]
     * @return Helper
     */
    T setLines(
            int lines,
            View... views
    );

    /**
     * 设置最大行数
     * @param maxLines 最大行数
     * @param views    View[]
     * @return Helper
     */
    T setMaxLines(
            int maxLines,
            View... views
    );

    /**
     * 设置最小行数
     * @param minLines 最小行数
     * @param views    View[]
     * @return Helper
     */
    T setMinLines(
            int minLines,
            View... views
    );

    /**
     * 设置最大字符宽度限制
     * @param maxEms 最大字符
     * @param views  View[]
     * @return Helper
     */
    T setMaxEms(
            int maxEms,
            View... views
    );

    /**
     * 设置最小字符宽度限制
     * @param minEms 最小字符
     * @param views  View[]
     * @return Helper
     */
    T setMinEms(
            int minEms,
            View... views
    );

    /**
     * 设置指定字符宽度
     * @param ems   字符
     * @param views View[]
     * @return Helper
     */
    T setEms(
            int ems,
            View... views
    );

    /**
     * 设置 Ellipsize 效果
     * @param where {@link TextUtils.TruncateAt}
     * @param views View[]
     * @return Helper
     */
    T setEllipsize(
            TextUtils.TruncateAt where,
            View... views
    );

    /**
     * 设置自动识别文本链接
     * @param mask  {@link android.text.util.Linkify}
     * @param views View[]
     * @return Helper
     */
    T setAutoLinkMask(
            int mask,
            View... views
    );

    /**
     * 设置文本全为大写
     * @param allCaps 是否全部大写
     * @param views   View[]
     * @return Helper
     */
    T setAllCaps(
            boolean allCaps,
            View... views
    );

    /**
     * 设置 Gravity
     * @param gravity {@link android.view.Gravity}
     * @param views   View[]
     * @return Helper
     */
    T setGravity(
            int gravity,
            View... views
    );

    /**
     * 设置 CompoundDrawables Padding
     * @param padding   CompoundDrawables Padding
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablePadding(
            int padding,
            TextView... textViews
    );

    /**
     * 设置 Left CompoundDrawables
     * @param left      left Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesByLeft(
            Drawable left,
            TextView... textViews
    );

    /**
     * 设置 Top CompoundDrawables
     * @param top       top Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesByTop(
            Drawable top,
            TextView... textViews
    );

    /**
     * 设置 Right CompoundDrawables
     * @param right     right Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesByRight(
            Drawable right,
            TextView... textViews
    );

    /**
     * 设置 Bottom CompoundDrawables
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesByBottom(
            Drawable bottom,
            TextView... textViews
    );

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
    T setCompoundDrawables(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom,
            TextView... textViews
    );

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left      left Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByLeft(
            Drawable left,
            TextView... textViews
    );

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param top       top Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByTop(
            Drawable top,
            TextView... textViews
    );

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param right     right Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByRight(
            Drawable right,
            TextView... textViews
    );

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByBottom(
            Drawable bottom,
            TextView... textViews
    );

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left      left Drawable
     * @param top       top Drawable
     * @param right     right Drawable
     * @param bottom    bottom Drawable
     * @param textViews TextView[]
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBounds(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom,
            TextView... textViews
    );

    /**
     * 通过设置默认的自动调整大小配置, 决定是否自动缩放文本
     * @param autoSizeTextType 自动调整大小类型
     * @param views            View[]
     * @return Helper
     */
    T setAutoSizeTextTypeWithDefaults(
            @TextViewCompat.AutoSizeTextType int autoSizeTextType,
            View... views
    );

    /**
     * 设置 TextView 自动调整字体大小配置
     * @param autoSizeMinTextSize     自动调整最小字体大小
     * @param autoSizeMaxTextSize     自动调整最大字体大小
     * @param autoSizeStepGranularity 自动调整大小变动粒度 ( 跨度区间值 )
     * @param unit                    字体参数类型
     * @param views                   View[]
     * @return Helper
     */
    T setAutoSizeTextTypeUniformWithConfiguration(
            int autoSizeMinTextSize,
            int autoSizeMaxTextSize,
            int autoSizeStepGranularity,
            int unit,
            View... views
    );

    /**
     * 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM
     * @param presetSizes 预设字体大小范围像素为单位
     * @param unit        字体参数类型
     * @param views       View[]
     * @return Helper
     */
    T setAutoSizeTextTypeUniformWithPresetSizes(
            int[] presetSizes,
            int unit,
            View... views
    );

    // =====================
    // = RecyclerViewUtils =
    // =====================

    /**
     * 设置 RecyclerView LayoutManager
     * @param view          {@link View}
     * @param layoutManager LayoutManager
     * @return Helper
     */
    T setLayoutManager(
            View view,
            RecyclerView.LayoutManager layoutManager
    );

    /**
     * 设置 GridLayoutManager SpanCount
     * @param view      {@link View}
     * @param spanCount Span Count
     * @return Helper
     */
    T setSpanCount(
            View view,
            int spanCount
    );

    /**
     * 设置 GridLayoutManager SpanCount
     * @param layoutManager LayoutManager
     * @param spanCount     Span Count
     * @return Helper
     */
    T setSpanCount(
            RecyclerView.LayoutManager layoutManager,
            int spanCount
    );

    /**
     * 设置 RecyclerView Orientation
     * @param view        {@link View}
     * @param orientation 方向
     * @return Helper
     */
    T setOrientation(
            View view,
            @RecyclerView.Orientation int orientation
    );

    /**
     * 设置 RecyclerView Adapter
     * @param view    {@link View}
     * @param adapter Adapter
     * @return Helper
     */
    T setAdapter(
            View view,
            RecyclerView.Adapter<?> adapter
    );

    /**
     * RecyclerView notifyItemRemoved
     * @param view     {@link View}
     * @param position 索引
     * @return Helper
     */
    T notifyItemRemoved(
            View view,
            int position
    );

    /**
     * RecyclerView notifyItemInserted
     * @param view     {@link View}
     * @param position 索引
     * @return Helper
     */
    T notifyItemInserted(
            View view,
            int position
    );

    /**
     * RecyclerView notifyItemMoved
     * @param view         {@link View}
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return Helper
     */
    T notifyItemMoved(
            View view,
            int fromPosition,
            int toPosition
    );

    /**
     * RecyclerView notifyDataSetChanged
     * @param view {@link View}
     * @return Helper
     */
    T notifyDataSetChanged(View view);

    /**
     * 设置 RecyclerView LinearSnapHelper
     * @param view {@link View}
     * @return Helper
     */
    T attachLinearSnapHelper(View view);

    /**
     * 设置 RecyclerView PagerSnapHelper
     * @param view {@link View}
     * @return Helper
     */
    T attachPagerSnapHelper(View view);

    /**
     * 添加 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    T addItemDecoration(
            View view,
            RecyclerView.ItemDecoration decor
    );

    /**
     * 添加 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @param index 添加索引
     * @return Helper
     */
    T addItemDecoration(
            View view,
            RecyclerView.ItemDecoration decor,
            int index
    );

    /**
     * 移除 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    T removeItemDecoration(
            View view,
            RecyclerView.ItemDecoration decor
    );

    /**
     * 移除 RecyclerView ItemDecoration
     * @param view  {@link View}
     * @param index RecyclerView ItemDecoration 索引
     * @return Helper
     */
    T removeItemDecorationAt(
            View view,
            int index
    );

    /**
     * 移除 RecyclerView 全部 ItemDecoration
     * @param view {@link View}
     * @return Helper
     */
    T removeAllItemDecoration(View view);

    /**
     * 设置 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return Helper
     */
    T setOnScrollListener(
            View view,
            RecyclerView.OnScrollListener listener
    );

    /**
     * 添加 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return Helper
     */
    T addOnScrollListener(
            View view,
            RecyclerView.OnScrollListener listener
    );

    /**
     * 移除 RecyclerView ScrollListener
     * @param view     {@link View}
     * @param listener ScrollListener
     * @return Helper
     */
    T removeOnScrollListener(
            View view,
            RecyclerView.OnScrollListener listener
    );

    /**
     * 清空 RecyclerView ScrollListener
     * @param view {@link View}
     * @return Helper
     */
    T clearOnScrollListeners(View view);

    /**
     * 设置 RecyclerView 嵌套滚动开关
     * @param enabled 嵌套滚动开关
     * @param views   View[]
     * @return Helper
     */
    T setNestedScrollingEnabled(
            boolean enabled,
            View... views
    );

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * @param view     {@link View}
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return Helper
     */
    T forceGetViewSize(
            View view,
            SizeUtils.OnGetSizeListener listener
    );
}