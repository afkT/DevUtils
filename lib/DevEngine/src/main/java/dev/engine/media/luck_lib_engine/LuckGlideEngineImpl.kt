package dev.engine.media.luck_lib_engine

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.listener.OnImageCompleteCallback
import com.luck.picture.lib.tools.MediaUtils
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import dev.engine.R

/**
 * @author luck
 * @date 2019-11-13 17:02
 * @describe Glide 加载引擎
 */
internal class LuckGlideEngineImpl private constructor() : ImageEngine {

    /**
     * 加载图片
     * @param context   上下文
     * @param url       资源 url
     * @param imageView 图片承载控件
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        if (!assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    /**
     * 加载网络图片适配长图方案
     * @param context       上下文
     * @param url           资源 url
     * @param imageView     图片承载控件
     * @param longImageView 长图承载控件
     * @param callback      网络图片加载回调监听
     */
    override fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        longImageView: SubsamplingScaleImageView?,
        callback: OnImageCompleteCallback?
    ) {
        if (!assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    callback?.onShowLoading()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback?.onHideLoading()
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    callback?.onHideLoading()
                    val eqLongImage = MediaUtils.isLongImg(
                        resource.width, resource.height
                    )
                    imageView.visibility = if (eqLongImage) View.GONE else View.VISIBLE
                    longImageView?.visibility = if (eqLongImage) View.VISIBLE else View.GONE
                    if (eqLongImage) {
                        longImageView?.apply {
                            // 加载长图
                            isQuickScaleEnabled = true
                            isZoomEnabled = true
                            setDoubleTapZoomDuration(100)
                            setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
                            setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
                            setImage(
                                ImageSource.cachedBitmap(resource),
                                ImageViewState(0F, PointF(0F, 0F), 0)
                            )
                        }
                    } else {
                        // 普通图片
                        imageView.setImageBitmap(resource)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    /**
     * 加载相册目录
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片 ImageView
     */
    override fun loadFolderImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        if (!assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .centerCrop()
            .sizeMultiplier(0.5F)
            .placeholder(R.drawable.picture_image_placeholder)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    try {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(
                            context.resources, resource
                        )
                        circularBitmapDrawable.cornerRadius = 8F
                        imageView.setImageDrawable(circularBitmapDrawable)
                    } catch (e: Exception) {
                    }
                }
            })
    }

    /**
     * 加载图片列表图片
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片 ImageView
     */
    override fun loadGridImage(
        context: Context,
        url: String,
        imageView: ImageView
    ) {
        if (!assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .placeholder(R.drawable.picture_image_placeholder)
            .into(imageView)
    }

    companion object {

        val instance: LuckGlideEngineImpl by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LuckGlideEngineImpl()
        }
    }

    // ====================
    // = ImageLoaderUtils =
    // ====================

    private fun assertValidRequest(context: Context?): Boolean {
        if (context is Activity) {
            return !isDestroy(context)
        } else if (context is ContextWrapper) {
            if (context.baseContext is Activity) {
                val activity = context.baseContext as Activity
                return !isDestroy(activity)
            }
        }
        return true
    }

    private fun isDestroy(activity: Activity?): Boolean {
        if (activity == null) {
            return true
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.isFinishing || activity.isDestroyed
        } else {
            activity.isFinishing
        }
    }
}