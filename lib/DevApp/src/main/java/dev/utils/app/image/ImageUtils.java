package dev.utils.app.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import dev.utils.LogPrintUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileUtils;

/**
 * detail: Image ( Bitmap、Drawable 等 ) 工具类
 * @author Ttt
 * <pre>
 *     图片文件头标识信息
 *     @see <a href="https://mp.weixin.qq.com/s?__biz=MzA3NTYzODYzMg==&mid=2653578220&idx=1&sn=bdc57c640427984e240b19d8b9e10a15&chksm=84b3b1ebb3c438fdcbd446e9f28abd2713e685246efedfc133eb453ca41d08730dbf297ba1a4&scene=4#wechat_redirect"/>
 *     @see <a href="https://developers.google.com/speed/webp/docs/riff_container"/>
 *     各类文件的文件头标志
 *     @see <a href="https://www.garykessler.net/library/file_sigs.html"/>
 *     @see <a href="https://blog.csdn.net/feixi7358/article/details/87712812"/>
 *     @see <a href="http://www.manongjc.com/article/56456.html"/>
 * </pre>
 */
public final class ImageUtils {

    private ImageUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageUtils.class.getSimpleName();

    /**
     * 判断 Bitmap 对象是否为 null
     * @param bitmap {@link Bitmap}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Bitmap bitmap) {
        return bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0;
    }

    /**
     * 判断 Bitmap 对象是否不为 null
     * @param bitmap {@link Bitmap}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Bitmap bitmap) {
        return bitmap != null && bitmap.getWidth() != 0 && bitmap.getHeight() != 0;
    }

    // =

    /**
     * 判断 Drawable 对象是否为 null
     * @param drawable {@link Drawable}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Drawable drawable) {
        return drawable == null;
    }

    /**
     * 判断 Drawable 对象是否不为 null
     * @param drawable {@link Drawable}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Drawable drawable) {
        return drawable != null;
    }

    // ===============
    // = 图片类型判断 =
    // ===============

    /**
     * 根据文件名判断文件是否为图片
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImageFormats(final File file) {
        return FileUtils.isImageFormats(file);
    }

    /**
     * 根据文件名判断文件是否为图片
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImageFormats(final String filePath) {
        return FileUtils.isImageFormats(filePath);
    }

    /**
     * 根据文件名判断文件是否为图片
     * @param filePath     文件路径
     * @param imageFormats 图片格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImageFormats(
            final String filePath,
            final String[] imageFormats
    ) {
        return FileUtils.isImageFormats(filePath, imageFormats);
    }

    // =

    /**
     * detail: 图片类型
     * @author Ttt
     */
    public enum ImageType {

        TYPE_PNG("png"),

        TYPE_JPG("jpg"),

        TYPE_BMP("bmp"),

        TYPE_GIF("gif"),

        TYPE_WEBP("webp"),

        TYPE_ICO("ico"),

        TYPE_TIFF("tiff"),

        TYPE_UNKNOWN("unknown");

        private final String value;

        ImageType(String value) {
            this.value = value;
        }

        /**
         * 获取图片类型字符串
         * @return 图片类型字符串
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * 获取图片类型
     * @param filePath 文件路径
     * @return {@link ImageType} 图片类型
     */
    public static ImageType getImageType(final String filePath) {
        return getImageType(FileUtils.getFileByPath(filePath));
    }

    /**
     * 获取图片类型
     * @param file 文件
     * @return {@link ImageType} 图片类型
     */
    public static ImageType getImageType(final File file) {
        if (file == null) return ImageType.TYPE_UNKNOWN;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getImageType(is);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageType");
            return ImageType.TYPE_UNKNOWN;
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
    }

    /**
     * 获取图片类型
     * @param inputStream {@link InputStream}
     * @return {@link ImageType} 图片类型
     */
    public static ImageType getImageType(final InputStream inputStream) {
        if (inputStream == null) return ImageType.TYPE_UNKNOWN;
        try {
            byte[] bytes = new byte[12];
            return inputStream.read(bytes, 0, 12) != -1 ? getImageType(bytes) : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageType");
            return ImageType.TYPE_UNKNOWN;
        }
    }

