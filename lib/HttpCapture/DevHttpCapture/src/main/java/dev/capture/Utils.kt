package dev.capture

import com.google.gson.GsonBuilder
import dev.DevHttpCapture
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.app.PathUtils
import dev.utils.common.*
import dev.utils.common.encrypt.MD5Utils
import dev.utils.common.validator.ValidatorUtils
import java.io.File

// =================
// = 对外公开快捷方法 =
// =================

/**
 * detail: 对外公开快捷方法
 * @author Ttt
 * 为减少使用入口, 统一封装为通过 [DevHttpCapture.utils] 进行获取
 */
class UtilsPublic private constructor() {

    private object Holder {
        val instance = UtilsPublic()
    }

    companion object {

        internal fun get(): UtilsPublic {
            return Holder.instance
        }
    }

    /**
     * 获取抓包存储路径
     * @return 抓包存储路径
     */
    fun getStoragePath(): String {
        return Utils.getStoragePath()
    }

    /**
     * 获取指定模块抓包存储路径
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包存储路径
     */
    fun getModulePath(moduleName: String): String {
        return Utils.getModulePath(moduleName)
    }

    /**
     * 获取全部模块名
     * @return 模块名集合
     */
    fun getAllModuleName(): MutableList<String> {
        val lists = mutableListOf<String>()
        val root = FileUtils.getFile(getStoragePath())
        if (FileUtils.isFileExists(root)) {
            root.listFiles()?.forEach { file ->
                if (FileUtils.isDirectory(file)) {
                    lists.add(file.name)
                }
            }
        }
        return lists
    }

    /**
     * 获取全部模块所有抓包数据
     * @param isEncrypt 是否加密数据
     * @return 全部模块所有抓包数据
     */
    fun getAllModule(isEncrypt: Boolean): LinkedHashMap<String, MutableList<CaptureItem>> {
        return Utils.getAllModule(isEncrypt)
    }

    // ======================
    // = 耗时操作需开启线程执行 =
    // ======================

    /**
     * 删除指定模块抓包数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return `true` success, `false` fail
     */
    fun deleteModule(moduleName: String): Boolean {
        return FileUtils.deleteAllInDir(getModulePath(moduleName))
    }

    /**
     * 删除全部模块抓包数据
     * @return `true` success, `false` fail
     */
    fun deleteAllModule(): Boolean {
        return FileUtils.deleteAllInDir(getStoragePath())
    }

    /**
     * 获取指定模块抓包文件大小
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包文件大小
     */
    fun getModuleFileSize(moduleName: String): String {
        return FileUtils.getDirSize(getModulePath(moduleName))
    }

    /**
     * 获取全部模块抓包文件大小
     * @return 全部模块抓包文件大小
     */
    fun getAllModuleFileSize(): String {
        return FileUtils.getDirSize(getStoragePath())
    }

    /**
     * 获取指定模块抓包文件大小
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包文件大小
     */
    fun getModuleFileLength(moduleName: String): Long {
        return FileUtils.getDirLength(getModulePath(moduleName))
    }

    /**
     * 获取全部模块抓包文件大小
     * @return 全部模块抓包文件大小
     */
    fun getAllModuleFileLength(): Long {
        return FileUtils.getDirLength(getStoragePath())
    }
}

/**
 * detail: 内部实现工具类
 * @author Ttt
 */
internal object Utils {

    // ========
    // = Gson =
    // ========

    private val GSON = GsonBuilder().create()

    /**
     * 将对象转换为 JSON String
     * @param obj [Object]
     * @return JSON String
     */
    fun toJson(obj: Any?): String? {
        if (obj == null) return null
        try {
            return GSON.toJson(obj)
        } catch (e: Exception) {
            LogPrintUtils.eTag(DevHttpCapture.TAG, e, "toJson")
        }
        return null
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT [Class] T
     * @param <T>      泛型
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        if (json == null) return null
        try {
            return GSON.fromJson(json, classOfT)
        } catch (e: Exception) {
            LogPrintUtils.eTag(DevHttpCapture.TAG, e, "fromJson")
        }
        return null
    }

    // =======
    // = 排序 =
    // =======

    /**
     * 通过文件名进行排序
     * @param files 待排序文件数组
     * @return 排序后的文件名
     */
    private fun sortFileByName(files: Array<File>?): MutableList<File> {
        val lists = mutableListOf<File>()
        if (files != null) {
            lists.addAll(files.toMutableList())
            lists.sortWith { file1, file2 ->
                val value1 = ConvertUtils.toInt(file1.name)
                val value2 = ConvertUtils.toInt(file2.name)
                value2.compareTo(value1)
            }
        }
        return lists
    }

    // =============
    // = 文件操作相关 =
    // =============

    // Http Capture Storage Path
    private var sStoragePath: String? = null

