package dev.utils.app.helper.view;

import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;

import dev.utils.LogPrintUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.WidgetUtils;
import dev.utils.app.anim.AnimationUtils;
import dev.utils.app.helper.IHelper;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.FieldUtils;

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
     * @return {@link View}
     */
    public static View setMinimumHeight(
            final View view,
            final int minHeight
    ) {
        if (view != null) {
            view.setMinimumHeight(minHeight);
        }
        return view;
    }

    /**
     * 设置 View 最小宽度
     * @param view     {@link View}
     * @param minWidth 最小宽度
     * @return {@link View}
     */
    public static View setMinimumWidth(
            final View view,
            final int minWidth
    ) {
        if (view != null) {
            view.setMinimumWidth(minWidth);
        }
        return view;
    }

    /**
     * 设置 View 透明度
     * @param view  View
     * @param alpha 透明度
     * @return {@link View}
     */
    public static View setAlpha(
            final View view,
            @FloatRange(from = 0.0, to = 1.0) final float alpha
    ) {
        if (view != null) {
            view.setAlpha(alpha);
        }
        return view;
    }

    /**
     * 设置 View Tag
     * @param view   View
     * @param object Tag
     * @return {@link View}
     */
    public static View setTag(
            final View view,
            final Object object
    ) {
        if (view != null) {
            view.setTag(object);
        }
        return view;
    }

    /**
     * 设置 View 滑动的 X 轴坐标
     * @param view  {@link View}
     * @param value X 轴坐标
     * @return {@link View}
     */
    public static View setScrollX(
            final View view,
            final int value
    ) {
        if (view != null) view.setScrollX(value);
        return view;
    }

    /**
     * 设置 View 滑动的 Y 轴坐标
     * @param view  {@link View}
     * @param value Y 轴坐标
     * @return {@link View}
     */
    public static View setScrollY(
            final View view,
            final int value
    ) {
        if (view != null) view.setScrollY(value);
        return view;
    }

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
     * @param <T>          泛型
     * @return {@link ViewGroup}
     */
    public static <T extends ViewGroup> T setDescendantFocusability(
            final T viewGroup,
            final int focusability
    ) {
        try {
            if (viewGroup != null) viewGroup.setDescendantFocusability(focusability);
        } catch (Exception e) {
        }
        return viewGroup;
    }

    /**
     * 设置 View 滚动模式
     * <pre>
     *     设置滑动到边缘时无效果模式 {@link View#OVER_SCROLL_NEVER}
     *     android:overScrollMode="never"
     * </pre>
     * @param view           {@link View}
     * @param overScrollMode {@link View#OVER_SCROLL_ALWAYS}、{@link View#OVER_SCROLL_IF_CONTENT_SCROLLS}、{@link View#OVER_SCROLL_NEVER}
     * @param <T>            泛型
     * @return {@link View}
     */
    public static <T extends View> T setOverScrollMode(
            final T view,
            final int overScrollMode
    ) {
        try {
            if (view != null) view.setOverScrollMode(overScrollMode);
        } catch (Exception e) {
        }
        return view;
    }

    /**
     * 设置是否绘制横向滚动条
     * @param view                       {@link View}
     * @param horizontalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link View}
     */
    public static View setHorizontalScrollBarEnabled(
            final View view,
            final boolean horizontalScrollBarEnabled
    ) {
        if (view != null) view.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
        return view;
    }

    /**
     * 设置是否绘制垂直滚动条
     * @param view                     {@link View}
     * @param verticalScrollBarEnabled {@code true} yes, {@code false} no
     * @return {@link View}
     */
    public static View setVerticalScrollBarEnabled(
            final View view,
            final boolean verticalScrollBarEnabled
    ) {
        if (view != null) view.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
        return view;
    }

    /**
     * 设置 View 滚动效应
     * @param view              {@link View}
     * @param isScrollContainer 是否需要滚动效应
     * @return {@link View}
     */
    public static View setScrollContainer(
            final View view,
            final boolean isScrollContainer
    ) {
        if (view != null) {
            view.setScrollContainer(isScrollContainer);
        }
        return view;
    }

    /**
     * 设置下一个获取焦点的 View id
     * @param view               {@link View}
     * @param nextFocusForwardId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusForwardId(
            final View view,
            @IdRes final int nextFocusForwardId
    ) {
        if (view != null) {
            view.setNextFocusForwardId(nextFocusForwardId);
        }
        return view;
    }

    /**
     * 设置向下移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusDownId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusDownId(
            final View view,
            @IdRes final int nextFocusDownId
    ) {
        if (view != null) {
            view.setNextFocusDownId(nextFocusDownId);
        }
        return view;
    }

    /**
     * 设置向左移动焦点时, 下一个获取焦点的 View id
     * @param view            {@link View}
     * @param nextFocusLeftId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusLeftId(
            final View view,
            @IdRes final int nextFocusLeftId
    ) {
        if (view != null) {
            view.setNextFocusLeftId(nextFocusLeftId);
        }
        return view;
    }

    /**
     * 设置向右移动焦点时, 下一个获取焦点的 View id
     * @param view             {@link View}
     * @param nextFocusRightId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusRightId(
            final View view,
            @IdRes final int nextFocusRightId
    ) {
        if (view != null) {
            view.setNextFocusRightId(nextFocusRightId);
        }
        return view;
    }

    /**
     * 设置向上移动焦点时, 下一个获取焦点的 View id
     * @param view          {@link View}
     * @param nextFocusUpId 下一个获取焦点的 View id
     * @return {@link View}
     */
    public static View setNextFocusUpId(
            final View view,
            @IdRes final int nextFocusUpId
    ) {
        if (view != null) {
            view.setNextFocusUpId(nextFocusUpId);
        }
        return view;
    }

    /**
     * 设置 View 旋转度数
     * @param view     {@link View}
     * @param rotation 旋转度数
     * @return {@link View}
     */
    public static View setRotation(
            final View view,
            final float rotation
    ) {
        if (view != null) {
            view.setRotation(rotation);
        }
        return view;
    }

    /**
     * 设置 View 水平旋转度数
     * @param view      {@link View}
     * @param rotationX 水平旋转度数
     * @return {@link View}
     */
    public static View setRotationX(
            final View view,
            final float rotationX
    ) {
        if (view != null) {
            view.setRotationX(rotationX);
        }
        return view;
    }

    /**
     * 设置 View 竖直旋转度数
     * @param view      {@link View}
     * @param rotationY 竖直旋转度数
     * @return {@link View}
     */
    public static View setRotationY(
            final View view,
            final float rotationY
    ) {
        if (view != null) {
            view.setRotationY(rotationY);
        }
        return view;
    }

    /**
     * 设置 View 水平方向缩放比例
     * @param view   View
     * @param scaleX 水平方向缩放比例
     * @return {@link View}
     */
    public static View setScaleX(
            final View view,
            final float scaleX
    ) {
        if (view != null) {
            view.setScaleX(scaleX);
        }
        return view;
    }

    /**
     * 设置 View 竖直方向缩放比例
     * @param view   View
     * @param scaleY 竖直方向缩放比例
     * @return {@link View}
     */
    public static View setScaleY(
            final View view,
            final float scaleY
    ) {
        if (view != null) {
            view.setScaleY(scaleY);
        }
        return view;
    }

    /**
     * 设置文本的显示方式
     * @param view          {@link View}
     * @param textAlignment 文本的显示方式
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View setTextAlignment(
            final View view,
            final int textAlignment
    ) {
        if (view != null) {
            view.setTextAlignment(textAlignment);
        }
        return view;
    }

    /**
     * 设置文本的显示方向
     * @param view          {@link View}
     * @param textDirection 文本的显示方向
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static View setTextDirection(
            final View view,
            final int textDirection
    ) {
        if (view != null) {
            view.setTextDirection(textDirection);
        }
        return view;
    }

    /**
     * 设置水平方向偏转量
     * @param view   View
     * @param pivotX 水平方向偏转量
     * @return {@link View}
     */
    public static View setPivotX(
            final View view,
            final float pivotX
    ) {
        if (view != null) {
            view.setPivotX(pivotX);
        }
        return view;
    }

    /**
     * 设置竖直方向偏转量
     * @param view   View
     * @param pivotY 竖直方向偏转量
     * @return {@link View}
     */
    public static View setPivotY(
            final View view,
            final float pivotY
    ) {
        if (view != null) {
            view.setPivotY(pivotY);
        }
        return view;
    }

    /**
     * 设置水平方向的移动距离
     * @param view         {@link View}
     * @param translationX 水平方向的移动距离
     * @return {@link View}
     */
    public static View setTranslationX(
            final View view,
            final float translationX
    ) {
        if (view != null) {
            view.setTranslationX(translationX);
        }
        return view;
    }

    /**
     * 设置竖直方向的移动距离
     * @param view         {@link View}
     * @param translationY 竖直方向的移动距离
     * @return {@link View}
     */
    public static View setTranslationY(
            final View view,
            final float translationY
    ) {
        if (view != null) {
            view.setTranslationY(translationY);
        }
        return view;
    }

    /**
     * 设置 View 硬件加速类型
     * @param view      {@link View}
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@link View}
     */
    public static View setLayerType(
            final View view,
            final int layerType,
            final Paint paint
    ) {
        if (view != null) {
            view.setLayerType(layerType, paint);
        }
        return view;
    }

    /**
     * 请求重新对 View 布局
     * @param view {@link View}
     * @return {@link View}
     */
    public static View requestLayout(final View view) {
        if (view != null) {
            view.requestLayout();
        }
        return view;
    }

    /**
     * View 请求获取焦点
     * @param view {@link View}
     * @return {@link View}
     */
    public static View requestFocus(final View view) {
        if (view != null) {
            view.requestFocus();
        }
        return view;
    }

    /**
     * View 清除焦点
     * @param view {@link View}
     * @return {@link View}
     */
    public static View clearFocus(final View view) {
        if (view != null) {
            view.clearFocus();
        }
        return view;
    }

    /**
     * 设置 View 是否在触摸模式下获得焦点
     * @param focusableInTouchMode {@code true} 可获取, {@code false} 不可获取
     * @param views                View[]
     * @return {@code true} 可获取, {@code false} 不可获取
     */
    public static boolean setFocusableInTouchMode(
            final boolean focusableInTouchMode,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setFocusableInTouchMode(focusableInTouchMode);
                }
            }
        }
        return focusableInTouchMode;
    }

    /**
     * 设置 View 是否可以获取焦点
     * @param focusable {@code true} 可获取, {@code false} 不可获取
     * @param views     View[]
     * @return {@code true} 可获取, {@code false} 不可获取
     */
    public static boolean setFocusable(
            final boolean focusable,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setFocusable(focusable);
                }
            }
        }
        return focusable;
    }

    /**
     * 切换获取焦点状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleFocusable(final View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setFocusable(!view.isFocusable());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 View 是否选中
     * @param selected {@code true} 选中, {@code false} 非选中
     * @param views    View[]
     * @return {@code true} 选中, {@code false} 非选中
     */
    public static boolean setSelected(
            final boolean selected,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setSelected(selected);
                }
            }
        }
        return selected;
    }

    /**
     * 切换选中状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleSelected(final View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setSelected(!view.isSelected());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 View 是否启用
     * @param enabled {@code true} 启用, {@code false} 禁用
     * @param views   View[]
     * @return {@code true} 启用, {@code false} 禁用
     */
    public static boolean setEnabled(
            final boolean enabled,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setEnabled(enabled);
                }
            }
        }
        return enabled;
    }

    /**
     * 切换 View 是否启用状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleEnabled(final View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setEnabled(!view.isEnabled());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 View 是否可以点击
     * @param clickable {@code true} 可点击, {@code false} 不可点击
     * @param views     View[]
     * @return {@code true} 可点击, {@code false} 不可点击
     */
    public static boolean setClickable(
            final boolean clickable,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setClickable(clickable);
                }
            }
        }
        return clickable;
    }

    /**
     * 切换 View 是否可以点击状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleClickable(final View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setClickable(!view.isClickable());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 View 是否可以长按
     * @param longClickable {@code true} 可长按, {@code false} 不可长按
     * @param views         View[]
     * @return {@code true} 可长按, {@code false} 不可长按
     */
    public static boolean setLongClickable(
            final boolean longClickable,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setLongClickable(longClickable);
                }
            }
        }
        return longClickable;
    }

    /**
     * 切换 View 是否可以长按状态
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleLongClickable(final View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setLongClickable(!view.isLongClickable());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibility(
            final boolean isVisibility,
            final View view
    ) {
        if (view != null) {
            view.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        }
        return isVisibility;
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibility(
            final int isVisibility,
            final View view
    ) {
        if (view != null) {
            view.setVisibility(isVisibility);
        }
        return (isVisibility == View.VISIBLE);
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param view         {@link View}
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibilityIN(
            final boolean isVisibility,
            final View view
    ) {
        if (view != null) {
            view.setVisibility(isVisibility ? View.VISIBLE : View.INVISIBLE);
        }
        return isVisibility;
    }

    // =

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibilitys(
            final boolean isVisibility,
            final View... views
    ) {
        return setVisibilitys(getVisibility(isVisibility), views);
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibilitys(
            final int isVisibility,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(isVisibility);
                }
            }
        }
        return (isVisibility == View.VISIBLE);
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.INVISIBLE
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean setVisibilityINs(
            final boolean isVisibility,
            final View... views
    ) {
        return setVisibilitys(getVisibilityIN(isVisibility), views);
    }

    // =

    /**
     * 切换 View 显示的状态
     * @param view  {@link View}
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleVisibilitys(
            final View view,
            final View... views
    ) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        setVisibilitys(View.GONE, views);
        return true;
    }

    /**
     * 切换 View 显示的状态
     * @param viewArrays View[]
     * @param views      View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleVisibilitys(
            final View[] viewArrays,
            final View... views
    ) {
        return toggleVisibilitys(View.GONE, viewArrays, views);
    }

    /**
     * 切换 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean toggleVisibilitys(
            final int state,
            final View[] viewArrays,
            final View... views
    ) {
        // 默认显示
        setVisibilitys(View.VISIBLE, viewArrays);
        // 根据状态处理
        setVisibilitys(state, views);
        return true;
    }

    // =

    /**
     * 反转 View 显示的状态
     * @param state      {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param viewArrays View[]
     * @param views      View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean reverseVisibilitys(
            final int state,
            final View[] viewArrays,
            final View... views
    ) {
        return reverseVisibilitys(state == View.VISIBLE, viewArrays, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param viewArrays   View[]
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean reverseVisibilitys(
            final boolean isVisibility,
            final View[] viewArrays,
            final View... views
    ) {
        // 默认处理第一个数组
        setVisibilitys(isVisibility, viewArrays);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
        return isVisibility;
    }

    /**
     * 反转 View 显示的状态
     * @param state {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view  {@link View}
     * @param views View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean reverseVisibilitys(
            final int state,
            final View view,
            final View... views
    ) {
        return reverseVisibilitys(state == View.VISIBLE, view, views);
    }

    /**
     * 反转 View 显示的状态
     * @param isVisibility {@code true} View.VISIBLE, {@code false} View.GONE
     * @param view         {@link View}
     * @param views        View[]
     * @return isVisibility 是否传入 {@link View#VISIBLE}
     */
    public static boolean reverseVisibilitys(
            final boolean isVisibility,
            final View view,
            final View... views
    ) {
        // 默认处理第一个 View
        setVisibilitys(isVisibility, view);
        // 根据状态处理
        setVisibilitys(!isVisibility, views);
        return isVisibility;
    }

    // =

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param view         {@link View}
     * @return isChange
     */
    public static boolean toggleView(
            final boolean isChange,
            final int isVisibility,
            final View view
    ) {
        if (isChange && view != null) {
            view.setVisibility(isVisibility);
        }
        return isChange;
    }

    /**
     * 切换 View 状态
     * @param isChange     是否改变
     * @param isVisibility {@link View#VISIBLE}、{@link View#INVISIBLE}、{@link View#GONE}
     * @param views        View[]
     * @return isChange
     */
    public static boolean toggleViews(
            final boolean isChange,
            final int isVisibility,
            final View... views
    ) {
        if (isChange && views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(isVisibility);
                }
            }
        }
        return isChange;
    }

    /**
     * 把自身从父 View 中移除
     * @param view {@link View}
     * @return {@link View}
     */
    public static View removeSelfFromParent(final View view) {
        if (view != null) {
            try {
                ViewParent parent = view.getParent();
                if (parent instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) parent;
                    group.removeView(view);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "removeSelfFromParent");
            }
        }
        return view;
    }

    /**
     * View 请求更新
     * @param view      {@link View}
     * @param allParent 是否全部父布局 View 都请求
     * @return {@link View}
     */
    public static View requestLayoutParent(
            final View view,
            final boolean allParent
    ) {
        if (view != null) {
            ViewParent parent = view.getParent();
            while (parent instanceof View) {
                if (!parent.isLayoutRequested()) {
                    parent.requestLayout();
                    if (!allParent) {
                        break;
                    }
                }
                parent = parent.getParent();
            }
        }
        return view;
    }

    /**
     * 测量 View
     * @param view           {@link View}
     * @param specifiedWidth 指定宽度
     * @return {@code true} success, {@code false} fail
     */
    public static boolean measureView(
            final View view,
            final int specifiedWidth
    ) {
        return WidgetUtils.measureView(view, specifiedWidth);
    }

    /**
     * 测量 View
     * @param view            {@link View}
     * @param specifiedWidth  指定宽度
     * @param specifiedHeight 指定高度
     * @return {@code true} success, {@code false} fail
     */
    public static boolean measureView(
            final View view,
            final int specifiedWidth,
            final int specifiedHeight
    ) {
        return WidgetUtils.measureView(view, specifiedWidth, specifiedHeight);
    }

    /**
     * 设置 View Layout Gravity
     * @param view    {@link View}
     * @param gravity Gravity
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLayoutGravity(
            final View view,
            final int gravity
    ) {
        return setLayoutGravity(view, gravity, true);
    }

    /**
     * 设置 View Layout Gravity
     * @param view         {@link View}
     * @param gravity      Gravity
     * @param isReflection 是否使用反射
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLayoutGravity(
            final View view,
            final int gravity,
            final boolean isReflection
    ) {
        if (view != null && view.getLayoutParams() != null) {
            try {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) layoutParams).gravity = gravity;
                    view.setLayoutParams(layoutParams);
                    return true;
                } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                    ((FrameLayout.LayoutParams) layoutParams).gravity = gravity;
                    view.setLayoutParams(layoutParams);
                    return true;
                } else if (layoutParams instanceof WindowManager.LayoutParams) {
                    ((WindowManager.LayoutParams) layoutParams).gravity = gravity;
                    view.setLayoutParams(layoutParams);
                    return true;
                }
                if (isReflection) {
                    Field[] fields = FieldUtils.getFields(layoutParams);
                    for (Field field : fields) {
                        if (field.getName().equals("gravity")) {
                            field.set(layoutParams, gravity);
                            view.setLayoutParams(layoutParams);
                            return true;
                        }
                    }
                }
                // 抛出不支持的类型
                throw new Exception("layoutParams:" + layoutParams.toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setLayoutGravity");
            }
        }
        return false;
    }

    // =

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginLeft(
            final View view,
            final int leftMargin
    ) {
        return setMarginLeft(view, leftMargin, true);
    }

    /**
     * 设置 View Left Margin
     * @param view       {@link View}
     * @param leftMargin Left Margin
     * @param reset      是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginLeft(
            final View view,
            final int leftMargin,
            final boolean reset
    ) {
        if (reset) return setMargin(view, leftMargin, 0, 0, 0);
        int[] margin = getMargin(view);
        return setMargin(view, leftMargin, margin[1], margin[2], margin[3]);
    }

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginTop(
            final View view,
            final int topMargin
    ) {
        return setMarginTop(view, topMargin, true);
    }

    /**
     * 设置 View Top Margin
     * @param view      {@link View}
     * @param topMargin Top Margin
     * @param reset     是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginTop(
            final View view,
            final int topMargin,
            final boolean reset
    ) {
        if (reset) return setMargin(view, 0, topMargin, 0, 0);
        int[] margin = getMargin(view);
        return setMargin(view, margin[0], topMargin, margin[2], margin[3]);
    }

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginRight(
            final View view,
            final int rightMargin
    ) {
        return setMarginRight(view, rightMargin, true);
    }

    /**
     * 设置 View Right Margin
     * @param view        {@link View}
     * @param rightMargin Right Margin
     * @param reset       是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginRight(
            final View view,
            final int rightMargin,
            final boolean reset
    ) {
        if (reset) return setMargin(view, 0, 0, rightMargin, 0);
        int[] margin = getMargin(view);
        return setMargin(view, margin[0], margin[1], rightMargin, margin[3]);
    }

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginBottom(
            final View view,
            final int bottomMargin
    ) {
        return setMarginBottom(view, bottomMargin, true);
    }

    /**
     * 设置 View Bottom Margin
     * @param view         {@link View}
     * @param bottomMargin Bottom Margin
     * @param reset        是否重置清空其他 margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMarginBottom(
            final View view,
            final int bottomMargin,
            final boolean reset
    ) {
        if (reset) return setMargin(view, 0, 0, 0, bottomMargin);
        int[] margin = getMargin(view);
        return setMargin(view, margin[0], margin[1], margin[2], bottomMargin);
    }

    /**
     * 设置 Margin 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(
            final View view,
            final int leftRight,
            final int topBottom
    ) {
        return setMargin(view, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param margin Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(
            final View view,
            final int margin
    ) {
        return setMargin(view, margin, margin, margin, margin);
    }

    /**
     * 设置 Margin 边距
     * @param view   {@link View}
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(
            final View view,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        if (view != null && view.getLayoutParams() != null) {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                layoutParams.setMargins(left, top, right, bottom);
                view.setLayoutParams(layoutParams);
                return true;
            }
        }
        return false;
    }

    // =

    /**
     * 设置 Margin 边距
     * @param views     View[]
     * @param leftRight Left and Right Margin
     * @param topBottom Top and bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(
            final View[] views,
            final int leftRight,
            final int topBottom
    ) {
        return setMargin(views, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param margin Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(
            final View[] views,
            final int margin
    ) {
        return setMargin(views, margin, margin, margin, margin);
    }

    /**
     * 设置 Margin 边距
     * @param views  View[]
     * @param left   Left Margin
     * @param top    Top Margin
     * @param right  Right Margin
     * @param bottom Bottom Margin
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMargin(
            final View[] views,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        if (views != null) {
            for (View view : views) {
                setMargin(view, left, top, right, bottom);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingLeft(
            final View view,
            final int leftPadding
    ) {
        return setPaddingLeft(view, leftPadding, true);
    }

    /**
     * 设置 View Left Padding
     * @param view        {@link View}
     * @param leftPadding Left Padding
     * @param reset       是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingLeft(
            final View view,
            final int leftPadding,
            final boolean reset
    ) {
        if (reset) return setPadding(view, leftPadding, 0, 0, 0);
        int[] padding = getPadding(view);
        return setPadding(view, leftPadding, padding[1], padding[2], padding[3]);
    }

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingTop(
            final View view,
            final int topPadding
    ) {
        return setPaddingTop(view, topPadding, true);
    }

    /**
     * 设置 View Top Padding
     * @param view       {@link View}
     * @param topPadding Top Padding
     * @param reset      是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingTop(
            final View view,
            final int topPadding,
            final boolean reset
    ) {
        if (reset) return setPadding(view, 0, topPadding, 0, 0);
        int[] padding = getPadding(view);
        return setPadding(view, padding[0], topPadding, padding[2], padding[3]);
    }

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingRight(
            final View view,
            final int rightPadding
    ) {
        return setPaddingRight(view, rightPadding, true);
    }

    /**
     * 设置 View Right Padding
     * @param view         {@link View}
     * @param rightPadding Right Padding
     * @param reset        是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingRight(
            final View view,
            final int rightPadding,
            final boolean reset
    ) {
        if (reset) return setPadding(view, 0, 0, rightPadding, 0);
        int[] padding = getPadding(view);
        return setPadding(view, padding[0], padding[1], rightPadding, padding[3]);
    }

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingBottom(
            final View view,
            final int bottomPadding
    ) {
        return setPaddingBottom(view, bottomPadding, true);
    }

    /**
     * 设置 View Bottom Padding
     * @param view          {@link View}
     * @param bottomPadding Bottom Padding
     * @param reset         是否重置清空其他 Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPaddingBottom(
            final View view,
            final int bottomPadding,
            final boolean reset
    ) {
        if (reset) return setPadding(view, 0, 0, 0, bottomPadding);
        int[] padding = getPadding(view);
        return setPadding(view, padding[0], padding[1], padding[2], bottomPadding);
    }

    /**
     * 设置 Padding 边距
     * @param view      {@link View}
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(
            final View view,
            final int leftRight,
            final int topBottom
    ) {
        return setPadding(view, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Padding 边距
     * @param view    {@link View}
     * @param padding Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(
            final View view,
            final int padding
    ) {
        return setPadding(view, padding, padding, padding, padding);
    }

    /**
     * 设置 Padding 边距
     * @param view   {@link View}
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(
            final View view,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        if (view != null) {
            view.setPadding(left, top, right, bottom);
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置 Padding 边距
     * @param views     View[]
     * @param leftRight Left and Right Padding
     * @param topBottom Top and bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(
            final View[] views,
            final int leftRight,
            final int topBottom
    ) {
        return setPadding(views, leftRight, topBottom, leftRight, topBottom);
    }

    /**
     * 设置 Padding 边距
     * @param views   View[]
     * @param padding Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(
            final View[] views,
            final int padding
    ) {
        return setPadding(views, padding, padding, padding, padding);
    }

    /**
     * 设置 Padding 边距
     * @param views  View[]
     * @param left   Left Padding
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPadding(
            final View[] views,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        if (views != null) {
            for (View view : views) {
                setPadding(view, left, top, right, bottom);
            }
            return true;
        }
        return false;
    }



    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addRules(
            final int verb,
            final View... views
    ) {
        return addRules(verb, -1, views);
    }

    /**
     * 设置多个 RelativeLayout View 布局规则
     * @param verb    布局位置
     * @param subject 关联 View id
     * @param views   View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addRules(
            final int verb,
            final int subject,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                addRule(view, verb, subject);
            }
            return true;
        }
        return false;
    }

    /**
     * 移除多个 RelativeLayout View 布局规则
     * @param verb  布局位置
     * @param views View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeRules(
            final int verb,
            final View... views
    ) {
        if (views != null) {
            for (View view : views) {
                removeRule(view, verb);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link View}
     */
    public static View setAnimation(
            final View view,
            final Animation animation
    ) {
        return AnimationUtils.setAnimation(view, animation);
    }

    /**
     * 清空动画
     * @param view {@link View}
     * @return {@link View}
     */
    public static View clearAnimation(final View view) {
        return AnimationUtils.clearAnimation(view);
    }

    /**
     * 启动动画
     * @param view      {@link View}
     * @param animation {@link Animation}
     * @return {@link View}
     */
    public static View startAnimation(
            final View view,
            final Animation animation
    ) {
        return AnimationUtils.startAnimation(view, animation);
    }

    /**
     * 取消动画
     * @param view {@link View}
     * @return {@link Animation}
     */
    public static Animation cancelAnimation(final View view) {
        return AnimationUtils.cancelAnimation(view);
    }/**
     * 设置背景图片
     * @param view       {@link View}
     * @param background 背景图片
     * @return {@link View}
     */
    public static View setBackground(
            final View view,
            final Drawable background
    ) {
        if (view != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(background);
                } else {
                    view.setBackgroundDrawable(background);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackground");
            }
        }
        return view;
    }

    /**
     * 设置背景颜色
     * @param view  {@link View}
     * @param color 背景颜色
     * @return {@link View}
     */
    public static View setBackgroundColor(
            final View view,
            @ColorInt final int color
    ) {
        if (view != null) {
            try {
                view.setBackgroundColor(color);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundColor");
            }
        }
        return view;
    }

    /**
     * 设置背景资源
     * @param view  {@link View}
     * @param resId resource identifier
     * @return {@link View}
     */
    public static View setBackgroundResource(
            final View view,
            @DrawableRes final int resId
    ) {
        if (view != null) {
            try {
                view.setBackgroundResource(resId);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundResource");
            }
        }
        return view;
    }

    /**
     * 设置背景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setBackgroundTintList(
            final View view,
            final ColorStateList tint
    ) {
        if (view != null) {
            try {
                view.setBackgroundTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundTintList");
            }
        }
        return view;
    }

    /**
     * 设置背景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static View setBackgroundTintMode(
            final View view,
            final PorterDuff.Mode tintMode
    ) {
        if (view != null) {
            try {
                view.setBackgroundTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBackgroundTintMode");
            }
        }
        return view;
    }

    // =======
    // = 前景 =
    // =======

    /**
     * 设置前景图片
     * @param view       {@link View}
     * @param foreground 前景图片
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForeground(
            final View view,
            final Drawable foreground
    ) {
        if (view != null) {
            try {
                view.setForeground(foreground);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForeground");
            }
        }
        return view;
    }

    /**
     * 设置前景重心
     * @param view    {@link View}
     * @param gravity 重心
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForegroundGravity(
            final View view,
            final int gravity
    ) {
        if (view != null) {
            try {
                view.setForegroundGravity(gravity);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundGravity");
            }
        }
        return view;
    }

    /**
     * 设置前景着色颜色
     * @param view {@link View}
     * @param tint 着色颜色
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForegroundTintList(
            final View view,
            final ColorStateList tint
    ) {
        if (view != null) {
            try {
                view.setForegroundTintList(tint);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundTintList");
            }
        }
        return view;
    }

    /**
     * 设置前景着色模式
     * @param view     {@link View}
     * @param tintMode 着色模式 {@link PorterDuff.Mode}
     * @return {@link View}
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static View setForegroundTintMode(
            final View view,
            final PorterDuff.Mode tintMode
    ) {
        if (view != null) {
            try {
                view.setForegroundTintMode(tintMode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setForegroundTintMode");
            }
        }
        return view;
    }/**
     * View 着色处理
     * @param view  {@link View}
     * @param color 颜色值
     * @return {@link View}
     */
    public static View setColorFilter(
            final View view,
            @ColorInt final int color
    ) {
        return setColorFilter(view, getBackground(view), color);
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return {@link View}
     */
    public static View setColorFilter(
            final View view,
            final Drawable drawable,
            @ColorInt final int color
    ) {
        try {
            setBackground(view, ImageUtils.setColorFilter(drawable, color));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setColorFilter");
        }
        return view;
    }

    // =

    /**
     * View 着色处理
     * @param view        {@link View}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link View}
     */
    public static View setColorFilter(
            final View view,
            final ColorFilter colorFilter
    ) {
        return setColorFilter(view, getBackground(view), colorFilter);
    }

    /**
     * View 着色处理, 并且设置 Background Drawable
     * @param view        {@link View}
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return {@link View}
     */
    public static View setColorFilter(
            final View view,
            final Drawable drawable,
            final ColorFilter colorFilter
    ) {
        try {
            setBackground(view, ImageUtils.setColorFilter(drawable, colorFilter));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setColorFilter");
        }
        return view;
    }

    /**
     * 设置 ProgressBar 进度条样式
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     * @return {@link View}
     */
    public static View setProgressDrawable(
            final View view,
            final Drawable drawable
    ) {
        if (view instanceof ProgressBar) {
            setProgressDrawable((ProgressBar) view, drawable);
        }
        return view;
    }

    /**
     * 设置 ProgressBar 进度条样式
     * @param view     {@link ProgressBar}
     * @param drawable {@link Drawable}
     * @return {@link ProgressBar}
     */
    public static ProgressBar setProgressDrawable(
            final ProgressBar view,
            final Drawable drawable
    ) {
        if (view != null) {
            view.setProgressDrawable(drawable);
        }
        return view;
    }

    // =

    /**
     * 设置 ProgressBar 进度值
     * @param view     {@link View}
     * @param progress 当前进度
     * @return {@link View}
     */
    public static View setBarProgress(
            final View view,
            final int progress
    ) {
        if (view instanceof ProgressBar) {
            setBarProgress((ProgressBar) view, progress);
        }
        return view;
    }

    /**
     * 设置 ProgressBar 最大值
     * @param view {@link View}
     * @param max  最大值
     * @return {@link View}
     */
    public static View setBarMax(
            final View view,
            final int max
    ) {
        if (view instanceof ProgressBar) {
            setBarMax((ProgressBar) view, max);
        }
        return view;
    }

    /**
     * 设置 ProgressBar 最大值
     * @param view     {@link View}
     * @param progress 当前进度
     * @param max      最大值
     * @return {@link View}
     */
    public static View setBarValue(
            final View view,
            final int progress,
            final int max
    ) {
        setBarMax(view, max);
        return setBarProgress(view, progress);
    }

    // 日志 TAG
    final String TAG          = IHelperByView.class.getSimpleName();
}