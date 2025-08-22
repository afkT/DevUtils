package dev.base.simple.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

///**
// * 检查当前 Fragment 是否为指定类型，如果是则执行回调
// * @param T 目标 Fragment 类型，必须是 BaseFragment 的子类
// * @param action 当 Fragment 匹配类型时执行的回调
// */
//@OptIn(ExperimentalContracts::class)
//inline fun <reified T : BaseFragment<*, *>> Any?.asFragment(
//    action: T.() -> Unit
//) {
//    contract {
//        returns() implies (this@asFragment is T)
//    }
//    // 当 Fragment 非空且是目标类型时执行回调
//    (this as? T)?.apply(action)
//}
//
//
///**
// * 检查当前 Activity 是否为指定类型，如果是则执行回调
// * @param T 目标 Activity 类型，必须是 BaseActivity 的子类
// * @param action 当 Activity 匹配类型时执行的回调
// */
//@OptIn(ExperimentalContracts::class)
//inline fun <reified T : BaseActivity<*, *>> Any?.asActivity(
//    action: T.() -> Unit
//) {
//    contract {
//        returns() implies (this@asActivity is T)
//    }
//    // 当 Activity 非空且是目标类型时执行回调
//    (this as? T)?.apply(action)
//}