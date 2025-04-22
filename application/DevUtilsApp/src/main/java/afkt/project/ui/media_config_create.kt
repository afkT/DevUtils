package afkt.project.ui

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.luck.lib.camerax.SimpleCameraX
import com.luck.picture.lib.R
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.interfaces.OnCallbackListener
import com.luck.picture.lib.interfaces.OnPermissionDeniedListener
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener
import com.luck.picture.lib.interfaces.OnRequestPermissionListener
import com.luck.picture.lib.utils.ActivityCompatHelper
import dev.engine.media.IMediaEngine
import dev.engine.media.MediaConfig
import dev.utils.DevFinal
import dev.utils.app.PathUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils

// =============================
// = IMediaEngine.EngineConfig =
// =============================

fun Activity.createGalleryConfig(): IMediaEngine.EngineConfig {
    // 初始化图片配置
    val selector = PictureSelector.create(this)
        .openGallery(SelectMimeType.ofImage())
        .setImageEngine(LuckImageEngineImpl.createEngine())
        .setPermissionsInterceptListener(OnPermissionsInterceptListenerImpl())
        .setPermissionDeniedListener(OnPermissionDeniedListenerImpl())
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

// ==========================
// = 修复 Android 权限适配问题 =
// ==========================

// https://github.com/LuckSiege/PictureSelector/issues/2499 修复 Android 权限适配问题

/**
 * detail: 结合 XXPermissions 权限适配
 * @author petterp
 * 拦截相册库的权限申请流程 simple
 */
class OnPermissionsInterceptListenerImpl : OnPermissionsInterceptListener {
    override fun requestPermission(
        fragment: Fragment?,
        permissionArray: Array<out String>?,
        call: OnRequestPermissionListener?
    ) {
        if (permissionArray == null || fragment == null || fragment.context == null) return
        if (XXPermissions.isGranted(
                fragment.requireContext(),
                permissionArray.toCompatPermissions
            )
        ) {
            call?.onCall(permissionArray, true)
            return
        }
        // 这里去申请自己的权限,可以在此处增加弹窗询问用户是否需要给权限
        XXPermissions.with(fragment)
            .permission(permissionArray.toCompatPermissions)
            .request(OnPermissionCallbackImpl(fragment, permissionArray, call))
    }

    override fun hasPermissions(
        fragment: Fragment?,
        permissionArray: Array<out String>?
    ): Boolean {
        if (permissionArray == null || fragment == null) return false
        return XXPermissions.isGranted(
            fragment.requireContext(),
            permissionArray.toCompatPermissions
        )
    }
}

/**
 * detail: 拦截相册库的权限拒绝流程
 * @author petterp
 */
class OnPermissionDeniedListenerImpl : OnPermissionDeniedListener {
    override fun onDenied(
        fragment: Fragment?,
        permissionArray: Array<out String>?,
        requestCode: Int,
        call: OnCallbackListener<Boolean>?
    ) {
        if (fragment == null || permissionArray == null || fragment.context == null) return
        // 这里即未获取到权限时, 合格的流程这里应该询问用户是否需要前往设置打开, 示例如下
        XXPermissions.startPermissionActivity(
            fragment,
            permissionArray.toCompatPermissions,
            object : OnPermissionPageCallback {
                override fun onGranted() {
                    call?.onCall(true)
                }

                override fun onDenied() {
                    ToastTintUtils.error("权限打开失败")
                    call?.onCall(false)
                }
            }
        )
    }
}

/**
 * 具体的权限申请 impl, 在这里, 我们对权限进行二次调整
 * @property fragment Fragment
 * @property permissionArray Array<out String>
 * @property call OnRequestPermissionListener?
 * @constructor
 */
class OnPermissionCallbackImpl(
    private val fragment: Fragment,
    private val permissionArray: Array<out String>,
    private val call: OnRequestPermissionListener?
) : OnPermissionCallback {

    override fun onGranted(
        permissions: MutableList<String>,
        all: Boolean
    ) {
        if (all) {
            call?.onCall(permissionArray, true)
        } else {
            // 此时直接 return, 因为我们知道这是用户自己操作的
            return
        }
    }

    override fun onDenied(
        permissions: MutableList<String>,
        never: Boolean
    ) {
        if (fragment.activity == null) return
        // 永久拒绝时让流程延续下去
        if (never) {
            call?.onCall(permissionArray, false)
        }
    }
}

private val Array<out String>.isFilePermission: Boolean
    get() = this.contains(Permission.WRITE_EXTERNAL_STORAGE) ||
            this.contains(Permission.READ_EXTERNAL_STORAGE)

private val Array<out String>.toCompatPermissions: Array<out String>
    get() {
        if (isFilePermission) return arrayOf(Permission.READ_MEDIA_IMAGES)
        return this
    }