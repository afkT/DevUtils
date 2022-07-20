package afkt.project.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.luck.lib.camerax.SimpleCameraX
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.utils.ActivityCompatHelper
import dev.engine.media.IMediaEngine
import dev.engine.media.MediaConfig
import dev.other.R
import dev.utils.DevFinal
import dev.utils.app.PathUtils
import dev.utils.common.FileUtils

// =============================
// = IMediaEngine.EngineConfig =
// =============================

fun Activity.createGalleryConfig(): IMediaEngine.EngineConfig {
    // 初始化图片配置
    val selector = PictureSelector.create(this)
        .openGallery(SelectMimeType.ofImage())
        .setImageEngine(LuckImageEngineImpl.createEngine())
        .setCameraInterceptListener { fragment, cameraMode, requestCode ->
            fragment?.let { itFrag ->
                val dirPath = PathUtils.getAppExternal().getAppCachePath(DevFinal.STR.SANDBOX)
                FileUtils.createFolder(dirPath)

                // ==========
                // = 点击拍照 =
                // ==========

                val camera = SimpleCameraX.of()
                camera.isAutoRotation(true)
                camera.setCameraMode(cameraMode)
                camera.setVideoFrameRate(25)
                camera.setVideoBitRate(3 * 1024 * 1024)
                camera.isDisplayRecordChangeTime(true)
                camera.isManualFocusCameraPreview(false)
                camera.isZoomCameraPreview(true)
                camera.setOutputPathDir(dirPath)
                camera.setImageEngine { context, url, imageView ->
                    Glide.with(context).load(url).into(imageView)
                }
                camera.start(this, itFrag, requestCode)
            }
        }
        .setMaxSelectNum(1).isGif(false)
    return MediaConfig().setLibCustomConfig(selector)
}

/**
 * detail: PictureSelector 相册图片加载引擎
 * @author luck
 */
class LuckImageEngineImpl private constructor() : ImageEngine {

    private object Holder {
        val instance = LuckImageEngineImpl()
    }

    companion object {
        fun createEngine(): LuckImageEngineImpl {
            return Holder.instance
        }
    }

    // ===============
    // = ImageEngine =
    // ===============

    /**
     * 加载图片
     * @param context   Context
     * @param url       资源 URL
     * @param imageView 图片承载控件
     */
    override fun loadImage(
        context: Context,
        url: String?,
        imageView: ImageView
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    override fun loadImage(
        context: Context,
        imageView: ImageView,
        url: String?,
        maxWidth: Int,
        maxHeight: Int
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .override(maxWidth, maxHeight)
            .into(imageView)
    }

    /**
     * 加载相册目录封面
     * @param context   Context
     * @param url       图片路径
     * @param imageView 图片承载控件
     */
    override fun loadAlbumCover(
        context: Context,
        url: String?,
        imageView: ImageView
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .sizeMultiplier(0.5f)
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.ps_image_placeholder)
            .into(imageView)
    }

    /**
     * 加载图片列表图片
     * @param context   Context
     * @param url       图片路径
     * @param imageView 图片承载控件
     */
    override fun loadGridImage(
        context: Context,
        url: String?,
        imageView: ImageView
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .placeholder(R.drawable.ps_image_placeholder)
            .into(imageView)
    }

    override fun pauseRequests(context: Context) {
        Glide.with(context).pauseRequests()
    }

    override fun resumeRequests(context: Context) {
        Glide.with(context).resumeRequests()
    }
}