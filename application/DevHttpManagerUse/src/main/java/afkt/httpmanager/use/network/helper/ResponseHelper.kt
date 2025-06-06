package afkt.httpmanager.use.network.helper

import com.google.gson.Gson
import dev.engine.json.JSONConfig
import dev.expand.engine.log.log_dTag
import dev.utils.app.toast.ToastTintUtils

// =================
// = 响应 Helper 类 =
// =================

object ResponseHelper {

    // 日志 TAG
    val TAG = ResponseHelper::class.java.simpleName

    // JSON Config
    private val jsonConfig = JSONConfig().apply {
        gson = Gson()
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 打印日志
     * @param tag 日志打印 TAG
     * @param message 日志信息
     */
    fun log(
        tag: String = TAG,
        message: String
    ) {
        tag.log_dTag(message = message)
    }

    /**
     * 显示操作成功 Toast
     * @param text Toast 内容
     */
    fun toastSuccess(text: String) {
        ToastTintUtils.success(text)
    }
}