package dev.utils.app.helper.quick;

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
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import dev.utils.app.SizeUtils;

/**
 * detail: QuickHelper 接口
 * @author Ttt
 */
public interface IHelperByQuick<T> {

    // ==============
    // = ClickUtils =
    // ==============

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param range 点击范围
     * @return Helper
     */
    T addTouchArea(int range);

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param left   left range
     * @param top    top range
     * @param right  right range
     * @param bottom bottom range
     * @return Helper
     */
    T addTouchArea(
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @return Helper
     */
    T setOnClick(View.OnClickListener listener);

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @return Helper
     */
    T setOnLongClick(View.OnLongClickListener listener);

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @return Helper
     */
    T setOnTouch(View.OnTouchListener listener);

    // =============
    // = ViewUtils =
    // =============

    /**
     * 设置 View Id
     * @param id View Id
     * @return Helper
     */
    T setId(int id);

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @return Helper
     */
    T setClipChildren(boolean clipChildren);

    /**
     * 移除全部子 View
     * @return Helper
     */
    T removeAllViews();

    /**
     * 添加 View
     * @param child 待添加 View
     * @return Helper
     */
    T addView(View child);

    /**
     * 添加 View
     * @param child 待添加 View
     * @param index 添加位置索引
     * @return Helper
     */
    T addView(
            View child,
            int index
    );

    /**
     * 添加 View
     * @param child  待添加 View
     * @param index  添加位置索引
     * @param params LayoutParams
     * @return Helper
     */
    T addView(
            View child,
            int index,
            ViewGroup.LayoutParams params
    );

    /**
     * 添加 View
     * @param child  待添加 View
     * @param params LayoutParams
     * @return Helper
     */
    T addView(
            View child,
            ViewGroup.LayoutParams params
    );

    /**
     * 添加 View
     * @param child  待添加 View
     * @param width  View 宽度
     * @param height View 高度
     * @return Helper
     */
    T addView(
            View child,
            int width,
            int height
    );

    /**
     * 设置 View LayoutParams
     * @param params LayoutParams
     * @return Helper
     */
    T setLayoutParams(ViewGroup.LayoutParams params);

    /**
     * 设置 View[] 宽度、高度
     * @param width  View 宽度
     * @param height View 高度
     * @return Helper
     */
    T setWidthHeight(
            int width,
            int height
    );

    /**
     * 设置 View[] 宽度、高度
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    T setWidthHeight(
            int width,
            int height,
            boolean nullNewLP
    );

    /**
     * 设置 View weight 权重
     * @param weight 权重比例
     * @return Helper
     */
    T setWeight(float weight);

    /**
     * 设置 View 宽度
     * @param width View 宽度
     * @return Helper
     */
    T setWidth(int width);

    /**
     * 设置 View 宽度
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    T setWidth(
            int width,
            boolean nullNewLP
    );

    /**
     * 设置 View 高度
     * @param height View 高度
     * @return Helper
     */
    T setHeight(int height);

    /**
     * 设置 View 高度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    T setHeight(
            int height,
            boolean nullNewLP
    );

    /**
     * 设置 View 最小宽度
     * @param minWidth 最小宽度
     * @return Helper
     */
    T setMinimumWidth(int minWidth);

    /**
     * 设置 View 最小高度
     * @param minHeight 最小高度
     * @return Helper
     */
    T setMinimumHeight(int minHeight);

    /**
     * 设置 View 透明度
     * @param alpha 透明度
     * @return Helper
     */
    T setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha);

    /**
     * 设置 View TAG
     * @param object TAG
     * @return Helper
     */
    T setTag(Object object);

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param value X 轴坐标
     * @return Helper
     */
    T setScrollX(int value);

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param value Y 轴坐标
     * @return Helper
     */
    T setScrollY(int value);

