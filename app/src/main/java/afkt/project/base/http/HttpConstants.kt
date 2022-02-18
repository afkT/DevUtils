package afkt.project.base.http

import dev.environment.annotation.Environment
import dev.environment.annotation.Module

/**
 * detail: Http 常量
 * @author Ttt
 */
class HttpConstants private constructor() {

    @Module(alias = "服务器请求地址")
    private inner class Service {
        @Environment(value = "https://www.wanandroid.com/", isRelease = true, alias = "线上环境")
        private val release: String? = null

        @Environment(value = "https://debug.com", alias = "测试环境")
        private val debug: String? = null

        @Environment(value = "https://pre_release.com", alias = "预发布环境")
        private val pre_release: String? = null

        @Environment(value = "https://development.com", alias = "开发环境")
        private val development: String? = null
    }

    @Module(alias = "开关")
    private inner class Switch {
        @Environment(value = "true", isRelease = true)
        private val open: String? = null

        @Environment(value = "false")
        private val close: String? = null
    }

    @Module(alias = "IM 模块")
    private inner class IM {
        @Environment(value = "https://im.release.com/", isRelease = true, alias = "线上环境")
        private val release: String? = null

        @Environment(value = "https://im.debug.com", alias = "测试环境")
        private val debug: String? = null
    }

    @Module(alias = "地图")
    private inner class Map {
        @Environment(value = "a3f4a5b080e2a4ef4a708b9c9f5ad003", isRelease = true, alias = "百度地图")
        private val baidu: String? = null

        @Environment(value = "9cc1b3fbd4e4d2f69994df700d648c40", alias = "高德地图")
        private val gaode: String? = null

        @Environment(value = "6b3d3b354aff2b2e4e37db5409e0ce7f", alias = "谷歌地图")
        private val google: String? = null

        @Environment(value = "1977803150186fe4d2a3e226e2869497", alias = "腾讯地图")
        private val qq: String? = null
    }
}