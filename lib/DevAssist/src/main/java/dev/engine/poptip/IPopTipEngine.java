package dev.engine.poptip;

import android.app.Activity;
import android.view.View;

/**
 * detail: PopTip Engine 接口
 * @author Ttt
 * <p></p>
 * PopTip 为类似 Toast 的非阻断式文本提示
 * 支持文本、图标、操作按钮、自动消失时长与常驻显示
 * 支持单例 PopTip ( 同时仅显示一个 ) 与默认 PopTip ( 可并存 )
 */
public interface IPopTipEngine<Config extends IPopTipEngine.EngineConfig,
        Item extends IPopTipEngine.EngineItem> {

    /**
     * detail: PopTip Config
     * @author Ttt
     */
    interface EngineConfig {

        // 是否同时仅显示一个 PopTip
        Boolean onlyOne();

        // 默认自动消失时长 ( ms )
        long autoDismissDelay();

        // 默认对齐方式
        int align();

        // 默认实现模式
        int dialogImplMode();

        // 默认圆角 ( px )
        int radiusPx();
    }

    /**
     * detail: PopTip ( Data、Params ) Item
     * @author Ttt
     */
    interface EngineItem {

        // 提示文本
        CharSequence message();

        // 提示文本资源 id
        int messageResId();

        // 图标资源 id
        int iconResId();

        // 按钮文本
        CharSequence buttonText();

        // 按钮点击事件
        OnButtonClickListener onButtonClickListener();

        // 对齐方式
        int align();

        // 自动消失时长 ( ms )
        long autoDismissDelay();

        // 是否常驻显示
        boolean noAutoDismiss();

        // 实现模式
        int dialogImplMode();

        // 背景色
        Integer backgroundColor();

        // 圆角 ( px )
        float radius();

        // 自定义布局
        Object customView();

        // 显示生命周期监听
        OnPopTipLifecycleListener lifecycleListener();
    }

    /**
     * detail: PopTip 按钮点击事件
     * @author Ttt
     */
    interface OnButtonClickListener {

        /**
         * 按钮点击回调
         * @param dialog PopTip 对象
         * @param view   触发点击的 View
         * @return {@code true} 拦截关闭, {@code false} 点击后关闭
         */
        boolean onClick(
                Object dialog,
                View view
        );
    }

    /**
     * detail: PopTip 显示生命周期监听
     * @author Ttt
     */
    interface OnPopTipLifecycleListener {

        /**
         * PopTip 显示回调
         * @param dialog PopTip 对象
         */
        void onShow(Object dialog);

        /**
         * PopTip 关闭回调
         * @param dialog PopTip 对象
         */
        void onDismiss(Object dialog);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 PopTip Engine Config
     * @return PopTip Config
     */
    Config getConfig();

    /**
     * 设置 PopTip Engine Config
     * @param config PopTip Config
     */
    void setConfig(Config config);

    // ==============
    // = 默认 PopTip =
    // ==============

    /**
     * 显示 PopTip
     * @param text 提示文本
     * @return PopTip 对象
     */
    Object show(CharSequence text);

    /**
     * 显示 PopTip
     * @param textResId 提示文本资源 id
     * @return PopTip 对象
     */
    Object show(int textResId);

    /**
     * 显示 PopTip
     * @param text       提示文本
     * @param buttonText 按钮文本
     * @return PopTip 对象
     */
    Object show(
            CharSequence text,
            CharSequence buttonText
    );

    /**
     * 显示 PopTip
     * @param iconResId 图标资源 id
     * @param text      提示文本
     * @return PopTip 对象
     */
    Object show(
            int iconResId,
            CharSequence text
    );

    /**
     * 显示 PopTip
     * @param item PopTip 参数
     * @return PopTip 对象
     */
    Object show(Item item);

    /**
     * 显示 PopTip
     * @param activity 显示的 Activity
     * @param item     PopTip 参数
     * @return PopTip 对象
     */
    Object show(
            Activity activity,
            Item item
    );

    // ==============
    // = 单例 PopTip =
    // ==============

    /**
     * 显示单例 PopTip
     * @param text 提示文本
     * @return PopTip 对象
     */
    Object showSingle(CharSequence text);

    /**
     * 显示单例 PopTip
     * @param item PopTip 参数
     * @return PopTip 对象
     */
    Object showSingle(Item item);

    /**
     * 显示单例 PopTip
     * @param activity 显示的 Activity
     * @param item     PopTip 参数
     * @return PopTip 对象
     */
    Object showSingle(
            Activity activity,
            Item item
    );

    // ==========
    // = 状态操作 =
    // ==========

    /**
     * 单例 PopTip 是否正在显示
     * @return {@code true} yes, {@code false} no
     */
    boolean isShow();

    /**
     * 关闭单例 PopTip
     */
    void dismiss();

    /**
     * 关闭指定 PopTip
     * @param popTip PopTip 对象
     */
    void dismiss(Object popTip);

    /**
     * 隐藏单例 PopTip
     */
    void hide();
}