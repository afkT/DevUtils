package afkt.httpmanager.use.feature.progress.download.data.helper

import dev.simple.core.app.AppExecutors
import dev.utils.app.PathUtils
import dev.utils.common.FileIOUtils
import dev.utils.common.FileUtils
import dev.utils.common.StreamUtils
import okhttp3.ResponseBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

// =================
// = 下载 Helper 类 =
// =================

object DownloadHelper {

    // ========
    // = Path =
    // ========

    /**
     * 下载存储文件夹路径
     */
    private fun downloadPath(): String {
        val folder = PathUtils.getInternal().getAppCachePath("download")
        FileUtils.createFolder(folder)
        return folder
    }

    /**
     * 是否存在下载文件
     * @param url 下载链接
     * @return `true` yes, `false` no
     */
    fun isFileExists(url: String): Boolean {
        val file = urlDownloadFile(url)
        return FileUtils.isFileExists(file)
    }

    /**
     * 删除已下载文件
     * @param url 下载链接
     */
    fun deleteUrlDownloadFile(url: String) {
        val file = urlDownloadFile(url)
        val tempFile = urlTempDownloadFile(url)
        FileUtils.deleteFiles(tempFile, file)
    }

    /**
     * 通过 Url 获取下载存储文件
     * @param url 下载链接
     * @return 下载存储文件 File
     */
    fun urlDownloadFile(url: String): File {
        val filePath = downloadPath()
        val fileName = FileUtils.getFileName(url)
        return FileUtils.getFile(filePath, fileName)
    }

    /**
     * 通过 Url 获取下载存储文件 ( 临时文件名 )
     * @param url 下载链接
     * @return 下载存储文件 File
     */
    private fun urlTempDownloadFile(url: String): File {
        val filePath = downloadPath()
        val fileName = FileUtils.getFileName(url) + ".download"
        return FileUtils.getFile(filePath, fileName)
    }

    // =========
    // = Write =
    // =========

    /**
     * 写入文件
     * @param url 下载链接
     * @param body 请求响应体
     * @param errorBlock 异常响应体
     */
    fun writeFile(
        url: String,
        body: ResponseBody,
        errorBlock: (Throwable) -> Unit = {}
    ) {
        AppExecutors.instance().networkIO().execute {
            try {
                val tempFile = urlTempDownloadFile(url)
                FileUtils.deleteFile(tempFile)
                val inputStream = body.byteStream()
                if (FileIOUtils.writeFileFromIS(tempFile, inputStream)) {
                    val file = urlDownloadFile(url)
                    FileUtils.deleteFile(file)
                    // 修改名字
                    FileUtils.rename(tempFile, file.name)
                }
            } catch (e: Exception) {
                errorBlock.invoke(e)
            }
        }
    }

    /**
     * 写入文件 ( 回调通知进度 )
     * @param url 下载链接
     * @param body 请求响应体
     * @param callback 写入回调
     */
    fun writeFile(
        url: String,
        body: ResponseBody,
        callback: StreamUtils.WriteCallback
    ) {
        AppExecutors.instance().networkIO().execute {
            try {
                val tempFile = urlTempDownloadFile(url)
                FileUtils.deleteFile(tempFile)
                val outputStream = BufferedOutputStream(FileOutputStream(tempFile))
                val inputStream = body.byteStream()
                val totalSize = body.contentLength()
                if (StreamUtils.inputWriteOutputStreamCallback(
                        inputStream, outputStream, totalSize, callback
                    )
                ) {
                    val file = urlDownloadFile(url)
                    FileUtils.deleteFile(file)
                    // 修改名字
                    FileUtils.rename(tempFile, file.name)
                }
            } catch (e: Exception) {
                // 写入异常回调
                callback.onError(e)
                callback.onEnd()
            }
        }
    }
}