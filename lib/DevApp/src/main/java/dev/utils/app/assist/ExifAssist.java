package dev.utils.app.assist;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;

import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 图片 EXIF 读写辅助类
 * @author Ttt
 * <pre>
 *     源码部分方法是不会抛出异常, 也统一进行 try-catch 防止后续库更新迭代代码变动
 *     <p></p>
 *     Supported for reading: JPEG, PNG, WebP, HEIF, DNG, CR2, NEF, NRW, ARW, RW2, ORF, PEF, SRW, RAF
 *     Supported for writing: JPEG, PNG, WebP, DNG
 * </pre>
 */
public class ExifAssist {

    // 日志 TAG
    private final String TAG = ExifAssist.class.getSimpleName();

    // 图片 EXIF 操作接口
    private final ExifInterface mExif;
    // 是否 EXIF 初始化异常
    private       Throwable     mExifError;

    // ==========
    // = 构造函数 =
    // ==========

    private ExifAssist(
            final ExifInterface exif,
            final Throwable exifError
    ) {
        mExif      = exif;
        mExifError = exifError;
    }

    private ExifAssist(final File file) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(file);
        } catch (Throwable e) {
            LogPrintUtils.eTag(
                    TAG, e, "ExifAssist - File %s",
                    FileUtils.getAbsolutePath(file)
            );
            mExifError = e;
        }
        mExif = exif;
    }

    private ExifAssist(final String fileName) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(fileName);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "ExifAssist - FileName %s", fileName);
            mExifError = e;
        }
        mExif = exif;
    }

    private ExifAssist(final FileDescriptor fd) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(fd);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "ExifAssist - FileDescriptor");
            mExifError = e;
        }
        mExif = exif;
    }

    private ExifAssist(final InputStream inputStream) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(inputStream);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "ExifAssist - InputStream");
            mExifError = e;
        }
        mExif = exif;
    }

    private ExifAssist(
            final InputStream inputStream,
            @ExifInterface.ExifStreamType final int streamType
    ) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(inputStream, streamType);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "ExifAssist - InputStream, StreamType");
            mExifError = e;
        }
        mExif = exif;
    }

    // ==========
    // = 静态方法 =
    // ==========

    public static ExifAssist get(final ExifInterface exif) {
        return new ExifAssist(exif, null);
    }

    public static ExifAssist get(final File file) {
        return new ExifAssist(file);
    }

    public static ExifAssist get(final String fileName) {
        return new ExifAssist(fileName);
    }

    public static ExifAssist get(final FileDescriptor fd) {
        return new ExifAssist(fd);
    }

    public static ExifAssist get(final InputStream inputStream) {
        return new ExifAssist(inputStream);
    }

    public static ExifAssist get(
            final InputStream inputStream,
            @ExifInterface.ExifStreamType final int streamType
    ) {
        return new ExifAssist(inputStream, streamType);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 克隆图片 EXIF 读写信息
     * @return {@link ExifAssist}
     */
    public ExifAssist clone() {
        return new ExifAssist(mExif, mExifError);
    }

    /**
     * 获取图片 EXIF 操作接口
     * @return {@link ExifInterface}
     */
    public ExifInterface getExif() {
        return mExif;
    }

    /**
     * 获取 EXIF 初始化异常信息
     * @return {@link Throwable}
     */
    public Throwable getExifError() {
        return mExifError;
    }

    // =

    /**
     * 是否图片 EXIF 为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExifNull() {
        return mExif == null;
    }

    /**
     * 是否图片 EXIF 不为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExifNotNull() {
        return mExif != null;
    }

    /**
     * 是否 EXIF 初始化异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isExifError() {
        return mExifError != null;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 根据 TAG 获取对应值
     * @param tag          TAG 标签
     * @param defaultValue 默认值
     * @return TAG 对应值
     */
    public int getAttributeInt(
            final String tag,
            final int defaultValue
    ) {
        if (StringUtils.isEmpty(tag)) return defaultValue;
        if (isExifNull()) return defaultValue;
        try {
            return mExif.getAttributeInt(tag, defaultValue);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAttributeInt - %s", tag);
            return defaultValue;
        }
    }

    /**
     * 根据 TAG 获取对应值
     * @param tag          TAG 标签
     * @param defaultValue 默认值
     * @return TAG 对应值
     */
    public double getAttributeDouble(
            final String tag,
            final double defaultValue
    ) {
        if (StringUtils.isEmpty(tag)) return defaultValue;
        if (isExifNull()) return defaultValue;
        try {
            return mExif.getAttributeDouble(tag, defaultValue);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAttributeDouble - %s", tag);
            return defaultValue;
        }
    }

    /**
     * 根据 TAG 获取对应值
     * @param tag TAG 标签
     * @return TAG 对应值
     */
    public String getAttribute(final String tag) {
        if (StringUtils.isEmpty(tag)) return null;
        if (isExifNull()) return null;
        try {
            return mExif.getAttribute(tag);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAttribute - %s", tag);
            return null;
        }
    }

    /**
     * 根据 TAG 获取对应值
     * @param tag TAG 标签
     * @return TAG 对应值
     */
    public byte[] getAttributeBytes(final String tag) {
        if (StringUtils.isEmpty(tag)) return null;
        if (isExifNull()) return null;
        try {
            return mExif.getAttributeBytes(tag);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAttributeBytes - %s", tag);
            return null;
        }
    }

    /**
     * 根据 TAG 获取对应值
     * @param tag TAG 标签
     * @return TAG 对应值
     */
    public long[] getAttributeRange(final String tag) {
        if (StringUtils.isEmpty(tag)) return null;
        if (isExifNull()) return null;
        try {
            return mExif.getAttributeRange(tag);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAttributeRange - %s", tag);
            return null;
        }
    }

    /**
     * 是否存在指定 TAG 值
     * @param tag TAG 标签
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasAttribute(final String tag) {
        if (StringUtils.isEmpty(tag)) return false;
        if (isExifNull()) return false;
        try {
            return mExif.hasAttribute(tag);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hasAttribute - %s", tag);
            return false;
        }
    }

    /**
     * 设置对应 TAG 值
     * @param tag   TAG 标签
     * @param value 待设置值
     * @return {@code true} success, {@code false} fail
     */
    public boolean setAttribute(
            final String tag,
            final String value
    ) {
        if (StringUtils.isEmpty(tag)) return false;
        if (isExifNull()) return false;
        try {
            mExif.setAttribute(tag, value);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setAttribute - %s : %s", tag, value);
            return false;
        }
    }

    /**
     * 将标签数据存储到图片中 ( 最终必须调用 )
     * <pre>
     *     Supported for writing: JPEG, PNG, WebP, DNG
     *     应该是全部设置完成后统一保存, 避免设置一次值调用一次
     * </pre>
     * @return {@code true} success, {@code false} fail
     */
    public boolean saveAttributes() {
        if (isExifNull()) return false;
        try {
            mExif.saveAttributes();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveAttributes");
            return false;
        }
    }

    // =======
    // = get =
    // =======
}