package dev.engine.storage;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * detail: Storage Item Params
 * @author Ttt
 * <pre>
 *     {@link #mFolder} 只需要文件夹名字
 *     是因为 Android 适配 {@link android.provider.MediaStore} 传入 RELATIVE_PATH 使用
 *     <p></p>
 *     {@link #mFilePath} 只会在内部存储时使用
 *     属于内部存储时, 完整路径为 filePath + folder + fileName
 * </pre>
 */
public class StorageItem
        extends IStorageEngine.EngineItem {

    // =======
    // = 通用 =
    // =======

    // 存储文件名
    private String mFileName;
    // 存储文件夹 ( 不包含完整路径, 就文件夹名, 不传则会存储在对应路径文件根目录 )
    private String mFolder; // 可以传入, 如: /Dev/Material
    // 存储路径 ( 存储路径 => filePath + folder + fileName )
    private String mFilePath;
    // 资源类型
    private String mMimeType;

    // 输出 Uri
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
}