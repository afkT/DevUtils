package dev.simple.bindingadapters.view

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import dev.utils.LogPrintUtils

// =======================================
// = ImageView Load Native BindingAdapter =
// =======================================

/**
 * [ImageView] 不经图片引擎、直接调用原生 API 的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_image_native_*`；每个适配器仅一个布局属性，语义对齐对应 `set*` 方法。
 * <pre>
 *     不走 [dev.engine.extensions.image.display] 与 [dev.engine.image.IImageEngine]；无 engine / config / listener 参数。
 *     经引擎加载（url、缓存、监听等）请使用 `ImageViewLoadEngine.kt` 中 `binding_image_*`（与本文件属性名互不重复）。
 * </pre>
 */

private const val TAG = "Dev_ImageView_Load_Native_BindingAdapter"

// ========
// = 前景图 =
// ========

/**
 * 通过数据绑定按 Uri 设置当前 ImageView 的前景图。
 * <pre>
 *     布局属性 `binding_image_native_uri`；语义对齐 [ImageView.setImageURI]；
 *     `uri` 为空时清空前景图。
 * </pre>
 *
 * @param uri 图片 Uri，可为空
 * @return [ImageView]
 */
@BindingAdapter("binding_image_native_uri")
fun ImageView.bindingImageNativeUri(uri: Uri?): ImageView {
    try {
        if (uri == null) {
            setImageDrawable(null)
        } else {
            setImageURI(uri)
        }
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageNativeUri")
    }
    return this
}

/**
 * 通过数据绑定按位图设置当前 ImageView 的前景图。
 * <pre>
 *     布局属性 `binding_image_native_bitmap`；语义对齐 [ImageView.setImageBitmap]。
 * </pre>
 *
 * @param bitmap 位图，可为空
 * @return [ImageView]
 */
@BindingAdapter("binding_image_native_bitmap")
fun ImageView.bindingImageNativeBitmap(bitmap: Bitmap?): ImageView {
    try {
        setImageBitmap(bitmap)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageNativeBitmap")
    }
    return this
}

/**
 * 通过数据绑定按 Drawable 设置当前 ImageView 的前景图。
 * <pre>
 *     布局属性 `binding_image_native_drawable`；语义对齐 [ImageView.setImageDrawable]。
 * </pre>
 *
 * @param drawable 前景 Drawable，可为空
 * @return [ImageView]
 */
@BindingAdapter("binding_image_native_drawable")
fun ImageView.bindingImageNativeDrawable(drawable: Drawable?): ImageView {
    try {
        setImageDrawable(drawable)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageNativeDrawable")
    }
    return this
}

/**
 * 通过数据绑定按资源 ID 设置当前 ImageView 的前景图。
 * <pre>
 *     布局属性 `binding_image_native_resource`；语义对齐 [ImageView.setImageResource]；
 *     `resId` 为空或 0 时清空前景图。
 * </pre>
 *
 * @param resId 图片资源 ID，可为空；非空时应为有效 drawable 资源
 * @return [ImageView]
 */
@BindingAdapter("binding_image_native_resource")
fun ImageView.bindingImageNativeResource(@DrawableRes resId: Int?): ImageView {
    try {
        when {
            resId == null || resId == 0 -> setImageDrawable(null)
            else -> setImageResource(resId)
        }
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageNativeResource")
    }
    return this
}

// =======
// = 背景 =
// =======

/**
 * 通过数据绑定设置当前 ImageView 的背景色。
 * <pre>
 *     布局属性 `binding_image_native_bg_color`；语义对齐 [ImageView.setBackgroundColor]；
 *     `color` 为空时使用透明色。
 * </pre>
 *
 * @param color 背景色整型（含 alpha），可为空
 * @return [ImageView]
 */
@BindingAdapter("binding_image_native_bg_color")
fun ImageView.bindingImageNativeBgColor(@ColorInt color: Int?): ImageView {
    try {
        setBackgroundColor(color ?: Color.TRANSPARENT)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageNativeBgColor")
    }
    return this
}

/**
 * 通过数据绑定按资源 ID 设置当前 ImageView 的背景。
 * <pre>
 *     布局属性 `binding_image_native_bg_resource`；语义对齐 [ImageView.setBackgroundResource]；
 *     `resId` 为空或 0 时移除背景。
 * </pre>
 *
 * @param resId 背景资源 ID，可为空；非空时应为有效 drawable 资源
 * @return [ImageView]
 */
@BindingAdapter("binding_image_native_bg_resource")
fun ImageView.bindingImageNativeBgResource(@DrawableRes resId: Int?): ImageView {
    try {
        when {
            resId == null || resId == 0 -> background = null
            else -> setBackgroundResource(resId)
        }
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageNativeBgResource")
    }
    return this
}