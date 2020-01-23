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
}