    /**
     * 获取图片类型
     * @param data 图片 byte[]
     * @return {@link ImageType} 图片类型
     */
    public static ImageType getImageType(final byte[] data) {
        if (isPNG(data)) return ImageType.TYPE_PNG;
        if (isJPEG(data)) return ImageType.TYPE_JPG;
        if (isBMP(data)) return ImageType.TYPE_BMP;
        if (isGif(data)) return ImageType.TYPE_GIF;
        if (isWEBP(data)) return ImageType.TYPE_WEBP;
        if (isICO(data)) return ImageType.TYPE_ICO;
        if (isTIFF(data)) return ImageType.TYPE_TIFF;
        return ImageType.TYPE_UNKNOWN;
    }

    // =

    /**
     * 判断是否 PNG 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPNG(final byte[] data) {
        return data != null && data.length >= 8
                && (data[0] == (byte) 137 && data[1] == (byte) 80
                && data[2] == (byte) 78 && data[3] == (byte) 71
                && data[4] == (byte) 13 && data[5] == (byte) 10
                && data[6] == (byte) 26 && data[7] == (byte) 10);
    }

    /**
     * 判断是否 JPG 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJPEG(final byte[] data) {
        return data != null && data.length >= 2 && (data[0] == (byte) 0xFF) && (data[1] == (byte) 0xD8);
    }

    /**
     * 判断是否 BMP 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBMP(final byte[] data) {
        return data != null && data.length >= 2 && (data[0] == (byte) 0x42) && (data[1] == (byte) 0x4d);
    }

    /**
     * 判断是否 GIF 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isGif(final byte[] data) {
        return data != null && data.length >= 6 && data[0] == 'G' && data[1] == 'I' && data[2] == 'F'
                && data[3] == '8' && (data[4] == '7' || data[4] == '9') && data[5] == 'a';
    }

    /**
     * 判断是否 WEBP 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWEBP(final byte[] data) {
        return data != null && data.length >= 12 && data[0] == 'R' && data[1] == 'I' && data[2] == 'F' && data[3] == 'F'
                && data[8] == 'W' && (data[9] == 'E' || data[10] == 'B') && data[11] == 'P';
    }

    /**
     * 判断是否 ICO 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isICO(final byte[] data) {
        return data != null && data.length >= 4 && data[0] == 0 && data[1] == 0 && data[2] == 1 && data[3] == 0;
    }

    /**
     * 判断是否 TIFF 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isTIFF(final byte[] data) {
        if (data != null && data.length >= 4) {
            if (data[0] == (byte) 73 && data[1] == (byte) 73 && data[2] == (byte) 0x2a && data[3] == 0) {
                return true; // 49 49 2a 00
            } else if (data[0] == (byte) 0x4d && data[1] == (byte) 0x4d && data[2] == 0 && data[3] == (byte) 0x2a) {
                return true; // 4d 4d 00 2a
            } else if (data[0] == (byte) 0x4d && data[1] == (byte) 0x4d && data[2] == 0 && data[3] == (byte) 0x2b) {
                return true; // 4d 4d 00 2b
            } else {
                return data[0] == (byte) 73 && data[1] == (byte) 32 && data[2] == (byte) 73; // 49 20 49
            }
        }
        return false;
    }

    // ===========
    // = 本地获取 =
    // ===========

    /**
     * 获取 Bitmap
     * @param file 文件
     * @return {@link Bitmap}
     */
    public static Bitmap decodeFile(final File file) {
        return decodeFile(FileUtils.getAbsolutePath(file), null);
    }

    /**
     * 获取 Bitmap
     * @param file    文件
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeFile(
            final File file,
            final BitmapFactory.Options options
    ) {
        return decodeFile(FileUtils.getAbsolutePath(file), options);
    }

    /**
     * 获取 Bitmap
     * @param filePath 文件路径
     * @return {@link Bitmap}
     */
    public static Bitmap decodeFile(final String filePath) {
        return decodeFile(filePath, null);
    }

    /**
     * 获取 Bitmap
     * @param filePath 文件路径
     * @param options  {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeFile(
            final String filePath,
            final BitmapFactory.Options options
    ) {
        if (filePath == null) return null;
        try {
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "decodeFile");
            return null;
        }
    }

    // =

    /**
     * 获取 Bitmap
     * @param resId resource identifier
     * @return {@link Bitmap}
     */
    public static Bitmap decodeResource(@DrawableRes final int resId) {
        return decodeResource(resId, null);
    }