    /**
     * 设置 ViewGroup 和其子控件两者之间的关系
     * <pre>
     *     beforeDescendants : ViewGroup 会优先其子类控件而获取到焦点
     *     afterDescendants : ViewGroup 只有当其子类控件不需要获取焦点时才获取焦点
     *     blocksDescendants : ViewGroup 会覆盖子类控件而直接获得焦点
     *     android:descendantFocusability="blocksDescendants"
     * </pre>
     * @param focusability {@link ViewGroup#FOCUS_BEFORE_DESCENDANTS}、{@link ViewGroup#FOCUS_AFTER_DESCENDANTS}、{@link ViewGroup#FOCUS_BLOCK_DESCENDANTS}
     * @return Helper
     */
    T setDescendantFocusability(int focusability);

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @return Helper
     */
    T setOverScrollMode(int overScrollMode);

    /**
     * 设置是否绘制横向滚动条
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    T setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled);

    /**
     * 设置是否绘制垂直滚动条
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    T setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled);

    /**
     * 设置 View 滚动效应
     * @param isScrollContainer 是否需要滚动效应
     * @return Helper
     */
    T setScrollContainer(boolean isScrollContainer);

    /**
     * 设置下一个获取焦点的 View id
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusForwardId(@IdRes int nextFocusForwardId);

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusDownId(@IdRes int nextFocusDownId);

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusLeftId(@IdRes int nextFocusLeftId);

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusRightId(@IdRes int nextFocusRightId);

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return Helper
     */
    T setNextFocusUpId(@IdRes int nextFocusUpId);

    /**
     * 设置 View 旋转度数
     * @param rotation 旋转度数
     * @return Helper
     */
    T setRotation(float rotation);

    /**
     * 设置 View 水平旋转度数
     * @param rotationX 水平旋转度数
     * @return Helper
     */
    T setRotationX(float rotationX);

    /**
     * 设置 View 竖直旋转度数
     * @param rotationY 竖直旋转度数
     * @return Helper
     */
    T setRotationY(float rotationY);

    /**
     * 设置 View 水平方向缩放比例
     * @param scaleX 水平方向缩放比例
     * @return Helper
     */
    T setScaleX(float scaleX);

    /**
     * 设置 View 竖直方向缩放比例
     * @param scaleY 竖直方向缩放比例
     * @return Helper
     */
    T setScaleY(float scaleY);

    /**
     * 设置文本的显示方式
     * @param textAlignment 文本的显示方式
     * @return Helper
     */
    T setTextAlignment(int textAlignment);

    /**
     * 设置文本的显示方向
     * @param textDirection 文本的显示方向
     * @return Helper
     */
    T setTextDirection(int textDirection);

    /**
     * 设置水平方向偏转量
     * @param pivotX 水平方向偏转量
     * @return Helper
     */
    T setPivotX(float pivotX);

    /**
     * 设置竖直方向偏转量
     * @param pivotY 竖直方向偏转量
     * @return Helper
     */
    T setPivotY(float pivotY);

    /**
     * 设置水平方向的移动距离
     * @param translationX 水平方向的移动距离
     * @return Helper
     */
    T setTranslationX(float translationX);

    /**
     * 设置竖直方向的移动距离
     * @param translationY 竖直方向的移动距离
     * @return Helper
     */
    T setTranslationY(float translationY);

    /**
     * 设置 X 轴位置
     * @param x X 轴位置
     * @return Helper
     */
    T setX(float x);

    /**
     * 设置 Y 轴位置
     * @param y Y 轴位置
     * @return Helper
     */
    T setY(float y);

    /**
     * 设置 View 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return Helper
     */
    T setLayerType(
            int layerType,
            Paint paint
    );

    /**
     * 请求重新对 View 布局
     * @return Helper
     */
    T requestLayout();

    /**
     * View 请求获取焦点
     * @return Helper
     */
    T requestFocus();

    /**
     * View 清除焦点
     * @return Helper
     */
    T clearFocus();

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @return Helper
     */
    T setFocusableInTouchMode(boolean focusableInTouchMode);

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @return Helper
     */
    T setFocusable(boolean focusable);

    /**
     * 切换获取焦点状态
     * @return Helper
     */
    T toggleFocusable();

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @return Helper
     */
    T setSelected(boolean selected);

