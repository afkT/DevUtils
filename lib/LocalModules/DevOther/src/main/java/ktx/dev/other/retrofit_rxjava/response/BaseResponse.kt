package ktx.dev.other.retrofit_rxjava.response

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * 主要用于统一解析后台返回 data、message、code 等
 */
class BaseResponse<T> {

    // 返回数据
    var data: T? = null

    // 返回消息
    var errorMsg: String? = null // message

    // 返回结果状态 ( 内部定义 )
    var errorCode: String? = null // code

    // 请求结果
    @Transient
    var result = false

    // 请求异常
    @Transient
    var exception: Throwable? = null
}