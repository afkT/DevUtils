package afkt.httpmanager.use.feature.progress.upload.data.helper

import dev.agile.app.AppExecutors
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import dev.utils.app.PathUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.FileUtils
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
     * @return File
     */
    private fun saveUploadFile(resName: String): File? {
        val filePath = uploadPath()
        val file = FileUtils.getFile(filePath, "$resName.webp")
        if (FileUtils.isFileExists(file)) return file
        val bitmap = ResourceUtils.getBitmapMipmap(resName)
        if (ImageUtils.saveBitmapToSDCardWEBP(bitmap, file)) return file
        return null
    }

    /**
     * 获取上传文件集合
     */
    fun fileLists(callback: (List<File>) -> Unit) {
        AppExecutors.instance().diskIO().execute {
            val lists = mutableListOf<File>()
            saveUploadFile("icon_launcher").hiIfNotNull { lists.add(it) }
            saveUploadFile("icon_launcher_round").hiIfNotNull { lists.add(it) }
            saveUploadFile("launcher_window_bg").hiIfNotNull { lists.add(it) }
            AppExecutors.instance().mainThread().execute {
                callback.invoke(lists)
            }
        }
    }
}