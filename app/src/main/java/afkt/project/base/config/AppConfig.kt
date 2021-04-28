package afkt.project.base.config;

/**
 * detail: App 配置
 * @author Ttt
 */
public final class AppConfig {

    private AppConfig() {
    }

    // ===========
    // = 项目信息 =
    // ===========

    // 项目名
    public static final String BASE_NAME           = "DevUtils";
    // 缩写标识 - 小写
    public static final String BASE_NAME_SHORT     = "dev_utils";
    // 缩写标识 - 大写
    public static final String BASE_NAME_SHORT_CAP = "DEV_UTILS";

    // ===========
    // = 其他配置 =
    // ===========

    // 项目日志 TAG
    public static final String LOG_TAG            = BASE_NAME + "_Log";
    // Bugly 项目标识
    public static final String BUGLY_PRONAME_MARK = BASE_NAME + "_Android";
}