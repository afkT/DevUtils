package afkt.httpmanager.use.network.config

import dev.environment.annotation.Environment
import dev.environment.annotation.Module

// ==============
// = 环境配置信息 =
// ==============

/**
 * detail: Environment 配置
 * @author Ttt
 */
private class EnvironmentConfig private constructor() {

    // ==========
    // = Module =
    // ==========

    @Module(alias = "媒体信息模块")
    private inner class Media {
        @Environment(
            value = "https://raw.githubusercontent.com/afkT/DevUtils/refs/heads/master/application/DevRetrofitUse/src/main/",
            isRelease = true,
            alias = "线上环境"
        )
        private val release: String? = null

        @Environment(value = "https://debug.github.com/", alias = "测试环境")
        private val debug: String? = null

        @Environment(value = "https://development.github.com/", alias = "开发环境")
        private val development: String? = null
    }
}