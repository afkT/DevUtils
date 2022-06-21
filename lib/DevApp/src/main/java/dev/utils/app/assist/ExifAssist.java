package dev.utils.app.assist;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import dev.utils.LogPrintUtils;
import dev.utils.app.ResourceUtils;
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
 *     源码部分方法是不会抛出异常, 也统一进行 try-catch 防止后续库更新迭代代码变动
 *     <p></p>
 *     如果需要获取敏感信息, 如图片位置信息则
 *     需要先申请权限 {@link Manifest.permission#ACCESS_MEDIA_LOCATION} 允许后
 *     再调用 {@link MediaStore#setRequireOriginal} 获取原始 Uri
 *     最后使用 {@link ResourceUtils#openInputStream(Uri)} 返回 InputStream 进行创建 ExifAssist
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

    public static ExifAssist get(final Uri uri) {
        return new ExifAssist(ResourceUtils.openInputStream(uri));
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
        } catch (NumberFormatException e) {
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
}