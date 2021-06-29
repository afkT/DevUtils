package dev.engine.storage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.InputStream;

import dev.base.DevSource;
import dev.engine.storage.listener.OnInsertListener;
import dev.utils.app.MediaStoreUtils;
import dev.utils.app.UriUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.FileIOUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StreamUtils;
import dev.utils.common.StringUtils;

/**
 * detail: DevUtils MediaStore Engine 实现
 * @author Ttt
 * <pre>
 *     如果需要设置全局结果监听, 可以新增构造函数传入 {@link OnDevInsertListener}
 *     并在 {@link #finalCallback} 方法中设置触发事件回调
 * </pre>
 */
public class DevMediaStoreEngineImpl
        implements IStorageEngine<StorageItem, StorageResult> {

    // ==========
    // = 外部存储 =
    // ==========

    /**
     * 插入一张图片到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertImageToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.IMAGE)) {
            insideInsertToExternal(params, source, listener, StorageType.IMAGE);
        }
    }

    /**
     * 插入一条视频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertVideoToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.VIDEO)) {
            insideInsertToExternal(params, source, listener, StorageType.VIDEO);
        }
    }

    /**
     * 插入一条音频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertAudioToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.AUDIO)) {
            insideInsertToExternal(params, source, listener, StorageType.AUDIO);
        }
    }

    /**
     * 插入一条文件资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertDownloadToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.DOWNLOAD)) {
            insideInsertToExternal(params, source, listener, StorageType.DOWNLOAD);
        }
    }

    /**
     * 插入一条多媒体资源到外部存储空间 ( SDCard )
     * <pre>
     *     并不局限于多媒体, 如文本存储、其他文件写入等
     * </pre>
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertMediaToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, true, StorageType.NONE)) {
            insideInsertToExternal(params, source, listener, StorageType.NONE);
        }
    }

    // ==========
    // = 内部存储 =
    // ==========

    /**
     * 插入一张图片到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertImageToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.IMAGE)) {
            insideInsertToInternal(params, source, listener, StorageType.IMAGE);
        }
    }

    /**
     * 插入一条视频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertVideoToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.VIDEO)) {
            insideInsertToInternal(params, source, listener, StorageType.VIDEO);
        }
    }

    /**
     * 插入一条音频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertAudioToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.AUDIO)) {
            insideInsertToInternal(params, source, listener, StorageType.AUDIO);
        }
    }

    /**
     * 插入一条文件资源到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertDownloadToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.DOWNLOAD)) {
            insideInsertToInternal(params, source, listener, StorageType.DOWNLOAD);
        }
    }

    /**
     * 插入一条多媒体资源到内部存储空间
     * <pre>
     *     并不局限于多媒体, 如文本存储、其他文件写入等
     * </pre>
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertMediaToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener, false, StorageType.NONE)) {
            insideInsertToInternal(params, source, listener, StorageType.NONE);
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // ========
    // = Type =
    // ========

    /**
     * 当属于 NONE 类型则进行只能校验
     * @param params 原始参数
     * @param source 原始数据
     * @param type   存储类型
     * @return 输出 Uri
     */
    private StorageType convertType(
            final StorageItem params,
            final DevSource source,
            final StorageType type
    ) {
        if (type == StorageType.NONE) {
            if (params != null) {
                StorageType typeResult = StorageType.convertTypeByMimeType(
                        params.getMimeType()
                );
                if (!typeResult.isNone()) return typeResult;

                typeResult = StorageType.convertTypeByFileName(
                        params.getFileName()
                );
                if (!typeResult.isNone()) return typeResult;
            }
            // 其他未知都放到 Download 文件夹下
            return StorageType.DOWNLOAD;
        }
        return type;
    }

    /**
     * 通用内部预校验
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储类型
     * @return {@code true} 校验通过, {@code false} 校验失败
     */
    private boolean insidePreCheck(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final boolean external,
            final StorageType type

    ) {
        // 判断参数是否有效
        if (params != null && source != null && source.isSource()) {
            return true;
        }
        // 无效数据触发回调
        if (listener != null) {
            StorageResult result = StorageResult.failure()
                    .setExternal(external)
                    .setType(convertType(params, source, type));
            if (params == null) {
                result.setError(new Exception("params is null"));
            }
            if (source == null || !source.isSource()) {
                if (params == null) {
                    result.setError(new Exception("params、source is null"));
                } else {
                    result.setError(new Exception("source is null"));
                }
            }
            // 统一触发事件回调
            finalCallback(result, params, source, listener);
        }
        return false;
    }

    // ============
    // = Uri、File =
    // ============

    /**
     * 获取输出 Uri ( 存储文件 Uri )
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储类型
     * @return 输出 Uri
     */
    private Uri getOutputUri(
            final StorageItem params,
            final DevSource source,
            final boolean external,
            final StorageType type
    ) {
        if (params != null && source != null && source.isSource()) {
            if (params.getOutputUri() != null) {
                return params.getOutputUri();
            }
            // 外部存储才需要进行适配
            if (external) {
                // Android 9.0 及以下版本则直接使用 File 读写
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                    if (type == StorageType.DOWNLOAD) {
//                        // 低版本直接创建 SDCard/Download File
//                        File file = getOutputFile(params, source, external, type);
//                        return MediaStoreUtils.createUriByFile(file);
//                    }
                    // 目前属于 IMAGE、VIDEO、AUDIO、DOWNLOAD 低版本都通过 File 读写
                    // 如果需要 IMAGE、VIDEO、AUDIO 使用 MediaStore 则放开上面注释
                    // 低版本直接创建 SDCard/Pictures、DCIM、Music、Download File
                    File file = getOutputFile(params, source, external, type);
                    return MediaStoreUtils.createUriByFile(file);
                }

                switch (type) {
                    case IMAGE: // 存储到 Pictures 文件夹
                        // 创建 Image Uri
                        return MediaStoreUtils.createImageUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                    case VIDEO: // 存储到 DCIM 文件夹
                        // 创建 Video Uri
                        return MediaStoreUtils.createVideoUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                    case AUDIO: // 存储到 Music 文件夹
                        // 创建 Audio Uri
                        return MediaStoreUtils.createAudioUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                    case DOWNLOAD: // 存储到 Download 文件夹
                        // 创建 Download Uri
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            return MediaStoreUtils.createDownloadUri(
                                    params.getFileName(),
                                    params.getMimeType(),
                                    params.getFolder()
                            );
                        }
                }
            } else { // 内部存储路径
                File file = getOutputFile(params, source, external, type);
                // FileProvider File Path Uri
                return UriUtils.getUriForFile(file);
            }
        }
        return null;
    }

    /**
     * 获取输出文件路径
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储类型
     * @return 输出文件路径
     */
    private File getOutputFile(
            final StorageItem params,
            final DevSource source,
            final boolean external,
            final StorageType type
    ) {
        if (params != null && source != null && source.isSource()) {
            if (params.getOutputUri() != null) {
                String uriPath = UriUtils.getFilePathByUri(
                        params.getOutputUri()
                );
                if (uriPath != null) {
                    return FileUtils.getFile(uriPath);
                }
            }
            if (external) { // 外部存储路径
                switch (type) {
                    case IMAGE: // 存储到 Pictures 文件夹
                    case VIDEO: // 存储到 DCIM 文件夹
                    case AUDIO: // 存储到 Music 文件夹
                    case DOWNLOAD: // 存储到 Download 文件夹
                        // 需考虑 fileName 是否存在后缀情况 ( 如果 mimeType 用了 xxx/* 则需指定后缀 )
                        if (StringUtils.isNotEmpty(params.getMimeType())) {
                            // 属于 * 自动识别, 则 fileName 要求指定后缀 ( 直接拼接即可 )
                            if (params.getMimeType().contains("*")) {
                                // SDCard/folder/fileName
                                return params.getExternalFile();
                            } else {
                                // 进行获取文件后缀 ( 不含 . )
                                String extension = MediaStoreUtils.getExtensionFromMimeType(
                                        params.getMimeType()
                                );
                                // 存在后缀才进行拼接
                                if (StringUtils.isNotEmpty(extension)) {
                                    // fileName.extension ( 小写后缀 )
                                    String fileName = params.getFileName() + "." + extension.toLowerCase();
                                    // SDCard/folder/fileName.extension
                                    return params.getExternalFile(fileName);
                                }
                                // 无法获取到后缀, 可考虑进行拼接返回
                                // SDCard/folder/fileName
                                return params.getExternalFile();
                            }
                        }
                        break;
                }
            } else { // 内部存储路径
                return params.getInternalFile();
            }
        }
        return null;
    }

    // =================
    // = 内部插入数据方法 =
    // =================

    /**
     * 内部插入数据方法 ( 外部存储空间 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param type     存储类型
     */
    private void insideInsertToExternal(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final StorageType type
    ) {
        insideInsertThread(
                params, source, listener, true,
                convertType(params, source, type)
        );
    }

    /**
     * 内部插入数据方法 ( 内部存储空间 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param type     存储类型
     */
    private void insideInsertToInternal(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final StorageType type
    ) {
        insideInsertThread(
                params, source, listener, false,
                convertType(params, source, type)
        );
    }

    /**
     * 内部插入数据方法 ( 后台运行 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储类型
     */
    private void insideInsertThread(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final boolean external,
            final StorageType type
    ) {
        new Thread(() -> {
            try {
                insideInsertFinal(params, source, listener, external, type);
            } catch (Exception e) {
                // 统一触发事件回调
                finalCallback(
                        StorageResult.failure().setError(e),
                        params, source, listener
                );
            }
        }).start();
    }

    /**
     * 内部插入数据方法 ( 最终方法 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储类型
     */
    private void insideInsertFinal(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final boolean external,
            final StorageType type
    ) {
        // 获取输出 Uri
        Uri outputUri = getOutputUri(params, source, external, type);
        // 获取输出文件路径
        File outputFile = getOutputFile(params, source, external, type);

        // =============
        // = 数据类型判断 =
        // =============

        // 存储结果
        boolean insertResult = false;

        if (outputUri != null) {
            // 属于 Bitmap、Drawable
            if (source.isBitmap() || source.isDrawable()) {
                Bitmap bitmap = source.mBitmap;
                if (source.isDrawable()) {
                    bitmap = ImageUtils.drawableToBitmap(source.mDrawable);
                }
                insertResult = MediaStoreUtils.insertMedia(
                        outputUri, bitmap, params.getFormat(), params.getQuality()
                );
            }

            // 属于 byte[]、File、InputStream
            if (source.isBytes() || source.isFile() || source.isInputStream()) {
                InputStream inputStream = source.mInputStream;
                if (source.isBytes()) {
                    inputStream = StreamUtils.bytesToInputStream(source.mBytes);
                } else if (source.isFile()) {
                    inputStream = FileIOUtils.getFileInputStream(source.mFile);
                }
                insertResult = MediaStoreUtils.insertMedia(
                        outputUri, inputStream
                );
            }

            // 属于 Uri
            if (source.isUri()) {
                insertResult = MediaStoreUtils.insertMedia(
                        outputUri, source.mUri
                );
            }

            // url: 暂不处理 Url 文件 ( 涉及下载 ) 自行下载后传入 File
            // resource Id: 无法确认属于 drawable、raw、assets 等 id ( 自行传入 Bitmap、InputStream、byte[] )
            if (source.isUrl() || source.isResource()) {
            }
        }

        // ==========
        // = 结果回调 =
        // ==========

        StorageResult result = insertResult ? StorageResult.success() : StorageResult.failure();
        // 保存结果信息
        result.setUri(outputUri).setFile(outputFile)
                .setExternal(external).setType(type);
        // 输出 Uri 为 null, 则设置 Error
        if (outputUri == null) {
            result.setError(new Exception("outputUri is null"));
        }
        // 属于外部存储并且存储成功
        if (external && insertResult) {
            String uriPath = UriUtils.getFilePathByUri(outputUri);
            if (StringUtils.isNotEmpty(uriPath)) {
                result.setFile(FileUtils.getFile(uriPath));
            }
            // 通知相册
            MediaStoreUtils.notifyMediaStore(outputUri);
        }

        // 统一触发事件回调
        finalCallback(result, params, source, listener);
    }

    // ==========
    // = 回调方法 =
    // ==========

    /**
     * 最终回调方法
     * @param result   存储结果
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     */
    private void finalCallback(
            final StorageResult result,
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (listener != null) {
            listener.onResult(result, params, source);
        }
    }
}