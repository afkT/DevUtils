package dev.widget;

/**
 * detail: DevWidget
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
public final class DevWidget {

    private DevWidget() {
    }

    // 日志 TAG
    public static final String TAG = DevWidget.class.getSimpleName();

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevWidget 版本号
     * @return DevWidget versionCode
     */
    public static int getDevWidgetVersionCode() {
        return BuildConfig.DevWidget_VersionCode;
    }

    /**
     * 获取 DevWidget 版本
     * @return DevWidget versionName
     */
    public static String getDevWidgetVersion() {
        return BuildConfig.DevWidget_Version;
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
}