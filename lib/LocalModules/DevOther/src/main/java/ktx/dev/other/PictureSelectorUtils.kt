package ktx.dev.other

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import dev.engine.media.LocalMediaData
import dev.engine.media.MediaConfig
import dev.engine.media.PictureSelectorEngineImpl

/**
 * detail: Android 平台下的图片选择器
 * @author Ttt
 */
@Deprecated("推荐使用 DevMediaEngine 实现类 PictureSelectorEngineImpl")
object PictureSelectorUtils {

    private val IMPL = PictureSelectorEngineImpl()

    // ==========
    // = 配置方法 =
    // ==========

    fun getConfig(): MediaConfig {
        return IMPL.config
    }

    fun setConfig(config: MediaConfig?) {
        IMPL.setConfig(config)
    }

    // =============
    // = 对外公开方法 =
    // =============

    fun openCamera(activity: Activity?): Boolean {
        return IMPL.openCamera(activity)
    }

    fun openCamera(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return IMPL.openCamera(activity, config)
    }

    fun openCamera(fragment: Fragment?): Boolean {
        return IMPL.openCamera(fragment)
    }

    fun openCamera(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return IMPL.openCamera(fragment, config)
    }

    // =

    fun openGallery(activity: Activity?): Boolean {
        return IMPL.openGallery(activity)
    }

    fun openGallery(
        activity: Activity?,
        config: MediaConfig?
    ): Boolean {
        return IMPL.openGallery(activity, config)
    }

    fun openGallery(fragment: Fragment?): Boolean {
        return IMPL.openGallery(fragment)
    }

    fun openGallery(
        fragment: Fragment?,
        config: MediaConfig?
    ): Boolean {
        return IMPL.openGallery(fragment, config)
    }

    // ==========
    // = 其他方法 =
    // ==========

    fun deleteCacheDirFile(
        context: Context?,
        type: Int
    ) {
        IMPL.deleteCacheDirFile(context, type)
    }

    fun deleteAllCacheDirFile(context: Context?) {
        IMPL.deleteAllCacheDirFile(context)
    }

    fun isMediaSelectorResult(
        requestCode: Int,
        resultCode: Int
    ): Boolean {
        return IMPL.isMediaSelectorResult(requestCode, resultCode)
    }

    // =

    fun getSelectors(intent: Intent?): MutableList<LocalMediaData> {
        return IMPL.getSelectors(intent)
    }

    fun getSelectorUris(
        intent: Intent?,
        original: Boolean
    ): MutableList<Uri> {
        return IMPL.getSelectorUris(intent, original)
    }

    fun getSingleSelector(intent: Intent?): LocalMediaData? {
        return IMPL.getSingleSelector(intent)
    }

    fun getSingleSelectorUri(
        intent: Intent?,
        original: Boolean
    ): Uri? {
        return IMPL.getSingleSelectorUri(intent, original)
    }
}