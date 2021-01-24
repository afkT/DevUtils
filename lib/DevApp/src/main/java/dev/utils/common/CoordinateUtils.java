package dev.utils.common;

import static java.lang.Math.PI;

/**
 * detail: 坐标 ( GPS 纠偏 ) 相关工具类
 * @author Ttt
 * <pre>
 *     地球坐标系 (WGS-84)
 *     火星坐标系 (GCJ-02)
 *     百度坐标系 (BD09)
 *     <p></p>
 *     @see <a href="https://github.com/hujiulong/gcoord"/>
 *     @see <a href="https://www.cnblogs.com/milkmap/p/3768379.html"/>
 *     根据两点经纬度计算距离
 *     @see <a href="https://www.cnblogs.com/ycsfwhh/archive/2010/12/20/1911232.html"/>
 *     根据经纬度计算两点之间的距离的公式推导过程以及 google.maps 的测距函数
 *     @see <a href="https://blog.csdn.net/xiejm2333/article/details/73297004"/>
 *     <p></p>
 *     1. WGS84 坐标系: 即地球坐标系, 国际上通用的坐标系, 设备一般包含 GPS 芯片或者北斗芯片获取的经纬度为 WGS84 地理坐标系
 *     谷歌地图采用的是 WGS84 地理坐标系 ( 中国范围除外 ) GPS 设备得到的经纬度就是在 WGS84 坐标系下的经纬度, 通常通过底层接口得到的定位信息都是 WGS84 坐标系
 *     <p></p>
 *     2. GCJ02 坐标系: 即火星坐标系, 是由中国国家测绘局制订的地理信息系统的坐标系统, 由 WGS84 坐标系经加密后的坐标系
 *     国家规定, 中国大陆所有公开地理数据都需要至少用 GCJ-02 进行加密, 也就是说我们从国内公司的产品中得到的数据, 一定是经过了加密的
 *     绝大部分国内互联网地图提供商都是使用 GCJ-02 坐标系, 包括高德地图, 谷歌地图中国区等
 *     <p></p>
 *     3. BD09 坐标系: 即百度坐标系, 其在 GCJ-02 上多增加了一次变换, 用来保护用户隐私, 从百度产品中得到的坐标都是 BD-09 坐标系
 * </pre>
 */
public final class CoordinateUtils {

    private CoordinateUtils() {
    }

    private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
    private static final double A    = 6378245.0;
    private static final double EE   = 0.00669342162296594323;

