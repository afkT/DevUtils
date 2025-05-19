package dev.simple.app.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import dev.base.utils.assist.DevBaseContentAssist
import dev.simple.app.controller.interfaces.IController
import dev.utils.app.BarUtils
import dev.utils.app.assist.WindowAssist

/**
 * detail: 基础 UI 控制封装
 * @author Ttt
 * VDB 定义变量初始化 ( 绑定 UI 自动更新 )
 * 一定要在设置 lifecycleOwner 之前进行调用, 否则部分值为 null 会导致抛出异常
 * 如 [TextView.setTextColor] 设置 ColorStateList 为 null 会抛出异常
 * 步骤
 * binding.setVariable(BR.xxx, variable)
 * // 支持 LiveData 绑定 xml 数据改变 UI 自动会更新
 * binding.lifecycleOwner = owner
 */
class BaseUIController(
    val contentAssist: DevBaseContentAssist,
    val controller: IController,
    private val owner: LifecycleOwner
) {
    init {
        // 是否安全处理 addView
        contentAssist.setSafe(controller.isContentAssistSafe())
    }

    // ==========
    // = 内部操作 =
    // ==========

    /**
     * 初始化 UI 样式
     * @param activity AppCompatActivity
     */
    internal fun initialize(activity: AppCompatActivity) {
        activity.apply {
            val windowAssist = WindowAssist.get(this)

            // 状态栏样式设置
            statusBarStyle(this)

            // Window Flag 样式设置
            windowFlagStyle(this, windowAssist)

            // Window Feature 样式设置
            windowFeatureStyle(this, windowAssist)

            // Activity onCreate 创建之前触发
            controller.onCreateBefore()?.execute(
                this, windowAssist, controller
            )
        }
    }

    /**
     * 状态栏样式设置
     * @param activity AppCompatActivity
     */
    private fun statusBarStyle(activity: AppCompatActivity) {
        activity.apply {
            // 设置 StatusBar 是否高亮模式
            BarUtils.setStatusBarLightMode(this, !controller.isLightMode())

            if (controller.isStatusBarFrame()) {
                // 设置透明 StatusBar
                BarUtils.transparentStatusBar(this)
            }
        }
    }

    /**
     * Window Flag 样式设置
     * @param activity AppCompatActivity
     * @param windowAssist WindowAssist
     */
    private fun windowFlagStyle(
        activity: AppCompatActivity,
        windowAssist: WindowAssist
    ) {
        // 设置 Activity 是否全屏
        if (controller.isFlagFullScreen()) {
            windowAssist.setFlagFullScreen()
        } else {
            windowAssist.clearFlagFullScreen()
        }

        // 设置是否屏幕常亮
        if (controller.isFlagKeepScreen()) {
            windowAssist.setFlagKeepScreenOn()
        } else {
            windowAssist.clearFlagKeepScreenOn()
        }

        // 设置是否允许截屏
        if (controller.isFlagSecure()) {
            windowAssist.clearFlagSecure()
        } else {
            windowAssist.setFlagSecure()
        }
    }

    /**
     * Window Feature 样式设置
     * @param activity AppCompatActivity
     * @param windowAssist WindowAssist
     */
    private fun windowFeatureStyle(
        activity: AppCompatActivity,
        windowAssist: WindowAssist
    ) {
        // 设置是否屏幕页面为无标题
        if (controller.isFeatureNoTitle()) {
            if (!windowAssist.isNoTitleFeature) {
                activity.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
            }
        }
    }

    /**
     * 添加基础骨架 View
     * @param context Context?
     */
    internal fun addSkeletonView(context: Context?) {
        context?.let { ctx ->
            val inflater = LayoutInflater.from(ctx)
            // 是否添加 StatusBar
            if (controller.isAddStatusBar()) {
                // 创建 StatusBar 骨架 View
                val statusBar = controller.createStatusBarSkeletonView(
                    ctx, inflater, owner
                )
                if (statusBar != null) {
                    contentAssist.addStatusBarView(statusBar)
                }
            }
            // 是否添加 TitleBar
            if (controller.isAddTitleBar()) {
                // 创建 TitleBar 骨架 View
                val titleBar = controller.createTitleBarSkeletonView(
                    ctx, inflater, owner
                )
                if (titleBar != null) {
                    contentAssist.addTitleView(titleBar)
                }
            }
        }
    }
}