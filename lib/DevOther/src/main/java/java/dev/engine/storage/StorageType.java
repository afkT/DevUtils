package java.dev.engine.storage;

import dev.utils.app.MediaStoreUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Storage Type
 * @author Ttt
 */
public enum StorageType {

    IMAGE,

    VIDEO,

    AUDIO,

    DOWNLOAD,

    NONE;

    // ==========
    // = 快捷方法 =
    // ==========

    public boolean isImage() {
        return this == IMAGE;
    }

    public boolean isVideo() {
        return this == VIDEO;
    }

    public boolean isAudio() {
        return this == AUDIO;
    }

    public boolean isDownload() {
        return this == DOWNLOAD;
    }

    public boolean isNone() {
        return this == NONE;
    }

    // ========
    // = Type =
    // ========

    /**
     * 通过文件后缀判断存储类型
     * @param extension 文件后缀
     * @return 存储类型
     */
    public static StorageType convertType(final String extension) {
        if (StringUtils.isNotEmpty(extension)) {
            String temp;
            if (extension.startsWith(".")) {
                temp = extension;
            } else { // 不存在 . 则补上
                temp = "." + extension;
            }
            if (FileUtils.isImageFormats(temp)) {
                return StorageType.IMAGE;
            }
            if (FileUtils.isVideoFormats(temp)) {
                return StorageType.VIDEO;
            }
            if (FileUtils.isAudioFormats(temp)) {
                return StorageType.AUDIO;
            }
        }
        return NONE;
    }

    /**
     * 通过 mimeType 判断存储类型
     * @param mimeType 资源类型
     * @return 存储类型
     */
    public static StorageType convertTypeByMimeType(final String mimeType) {
        // 获取 mimeType 后缀
        String mimeTypeExtension = MediaStoreUtils.getExtensionFromMimeType(
                mimeType
        );
        return StorageType.convertType(mimeTypeExtension);
    }

    /**
     * 通过 fileName 判断存储类型
     * @param fileName 文件名
     * @return 存储类型
     */
    public static StorageType convertTypeByFileName(final String fileName) {
        // 获取文件名内的文件后缀
        String fileNameExtension = FileUtils.getFileExtension(
                fileName
        );
        return StorageType.convertType(fileNameExtension);
    }

    // =

    /**
     * 通过 mimeType 获取对应存储文件夹
     * @param mimeType 资源类型
     * @return 存储文件夹
     */
    public static String getTypeRelativePath(final String mimeType) {
        return getTypeRelativePath(
                convertTypeByMimeType(mimeType)
        );
    }

    /**
     * 通过存储类型获取对应存储文件夹
     * @param type 存储类型
     * @return 存储文件夹
     */
    public static String getTypeRelativePath(final StorageType type) {
        if (type != null) {
            switch (type) {
                case IMAGE:
                    return MediaStoreUtils.RELATIVE_IMAGE_PATH;
                case VIDEO:
                    return MediaStoreUtils.RELATIVE_VIDEO_PATH;
                case AUDIO:
                    return MediaStoreUtils.RELATIVE_AUDIO_PATH;
            }
        }
        return MediaStoreUtils.RELATIVE_DOWNLOAD_PATH;
    }
}