    // 文件后缀
    private const val FILE_EXTENSION = ".json"
    private const val DATA_FILE_EXTENSION = "_data.json"

    /**
     * 获取抓包存储路径
     * @return 抓包存储路径
     */
    fun getStoragePath(): String {
        if (StringUtils.isEmpty(sStoragePath)) {
            sStoragePath = PathUtils.getInternal().getAppDataPath(
                DevHttpCapture.TAG
            )
        }
        return sStoragePath ?: ""
    }

    /**
     * 获取指定模块抓包存储路径
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包存储路径
     */
    fun getModulePath(moduleName: String): String {
        return FileUtils.getAbsolutePath(
            FileUtils.getFile(getStoragePath(), moduleName)
        ) ?: ""
    }

    /**
     * 获取指定模块指定抓包存储文件
     * @param modulePath 模块路径
     * @param fileName   抓包存储文件名
     * @return 指定模块指定抓包存储文件
     */
    private fun getModuleHttpCaptureFile(
        modulePath: String,
        fileName: String
    ): File {
        return FileUtils.getFile(modulePath, fileName)
    }

    /**
     * 获取指定模块指定抓包存储文件
     * @param captureFile 抓包存储文件
     * @return 指定模块指定抓包存储文件
     */
    fun getModuleHttpCaptureFile(captureFile: CaptureFile): File {
        val modulePath = getModulePath(captureFile.getModuleName())
        val filePath = getTimeFile(modulePath, captureFile.getTime())
        return getModuleHttpCaptureFile(filePath, captureFile.getFileName())
    }

    /**
     * 获取指定模块指定抓包数据存储文件
     * @param captureFile 抓包存储文件
     * @return 指定模块指定抓包数据存储文件
     */
    fun getModuleHttpCaptureDataFile(
        captureFile: CaptureFile
    ): File {
        val filePath = StringUtils.replaceEndsWith(
            FileUtils.getAbsolutePath(getModuleHttpCaptureFile(captureFile)),
            FILE_EXTENSION, DATA_FILE_EXTENSION
        )
        return FileUtils.getFile(filePath)
    }

    // =

    /**
     * 获取唯一文件名
     * @param filePath  文件路径 [getModulePath]
     * @param isEncrypt 是否加密数据
     * @return 唯一文件名
     */
    private fun getUniqueFileName(
        filePath: String,
        isEncrypt: Boolean
    ): String? {
        if (StringUtils.isEmpty(filePath)) return null
        while (true) {
            val md5Value = MD5Utils.md5(DevCommonUtils.getRandomUUIDToString())
            // 属于加密的文件名前加前缀
            val fileName = if (isEncrypt) {
                "encrypt_$md5Value$FILE_EXTENSION"
            } else {
                md5Value + FILE_EXTENSION
            }
            // 文件不存在才返回文件名
            if (!FileUtils.isFileExists(filePath, fileName)) {
                return fileName
            }
        }
    }

    /**
     * 获取时间间隔文件夹路径
     * @param modulePath 模块名
     * @param millis     创建时间 ( 本地时间戳 )
     * @return 时间间隔文件夹路径
     */
    private fun getTimeFile(
        modulePath: String,
        millis: Long
    ): String {
        val yyyyMMdd = DateUtils.formatTime(millis, DevFinal.TIME.yyyyMMdd)
        val HH = DateUtils.formatTime(millis, DevFinal.TIME.HH)
        val mm = ConvertUtils.toInt(DateUtils.formatTime(millis, DevFinal.TIME.mm))
        // 存储间隔以 10 分钟为单位
        val mmStr = if (mm < 10) { // 00-09
            "00"
        } else if (mm < 20) { // 10-19
            "10"
        } else if (mm < 30) { // 20-29
            "20"
        } else if (mm < 40) { // 30-39
            "30"
        } else if (mm < 50) { // 40-49
            "40"
        } else { // 50-59
            "50"
        }
        // 存储文件夹路径
        return FileUtils.getAbsolutePath(
            FileUtils.getFile(modulePath, yyyyMMdd + File.separator + HH + mmStr)
        )
    }

    /**
     * 存储 Http 抓包数据
     * @param captureFile 抓包存储文件
     * @return `true` success, `false` fail
     */
    fun saveHttpCaptureFile(captureFile: CaptureFile): Boolean {
        // 获取指定模块抓包存储路径
        val modulePath = getModulePath(captureFile.getModuleName())
        // 存储文件夹路径
        val filePath = getTimeFile(modulePath, captureFile.getTime())
        // 创建文件夹
        FileUtils.createFolder(filePath)
        // 获取文件名
        val fileName = getUniqueFileName(filePath, captureFile.isEncrypt())
        if (fileName != null) {
            captureFile.setFileName(fileName)
            // 将对象转换为 JSON String
            val json = captureFile.toJson()
            // 存储抓包数据文件
            FileUtils.saveFile(
                captureFile.getDataFile(),
                StringUtils.getBytes(captureFile.httpCaptureData)
            )
            // 存储处理
            return FileUtils.saveFile(
                getModuleHttpCaptureFile(filePath, fileName),
                StringUtils.getBytes(json)
            )
        }
        return false
    }

