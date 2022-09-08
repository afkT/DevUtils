package dev.capture

import dev.DevHttpCapture
import dev.utils.common.FileUtils
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
    fun getModulePath(moduleName: String?): String? {
        return Utils.getModulePath(moduleName)
    }

    /**
     * 获取全部模块名
     * @return 模块名集合
     */
    fun getAllModuleName(): MutableList<String> {
        val lists = mutableListOf<String>()
        val root: File = FileUtils.getFile(getStoragePath())
        if (FileUtils.isFileExists(root)) {
            root.listFiles()?.forEach { file ->
                if (file != null && file.isDirectory) {
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
    fun getAllModule(
        isEncrypt: Boolean
    ): MutableMap<String, MutableList<CaptureItem>> {
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
    fun deleteModule(moduleName: String?): Boolean {
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
    fun getModuleFileSize(moduleName: String?): String? {
        return FileUtils.getDirSize(getModulePath(moduleName))
    }

    /**
     * 获取全部模块抓包文件大小
     * @return 全部模块抓包文件大小
     */
    fun getAllModuleFileSize(): String? {
        return FileUtils.getDirSize(getStoragePath())
    }

    /**
     * 获取指定模块抓包文件大小
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包文件大小
     */
    fun getModuleFileLength(moduleName: String?): Long {
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