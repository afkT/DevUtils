package dev.engine.core.popnotification

/**
 * detail: PopNotification 常量
 * @author Ttt
 */
object PopNotificationConst {

    // ==========
    // = 未设置值 =
    // ==========

    // 未设置值 ( Int )
    const val UNSET = -1

    // 未设置值 ( Long )
    const val UNSET_LONG = -1L

    // 未设置值 ( Float )
    const val UNSET_FLOAT = -1F

    // ==========
    // = 对齐方式 =
    // ==========

    // 默认对齐方式
    const val ALIGN_DEFAULT = -1

    // 居中对齐
    const val ALIGN_CENTER = 0

    // 顶部对齐
    const val ALIGN_TOP = 1

    // 底部对齐
    const val ALIGN_BOTTOM = 2

    // 顶部内侧对齐
    const val ALIGN_TOP_INSIDE = 3

    // 底部内侧对齐
    const val ALIGN_BOTTOM_INSIDE = 4

    // ==========
    // = 状态图标 =
    // ==========

    // 默认 ( 不使用状态预置图标 )
    const val ICON_DEFAULT = -1

    // 成功状态图标
    const val ICON_SUCCESS = 0

    // 警告状态图标
    const val ICON_WARNING = 1

    // 错误状态图标
    const val ICON_ERROR = 2

    // ==========
    // = 主题明暗 =
    // ==========

    // 默认主题明暗 ( 跟随全局 )
    const val THEME_DEFAULT = -1

    // 亮色主题
    const val THEME_LIGHT = 0

    // 暗色主题
    const val THEME_DARK = 1

    // 自动 ( 跟随系统 )
    const val THEME_AUTO = 2

    // ==========
    // = 实现模式 =
    // ==========

    // 默认实现模式
    const val IMPL_MODE_DEFAULT = -1

    // View 实现模式
    const val IMPL_MODE_VIEW = 0

    // Window 实现模式
    const val IMPL_MODE_WINDOW = 1

    // DialogFragment 实现模式
    const val IMPL_MODE_DIALOG_FRAGMENT = 2

    // FloatingActivity 实现模式
    const val IMPL_MODE_FLOATING_ACTIVITY = 3
}