package dev;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.capture.BuildConfig;
import dev.capture.CaptureItem;
import dev.capture.HttpCaptureInterceptor;
import dev.capture.IHttpCapture;
import dev.capture.IHttpFilter;
import dev.capture.UtilsPublic;
import dev.utils.common.StringUtils;
import dev.utils.common.cipher.Encrypt;
import okhttp3.OkHttpClient;

/**
 * detail: OkHttp 抓包工具库
 * @author Ttt
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 *     DevBase README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md"/>
 *     DevBaseMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md"/>
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevHttpCapture Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md"/>
 *     DevHttpManager Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md"/>
 *     DevRetrofit Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 *     DevWidget Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md"/>
 *     DevEnvironment Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/Environment"/>
 * </pre>
 */
public final class DevHttpCapture {

    private DevHttpCapture() {
    }

    // 日志 TAG
    public static final String TAG = DevHttpCapture.class.getSimpleName();

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevHttpCapture 版本号
     * @return DevHttpCapture versionCode
     */
    public static int getDevHttpCaptureVersionCode() {
        return BuildConfig.DevHttpCapture_VersionCode;
    }

    /**
     * 获取 DevHttpCapture 版本
     * @return DevHttpCapture versionName
     */
    public static String getDevHttpCaptureVersion() {
        return BuildConfig.DevHttpCapture_Version;
    }

    /**
     * 获取 DevApp 版本号
     * @return DevApp versionCode
     */
    public static int getDevAppVersionCode() {
        return BuildConfig.DevApp_VersionCode;
    }

    /**
     * 获取 DevApp 版本
     * @return DevApp versionName
     */
    public static String getDevAppVersion() {
        return BuildConfig.DevApp_Version;
    }

    // ===============
    // = Interceptor =
    // ===============

    // Http 抓包接口 Map
    private static final Map<String, IHttpCapture> sCaptureMaps = new LinkedHashMap<>();

    /**
     * 添加 Http 抓包拦截处理
     * @param builder    OkHttpClient Builder
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addInterceptor(
            final OkHttpClient.Builder builder,
            final String moduleName
    ) {
        return addInterceptor(builder, moduleName, null, null, true);
    }

    /**
     * 添加 Http 抓包拦截处理
     * @param builder    OkHttpClient Builder
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param isCapture  是否进行 Http 抓包拦截
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addInterceptor(
            final OkHttpClient.Builder builder,
            final String moduleName,
            final boolean isCapture
    ) {
        return addInterceptor(builder, moduleName, null, null, isCapture);
    }

    /**
     * 添加 Http 抓包拦截处理
     * @param builder    OkHttpClient Builder
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param encrypt    抓包数据加密中间层
     * @param httpFilter Http 拦截过滤器
     * @param isCapture  是否进行 Http 抓包拦截
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addInterceptor(
            final OkHttpClient.Builder builder,
            final String moduleName,
            final Encrypt encrypt,
            final IHttpFilter httpFilter,
            final boolean isCapture
    ) {
        if (builder != null && StringUtils.isNotEmpty(moduleName)) {
            if (!sCaptureMaps.containsKey(moduleName)) {
                HttpCaptureInterceptor interceptor = new HttpCaptureInterceptor(
                        moduleName, encrypt, httpFilter, isCapture
                );
                // 添加抓包拦截
                builder.addInterceptor(interceptor);
                // 保存 IHttpCapture 接口对象
                sCaptureMaps.put(moduleName, interceptor);
                return true;
            }
        }
        return false;
    }

    /**
     * 是否存在对应 Module Http 抓包拦截
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainsModule(final String moduleName) {
        if (StringUtils.isNotEmpty(moduleName)) {
            return sCaptureMaps.containsKey(moduleName);
        }
        return false;
    }

    /**
     * 移除对应 Module Http 抓包拦截
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeInterceptor(final String moduleName) {
        if (StringUtils.isNotEmpty(moduleName)) {
            IHttpCapture httpCapture = sCaptureMaps.get(moduleName);
            if (httpCapture != null) {
                httpCapture.setCapture(false);
                sCaptureMaps.remove(moduleName);
                return true;
            }
        }
        return false;
    }

    /**
     * 更新对应 Module Http 抓包拦截处理
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param isCapture  是否进行 Http 抓包拦截
     * @return {@code true} success, {@code false} fail
     */
    public static boolean updateInterceptor(
            final String moduleName,
            final boolean isCapture
    ) {
        if (StringUtils.isNotEmpty(moduleName)) {
            IHttpCapture httpCapture = sCaptureMaps.get(moduleName);
            if (httpCapture != null) {
                httpCapture.setCapture(isCapture);
                return true;
            }
        }
        return false;
    }

    // ==========
    // = 获取操作 =
    // ==========

    /**
     * 获取指定模块抓包存储路径
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包存储路径
     */
    public static String getModulePath(final String moduleName) {
        if (StringUtils.isNotEmpty(moduleName)) {
            IHttpCapture httpCapture = sCaptureMaps.get(moduleName);
            if (httpCapture != null) {
                return httpCapture.getModulePath();
            }
        }
        return null;
    }

    /**
     * 获取指定模块所有抓包数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块所有抓包数据
     */
    public static List<CaptureItem> getModuleHttpCaptures(final String moduleName) {
        if (StringUtils.isNotEmpty(moduleName)) {
            IHttpCapture httpCapture = sCaptureMaps.get(moduleName);
            if (httpCapture != null) {
                return httpCapture.getModuleHttpCaptures();
            }
        }
        return new ArrayList<>();
    }

    /**
     * 获取全部模块所有抓包数据
     * @param isEncrypt 是否加密数据
     * @return 全部模块所有抓包数据
     */
    public static Map<String, List<CaptureItem>> getAllModule(
            final boolean isEncrypt
    ) {
        return UtilsPublic.getAllModule(isEncrypt);
    }
}