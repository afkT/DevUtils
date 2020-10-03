package afkt.project.base.constants.http;

import dev.environment.annotation.Environment;
import dev.environment.annotation.Module;

/**
 * detail: Http 常量
 * @author Ttt
 */
public final class HttpConstants {

    private HttpConstants() {
    }

    @Module(alias = "服务器请求地址")
    private class Service {

        @Environment(value = "https://www.wanandroid.com/", isRelease = true, alias = "线上环境")
        private String release;

        @Environment(value = "https://debug.com", alias = "测试环境")
        private String debug;

        @Environment(value = "https://pre_release.com", alias = "预发布环境")
        private String pre_release;

        @Environment(value = "https://development.com", alias = "开发环境")
        private String development;
    }

    @Module(alias = "开关")
    private class Switch {

        @Environment(value = "true", isRelease = true)
        private String open;

        @Environment(value = "false")
        private String close;
    }

    @Module(alias = "IM 模块")
    private class IM {

        @Environment(value = "https://im.release.com/", isRelease = true, alias = "线上环境")
        private String release;

        @Environment(value = "https://im.debug.com", alias = "测试环境")
        private String debug;
    }

    @Module(alias = "地图")
    private class Map {

        @Environment(value = "a3f4a5b080e2a4ef4a708b9c9f5ad003", isRelease = true, alias = "百度地图")
        private String baidu;

        @Environment(value = "9cc1b3fbd4e4d2f69994df700d648c40", alias = "高德地图")
        private String gaode;

        @Environment(value = "6b3d3b354aff2b2e4e37db5409e0ce7f", alias = "谷歌地图")
        private String google;

        @Environment(value = "1977803150186fe4d2a3e226e2869497", alias = "腾讯地图")
        private String qq;

    }
}