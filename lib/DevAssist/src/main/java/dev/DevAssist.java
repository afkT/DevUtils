package dev.assist;

/**
 * detail: 开发辅助类
 * @author Ttt
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 * </pre>
 */
public final class DevAssist {

    private DevAssist() {
    }

    // 日志 TAG
    private static final String TAG = DevAssist.class.getSimpleName();

    // ==============
    // = 工具类版本 =
    // ==============

    /**
     * 获取 DevAssist 工具类版本
     * @return DevAssist versionName
     */
    public static String getDevAssistUtilsVersion() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取 DevAssist 工具类版本号
     * @return DevAssist versionCode
     */
    public static int getDevAssistUtilsVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 获取 DevJava 工具类版本
     * @return DevJava version
     */
    public static String getDevJavaUtilsVersion() {
        return BuildConfig.DevJava_Version;
    }

    /**
     * 获取 DevJava 工具类版本号
     * @return DevJava version
     */
    public static int getDevJavaUtilsVersionCode() {
        return BuildConfig.DevJava_VersionCode;
    }
}