    /**
     * 通过 File 读取文件映射为抓包存储文件
     * @param file 抓包存储文件地址
     * @return 抓包存储文件
     */
    private fun fromCaptureFile(file: File): CaptureFile? {
        if (FileUtils.isFile(file)) {
            val json = FileUtils.readFile(file)
            return fromJson(json, CaptureFile::class.java)
        }
        return null
    }

    /**
     * 获取全部模块所有抓包数据
     * @param isEncrypt 是否加密数据
     * @return 全部模块所有抓包数据
     */
    fun getAllModule(isEncrypt: Boolean): LinkedHashMap<String, MutableList<CaptureItem>> {
        val maps = linkedMapOf<String, MutableList<CaptureItem>>()
        val filePath = getStoragePath()
        val rootFile = FileUtils.getFile(filePath)
        if (FileUtils.isFileExists(rootFile)) {
            rootFile.listFiles()?.forEach { file ->
                if (FileUtils.isDirectory(file)) {
                    val moduleName = file.name
                    maps[moduleName] = getModuleHttpCaptures(moduleName, isEncrypt)
                }
            }
        }
        return maps
    }

    /**
     * 获取指定模块所有抓包数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param isEncrypt  是否加密数据
     * @return 指定模块所有抓包数据
     */
    fun getModuleHttpCaptures(
        moduleName: String,
        isEncrypt: Boolean
    ): MutableList<CaptureItem> {
        val lists = mutableListOf<CaptureItem>()
        // 获取指定模块抓包存储路径
        val filePath = getModulePath(moduleName)
        val moduleFile = FileUtils.getFile(filePath)
        if (FileUtils.isFileExists(moduleFile)) {
            // 循环年月日文件夹
            val yyyyMMddFiles = sortFileByName(moduleFile.listFiles())
            yyyyMMddFiles.forEach { ymdFile ->
                // 验证是否 yyyyMMdd 8 位数数字文件名
                if (validateFileName(ymdFile, 8)) {
                    val ymdName = ymdFile.name
                    // 循环时分文件夹
                    val hhmmFiles = sortFileByName(ymdFile.listFiles())
                    if (hhmmFiles.isNotEmpty()) {
                        val captureItem = CaptureItem(ymdName)
                        hhmmFiles.forEach { hmFile ->
                            // 验证是否 hhmm 4 位数数字文件名
                            if (validateFileName(hmFile, 4)) {
                                val hmName = hmFile.name
                                // 循环抓包存储文件
                                val files = hmFile.listFiles()
                                if (files != null && files.isNotEmpty()) {
                                    val captureList = mutableListOf<CaptureFile>()
                                    files.forEach { file ->
                                        if (FileUtils.isFile(file)) {
                                            val fileName = file.name
                                            // 不属于数据文件才读取
                                            if (!fileName.endsWith(DATA_FILE_EXTENSION)) {
                                                // 判断是否加密文件
                                                val isEncryptFile = fileName.startsWith(
                                                    "encrypt_"
                                                )
                                                if (isEncrypt) {
                                                    // 要求获取加密文件并且属于加密文件才处理
                                                    if (isEncryptFile) {
                                                        fromCaptureFile(file)?.let {
                                                            captureList.add(it)
                                                        }
                                                    }
                                                } else {
                                                    // 要求获取非加密文件并且属于非加密文件才处理
                                                    if (!isEncryptFile) {
                                                        fromCaptureFile(file)?.let {
                                                            captureList.add(it)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (captureList.isNotEmpty()) {
                                        // 最新的在最前面
                                        captureList.sortWith { o1, o2 ->
                                            val diff = o1.getTime() - o2.getTime()
                                            if (diff > 0) {
                                                -1
                                            } else if (diff < 0) {
                                                1
                                            } else {
                                                0
                                            }
                                        }
                                        // 存储数据 - 时分
                                        captureItem.data[hmName] = captureList
                                    }
                                }
                            }
                        }
                        if (captureItem.data.size != 0) {
                            lists.add(captureItem)
                        }
                    }
                }
            }
        }
        return lists
    }

    /**
     * 验证文件名是否符合要求
     * @param file 待验证文件
     * @param length 文件名长度
     * @return `true` yes, `false` no
     */
    private fun validateFileName(
        file: File,
        length: Int
    ): Boolean {
        if (FileUtils.isDirectory(file)) {
            val name = file.name
            return ValidatorUtils.isNumber(name) &&
                    StringUtils.isLength(name, length)
        }
        return false
    }
}