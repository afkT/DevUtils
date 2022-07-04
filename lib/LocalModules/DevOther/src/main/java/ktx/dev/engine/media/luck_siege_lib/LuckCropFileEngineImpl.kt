package ktx.dev.engine.media.luck_siege_lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.luck.picture.lib.engine.CropFileEngine
import com.luck.picture.lib.utils.ActivityCompatHelper
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine

/**
 * detail: PictureSelector 相册裁剪引擎
 * @author luck
 */
open class LuckCropFileEngineImpl(
    // UCrop.Options
    protected val mOptions: UCrop.Options
) : CropFileEngine {

    // ==================
    // = CropFileEngine =
    // ==================

    override fun onStartCrop(
        fragment: Fragment,
        srcUri: Uri,
        destinationUri: Uri,
        dataSource: ArrayList<String>,
        requestCode: Int
    ) {
        val uCrop = UCrop.of(srcUri, destinationUri, dataSource)
        uCrop.setImageEngine(uCropImageEngine)
        uCrop.withOptions(mOptions)
        uCrop.start(fragment.requireContext(), fragment, requestCode)
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 裁剪库图片加载引擎
    protected val uCropImageEngine: UCropImageEngine = object : UCropImageEngine {
        override fun loadImage(
            context: Context,
            url: String,
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
            url: Uri,
            maxWidth: Int,
            maxHeight: Int,
            call: UCropImageEngine.OnCallbackListener<Bitmap>
        ) {
            if (!ActivityCompatHelper.assertValidRequest(context)) {
                return
            }
            Glide.with(context)
                .asBitmap()
                .override(maxWidth, maxHeight)
                .load(url)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        call.onCall(resource)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        call.onCall(null)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }
}