    /**
     * 切换选中状态
     * @return Helper
     */
    T toggleSelected();

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @return Helper
     */
    T setEnabled(boolean enabled);

    /**
     * 切换 View 是否启用状态
     * @return Helper
     */
    T toggleEnabled();

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @return Helper
     */
    T setClickable(boolean clickable);

    /**
     * 切换 View 是否可以点击状态
     * @return Helper
     */
    T toggleClickable();

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @return Helper
     */
    T setLongClickable(boolean longClickable);

    /**
     * 切换 View 是否可以长按状态
     * @return Helper
     */
    T toggleLongClickable();

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @return Helper
     */
    T setVisibilitys(boolean isVisibility);

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T setVisibilitys(int isVisibility);

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @return Helper
     */
    T setVisibilityINs(boolean isVisibility);

    /**
     * 切换 View 显示的状态
     * @param views View[]
     * @return Helper
     */
    T toggleVisibilitys(View... views);

    /**
     * 切换 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views View[]
     * @return Helper
     */
    T toggleVisibilitys(
            int state,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views View[]
     * @return Helper
     */
    T reverseVisibilitys(
            int state,
            View... views
    );

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return Helper
     */
    T reverseVisibilitys(
            boolean isVisibility,
            View... views
    );

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T toggleViews(
            boolean isChange,
            int isVisibility
    );

    /**
     * 把自身从父 View 中移除
     * @return Helper
     */
    T removeSelfFromParent();

    /**
     * View 请求更新
     * @param allParent 是否全部父布局 View 都请求
     * @return Helper
     */
    T requestLayoutParent(boolean allParent);

    /**
     * 测量 View
     * @param specifiedWidth 指定宽度
     * @return Helper
     */
    T measureView(int specifiedWidth);

    /**
     * 测量 View
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return Helper
     */
    T measureView(
            int specifiedWidth,
            int specifiedHeight
    );

    /**
     * 设置 View Layout Gravity
     * @param gravity Gravity
     * @return Helper
     */
    T setLayoutGravity(int gravity);

    /**
     * 设置 View Layout Gravity
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return Helper
     */
    T setLayoutGravity(
            int gravity,
            boolean isReflection
    );

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @return Helper
     */
    T setMarginLeft(int leftMargin);

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return Helper
     */
    T setMarginLeft(
            int leftMargin,
            boolean reset
    );

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @return Helper
     */
    T setMarginTop(int topMargin);

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return Helper
     */
    T setMarginTop(
            int topMargin,
            boolean reset
    );

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @return Helper
     */
    T setMarginRight(int rightMargin);

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return Helper
     */
    T setMarginRight(
            int rightMargin,
            boolean reset
    );

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @return Helper
     */
    T setMarginBottom(int bottomMargin);

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return Helper
     */
    T setMarginBottom(
            int bottomMargin,
            boolean reset
    );

    /**
     * 设置 Margin 边距
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return Helper
     */
    T setMargin(
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Margin 边距
     * @param margin Margin
     * @return Helper
     */
    T setMargin(int margin);

    /**
     * 设置 Margin 边距
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return Helper
     */
    T setMargin(
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @return Helper
     */
    T setPaddingLeft(int leftPadding);

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingLeft(
            int leftPadding,
            boolean reset
    );

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @return Helper
     */
    T setPaddingTop(int topPadding);

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingTop(
            int topPadding,
            boolean reset
    );

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @return Helper
     */
    T setPaddingRight(int rightPadding);

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingRight(
            int rightPadding,
            boolean reset
    );

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @return Helper
     */
    T setPaddingBottom(int bottomPadding);

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return Helper
     */
    T setPaddingBottom(
            int bottomPadding,
            boolean reset
    );

    /**
     * 设置 Padding 边距
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return Helper
     */
    T setPadding(
            int leftRight,
            int topBottom
    );

    /**
     * 设置 Padding 边距
     * @param padding Padding
     * @return Helper
     */
    T setPadding(int padding);

    /**
     * 设置 Padding 边距
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return Helper
     */
    T setPadding(
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb 布局位置
     * @return Helper
     */
    T addRules(int verb);

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @return Helper
     */
    T addRules(
            int verb,
            int subject
    );

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb 布局位置
     * @return Helper
     */
    T removeRules(int verb);

    /**
     * 设置动画
     * @param animation {@link Animation}
     * @return Helper
     */
    T setAnimation(Animation animation);

    /**
     * 清空动画
     * @return Helper
     */
    T clearAnimation();

    /**
     * 启动动画
     * @return Helper
     */
    T startAnimation();

    /**
     * 启动动画
     * @param animation {@link Animation}
     * @return Helper
     */
    T startAnimation(Animation animation);

    /**
     * 取消动画
     * @return Helper
     */
    T cancelAnimation();

    /**
     * 设置背景图片
     * @param background 背景图片
     * @return Helper
     */
    T setBackground(Drawable background);

    /**
     * 设置背景颜色
     * @param color 背景颜色
     * @return Helper
     */
    T setBackgroundColor(@ColorInt int color);

    /**
     * 设置背景资源
     * @param resId resource identifier
     * @return Helper
     */
    T setBackgroundResource(@DrawableRes int resId);

    /**
     * 设置背景着色颜色
     * @param tint 着色颜色
     * @return Helper
     */
    T setBackgroundTintList(ColorStateList tint);

    /**
     * 设置背景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setBackgroundTintMode(PorterDuff.Mode tintMode);

    /**
     * 设置前景图片
     * @param foreground 前景图片
     * @return Helper
     */
    T setForeground(Drawable foreground);

    /**
     * 设置前景重心
     * @param gravity 重心
     * @return Helper
     */
    T setForegroundGravity(int gravity);

    /**
     * 设置前景着色颜色
     * @param tint 着色颜色
     * @return Helper
     */
    T setForegroundTintList(ColorStateList tint);

    /**
     * 设置前景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setForegroundTintMode(PorterDuff.Mode tintMode);

    /**
     * View 着色处理
     * @param color 颜色值
     * @return Helper
     */
    T setColorFilter(@ColorInt int color);

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return Helper
     */
    T setColorFilter(
            Drawable drawable,
            @ColorInt int color
    );

    /**
     * View 着色处理
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    T setColorFilter(ColorFilter colorFilter);

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    T setColorFilter(
            Drawable drawable,
            ColorFilter colorFilter
    );

    /**
     * 设置 ProgressBar 进度条样式
     * @param drawable {@link Drawable}
     * @return Helper
     */
    T setProgressDrawable(Drawable drawable);

    /**
     * 设置 ProgressBar 进度值
     * @param progress 当前进度
     * @return Helper
     */
    T setBarProgress(int progress);

    /**
     * 设置 ProgressBar 最大值
     * @param max 最大值
     * @return Helper
     */
    T setBarMax(int max);

    /**
     * 设置 ProgressBar 最大值
     * @param progress 当前进度
     * @param max      最大值
     * @return Helper
     */
    T setBarValue(
            int progress,
            int max
    );

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param position 索引
     * @return Helper
     */
    T smoothScrollToPosition(int position);

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param position 索引
     * @return Helper
     */
    T scrollToPosition(int position);

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @return Helper
     */
    T smoothScrollToTop();

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @return Helper
     */
    T scrollToTop();

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部 )
     *     smoothScrollToBottom(view)
     *     smoothScrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @return Helper
     */
    T smoothScrollToBottom();

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @return Helper
     */
    T scrollToBottom();

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    T smoothScrollTo(
            int x,
            int y
    );

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    T smoothScrollBy(
            int x,
            int y
    );

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @return Helper
     */
    T fullScroll(int direction);

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    T scrollTo(
            int x,
            int y
    );

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    T scrollBy(
            int x,
            int y
    );

    // ==================
    // = ImageViewUtils =
    // ==================

    /**
     * 设置 ImageView 是否保持宽高比
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @return Helper
     */
    T setAdjustViewBounds(boolean adjustViewBounds);

    /**
     * 设置 ImageView 最大高度
     * @param maxHeight 最大高度
     * @return Helper
     */
    T setMaxHeight(int maxHeight);

    /**
     * 设置 ImageView 最大宽度
     * @param maxWidth 最大宽度
     * @return Helper
     */
    T setMaxWidth(int maxWidth);

    /**
     * 设置 ImageView Level
     * @param level level Image
     * @return Helper
     */
    T setImageLevel(int level);

    /**
     * 设置 ImageView Bitmap
     * @param bitmap {@link Bitmap}
     * @return Helper
     */
    T setImageBitmap(Bitmap bitmap);

    /**
     * 设置 ImageView Drawable
     * @param drawable {@link Bitmap}
     * @return Helper
     */
    T setImageDrawable(Drawable drawable);

    /**
     * 设置 ImageView 资源
     * @param resId resource identifier
     * @return Helper
     */
    T setImageResource(@DrawableRes int resId);

    /**
     * 设置 ImageView Matrix
     * @param matrix {@link Matrix}
     * @return Helper
     */
    T setImageMatrix(Matrix matrix);

    /**
     * 设置 ImageView 着色颜色
     * @param tint 着色颜色
     * @return Helper
     */
    T setImageTintList(ColorStateList tint);

    /**
     * 设置 ImageView 着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    T setImageTintMode(PorterDuff.Mode tintMode);

    /**
     * 设置 ImageView 缩放类型
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @return Helper
     */
    T setScaleType(ImageView.ScaleType scaleType);

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @return Helper
     */
    T setBackgroundResources(@DrawableRes int resId);

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T setBackgroundResources(
            @DrawableRes int resId,
            int isVisibility
    );

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @return Helper
     */
    T setImageResources(@DrawableRes int resId);

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T setImageResources(
            @DrawableRes int resId,
            int isVisibility
    );

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @return Helper
     */
    T setImageBitmaps(Bitmap bitmap);

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T setImageBitmaps(
            Bitmap bitmap,
            int isVisibility
    );

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @return Helper
     */
    T setImageDrawables(Drawable drawable);

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T setImageDrawables(
            Drawable drawable,
            int isVisibility
    );

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @return Helper
     */
    T setScaleTypes(ImageView.ScaleType scaleType);

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    T setScaleTypes(
            ImageView.ScaleType scaleType,
            int isVisibility
    );

    // ===============================
    // = EditTextUtils、TextViewUtils =
    // ===============================

    /**
     * 设置文本
     * @param text TextView text
     * @return Helper
     */
    T setText(CharSequence text);

    /**
     * 设置长度限制
     * @param maxLength 长度限制
     * @return Helper
     */
    T setMaxLength(int maxLength);

    /**
     * 设置长度限制, 并且设置内容
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return Helper
     */
    T setMaxLengthAndText(
            CharSequence content,
            int maxLength
    );

    /**
     * 设置输入类型
     * @param type 类型
     * @return Helper
     */
    T setInputType(int type);

    /**
     * 设置软键盘右下角按钮类型
     * @param imeOptions 软键盘按钮类型
     * @return Helper
     */
    T setImeOptions(int imeOptions);

    /**
     * 设置文本视图显示转换
     * @param method {@link TransformationMethod}
     * @return Helper
     */
    T setTransformationMethod(TransformationMethod method);

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @return Helper
     */
    T setTransformationMethod(boolean isDisplayPassword);

    // =================
    // = EditTextUtils =
    // =================

    /**
     * 设置内容
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return Helper
     */
    T setText(
            CharSequence content,
            boolean isSelect
    );

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return Helper
     */
    T insert(
            CharSequence content,
            boolean isSelect
    );

    /**
     * 追加内容
     * @param content  文本内容
     * @param start    开始添加的位置
     * @param isSelect 是否设置光标
     * @return Helper
     */
    T insert(
            CharSequence content,
            int start,
            boolean isSelect
    );

    /**
     * 设置是否显示光标
     * @param visible 是否显示光标
     * @return Helper
     */
    T setCursorVisible(boolean visible);

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @return Helper
     */
    T setTextCursorDrawable(@DrawableRes int textCursorDrawable);

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @return Helper
     */
    T setTextCursorDrawable(Drawable textCursorDrawable);

    /**
     * 设置光标在第一位
     * @return Helper
     */
    T setSelectionToTop();

    /**
     * 设置光标在最后一位
     * @return Helper
     */
    T setSelectionToBottom();

    /**
     * 设置光标位置
     * @param index 光标位置
     * @return Helper
     */
    T setSelection(int index);

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @return Helper
     */
    T setTransformationMethod(
            boolean isDisplayPassword,
            boolean isSelectBottom
    );

    /**
     * 添加输入监听事件
     * @param watcher 输入监听
     * @return Helper
     */
    T addTextChangedListener(TextWatcher watcher);

    /**
     * 移除输入监听事件
     * @param watcher 输入监听
     * @return Helper
     */
    T removeTextChangedListener(TextWatcher watcher);

    /**
     * 设置 KeyListener
     * @param listener {@link KeyListener}
     * @return Helper
     */
    T setKeyListener(KeyListener listener);

    /**
     * 设置 KeyListener
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return Helper
     */
    T setKeyListener(String accepted);

    /**
     * 设置 KeyListener
     * @param accepted 允许输入的内容
     * @return Helper
     */
    T setKeyListener(char[] accepted);

    // =================
    // = TextViewUtils =
    // =================

    /**
     * 设置 Hint 文本
     * @param text Hint text
     * @return Helper
     */
    T setHint(CharSequence text);

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @return Helper
     */
    T setHintTextColors(@ColorInt int color);

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @return Helper
     */
    T setHintTextColors(ColorStateList colors);

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @return Helper
     */
    T setTextColors(@ColorInt int color);

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @return Helper
     */
    T setTextColors(ColorStateList colors);

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @return Helper
     */
    T setHtmlTexts(String content);

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @return Helper
     */
    T setTypeface(Typeface typeface);

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @param style    样式
     * @return Helper
     */
    T setTypeface(
            Typeface typeface,
            int style
    );

    /**
     * 设置字体大小 ( px 像素 )
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeByPx(float size);

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeBySp(float size);

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeByDp(float size);

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param size 字体大小
     * @return Helper
     */
    T setTextSizeByIn(float size);

    /**
     * 设置字体大小
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return Helper
     */
    T setTextSize(
            int unit,
            float size
    );

    /**
     * 清空 flags
     * @return Helper
     */
    T clearFlags();

    /**
     * 设置 TextView flags
     * @param flags flags
     * @return Helper
     */
    T setPaintFlags(int flags);

    /**
     * 设置 TextView 抗锯齿 flags
     * @return Helper
     */
    T setAntiAliasFlag();

    /**
     * 设置 TextView 是否加粗
     * @return Helper
     */
    T setBold();

    /**
     * 设置 TextView 是否加粗
     * @param isBold {@code true} yes, {@code false} no
     * @return Helper
     */
    T setBold(boolean isBold);

    /**
     * 设置 TextView 是否加粗
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return Helper
     */
    T setBold(
            Typeface typeface,
            boolean isBold
    );

    /**
     * 设置下划线
     * @return Helper
     */
    T setUnderlineText();

    /**
     * 设置下划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @return Helper
     */
    T setUnderlineText(boolean isAntiAlias);

    /**
     * 设置中划线
     * @return Helper
     */
    T setStrikeThruText();

    /**
     * 设置中划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @return Helper
     */
    T setStrikeThruText(boolean isAntiAlias);

    /**
     * 设置文字水平间距
     * @param letterSpacing 文字水平间距
     * @return Helper
     */
    T setLetterSpacing(float letterSpacing);

    /**
     * 设置文字行间距 ( 行高 )
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return Helper
     */
    T setLineSpacing(float lineSpacing);

    /**
     * 设置文字行间距 ( 行高 ) 、行间距倍数
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return Helper
     */
    T setLineSpacingAndMultiplier(
            float lineSpacing,
            float multiplier
    );

    /**
     * 设置字体水平方向的缩放
     * @param size 缩放比例
     * @return Helper
     */
    T setTextScaleX(float size);

    /**
     * 设置是否保留字体留白间隙区域
     * @param includePadding 是否保留字体留白间隙区域
     * @return Helper
     */
    T setIncludeFontPadding(boolean includePadding);

    /**
     * 设置行数
     * @param lines 行数
     * @return Helper
     */
    T setLines(int lines);

    /**
     * 设置最大行数
     * @param maxLines 最大行数
     * @return Helper
     */
    T setMaxLines(int maxLines);

    /**
     * 设置最小行数
     * @param minLines 最小行数
     * @return Helper
     */
    T setMinLines(int minLines);

    /**
     * 设置最大字符宽度限制
     * @param maxEms 最大字符
     * @return Helper
     */
    T setMaxEms(int maxEms);

    /**
     * 设置最小字符宽度限制
     * @param minEms 最小字符
     * @return Helper
     */
    T setMinEms(int minEms);

    /**
     * 设置指定字符宽度
     * @param ems 字符
     * @return Helper
     */
    T setEms(int ems);

    /**
     * 设置 Ellipsize 效果
     * @param where {@link TextUtils.TruncateAt}
     * @return Helper
     */
    T setEllipsize(TextUtils.TruncateAt where);

    /**
     * 设置自动识别文本链接
     * @param mask {@link android.text.util.Linkify}
     * @return Helper
     */
    T setAutoLinkMask(int mask);

    /**
     * 设置文本全为大写
     * @param allCaps 是否全部大写
     * @return Helper
     */
    T setAllCaps(boolean allCaps);

    /**
     * 设置 Gravity
     * @param gravity {@link android.view.Gravity}
     * @return Helper
     */
    T setGravity(int gravity);

    /**
     * 设置 CompoundDrawables Padding
     * @param padding CompoundDrawables Padding
     * @return Helper
     */
    T setCompoundDrawablePadding(int padding);

    /**
     * 设置 Left CompoundDrawables
     * @param left left Drawable
     * @return Helper
     */
    T setCompoundDrawablesByLeft(Drawable left);

    /**
     * 设置 Top CompoundDrawables
     * @param top top Drawable
     * @return Helper
     */
    T setCompoundDrawablesByTop(Drawable top);

    /**
     * 设置 Right CompoundDrawables
     * @param right right Drawable
     * @return Helper
     */
    T setCompoundDrawablesByRight(Drawable right);

    /**
     * 设置 Bottom CompoundDrawables
     * @param bottom bottom Drawable
     * @return Helper
     */
    T setCompoundDrawablesByBottom(Drawable bottom);

    /**
     * 设置 CompoundDrawables
     * <pre>
     *     CompoundDrawable 的大小控制是通过 drawable.setBounds() 控制
     *     需要先设置 Drawable 的 setBounds
     *     {@link dev.utils.app.image.ImageUtils#setBounds}
     * </pre>
     * @param left   left Drawable
     * @param top    top Drawable
     * @param right  right Drawable
     * @param bottom bottom Drawable
     * @return Helper
     */
    T setCompoundDrawables(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    );

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left left Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByLeft(Drawable left);

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param top top Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByTop(Drawable top);

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param right right Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByRight(Drawable right);

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param bottom bottom Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBoundsByBottom(Drawable bottom);

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left   left Drawable
     * @param top    top Drawable
     * @param right  right Drawable
     * @param bottom bottom Drawable
     * @return Helper
     */
    T setCompoundDrawablesWithIntrinsicBounds(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    );

    /**
     * 通过设置默认的自动调整大小配置, 决定是否自动缩放文本
     * @param autoSizeTextType 自动调整大小类型
     * @return Helper
     */
    T setAutoSizeTextTypeWithDefaults(
            @TextViewCompat.AutoSizeTextType int autoSizeTextType
    );

    /**
     * 设置 TextView 自动调整字体大小配置
     * @param autoSizeMinTextSize     自动调整最小字体大小
     * @param autoSizeMaxTextSize     自动调整最大字体大小
     * @param autoSizeStepGranularity 自动调整大小变动粒度 ( 跨度区间值 )
     * @param unit                    字体参数类型
     * @return Helper
     */
    T setAutoSizeTextTypeUniformWithConfiguration(
            int autoSizeMinTextSize,
            int autoSizeMaxTextSize,
            int autoSizeStepGranularity,
            int unit
    );

    /**
     * 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM
     * @param presetSizes 预设字体大小范围像素为单位
     * @param unit        字体参数类型
     * @return Helper
     */
    T setAutoSizeTextTypeUniformWithPresetSizes(
            int[] presetSizes,
            int unit
    );

    // =====================
    // = RecyclerViewUtils =
    // =====================

    /**
     * 设置 RecyclerView LayoutManager
     * @param layoutManager LayoutManager
     * @return Helper
     */
    T setLayoutManager(RecyclerView.LayoutManager layoutManager);

    /**
     * 设置 GridLayoutManager SpanCount
     * @param spanCount Span Count
     * @return Helper
     */
    T setSpanCount(int spanCount);

    /**
     * 设置 RecyclerView Orientation
     * @param orientation 方向
     * @return Helper
     */
    T setOrientation(@RecyclerView.Orientation int orientation);

    /**
     * 设置 RecyclerView Adapter
     * @param adapter Adapter
     * @return Helper
     */
    T setAdapter(RecyclerView.Adapter<?> adapter);

    /**
     * RecyclerView notifyItemRemoved
     * @param position 索引
     * @return Helper
     */
    T notifyItemRemoved(int position);

    /**
     * RecyclerView notifyItemInserted
     * @param position 索引
     * @return Helper
     */
    T notifyItemInserted(int position);

    /**
     * RecyclerView notifyItemMoved
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return Helper
     */
    T notifyItemMoved(
            int fromPosition,
            int toPosition
    );

    /**
     * RecyclerView notifyDataSetChanged
     * @return Helper
     */
    T notifyDataSetChanged();

    /**
     * 设置 RecyclerView LinearSnapHelper
     * @return Helper
     */
    T attachLinearSnapHelper();

    /**
     * 设置 RecyclerView PagerSnapHelper
     * @return Helper
     */
    T attachPagerSnapHelper();

    /**
     * 添加 RecyclerView ItemDecoration
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    T addItemDecoration(RecyclerView.ItemDecoration decor);

    /**
     * 添加 RecyclerView ItemDecoration
     * @param decor RecyclerView ItemDecoration
     * @param index 添加索引
     * @return Helper
     */
    T addItemDecoration(
            RecyclerView.ItemDecoration decor,
            int index
    );

    /**
     * 移除 RecyclerView ItemDecoration
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    T removeItemDecoration(RecyclerView.ItemDecoration decor);

    /**
     * 移除 RecyclerView ItemDecoration
     * @param index RecyclerView ItemDecoration 索引
     * @return Helper
     */
    T removeItemDecorationAt(int index);

    /**
     * 移除 RecyclerView 全部 ItemDecoration
     * @return Helper
     */
    T removeAllItemDecoration();

    /**
     * 设置 RecyclerView ScrollListener
     * @param listener ScrollListener
     * @return Helper
     */
    T setOnScrollListener(RecyclerView.OnScrollListener listener);

    /**
     * 添加 RecyclerView ScrollListener
     * @param listener ScrollListener
     * @return Helper
     */
    T addOnScrollListener(RecyclerView.OnScrollListener listener);

    /**
     * 移除 RecyclerView ScrollListener
     * @param listener ScrollListener
     * @return Helper
     */
    T removeOnScrollListener(RecyclerView.OnScrollListener listener);

    /**
     * 清空 RecyclerView ScrollListener
     * @return Helper
     */
    T clearOnScrollListeners();

    /**
     * 设置 RecyclerView 嵌套滚动开关
     * @param enabled 嵌套滚动开关
     * @return Helper
     */
    T setNestedScrollingEnabled(boolean enabled);

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return Helper
     */
    T forceGetViewSize(SizeUtils.OnGetSizeListener listener);
}