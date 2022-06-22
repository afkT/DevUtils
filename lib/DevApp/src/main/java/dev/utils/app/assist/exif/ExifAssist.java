package dev.utils.app.assist.exif;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dev.utils.LogPrintUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 图片 EXIF 读写辅助类
 * @author Ttt
 * <pre>
 *     Supported for reading: JPEG, PNG, WebP, HEIF, DNG, CR2, NEF, NRW, ARW, RW2, ORF, PEF, SRW, RAF
 *     Supported for writing: JPEG, PNG, WebP, DNG
 *     <p></p>
 *     需要注意的是:
 *     支持写入变更的只有上述 Supported for writing 类型, 如想要操作全部格式应使用其他库封装实现
 *     该辅助类使用 Android 官方 API 不依赖任何第三方库
 *     <p></p>
 *     源码部分方法是不会抛出异常, 也统一进行 try-catch 防止后续库更新迭代代码变动
 *     <p></p>
 *     如果需要获取敏感信息, 如图片位置信息则
 *     需要先申请权限 {@link Manifest.permission#ACCESS_MEDIA_LOCATION} 允许后
 *     再调用 {@link MediaStore#setRequireOriginal} 获取原始 Uri
 *     最后使用 {@link ResourceUtils#openFileDescriptor(Uri, String)} 返回 ParcelFileDescriptor
 *     再通过 ParcelFileDescriptor.getFileDescriptor() 进行创建 ExifAssist
 *     <p></p>
 *     注意事项:
 *     为什么不通过 {@link ResourceUtils#openInputStream(Uri)} 返回 InputStream 进行创建 ExifAssist
 *     是因为通过 InputStream 创建 ExifAssist 进行 saveAttributes 会抛出异常
 *     throw new IOException("Failed to save new file. Original file is stored in")
 *     Write failed: EBADF (Bad file descriptor)
 *     <p></p>
 *     已提供 {@link ExifAssist#getByRequire(Uri)} 进行创建 ( 在申请权限成功后直接通过该方法创建即可 )
 *     或通过 {@link ExifAssist#requireOriginal(Uri)} 申请获取原始 Uri
 *     <p></p>
 *     申请权限方法已封装 {@link ExifAssist#requestPermission(Activity, PermissionUtils.PermissionCallback)}
 *     在 Callback onGranted() 方法中调用 {@link ExifAssist#getByRequire(Uri)} 即可
 *     以上所有方法都已进行版本适配处理直接调用无需额外逻辑判断
 * </pre>
 */
public final class ExifAssist {

    // 日志 TAG
    private static final String TAG = ExifAssist.class.getSimpleName();

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

    private ExifAssist(final String filePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "ExifAssist - FilePath %s", filePath);
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

    public static ExifAssist get(final String filePath) {
        return new ExifAssist(filePath);
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

    public static ExifAssist get(final Uri uri) {
        ParcelFileDescriptor pfd            = ResourceUtils.openFileDescriptor(uri, "rw");
        FileDescriptor       fileDescriptor = null;
        if (pfd != null) fileDescriptor = pfd.getFileDescriptor();
        return new ExifAssist(fileDescriptor);
    }

    /**
     * 创建可获取 EXIF 敏感信息辅助类
     * @param uri 待请求 Uri
     * @return {@link ExifAssist}
     */
    public static ExifAssist getByRequire(final Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                return get(MediaStore.setRequireOriginal(uri));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getByRequire");
                return new ExifAssist(null, e);
            }
        }
        return get(uri);
    }

    // =

    /**
     * 获取 EXIF 敏感信息, 请求获取原始 Uri
     * @param uri 待请求 Uri
     * @return Uri
     */
    public static Uri requireOriginal(final Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                return MediaStore.setRequireOriginal(uri);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "requireOriginal");
                return null;
            }
        }
        return uri;
    }

    /**
     * 请求 ACCESS_MEDIA_LOCATION 权限并进行通知
     * @param activity {@link Activity}
     * @param callback {@link PermissionUtils.PermissionCallback}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean requestPermission(
            final Activity activity,
            final PermissionUtils.PermissionCallback callback
    ) {
        if (activity == null) return false;
        if (callback == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PermissionUtils.permission(
                    Manifest.permission.ACCESS_MEDIA_LOCATION
            ).callback(callback).request(activity);
        } else {
            callback.onGranted();
        }
        return true;
    }

    /**
     * 判断是否支持读取的资源类型
     * @param mimeType 资源类型
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSupportedMimeType(final String mimeType) {
        if (StringUtils.isEmpty(mimeType)) return false;
        return ExifInterface.isSupportedMimeType(mimeType);
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
     *     正确使用是全部设置完成后统一保存, 避免设置一次值调用一次
     *     因为每次调用 saveAttributes 都会创建一个临时文件写入成功后, 再写到原始文件
     *     最后才删除临时文件
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

    // =

    /**
     * 擦除图像 Exif 信息 ( 指定集合 )
     * @param list 待擦除 TAG
     * @return {@code true} success, {@code false} fail
     */
    public boolean eraseExifByList(final List<String> list) {
        if (list == null) return false;
        return eraseExifByArray(list.toArray(new String[0]));
    }

    /**
     * 擦除图像 Exif 信息 ( 指定数组 )
     * <pre>
     *     可删除指定 Group TAG Exif 信息
     *     eraseExifByList(ExifTag.IFD_EXIF_TAGS)
     *     也可以删除指定 TAG Exif 信息
     *     eraseExifByArray(ExifInterface.TAG_GPS_LATITUDE)
     * </pre>
     * @param tags 待擦除 TAG
     * @return {@code true} success, {@code false} fail
     */
    public boolean eraseExifByArray(final String... tags) {
        if (isExifNull()) return false;
        if (tags != null) {
            for (String tag : tags) {
                if (tag != null) {
                    try {
                        mExif.setAttribute(tag, null);
                    } catch (Exception ignored) {
                    }
                }
            }
            return saveAttributes();
        }
        return false;
    }

    /**
     * 擦除图像 Exif 信息 ( 全部 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean eraseAllExif() {
        if (isExifNull()) return false;
        for (List<String> list : ExifTag.EXIF_TAGS) {
            for (String tag : list) {
                if (tag != null) {
                    try {
                        mExif.setAttribute(tag, null);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return saveAttributes();
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取经纬度信息
     * @return [0] = 纬度、[1] = 经度
     */
    public double[] getLatLong() {
        if (isExifNull()) return null;
        try {
            return mExif.getLatLong();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLatLong");
            return null;
        }
    }

    /**
     * 设置经纬度信息
     * @param latitude  纬度 ( -90.0 - 90 之间 )
     * @param longitude 经度 ( -180.0 - 180.0 之间 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean setLatLong(
            final double latitude,
            final double longitude
    ) {
        if (isExifNull()) return false;
        try {
            mExif.setLatLong(latitude, longitude);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setLatLong");
            return false;
        }
    }

    /**
     * 获取 GPS 信息
     * <pre>
     *     API 没有该方法, 通过 {@link #setGpsInfo(Location)} 倒推
     * </pre>
     * @return Location
     */
    public Location getGpsInfo() {
        if (isExifNull()) return null;
        try {
            String provider = mExif.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);
            double altitude = mExif.getAltitude(0D);
            Long   dateTime = mExif.getGpsDateTime();
            if (dateTime == null) return null;

            double[] latLong = mExif.getLatLong();
            if (latLong == null) return null;

            String gpsSpeed   = mExif.getAttribute(ExifInterface.TAG_GPS_SPEED);
            String speedKMHR  = gpsSpeed.substring(0, gpsSpeed.indexOf("/"));
            double speedKMHRD = Double.parseDouble(speedKMHR);
            double speed      = speedKMHRD / TimeUnit.HOURS.toSeconds(1) * 1000D;

            Location location = new Location(provider);
            location.setAltitude(altitude);
            location.setTime(dateTime);
            location.setLatitude(latLong[0]);
            location.setLongitude(latLong[1]);
            location.setSpeed((float) speed);
            return location;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getGpsInfo");
            return null;
        }
    }

    /**
     * 设置 GPS 信息
     * @param location Location
     * @return {@code true} success, {@code false} fail
     */
    public boolean setGpsInfo(final Location location) {
        if (location == null) return false;
        if (isExifNull()) return false;
        try {
            mExif.setGpsInfo(location);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setGpsInfo");
            return false;
        }
    }

    /**
     * 获取 GPS 定位时间信息
     * @return GPS 定位时间
     */
    public Long getGpsDateTime() {
        if (isExifNull()) return null;
        try {
            return mExif.getGpsDateTime();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getGpsDateTime");
            return null;
        }
    }

    /**
     * 获取高度信息 ( 单位米 )
     * @param defaultValue 无数据时返回默认值
     * @return 高度信息 ( 单位米 )
     */
    public double getAltitude(final double defaultValue) {
        if (isExifNull()) return defaultValue;
        try {
            return mExif.getAltitude(defaultValue);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAltitude");
            return defaultValue;
        }
    }

    /**
     * 设置高度信息
     * @param altitude 高度值 ( 单位米 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean setAltitude(final double altitude) {
        if (isExifNull()) return false;
        try {
            mExif.setAltitude(altitude);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setAltitude");
            return false;
        }
    }

    // =

    /**
     * 是否存在缩略图
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasThumbnail() {
        if (isExifNull()) return false;
        try {
            return mExif.hasThumbnail();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hasThumbnail");
            return false;
        }
    }

    /**
     * 是否存在 JPEG 压缩缩略图
     * @return {@code true} yes, {@code false} no
     */
    public boolean isThumbnailCompressed() {
        if (isExifNull()) return false;
        try {
            return mExif.isThumbnailCompressed();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isThumbnailCompressed");
            return false;
        }
    }

    /**
     * 获取 JPEG 压缩缩略图
     * <pre>
     *     如果非 JPEG 则返回 null
     *     可通过 {@link ImageUtils#decodeByteArray(byte[])}
     * </pre>
     * @return 压缩缩略图数据
     */
    public byte[] getThumbnail() {
        if (isExifNull()) return null;
        try {
            return mExif.getThumbnail();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThumbnail");
            return null;
        }
    }

    /**
     * 获取 Exif 缩略图
     * <pre>
     *     不管什么类型, 有的话则返回缩略图数据
     * </pre>
     * @return 缩略图数据
     */
    public byte[] getThumbnailBytes() {
        if (isExifNull()) return null;
        try {
            return mExif.getThumbnailBytes();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThumbnailBytes");
            return null;
        }
    }

    /**
     * 获取 Exif 缩略图
     * @return 缩略图
     */
    public Bitmap getThumbnailBitmap() {
        if (isExifNull()) return null;
        try {
            return mExif.getThumbnailBitmap();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThumbnailBitmap");
            return null;
        }
    }

    /**
     * 获取缩略图数据偏移量位置和长度信息
     * @return offset and length of thumbnail inside the image file
     */
    public long[] getThumbnailRange() {
        if (isExifNull()) return null;
        try {
            return mExif.getThumbnailRange();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThumbnailRange");
            return null;
        }
    }

    // =

    /**
     * 当前图片是否翻转
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlipped() {
        if (isExifNull()) return false;
        try {
            return mExif.isFlipped();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isFlipped");
            return false;
        }
    }

    /**
     * 进行水平翻转图片
     * @return {@code true} success, {@code false} fail
     */
    public boolean flipHorizontally() {
        if (isExifNull()) return false;
        try {
            mExif.flipHorizontally();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "flipHorizontally");
            return false;
        }
    }

    /**
     * 进行垂直翻转图片
     * @return {@code true} success, {@code false} fail
     */
    public boolean flipVertically() {
        if (isExifNull()) return false;
        try {
            mExif.flipVertically();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "flipVertically");
            return false;
        }
    }

    /**
     * 获取图片旋转角度
     * @return 图片旋转角度
     */
    public int getRotationDegrees() {
        if (isExifNull()) return 0;
        try {
            return mExif.getRotationDegrees();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRotationDegrees");
            return 0;
        }
    }

    /**
     * 将图片顺时针旋转给定度数
     * @param degree 旋转角度 ( 必须是 90 整数倍 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean rotate(final int degree) {
        if (isExifNull()) return false;
        try {
            mExif.rotate(degree);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "rotate");
            return false;
        }
    }

    /**
     * 重置图片方向为默认方向
     * @return {@code true} success, {@code false} fail
     */
    public boolean resetOrientation() {
        if (isExifNull()) return false;
        try {
            mExif.resetOrientation();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "resetOrientation");
            return false;
        }
    }
}