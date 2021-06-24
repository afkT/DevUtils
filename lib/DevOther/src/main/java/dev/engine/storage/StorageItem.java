package dev.engine.storage;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

import dev.utils.app.SDCardUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Storage Item Params
 * @author Ttt
 * <pre>
 *     {@link #mFolder} 只需要文件夹名字
 *     是因为 Android 适配 {@link android.provider.MediaStore} 传入 RELATIVE_PATH 使用
 *     <p></p>
 *     {@link #mFilePath} 只会在内部存储时使用
 *     属于内部存储时, 完整路径为 filePath + folder + fileName
 *     <p></p>
 *     可传入输出 Uri 或通过拼接路径创建 Uri 二选一
 *     外部存储时:
 *     必须传入 {@link #mFolder}、{@link #mMimeType}、{@link #mFileName}
 *     {@link #mFileName} 存储文件名是否需要后缀视 {@link #mMimeType} 情况而定
 *     ( 正常无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
 *     内部存储时:
 *     必须传入 {@link #mFilePath}、{@link #mFileName}
 *     {@link #mFileName} 需携带后缀
 * </pre>
 */
public class StorageItem
        extends IStorageEngine.EngineItem {

    // =======
    // = 通用 =
    // =======

    // 存储路径 ( 不包含文件名, 纯路径 ) 只会在内部存储时使用
    private String mFilePath;
    // 存储文件名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
    private String mFileName;
    // 存储文件夹 ( 不包含完整路径, 就文件夹名, 不传则会存储在对应路径文件根目录 )
    private String mFolder; // 可以传入, 如: /Dev/Material
    // 资源类型
    private String mMimeType;

    // 输出 Uri ( 可以自行指定输出 Uri 优先使用该值 )
    private Uri mOutputUri;

    // ==============================================
    // = Storage DevSource 属于 Bitmap、Drawable 使用 =
    // ==============================================

    // 图片格式
    private Bitmap.CompressFormat mFormat  = Bitmap.CompressFormat.PNG;
    // 图片质量
    private int                   mQuality = 100;

    // =============
    // = 对外公开方法 =
    // =============

    public String getFilePath() {
        return mFilePath;
    }

    public StorageItem setFilePath(String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    public String getFileName() {
        return mFileName;
    }

    public StorageItem setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    public String getFolder() {
        return mFolder;
    }

    public StorageItem setFolder(String folder) {
        this.mFolder = folder;
        return this;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public StorageItem setMimeType(String mimeType) {
        this.mMimeType = mimeType;
        return this;
    }

    public Uri getOutputUri() {
        return mOutputUri;
    }

    public StorageItem setOutputUri(Uri outputUri) {
        this.mOutputUri = outputUri;
        return this;
    }

    // =

    public Bitmap.CompressFormat getFormat() {
        return mFormat;
    }

    public StorageItem setFormat(Bitmap.CompressFormat format) {
        this.mFormat = format;
        return this;
    }

    public int getQuality() {
        return mQuality;
    }

    public StorageItem setQuality(int quality) {
        this.mQuality = quality;
        return this;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    // ==========
    // = 路径获取 =
    // ==========

    /**
     * 获取内部存储完整路径
     * @return 内部存储完整路径
     */
    public File getInternalFile() {
        String internalPath = mFilePath;
        // 判断是否存在文件夹, 存在则追加到 存储路径 上
        if (StringUtils.isNotEmpty(mFolder)) {
            File internalFile = FileUtils.getFile(
                    mFilePath, mFolder
            );
            internalPath = FileUtils.getAbsolutePath(internalFile);
        }
        // filePath + folder + fileName
        return FileUtils.getFile(internalPath, mFileName);
    }

    /**
     * 获取内部存储文件夹路径
     * @return 内部存储文件夹路径
     */
    public File getInternalFolder() {
        String internalPath = mFilePath;
        // 判断是否存在文件夹, 存在则追加到 存储路径 上
        if (StringUtils.isNotEmpty(mFolder)) {
            File internalFile = FileUtils.getFile(
                    mFilePath, mFolder
            );
            internalPath = FileUtils.getAbsolutePath(internalFile);
        }
        // filePath + folder
        return FileUtils.getFile(internalPath);
    }

    // =

    /**
     * 获取外部存储完整路径
     * @return 外部存储完整路径
     */
    public File getExternalFile() {
        return getExternalFile(mFileName);
    }

    /**
     * 获取外部存储完整路径
     * @param fileName 文件名
     * @return 外部存储完整路径
     */
    public File getExternalFile(final String fileName) {
        // SDCard/folder/fileName
        return FileUtils.getFile(
                SDCardUtils.getSDCardPath(mFolder),
                fileName
        );
    }

    /**
     * 获取外部存储文件夹路径
     * @return 外部存储文件夹路径
     */
    public File getExternalFolder() {
        // SDCard/folder
        return SDCardUtils.getSDCardFile(mFolder);
    }

    // ==========
    // = 快捷创建 =
    // ==========

    /**
     * 创建指定输出 Uri Item
     * @param outputUri 输出 Uri
     * @return {@link StorageItem}
     */
    public static StorageItem createUriItem(final Uri outputUri) {
        return new StorageItem().setOutputUri(outputUri);
    }

    // ==========
    // = 内部存储 =
    // ==========

    /**
     * 创建内部存储路径信息 Item
     * @param filePath 存储路径 ( 不包含文件名, 纯路径 ) 只会在内部存储时使用
     * @param fileName 存储文件名 ( 需携带后缀 )
     * @return {@link StorageItem}
     */
    public static StorageItem createInternalItem(
            final String filePath,
            final String fileName
    ) {
        return new StorageItem().setFilePath(filePath)
                .setFileName(fileName);
    }

    /**
     * 创建内部存储路径信息 Item
     * @param filePath 存储路径 ( 不包含文件名, 纯路径 ) 只会在内部存储时使用
     * @param fileName 存储文件名 ( 需携带后缀 )
     * @param folder   存储文件夹 ( 不包含完整路径, 就文件夹名, 不传则会存储在对应路径文件根目录 )
     * @return {@link StorageItem}
     */
    public static StorageItem createInternalItem(
            final String filePath,
            final String fileName,
            final String folder
    ) {
        return new StorageItem().setFilePath(filePath)
                .setFileName(fileName).setFolder(folder);
    }

    // ==========
    // = 外部存储 =
    // ==========

    /**
     * 创建外部存储路径信息 Item
     * @param fileName 存储文件名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType 资源类型
     * @return {@link StorageItem}
     */
    public static StorageItem createExternalItem(
            final String fileName,
            final String mimeType
    ) {
        return createExternalItem(
                fileName, mimeType,
                StorageType.getTypeRelativePath(mimeType)
        );
    }

    /**
     * 创建外部存储路径信息 Item
     * @param fileName 存储文件名 ( 无需后缀, 根据 mimeType 决定, 如果 mimeType 用了 xxx/* 则需指定后缀 )
     * @param mimeType 资源类型
     * @param folder   存储文件夹 ( 不包含完整路径, 就文件夹名, 不传则会存储在对应路径文件根目录 )
     * @return {@link StorageItem}
     */
    public static StorageItem createExternalItem(
            final String fileName,
            final String mimeType,
            final String folder
    ) {
        return new StorageItem().setFileName(fileName)
                .setMimeType(mimeType).setFolder(folder);
    }
}