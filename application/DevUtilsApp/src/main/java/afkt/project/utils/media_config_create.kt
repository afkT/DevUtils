package afkt.project.utils

import android.app.Activity
import com.bumptech.glide.Glide
import com.luck.lib.camerax.SimpleCameraX
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import dev.engine.media.IMediaEngine
import dev.engine.media.MediaConfig
import dev.utils.DevFinal
import dev.utils.app.PathUtils
import dev.utils.common.FileUtils
import java.dev.engine.media.luck_siege_lib.LuckCompressFileEngineImpl
import java.dev.engine.media.luck_siege_lib.LuckImageEngineImpl


// =============================
// = IMediaEngine.EngineConfig =
// =============================

fun Activity.createGalleryConfig(): IMediaEngine.EngineConfig {
    // 初始化图片配置
    val selector = PictureSelector.create(this)
        .openGallery(SelectMimeType.ofImage())
        .setImageEngine(LuckImageEngineImpl.createEngine())
        .setCompressEngine(LuckCompressFileEngineImpl(100))
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