package afkt.httpmanager.use.network.model

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