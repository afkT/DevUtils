package ktx.dev.other.retrofit_coroutines.response

import dev.engine.json.DevJSONEngine

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * 主要用于统一解析后台返回 data、message、code 等
 */
class BaseResponse<T> {

    // 返回数据
    var data: T? = null

    // 返回消息
    var message: String? = null

    // 返回结果状态 ( 内部定义 )
    var code: String? = null

    // 请求结果
    @Transient
    var result = false

    // 请求异常
    @Transient
    var exception: Throwable? = null

    // 返回原始数据
    @Transient
    private var original: String? = null

    fun getOriginal(): String? {
        if (original != null) return original
        original = DevJSONEngine.getEngine()?.toJson(this)
        return original
    }
}