    /**
     * BD09 坐标转 GCJ02 坐标
     * @param lng BD09 坐标纬度
     * @param lat BD09 坐标经度
     * @return GCJ02 坐标 [ 经度, 纬度 ]
     */
    public static double[] bd09ToGcj02(
            final double lng,
            final double lat
    ) {
        double x      = lng - 0.0065;
        double y      = lat - 0.006;
        double z      = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta  = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lng, gg_lat};
    }

    /**
     * GCJ02 坐标转 BD09 坐标
     * @param lng GCJ02 坐标经度
     * @param lat GCJ02 坐标纬度
     * @return BD09 坐标 [ 经度, 纬度 ]
     */
    public static double[] gcj02ToBd09(
            final double lng,
            final double lat
    ) {
        double z      = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta  = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lng, bd_lat};
    }

    /**
     * GCJ02 坐标转 WGS84 坐标
     * @param lng GCJ02 坐标经度
     * @param lat GCJ02 坐标纬度
     * @return WGS84 坐标 [ 经度, 纬度 ]
     */
    public static double[] gcj02ToWGS84(
            final double lng,
            final double lat
    ) {
        if (outOfChina(lng, lat)) return new double[]{lng, lat};
        double dlat   = transformLat(lng - 105.0, lat - 35.0);
        double dlng   = transformLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic  = Math.sin(radlat);
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
     * @return GCJ02 坐标 [ 经度, 纬度 ]
     */
    public static double[] wgs84ToGcj02(
            final double lng,
            final double lat
    ) {
        if (outOfChina(lng, lat)) return new double[]{lng, lat};
        double dlat   = transformLat(lng - 105.0, lat - 35.0);
        double dlng   = transformLng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic  = Math.sin(radlat);
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
     * @return WGS84 坐标 [ 经度, 纬度 ]
     */
    public static double[] bd09ToWGS84(
            final double lng,
            final double lat
    ) {
        double[] gcj = bd09ToGcj02(lng, lat);
        return gcj02ToWGS84(gcj[0], gcj[1]);
    }

    /**
     * WGS84 坐标转 BD09 坐标
     * @param lng WGS84 坐标经度
     * @param lat WGS84 坐标纬度
     * @return BD09 坐标 [ 经度, 纬度 ]
     */
    public static double[] wgs84ToBd09(
            final double lng,
            final double lat
    ) {
        double[] gcj = wgs84ToGcj02(lng, lat);
        return gcj02ToBd09(gcj[0], gcj[1]);
    }

    /**
     * 转换经度
     * @param lng 经度
     * @param lat 纬度
     * @return 转换后的经度
     */
    private static double transformLat(
            final double lng,
            final double lat
    ) {
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
    private static double transformLng(
            final double lng,
            final double lat
    ) {
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
    public static boolean outOfChina(
            final double lng,
            final double lat
    ) {
        return lng < 72.004 || lng > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }

    // ===========
    // = 计算坐标 =
    // ===========

    // 赤道半径
    private static final double EARTH_RADIUS = 6378.137;

    /**
     * 计算弧度角
     * @param degree 度数
     * @return 弧度角
     */
    private static double rad(final double degree) {
        return degree * Math.PI / 180.0;
    }

    /**
     * 计算两个坐标相距距离 ( 单位: 米 )
     * <pre>
     *     计算点与点直线间距离
     * </pre>
     * @param originLng 起点经度
     * @param originLat 起点纬度
     * @param targetLng 目标经度
     * @param targetLat 目标纬度
     * @return 两个坐标相距距离 ( 单位: 米 )
     */
    public static double getDistance(
            final double originLng,
            final double originLat,
            final double targetLng,
            final double targetLat
    ) {
        double radLat1 = rad(originLat);
        double radLat2 = rad(targetLat);
        double a       = radLat1 - radLat2;
        double b       = rad(originLng) - rad(targetLng);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // 保留两位小数
        s = Math.round(s * 100d) / 100d;
        s = s * 1000;
        return s;
    }

    /**
     * 计算两个坐标的方向角度
     * <pre>
     *     以 origin 为参考点坐标, 获取目标坐标位于参考点坐标方向
     * </pre>
     * @param originLng 起点经度
     * @param originLat 起点纬度
     * @param targetLng 目标经度
     * @param targetLat 目标纬度
     * @return 两个坐标的方向角度
     */
    public static double getAngle(
            final double originLng,
            final double originLat,
            final double targetLng,
            final double targetLat
    ) {
        double radLat1 = rad(originLat);
        double radLng1 = rad(originLng);
        double radLat2 = rad(targetLat);
        double radLng2 = rad(targetLng);
        double ret;
        if (radLng1 == radLng2) {
            if (radLat1 > radLat2) {
                return 270; // 北半球的情况, 南半球忽略
            } else if (radLat1 < radLat2) {
                return 90;
            } else {
                return Integer.MAX_VALUE; // 位置完全相同
            }
        }
        ret = 4 * Math.pow(Math.sin((radLat1 - radLat2) / 2), 2)
                - Math.pow(Math.sin((radLng1 - radLng2) / 2) * (Math.cos(radLat1) - Math.cos(radLat2)), 2);
        ret = Math.sqrt(ret);
        ret = ret / Math.sin(Math.abs(radLng1 - radLng2) / 2) * (Math.cos(radLat1) + Math.cos(radLat2));
        ret = Math.atan(ret) / Math.PI * 180;
        if (radLng1 > radLng2) { // 以 origin 为参考点坐标
            if (radLat1 > radLat2) {
                ret += 180;
            } else {
                ret = 180 - ret;
            }
        } else if (radLat1 > radLat2) {
            ret = 360 - ret;
        }
        return ret;
    }

    /**
     * 计算两个坐标的方向
     * @param originLng 起点经度
     * @param originLat 起点纬度
     * @param targetLng 目标经度
     * @param targetLat 目标纬度
     * @return 两个坐标的方向
     */
    public static Direction getDirection(
            final double originLng,
            final double originLat,
            final double targetLng,
            final double targetLat
    ) {
        double angle = getAngle(originLng, originLat, targetLng, targetLat);
        return getDirection(angle);
    }

    /**
     * 通过角度获取方向
     * @param angle 角度
     * @return 方向
     */
    public static Direction getDirection(final double angle) {
        if (angle == Integer.MAX_VALUE) return Direction.SAME;
        if ((angle <= 10) || (angle > 350)) {
            return Direction.RIGHT;
        }
        if ((angle > 10) && (angle <= 80)) {
            return Direction.RIGHT_TOP;
        }
        if ((angle > 80) && (angle <= 100)) {
            return Direction.TOP;
        }
        if ((angle > 100) && (angle <= 170)) {
            return Direction.LEFT_TOP;
        }
        if ((angle > 170) && (angle <= 190)) {
            return Direction.LEFT;
        }
        if ((angle > 190) && (angle <= 260)) {
            return Direction.LEFT_BOTTOM;
        }
        if ((angle > 260) && (angle <= 280)) {
            return Direction.BOTTOM;
        }
        if ((angle > 280) && (angle <= 350)) {
            return Direction.RIGHT_BOTTOM;
        }
        return Direction.SAME;
    }

    /**
     * detail: 坐标方向
     * @author Ttt
     */
    public enum Direction {

        SAME("相同"), // 坐标相同
        TOP("北"), // 上 ( 北 )
        BOTTOM("南"), // 下 ( 南 )
        LEFT("西"), // 左 ( 西 )
        RIGHT("东"), // 右 ( 东 )
        LEFT_TOP("西北"), // 左上 ( 西北 )
        LEFT_BOTTOM("西南"), // 左下 ( 西南 )
        RIGHT_TOP("东北"), // 右上 ( 东北 )
        RIGHT_BOTTOM("东南"); // 右下 ( 东南 )

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        /**
         * 获取中文方向值
         * @return 中文方向值
         */
        public String getValue() {
            return value;
        }
    }
}