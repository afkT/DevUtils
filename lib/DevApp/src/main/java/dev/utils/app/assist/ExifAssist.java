package dev.utils.app.assist;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;

import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 图片 EXIF 读写辅助类
 * @author Ttt
 * <pre>
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

    // =======
    // = get =
    // =======

    public int getAttributeInt(
            final String tag,
            final int defaultValue
    ) {
        if (isExifNull()) return defaultValue;
        try {
            return mExif.getAttributeInt(tag, defaultValue);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAttributeInt - %s", tag);
            return defaultValue;
        }
    }
}