    /**
     * 获取 Bitmap
     * @param resId   resource identifier
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeResource(
            @DrawableRes final int resId,
            final BitmapFactory.Options options
    ) {
        try {
            return BitmapFactory.decodeResource(ResourceUtils.getResources(), resId, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "decodeResource");
            return null;
        }
    }

    // =

    /**
     * 获取 Bitmap
     * @param inputStream {@link InputStream}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeStream(final InputStream inputStream) {
        return decodeStream(inputStream, null);
    }

    /**
     * 获取 Bitmap
     * @param inputStream {@link InputStream}
     * @param options     {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeStream(
            final InputStream inputStream,
            final BitmapFactory.Options options
    ) {
        if (inputStream == null) return null;
        try {
            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "decodeStream");
            return null;
        }
    }

    // =

    /**
     * 获取 Bitmap
     * @param fd 文件描述
     * @return {@link Bitmap}
     */
    public static Bitmap decodeFileDescriptor(final FileDescriptor fd) {
        return decodeFileDescriptor(fd, null);
    }

    /**
     * 获取 Bitmap
     * @param fd      文件描述
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeFileDescriptor(
            final FileDescriptor fd,
            final BitmapFactory.Options options
    ) {
        if (fd == null) return null;
        try {
            return BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "decodeFileDescriptor");
            return null;
        }
    }

    // =

    /**
     * 获取 Bitmap
     * @param data byte[]
     * @return {@link Bitmap}
     */
    public static Bitmap decodeByteArray(final byte[] data) {
        return decodeByteArray(data, 0, (data == null) ? 0 : data.length, null);
    }

    /**
     * 获取 Bitmap
     * @param data    byte[]
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeByteArray(
            final byte[] data,
            final BitmapFactory.Options options
    ) {
        return decodeByteArray(data, 0, (data == null) ? 0 : data.length, options);
    }

    /**
     * 获取 Bitmap
     * @param data   byte[]
     * @param offset 偏移量
     * @param length 所需长度
     * @return {@link Bitmap}
     */
    public static Bitmap decodeByteArray(
            final byte[] data,
            final int offset,
            final int length
    ) {
        return decodeByteArray(data, offset, length, null);
    }

    /**
     * 获取 Bitmap
     * @param data    byte[]
     * @param offset  偏移量
     * @param length  所需长度
     * @param options {@link BitmapFactory.Options}
     * @return {@link Bitmap}
     */
    public static Bitmap decodeByteArray(
            final byte[] data,
            final int offset,
            final int length,
            final BitmapFactory.Options options
    ) {
        if (data == null) return null;
        if ((offset | length) < 0 || data.length < offset + length) return null;
        try {
            return BitmapFactory.decodeByteArray(data, offset, length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "decodeByteArray");
            return null;
        }
    }

    // ===========
    // = 本地保存 =
    // ===========

