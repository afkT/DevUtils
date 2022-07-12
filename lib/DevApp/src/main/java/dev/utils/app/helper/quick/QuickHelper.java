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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

import dev.utils.app.HandlerUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.BaseHelper;
import dev.utils.app.helper.flow.FlowHelper;
import dev.utils.app.helper.view.ViewHelper;

/**
 * detail: 简化链式设置 View Quick Helper 类
 * @author Ttt
 * <pre>
 *     整合 {@link ViewHelper} 代码
 *     针对单个 View 设置处理, 无需多次传入 View
 * </pre>
 */
public final class QuickHelper
        extends BaseHelper<QuickHelper>
        implements IHelperByQuick<QuickHelper> {

    // 持有 View
    private final WeakReference<View> mViewRef;

    /**
     * 构造函数
     * @param target 目标 View
     */
    private QuickHelper(final View target) {
        this.mViewRef = new WeakReference<>(target);
    }

    // =

    /**
     * 获取 QuickHelper
     * @param target 目标 View
     * @return {@link QuickHelper}
     */
    public static QuickHelper get(final View target) {
        return new QuickHelper(target);
    }

    /**
     * 获取 View
     * @param <T> 泛型
     * @return {@link View}
     */
    public <T extends View> T getView() {
        return ViewUtils.convertView(targetView());
    }

    /**
     * 获取目标 View
     * @return {@link View}
     */
    public View targetView() {
        return mViewRef.get();
    }

    /**
     * 获取目标 View ( 转 ViewGroup )
     * @return {@link ViewGroup}
     */
    public ViewGroup targetViewGroup() {
        View view = targetView();
        if (view instanceof ViewGroup) {
            return (ViewGroup) view;
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 ImageView )
     * @return {@link ImageView}
     */
    public ImageView targetImageView() {
        View view = targetView();
        if (view instanceof ImageView) {
            return (ImageView) view;
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 TextView )
     * @return {@link TextView}
     */
    public TextView targetTextView() {
        View view = targetView();
        if (view instanceof TextView) {
            return (TextView) view;
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 EditText )
     * @return {@link EditText}
     */
    public EditText targetEditText() {
        View view = targetView();
        if (view instanceof EditText) {
            return (EditText) view;
        }
        return null;
    }

    /**
     * 获取目标 View ( 转 RecyclerView )
     * @return {@link RecyclerView}
     */
    public RecyclerView targetRecyclerView() {
        View view = targetView();
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        return null;
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
    public QuickHelper flow(FlowHelper.Action action) {
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
    public QuickHelper postRunnable(Runnable runnable) {
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
    public QuickHelper postRunnable(
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
    public QuickHelper postRunnable(
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
    public QuickHelper postRunnable(
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
    public QuickHelper removeRunnable(Runnable runnable) {
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
     * @return Helper
     */
    @Override
    public QuickHelper addTouchArea(int range) {
        ViewHelper.get().addTouchArea(range, targetView());
        return this;
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param left   left range
     * @param top    top range
     * @param right  right range
     * @param bottom bottom range
     * @return Helper
     */
    @Override
    public QuickHelper addTouchArea(
            int left,
            int top,
            int right,
            int bottom
    ) {
        ViewHelper.get().addTouchArea(left, top, right, bottom, targetView());
        return this;
    }

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @return Helper
     */
    @Override
    public QuickHelper setOnClick(View.OnClickListener listener) {
        ViewHelper.get().setOnClick(listener, targetView());
        return this;
    }

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @return Helper
     */
    @Override
    public QuickHelper setOnLongClick(View.OnLongClickListener listener) {
        ViewHelper.get().setOnLongClick(listener, targetView());
        return this;
    }

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @return Helper
     */
    @Override
    public QuickHelper setOnTouch(View.OnTouchListener listener) {
        ViewHelper.get().setOnTouch(listener, targetView());
        return this;
    }

    // =============
    // = ViewUtils =
    // =============

    /**
     * 设置 View Id
     * @param id View Id
     * @return Helper
     */
    @Override
    public QuickHelper setId(int id) {
        ViewHelper.get().setId(targetView(), id);
        return this;
    }

    /**
     * 设置是否限制子 View 在其边界内绘制
     * @param clipChildren {@code true} yes, {@code false} no
     * @return Helper
     */
    @Override
    public QuickHelper setClipChildren(boolean clipChildren) {
        ViewHelper.get().setClipChildren(clipChildren, targetViewGroup());
        return this;
    }

    /**
     * 移除全部子 View
     * @return Helper
     */
    @Override
    public QuickHelper removeAllViews() {
        ViewHelper.get().removeAllViews(targetViewGroup());
        return this;
    }

    /**
     * 添加 View
     * @param child 待添加 View
     * @return Helper
     */
    @Override
    public QuickHelper addView(View child) {
        ViewHelper.get().addView(targetViewGroup(), child);
        return this;
    }

    /**
     * 添加 View
     * @param child 待添加 View
     * @param index 添加位置索引
     * @return Helper
     */
    @Override
    public QuickHelper addView(
            View child,
            int index
    ) {
        ViewHelper.get().addView(targetViewGroup(), child, index);
        return this;
    }

    /**
     * 添加 View
     * @param child  待添加 View
     * @param index  添加位置索引
     * @param params LayoutParams
     * @return Helper
     */
    @Override
    public QuickHelper addView(
            View child,
            int index,
            ViewGroup.LayoutParams params
    ) {
        ViewHelper.get().addView(targetViewGroup(), child, index, params);
        return this;
    }

    /**
     * 添加 View
     * @param child  待添加 View
     * @param params LayoutParams
     * @return Helper
     */
    @Override
    public QuickHelper addView(
            View child,
            ViewGroup.LayoutParams params
    ) {
        ViewHelper.get().addView(targetViewGroup(), child, params);
        return this;
    }

    /**
     * 添加 View
     * @param child  待添加 View
     * @param width  View 宽度
     * @param height View 高度
     * @return Helper
     */
    @Override
    public QuickHelper addView(
            View child,
            int width,
            int height
    ) {
        ViewHelper.get().addView(targetViewGroup(), child, width, height);
        return this;
    }

    /**
     * 设置 View LayoutParams
     * @param params LayoutParams
     * @return Helper
     */
    @Override
    public QuickHelper setLayoutParams(ViewGroup.LayoutParams params) {
        ViewHelper.get().setLayoutParams(targetView(), params);
        return this;
    }

    /**
     * 设置 View[] 宽度、高度
     * @param width  View 宽度
     * @param height View 高度
     * @return Helper
     */
    @Override
    public QuickHelper setWidthHeight(
            int width,
            int height
    ) {
        ViewHelper.get().setWidthHeight(width, height, targetView());
        return this;
    }

    /**
     * 设置 View[] 宽度、高度
     * @param width     View 宽度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    @Override
    public QuickHelper setWidthHeight(
            int width,
            int height,
            boolean nullNewLP
    ) {
        ViewHelper.get().setWidthHeight(width, height, nullNewLP, targetView());
        return this;
    }

    /**
     * 设置 View weight 权重
     * @param weight 权重比例
     * @return Helper
     */
    @Override
    public QuickHelper setWeight(float weight) {
        ViewHelper.get().setWeight(weight, targetView());
        return this;
    }

    /**
     * 设置 View 宽度
     * @param width View 宽度
     * @return Helper
     */
    @Override
    public QuickHelper setWidth(int width) {
        ViewHelper.get().setWidth(width, targetView());
        return this;
    }

    /**
     * 设置 View 宽度
     * @param width     View 宽度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    @Override
    public QuickHelper setWidth(
            int width,
            boolean nullNewLP
    ) {
        ViewHelper.get().setWidth(width, nullNewLP, targetView());
        return this;
    }

    /**
     * 设置 View 高度
     * @param height View 高度
     * @return Helper
     */
    @Override
    public QuickHelper setHeight(int height) {
        ViewHelper.get().setHeight(height, targetView());
        return this;
    }

    /**
     * 设置 View 高度
     * @param height    View 高度
     * @param nullNewLP 如果 LayoutParams 为 null 是否创建新的
     * @return Helper
     */
    @Override
    public QuickHelper setHeight(
            int height,
            boolean nullNewLP
    ) {
        ViewHelper.get().setHeight(height, nullNewLP, targetView());
        return this;
    }

    /**
     * 设置 View 最小宽度
     * @param minWidth 最小宽度
     * @return Helper
     */
    @Override
    public QuickHelper setMinimumWidth(int minWidth) {
        ViewHelper.get().setMinimumWidth(minWidth, targetView());
        return this;
    }

    /**
     * 设置 View 最小高度
     * @param minHeight 最小高度
     * @return Helper
     */
    @Override
    public QuickHelper setMinimumHeight(int minHeight) {
        ViewHelper.get().setMinimumHeight(minHeight, targetView());
        return this;
    }

    /**
     * 设置 View 透明度
     * @param alpha 透明度
     * @return Helper
     */
    @Override
    public QuickHelper setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        ViewHelper.get().setAlpha(alpha, targetView());
        return this;
    }

    /**
     * 设置 View TAG
     * @param object TAG
     * @return Helper
     */
    @Override
    public QuickHelper setTag(Object object) {
        ViewHelper.get().setTag(targetView(), object);
        return this;
    }

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param value X 轴坐标
     * @return Helper
     */
    @Override
    public QuickHelper setScrollX(int value) {
        ViewHelper.get().setScrollX(value, targetView());
        return this;
    }

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param value Y 轴坐标
     * @return Helper
     */
    @Override
    public QuickHelper setScrollY(int value) {
        ViewHelper.get().setScrollY(value, targetView());
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
     * @return Helper
     */
    @Override
    public QuickHelper setDescendantFocusability(int focusability) {
        ViewHelper.get().setDescendantFocusability(focusability, targetViewGroup());
        return this;
    }

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @return Helper
     */
    @Override
    public QuickHelper setOverScrollMode(int overScrollMode) {
        ViewHelper.get().setOverScrollMode(overScrollMode, targetView());
        return this;
    }

    /**
     * 设置是否绘制横向滚动条
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    @Override
    public QuickHelper setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        ViewHelper.get().setHorizontalScrollBarEnabled(horizontalScrollBarEnabled, targetView());
        return this;
    }

    /**
     * 设置是否绘制垂直滚动条
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return Helper
     */
    @Override
    public QuickHelper setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        ViewHelper.get().setVerticalScrollBarEnabled(verticalScrollBarEnabled, targetView());
        return this;
    }

    /**
     * 设置 View 滚动效应
     * @param isScrollContainer 是否需要滚动效应
     * @return Helper
     */
    @Override
    public QuickHelper setScrollContainer(boolean isScrollContainer) {
        ViewHelper.get().setScrollContainer(isScrollContainer, targetView());
        return this;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public QuickHelper setNextFocusForwardId(@IdRes int nextFocusForwardId) {
        ViewHelper.get().setNextFocusForwardId(targetView(), nextFocusForwardId);
        return this;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public QuickHelper setNextFocusDownId(@IdRes int nextFocusDownId) {
        ViewHelper.get().setNextFocusDownId(targetView(), nextFocusDownId);
        return this;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public QuickHelper setNextFocusLeftId(@IdRes int nextFocusLeftId) {
        ViewHelper.get().setNextFocusLeftId(targetView(), nextFocusLeftId);
        return this;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public QuickHelper setNextFocusRightId(@IdRes int nextFocusRightId) {
        ViewHelper.get().setNextFocusRightId(targetView(), nextFocusRightId);
        return this;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return Helper
     */
    @Override
    public QuickHelper setNextFocusUpId(@IdRes int nextFocusUpId) {
        ViewHelper.get().setNextFocusUpId(targetView(), nextFocusUpId);
        return this;
    }

    /**
     * 设置 View 旋转度数
     * @param rotation 旋转度数
     * @return Helper
     */
    @Override
    public QuickHelper setRotation(float rotation) {
        ViewHelper.get().setRotation(rotation, targetView());
        return this;
    }

    /**
     * 设置 View 水平旋转度数
     * @param rotationX 水平旋转度数
     * @return Helper
     */
    @Override
    public QuickHelper setRotationX(float rotationX) {
        ViewHelper.get().setRotationX(rotationX, targetView());
        return this;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param rotationY 竖直旋转度数
     * @return Helper
     */
    @Override
    public QuickHelper setRotationY(float rotationY) {
        ViewHelper.get().setRotationY(rotationY, targetView());
        return this;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param scaleX 水平方向缩放比例
     * @return Helper
     */
    @Override
    public QuickHelper setScaleX(float scaleX) {
        ViewHelper.get().setScaleX(scaleX, targetView());
        return this;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param scaleY 竖直方向缩放比例
     * @return Helper
     */
    @Override
    public QuickHelper setScaleY(float scaleY) {
        ViewHelper.get().setScaleY(scaleY, targetView());
        return this;
    }

    /**
     * 设置文本的显示方式
     * @param textAlignment 文本的显示方式
     * @return Helper
     */
    @Override
    public QuickHelper setTextAlignment(int textAlignment) {
        ViewHelper.get().setTextAlignment(textAlignment, targetView());
        return this;
    }

    /**
     * 设置文本的显示方向
     * @param textDirection 文本的显示方向
     * @return Helper
     */
    @Override
    public QuickHelper setTextDirection(int textDirection) {
        ViewHelper.get().setTextDirection(textDirection, targetView());
        return this;
    }

    /**
     * 设置水平方向偏转量
     * @param pivotX 水平方向偏转量
     * @return Helper
     */
    @Override
    public QuickHelper setPivotX(float pivotX) {
        ViewHelper.get().setPivotX(pivotX, targetView());
        return this;
    }

    /**
     * 设置竖直方向偏转量
     * @param pivotY 竖直方向偏转量
     * @return Helper
     */
    @Override
    public QuickHelper setPivotY(float pivotY) {
        ViewHelper.get().setPivotY(pivotY, targetView());
        return this;
    }

    /**
     * 设置水平方向的移动距离
     * @param translationX 水平方向的移动距离
     * @return Helper
     */
    @Override
    public QuickHelper setTranslationX(float translationX) {
        ViewHelper.get().setTranslationX(translationX, targetView());
        return this;
    }

    /**
     * 设置竖直方向的移动距离
     * @param translationY 竖直方向的移动距离
     * @return Helper
     */
    @Override
    public QuickHelper setTranslationY(float translationY) {
        ViewHelper.get().setTranslationY(translationY, targetView());
        return this;
    }

    /**
     * 设置 X 轴位置
     * @param x X 轴位置
     * @return Helper
     */
    @Override
    public QuickHelper setX(float x) {
        ViewHelper.get().setX(x, targetView());
        return this;
    }

    /**
     * 设置 Y 轴位置
     * @param y Y 轴位置
     * @return Helper
     */
    @Override
    public QuickHelper setY(float y) {
        ViewHelper.get().setY(y, targetView());
        return this;
    }

    /**
     * 设置 View 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return Helper
     */
    @Override
    public QuickHelper setLayerType(
            int layerType,
            Paint paint
    ) {
        ViewHelper.get().setLayerType(layerType, paint, targetView());
        return this;
    }

    /**
     * 请求重新对 View 布局
     * @return Helper
     */
    @Override
    public QuickHelper requestLayout() {
        ViewHelper.get().requestLayout(targetView());
        return this;
    }

    /**
     * View 请求获取焦点
     * @return Helper
     */
    @Override
    public QuickHelper requestFocus() {
        ViewHelper.get().requestFocus(targetView());
        return this;
    }

    /**
     * View 清除焦点
     * @return Helper
     */
    @Override
    public QuickHelper clearFocus() {
        ViewHelper.get().clearFocus(targetView());
        return this;
    }

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @return Helper
     */
    @Override
    public QuickHelper setFocusableInTouchMode(boolean focusableInTouchMode) {
        ViewHelper.get().setFocusableInTouchMode(focusableInTouchMode, targetView());
        return this;
    }

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @return Helper
     */
    @Override
    public QuickHelper setFocusable(boolean focusable) {
        ViewHelper.get().setFocusable(focusable, targetView());
        return this;
    }

    /**
     * 切换获取焦点状态
     * @return Helper
     */
    @Override
    public QuickHelper toggleFocusable() {
        ViewHelper.get().toggleFocusable(targetView());
        return this;
    }

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @return Helper
     */
    @Override
    public QuickHelper setSelected(boolean selected) {
        ViewHelper.get().setSelected(selected, targetView());
        return this;
    }

    /**
     * 切换选中状态
     * @return Helper
     */
    @Override
    public QuickHelper toggleSelected() {
        ViewHelper.get().toggleSelected(targetView());
        return this;
    }

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @return Helper
     */
    @Override
    public QuickHelper setEnabled(boolean enabled) {
        ViewHelper.get().setEnabled(enabled, targetView());
        return this;
    }

    /**
     * 切换 View 是否启用状态
     * @return Helper
     */
    @Override
    public QuickHelper toggleEnabled() {
        ViewHelper.get().toggleEnabled(targetView());
        return this;
    }

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @return Helper
     */
    @Override
    public QuickHelper setClickable(boolean clickable) {
        ViewHelper.get().setClickable(clickable, targetView());
        return this;
    }

    /**
     * 切换 View 是否可以点击状态
     * @return Helper
     */
    @Override
    public QuickHelper toggleClickable() {
        ViewHelper.get().toggleClickable(targetView());
        return this;
    }

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @return Helper
     */
    @Override
    public QuickHelper setLongClickable(boolean longClickable) {
        ViewHelper.get().setLongClickable(longClickable, targetView());
        return this;
    }

    /**
     * 切换 View 是否可以长按状态
     * @return Helper
     */
    @Override
    public QuickHelper toggleLongClickable() {
        ViewHelper.get().toggleLongClickable(targetView());
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @return Helper
     */
    @Override
    public QuickHelper setVisibilitys(boolean isVisibility) {
        ViewHelper.get().setVisibilitys(isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper setVisibilitys(int isVisibility) {
        ViewHelper.get().setVisibilitys(isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @return Helper
     */
    @Override
    public QuickHelper setVisibilityINs(boolean isVisibility) {
        ViewHelper.get().setVisibilityINs(isVisibility, targetView());
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param views View[]
     * @return Helper
     */
    @Override
    public QuickHelper toggleVisibilitys(View... views) {
        ViewHelper.get().toggleVisibilitys(targetView(), views);
        return this;
    }

    /**
     * 切换 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views View[]
     * @return Helper
     */
    @Override
    public QuickHelper toggleVisibilitys(
            int state,
            View... views
    ) {
        ViewHelper.get().toggleVisibilitys(state, new View[]{targetView()}, views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views View[]
     * @return Helper
     */
    @Override
    public QuickHelper reverseVisibilitys(
            int state,
            View... views
    ) {
        ViewHelper.get().reverseVisibilitys(state, targetView(), views);
        return this;
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return Helper
     */
    @Override
    public QuickHelper reverseVisibilitys(
            boolean isVisibility,
            View... views
    ) {
        ViewHelper.get().reverseVisibilitys(isVisibility, targetView(), views);
        return this;
    }

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper toggleViews(
            boolean isChange,
            int isVisibility
    ) {
        ViewHelper.get().toggleViews(isChange, isVisibility, targetView());
        return this;
    }

    /**
     * 把自身从父 View 中移除
     * @return Helper
     */
    @Override
    public QuickHelper removeSelfFromParent() {
        ViewHelper.get().removeSelfFromParent(targetView());
        return this;
    }

    /**
     * View 请求更新
     * @param allParent 是否全部父布局 View 都请求
     * @return Helper
     */
    @Override
    public QuickHelper requestLayoutParent(boolean allParent) {
        ViewHelper.get().requestLayoutParent(allParent, targetView());
        return this;
    }

    /**
     * 测量 View
     * @param specifiedWidth 指定宽度
     * @return Helper
     */
    @Override
    public QuickHelper measureView(int specifiedWidth) {
        ViewHelper.get().measureView(specifiedWidth, targetView());
        return this;
    }

    /**
     * 测量 View
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return Helper
     */
    @Override
    public QuickHelper measureView(
            int specifiedWidth,
            int specifiedHeight
    ) {
        ViewHelper.get().measureView(specifiedWidth, specifiedHeight, targetView());
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param gravity Gravity
     * @return Helper
     */
    @Override
    public QuickHelper setLayoutGravity(int gravity) {
        ViewHelper.get().setLayoutGravity(gravity, targetView());
        return this;
    }

    /**
     * 设置 View Layout Gravity
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return Helper
     */
    @Override
    public QuickHelper setLayoutGravity(
            int gravity,
            boolean isReflection
    ) {
        ViewHelper.get().setLayoutGravity(gravity, isReflection, targetView());
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginLeft(int leftMargin) {
        ViewHelper.get().setMarginLeft(leftMargin, targetView());
        return this;
    }

    /**
     * 设置 View Left Margin
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginLeft(
            int leftMargin,
            boolean reset
    ) {
        ViewHelper.get().setMarginLeft(leftMargin, reset, targetView());
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginTop(int topMargin) {
        ViewHelper.get().setMarginTop(topMargin, targetView());
        return this;
    }

    /**
     * 设置 View Top Margin
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginTop(
            int topMargin,
            boolean reset
    ) {
        ViewHelper.get().setMarginTop(topMargin, reset, targetView());
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginRight(int rightMargin) {
        ViewHelper.get().setMarginRight(rightMargin, targetView());
        return this;
    }

    /**
     * 设置 View Right Margin
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginRight(
            int rightMargin,
            boolean reset
    ) {
        ViewHelper.get().setMarginRight(rightMargin, reset, targetView());
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginBottom(int bottomMargin) {
        ViewHelper.get().setMarginBottom(bottomMargin, targetView());
        return this;
    }

    /**
     * 设置 View Bottom Margin
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return Helper
     */
    @Override
    public QuickHelper setMarginBottom(
            int bottomMargin,
            boolean reset
    ) {
        ViewHelper.get().setMarginBottom(bottomMargin, reset, targetView());
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMargin(
            int leftRight,
            int topBottom
    ) {
        ViewHelper.get().setMargin(leftRight, topBottom, targetView());
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param margin Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMargin(int margin) {
        ViewHelper.get().setMargin(margin, targetView());
        return this;
    }

    /**
     * 设置 Margin 边距
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return Helper
     */
    @Override
    public QuickHelper setMargin(
            int left,
            int top,
            int right,
            int bottom
    ) {
        ViewHelper.get().setMargin(left, top, right, bottom, targetView());
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingLeft(int leftPadding) {
        ViewHelper.get().setPaddingLeft(leftPadding, targetView());
        return this;
    }

    /**
     * 设置 View Left Padding
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingLeft(
            int leftPadding,
            boolean reset
    ) {
        ViewHelper.get().setPaddingLeft(leftPadding, reset, targetView());
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingTop(int topPadding) {
        ViewHelper.get().setPaddingTop(topPadding, targetView());
        return this;
    }

    /**
     * 设置 View Top Padding
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingTop(
            int topPadding,
            boolean reset
    ) {
        ViewHelper.get().setPaddingTop(topPadding, reset, targetView());
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingRight(int rightPadding) {
        ViewHelper.get().setPaddingRight(rightPadding, targetView());
        return this;
    }

    /**
     * 设置 View Right Padding
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingRight(
            int rightPadding,
            boolean reset
    ) {
        ViewHelper.get().setPaddingRight(rightPadding, reset, targetView());
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingBottom(int bottomPadding) {
        ViewHelper.get().setPaddingBottom(bottomPadding, targetView());
        return this;
    }

    /**
     * 设置 View Bottom Padding
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPaddingBottom(
            int bottomPadding,
            boolean reset
    ) {
        ViewHelper.get().setPaddingBottom(bottomPadding, reset, targetView());
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPadding(
            int leftRight,
            int topBottom
    ) {
        ViewHelper.get().setPadding(leftRight, topBottom, targetView());
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param padding Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPadding(int padding) {
        ViewHelper.get().setPadding(padding, targetView());
        return this;
    }

    /**
     * 设置 Padding 边距
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return Helper
     */
    @Override
    public QuickHelper setPadding(
            int left,
            int top,
            int right,
            int bottom
    ) {
        ViewHelper.get().setPadding(left, top, right, bottom, targetView());
        return this;
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb 布局位置
     * @return Helper
     */
    @Override
    public QuickHelper addRules(int verb) {
        ViewHelper.get().addRules(verb, targetView());
        return this;
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @return Helper
     */
    @Override
    public QuickHelper addRules(
            int verb,
            int subject
    ) {
        ViewHelper.get().addRules(verb, subject, targetView());
        return this;
    }

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb 布局位置
     * @return Helper
     */
    @Override
    public QuickHelper removeRules(int verb) {
        ViewHelper.get().removeRules(verb, targetView());
        return this;
    }

    /**
     * 设置动画
     * @param animation {@link Animation}
     * @return Helper
     */
    @Override
    public QuickHelper setAnimation(Animation animation) {
        ViewHelper.get().setAnimation(targetView(), animation);
        return this;
    }

    /**
     * 清空动画
     * @return Helper
     */
    @Override
    public QuickHelper clearAnimation() {
        ViewHelper.get().clearAnimation(targetView());
        return this;
    }

    /**
     * 启动动画
     * @return Helper
     */
    @Override
    public QuickHelper startAnimation() {
        ViewHelper.get().startAnimation(targetView());
        return this;
    }

    /**
     * 启动动画
     * @param animation {@link Animation}
     * @return Helper
     */
    @Override
    public QuickHelper startAnimation(Animation animation) {
        ViewHelper.get().startAnimation(targetView(), animation);
        return this;
    }

    /**
     * 取消动画
     * @return Helper
     */
    @Override
    public QuickHelper cancelAnimation() {
        ViewHelper.get().cancelAnimation(targetView());
        return this;
    }

    /**
     * 设置背景图片
     * @param background 背景图片
     * @return Helper
     */
    @Override
    public QuickHelper setBackground(Drawable background) {
        ViewHelper.get().setBackground(background, targetView());
        return this;
    }

    /**
     * 设置背景颜色
     * @param color 背景颜色
     * @return Helper
     */
    @Override
    public QuickHelper setBackgroundColor(@ColorInt int color) {
        ViewHelper.get().setBackgroundColor(color, targetView());
        return this;
    }

    /**
     * 设置背景资源
     * @param resId resource identifier
     * @return Helper
     */
    @Override
    public QuickHelper setBackgroundResource(@DrawableRes int resId) {
        ViewHelper.get().setBackgroundResource(resId, targetView());
        return this;
    }

    /**
     * 设置背景着色颜色
     * @param tint 着色颜色
     * @return Helper
     */
    @Override
    public QuickHelper setBackgroundTintList(ColorStateList tint) {
        ViewHelper.get().setBackgroundTintList(tint, targetView());
        return this;
    }

    /**
     * 设置背景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    @Override
    public QuickHelper setBackgroundTintMode(PorterDuff.Mode tintMode) {
        ViewHelper.get().setBackgroundTintMode(tintMode, targetView());
        return this;
    }

    /**
     * 设置前景图片
     * @param foreground 前景图片
     * @return Helper
     */
    @Override
    public QuickHelper setForeground(Drawable foreground) {
        ViewHelper.get().setForeground(foreground, targetView());
        return this;
    }

    /**
     * 设置前景重心
     * @param gravity 重心
     * @return Helper
     */
    @Override
    public QuickHelper setForegroundGravity(int gravity) {
        ViewHelper.get().setForegroundGravity(gravity, targetView());
        return this;
    }

    /**
     * 设置前景着色颜色
     * @param tint 着色颜色
     * @return Helper
     */
    @Override
    public QuickHelper setForegroundTintList(ColorStateList tint) {
        ViewHelper.get().setForegroundTintList(tint, targetView());
        return this;
    }

    /**
     * 设置前景着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    @Override
    public QuickHelper setForegroundTintMode(PorterDuff.Mode tintMode) {
        ViewHelper.get().setForegroundTintMode(tintMode, targetView());
        return this;
    }

    /**
     * View 着色处理
     * @param color 颜色值
     * @return Helper
     */
    @Override
    public QuickHelper setColorFilter(@ColorInt int color) {
        ViewHelper.get().setColorFilter(color, targetView());
        return this;
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return Helper
     */
    @Override
    public QuickHelper setColorFilter(
            Drawable drawable,
            @ColorInt int color
    ) {
        ViewHelper.get().setColorFilter(drawable, color, targetView());
        return this;
    }

    /**
     * View 着色处理
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    @Override
    public QuickHelper setColorFilter(ColorFilter colorFilter) {
        ViewHelper.get().setColorFilter(colorFilter, targetView());
        return this;
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return Helper
     */
    @Override
    public QuickHelper setColorFilter(
            Drawable drawable,
            ColorFilter colorFilter
    ) {
        ViewHelper.get().setColorFilter(drawable, colorFilter, targetView());
        return this;
    }

    /**
     * 设置 ProgressBar 进度条样式
     * @param drawable {@link Drawable}
     * @return Helper
     */
    @Override
    public QuickHelper setProgressDrawable(Drawable drawable) {
        ViewHelper.get().setProgressDrawable(drawable, targetView());
        return this;
    }

    /**
     * 设置 ProgressBar 进度值
     * @param progress 当前进度
     * @return Helper
     */
    @Override
    public QuickHelper setBarProgress(int progress) {
        ViewHelper.get().setBarProgress(progress, targetView());
        return this;
    }

    /**
     * 设置 ProgressBar 最大值
     * @param max 最大值
     * @return Helper
     */
    @Override
    public QuickHelper setBarMax(int max) {
        ViewHelper.get().setBarMax(max, targetView());
        return this;
    }

    /**
     * 设置 ProgressBar 最大值
     * @param progress 当前进度
     * @param max      最大值
     * @return Helper
     */
    @Override
    public QuickHelper setBarValue(
            int progress,
            int max
    ) {
        ViewHelper.get().setBarValue(progress, max, targetView());
        return this;
    }

    // =================
    // = ListViewUtils =
    // =================

    /**
     * 滑动到指定索引 ( 有滚动过程 )
     * @param position 索引
     * @return Helper
     */
    @Override
    public QuickHelper smoothScrollToPosition(int position) {
        ViewHelper.get().smoothScrollToPosition(position, targetView());
        return this;
    }

    /**
     * 滑动到指定索引 ( 无滚动过程 )
     * @param position 索引
     * @return Helper
     */
    @Override
    public QuickHelper scrollToPosition(int position) {
        ViewHelper.get().scrollToPosition(position, targetView());
        return this;
    }

    /**
     * 滑动到顶部 ( 有滚动过程 )
     * @return Helper
     */
    @Override
    public QuickHelper smoothScrollToTop() {
        ViewHelper.get().smoothScrollToTop(targetView());
        return this;
    }

    /**
     * 滑动到顶部 ( 无滚动过程 )
     * @return Helper
     */
    @Override
    public QuickHelper scrollToTop() {
        ViewHelper.get().scrollToTop(targetView());
        return this;
    }

    /**
     * 滑动到底部 ( 有滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 smoothScrollBy 搭配到底部 )
     *     smoothScrollToBottom(view)
     *     smoothScrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @return Helper
     */
    @Override
    public QuickHelper smoothScrollToBottom() {
        ViewHelper.get().smoothScrollToBottom(targetView());
        return this;
    }

    /**
     * 滑动到底部 ( 无滚动过程 )
     * <pre>
     *     如果未到达底部 ( position 可以再加上 scrollBy 搭配到底部 )
     *     scrollToBottom(view)
     *     scrollBy(view, 0, Integer.MAX_VALUE);
     * </pre>
     * @return Helper
     */
    @Override
    public QuickHelper scrollToBottom() {
        ViewHelper.get().scrollToBottom(targetView());
        return this;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于初始位置移动 )
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    @Override
    public QuickHelper smoothScrollTo(
            int x,
            int y
    ) {
        ViewHelper.get().smoothScrollTo(x, y, targetView());
        return this;
    }

    /**
     * 滚动到指定位置 ( 有滚动过程, 相对于上次移动的最后位置移动 )
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    @Override
    public QuickHelper smoothScrollBy(
            int x,
            int y
    ) {
        ViewHelper.get().smoothScrollBy(x, y, targetView());
        return this;
    }

    /**
     * 滚动方向 ( 有滚动过程 )
     * @param direction 滚动方向 如: View.FOCUS_UP、View.FOCUS_DOWN
     * @return Helper
     */
    @Override
    public QuickHelper fullScroll(int direction) {
        ViewHelper.get().fullScroll(direction, targetView());
        return this;
    }

    /**
     * View 内容滚动位置 ( 相对于初始位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    @Override
    public QuickHelper scrollTo(
            int x,
            int y
    ) {
        ViewHelper.get().scrollTo(x, y, targetView());
        return this;
    }

    /**
     * View 内部滚动位置 ( 相对于上次移动的最后位置移动 )
     * <pre>
     *     无滚动过程
     * </pre>
     * @param x X 轴开始坐标
     * @param y Y 轴开始坐标
     * @return Helper
     */
    @Override
    public QuickHelper scrollBy(
            int x,
            int y
    ) {
        ViewHelper.get().scrollBy(x, y, targetView());
        return this;
    }

    // ==================
    // = ImageViewUtils =
    // ==================

    /**
     * 设置 ImageView 是否保持宽高比
     * @param adjustViewBounds 是否调整此视图的边界以保持可绘制的原始纵横比
     * @return Helper
     */
    @Override
    public QuickHelper setAdjustViewBounds(boolean adjustViewBounds) {
        ViewHelper.get().setAdjustViewBounds(adjustViewBounds, targetImageView());
        return this;
    }

    /**
     * 设置 ImageView 最大高度
     * @param maxHeight 最大高度
     * @return Helper
     */
    @Override
    public QuickHelper setMaxHeight(int maxHeight) {
        ViewHelper.get().setMaxHeight(maxHeight, targetImageView());
        return this;
    }

    /**
     * 设置 ImageView 最大宽度
     * @param maxWidth 最大宽度
     * @return Helper
     */
    @Override
    public QuickHelper setMaxWidth(int maxWidth) {
        ViewHelper.get().setMaxWidth(maxWidth, targetImageView());
        return this;
    }

    /**
     * 设置 ImageView Level
     * @param level level Image
     * @return Helper
     */
    @Override
    public QuickHelper setImageLevel(int level) {
        ViewHelper.get().setImageLevel(level, targetView());
        return this;
    }

    /**
     * 设置 ImageView Bitmap
     * @param bitmap {@link Bitmap}
     * @return Helper
     */
    @Override
    public QuickHelper setImageBitmap(Bitmap bitmap) {
        ViewHelper.get().setImageBitmap(bitmap, targetView());
        return this;
    }

    /**
     * 设置 ImageView Drawable
     * @param drawable {@link Bitmap}
     * @return Helper
     */
    @Override
    public QuickHelper setImageDrawable(Drawable drawable) {
        ViewHelper.get().setImageDrawable(drawable, targetView());
        return this;
    }

    /**
     * 设置 ImageView 资源
     * @param resId resource identifier
     * @return Helper
     */
    @Override
    public QuickHelper setImageResource(@DrawableRes int resId) {
        ViewHelper.get().setImageResource(resId, targetView());
        return this;
    }

    /**
     * 设置 ImageView Matrix
     * @param matrix {@link Matrix}
     * @return Helper
     */
    @Override
    public QuickHelper setImageMatrix(Matrix matrix) {
        ViewHelper.get().setImageMatrix(matrix, targetView());
        return this;
    }

    /**
     * 设置 ImageView 着色颜色
     * @param tint 着色颜色
     * @return Helper
     */
    @Override
    public QuickHelper setImageTintList(ColorStateList tint) {
        ViewHelper.get().setImageTintList(tint, targetView());
        return this;
    }

    /**
     * 设置 ImageView 着色模式
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return Helper
     */
    @Override
    public QuickHelper setImageTintMode(PorterDuff.Mode tintMode) {
        ViewHelper.get().setImageTintMode(tintMode, targetView());
        return this;
    }

    /**
     * 设置 ImageView 缩放类型
     * @param scaleType 缩放类型 {@link ImageView.ScaleType}
     * @return Helper
     */
    @Override
    public QuickHelper setScaleType(ImageView.ScaleType scaleType) {
        ViewHelper.get().setScaleType(scaleType, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @return Helper
     */
    @Override
    public QuickHelper setBackgroundResources(@DrawableRes int resId) {
        ViewHelper.get().setBackgroundResources(resId, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper setBackgroundResources(
            @DrawableRes int resId,
            int isVisibility
    ) {
        ViewHelper.get().setBackgroundResources(resId, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId resource identifier
     * @return Helper
     */
    @Override
    public QuickHelper setImageResources(@DrawableRes int resId) {
        ViewHelper.get().setImageResources(resId, targetView());
        return this;
    }

    /**
     * 设置 View 图片资源
     * @param resId        resource identifier
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper setImageResources(
            @DrawableRes int resId,
            int isVisibility
    ) {
        ViewHelper.get().setImageResources(resId, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap {@link Bitmap}
     * @return Helper
     */
    @Override
    public QuickHelper setImageBitmaps(Bitmap bitmap) {
        ViewHelper.get().setImageBitmaps(bitmap, targetView());
        return this;
    }

    /**
     * 设置 View Bitmap
     * @param bitmap       {@link Bitmap}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper setImageBitmaps(
            Bitmap bitmap,
            int isVisibility
    ) {
        ViewHelper.get().setImageBitmaps(bitmap, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable {@link drawable}
     * @return Helper
     */
    @Override
    public QuickHelper setImageDrawables(Drawable drawable) {
        ViewHelper.get().setImageDrawables(drawable, targetView());
        return this;
    }

    /**
     * 设置 View Drawable
     * @param drawable     {@link drawable}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper setImageDrawables(
            Drawable drawable,
            int isVisibility
    ) {
        ViewHelper.get().setImageDrawables(drawable, isVisibility, targetView());
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType {@link ImageView.ScaleType}
     * @return Helper
     */
    @Override
    public QuickHelper setScaleTypes(ImageView.ScaleType scaleType) {
        ViewHelper.get().setScaleTypes(scaleType, targetView());
        return this;
    }

    /**
     * 设置 View 缩放模式
     * @param scaleType    {@link ImageView.ScaleType}
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @return Helper
     */
    @Override
    public QuickHelper setScaleTypes(
            ImageView.ScaleType scaleType,
            int isVisibility
    ) {
        ViewHelper.get().setScaleTypes(scaleType, isVisibility, targetView());
        return this;
    }

    // ===============================
    // = EditTextUtils、TextViewUtils =
    // ===============================

    /**
     * 设置文本
     * @param text TextView text
     * @return Helper
     */
    @Override
    public QuickHelper setText(CharSequence text) {
        ViewHelper.get().setText(text, targetView());
        return this;
    }

    /**
     * 设置长度限制
     * @param maxLength 长度限制
     * @return Helper
     */
    @Override
    public QuickHelper setMaxLength(int maxLength) {
        ViewHelper.get().setMaxLength(maxLength, targetView());
        return this;
    }

    /**
     * 设置长度限制, 并且设置内容
     * @param content   文本内容
     * @param maxLength 长度限制
     * @return Helper
     */
    @Override
    public QuickHelper setMaxLengthAndText(
            CharSequence content,
            int maxLength
    ) {
        ViewHelper.get().setMaxLengthAndText(content, maxLength, targetView());
        return this;
    }

    /**
     * 设置输入类型
     * @param type 类型
     * @return Helper
     */
    @Override
    public QuickHelper setInputType(int type) {
        ViewHelper.get().setInputType(type, targetView());
        return this;
    }

    /**
     * 设置软键盘右下角按钮类型
     * @param imeOptions 软键盘按钮类型
     * @return Helper
     */
    @Override
    public QuickHelper setImeOptions(int imeOptions) {
        ViewHelper.get().setImeOptions(imeOptions, targetView());
        return this;
    }

    /**
     * 设置文本视图显示转换
     * @param method {@link TransformationMethod}
     * @return Helper
     */
    @Override
    public QuickHelper setTransformationMethod(TransformationMethod method) {
        ViewHelper.get().setTransformationMethod(method, targetView());
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @return Helper
     */
    @Override
    public QuickHelper setTransformationMethod(boolean isDisplayPassword) {
        ViewHelper.get().setTransformationMethod(isDisplayPassword, targetView());
        return this;
    }

    // =================
    // = EditTextUtils =
    // =================

    /**
     * 设置内容
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return Helper
     */
    @Override
    public QuickHelper setText(
            CharSequence content,
            boolean isSelect
    ) {
        ViewHelper.get().setText(content, isSelect, targetEditText());
        return this;
    }

    /**
     * 追加内容 ( 当前光标位置追加 )
     * @param content  文本内容
     * @param isSelect 是否设置光标
     * @return Helper
     */
    @Override
    public QuickHelper insert(
            CharSequence content,
            boolean isSelect
    ) {
        ViewHelper.get().insert(content, isSelect, targetEditText());
        return this;
    }

    /**
     * 追加内容
     * @param content  文本内容
     * @param start    开始添加的位置
     * @param isSelect 是否设置光标
     * @return Helper
     */
    @Override
    public QuickHelper insert(
            CharSequence content,
            int start,
            boolean isSelect
    ) {
        ViewHelper.get().insert(content, start, isSelect, targetEditText());
        return this;
    }

    /**
     * 设置是否显示光标
     * @param visible 是否显示光标
     * @return Helper
     */
    @Override
    public QuickHelper setCursorVisible(boolean visible) {
        ViewHelper.get().setCursorVisible(visible, targetEditText());
        return this;
    }

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @return Helper
     */
    @Override
    public QuickHelper setTextCursorDrawable(@DrawableRes int textCursorDrawable) {
        ViewHelper.get().setTextCursorDrawable(textCursorDrawable, targetEditText());
        return this;
    }

    /**
     * 设置光标
     * @param textCursorDrawable 光标
     * @return Helper
     */
    @Override
    public QuickHelper setTextCursorDrawable(Drawable textCursorDrawable) {
        ViewHelper.get().setTextCursorDrawable(textCursorDrawable, targetEditText());
        return this;
    }

    /**
     * 设置光标在第一位
     * @return Helper
     */
    @Override
    public QuickHelper setSelectionToTop() {
        ViewHelper.get().setSelectionToTop(targetEditText());
        return this;
    }

    /**
     * 设置光标在最后一位
     * @return Helper
     */
    @Override
    public QuickHelper setSelectionToBottom() {
        ViewHelper.get().setSelectionToBottom(targetEditText());
        return this;
    }

    /**
     * 设置光标位置
     * @param index 光标位置
     * @return Helper
     */
    @Override
    public QuickHelper setSelection(int index) {
        ViewHelper.get().setSelection(index, targetEditText());
        return this;
    }

    /**
     * 设置密码文本视图显示转换
     * @param isDisplayPassword 是否显示密码
     * @param isSelectBottom    是否设置光标到最后
     * @return Helper
     */
    @Override
    public QuickHelper setTransformationMethod(
            boolean isDisplayPassword,
            boolean isSelectBottom
    ) {
        ViewHelper.get().setTransformationMethod(isDisplayPassword, isSelectBottom, targetEditText());
        return this;
    }

    /**
     * 添加输入监听事件
     * @param watcher 输入监听
     * @return Helper
     */
    @Override
    public QuickHelper addTextChangedListener(TextWatcher watcher) {
        ViewHelper.get().addTextChangedListener(watcher, targetEditText());
        return this;
    }

    /**
     * 移除输入监听事件
     * @param watcher 输入监听
     * @return Helper
     */
    @Override
    public QuickHelper removeTextChangedListener(TextWatcher watcher) {
        ViewHelper.get().removeTextChangedListener(watcher, targetEditText());
        return this;
    }

    /**
     * 设置 KeyListener
     * @param listener {@link KeyListener}
     * @return Helper
     */
    @Override
    public QuickHelper setKeyListener(KeyListener listener) {
        ViewHelper.get().setKeyListener(listener, targetEditText());
        return this;
    }

    /**
     * 设置 KeyListener
     * @param accepted 允许输入的内容, 如: 0123456789
     * @return Helper
     */
    @Override
    public QuickHelper setKeyListener(String accepted) {
        ViewHelper.get().setKeyListener(accepted, targetEditText());
        return this;
    }

    /**
     * 设置 KeyListener
     * @param accepted 允许输入的内容
     * @return Helper
     */
    @Override
    public QuickHelper setKeyListener(char[] accepted) {
        ViewHelper.get().setKeyListener(accepted, targetEditText());
        return this;
    }

    // =================
    // = TextViewUtils =
    // =================

    /**
     * 设置 Hint 文本
     * @param text Hint text
     * @return Helper
     */
    @Override
    public QuickHelper setHint(CharSequence text) {
        ViewHelper.get().setHint(text, targetView());
        return this;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param color R.color.id
     * @return Helper
     */
    @Override
    public QuickHelper setHintTextColors(@ColorInt int color) {
        ViewHelper.get().setHintTextColors(color, targetView());
        return this;
    }

    /**
     * 设置多个 TextView Hint 字体颜色
     * @param colors {@link ColorStateList}
     * @return Helper
     */
    @Override
    public QuickHelper setHintTextColors(ColorStateList colors) {
        ViewHelper.get().setHintTextColors(colors, targetView());
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param color R.color.id
     * @return Helper
     */
    @Override
    public QuickHelper setTextColors(@ColorInt int color) {
        ViewHelper.get().setTextColors(color, targetView());
        return this;
    }

    /**
     * 设置多个 TextView 字体颜色
     * @param colors {@link ColorStateList}
     * @return Helper
     */
    @Override
    public QuickHelper setTextColors(ColorStateList colors) {
        ViewHelper.get().setTextColors(colors, targetView());
        return this;
    }

    /**
     * 设置多个 TextView Html 内容
     * @param content Html content
     * @return Helper
     */
    @Override
    public QuickHelper setHtmlTexts(String content) {
        ViewHelper.get().setHtmlTexts(content, targetView());
        return this;
    }

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @return Helper
     */
    @Override
    public QuickHelper setTypeface(Typeface typeface) {
        ViewHelper.get().setTypeface(typeface, targetView());
        return this;
    }

    /**
     * 设置字体
     * @param typeface {@link Typeface} 字体样式
     * @param style    样式
     * @return Helper
     */
    @Override
    public QuickHelper setTypeface(
            Typeface typeface,
            int style
    ) {
        ViewHelper.get().setTypeface(typeface, style, targetView());
        return this;
    }

    /**
     * 设置字体大小 ( px 像素 )
     * @param size 字体大小
     * @return Helper
     */
    @Override
    public QuickHelper setTextSizeByPx(float size) {
        ViewHelper.get().setTextSizeByPx(size, targetView());
        return this;
    }

    /**
     * 设置字体大小 ( sp 缩放像素 )
     * @param size 字体大小
     * @return Helper
     */
    @Override
    public QuickHelper setTextSizeBySp(float size) {
        ViewHelper.get().setTextSizeBySp(size, targetView());
        return this;
    }

    /**
     * 设置字体大小 ( dp 与设备无关的像素 )
     * @param size 字体大小
     * @return Helper
     */
    @Override
    public QuickHelper setTextSizeByDp(float size) {
        ViewHelper.get().setTextSizeByDp(size, targetView());
        return this;
    }

    /**
     * 设置字体大小 ( inches 英寸 )
     * @param size 字体大小
     * @return Helper
     */
    @Override
    public QuickHelper setTextSizeByIn(float size) {
        ViewHelper.get().setTextSizeByIn(size, targetView());
        return this;
    }

    /**
     * 设置字体大小
     * @param unit 字体参数类型
     * @param size 字体大小
     * @return Helper
     */
    @Override
    public QuickHelper setTextSize(
            int unit,
            float size
    ) {
        ViewHelper.get().setTextSize(unit, size, targetView());
        return this;
    }

    /**
     * 清空 flags
     * @return Helper
     */
    @Override
    public QuickHelper clearFlags() {
        ViewHelper.get().clearFlags(targetView());
        return this;
    }

    /**
     * 设置 TextView flags
     * @param flags flags
     * @return Helper
     */
    @Override
    public QuickHelper setPaintFlags(int flags) {
        ViewHelper.get().setPaintFlags(flags, targetView());
        return this;
    }

    /**
     * 设置 TextView 抗锯齿 flags
     * @return Helper
     */
    @Override
    public QuickHelper setAntiAliasFlag() {
        ViewHelper.get().setAntiAliasFlag(targetView());
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @return Helper
     */
    @Override
    public QuickHelper setBold() {
        ViewHelper.get().setBold(targetView());
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param isBold {@code true} yes, {@code false} no
     * @return Helper
     */
    @Override
    public QuickHelper setBold(boolean isBold) {
        ViewHelper.get().setBold(isBold, targetView());
        return this;
    }

    /**
     * 设置 TextView 是否加粗
     * @param typeface {@link Typeface} 字体样式
     * @param isBold   {@code true} yes, {@code false} no
     * @return Helper
     */
    @Override
    public QuickHelper setBold(
            Typeface typeface,
            boolean isBold
    ) {
        ViewHelper.get().setBold(typeface, isBold, targetView());
        return this;
    }

    /**
     * 设置下划线
     * @return Helper
     */
    @Override
    public QuickHelper setUnderlineText() {
        ViewHelper.get().setUnderlineText(targetView());
        return this;
    }

    /**
     * 设置下划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @return Helper
     */
    @Override
    public QuickHelper setUnderlineText(boolean isAntiAlias) {
        ViewHelper.get().setUnderlineText(isAntiAlias, targetView());
        return this;
    }

    /**
     * 设置中划线
     * @return Helper
     */
    @Override
    public QuickHelper setStrikeThruText() {
        ViewHelper.get().setStrikeThruText(targetView());
        return this;
    }

    /**
     * 设置中划线并加清晰
     * @param isAntiAlias 是否消除锯齿
     * @return Helper
     */
    @Override
    public QuickHelper setStrikeThruText(boolean isAntiAlias) {
        ViewHelper.get().setStrikeThruText(isAntiAlias, targetView());
        return this;
    }

    /**
     * 设置文字水平间距
     * @param letterSpacing 文字水平间距
     * @return Helper
     */
    @Override
    public QuickHelper setLetterSpacing(float letterSpacing) {
        ViewHelper.get().setLetterSpacing(letterSpacing, targetView());
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 )
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @return Helper
     */
    @Override
    public QuickHelper setLineSpacing(float lineSpacing) {
        ViewHelper.get().setLineSpacing(lineSpacing, targetView());
        return this;
    }

    /**
     * 设置文字行间距 ( 行高 ) 、行间距倍数
     * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
     * @param multiplier  行间距倍数, android:lineSpacingMultiplier
     * @return Helper
     */
    @Override
    public QuickHelper setLineSpacingAndMultiplier(
            float lineSpacing,
            float multiplier
    ) {
        ViewHelper.get().setLineSpacingAndMultiplier(lineSpacing, multiplier, targetView());
        return this;
    }

    /**
     * 设置字体水平方向的缩放
     * @param size 缩放比例
     * @return Helper
     */
    @Override
    public QuickHelper setTextScaleX(float size) {
        ViewHelper.get().setTextScaleX(size, targetView());
        return this;
    }

    /**
     * 设置是否保留字体留白间隙区域
     * @param includePadding 是否保留字体留白间隙区域
     * @return Helper
     */
    @Override
    public QuickHelper setIncludeFontPadding(boolean includePadding) {
        ViewHelper.get().setIncludeFontPadding(includePadding, targetView());
        return this;
    }

    /**
     * 设置行数
     * @param lines 行数
     * @return Helper
     */
    @Override
    public QuickHelper setLines(int lines) {
        ViewHelper.get().setLines(lines, targetView());
        return this;
    }

    /**
     * 设置最大行数
     * @param maxLines 最大行数
     * @return Helper
     */
    @Override
    public QuickHelper setMaxLines(int maxLines) {
        ViewHelper.get().setMaxLines(maxLines, targetView());
        return this;
    }

    /**
     * 设置最小行数
     * @param minLines 最小行数
     * @return Helper
     */
    @Override
    public QuickHelper setMinLines(int minLines) {
        ViewHelper.get().setMinLines(minLines, targetView());
        return this;
    }

    /**
     * 设置最大字符宽度限制
     * @param maxEms 最大字符
     * @return Helper
     */
    @Override
    public QuickHelper setMaxEms(int maxEms) {
        ViewHelper.get().setMaxEms(maxEms, targetView());
        return this;
    }

    /**
     * 设置最小字符宽度限制
     * @param minEms 最小字符
     * @return Helper
     */
    @Override
    public QuickHelper setMinEms(int minEms) {
        ViewHelper.get().setMinEms(minEms, targetView());
        return this;
    }

    /**
     * 设置指定字符宽度
     * @param ems 字符
     * @return Helper
     */
    @Override
    public QuickHelper setEms(int ems) {
        ViewHelper.get().setEms(ems, targetView());
        return this;
    }

    /**
     * 设置 Ellipsize 效果
     * @param where {@link TextUtils.TruncateAt}
     * @return Helper
     */
    @Override
    public QuickHelper setEllipsize(TextUtils.TruncateAt where) {
        ViewHelper.get().setEllipsize(where, targetView());
        return this;
    }

    /**
     * 设置自动识别文本链接
     * @param mask {@link android.text.util.Linkify}
     * @return Helper
     */
    @Override
    public QuickHelper setAutoLinkMask(int mask) {
        ViewHelper.get().setAutoLinkMask(mask, targetView());
        return this;
    }

    /**
     * 设置文本全为大写
     * @param allCaps 是否全部大写
     * @return Helper
     */
    @Override
    public QuickHelper setAllCaps(boolean allCaps) {
        ViewHelper.get().setAllCaps(allCaps, targetView());
        return this;
    }

    /**
     * 设置 Gravity
     * @param gravity {@link android.view.Gravity}
     * @return Helper
     */
    @Override
    public QuickHelper setGravity(int gravity) {
        ViewHelper.get().setGravity(gravity, targetView());
        return this;
    }

    /**
     * 设置 CompoundDrawables Padding
     * @param padding CompoundDrawables Padding
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablePadding(int padding) {
        ViewHelper.get().setCompoundDrawablePadding(padding, targetTextView());
        return this;
    }

    /**
     * 设置 Left CompoundDrawables
     * @param left left Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesByLeft(Drawable left) {
        ViewHelper.get().setCompoundDrawablesByLeft(left, targetTextView());
        return this;
    }

    /**
     * 设置 Top CompoundDrawables
     * @param top top Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesByTop(Drawable top) {
        ViewHelper.get().setCompoundDrawablesByTop(top, targetTextView());
        return this;
    }

    /**
     * 设置 Right CompoundDrawables
     * @param right right Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesByRight(Drawable right) {
        ViewHelper.get().setCompoundDrawablesByRight(right, targetTextView());
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables
     * @param bottom bottom Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesByBottom(Drawable bottom) {
        ViewHelper.get().setCompoundDrawablesByBottom(bottom, targetTextView());
        return this;
    }

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
    @Override
    public QuickHelper setCompoundDrawables(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    ) {
        ViewHelper.get().setCompoundDrawables(left, top, right, bottom, targetTextView());
        return this;
    }

    /**
     * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left left Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByLeft(Drawable left) {
        ViewHelper.get().setCompoundDrawablesWithIntrinsicBoundsByLeft(left, targetTextView());
        return this;
    }

    /**
     * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param top top Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByTop(Drawable top) {
        ViewHelper.get().setCompoundDrawablesWithIntrinsicBoundsByTop(top, targetTextView());
        return this;
    }

    /**
     * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param right right Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByRight(Drawable right) {
        ViewHelper.get().setCompoundDrawablesWithIntrinsicBoundsByRight(right, targetTextView());
        return this;
    }

    /**
     * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param bottom bottom Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesWithIntrinsicBoundsByBottom(Drawable bottom) {
        ViewHelper.get().setCompoundDrawablesWithIntrinsicBoundsByBottom(bottom, targetTextView());
        return this;
    }

    /**
     * 设置 CompoundDrawables ( 按照原有比例大小显示图片 )
     * @param left   left Drawable
     * @param top    top Drawable
     * @param right  right Drawable
     * @param bottom bottom Drawable
     * @return Helper
     */
    @Override
    public QuickHelper setCompoundDrawablesWithIntrinsicBounds(
            Drawable left,
            Drawable top,
            Drawable right,
            Drawable bottom
    ) {
        ViewHelper.get().setCompoundDrawablesWithIntrinsicBounds(
                left, top, right, bottom, targetTextView()
        );
        return this;
    }

    /**
     * 通过设置默认的自动调整大小配置, 决定是否自动缩放文本
     * @param autoSizeTextType 自动调整大小类型
     * @return Helper
     */
    @Override
    public QuickHelper setAutoSizeTextTypeWithDefaults(
            @TextViewCompat.AutoSizeTextType int autoSizeTextType
    ) {
        ViewHelper.get().setAutoSizeTextTypeWithDefaults(
                autoSizeTextType, targetView()
        );
        return this;
    }

    /**
     * 设置 TextView 自动调整字体大小配置
     * @param autoSizeMinTextSize     自动调整最小字体大小
     * @param autoSizeMaxTextSize     自动调整最大字体大小
     * @param autoSizeStepGranularity 自动调整大小变动粒度 ( 跨度区间值 )
     * @param unit                    字体参数类型
     * @return Helper
     */
    @Override
    public QuickHelper setAutoSizeTextTypeUniformWithConfiguration(
            int autoSizeMinTextSize,
            int autoSizeMaxTextSize,
            int autoSizeStepGranularity,
            int unit
    ) {
        ViewHelper.get().setAutoSizeTextTypeUniformWithConfiguration(
                autoSizeMinTextSize, autoSizeMaxTextSize,
                autoSizeStepGranularity, unit, targetView()
        );
        return this;
    }

    /**
     * 设置 TextView 自动调整如果预设字体大小范围有效则修改类型为 AUTO_SIZE_TEXT_TYPE_UNIFORM
     * @param presetSizes 预设字体大小范围像素为单位
     * @param unit        字体参数类型
     * @return Helper
     */
    @Override
    public QuickHelper setAutoSizeTextTypeUniformWithPresetSizes(
            int[] presetSizes,
            int unit
    ) {
        ViewHelper.get().setAutoSizeTextTypeUniformWithPresetSizes(
                presetSizes, unit, targetView()
        );
        return this;
    }

    // =====================
    // = RecyclerViewUtils =
    // =====================

    /**
     * 设置 RecyclerView LayoutManager
     * @param layoutManager LayoutManager
     * @return Helper
     */
    @Override
    public QuickHelper setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        ViewHelper.get().setLayoutManager(targetView(), layoutManager);
        return this;
    }

    /**
     * 设置 GridLayoutManager SpanCount
     * @param spanCount Span Count
     * @return Helper
     */
    @Override
    public QuickHelper setSpanCount(int spanCount) {
        ViewHelper.get().setSpanCount(targetView(), spanCount);
        return this;
    }

    /**
     * 设置 RecyclerView Orientation
     * @param orientation 方向
     * @return Helper
     */
    @Override
    public QuickHelper setOrientation(@RecyclerView.Orientation int orientation) {
        ViewHelper.get().setOrientation(targetView(), orientation);
        return this;
    }

    /**
     * 设置 RecyclerView Adapter
     * @param adapter Adapter
     * @return Helper
     */
    @Override
    public QuickHelper setAdapter(RecyclerView.Adapter<?> adapter) {
        ViewHelper.get().setAdapter(targetView(), adapter);
        return this;
    }

    /**
     * RecyclerView notifyItemRemoved
     * @param position 索引
     * @return Helper
     */
    @Override
    public QuickHelper notifyItemRemoved(int position) {
        ViewHelper.get().notifyItemRemoved(targetView(), position);
        return this;
    }

    /**
     * RecyclerView notifyItemInserted
     * @param position 索引
     * @return Helper
     */
    @Override
    public QuickHelper notifyItemInserted(int position) {
        ViewHelper.get().notifyItemInserted(targetView(), position);
        return this;
    }

    /**
     * RecyclerView notifyItemMoved
     * @param fromPosition 当前索引
     * @param toPosition   更新后索引
     * @return Helper
     */
    @Override
    public QuickHelper notifyItemMoved(
            int fromPosition,
            int toPosition
    ) {
        ViewHelper.get().notifyItemMoved(targetView(), fromPosition, toPosition);
        return this;
    }

    /**
     * RecyclerView notifyDataSetChanged
     * @return Helper
     */
    @Override
    public QuickHelper notifyDataSetChanged() {
        ViewHelper.get().notifyDataSetChanged(targetView());
        return this;
    }

    /**
     * 设置 RecyclerView LinearSnapHelper
     * @return Helper
     */
    @Override
    public QuickHelper attachLinearSnapHelper() {
        ViewHelper.get().attachLinearSnapHelper(targetView());
        return this;
    }

    /**
     * 设置 RecyclerView PagerSnapHelper
     * @return Helper
     */
    @Override
    public QuickHelper attachPagerSnapHelper() {
        ViewHelper.get().attachPagerSnapHelper(targetView());
        return this;
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    @Override
    public QuickHelper addItemDecoration(RecyclerView.ItemDecoration decor) {
        ViewHelper.get().addItemDecoration(targetView(), decor);
        return this;
    }

    /**
     * 添加 RecyclerView ItemDecoration
     * @param decor RecyclerView ItemDecoration
     * @param index 添加索引
     * @return Helper
     */
    @Override
    public QuickHelper addItemDecoration(
            RecyclerView.ItemDecoration decor,
            int index
    ) {
        ViewHelper.get().addItemDecoration(targetView(), decor, index);
        return this;
    }

    /**
     * 移除 RecyclerView ItemDecoration
     * @param decor RecyclerView ItemDecoration
     * @return Helper
     */
    @Override
    public QuickHelper removeItemDecoration(RecyclerView.ItemDecoration decor) {
        ViewHelper.get().removeItemDecoration(targetView(), decor);
        return this;
    }

    /**
     * 移除 RecyclerView ItemDecoration
     * @param index RecyclerView ItemDecoration 索引
     * @return Helper
     */
    @Override
    public QuickHelper removeItemDecorationAt(int index) {
        ViewHelper.get().removeItemDecorationAt(targetView(), index);
        return this;
    }

    /**
     * 移除 RecyclerView 全部 ItemDecoration
     * @return Helper
     */
    @Override
    public QuickHelper removeAllItemDecoration() {
        ViewHelper.get().removeAllItemDecoration(targetView());
        return this;
    }

    /**
     * 设置 RecyclerView ScrollListener
     * @param listener ScrollListener
     * @return Helper
     */
    @Override
    public QuickHelper setOnScrollListener(RecyclerView.OnScrollListener listener) {
        ViewHelper.get().setOnScrollListener(targetView(), listener);
        return this;
    }

    /**
     * 添加 RecyclerView ScrollListener
     * @param listener ScrollListener
     * @return Helper
     */
    @Override
    public QuickHelper addOnScrollListener(RecyclerView.OnScrollListener listener) {
        ViewHelper.get().addOnScrollListener(targetView(), listener);
        return this;
    }

    /**
     * 移除 RecyclerView ScrollListener
     * @param listener ScrollListener
     * @return Helper
     */
    @Override
    public QuickHelper removeOnScrollListener(RecyclerView.OnScrollListener listener) {
        ViewHelper.get().removeOnScrollListener(targetView(), listener);
        return this;
    }

    /**
     * 清空 RecyclerView ScrollListener
     * @return Helper
     */
    @Override
    public QuickHelper clearOnScrollListeners() {
        ViewHelper.get().clearOnScrollListeners(targetView());
        return this;
    }

    /**
     * 设置 RecyclerView 嵌套滚动开关
     * @param enabled 嵌套滚动开关
     * @return Helper
     */
    @Override
    public QuickHelper setNestedScrollingEnabled(boolean enabled) {
        ViewHelper.get().setNestedScrollingEnabled(enabled, targetView());
        return this;
    }

    // =============
    // = SizeUtils =
    // =============

    /**
     * 在 onCreate 中获取视图的尺寸 ( 需回调 onGetSizeListener 接口, 在 onGetSize 中获取 View 宽高 )
     * @param listener {@link SizeUtils.OnGetSizeListener}
     * @return Helper
     */
    @Override
    public QuickHelper forceGetViewSize(SizeUtils.OnGetSizeListener listener) {
        ViewHelper.get().forceGetViewSize(targetView(), listener);
        return this;
    }
}