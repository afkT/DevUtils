package dev.utils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

import static android.content.Context.LOCATION_SERVICE;

/**
 * detail: 定位相关工具类
 * Created by Ttt
 */
public final class LocationUtils {

    private LocationUtils() {
    }

    // 日志TAG
    private static final String TAG = LocationUtils.class.getSimpleName();

    /** 时间常量 = 2分钟 */
    private static final int MINUTES_TWO = 1000 * 60 * 2;
    /** 定位改变通知事件 */
    private static OnLocationChangeListener mListener;
    /** 自定义定位事件 */
    private static CustomLocationListener myLocationListener;
    /** 定位管理对象 */
    private static LocationManager mLocationManager;

    /**
     * 获取位置, 需要先判断是否开启了定位
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     * @param context
     * @param listener
     * @param time 间隔时间
     * @param distance 间隔距离
     * @return {@code Location}
     */
    @SuppressLint("MissingPermission")
    public static Location getLocation(Context context, LocationListener listener, long time, float distance) {
        Location location = null;
        try {
            mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            if (isLocationEnabled()) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, time, distance, listener);
                if (mLocationManager != null) {
                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        mLocationManager.removeUpdates(listener);
                        return location;
                    }
                }
            }
            //when GPS is enabled.
            if (isGpsEnabled()) {
                if (location == null) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, time, distance, listener);
                    if (mLocationManager != null) {
                        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            mLocationManager.removeUpdates(listener);
                            return location;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e,"getLocation");
        }
        return location;
    }

    /**
     * 判断Gps是否可用
     * @return true : 是, false : 否
     */
    public static boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) DevUtils.getContext().getSystemService(LOCATION_SERVICE);
        return lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断定位是否可用
     * @return true : 是, false : 否
     */
    public static boolean isLocationEnabled() {
        LocationManager lm = (LocationManager) DevUtils.getContext().getSystemService(LOCATION_SERVICE);
        return lm != null && (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    /**
     * 打开Gps设置界面
     */
    public static void openGpsSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        DevUtils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 注册
     * <p>使用完记得调用{@link #unregister()}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />}</p>
     * <p>如果{@code minDistance}为0，则通过{@code minTime}来定时更新；</p>
     * <p>{@code minDistance}不为0，则以{@code minDistance}为准；</p>
     * <p>两者都为0，则随时刷新。</p>
     * @param minTime 位置信息更新周期（单位：毫秒）
     * @param minDistance 位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）
     * @param listener 位置刷新的回调接口
     * @return true : 初始化成功, false : 初始化失败
     */
    @SuppressLint("MissingPermission")
    public static boolean register(long minTime, long minDistance, OnLocationChangeListener listener) {
        if (listener == null) return false;
        mLocationManager = (LocationManager) DevUtils.getContext().getSystemService(LOCATION_SERVICE);
        if (mLocationManager == null || (!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                && !mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            return false;
        }
        mListener = listener;
        String provider = mLocationManager.getBestProvider(getCriteria(), true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) listener.getLastKnownLocation(location);
        if (myLocationListener == null) myLocationListener = new CustomLocationListener();
        mLocationManager.requestLocationUpdates(provider, minTime, minDistance, myLocationListener);
        return true;
    }

    /**
     * 注销
     */
    @SuppressLint("MissingPermission")
    public static void unregister() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
        if (mListener != null) {
            mListener = null;
        }
    }

    /**
     * 设置定位参数
     * @return {@link Criteria}
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * 根据经纬度获取地理位置
     * @param latitude  纬度
     * @param longitude 经度
     * @return {@link Address}
     */
    public static Address getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(DevUtils.getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) return addresses.get(0);
        } catch (IOException e) {
            LogPrintUtils.eTag(TAG, e,"getAddress");
        }
        return null;
    }

    /**
     * 根据经纬度获取所在国家
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在国家
     */
    public static String getCountryName(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getCountryName();
    }

    /**
     * 根据经纬度获取所在地
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在地
     */
    public static String getLocality(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getLocality();
    }

    /**
     * 根据经纬度获取所在街道
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在街道
     */
    public static String getStreet(double latitude, double longitude) {
        Address address = getAddress(latitude, longitude);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    /**
     * 是否更好的位置
     * @param newLocation The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     * @return true : 是, false : 否
     */
    public static boolean isBetterLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }
        // 检查位置信息的时间间隔
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > MINUTES_TWO;
        boolean isSignificantlyOlder = timeDelta < -MINUTES_TWO;
        boolean isNewer = timeDelta > 0;

        // 如果时间超过2分钟, 则使用新的位置
        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) { // 时间超过两分钟
            return false;
        }

        // 检查新的位置时间
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        //  检查旧位置和新位置是否来自同一提供者。
        boolean isFromSameProvider = isSameProvider(newLocation.getProvider(), currentBestLocation.getProvider());

        // 判断最新的位置
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * 是否相同的提供者
     * @param provider0 提供者1
     * @param provider1 提供者2
     * @return true : 是, false : 否
     */
    public static boolean isSameProvider(String provider0, String provider1) {
        if (provider0 == null) {
            return provider1 == null;
        }
        return provider0.equals(provider1);
    }

    /**
     * 自定义定位监听事件
     */
    private static class CustomLocationListener implements LocationListener {
        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         * @param location 坐标
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         * @param provider 提供者
         * @param status 状态
         * @param extras provider可选包
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    LogPrintUtils.dTag(TAG, "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    LogPrintUtils.dTag(TAG, "当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    LogPrintUtils.dTag(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /** provider被enable时触发此函数，比如GPS被打开 */
        @Override
        public void onProviderEnabled(String provider) {
        }

        /** provider被disable时触发此函数，比如GPS被关闭 */
        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    /** 定位改变事件 */
    public interface OnLocationChangeListener {

        /**
         * 获取最后一次保留的坐标
         * @param location 坐标
         */
        void getLastKnownLocation(Location location);

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         * @param location 坐标
         */
        void onLocationChanged(Location location);

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         * @param provider 提供者
         * @param status 状态
         * @param extras provider可选包
         */
        void onStatusChanged(String provider, int status, Bundle extras);//位置状态发生改变
    }
}
