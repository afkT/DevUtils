package dev.utils.common;

import static java.lang.Math.PI;

/**
 * detail: 坐标相关工具类 - GPS 纠偏
 * @author Ttt
 * <pre>
 *      地球坐标系 (WGS-84)
 *      火星坐标系 (GCJ-02)
 *      百度坐标系 (BD09)
 *      <p></p>
 *      @see <a href="https://github.com/hujiulong/gcoord"/>
 *      <p></p>
 *      1. WGS84 坐标系: 即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 *      谷歌地图采用的是WGS84地理坐标系（中国范围除外）GPS设备得到的经纬度就是在WGS84坐标系下的经纬度。通常通过底层接口得到的定位信息都是WGS84坐标系。
 *      <p></p>
 *      2. GCJ02 坐标系: 即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 *      国家规定，中国大陆所有公开地理数据都需要至少用GCJ-02进行加密，也就是说我们从国内公司的产品中得到的数据，一定是经过了加密的。
 *      绝大部分国内互联网地图提供商都是使用GCJ-02坐标系，包括高德地图，谷歌地图中国区等。
 *      <p></p>
 *      3. BD09 坐标系: 即百度坐标系，其在GCJ-02上多增加了一次变换，用来保护用户隐私。从百度产品中得到的坐标都是BD-09坐标系。
 * </pre>
 */
public final class CoordinateUtils {

    private CoordinateUtils() {
    }

    private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    private static final double A = 6378245.0;
    private static final double EE = 0.00669342162296594323;

    /**
     * BD09 坐标转 GCJ02 坐标
     * @param lng BD09 坐标纬度
     * @param lat BD09 坐标经度
     * @return GCJ02 坐标: [经度，纬度]
     */
    public static double[] bd09ToGcj02(final double lng, final double lat) {
        double x = lng - 0.0065;
        double y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lng, gg_lat};
    }

    /**
     * GCJ02 坐标转 BD09 坐标
     * @param lng GCJ02 坐标经度
     * @param lat GCJ02 坐标纬度
     * @return BD09 坐标: [经度，纬度]
     */
    public static double[] gcj02ToBd09(final double lng, final double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lng, bd_lat};
    }

    /**
     * GCJ02 坐标转 WGS84 坐标
     * @param lng GCJ02 坐标经度
     * @param lat GCJ02 坐标纬度
     * @return WGS84 坐标: [经度，纬度]
     */
    public static double[] gcj02ToWGS84(final double lng, final double lat) {
        if (outOfChina(lng, lat)) return new double[]{lng, lat};
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{lng * 2 - mglng, lat * 2 - mglat};
    }

    /**
     * WGS84 坐标转 GCJ02 坐标
     * @param lng WGS84 坐标经度
     * @param lat WGS84 坐标纬度
     * @return GCJ02 坐标: [经度，纬度]
     */
    public static double[] wgs84ToGcj02(final double lng, final double lat) {
        if (outOfChina(lng, lat)) return new double[]{lng, lat};
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - EE * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((A * (1 - EE)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (A / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{mglng, mglat};
    }

    /**
     * BD09 坐标转 WGS84 坐标
     * @param lng BD09 坐标经度
     * @param lat BD09 坐标纬度
     * @return WGS84 坐标: [经度，纬度]
     */
    public static double[] bd09ToWGS84(final double lng, final double lat) {
        double[] gcj = bd09ToGcj02(lng, lat);
        return gcj02ToWGS84(gcj[0], gcj[1]);
    }

    /**
     * WGS84 坐标转 BD09 坐标
     * @param lng WGS84 坐标经度
     * @param lat WGS84 坐标纬度
     * @return BD09 坐标: [经度，纬度]
     */
    public static double[] wgs84ToBd09(final double lng, final double lat) {
        double[] gcj = wgs84ToGcj02(lng, lat);
        return gcj02ToBd09(gcj[0], gcj[1]);
    }

    /**
     * 转换经度
     * @param lng 经度
     * @param lat 纬度
     * @return 转换后的经度
     */
    private static double transformLat(final double lng, final double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 转换纬度
     * @param lng 经度
     * @param lat 纬度
     * @return 转换后的纬度
     */
    private static double transformLng(final double lng, final double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 判断是否中国境外
     * @param lng 经度
     * @param lat 纬度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean outOfChina(final double lng, final double lat) {
        return lng < 72.004 || lng > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }
}
