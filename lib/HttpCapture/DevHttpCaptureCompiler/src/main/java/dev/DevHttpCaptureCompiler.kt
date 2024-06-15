package dev

import android.content.Context
import android.content.Intent
import dev.capture.UrlFunctionGet
import dev.capture.UtilsCompiler
import dev.capture.activity.DevHttpCaptureMainActivity
import dev.utils.DevFinal
import dev.utils.LogPrintUtils

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
 * DevBaseMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
 * DevMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/README.md
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/README.md
 * DevAgile README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevAgile/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevHttpCaptureCompiler {

    // 日志 TAG
    private val TAG = DevHttpCaptureCompiler::class.java.simpleName

    // =============
    // = 对外提供方法 =
    // =============

    /**
     * 结束所有 Activity
     */
    fun finishAllActivity() {
        UtilsCompiler.finishAllActivity()
    }

    // ==========
    // = 跳转方法 =
    // ==========

    /**
     * 跳转抓包数据可视化 Activity
     * @param context [Context]
     * @return `true` success, `false` fail
     */
    fun start(context: Context?): Boolean {
        return start(context, "")
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
        // 关闭全部页面
        finishAllActivity()
        try {
            val intent = Intent(context, DevHttpCaptureMainActivity::class.java)
            intent.putExtra(DevFinal.STR.MODULE, moduleName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(intent)
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "start by module: %s", moduleName)
        }
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
        UtilsCompiler.putUrlFunction(
            moduleName, function
        )
    }

    /**
     * 移除接口所属功能注释
     * @param moduleName 模块名 ( 要求唯一性 )
     */
    fun removeUrlFunction(moduleName: String) {
        UtilsCompiler.removeUrlFunction(moduleName)
    }
}