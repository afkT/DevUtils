package dev.engine.core.router

/**
 * detail: Router 常量
 * @author Ttt
 * <p></p>
 * 对齐 com.therouter.router.NavigatorKt 中 KEY_* 与 DEFAULT_REQUEST_CODE
 */
object RouterConst {

    // ==========
    // = 未设置值 =
    // ==========

    // 未设置值 ( Int )
    const val UNSET = -1

    // 未设置值 ( Long )
    const val UNSET_LONG = -1L

    // ==========
    // = TheRouter =
    // ==========

    // 默认跳转 requestCode ( 映射 NavigatorKt.DEFAULT_REQUEST_CODE, 当前库为 internal 故写死同值 )
    const val DEFAULT_REQUEST_CODE = -1008600

    // Action Extra Key ( 映射 NavigatorKt.KEY_ACTION )
    const val KEY_ACTION = com.therouter.router.KEY_ACTION

    // Path Extra Key ( 映射 NavigatorKt.KEY_PATH )
    const val KEY_PATH = com.therouter.router.KEY_PATH

    // Description Extra Key ( 映射 NavigatorKt.KEY_DESCRIPTION )
    const val KEY_DESCRIPTION = com.therouter.router.KEY_DESCRIPTION

    // Bundle Extra Key ( 映射 NavigatorKt.KEY_BUNDLE )
    const val KEY_BUNDLE = com.therouter.router.KEY_BUNDLE

    // Intent Flags Extra Key ( 映射 NavigatorKt.KEY_INTENT_FLAGS )
    const val KEY_INTENT_FLAGS = com.therouter.router.KEY_INTENT_FLAGS

    // 进入动画 Extra Key ( 映射 NavigatorKt.KEY_ANIM_IN )
    const val KEY_ANIM_IN = com.therouter.router.KEY_ANIM_IN

    // 退出动画 Extra Key ( 映射 NavigatorKt.KEY_ANIM_OUT )
    const val KEY_ANIM_OUT = com.therouter.router.KEY_ANIM_OUT

    // Navigator 对象 Extra Key ( 映射 NavigatorKt.KEY_OBJECT_NAVIGATOR )
    const val KEY_OBJECT_NAVIGATOR = com.therouter.router.KEY_OBJECT_NAVIGATOR

    // Activity 对象 Extra Key ( 映射 NavigatorKt.KEY_OBJECT_ACTIVITY )
    const val KEY_OBJECT_ACTIVITY = com.therouter.router.KEY_OBJECT_ACTIVITY
}