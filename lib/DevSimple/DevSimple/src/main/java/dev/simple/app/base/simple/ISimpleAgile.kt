package dev.simple.app.base.simple

import dev.simple.app.BaseAppActivity
import dev.simple.app.BaseAppFragment
import dev.simple.app.base.BaseActivity
import dev.simple.app.base.BaseFragment
import dev.simple.app.extension.loading.BaseLoadingActivity
import dev.simple.app.extension.loading.BaseLoadingFragment
import dev.simple.app.extension.loading.BaseLoadingSkeletonActivity
import dev.simple.app.extension.loading.BaseLoadingSkeletonFragment
import dev.simple.app.extension.mvvm.BaseMVVMActivity
import dev.simple.app.extension.mvvm.BaseMVVMFragment
import dev.simple.app.extension.theme.BaseUIThemeActivity
import dev.simple.app.extension.theme.BaseUIThemeFragment
import dev.widget.assist.ViewAssist

/**
 * detail: 敏捷简化开发扩展接口
 * @author Ttt
 * 专门用于简化调用代码, 方便敏捷开发相关操作
 * TODO 特殊注释提醒
 * 项目基类都是循序式高度定制化
 * 从最初的 [BaseActivity]、[BaseFragment] 推进至 [BaseUIThemeActivity]、[BaseUIThemeFragment]
 * 使之拥有统一主题样式、样式复用二次自定义功能。
 * 并且在此基础上又进行推进 [BaseMVVMActivity]、[BaseMVVMFragment]
 * 支持构造函数传入 ViewDataBinding 所需映射的 layoutId 或 layoutView, 并进行变量双向绑定关联。
 * 至此整个基类基础功能都已实现, 并对单一功能基类实现拆分。
 * 为了统一规范创建 [BaseAppActivity]、[BaseAppFragment] 冠以 App 名称, 对外都以该基类为【起始点】进行继承。
 * 以防后续需要插入其他技术封装基类, 同上述循序式推进继承使之都拥有新技术扩展功能。
 * =========
 * 该敏捷简化开发扩展接口以【起始点】进行支持, 减少无效扩展代码
 * 着重说明【起始点】后扩展的基类为了统一规范, 都需支持并实现敏捷开发功能
 * =========
 * 例: 在【起始点】上又扩展推进 [BaseLoadingSkeletonActivity]、[BaseLoadingSkeletonFragment]
 * 用于首次进入使用 Activity、Fragment 统一显示 Loading 骨架 UI
 * 通过 [ViewAssist] 实现无需在具体功能布局中添加上述功能及实现逻辑等
 * 上述基类属于首次使用 ( 只要切换成功状态就不会再次显示 )
 * =========
 * 在 Loading 骨架 UI 基类上又推进 [BaseLoadingActivity]、[BaseLoadingFragment]
 * 在内容布局上盖一层状态布局 ( 加载中、失败、成功等自定义状态 )。
 * 使用场景: 点击某个功能按钮后需要显示 loading 在 content layout 之上 ( 非 Dialog 使用 FrameLayout 实现 )
 * 可自行根据使用场景进行选择或设计 Dialog Loading 方式状态管理基类
 */
interface ISimpleAgile {

    // ==========
    // = 具体方法 =
    // ==========

    /**
     * 简化内部初始化前调用
     */
    fun simpleInit()

    /**
     * 简化内部初始化后开始流程调用
     */
    fun simpleStart()

    /**
     * 简化预加载
     */
    fun simplePreLoad()

    /**
     * 简化调用代码
     */
    fun simpleAgile()

    // ==========
    // = 执行顺序 =
    // ==========

    // =======================
    // = BaseActivity 执行顺序 =
    // =======================

    /**
     * [BaseActivity] 执行顺序
     * =
     * [BaseActivity.onCreate]
     * // 内部初始化前调用
     * [simpleInit]
     * // 内部初始化
     * innerInitialize()
     * // 内部初始化后开始流程调用
     * [simpleStart]
     * // 初始化 ViewModel
     * initViewModel()
     * // 添加基础骨架 View
     * uiController.addSkeletonView(this)
     * // 简化预加载
     * [simplePreLoad]
     * // 预加载方法
     * preLoad()
     * // 初始化方法
     * initOrder()
     * // 敏捷开发简化调用
     * [simpleAgile]
     */

    // =======================
    // = BaseFragment 执行顺序 =
    // =======================

    /**
     * [BaseFragment] 执行顺序
     * =
     * [BaseFragment.onCreateView]
     * // 内部初始化前调用
     * [simpleInit]
     * // 内部初始化
     * innerInitialize()
     * // 内部初始化后开始流程调用
     * [simpleStart]
     * =
     * [BaseFragment.onViewCreated]
     * // 初始化 ViewModel
     * initViewModel()
     * // 添加基础骨架 View
     * uiController.addSkeletonView(context)
     * // 简化预加载
     * [simplePreLoad]
     * // 预加载方法
     * preLoad()
     * // 初始化方法
     * initOrder()
     * // 敏捷开发简化调用
     * [simpleAgile]
     */
}