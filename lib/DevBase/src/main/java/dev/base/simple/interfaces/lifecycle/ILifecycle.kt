package dev.base.simple.interfaces.lifecycle

import dev.utils.app.assist.lifecycle.AbstractActivityLifecycle
import dev.utils.app.assist.lifecycle.fragment.AbstractFragmentLifecycle

/**
 * detail: 生命周期接口
 * @author Ttt
 */
interface ILifecycle {

//    /**
//     * 是否监听生命周期
//     * @return `true` yes, `false` no
//     */
//    fun isRegisterLifecycleCallbacks(): Boolean = false
}

/**
 * detail: Activity 生命周期接口
 * @author Ttt
 */
interface IActivityLifecycle : ILifecycle {

    /**
     * 获取 Activity LifecycleCallbacks 抽象类实现类
     * @return AbstractActivityLifecycle?
     */
    fun activityLifecycleIMPL(): AbstractActivityLifecycle? = null
}

/**
 * detail: Fragment 生命周期接口
 * @author Ttt
 */
interface IFragmentLifecycle : ILifecycle {

    /**
     * 获取 Fragment LifecycleCallbacks 抽象类实现类
     * @return AbstractFragmentLifecycle?
     */
    fun fragmentLifecycleIMPL(): AbstractFragmentLifecycle? = null
}