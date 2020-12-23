package dev.utils.app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import dev.utils.LogPrintUtils;

/**
 * detail: 颜色状态列表工具类
 * @author Ttt
 * <pre>
 *     android:state_active	        是否处于激活状态
 *     android:state_checkable	    是否可勾选
 *     android:state_checked	    是否已勾选
 *     android:state_enabled	    是否可用
 *     android:state_first	        是否开始状态
 *     android:state_focused	    是否已获取焦点
 *     android:state_last	        是否处于结束
 *     android:state_middle	        是否处于中间
 *     android:state_pressed	    是否处于按下状态
 *     android:state_selected	    是否处于选中状态
 *     android:state_window_focused	是否窗口已获取焦点
 * </pre>
 */
public final class StateListUtils {

    private StateListUtils() {
    }

    // 日志 TAG
    private static final String TAG = StateListUtils.class.getSimpleName();

    /**
     * 获取 ColorStateList
     * @param id resource identifier of a {@link ColorStateList}
     * @return {@link ColorStateList}
     */
    public static ColorStateList getColorStateList(@ColorRes final int id) {
        return ResourceUtils.getColorStateList(id);
    }

    // ===============
    // = 设置字体颜色 =
    // ===============

    // =============
    // = 字符串颜色 =
    // =============

    /**
     * 创建 ColorStateList
     * <pre>
     *     createColorStateList("#ffffffff", "#ff44e6ff")
     * </pre>
     * @param pressed 按下状态
     * @param normal  默认状态
     * @return {@link ColorStateList}
     */
    public static ColorStateList createColorStateList(
            final String pressed,
            final String normal
    ) {
        try {
            // 颜色值
            int[] colors = new int[]{Color.parseColor(pressed), Color.parseColor(normal)};
            // 状态值
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_pressed}; // 选中状态
            states[1] = new int[]{}; // 默认状态
            // 生成 ColorStateList
            return new ColorStateList(states, colors);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createColorStateList");
        }
        return null;
    }

    /**
     * 创建 ColorStateList
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param normal   默认状态
     * @return {@link ColorStateList}
     */
    public static ColorStateList createColorStateList(
            final String selected,
            final String pressed,
            final String normal
    ) {
        try {
            // 颜色值
            int[] colors = new int[]{Color.parseColor(selected), Color.parseColor(pressed), Color.parseColor(normal)};
            // 状态值
            int[][] states = new int[3][];
            states[0] = new int[]{android.R.attr.state_selected}; // 选中状态
            states[1] = new int[]{android.R.attr.state_pressed}; // 点击状态
            states[2] = new int[]{}; // 默认状态
            // 生成 ColorStateList
            return new ColorStateList(states, colors);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createColorStateList");
        }
        return null;
    }

    /**
     * 创建 ColorStateList
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param focused  获取焦点状态
     * @param checked  已勾选状态
     * @param normal   默认状态
     * @return {@link ColorStateList}
     */
    public static ColorStateList createColorStateList(
            final String selected,
            final String pressed,
            final String focused,
            final String checked,
            final String normal
    ) {
        try {
            // 颜色值
            int[] colors = new int[]{Color.parseColor(selected), Color.parseColor(pressed),
                    Color.parseColor(focused), Color.parseColor(checked), Color.parseColor(normal)};
            // 状态值
            int[][] states = new int[5][];
            states[0] = new int[]{android.R.attr.state_selected}; // 选中状态
            states[1] = new int[]{android.R.attr.state_pressed}; // 点击状态
            states[2] = new int[]{android.R.attr.state_focused}; // 获取焦点状态
            states[3] = new int[]{android.R.attr.state_checked}; // 选中状态
            states[4] = new int[]{}; // 默认状态
            // 生成 ColorStateList
            return new ColorStateList(states, colors);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createColorStateList");
        }
        return null;
    }

    // =======
    // = int =
    // =======

    /**
     * 创建 ColorStateList
     * <pre>
     *     createColorStateList(R.color.white, R.color.black)
     * </pre>
     * @param pressed 按下状态
     * @param normal  默认状态
     * @return {@link ColorStateList}
     */
    public static ColorStateList createColorStateList(
            @ColorRes final int pressed,
            @ColorRes final int normal
    ) {
        // 颜色值
        int[] colors = new int[2];
        colors[0] = ResourceUtils.getColor(pressed);
        colors[1] = ResourceUtils.getColor(normal);
        // 状态值
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        // 生成 ColorStateList
        return new ColorStateList(states, colors);
    }

    /**
     * 创建 ColorStateList
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param normal   默认状态
     * @return {@link ColorStateList}
     */
    public static ColorStateList createColorStateList(
            @ColorRes final int selected,
            @ColorRes final int pressed,
            @ColorRes final int normal
    ) {
        // 颜色值
        int[] colors = new int[3];
        colors[0] = ResourceUtils.getColor(selected);
        colors[1] = ResourceUtils.getColor(pressed);
        colors[2] = ResourceUtils.getColor(normal);
        // 状态值
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        // 生成 ColorStateList
        return new ColorStateList(states, colors);
    }

    /**
     * 创建 ColorStateList
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param focused  获取焦点状态
     * @param checked  已勾选状态
     * @param normal   默认状态
     * @return {@link ColorStateList}
     */
    public static ColorStateList createColorStateList(
            @ColorRes final int selected,
            @ColorRes final int pressed,
            @ColorRes final int focused,
            @ColorRes final int checked,
            @ColorRes final int normal
    ) {
        // 颜色值
        int[] colors = new int[5];
        colors[0] = ResourceUtils.getColor(selected);
        colors[1] = ResourceUtils.getColor(pressed);
        colors[2] = ResourceUtils.getColor(focused);
        colors[3] = ResourceUtils.getColor(checked);
        colors[4] = ResourceUtils.getColor(normal);
        // 状态值
        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{android.R.attr.state_focused};
        states[3] = new int[]{android.R.attr.state_checked};
        states[4] = new int[]{};
        // 生成 ColorStateList
        return new ColorStateList(states, colors);
    }

    // ========================
    // = 设置背景切换 Drawable =
    // ========================

    /**
     * 创建 StateListDrawable
     * <pre>
     *     view.setBackground(Drawable)
     * </pre>
     * @param pressed 按下状态
     * @param normal  默认状态
     * @return {@link StateListDrawable}
     */
    public static StateListDrawable newSelector(
            @DrawableRes final int pressed,
            @DrawableRes final int normal
    ) {
        // 获取 Drawable
        Drawable pressedDraw = ResourceUtils.getDrawable(pressed);
        Drawable normalDraw  = ResourceUtils.getDrawable(normal);
        // 创建 StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDraw);
        stateListDrawable.addState(new int[]{}, normalDraw);
        return stateListDrawable;
    }

    /**
     * 创建 StateListDrawable
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param normal   默认状态
     * @return {@link StateListDrawable}
     */
    public static StateListDrawable newSelector(
            @DrawableRes final int selected,
            @DrawableRes final int pressed,
            @DrawableRes final int normal
    ) {
        // 获取 Drawable
        Drawable selectedDraw = ResourceUtils.getDrawable(selected);
        Drawable pressedDraw  = ResourceUtils.getDrawable(pressed);
        Drawable normalDraw   = ResourceUtils.getDrawable(normal);
        // 创建 StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDraw);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDraw);
        stateListDrawable.addState(new int[]{}, normalDraw);
        return stateListDrawable;
    }

    /**
     * 创建 StateListDrawable
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param focused  获取焦点状态
     * @param checked  已勾选状态
     * @param normal   默认状态
     * @return {@link StateListDrawable}
     */
    public static StateListDrawable newSelector(
            @DrawableRes final int selected,
            @DrawableRes final int pressed,
            @DrawableRes final int focused,
            @DrawableRes final int checked,
            @DrawableRes final int normal
    ) {
        // 获取 Drawable
        Drawable selectedDraw = ResourceUtils.getDrawable(selected);
        Drawable pressedDraw  = ResourceUtils.getDrawable(pressed);
        Drawable focusedDraw  = ResourceUtils.getDrawable(focused);
        Drawable checkedDraw  = ResourceUtils.getDrawable(checked);
        Drawable normalDraw   = ResourceUtils.getDrawable(normal);
        // 创建 StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDraw);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDraw);
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focusedDraw);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDraw);
        stateListDrawable.addState(new int[]{}, normalDraw);
        return stateListDrawable;
    }

    // ========================
    // = 设置背景切换 Drawable =
    // ========================

    /**
     * 创建 StateListDrawable
     * <pre>
     *     view.setBackground(Drawable)
     * </pre>
     * @param pressed 按下状态
     * @param normal  默认状态
     * @return {@link StateListDrawable}
     */
    public static StateListDrawable newSelector(
            final Drawable pressed,
            final Drawable normal
    ) {
        // 创建 StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{}, normal);
        return stateListDrawable;
    }

    /**
     * 创建 StateListDrawable
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param normal   默认状态
     * @return {@link StateListDrawable}
     */
    public static StateListDrawable newSelector(
            final Drawable selected,
            final Drawable pressed,
            final Drawable normal
    ) {
        // 创建 StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selected);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{}, normal);
        return stateListDrawable;
    }

    /**
     * 创建 StateListDrawable
     * @param selected 选中状态
     * @param pressed  按下状态
     * @param focused  获取焦点状态
     * @param checked  已勾选状态
     * @param normal   默认状态
     * @return {@link StateListDrawable}
     */
    public static StateListDrawable newSelector(
            final Drawable selected,
            final Drawable pressed,
            final Drawable focused,
            final Drawable checked,
            final Drawable normal
    ) {
        // 创建 StateListDrawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selected);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focused);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checked);
        stateListDrawable.addState(new int[]{}, normal);
        return stateListDrawable;
    }
}