package dev.engine.core.storage

import dev.utils.app.MediaStoreUtils
import dev.utils.common.FileUtils

/**
 * detail: Storage Type
 * @author Ttt
 */
enum class StorageType {

    IMAGE,

    VIDEO,

    AUDIO,

    DOWNLOAD,

    NONE

    ;

    // ==========
    // = 快捷方法 =
    // ==========

    open fun isImage(): Boolean {
        return this == IMAGE
    }

    open fun isVideo(): Boolean {
        return this == VIDEO
    }

    open fun isAudio(): Boolean {
        return this == AUDIO
    }

    open fun isDownload(): Boolean {
        return this == DOWNLOAD
    }

    open fun isNone(): Boolean {
        return this == NONE
    }

    // ========
    // = Type =
    // ========

    companion object {

        /**
         * 通过文件后缀判断存储类型
         * @param extension 文件后缀
         * @return 存储类型
         */
        fun convertType(extension: String?): StorageType {
            extension?.let {
                val temp = if (it.startsWith(".")) {
                    it
                } else { // 不存在 . 则补上
                    ".$it"
                }
                if (FileUtils.isImageFormats(temp)) {
                    return IMAGE
                }
                if (FileUtils.isVideoFormats(temp)) {
                    return VIDEO
                }
                if (FileUtils.isAudioFormats(temp)) {
                    return AUDIO
                }
            }
            return NONE
        }

        /**
         * 通过 mimeType 判断存储类型
         * @param mimeType 资源类型
         * @return 存储类型
         */
        fun convertTypeByMimeType(mimeType: String?): StorageType {
            // 获取 mimeType 后缀
            val mimeTypeExtension = MediaStoreUtils.getExtensionFromMimeType(
                mimeType
            )
            return convertType(mimeTypeExtension)
        }

        /**
         * 通过 fileName 判断存储类型
         * @param fileName 文件名
         * @return 存储类型
         */
        fun convertTypeByFileName(fileName: String?): StorageType {
            // 获取文件名内的文件后缀
            val fileNameExtension = FileUtils.getFileExtension(
                fileName
            )
            return convertType(fileNameExtension)
        }

        // =

        /**
         * 通过 mimeType 获取对应存储文件夹
         * @param mimeType 资源类型
         * @return 存储文件夹
         */
        fun getTypeRelativePath(mimeType: String?): String {
            return getTypeRelativePath(
                convertTypeByMimeType(mimeType)
            )
        }

        /**
         * 通过存储类型获取对应存储文件夹
         * @param type 存储类型
         * @return 存储文件夹
         */
        fun getTypeRelativePath(type: StorageType?): String {
            type?.let {
                when (it) {
                    IMAGE -> return MediaStoreUtils.RELATIVE_IMAGE_PATH
                    VIDEO -> return MediaStoreUtils.RELATIVE_VIDEO_PATH
                    AUDIO -> return MediaStoreUtils.RELATIVE_AUDIO_PATH
                    else -> MediaStoreUtils.RELATIVE_DOWNLOAD_PATH
                }
            }
            return MediaStoreUtils.RELATIVE_DOWNLOAD_PATH
        }
    }
}