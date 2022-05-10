package dev.http.progress

import dev.http.progress.operation.IOperation
import dev.utils.common.assist.url.UrlExtras

/**
 * detail: Progress Operation
 * @author Ttt
 * 通过 key 区分, 支持不同组件模块化网络进度监听
 * 支持上行、下行、上下行三种类型, 内部拦截器监听回调
 * <p></p>
 * 注意事项:
 * 绑定监听事件是通过 url 进行绑定的
 * 例 url 为 https://www.abc.com/asd?type=1&abc=b
 * 会统一处理成 https://www.abc.com/asd 作为 key
 * 而其他信息通过 [Progress.Extras] 进行获取请求方法、请求头信息
 * 以及 url 携带参数解析 [Progress.Extras.getUrlExtras]
 * 具体参数信息拆分获取可通过 [UrlExtras] 进行获取
 * 并自行根据 Request 信息进行判断处理回调事件
 * <p></p>
 * 因通知回调功能支持方式不同, 选择的技术方案不同, 各有利弊。
 * 方式一:
 * 在创建 [wrapRequestBody]、[wrapResponseBody] 时, 创建一个新的 Callback
 * 并把 listener map 对应 url 监听的 Callback List toArray 传入进行通知使用
 * 优点: 可以对 List 进行弱引用处理, 会自动进行释放资源
 * 缺点: 对 listener map 新增 url add listener 在请求之后添加
 *      则会无法触发 ( 因为在请求拦截时候就已传入 Callback List toArray )
 * 方式二 ( 该库实现方式 ):
 * 在创建 [wrapRequestBody]、[wrapResponseBody] 时, 使用统一回调 [innerCallback] 无需每次 new Callback
 * 在统一回调内获取 listener map 对应 url 监听的 Callback List 并进行通知
 * 优点: 支持实时 add listener 并且通知回调
 * 缺点: 实时通知可能因绑定顺序差异, 需自行根据 [Progress.Extras.getUrlExtras] 进行判断是否需要处理该通知
 *      listener map 中的 Callback 需要手动进行释放
 * 针对该缺点提供了两个解决方案
 * 1.[Progress.Callback] 提供 isAutoRecycle 方法 ( 默认销毁 ) 可自行判断是否需要销毁
 * 2.提供 [recycleListener] 方法可在 Callback onEnd 中直接调用释放资源无需实现逻辑
 */
class ProgressOperation private constructor(
    private val key: String,
    // 全局默认操作对象
    private val globalDefault: Boolean,
    // 内部拦截器监听类型
    private val type: Int,
) {

    companion object {

        // ==================
        // = 内部拦截器监听类型 =
        // ==================

        // 监听上下行类型
        internal const val TYPE_ALL = 0

        // 监听上行 ( 上传、请求 )
        internal const val TYPE_REQUEST = 1

        // 监听下行 ( 下载、响应 )
        internal const val TYPE_RESPONSE = 2

        // =============
        // = 实现方式类型 =
        // =============

        // 实现方式一 ( 默认 )
        internal const val PLAN_A = 0

        // 实现方式二
        internal const val PLAN_B = 1

        // ==========
        // = create =
        // ==========

        /**
         * 创建 Progress Operation
         * @param key Key
         * @param globalDefault 全局默认操作对象
         * @param type 内部拦截器监听类型
         * @return Progress Operation
         */
        internal fun get(
            key: String,
            globalDefault: Boolean,
            type: Int
        ): ProgressOperation {
            return ProgressOperation(key, globalDefault, type)
        }
    }

//    // =============
//    // = 对外公开方法 =
//    // =============
//
//    /**
//     * 设置 Progress Operation 实现方式类型
//     * @param planType 实现方式类型
//     * @return IOperation
//     */
//    fun setPlanType(planType: Int): IOperation {
//
//    }
}