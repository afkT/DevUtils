package dev.engine.popnotification;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.Map;

/**
 * detail: PopNotification Engine 接口
 * @author Ttt
 * <p></p>
 * PopNotification 为顶部 ( 或指定方向 ) 滑入的简单通知提示
 * 支持图标、标题、内容、操作按钮、滑动关闭、自动消失时长与常驻显示
 * 支持单例 PopNotification ( 同时仅显示一个 ) 与默认 PopNotification ( 可堆叠并存 )
 * <p></p>
 * 方法、参数对齐 com.kongzue.dialogx.dialogs.PopNotification
 * <pre>
 *     show、build 系列返回 Object PopNotification 句柄
 *     句柄操作方法统一以 Object popNotification 作为首参对该句柄进行操作
 *     为保持与第三方库解耦, 第三方专属类型 ( style、textInfo、onBindView、animImpl、owner )
 *     统一以 Object 形式传递, 枚举 ( align、theme、implMode ) 以 int 常量传递
 * </pre>
 */
public interface IPopNotificationEngine<Config extends IPopNotificationEngine.EngineConfig,
        Item extends IPopNotificationEngine.EngineItem> {

    /**
     * detail: PopNotification Config
     * @author Ttt
     */
    interface EngineConfig {

        // 是否同时仅显示一个 PopNotification
        Boolean onlyOne();

        // 默认自动消失时长 ( ms )
        long autoDismissDelay();

        // 默认对齐方式
        int align();

        // ===========
        // = DialogX =
        // ===========

        // 默认实现模式 ( 映射全局 DialogX implIMPLMode )
        int dialogImplMode();

        // 默认圆角 ( px, 映射全局 DialogX defaultPopNotificationBackgroundRadius )
        int radiusPx();

        // 默认主题样式对象 ( 映射全局 DialogX globalStyle )
        Object style();

        // 默认明暗主题 ( PopNotificationConst.THEME_*, 映射全局 DialogX globalTheme )
        int theme();

        // 默认标题文本样式对象 ( 映射全局 DialogX titleTextInfo )
        Object titleTextInfo();

        // 默认提示文本样式对象 ( 映射全局 DialogX messageTextInfo )
        Object messageTextInfo();

        // 默认按钮文本样式对象 ( 映射全局 DialogX buttonTextInfo )
        Object buttonTextInfo();

        // 默认是否启用振动反馈 ( 映射全局 DialogX useHaptic )
        Boolean useHaptic();

        // 默认进入动画时长 ( ms, 映射全局 DialogX enterAnimDuration )
        long enterAnimDuration();

        // 默认退出动画时长 ( ms, 映射全局 DialogX exitAnimDuration )
        long exitAnimDuration();

        // 默认背景色 ( ColorInt, 映射全局 DialogX backgroundColor )
        Integer backgroundColor();

        // ==================
        // = PopNotification =
        // ==================

        // 最大同时显示数量 ( 映射全局 PopNotification maxShowCount )
        int maxShowCount();

        // 覆盖进入动画时长 ( ms, 映射全局 PopNotification overrideEnterDuration )
        long overrideEnterDuration();

        // 覆盖退出动画时长 ( ms, 映射全局 PopNotification overrideExitDuration )
        long overrideExitDuration();

        // 覆盖进入动画资源 id ( 映射全局 PopNotification overrideEnterAnimRes )
        int overrideEnterAnimRes();

        // 覆盖退出动画资源 id ( 映射全局 PopNotification overrideExitAnimRes )
        int overrideExitAnimRes();

        // 多 PopNotification 位移拦截器对象 ( 映射全局 PopNotification moveDisplacementInterceptor )
        Object moveDisplacementInterceptor();
    }

    /**
     * detail: PopNotification ( Data、Params ) Item
     * @author Ttt
     */
    interface EngineItem {

        // 标题文本
        CharSequence title();

        // 标题文本资源 id
        int titleResId();

        // 提示文本
        CharSequence message();

        // 提示文本资源 id
        int messageResId();

        // 图标资源 id
        int iconResId();

        // 图标 Bitmap 对象
        Bitmap iconBitmap();

        // 图标 Drawable 对象
        Drawable iconDrawable();

        // 图标尺寸 ( px )
        int iconSize();

        // 按钮文本
        CharSequence buttonText();

        // 按钮文本资源 id
        int buttonTextResId();

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
        OnPopNotificationLifecycleListener lifecycleListener();

        // 主题样式对象
        Object style();

        // 明暗主题 ( PopNotificationConst.THEME_* )
        int theme();

        // 标题文本样式对象
        Object titleTextInfo();

        // 提示文本样式对象
        Object messageTextInfo();

        // 按钮文本样式对象
        Object buttonTextInfo();

        // 状态预置图标 ( PopNotificationConst.ICON_* )
        int iconState();

        // PopNotification 自身点击事件
        OnButtonClickListener onPopNotificationClickListener();

        // 背景色资源 id ( ColorRes )
        int backgroundColorRes();

        // 图标是否随明暗模式自动染色 ( null 不设置 )
        Boolean autoTintIcon();

        // 图标是否染色 ( null 不设置 )
        Boolean tintIcon();

        // 是否支持滑动关闭 ( null 不设置 )
        Boolean slideToClose();

        // 进入动画时长 ( ms )
        long enterAnimDuration();

        // 退出动画时长 ( ms )
        long exitAnimDuration();

        // 进入动画资源 id
        int enterAnimResId();

        // 退出动画资源 id
        int exitAnimResId();

        // 自定义动画实现对象
        Object dialogXAnimImpl();

        // 是否启用振动反馈 ( null 不设置 )
        Boolean hapticFeedbackEnabled();

        // 左外边距 ( px )
        int marginLeft();

        // 上外边距 ( px )
        int marginTop();

        // 右外边距 ( px )
        int marginRight();

        // 下外边距 ( px )
        int marginBottom();

        // 根布局内边距 ( px )
        int rootPadding();

        // 临时存储数据
        Map<String, Object> data();

        // 显示层级
        int thisOrderIndex();

        // 绑定关闭的 LifecycleOwner 对象
        Object lifecycleOwner();

        // 自定义弹窗布局资源 id
        int customDialogLayoutResId();

        // 自定义弹窗布局是否亮色主题 ( null 使用单参方法 )
        Boolean customDialogLayoutLightTheme();
    }

    /**
     * detail: PopNotification 按钮点击事件
     * @author Ttt
     */
    interface OnButtonClickListener {

        /**
         * 按钮点击回调
         * @param dialog PopNotification 对象
         * @param view   触发点击的 View
         * @return {@code true} 拦截关闭, {@code false} 点击后关闭
         */
        boolean onClick(
                Object dialog,
                View view
        );
    }

    /**
     * detail: PopNotification 显示生命周期监听
     * @author Ttt
     */
    interface OnPopNotificationLifecycleListener {

        /**
         * PopNotification 显示回调
         * @param dialog PopNotification 对象
         */
        void onShow(Object dialog);

        /**
         * PopNotification 关闭回调
         * @param dialog PopNotification 对象
         */
        void onDismiss(Object dialog);
    }

    /**
     * detail: PopNotification 通用执行体
     * @author Ttt
     * <p></p>
     * 对齐 com.kongzue.dialogx.interfaces.DialogXRunnable
     * 用于 onShow、onDismiss、setActionRunnable 等回调
     */
    interface OnPopNotificationRunnable {

        /**
         * 执行回调
         * @param dialog PopNotification 对象
         */
        void run(Object dialog);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 PopNotification Engine Config
     * @return PopNotification Config
     */
    Config getConfig();

    /**
     * 设置 PopNotification Engine Config
     * @param config PopNotification Config
     */
    void setConfig(Config config);

    // =======================
    // = 构建 PopNotification =
    // =======================

    /**
     * 构建 PopNotification ( 不显示 )
     * @return PopNotification 对象
     */
    Object build();

    /**
     * 构建 PopNotification ( 不显示 )
     * @param item PopNotification 参数
     * @return PopNotification 对象
     */
    Object build(Item item);

    /**
     * 构建 PopNotification ( 不显示 )
     * @param onBindView 自定义布局
     * @return PopNotification 对象
     */
    Object buildView(Object onBindView);

    // =======================
    // = 默认 PopNotification =
    // =======================

    /**
     * 显示 PopNotification
     * @param title 标题文本
     * @return PopNotification 对象
     */
    Object show(CharSequence title);

    /**
     * 显示 PopNotification
     * @param titleResId 标题文本资源 id
     * @return PopNotification 对象
     */
    Object show(int titleResId);

    /**
     * 显示 PopNotification
     * @param title   标题文本
     * @param message 提示文本
     * @return PopNotification 对象
     */
    Object show(
            CharSequence title,
            CharSequence message
    );

    /**
     * 显示 PopNotification
     * @param iconResId 图标资源 id
     * @param title     标题文本
     * @return PopNotification 对象
     */
    Object show(
            int iconResId,
            CharSequence title
    );

    /**
     * 显示 PopNotification
     * @param item PopNotification 参数
     * @return PopNotification 对象
     */
    Object show(Item item);

    /**
     * 显示 PopNotification
     * @param activity 显示的 Activity
     * @param item     PopNotification 参数
     * @return PopNotification 对象
     */
    Object show(
            Activity activity,
            Item item
    );

    // ===============================
    // = 单例 PopNotification 句柄操作 =
    // ===============================

    // 以下方法直接操作 Engine 内部维护的单例 PopNotification ( onlyOne 时记录 )

    /**
     * 是否使用单例 PopNotification
     * @return {@code true} yes, {@code false} no
     */
    boolean isSinglePopNotification();

    /**
     * 获取单例 PopNotification
     * @return 单例 PopNotification 对象
     */
    Object getSinglePopNotification();

    /**
     * 单例 PopNotification 是否正在显示
     * @return {@code true} yes, {@code false} no
     */
    boolean isShowSinglePopNotification();

    /**
     * 关闭单例 PopNotification
     */
    void dismissSinglePopNotification();

    /**
     * 关闭单例 PopNotification ( 动画 )
     */
    void hideSinglePopNotification();

    // ==========================
    // = PopNotification 句柄操作 =
    // ==========================

    // 以下方法对齐 com.kongzue.dialogx.dialogs.PopNotification 实例方法
    // 统一以 Object popNotification 作为操作句柄, 链式方法返回该句柄便于继续调用

    /**
     * 指定 PopNotification 是否正在显示
     * @param popNotification PopNotification 对象
     * @return {@code true} yes, {@code false} no
     */
    boolean isShow(Object popNotification);

    /**
     * 关闭指定 PopNotification
     * @param popNotification PopNotification 对象
     */
    void dismiss(Object popNotification);

    /**
     * 关闭指定 PopNotification ( 动画 )
     * @param popNotification PopNotification 对象
     */
    void hide(Object popNotification);

    /**
     * 重新显示指定 PopNotification ( hide 后复显 )
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object show(Object popNotification);

    /**
     * 重新显示指定 PopNotification ( hide 后复显 )
     * @param popNotification PopNotification 对象
     * @param activity        显示的 Activity
     * @return PopNotification 对象
     */
    Object show(
            Object popNotification,
            Activity activity
    );

    /**
     * 刷新指定 PopNotification 界面
     * @param popNotification PopNotification 对象
     */
    void refreshUI(Object popNotification);

    /**
     * 设置指定 PopNotification 自动消失时长
     * @param popNotification PopNotification 对象
     * @param delay           自动消失时长 ( ms )
     * @return PopNotification 对象
     */
    Object autoDismiss(
            Object popNotification,
            long delay
    );

    /**
     * 重置指定 PopNotification 自动消失计时器
     * @param popNotification PopNotification 对象
     */
    void resetAutoDismissTimer(Object popNotification);

    /**
     * 指定 PopNotification 短时间显示
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object showShort(Object popNotification);

    /**
     * 指定 PopNotification 长时间显示
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object showLong(Object popNotification);

    /**
     * 指定 PopNotification 常驻显示 ( 不自动消失 )
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object showAlways(Object popNotification);

    /**
     * 指定 PopNotification 取消自动消失
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object noAutoDismiss(Object popNotification);

    /**
     * 指定 PopNotification 置顶显示
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object bringToFront(Object popNotification);

    /**
     * 设置指定 PopNotification 显示层级
     * @param popNotification PopNotification 对象
     * @param orderIndex      显示层级
     * @return PopNotification 对象
     */
    Object setThisOrderIndex(
            Object popNotification,
            int orderIndex
    );

    /**
     * 设置指定 PopNotification 主题样式
     * @param popNotification PopNotification 对象
     * @param style           主题样式对象
     * @return PopNotification 对象
     */
    Object setStyle(
            Object popNotification,
            Object style
    );

    /**
     * 设置指定 PopNotification 明暗主题
     * @param popNotification PopNotification 对象
     * @param theme           明暗主题 ( PopNotificationConst.THEME_* )
     * @return PopNotification 对象
     */
    Object setTheme(
            Object popNotification,
            int theme
    );

    /**
     * 设置指定 PopNotification 自定义布局
     * @param popNotification PopNotification 对象
     * @param onBindView      自定义布局
     * @return PopNotification 对象
     */
    Object setCustomView(
            Object popNotification,
            Object onBindView
    );

    /**
     * 获取指定 PopNotification 自定义布局 View
     * @param popNotification PopNotification 对象
     * @return 自定义布局 View
     */
    View getCustomView(Object popNotification);

    /**
     * 移除指定 PopNotification 自定义布局
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object removeCustomView(Object popNotification);

    /**
     * 获取指定 PopNotification 对齐方式
     * @param popNotification PopNotification 对象
     * @return 对齐方式 ( PopNotificationConst.ALIGN_* )
     */
    int getAlign(Object popNotification);

    /**
     * 设置指定 PopNotification 对齐方式
     * @param popNotification PopNotification 对象
     * @param align           对齐方式 ( PopNotificationConst.ALIGN_* )
     * @return PopNotification 对象
     */
    Object setAlign(
            Object popNotification,
            int align
    );

    /**
     * 获取指定 PopNotification 图标资源 id
     * @param popNotification PopNotification 对象
     * @return 图标资源 id
     */
    int getIconResId(Object popNotification);

    /**
     * 设置指定 PopNotification 图标资源 id
     * @param popNotification PopNotification 对象
     * @param iconResId       图标资源 id
     * @return PopNotification 对象
     */
    Object setIconResId(
            Object popNotification,
            int iconResId
    );

    /**
     * 获取指定 PopNotification 图标 Bitmap
     * @param popNotification PopNotification 对象
     * @return 图标 Bitmap
     */
    Bitmap getIconBitmap(Object popNotification);

    /**
     * 设置指定 PopNotification 图标 Bitmap
     * @param popNotification PopNotification 对象
     * @param bitmap          图标 Bitmap
     * @return PopNotification 对象
     */
    Object setIcon(
            Object popNotification,
            Bitmap bitmap
    );

    /**
     * 获取指定 PopNotification 图标 Drawable
     * @param popNotification PopNotification 对象
     * @return 图标 Drawable
     */
    Drawable getIconDrawable(Object popNotification);

    /**
     * 设置指定 PopNotification 图标 Drawable
     * @param popNotification PopNotification 对象
     * @param drawable        图标 Drawable
     * @return PopNotification 对象
     */
    Object setIcon(
            Object popNotification,
            Drawable drawable
    );

    /**
     * 获取指定 PopNotification 图标尺寸 ( px )
     * @param popNotification PopNotification 对象
     * @return 图标尺寸 ( px )
     */
    int getIconSize(Object popNotification);

    /**
     * 设置指定 PopNotification 图标尺寸 ( px )
     * @param popNotification PopNotification 对象
     * @param iconSize        图标尺寸 ( px )
     * @return PopNotification 对象
     */
    Object setIconSize(
            Object popNotification,
            int iconSize
    );

    /**
     * 获取指定 PopNotification 标题文本
     * @param popNotification PopNotification 对象
     * @return 标题文本
     */
    CharSequence getTitle(Object popNotification);

    /**
     * 设置指定 PopNotification 标题文本
     * @param popNotification PopNotification 对象
     * @param title           标题文本
     * @return PopNotification 对象
     */
    Object setTitle(
            Object popNotification,
            CharSequence title
    );

    /**
     * 获取指定 PopNotification 标题文本样式
     * @param popNotification PopNotification 对象
     * @return 标题文本样式对象
     */
    Object getTitleTextInfo(Object popNotification);

    /**
     * 设置指定 PopNotification 标题文本样式
     * @param popNotification PopNotification 对象
     * @param titleTextInfo   标题文本样式对象
     * @return PopNotification 对象
     */
    Object setTitleTextInfo(
            Object popNotification,
            Object titleTextInfo
    );

    /**
     * 获取指定 PopNotification 提示文本
     * @param popNotification PopNotification 对象
     * @return 提示文本
     */
    CharSequence getMessage(Object popNotification);

    /**
     * 设置指定 PopNotification 提示文本
     * @param popNotification PopNotification 对象
     * @param message         提示文本
     * @return PopNotification 对象
     */
    Object setMessage(
            Object popNotification,
            CharSequence message
    );

    /**
     * 设置指定 PopNotification 提示文本
     * @param popNotification PopNotification 对象
     * @param messageResId    提示文本资源 id
     * @return PopNotification 对象
     */
    Object setMessage(
            Object popNotification,
            int messageResId
    );

    /**
     * 追加指定 PopNotification 提示文本
     * @param popNotification PopNotification 对象
     * @param message         追加文本
     * @return PopNotification 对象
     */
    Object appendMessage(
            Object popNotification,
            CharSequence message
    );

    /**
     * 获取指定 PopNotification 按钮文本
     * @param popNotification PopNotification 对象
     * @return 按钮文本
     */
    CharSequence getButtonText(Object popNotification);

    /**
     * 设置指定 PopNotification 按钮文本
     * @param popNotification PopNotification 对象
     * @param buttonText      按钮文本
     * @return PopNotification 对象
     */
    Object setButton(
            Object popNotification,
            CharSequence buttonText
    );

    /**
     * 设置指定 PopNotification 按钮文本
     * @param popNotification PopNotification 对象
     * @param buttonTextResId 按钮文本资源 id
     * @return PopNotification 对象
     */
    Object setButton(
            Object popNotification,
            int buttonTextResId
    );

    /**
     * 设置指定 PopNotification 按钮点击事件
     * @param popNotification PopNotification 对象
     * @param listener        按钮点击事件
     * @return PopNotification 对象
     */
    Object setButton(
            Object popNotification,
            OnButtonClickListener listener
    );

    /**
     * 设置指定 PopNotification 按钮文本及点击事件
     * @param popNotification PopNotification 对象
     * @param buttonText      按钮文本
     * @param listener        按钮点击事件
     * @return PopNotification 对象
     */
    Object setButton(
            Object popNotification,
            CharSequence buttonText,
            OnButtonClickListener listener
    );

    /**
     * 设置指定 PopNotification 按钮文本及点击事件
     * @param popNotification PopNotification 对象
     * @param buttonTextResId 按钮文本资源 id
     * @param listener        按钮点击事件
     * @return PopNotification 对象
     */
    Object setButton(
            Object popNotification,
            int buttonTextResId,
            OnButtonClickListener listener
    );

    /**
     * 获取指定 PopNotification 提示文本样式
     * @param popNotification PopNotification 对象
     * @return 提示文本样式对象
     */
    Object getMessageTextInfo(Object popNotification);

    /**
     * 设置指定 PopNotification 提示文本样式
     * @param popNotification PopNotification 对象
     * @param messageTextInfo 提示文本样式对象
     * @return PopNotification 对象
     */
    Object setMessageTextInfo(
            Object popNotification,
            Object messageTextInfo
    );

    /**
     * 获取指定 PopNotification 按钮文本样式
     * @param popNotification PopNotification 对象
     * @return 按钮文本样式对象
     */
    Object getButtonTextInfo(Object popNotification);

    /**
     * 设置指定 PopNotification 按钮文本样式
     * @param popNotification PopNotification 对象
     * @param buttonTextInfo  按钮文本样式对象
     * @return PopNotification 对象
     */
    Object setButtonTextInfo(
            Object popNotification,
            Object buttonTextInfo
    );

    /**
     * 设置指定 PopNotification 按钮点击事件
     * @param popNotification PopNotification 对象
     * @param listener        按钮点击事件
     * @return PopNotification 对象
     */
    Object setOnButtonClickListener(
            Object popNotification,
            OnButtonClickListener listener
    );

    /**
     * 设置指定 PopNotification 自身点击事件
     * @param popNotification PopNotification 对象
     * @param listener        点击事件
     * @return PopNotification 对象
     */
    Object setOnPopNotificationClickListener(
            Object popNotification,
            OnButtonClickListener listener
    );

    /**
     * 指定 PopNotification 图标是否随明暗模式自动染色
     * @param popNotification PopNotification 对象
     * @return {@code true} yes, {@code false} no
     */
    boolean isAutoTintIconInLightOrDarkMode(Object popNotification);

    /**
     * 设置指定 PopNotification 图标是否随明暗模式自动染色
     * @param popNotification PopNotification 对象
     * @param autoTint        是否自动染色
     * @return PopNotification 对象
     */
    Object setAutoTintIconInLightOrDarkMode(
            Object popNotification,
            boolean autoTint
    );

    /**
     * 指定 PopNotification 图标是否染色
     * @param popNotification PopNotification 对象
     * @return {@code true} yes, {@code false} no
     */
    boolean getTintIcon(Object popNotification);

    /**
     * 设置指定 PopNotification 图标是否染色
     * @param popNotification PopNotification 对象
     * @param tintIcon        是否染色
     * @return PopNotification 对象
     */
    Object setTintIcon(
            Object popNotification,
            boolean tintIcon
    );

    /**
     * 设置指定 PopNotification 成功状态图标
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object iconSuccess(Object popNotification);

    /**
     * 设置指定 PopNotification 警告状态图标
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object iconWarning(Object popNotification);

    /**
     * 设置指定 PopNotification 错误状态图标
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object iconError(Object popNotification);

    /**
     * 获取指定 PopNotification 背景色
     * @param popNotification PopNotification 对象
     * @return 背景色 ( ColorInt )
     */
    int getBackgroundColor(Object popNotification);

    /**
     * 设置指定 PopNotification 背景色
     * @param popNotification PopNotification 对象
     * @param backgroundColor 背景色 ( ColorInt )
     * @return PopNotification 对象
     */
    Object setBackgroundColor(
            Object popNotification,
            int backgroundColor
    );

    /**
     * 设置指定 PopNotification 背景色
     * @param popNotification      PopNotification 对象
     * @param backgroundColorResId 背景色资源 id ( ColorRes )
     * @return PopNotification 对象
     */
    Object setBackgroundColorRes(
            Object popNotification,
            int backgroundColorResId
    );

    /**
     * 获取指定 PopNotification 圆角 ( px )
     * @param popNotification PopNotification 对象
     * @return 圆角 ( px )
     */
    float getRadius(Object popNotification);

    /**
     * 设置指定 PopNotification 圆角 ( px )
     * @param popNotification PopNotification 对象
     * @param radiusPx        圆角 ( px )
     * @return PopNotification 对象
     */
    Object setRadius(
            Object popNotification,
            float radiusPx
    );

    /**
     * 获取指定 PopNotification 进入动画时长 ( ms )
     * @param popNotification PopNotification 对象
     * @return 进入动画时长 ( ms )
     */
    long getEnterAnimDuration(Object popNotification);

    /**
     * 设置指定 PopNotification 进入动画时长 ( ms )
     * @param popNotification   PopNotification 对象
     * @param enterAnimDuration 进入动画时长 ( ms )
     * @return PopNotification 对象
     */
    Object setEnterAnimDuration(
            Object popNotification,
            long enterAnimDuration
    );

    /**
     * 获取指定 PopNotification 退出动画时长 ( ms )
     * @param popNotification PopNotification 对象
     * @return 退出动画时长 ( ms )
     */
    long getExitAnimDuration(Object popNotification);

    /**
     * 设置指定 PopNotification 退出动画时长 ( ms )
     * @param popNotification  PopNotification 对象
     * @param exitAnimDuration 退出动画时长 ( ms )
     * @return PopNotification 对象
     */
    Object setExitAnimDuration(
            Object popNotification,
            long exitAnimDuration
    );

    /**
     * 设置指定 PopNotification 进出场动画资源
     * @param popNotification PopNotification 对象
     * @param enterResId      进入动画资源 id
     * @param exitResId       退出动画资源 id
     * @return PopNotification 对象
     */
    Object setAnimResId(
            Object popNotification,
            int enterResId,
            int exitResId
    );

    /**
     * 设置指定 PopNotification 进入动画资源
     * @param popNotification PopNotification 对象
     * @param enterResId      进入动画资源 id
     * @return PopNotification 对象
     */
    Object setEnterAnimResId(
            Object popNotification,
            int enterResId
    );

    /**
     * 设置指定 PopNotification 退出动画资源
     * @param popNotification PopNotification 对象
     * @param exitResId       退出动画资源 id
     * @return PopNotification 对象
     */
    Object setExitAnimResId(
            Object popNotification,
            int exitResId
    );

    /**
     * 获取指定 PopNotification 动画实现
     * @param popNotification PopNotification 对象
     * @return 动画实现对象
     */
    Object getDialogXAnimImpl(Object popNotification);

    /**
     * 设置指定 PopNotification 动画实现
     * @param popNotification PopNotification 对象
     * @param animImpl        动画实现对象
     * @return PopNotification 对象
     */
    Object setDialogXAnimImpl(
            Object popNotification,
            Object animImpl
    );

    /**
     * 设置指定 PopNotification 是否启用振动反馈
     * @param popNotification PopNotification 对象
     * @param enabled         是否启用振动反馈
     * @return PopNotification 对象
     */
    Object setHapticFeedbackEnabled(
            Object popNotification,
            boolean enabled
    );

    /**
     * 指定 PopNotification 是否支持滑动关闭
     * @param popNotification PopNotification 对象
     * @return {@code true} yes, {@code false} no
     */
    boolean isSlideToClose(Object popNotification);

    /**
     * 设置指定 PopNotification 是否支持滑动关闭
     * @param popNotification PopNotification 对象
     * @param slideToClose    是否支持滑动关闭
     * @return PopNotification 对象
     */
    Object setSlideToClose(
            Object popNotification,
            boolean slideToClose
    );

    /**
     * 设置指定 PopNotification 实现模式
     * @param popNotification PopNotification 对象
     * @param dialogImplMode  实现模式 ( PopNotificationConst.IMPL_MODE_* )
     * @return PopNotification 对象
     */
    Object setDialogImplMode(
            Object popNotification,
            int dialogImplMode
    );

    /**
     * 设置指定 PopNotification 外边距
     * @param popNotification PopNotification 对象
     * @param left            左边距
     * @param top             上边距
     * @param right           右边距
     * @param bottom          下边距
     * @return PopNotification 对象
     */
    Object setMargin(
            Object popNotification,
            int left,
            int top,
            int right,
            int bottom
    );

    /**
     * 获取指定 PopNotification 左外边距
     * @param popNotification PopNotification 对象
     * @return 左外边距
     */
    int getMarginLeft(Object popNotification);

    /**
     * 设置指定 PopNotification 左外边距
     * @param popNotification PopNotification 对象
     * @param left            左边距
     * @return PopNotification 对象
     */
    Object setMarginLeft(
            Object popNotification,
            int left
    );

    /**
     * 获取指定 PopNotification 上外边距
     * @param popNotification PopNotification 对象
     * @return 上外边距
     */
    int getMarginTop(Object popNotification);

    /**
     * 设置指定 PopNotification 上外边距
     * @param popNotification PopNotification 对象
     * @param top             上边距
     * @return PopNotification 对象
     */
    Object setMarginTop(
            Object popNotification,
            int top
    );

    /**
     * 获取指定 PopNotification 右外边距
     * @param popNotification PopNotification 对象
     * @return 右外边距
     */
    int getMarginRight(Object popNotification);

    /**
     * 设置指定 PopNotification 右外边距
     * @param popNotification PopNotification 对象
     * @param right           右边距
     * @return PopNotification 对象
     */
    Object setMarginRight(
            Object popNotification,
            int right
    );

    /**
     * 获取指定 PopNotification 下外边距
     * @param popNotification PopNotification 对象
     * @return 下外边距
     */
    int getMarginBottom(Object popNotification);

    /**
     * 设置指定 PopNotification 下外边距
     * @param popNotification PopNotification 对象
     * @param bottom          下边距
     * @return PopNotification 对象
     */
    Object setMarginBottom(
            Object popNotification,
            int bottom
    );

    /**
     * 设置指定 PopNotification 根布局内边距
     * @param popNotification PopNotification 对象
     * @param padding         内边距
     * @return PopNotification 对象
     */
    Object setRootPadding(
            Object popNotification,
            int padding
    );

    /**
     * 设置指定 PopNotification 根布局内边距
     * @param popNotification PopNotification 对象
     * @param paddingLeft     左内边距
     * @param paddingTop      上内边距
     * @param paddingRight    右内边距
     * @param paddingBottom   下内边距
     * @return PopNotification 对象
     */
    Object setRootPadding(
            Object popNotification,
            int paddingLeft,
            int paddingTop,
            int paddingRight,
            int paddingBottom
    );

    /**
     * 设置指定 PopNotification 显示生命周期监听
     * @param popNotification PopNotification 对象
     * @param listener        显示生命周期监听
     * @return PopNotification 对象
     */
    Object setLifecycleListener(
            Object popNotification,
            OnPopNotificationLifecycleListener listener
    );

    /**
     * 设置指定 PopNotification 显示回调
     * @param popNotification PopNotification 对象
     * @param runnable        显示回调
     * @return PopNotification 对象
     */
    Object onShow(
            Object popNotification,
            OnPopNotificationRunnable runnable
    );

    /**
     * 设置指定 PopNotification 关闭回调
     * @param popNotification PopNotification 对象
     * @param runnable        关闭回调
     * @return PopNotification 对象
     */
    Object onDismiss(
            Object popNotification,
            OnPopNotificationRunnable runnable
    );

    /**
     * 设置指定 PopNotification 快捷功能键动作
     * @param popNotification PopNotification 对象
     * @param actionId        动作 id
     * @param runnable        动作执行体
     * @return PopNotification 对象
     */
    Object setActionRunnable(
            Object popNotification,
            int actionId,
            OnPopNotificationRunnable runnable
    );

    /**
     * 清除指定 PopNotification 快捷功能键动作
     * @param popNotification PopNotification 对象
     * @param actionId        动作 id
     * @return PopNotification 对象
     */
    Object cleanAction(
            Object popNotification,
            int actionId
    );

    /**
     * 清除指定 PopNotification 全部快捷功能键动作
     * @param popNotification PopNotification 对象
     * @return PopNotification 对象
     */
    Object cleanAllAction(Object popNotification);

    /**
     * 设置指定 PopNotification 临时存储数据
     * @param popNotification PopNotification 对象
     * @param key             数据 key
     * @param obj             数据值
     * @return PopNotification 对象
     */
    Object setData(
            Object popNotification,
            String key,
            Object obj
    );

    /**
     * 绑定指定 PopNotification 随 LifecycleOwner 关闭
     * @param popNotification PopNotification 对象
     * @param owner           LifecycleOwner 对象
     * @return PopNotification 对象
     */
    Object bindDismissWithLifecycleOwner(
            Object popNotification,
            Object owner
    );

    /**
     * 设置指定 PopNotification 自定义弹窗布局资源 id
     * @param popNotification      PopNotification 对象
     * @param customDialogLayoutId 自定义弹窗布局资源 id
     * @return PopNotification 对象
     */
    Object setCustomDialogLayoutResId(
            Object popNotification,
            int customDialogLayoutId
    );

    /**
     * 设置指定 PopNotification 自定义弹窗布局资源 id
     * @param popNotification      PopNotification 对象
     * @param customDialogLayoutId 自定义弹窗布局资源 id
     * @param isLightTheme         是否亮色主题布局
     * @return PopNotification 对象
     */
    Object setCustomDialogLayoutResId(
            Object popNotification,
            int customDialogLayoutId,
            boolean isLightTheme
    );
}