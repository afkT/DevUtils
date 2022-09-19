package dev.mvvm.utils

// ==============
// = 通用扩展函数 =
// ==============

/**
 * 在 let 基础上增加如果 null 执行方法体
 * @receiver T?
 * @param block 不为 null 执行方法体
 * @param nullBlock null 执行方法体
 */
inline fun <T, R> T?.letNull(
    block: (T) -> R,
    nullBlock: () -> Unit
) {
    if (this != null) {
        block.invoke(this)
    } else {
        nullBlock.invoke()
    }
}