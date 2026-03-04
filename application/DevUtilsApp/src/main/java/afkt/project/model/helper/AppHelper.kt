package afkt.project.model.helper

import afkt.project.R
import afkt.project.app.basic.BaseActivity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Looper
import dev.*
import dev.base.DevBase
import dev.engine.DevEngine
import dev.engine.extensions.log.log_iTag
import dev.environment.DevEnvironmentUtils
import dev.simple.DevSimple
import dev.utils.app.AppUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.VersionUtils
import dev.utils.common.DateUtils
import dev.utils.common.assist.TimeCounter
import dev.widget.DevWidget
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.internal.CancelAdapt

// =================
// = App Helper 类 =
// =================

object AppHelper {

    // 日志 TAG
    val TAG = AppHelper::class.java.simpleName

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 打印项目信息
     * @param timeCounter [TimeCounter]
     */
    fun printInfo(timeCounter: TimeCounter) {
        val builder = StringBuilder()
            .append("项目名: ").append(ResourceUtils.getString(R.string.app_name))
            .append("\nSDK: ").append(Build.VERSION.SDK_INT).append("(")
            .append(VersionUtils.convertSDKVersion(Build.VERSION.SDK_INT)).append(")")
            .append("\nPackageName: ").append(AppUtils.getPackageName())
            .append("\nVersionCode: ").append(AppUtils.getAppVersionCode())
            .append("\nVersionName: ").append(AppUtils.getAppVersionName())
            .append("\nDevUtils 版本: ").append(DevUtils.getDevAppVersion())
            .append("\nDevAssist 版本: ").append(DevAssist.getDevAssistVersion())
            .append("\nDevBase 版本: ").append(DevBase.getDevBaseVersion())
            .append("\nDevSimple 版本: ").append(DevSimple.getDevSimpleVersion())
            .append("\nDevEngine 版本: ").append(DevEngine.getDevEngineVersion())
            .append("\nDevWidget 版本: ").append(DevWidget.getDevWidgetVersion())
            .append("\nDevHttpCapture 版本: ").append(DevHttpCapture.getDevHttpCaptureVersion())
            .append("\nDevEnvironment 版本: ")
            .append(DevEnvironmentUtils.getDevEnvironmentVersion())
            .append("\nDevHttpManager 版本: ").append(DevHttpManager.getDevHttpManagerVersion())
            .append("\nDevRetrofit 版本: ").append(DevRetrofit.getDevRetrofitVersion())
            .append("\nDevJava 版本: ").append(DevUtils.getDevJavaVersion())
            .append("\n时间: ").append(DateUtils.getDateNow())
            .append("\n初始化耗时(毫秒): ").append(timeCounter.duration())
        TAG.log_iTag(message = builder.toString())
    }
}

// ============
// = AutoSize =
// ============

/**
 * 返回自动适配 Resources
 * @param resource Resources
 * @return AutoSize Resources
 */
fun BaseActivity<*, *>.autoResources(resource: Resources?): Resources? {
    if (resource == null) return resource
    if (this !is CancelAdapt) {
        // 解决 Android 14 系统软键盘无法弹出
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return resource
        }
        // 360 -> design_width_in_dp
        if (resource.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            AutoSizeCompat.autoConvertDensityBaseOnWidth(
                resource, 360.0f
            )
        } else {
            // 横屏
            AutoSizeCompat.autoConvertDensityBaseOnHeight(
                resource, 360.0f
            )
        }
    }
    return resource
}