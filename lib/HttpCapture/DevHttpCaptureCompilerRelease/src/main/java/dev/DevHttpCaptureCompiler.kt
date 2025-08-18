package dev

import android.content.Context
import dev.capture.UrlFunctionGet

/**
 * detail: OkHttp 抓包工具库
 * @author Ttt
 * <p></p>
 * GitHub
 * @see https://github.com/afkT/DevUtils
 * DevApp Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
 * DevAssist Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
 * DevBase README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevHttpCaptureCompiler {

    // =============
    // = 对外提供方法 =
    // =============

    /**
     * 结束所有 Activity
     */
    fun finishAllActivity() {}

    // ==========
    // = 跳转方法 =
    // ==========

    /**
     * 跳转抓包数据可视化 Activity
     * @param context [Context]
     * @return `true` success, `false` fail
     */
    fun start(context: Context?): Boolean {
        return false
    }

    /**
     * 跳转抓包数据可视化 Activity
     * @param context    [Context]
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return `true` success, `false` fail
     */
    fun start(
        context: Context?,
        moduleName: String
    ): Boolean {
        return false
    }

    /**
     * 添加接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param function   接口所属功能注释获取
     */
    fun putUrlFunction(
        moduleName: String,
        function: UrlFunctionGet
    ) {
    }

    /**
     * 移除接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     */
    fun removeUrlFunction(moduleName: String) {}
}