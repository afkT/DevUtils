package afkt.httpmanager.use.feature.progress.upload.data.helper

import android.graphics.Bitmap
import dev.simple.core.app.AppExecutors
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.utils.app.MediaStoreUtils
import dev.utils.app.PathUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.UriUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.FileUtils
import dev.utils.common.StringUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

// =================
// = 上传 Helper 类 =
// =================

object UploadHelper {

    // ========
    // = Path =
    // ========

    /**
     * 上传存储文件夹路径
     */
    private fun uploadPath(): String {
        val folder = PathUtils.getAppExternal().getAppCachePath("upload")
        FileUtils.createFolder(folder)
        return folder
    }

    /**
     * 保存上传文件
     * @param resName 资源名
     * @param format 图片格式
     * @return File
     */
    private fun saveUploadFile(
        resName: String,
        format: Bitmap.CompressFormat
    ): File? {
        val filePath = uploadPath()
        val file = FileUtils.getFile(filePath, "$resName.${format.toString().lowercase()}")
        if (FileUtils.isFileExists(file)) return file
        val bitmap = ResourceUtils.getBitmapMipmap(resName)
        if (ImageUtils.saveBitmapToSDCard(bitmap, file, format, 100)) return file
        return null
    }

    /**
     * 获取上传文件集合
     */
    fun fileLists(callback: (List<File>) -> Unit) {
        AppExecutors.instance().diskIO().execute {
            val lists = mutableListOf<File>()
            mutableListOf(
                "icon_launcher", "icon_launcher_round", "launcher_window_bg"
            ).forEach { resName ->
                // 每份文件存储 webp、png 两种格式防止上传速度过快，用于演示
                saveUploadFile(resName, Bitmap.CompressFormat.WEBP).hiIfNotNull {
                    lists.add(it)
                }
                saveUploadFile(resName, Bitmap.CompressFormat.PNG).hiIfNotNull {
                    lists.add(it)
                }
            }
            AppExecutors.instance().mainThread().execute {
                callback.invoke(lists)
            }
        }
    }
}

/**
 * File 转 RequestBody
 * @return File RequestBody
 */
fun File.toRequestBody(): RequestBody {
    return asRequestBody(getMimeType().toMediaTypeOrNull())
}

/**
 * 获取文件 MimeType
 * @return File MimeType
 */
fun File.getMimeType(): String {
    var result: String? = null
    try {
        val suffix = FileUtils.getFileSuffix(this)
        result = MediaStoreUtils.getMimeTypeFromExtension(suffix)
    } catch (_: Exception) {
        try {
            val uri = UriUtils.getUriForFile(this)
            result = ResourceUtils.getContentResolver().getType(uri)
        } catch (_: Exception) {
        }
    }
    return StringUtils.checkValue(result)
}