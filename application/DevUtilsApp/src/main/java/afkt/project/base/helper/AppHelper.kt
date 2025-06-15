package afkt.project.base.helper

import afkt.project.R
import android.os.Build
import dev.*
import dev.base.DevBase
import dev.base.DevBaseMVVM
import dev.engine.DevEngine
import dev.engine.image.ImageConfig
import dev.environment.DevEnvironmentUtils
import dev.expand.engine.log.log_iTag
import dev.simple.DevSimple
import dev.utils.app.AppUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.VersionUtils
import dev.utils.common.DateUtils
import dev.utils.common.assist.TimeCounter
import dev.widget.DevWidget

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
            .append("项目名: ").append(ResourceUtils.getString(R.string.str_app_name))
            .append("\nSDK: ").append(Build.VERSION.SDK_INT).append("(")
            .append(VersionUtils.convertSDKVersion(Build.VERSION.SDK_INT)).append(")")
            .append("\nPackageName: ").append(AppUtils.getPackageName())
            .append("\nVersionCode: ").append(AppUtils.getAppVersionCode())
            .append("\nVersionName: ").append(AppUtils.getAppVersionName())
            .append("\nDevUtils 版本: ").append(DevUtils.getDevAppVersion())
            .append("\nDevAssist 版本: ").append(DevAssist.getDevAssistVersion())
            .append("\nDevBase 版本: ").append(DevBase.getDevBaseVersion())
            .append("\nDevBaseMVVM 版本: ").append(DevBaseMVVM.getDevBaseMVVMVersion())
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

// ================================
// = dev.engine.image.ImageConfig =
// ================================

// ============
// = 使用方式一 =
// ============

val IMAGE_ROUND = ImageConfig.create().apply {
    setTransform(ImageConfig.TRANSFORM_ROUNDED_CORNERS)
    setScaleType(ImageConfig.SCALE_NONE)
}

//val IMAGE_DEFAULT_CROP = ImageConfig.create().apply {
//    setScaleType(ImageConfig.SCALE_CENTER_CROP)
//}
//
//val IMAGE_ROUND_3 = ImageConfig.create(IMAGE_ROUND).apply {
//    setRoundedCornersRadius(
//        AppSize.dp2px(3F)
//    )
//

// ============
// = 使用方式二 =
// ============

// IMAGE_KEY.toImageConfig() => ImageConfig
const val IMAGE_DEFAULT_CROP = "IMAGE_DEFAULT_CROP"
const val IMAGE_DEFAULT_FIX = "IMAGE_DEFAULT_FIX"
const val IMAGE_ROUND_3 = "IMAGE_ROUND_3"
const val IMAGE_ROUND_10 = "IMAGE_ROUND_10"
const val IMAGE_ROUND_CROP_10 = "IMAGE_ROUND_CROP_10"
const val IMAGE_ROUND_FIX_10 = "IMAGE_ROUND_FIX_10"