    /**
     * 保存图片到 SDCard ( JPEG )
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardJPEG(
            final Bitmap bitmap,
            final String filePath
    ) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.JPEG, 100);
    }

    /**
     * 保存图片到 SDCard ( JPEG )
     * @param bitmap 待保存图片
     * @param file   保存路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardJPEG(
            final Bitmap bitmap,
            final File file
    ) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.JPEG, 100);
    }

    // =

    /**
     * 保存图片到 SDCard ( JPEG )
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardJPEG(
            final Bitmap bitmap,
            final String filePath,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * 保存图片到 SDCard ( JPEG )
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardJPEG(
            final Bitmap bitmap,
            final File file,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.JPEG, quality);
    }

    // =

    /**
     * 保存图片到 SDCard ( PNG )
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardPNG(
            final Bitmap bitmap,
            final String filePath
    ) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * 保存图片到 SDCard ( PNG )
     * @param bitmap 待保存图片
     * @param file   保存路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardPNG(
            final Bitmap bitmap,
            final File file
    ) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.PNG, 100);
    }

    // =

    /**
     * 保存图片到 SDCard ( PNG )
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardPNG(
            final Bitmap bitmap,
            final String filePath,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.PNG, quality);
    }

    /**
     * 保存图片到 SDCard ( PNG )
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardPNG(
            final Bitmap bitmap,
            final File file,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.PNG, quality);
    }

    // =

    /**
     * 保存图片到 SDCard ( WEBP )
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardWEBP(
            final Bitmap bitmap,
            final String filePath
    ) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.WEBP, 100);
    }

    /**
     * 保存图片到 SDCard ( WEBP )
     * @param bitmap 待保存图片
     * @param file   保存路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardWEBP(
            final Bitmap bitmap,
            final File file
    ) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.WEBP, 100);
    }

    // =

    /**
     * 保存图片到 SDCard ( WEBP )
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardWEBP(
            final Bitmap bitmap,
            final String filePath,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, filePath, Bitmap.CompressFormat.WEBP, quality);
    }

    /**
     * 保存图片到 SDCard ( WEBP )
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCardWEBP(
            final Bitmap bitmap,
            final File file,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, file, Bitmap.CompressFormat.WEBP, quality);
    }

    // =

    /**
     * 保存图片到 SDCard
     * @param bitmap   待保存图片
     * @param filePath 保存路径
     * @param format   如 Bitmap.CompressFormat.PNG
     * @param quality  质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCard(
            final Bitmap bitmap,
            final String filePath,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToSDCard(bitmap, FileUtils.getFileByPath(filePath), format, quality);
    }

    /**
     * 保存图片到 SDCard
     * @param bitmap  待保存图片
     * @param file    保存路径
     * @param format  如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToSDCard(
            final Bitmap bitmap,
            final File file,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        if (bitmap == null || file == null || format == null) return false;
        // 防止 Bitmap 为 null, 或者创建文件夹失败 ( 文件存在则删除 )
        if (isEmpty(bitmap) || !FileUtils.createFileByDeleteOldFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            if (os != null) {
                bitmap.compress(format, quality, os);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveBitmapToSDCard");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(os);
        }
        return true;
    }

    // ================
    // = OutputStream =
    // ================

    /**
     * 保存 JPEG 图片
     * @param bitmap 待保存图片
     * @param stream {@link OutputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStreamJPEG(
            final Bitmap bitmap,
            final OutputStream stream
    ) {
        return saveBitmapToStream(bitmap, stream, Bitmap.CompressFormat.JPEG, 100);
    }

    /**
     * 保存 JPEG 图片
     * @param bitmap  待保存图片
     * @param stream  {@link OutputStream}
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStreamJPEG(
            final Bitmap bitmap,
            final OutputStream stream,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToStream(bitmap, stream, Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * 保存 PNG 图片
     * @param bitmap 待保存图片
     * @param stream {@link OutputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStreamPNG(
            final Bitmap bitmap,
            final OutputStream stream
    ) {
        return saveBitmapToStream(bitmap, stream, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * 保存 PNG 图片
     * @param bitmap  待保存图片
     * @param stream  {@link OutputStream}
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStreamPNG(
            final Bitmap bitmap,
            final OutputStream stream,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToStream(bitmap, stream, Bitmap.CompressFormat.PNG, quality);
    }

    /**
     * 保存 WEBP 图片
     * @param bitmap 待保存图片
     * @param stream {@link OutputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStreamWEBP(
            final Bitmap bitmap,
            final OutputStream stream
    ) {
        return saveBitmapToStream(bitmap, stream, Bitmap.CompressFormat.WEBP, 100);
    }

    /**
     * 保存 WEBP 图片
     * @param bitmap  待保存图片
     * @param stream  {@link OutputStream}
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStreamWEBP(
            final Bitmap bitmap,
            final OutputStream stream,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return saveBitmapToStream(bitmap, stream, Bitmap.CompressFormat.WEBP, quality);
    }

    /**
     * 保存图片
     * @param bitmap  待保存图片
     * @param stream  {@link OutputStream}
     * @param format  如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveBitmapToStream(
            final Bitmap bitmap,
            final OutputStream stream,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        if (bitmap == null || stream == null || format == null) return false;
        try {
            bitmap.compress(format, quality, stream);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveBitmapToStream");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(stream);
        }
        return true;
    }

    // ============
    // = Drawable =
    // ============

    /**
     * 获取 .9 Drawable
     * <pre>
     *     .9 图片如果需要着色, 需要传入 NinePatchDrawable
     *     setColorFilter(get9PatchDrawable(drawable), color);
     * </pre>
     * @param drawable {@link Drawable}
     * @return .9 {@link NinePatchDrawable}
     */
    public static NinePatchDrawable get9PatchDrawable(final Drawable drawable) {
        try {
            return (NinePatchDrawable) drawable;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "get9PatchDrawable");
        }
        return null;
    }

    /**
     * 图片着色 ( tint )
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @return 着色后的 {@link Drawable}
     */
    public static Drawable setColorFilter(
            final Drawable drawable,
            @ColorInt final int color
    ) {
        return setColorFilter(drawable, color, PorterDuff.Mode.SRC_IN);
    }

