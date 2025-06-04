package afkt.retrofit.use.helper

import dev.retrofit.Base

// ==========
// = 响应模型 =
// ==========

/**
 * detail: Service Exception
 * @author Ttt
 */
class ServiceException(
    errorMessage: String?
) : Exception(errorMessage.orEmpty())


// ==============
// = 通用响应模型 =
// ==============

/**
 * 参考 error.json、photos.json 返回字段类型是通用
 * 但是对于 books.json、movie_detail.json 非通用的
 * 可以使用 [Base.Response] 实现，正常不会有这种情况
 * 解决新旧 API 变更、使用第三方接口、SDK 等返回结构不一致时，可自定义解析类进行解析处理
 */

/**
 * detail: 通用响应模型
 * @author Ttt
 */
data class BaseResponse<T>(
    val code: String,
    val message: String,
    val data: T,
) {
    /**
     * 是否请求成功
     */
    fun isSuccess(): Boolean {
        return code == "200"
    }
}

/**
 * detail: 通用分页数据模型
 * @author Ttt
 */
data class BasePage<T>(
    val current: Int,
    val size: Int,
    val total: Int,
    val datas: List<T>
)

// =====================
// = Base.Response 实现 =
// =====================

/**
 * detail: App 统一响应模型
 * @author Ttt
 * 解决不同请求响应字段不一致情况
 */
open class AppResponse<T> : Base.Response<T> {

    // ===========================
    // = error.json、photos.json =
    // ===========================

    private var code: String? = null
    private var message: String? = null
    private var data: T? = null

    // ==============
    // = books.json =
    // ==============

    private var resultCode: Int? = null
    private var result: T? = null

    // =====================
    // = movie_detail.json =
    // =====================

    private var responseCode: Int? = null
    private var responseMessage: String? = null
    private var response: T? = null

    // =====================
    // = Base.Response 实现 =
    // =====================

    override fun getData(): T? {
        if (data != null) return data
        if (result != null) return result
        if (response != null) return response
        return null
    }

    override fun getCode(): String? {
        if (code != null) return code
        if (resultCode != null) return resultCode.toString()
        if (responseCode != null) return responseCode.toString()
        return null
    }

    override fun getMessage(): String? {
        if (message != null) return message
        if (responseMessage != null) return responseMessage
        return null
    }

    override fun isSuccess(): Boolean {
//        // 方式一
//        val _code = getCode().orEmpty()
//        return when (_code) {
//            "200", "0" -> true
//            else -> false
//        }
        // 方式二
        if (code == "200") return true
        if (resultCode == 200) return true
        if (responseCode == 0) return true
        return false
    }
}