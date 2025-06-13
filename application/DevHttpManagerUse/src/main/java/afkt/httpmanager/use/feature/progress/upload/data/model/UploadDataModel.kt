package afkt.httpmanager.use.feature.progress.upload.data.model

// =================
// = 上传模块数据模型 =
// =================

/**
 * detail: 上传响应实体类
 * @author Ttt
 */
data class UploadBean(
    val err: Int,
    val msg: String?,
    val id: String,
    val ids: List<String>,
    val name: String,
    val names: List<String>,
    val size: Int,
    val sizes: List<Int>,
    val url: String,
    val urls: List<String>
)