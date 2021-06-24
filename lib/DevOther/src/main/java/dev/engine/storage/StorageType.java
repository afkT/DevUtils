package dev.engine.storage;

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
}