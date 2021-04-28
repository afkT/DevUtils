package afkt.project.base.config

import dev.utils.app.PathUtils
import dev.utils.common.FileUtils
import java.io.File

/**
 * detail: 文件路径配置
 * @author Ttt
 * 为考虑适配情况建议参考 [PathUtils]
 * 关于路径建议及兼容:
 * 推荐隐私数据存储到内部存储中 [PathUtils.getInternal]
 * 应用数据全部存储到 外部存储 ( 私有目录 ) 中 [PathUtils.getAppExternal]
 * SDCard 目录推荐使用 [MediaStoreUtils] 存储常用 Image、Video、Document 等, 尽量减少需适配情况
 * 并且能够完善项目存储目录结构, 减少外部存储 ( 公开目录 ) 目录混乱等
 */
object PathConfig {

    // ==========
    // = SDCard =
    // ==========

    // 已抛弃统一 private 做演示效果
    @Deprecated("") // SDCard 路径
    private val BASE_SDCARD_PATH = PathUtils.getSDCard().sdCardPath

    // SDCard Project 文件统一前缀 SDP_xxx
    @Deprecated("") // 统一文件夹
    private val SDP_PATH = BASE_SDCARD_PATH + File.separator + AppConfig.BASE_NAME + File.separator

    @Deprecated("") // 临时存储
    private val SDP_TEMP_PATH = SDP_PATH + "Temp" + File.separator

    @Deprecated("") // 本地 SDCard 资源缓存地址
    private val SDP_CACHE_PATH = SDP_PATH + "Cache" + File.separator

    @Deprecated("") // 下载路径
    private val SDP_DOWN_PATH = SDP_PATH + "Download" + File.separator

    @Deprecated("") // 图片路径
    private val SDP_DOWN_IMAGE_PATH = SDP_PATH + "Image" + File.separator

    @Deprecated("") // 文本路径
    private val SDP_TEXT_PATH = SDP_PATH + "Text" + File.separator

    @Deprecated("") // 错误日志路径
    private val SDP_ERROR_PATH = SDP_PATH + "Error" + File.separator

    // ===============
    // = 应用外部存储 =
    // ===============

    // 应用外部存储
    val BASE_APP_PATH = PathUtils.getAppExternal().appDataPath

    // App External Path 文件统一前缀 AEP_xxx
    @JvmField
    val AEP_PATH = BASE_APP_PATH + File.separator + AppConfig.BASE_NAME + File.separator

    // 临时存储
    val AEP_TEMP_PATH = AEP_PATH + "Temp" + File.separator

    // 本地 SDCard 资源缓存地址
    val AEP_CACHE_PATH = AEP_PATH + "Cache" + File.separator

    // 下载路径
    val AEP_DOWN_PATH = AEP_PATH + "Download" + File.separator

    // 图片路径
    @JvmField
    val AEP_DOWN_IMAGE_PATH = AEP_PATH + "Image" + File.separator

    // 文本路径
    val AEP_TEXT_PATH = AEP_PATH + "Text" + File.separator

    // 错误日志路径
    @JvmField
    val AEP_ERROR_PATH = AEP_PATH + "Error" + File.separator

    /**
     * 初始化创建文件夹
     */
    @JvmStatic
    fun createFolder() {
        FileUtils.createFolderByPaths(
            // 临时存储
            AEP_TEMP_PATH,
            // 本地 SDCard 资源缓存地址
            AEP_CACHE_PATH,
            // 下载路径
            AEP_DOWN_PATH,
            // 图片路径
            AEP_DOWN_IMAGE_PATH,
            // 文本路径
            AEP_TEXT_PATH,
            // 错误日志路径
            AEP_ERROR_PATH
        )
    }
}