    /**
     * 图片着色 ( tint )
     * @param drawable {@link Drawable}
     * @param color    颜色值
     * @param mode     着色模式 {@link PorterDuff.Mode}
     * @return 着色后的 {@link Drawable}
     */
    public static Drawable setColorFilter(
            final Drawable drawable,
            @ColorInt final int color,
            final PorterDuff.Mode mode
    ) {
        if (drawable != null && mode != null) {
            try {
                drawable.setColorFilter(color, mode);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setColorFilter");
            }
        }
        return drawable;
    }

    // =

    /**
     * 图片着色 ( tint )
     * @param drawable    {@link Drawable}
     * @param colorFilter 颜色过滤 ( 效果 )
     * @return 着色后的 {@link Drawable}
     */
    public static Drawable setColorFilter(
            final Drawable drawable,
            final ColorFilter colorFilter
    ) {
        if (drawable != null && colorFilter != null) {
            try {
                drawable.setColorFilter(colorFilter);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setColorFilter");
            }
        }
        return drawable;
    }

    // ==========
    // = Bitmap =
    // ==========

    /**
     * 获取 Bitmap
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final File file,
            final int maxWidth,
            final int maxHeight
    ) {
        return getBitmap(FileUtils.getAbsolutePath(file), maxWidth, maxHeight);
    }

    /**
     * 获取 Bitmap
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final String filePath,
            final int maxWidth,
            final int maxHeight
    ) {
        if (filePath == null) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
            return null;
        }
    }

    /**
     * 获取 Bitmap
     * @param resId     resource identifier
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            @DrawableRes final int resId,
            final int maxWidth,
            final int maxHeight
    ) {
        try {
            Resources             resources = ResourceUtils.getResources();
            BitmapFactory.Options options   = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, resId, options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(resources, resId, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
            return null;
        }
    }

    /**
     * 获取 Bitmap
     * @param inputStream {@link InputStream}
     * @param maxWidth    最大宽度
     * @param maxHeight   最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final InputStream inputStream,
            final int maxWidth,
            final int maxHeight
    ) {
        if (inputStream == null) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
            return null;
        }
    }

    /**
     * 获取 Bitmap
     * @param fd        文件描述
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final FileDescriptor fd,
            final int maxWidth,
            final int maxHeight
    ) {
        if (fd == null) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
            return null;
        }
    }

    /**
     * 获取 Bitmap
     * @param data      byte[]
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmap(
            final byte[] data,
            final int maxWidth,
            final int maxHeight
    ) {
        if (data == null) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmap");
            return null;
        }
    }

    // =

    /**
     * 通过 View 绘制为 Bitmap
     * @param view {@link View}
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmapFromView(final View view) {
        if (view == null) return null;
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromView");
        }
        return null;
    }

    /**
     * 通过 View Cache 绘制为 Bitmap
     * @param view {@link View}
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmapFromViewCache(final View view) {
        if (view == null) return null;
        try {
            // 清除视图焦点
            view.clearFocus();
            // 将视图设为不可点击
            view.setPressed(false);

            // 获取视图是否可以保存画图缓存
            boolean willNotCache = view.willNotCacheDrawing();
            view.setWillNotCacheDrawing(false);

            // 获取绘制缓存位图的背景颜色
            int color = view.getDrawingCacheBackgroundColor();
            // 设置绘图背景颜色
            view.setDrawingCacheBackgroundColor(0);
            if (color != 0) { // 获取的背景不是黑色的则释放以前的绘图缓存
                view.destroyDrawingCache(); // 释放绘图资源所使用的缓存
            }

            // 重新创建绘图缓存, 此时的背景色是黑色
            view.buildDrawingCache();
            // 获取绘图缓存, 注意这里得到的只是一个图像的引用
            Bitmap cacheBitmap = view.getDrawingCache();
            if (cacheBitmap == null) return null;

            Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
            // 释放位图内存
            view.destroyDrawingCache();
            // 回滚以前的缓存设置、缓存颜色设置
            view.setWillNotCacheDrawing(willNotCache);
            view.setDrawingCacheBackgroundColor(color);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromViewCache");
        }
        return null;
    }

    // =========================
    // = Bitmap、Drawable 转换 =
    // =========================

    // ===============
    // = 转为 byte[] =
    // ===============

    /**
     * Bitmap 转换成 byte[]
     * @param bitmap 待转换图片
     * @return byte[]
     */
    public static byte[] bitmapToByte(final Bitmap bitmap) {
        return bitmapToByte(bitmap, 100, Bitmap.CompressFormat.PNG);
    }

    /**
     * Bitmap 转换成 byte[]
     * @param bitmap 待转换图片
     * @param format 如 Bitmap.CompressFormat.PNG
     * @return byte[]
     */
    public static byte[] bitmapToByte(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format
    ) {
        return bitmapToByte(bitmap, 100, format);
    }

    /**
     * Bitmap 转换成 byte[]
     * @param bitmap  待转换图片
     * @param quality 质量
     * @param format  如 Bitmap.CompressFormat.PNG
     * @return byte[]
     */
    public static byte[] bitmapToByte(
            final Bitmap bitmap,
            @IntRange(from = 0, to = 100) final int quality,
            final Bitmap.CompressFormat format
    ) {
        if (bitmap == null || format == null) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, quality, baos);
            return baos.toByteArray();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bitmapToByte");
        }
        return null;
    }

    // =

    /**
     * Drawable 转换成 byte[]
     * @param drawable 待转换图片
     * @return byte[]
     */
    public static byte[] drawableToByte(final Drawable drawable) {
        return drawableToByte(drawable, 100, Bitmap.CompressFormat.PNG);
    }

    /**
     * Drawable 转换成 byte[]
     * @param drawable 待转换图片
     * @param format   如 Bitmap.CompressFormat.PNG
     * @return byte[]
     */
    public static byte[] drawableToByte(
            final Drawable drawable,
            final Bitmap.CompressFormat format
    ) {
        return drawableToByte(drawable, 100, format);
    }

    /**
     * Drawable 转换成 byte[]
     * @param drawable 待转换图片
     * @param quality  质量
     * @param format   如 Bitmap.CompressFormat.PNG
     * @return byte[]
     */
    public static byte[] drawableToByte(
            final Drawable drawable,
            @IntRange(from = 0, to = 100) final int quality,
            final Bitmap.CompressFormat format
    ) {
        if (drawable == null || format == null) return null;
        return bitmapToByte(drawableToBitmap(drawable), quality, format);
    }

    // ==========
    // = Bitmap =
    // ==========

    /**
     * byte[] 转 Bitmap
     * @param data byte[]
     * @return {@link Bitmap}
     */
    public static Bitmap byteToBitmap(final byte[] data) {
        return decodeByteArray(data);
    }

    /**
     * Bitmap 转 Drawable
     * @param bitmap 待转换图片
     * @return {@link Drawable}
     */
    public static Drawable bitmapToDrawable(final Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            return new BitmapDrawable(ResourceUtils.getResources(), bitmap);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "bitmapToDrawable");
        }
        return null;
    }

    // ============
    // = Drawable =
    // ============

    /**
     * byte[] 转 Drawable
     * @param data byte[]
     * @return {@link Drawable}
     */
    public static Drawable byteToDrawable(final byte[] data) {
        return bitmapToDrawable(decodeByteArray(data));
    }

    /**
     * Drawable 转 Bitmap
     * @param drawable 待转换图片
     * @return {@link Bitmap}
     */
    public static Bitmap drawableToBitmap(final Drawable drawable) {
        if (drawable == null) return null;
        // 属于 BitmapDrawable 直接转换
        if (drawable instanceof BitmapDrawable) {
            try {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "drawableToBitmap - BitmapDrawable");
            }
        }
        try {
            // 获取 drawable 的宽高
            int width  = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // 获取 drawable 的颜色格式
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            // 创建 bitmap
            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            // 创建 bitmap 画布
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            // 把 drawable 内容画到画布中
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "drawableToBitmap");
        }
        return null;
    }

    // =

    /**
     * 设置 Drawable 绘制区域
     * @param drawable {@link Drawable}
     * @return {@link Drawable}
     */
    public static Drawable setBounds(final Drawable drawable) {
        try {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setBounds");
        }
        return drawable;
    }

    /**
     * 设置 Drawable 绘制区域
     * @param drawable {@link Drawable}
     * @param right    right 坐标
     * @param bottom   bottom 坐标
     * @return {@link Drawable}
     */
    public static Drawable setBounds(
            final Drawable drawable,
            final int right,
            final int bottom
    ) {
        return setBounds(drawable, 0, 0, right, bottom);
    }

    /**
     * 设置 Drawable 绘制区域
     * @param drawable {@link Drawable}
     * @param left     left 坐标
     * @param top      top 坐标
     * @param right    right 坐标
     * @param bottom   bottom 坐标
     * @return {@link Drawable}
     */
    public static Drawable setBounds(
            final Drawable drawable,
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        try {
            drawable.setBounds(left, top, right, bottom);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setBounds");
        }
        return drawable;
    }
}